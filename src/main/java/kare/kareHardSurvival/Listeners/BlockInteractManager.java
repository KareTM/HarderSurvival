package kare.kareHardSurvival.Listeners;

import com.jeff_media.customblockdata.CustomBlockData;
import kare.kareHardSurvival.Advancements.AdvancementManager;
import kare.kareHardSurvival.GUI.ForgeGUI;
import kare.kareHardSurvival.Helpers.FlagHelper;
import kare.kareHardSurvival.Helpers.InventoryHelpers;
import kare.kareHardSurvival.Helpers.FuelHelper;
import kare.kareHardSurvival.Helpers.Prospecting.ProspectActionBar;
import kare.kareHardSurvival.Helpers.Prospecting.ProspectingProfiles;
import kare.kareHardSurvival.Helpers.Prospecting.Prospector;
import org.bukkit.Material;
import org.bukkit.block.BlastFurnace;
import org.bukkit.block.Furnace;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class BlockInteractManager implements Listener {
    static Plugin plugin;

    public BlockInteractManager(Plugin plugin) {
        BlockInteractManager.plugin = plugin;
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        var block = event.getClickedBlock();
        var p = event.getPlayer();
        if (block == null) return;

        var cbd = new CustomBlockData(block, plugin);

        if (FlagHelper.hasFlag(cbd, FlagHelper.flagRequiresCarving)) {
            if (event.getAction().isRightClick())
                event.setCancelled(true);
        }

        if (block.getType() == Material.CAMPFIRE) {
            if (event.getAction().isRightClick()) {
                if (AdvancementManager.Terracotta.getProgression(p) == 0)
                    AdvancementManager.Terracotta.incrementProgression(p);
            }
        }

        if (FlagHelper.hasFlag(cbd, FlagHelper.flagForge) && event.getAction().isRightClick()) {
            event.setCancelled(true);
            ForgeGUI.getGUI(block).open(p);
        }

        var tool = InventoryHelpers.getMainhand(p);
        if (FlagHelper.hasFlag(tool, FlagHelper.flagProspect)) {
            if (event.getAction().isRightClick() && p.getCooldown(tool) == 0) {
                InventoryHelpers.damageToolConsiderUnbreaking(p, tool);
                List<Prospector.ProspectResult> results = Prospector.prospect(p.getLocation(), ProspectingProfiles.getFromTool(tool));
                ProspectActionBar.showTopResultsOnce(p, results);
                p.setCooldown(FlagHelper.flagProspect, FlagHelper.getIntValue(tool, FlagHelper.flagProspectCooldown));
            }
            event.setCancelled(true);
            p.swingMainHand();
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        var tool = InventoryHelpers.getMainhand(p);
        if (tool.getType().asBlockType() == null || tool.isEmpty()) {
            tool = InventoryHelpers.getOffhand(p);
        }
        var block = event.getBlockPlaced();

        if (!tool.isEmpty()) {
            if (FlagHelper.hasFlag(tool, FlagHelper.flagNoPlace)) {
                event.setCancelled(true);
                return;
            }

            var cbd = new CustomBlockData(block, plugin);
            if (FlagHelper.hasFlag(tool, FlagHelper.flagRequiresCarving)) {
                FlagHelper.setByteValue(cbd, FlagHelper.flagRequiresCarving, 2);
            }

            if (FlagHelper.hasFlag(tool, FlagHelper.flagFurnace)) {
                FlagHelper.setFlag(cbd, FlagHelper.flagFurnace, true);
            }

            if (FlagHelper.hasFlag(tool, FlagHelper.flagFurnaceTier1)) {
                FlagHelper.setFlag(cbd, FlagHelper.flagFurnaceTier1, true);
                var furnace = (Furnace) block.getState();
                furnace.setCookSpeedMultiplier((double) 1 / FuelHelper.TIER1_SPEED_DIVISOR);

                furnace.update();
            } else if (FlagHelper.hasFlag(tool, FlagHelper.flagFurnaceTier2)) {
                FlagHelper.setFlag(cbd, FlagHelper.flagFurnaceTier2, true);
                var furnace = (Furnace) block.getState();
                furnace.setCookSpeedMultiplier((double) 1 / FuelHelper.TIER2_SPEED_DIVISOR);

                furnace.update();
            } else if (FlagHelper.hasFlag(tool, FlagHelper.flagFurnaceTier3)) {
                FlagHelper.setFlag(cbd, FlagHelper.flagFurnaceTier3, true);
                var furnace = (BlastFurnace) block.getState();
                furnace.setCookSpeedMultiplier((double) 1 / FuelHelper.TIER3_SPEED_DIVISOR);

                furnace.update();
            } else if (FlagHelper.hasFlag(tool, FlagHelper.flagFurnaceTier3Alt)) {
                FlagHelper.setFlag(cbd, FlagHelper.flagFurnaceTier3Alt, true);
                var furnace = (Furnace) block.getState();
                furnace.setCookSpeedMultiplier(FuelHelper.TIER3_ALT_SPEED_MULT);

                furnace.update();
            } else if (FlagHelper.hasFlag(tool, FlagHelper.flagFurnaceTier4)) {
                FlagHelper.setFlag(cbd, FlagHelper.flagFurnaceTier4, true);
                var furnace = (BlastFurnace) block.getState();
                furnace.setCookSpeedMultiplier((double) 1 / FuelHelper.TIER4_SPEED_DIVISOR);

                furnace.update();
            }

            if (tool.getType().equals(Material.CAMPFIRE)) {
                Campfire campfire = (Campfire) block.getBlockData();
                campfire.setLit(false);
                block.setBlockData(campfire);
            }

            if (FlagHelper.hasFlag(tool, FlagHelper.flagForge)) {
                FlagHelper.setFlag(cbd, FlagHelper.flagForge, true);
            }
        }
    }

    @EventHandler
    public void onBlockChangeEvent(EntityChangeBlockEvent event) {
        if (isStrippedLog(event.getTo())) {
            var et = event.getEntityType();
            if (et == EntityType.PLAYER) {
                var p = (Player) event.getEntity();
                var tool = InventoryHelpers.getMainhand(p);
                if (!tool.getType().name().contains("AXE")) {
                    tool = InventoryHelpers.getOffhand(p);
                }

                if (FlagHelper.hasFlag(tool, FlagHelper.flagAxe)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    private boolean isLog(Material type) {
        return type.name().endsWith("_LOG");
    }

    private boolean isStrippedLog(Material type) {
        return type.name().startsWith("STRIPPED_") && type.name().endsWith("_LOG");
    }
}
