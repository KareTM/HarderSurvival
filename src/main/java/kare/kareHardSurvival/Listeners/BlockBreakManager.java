package kare.kareHardSurvival.Listeners;

import com.jeff_media.customblockdata.CustomBlockData;
import io.papermc.paper.datacomponent.DataComponentTypes;
import kare.kareHardSurvival.Advancements.AdvancementManager;
import kare.kareHardSurvival.GUI.ForgeGUI;
import kare.kareHardSurvival.Helpers.*;
import kare.kareHardSurvival.Items.ItemManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class BlockBreakManager implements Listener {
    static Plugin plugin;
    TreeStripper ts;

    public BlockBreakManager(Plugin plugin) {
        BlockBreakManager.plugin = plugin;
        ts = new TreeStripper(plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        var p = e.getPlayer();
        var tool = InventoryHelpers.getMainhand(p);
        var block = e.getBlock();

        if (Tag.LOGS.getValues().contains(block.getType())) {
            if (!FlagHelper.hasFlag(tool, FlagHelper.flagAxe)
                    && !Tag.ITEMS_AXES.getValues().contains(tool.getType())) {
                e.setCancelled(true);
                AdvancementManager.NoLog.grant(p);
                if (tool.isEmpty())
                    p.damage(1);
            } else if (FlagHelper.hasFlag(tool, FlagHelper.flagAxe) || p.isSneaking()){
                e.setDropItems(false);
                ts.onBlockBreak(p, e);
            }
        }

        var bd = new CustomBlockData(block, plugin);
        carveTable(e, bd, tool, block);
        cutGrass(p, block, tool);
        campfire(e, block, tool, p);
        furnaces(e, block, bd);
        forge(e, bd, block);
    }

    private static void forge(BlockBreakEvent e, CustomBlockData bd, Block block) {
        if (FlagHelper.hasFlag(bd, FlagHelper.flagForge)) {
            e.setDropItems(false);
            var fd = ForgeStorage.load(block, plugin);
            var world = block.getWorld();
            var loc = block.getLocation().toCenterLocation();
            if (fd.hammer != null && !fd.hammer.isEmpty()) {
                ItemStack newHammer = InventoryHelpers.getFreshHammer(fd.hammer);
                world.dropItemNaturally(loc, newHammer == null ? ItemStack.empty() : newHammer);
            }
            if (fd.material1 != null && !fd.material1.isEmpty())
                world.dropItemNaturally(loc, InventoryHelpers.stripIFUUID(fd.material1));
            if (fd.material2 != null && !fd.material2.isEmpty())
                world.dropItemNaturally(loc, InventoryHelpers.stripIFUUID(fd.material2));
            FlagHelper.removeFlag(bd, FlagHelper.flagForge);
            FlagHelper.removeFlag(bd, ForgeStorage.hammer);
            FlagHelper.removeFlag(bd, ForgeStorage.matFirst);
            FlagHelper.removeFlag(bd, ForgeStorage.matSecond);
            ForgeGUI.removeGUI(block);

            world.dropItemNaturally(loc, ItemManager.createForge());
        }
    }

    private static void furnaces(BlockBreakEvent e, Block block, CustomBlockData bd) {
        var loc = block.getLocation().toCenterLocation();
        var world = block.getWorld();

        if (FlagHelper.hasFlag(bd, FlagHelper.flagFurnace)) {
            FlagHelper.removeFlag(bd, FlagHelper.flagFurnace);
        }

        if (FlagHelper.hasFlag(bd, FlagHelper.flagFurnaceTier1)) {
            world.dropItemNaturally(loc, ItemManager.createFurnace());
            FlagHelper.removeFlag(bd, FlagHelper.flagFurnaceTier1);
        }
        if (FlagHelper.hasFlag(bd, FlagHelper.flagFurnaceTier2)) {
            world.dropItemNaturally(loc, ItemManager.createFurnaceCopper());
            FlagHelper.removeFlag(bd, FlagHelper.flagFurnaceTier2);
        }
        if (FlagHelper.hasFlag(bd, FlagHelper.flagFurnaceTier3)) {
            world.dropItemNaturally(loc, ItemManager.createWroughtIronBlastFurnace());
            FlagHelper.removeFlag(bd, FlagHelper.flagFurnaceTier3);
        }
        if (FlagHelper.hasFlag(bd, FlagHelper.flagFurnaceTier4)) {
            FlagHelper.removeFlag(bd, FlagHelper.flagFurnaceTier4);
        }
    }

    private static void campfire(BlockBreakEvent e, Block block, ItemStack tool, Player p) {
        if (block.getType().equals(Material.CAMPFIRE)) {
            if (!FlagHelper.hasFlag(tool, FlagHelper.flagAxe) && tool.getType().equals(Material.FLINT)) {
                Campfire campfire = (Campfire) block.getBlockData();
                if (!campfire.isLit()) {
                    e.setCancelled(true);
                    campfire.setLit(true);
                    block.setBlockData(campfire);
                    AdvancementManager.Campfire.grant(p);

                    p.playSound(block.getLocation(), Sound.ITEM_FIRECHARGE_USE, 1f, 1f);
                }
            }
        }
    }

    private static void cutGrass(Player p, Block block, ItemStack tool) {
        if (block.getType().equals(Material.SHORT_GRASS) || block.getType().equals(Material.TALL_GRASS)) {
            if (FlagHelper.hasFlag(tool, FlagHelper.flagGrass)) {
                InventoryHelpers.damageTool(p, tool);

                if (Math.random() < 0.5) {
                    var count = 1;
                    if (block.getType() == Material.TALL_GRASS) {
                        count = 2;
                    }

                    var item = ItemStack.of(Material.SHORT_GRASS, count);
                    block.getWorld().dropItemNaturally(block.getLocation().toCenterLocation(), item);

                    AdvancementManager.Grass.grant(p);
                    p.discoverRecipe(RecipeKeyList.twine);
                }
            }
        }
    }

    private static void carveTable(BlockBreakEvent e, CustomBlockData bd, ItemStack tool, Block block) {
        if (FlagHelper.hasByteValue(bd, FlagHelper.flagRequiresCarving)) {
            if (FlagHelper.hasFlag(tool, FlagHelper.flagCarvingTool)) {
                var carvesLeft = FlagHelper.getByteValue(bd, FlagHelper.flagRequiresCarving);
                InventoryHelpers.damageTool(e.getPlayer(), tool);
                e.setCancelled(true);
                if (carvesLeft > 0) {
                    Particle.CLOUD.builder().location(block.getLocation().toCenterLocation()).
                            offset(0.33, 0.33, 0.33).count(15).extra(0.05).
                            receivers(32, true).spawn();
                    FlagHelper.setByteValue(bd, FlagHelper.flagRequiresCarving, carvesLeft - 1);
                    AdvancementManager.Carving.incrementProgression(e.getPlayer());
                } else {
                    Particle.BLOCK.builder().data(block.getBlockData()).location(block.getLocation().toCenterLocation()).
                            offset(0.5, 0.5, 0.5).count(10).
                            receivers(32, true).spawn();
                    FlagHelper.removeFlag(bd, FlagHelper.flagRequiresCarving);
                    AdvancementManager.Carving.grant(e.getPlayer());
                }
            } else {
                e.setDropItems(false);
                var item = ItemManager.createCraftingTable();
                block.getWorld().dropItemNaturally(block.getLocation().toCenterLocation(), item);
                bd.remove(FlagHelper.flagRequiresCarving);
            }
        }
    }

    @EventHandler
    public void onBlockDropItem(BlockDropItemEvent e) {
        var p = e.getPlayer();
        var tool = InventoryHelpers.getMainhand(p);
        furnaceCheck(e);
        stickObtain(e, tool);
        handaxeObtain(e, tool, p);
        flintObtain(e, tool);
        copperObtain(e, tool);
        coalObtain(e, tool);
        ironObtain(e, tool);
    }

    private static void furnaceCheck(BlockDropItemEvent e) {
        var toRemove = new ArrayList<Item>();

        for(var item : e.getItems()) {
            if (item.getItemStack().getType() != Material.FURNACE && item.getItemStack().getType() != Material.BLAST_FURNACE)
                continue;
            var name = item.getItemStack().getData(DataComponentTypes.CUSTOM_NAME);
            if (name == null)
                continue;
            if (name.equals(ItemManager.createFurnace().getData(DataComponentTypes.CUSTOM_NAME))) {
                toRemove.add(item);
            }
            else if (name.equals(ItemManager.createFurnaceCopper().getData(DataComponentTypes.CUSTOM_NAME))) {
                toRemove.add(item);
            }
            else if (name.equals(ItemManager.createWroughtIronBlastFurnace().getData(DataComponentTypes.CUSTOM_NAME))) {
                toRemove.add(item);
            }
        }

        if (e.getItems().size() == toRemove.size()) {
            e.getItems().clear();
        } else {
            e.getItems().removeAll(toRemove);
        }
    }

    private static void copperObtain(BlockDropItemEvent e, ItemStack tool) {
        var block = e.getBlockState();
        if (e.getItems().isEmpty())
            return;

        if (block.getType() == Material.COPPER_ORE || block.getType() == Material.DEEPSLATE_COPPER_ORE) {
            // Remove vanilla drops
            e.getItems().clear();

            // Determine tool tier
            int min, max;
            if (FlagHelper.hasFlag(tool, FlagHelper.flagPickTier1)) {
                min = 1;
                max = 2;
            } else if (FlagHelper.hasFlag(tool, FlagHelper.flagPickTier2)) {
                min = 3;
                max = 5;
            } else {
                min = 9;
                max = 12;
            }

            int fortune = tool.getEnchantmentLevel(Enchantment.FORTUNE);
            int amount = applyFortune(ThreadLocalRandom.current().nextInt(min, max + 1), fortune);

            ItemStack drop = new ItemStack(Material.RAW_COPPER, amount);
            block.getWorld().dropItemNaturally(block.getLocation().toCenterLocation(), drop);
        }
    }

    private static void coalObtain(BlockDropItemEvent e, ItemStack tool) {
        var block = e.getBlockState();
        if (e.getItems().isEmpty())
            return;

        if (block.getType() == Material.COAL_ORE || block.getType() == Material.DEEPSLATE_COAL_ORE) {
            // Remove vanilla drops
            e.getItems().clear();

            // Determine tool tier
            int min, max;
            if (FlagHelper.hasFlag(tool, FlagHelper.flagPickTier1)) {
                min = 1;
                max = 1;
            } else if (FlagHelper.hasFlag(tool, FlagHelper.flagPickTier2)) {
                min = 2;
                max = 3;
            } else if (FlagHelper.hasFlag(tool, FlagHelper.flagPickTier3)) {
                min = 3;
                max = 5;
            } else {
                min = 4;
                max = 6;
            }

            int fortune = tool.getEnchantmentLevel(Enchantment.FORTUNE);
            int amount = applyFortune(ThreadLocalRandom.current().nextInt(min, max + 1), fortune);

            ItemStack drop = new ItemStack(Material.COAL, amount);
            block.getWorld().dropItemNaturally(block.getLocation().toCenterLocation(), drop);
        }
    }

    private static void ironObtain(BlockDropItemEvent e, ItemStack tool) {
        var block = e.getBlockState();
        if (e.getItems().isEmpty())
            return;

        if (block.getType() == Material.IRON_ORE || block.getType() == Material.DEEPSLATE_IRON_ORE) {
            // Remove vanilla drops
            e.getItems().clear();

            // Determine tool tier
            int min, max;
            if (FlagHelper.hasFlag(tool, FlagHelper.flagPickTier1)) {
                min = 0;
                max = 0;
            } else if (FlagHelper.hasFlag(tool, FlagHelper.flagPickTier2)) {
                min = 0;
                max = 0;
            } else if (FlagHelper.hasFlag(tool, FlagHelper.flagPickTier3)) {
                min = 1;
                max = 2;
            } else if (FlagHelper.hasFlag(tool, FlagHelper.flagPickTier4)) {
                min = 3;
                max = 5;
            } else {
                min = 9;
                max = 12;
            }

            int fortune = tool.getEnchantmentLevel(Enchantment.FORTUNE);
            int amount = applyFortune(ThreadLocalRandom.current().nextInt(min, max + 1), fortune);

            ItemStack drop = new ItemStack(Material.RAW_IRON, amount);
            block.getWorld().dropItemNaturally(block.getLocation().toCenterLocation(), drop);
        }
    }

    private static void flintObtain(BlockDropItemEvent e, ItemStack tool) {
        var block = e.getBlockState();
        if ((block.getType() == Material.COBBLESTONE || block.getType() == Material.STONE)
                && FlagHelper.hasFlag(tool, FlagHelper.flagFlint)) {
            var item = e.getBlock().getWorld().spawn(e.getBlock().getLocation(), Item.class,
                    i -> i.setItemStack(ItemStack.of(Material.FLINT)));

            e.getItems().add(item);

            if (block.getType() == Material.STONE) {
                e.getBlock().setType(Material.COBBLESTONE);
            }

            var p = e.getPlayer();
            AdvancementManager.Flint.grant(p);
            p.discoverRecipe(RecipeKeyList.flintAxeSimple);
            p.discoverRecipe(RecipeKeyList.flintKnifeSimple);
        }
    }

    private static void handaxeObtain(BlockDropItemEvent e, ItemStack tool, Player p) {
        if (e.getBlockState().getType() == Material.STONE && tool.isEmpty()) {
            var item = e.getBlock().getWorld().spawn(e.getBlock().getLocation(), Item.class,
                    i -> i.setItemStack(ItemManager.createHandaxe()));
            e.getItems().add(item);

            e.getBlock().setType(Material.COBBLESTONE);
            p.damage(1);

            AdvancementManager.Handaxe.grant(p);
        }
    }

    private static void stickObtain(BlockDropItemEvent e, ItemStack tool) {
        if (Tag.LEAVES.getValues().contains(e.getBlockState().getType())) {
            if (!FlagHelper.hasFlag(tool, FlagHelper.flagSticks)) {
                e.getItems().removeIf(item -> item.getItemStack().getType() == Material.STICK);
            } else {
                boolean found = false;
                for (var item : e.getItems()) {
                    if (item.getItemStack().getType() == Material.STICK) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    ItemStack stick = ItemStack.of(Material.STICK);
                    var item = e.getBlock().getWorld().spawn(e.getBlock().getLocation(), Item.class, i -> i.setItemStack(stick));
                    e.getItems().add(item);
                }

                AdvancementManager.Sticks.grant(e.getPlayer());
            }
        }
    }

    private static int applyFortune(int base, int level) {
        if (level <= 0) return base;

        int total = level + 2; // 2 no-bonus slots + L bonus slots
        int roll = ThreadLocalRandom.current().nextInt(total); // 0..(total-1)

        if (roll <= 1) {
            return base; // no bonus
        }

        // roll = 2 -> ×2
        // roll = 3 -> ×3
        // ...
        // roll = (L+1) -> ×(L+1)
        return base * roll;
    }
}
