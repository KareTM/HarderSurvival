package kare.kareHardSurvival.Listeners;

import com.jeff_media.customblockdata.CustomBlockData;
import kare.kareHardSurvival.Advancements.AdvancementManager;
import kare.kareHardSurvival.Helpers.FlagHelper;
import kare.kareHardSurvival.Helpers.FuelHelper;
import kare.kareHardSurvival.Helpers.Granter.Granter;
import kare.kareHardSurvival.Helpers.Granter.GranterBuilder;
import kare.kareHardSurvival.Helpers.RecipeKeyList;
import kare.kareHardSurvival.Items.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FurnaceManager implements Listener {
    static Plugin plugin;

    public FurnaceManager(Plugin plugin) {
        FurnaceManager.plugin = plugin;
    }

    static List<Granter> granters = new ArrayList<>(List.of(
            GranterBuilder.of(ItemManager.createHeatedCopper()).discover(RecipeKeyList.forge).grant(AdvancementManager.HeatedCopper).build(),
            GranterBuilder.of(ItemStack.of(Material.COPPER_NUGGET)).grant(AdvancementManager.Copper).build(),
            GranterBuilder.of(ItemManager.createIronBloom()).grant(AdvancementManager.IronBloom)
                    .condition(p -> {
                        p.undiscoverRecipe(NamespacedKey.minecraft("netherite_scrap"));
                        p.undiscoverRecipe(NamespacedKey.minecraft("netherite_scrap_from_blasting"));
                        return true;
                    })
                    .build()
    ));

    @EventHandler
    public void onFurnaceExtract(FurnaceExtractEvent event) {
        var result = event.getItemType();
        var p = event.getPlayer();

        for (var rule : granters) {
            if (rule.items().contains(ItemStack.of(result))) {
                rule.grant().accept(p);
            }
        }

        FurnaceRecipe found = null;
        for (@NotNull Iterator<Recipe> it = Bukkit.recipeIterator(); it.hasNext(); ) {
            var r = it.next();
            if (r instanceof FurnaceRecipe fr) {
                if (fr.getResult().getType() == result) {
                    found = fr;
                    break;
                }
            }
        }

        if (found == null) return;

        for (var rule : granters) {
            if (rule.items().contains(found.getResult())) {
                rule.grant().accept(p);
            }
        }
    }

    @EventHandler
    public void onFurnaceStart(FurnaceBurnEvent e) {
        // Check for no_burn flag on the fuel item
        var fuel = e.getFuel();

        if (FlagHelper.hasFlag(fuel, FlagHelper.flagNoBurn)) {
            e.setCancelled(true);
            return;
        }

        var block = e.getBlock();
        var bd = new CustomBlockData(block, plugin);
        var fuelType = fuel.getType();
        if (FlagHelper.hasFlag(bd, FlagHelper.flagFurnaceTier1)) {
            if (!FuelHelper.fuelTier1.contains(fuelType)) {
                e.setCancelled(true);
                return;
            }
        } else if (FlagHelper.hasFlag(bd, FlagHelper.flagFurnaceTier2)) {
            if (!FuelHelper.fuelTier2.contains(fuelType)) {
                e.setCancelled(true);
                return;
            }
        } else if (FlagHelper.hasFlag(bd, FlagHelper.flagFurnaceTier3)) {
            if (!FuelHelper.fuelTier3.contains(fuelType)) {
                e.setCancelled(true);
                return;
            }
        } else if (FlagHelper.hasFlag(bd, FlagHelper.flagFurnaceTier4)) {
            if (!FuelHelper.fuelTier4.contains(fuelType)) {
                e.setCancelled(true);
                return;
            }
        }

        var inv = ((Furnace) e.getBlock().getState()).getInventory();
        var smelting = inv.getSmelting(); // the input item

        if (smelting == null) return;
        if (FlagHelper.hasFlag(smelting, FlagHelper.flagNoSmelt)) {
            e.setCancelled(true);
            return;
        }

        FurnaceRecipe found = null;
        for (@NotNull Iterator<Recipe> it = Bukkit.recipeIterator(); it.hasNext(); ) {
            var r = it.next();
            if (r instanceof FurnaceRecipe fr) {
                if (fr.getInputChoice().test(smelting)) {
                    found = fr;
                    break;
                }
            }
        }

        if (found == null) return;

        if (found.getKey().getNamespace().equals(plugin.namespace()) && !FlagHelper.hasFlag(bd, FlagHelper.flagFurnace)) {
            e.setCancelled(true);
        }

        if (FlagHelper.hasFlag(found.getResult(), FlagHelper.flagRequiresFurnaceTier2)) {
            if (!FlagHelper.hasFlag(bd, FlagHelper.flagFurnaceTier2)) {
                e.setCancelled(true);
            }
        }
    }
}
