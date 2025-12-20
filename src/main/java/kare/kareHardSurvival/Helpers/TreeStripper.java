package kare.kareHardSurvival.Helpers;

import kare.kareHardSurvival.Advancements.AdvancementManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class TreeStripper {
    // Per-player progress
    private final Plugin plugin;
    private final Map<UUID, Queue<Block>> playerQueue = new HashMap<>();
    private final Map<UUID, List<Block>> playerStripped = new HashMap<>();

    public TreeStripper(Plugin plugin) {
        this.plugin = plugin;
    }

    private static final BlockFace[] HORIZONTAL = {
            BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST
    };

    private static final BlockFace[] DIAGONAL = {
            BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.SOUTH_EAST, BlockFace.SOUTH_WEST
    };

    public void onBlockBreak(Player player, BlockBreakEvent e) {
        Block block = e.getBlock();
        Material type = block.getType();
        boolean firstBreak = false;

        if (!isLog(type)) return;

        e.setDropItems(false); // We'll handle drops

        UUID uuid = player.getUniqueId();
        Queue<Block> queue = playerQueue.computeIfAbsent(uuid, k -> new LinkedList<>());
        List<Block> stripped = playerStripped.computeIfAbsent(uuid, k -> new ArrayList<>());

        if (!isStrippedLog(type)) {
            // New tree -> start progress
            queue.clear();
            stripped.clear();
            queue.addAll(findNeighbors(block));
            stripLog(block);
            stripped.add(block);

            firstBreak = true;
        } else {
            // Existing tree in progress -> strip one block
            if (queue.isEmpty()) {
                e.setDropItems(true);
                return;
            }
            Block next = queue.poll();
            if (next == null || next.getType() == Material.AIR) return;
            stripLog(next);
            stripped.add(next);
            queue.addAll(findNeighbors(next).stream().filter(n -> !queue.contains(n)).toList());
        }

        // Finalization: all logs stripped
        if (queue.isEmpty() && !firstBreak) {
            for (Block b : stripped) {
                b.breakNaturally();

            }
            stripped.clear();
            playerQueue.remove(uuid);
            AdvancementManager.Log.grant(player);
            player.discoverRecipes(List.of(RecipeKeyList.planks));
            player.discoverRecipes(List.of(RecipeKeyList.planksCut));
            player.discoverRecipe(RecipeKeyList.campfire);
        } else {
            Bukkit.getScheduler().runTask(plugin, () -> {
                block.setType(type);
                stripLog(block);
            });
        }
    }

    private boolean isLog(Material type) {
        return type.name().endsWith("_LOG");
    }

    private boolean isStrippedLog(Material type) {
        return type.name().startsWith("STRIPPED_") && type.name().endsWith("_LOG");
    }

    private void stripLog(Block block) {
        Material stripped = getStrippedMaterial(block.getType());
        block.setType(stripped);
    }

    private Material getStrippedMaterial(Material log) {
        if (isStrippedLog(log)) return log;
        return Material.valueOf("STRIPPED_" + log.name());
    }

    /**
     * Returns horizontal + above + diagonal above logs
     */
    private List<Block> findNeighbors(Block block) {
        List<Block> neighbors = new ArrayList<>();
        var COMBINED = new ArrayList<BlockFace>();
        COMBINED.addAll(Arrays.asList(HORIZONTAL));
        COMBINED.addAll(Arrays.asList(DIAGONAL));

        // Horizontal
        for (BlockFace face : COMBINED) {
            Block adj = block.getRelative(face);
            if (isLog(adj.getType()) && !isStrippedLog(adj.getType())) {
                neighbors.add(adj);
            }
        }

        // Directly above
        Block above = block.getRelative(BlockFace.UP);
        if (isLog(above.getType()) && !isStrippedLog(above.getType())) neighbors.add(above);

        // Diagonals above (branches)
        for (BlockFace diag : COMBINED) {
            Block b = block.getRelative(diag).getRelative(BlockFace.UP);
            if (isLog(b.getType()) && !isStrippedLog(b.getType())) neighbors.add(b);
        }

        return neighbors;
    }
}
