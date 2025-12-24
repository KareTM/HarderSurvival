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
import org.bukkit.block.BlastFurnace;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

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
            GranterBuilder.of(ItemManager.createCopper()).grant(AdvancementManager.BulkCopper).build(),
            GranterBuilder.of(ItemManager.createCastIron()).grant(AdvancementManager.CastIron).build(),
            GranterBuilder.of(ItemManager.createIronBloom()).grant(AdvancementManager.IronBloom).build(),
            GranterBuilder.of(ItemManager.createCoalCoke()).discover(RecipeKeyList.heavyBurden, RecipeKeyList.richBloom).grant(AdvancementManager.Coke).build(),
            GranterBuilder.of(ItemManager.createRichBloom()).grant(AdvancementManager.RichBloom).build(),
            GranterBuilder.of(ItemManager.createPigIronMass()).discover(RecipeKeyList.steelBilletFromPigIron).grant(AdvancementManager.PigIron).build(),
            GranterBuilder.of(ItemManager.createSteelBillet()).grant(AdvancementManager.SteelBillet).build()
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

        var furnace = (Furnace) event.getBlock().getState();
        searchResult searchResult = getSearchResult(result, furnace);
        if (searchResult == null) return;

        for (var rule : granters) {
            if (searchResult.foundFurnace() != null && rule.items().contains(searchResult.foundFurnace().getResult())) {
                rule.grant().accept(p);
            } else if (searchResult.foundBlast() != null && rule.items().contains(searchResult.foundBlast().getResult())) {
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
            Bukkit.getScheduler().runTask(plugin, t -> {
                var furnace = (BlastFurnace) e.getBlock().getState();
                furnace.setBurnTime((short) (furnace.getBurnTime() * FuelHelper.TIER3_FUEL_MULT));
                furnace.update();
            });
        } else if (FlagHelper.hasFlag(bd, FlagHelper.flagFurnaceTier3Alt)) {
            if (!FuelHelper.fuelTier3.contains(fuelType)) {
                e.setCancelled(true);
                return;
            }
            Bukkit.getScheduler().runTask(plugin, t -> {
                var furnace = (Furnace) e.getBlock().getState();
                furnace.setBurnTime((short) (furnace.getBurnTime() / FuelHelper.TIER3_ALT_FUEL_DIV + 5));
                furnace.update();
            });
        } else if (FlagHelper.hasFlag(bd, FlagHelper.flagFurnaceTier4)) {
            if (!FuelHelper.fuelTier4.contains(fuel.asOne())) {
                e.setCancelled(true);
                return;
            }
        }

        var inv = ((Furnace) e.getBlock().getState()).getInventory();
        var smelting = inv.getSmelting(); // the input item

        if (smelting == null) return;

        searchResult searchResult = getSearchResult(smelting);
        if (searchResult == null) return;

        var found = searchResult.foundFurnace != null ? searchResult.foundFurnace : searchResult.foundBlast;

        if (FlagHelper.hasFlag(smelting, FlagHelper.flagNoSmelt)) {
            e.setCancelled(true);
            return;
        } else if (FlagHelper.hasFlag(smelting, FlagHelper.flagSmeltOnlyPlugin)) {
            if (!plugin.namespace().equals(found.getKey().getNamespace())) {
                e.setCancelled(true);
                return;
            }
        }


        if (found.getKey().getNamespace().equals(plugin.namespace()) && !FlagHelper.hasFlag(bd, FlagHelper.flagFurnace)) {
            e.setCancelled(true);
        }

        if (FlagHelper.hasFlag(found.getResult(), FlagHelper.flagRequiresFurnaceTier2)) {
            if (!FlagHelper.hasFlag(bd, FlagHelper.flagFurnaceTier2) &&
                    !FlagHelper.hasFlag(bd, FlagHelper.flagFurnaceTier3) &&
                    !FlagHelper.hasFlag(bd, FlagHelper.flagFurnaceTier3Alt) &&
                    !FlagHelper.hasFlag(bd, FlagHelper.flagFurnaceTier4)) {
                e.setCancelled(true);
            }
        } else if (FlagHelper.hasFlag(found.getResult(), FlagHelper.flagRequiresFurnaceTier4)) {
            if (!FlagHelper.hasFlag(bd, FlagHelper.flagFurnaceTier4)) {
                e.setCancelled(true);
            }
        }
    }

    private static @Nullable searchResult getSearchResult(Material result, Furnace furnace) {
        FurnaceRecipe foundFurnace = null;
        BlastingRecipe foundBlast = null;
        for (@NotNull Iterator<Recipe> it = Bukkit.recipeIterator(); it.hasNext(); ) {
            var r = it.next();
            if (r instanceof FurnaceRecipe fr) {
                if (fr.getResult().getType() == result && furnace.hasRecipeUsedCount(fr.getKey())) {
                    foundFurnace = fr;
                    break;
                }
            } else if (r instanceof BlastingRecipe br) {
                if (br.getResult().getType() == result && furnace.hasRecipeUsedCount(br.getKey())) {
                    foundBlast = br;
                    break;
                }
            }
        }

        if (foundFurnace == null && foundBlast == null) return null;
        return new searchResult(foundFurnace, foundBlast);
    }

    private static @Nullable searchResult getSearchResult(ItemStack input) {
        FurnaceRecipe foundFurnace = null;
        BlastingRecipe foundBlast = null;
        for (@NotNull Iterator<Recipe> it = Bukkit.recipeIterator(); it.hasNext(); ) {
            var r = it.next();
            if (r instanceof FurnaceRecipe fr) {
                if (fr.getInputChoice().test(input)) {
                    foundFurnace = fr;
                    break;
                }
            } else if (r instanceof BlastingRecipe br) {
                if (br.getInputChoice().test(input)) {
                    foundBlast = br;
                    break;
                }
            }
        }

        if (foundFurnace == null && foundBlast == null) return null;
        return new searchResult(foundFurnace, foundBlast);
    }

    private record searchResult(FurnaceRecipe foundFurnace, BlastingRecipe foundBlast) {
    }
}
