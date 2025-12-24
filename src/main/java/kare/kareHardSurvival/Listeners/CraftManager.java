package kare.kareHardSurvival.Listeners;

import kare.kareHardSurvival.Advancements.AdvancementManager;
import kare.kareHardSurvival.Helpers.FlagHelper;
import kare.kareHardSurvival.Helpers.Granter.Granter;
import kare.kareHardSurvival.Helpers.Granter.GranterBuilder;
import kare.kareHardSurvival.Helpers.RecipeKeyList;
import kare.kareHardSurvival.Items.ItemManager;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.*;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class CraftManager implements Listener {
    static Plugin plugin;

    public CraftManager(Plugin plugin) {
        CraftManager.plugin = plugin;
    }

    static List<Granter> granters = new ArrayList<>(List.of(
            GranterBuilder.of(ItemManager.createFlintKnife(false)).grant(AdvancementManager.Knife).build(),
            GranterBuilder.of(ItemManager.createFlintAxe(false)).grant(AdvancementManager.Axe).build(),
            GranterBuilder.of(ItemManager.createCraftingTable()).grant(AdvancementManager.Crafting).build(),
            GranterBuilder.of(ItemManager.createTwine()).grant(AdvancementManager.Twine).discover(RecipeKeyList.flintPick,
                    RecipeKeyList.flintKnife, RecipeKeyList.flintAxe, RecipeKeyList.flintShovel).build(),
            GranterBuilder.of(ItemManager.createFlintPick()).grant(AdvancementManager.Pick).build(),
            GranterBuilder.of(ItemManager.createFurnace()).grant(AdvancementManager.Furnace).discover(RecipeKeyList.copperNugget).build(),
            GranterBuilder.of(ItemManager.createCopperPick()).grant(AdvancementManager.CopperPick).build(),
            GranterBuilder.of(ItemManager.createCopperPick()).grant(AdvancementManager.CopperPick).build(),
            GranterBuilder.of(ItemStack.of(Material.COPPER_INGOT)).discover(RecipeKeyList.copperPick, RecipeKeyList.copperAxe, RecipeKeyList.copperShovel,
                    RecipeKeyList.furnaceCopper, RecipeKeyList.copperHammer, RecipeKeyList.stonecutter, RecipeKeyList.copperSword).build(),
            GranterBuilder.of(ItemManager.createFurnaceCopper()).discover(RecipeKeyList.heatedCopper, RecipeKeyList.copperIngot).grant(AdvancementManager.CopperFurnace).build(),
            GranterBuilder.of(ItemManager.createCopperHammer()).grant(AdvancementManager.HammerTime).build(),
            GranterBuilder.of(ItemManager.createForge()).grant(AdvancementManager.Forge).build(),
            GranterBuilder.of(ItemStack.of(Material.STONECUTTER)).grant(AdvancementManager.Stonecutter).build(),
            GranterBuilder.of(ItemManager.createIronBurden()).discover(RecipeKeyList.ironBloom).grant(AdvancementManager.IronBurden).build(),
            GranterBuilder.of(ItemManager.createWroughtIronBlastFurnace()).discover(RecipeKeyList.copperNuggetBlast, RecipeKeyList.copperIngotBlast,
                            RecipeKeyList.heatedCopperBlast, RecipeKeyList.ironBloomBlast, RecipeKeyList.castIronBlast, RecipeKeyList.coalCoke, RecipeKeyList.charcoalCoke).
                    grant(AdvancementManager.BlastFurnace).build(),
            GranterBuilder.of(ItemStack.of(Material.NETHER_BRICKS)).discover(RecipeKeyList.ovenPadding).build(),
            GranterBuilder.of(ItemManager.createPadding()).discover(RecipeKeyList.furnaceNether, RecipeKeyList.furnaceCopperUpgrade).grant(AdvancementManager.Padding).build(),
            GranterBuilder.of(ItemManager.createBlastOven()).discover(RecipeKeyList.steelCharge, RecipeKeyList.highCarbonBurden).grant(AdvancementManager.Oven).build(),
            GranterBuilder.of(ItemManager.createCopperOven()).discover().grant(AdvancementManager.CopperFurnaceUpgrade).build(),
            GranterBuilder.of(ItemManager.createSteelCharge()).discover(RecipeKeyList.steelBillet).grant(AdvancementManager.SteelCharge).build(),
            GranterBuilder.of(ItemManager.createHighCarbonBurden()).discover(RecipeKeyList.pigIronMass).grant(AdvancementManager.CarbonBurden).build()
    ));

    static {
        for (var plank : Tag.PLANKS.getValues()) {
            var item = ItemStack.of(plank);
            granters.add(GranterBuilder.of(item).discover(RecipeKeyList.unfinishedCraftingTable).build());
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        var result = event.getInventory().getResult();
        if (result == null) return;

        for (var item : event.getInventory().getMatrix()) {
            if (item == null)
                continue;
            if (FlagHelper.hasFlag(item, FlagHelper.flagNoCraft)) {
                event.setCancelled(true);
                return;
            } else if (FlagHelper.hasFlag(item, FlagHelper.flagCraftOnlyPlugin)) {
                var recipe = (CraftingRecipe) event.getRecipe();
                if (!recipe.getKey().getNamespace().equals(plugin.namespace())) {
                    event.setCancelled(true);
                }
            }
        }

        var p = (Player) event.getWhoClicked();

        for (var rule : granters) {
            if (rule.items().contains(result)) {
                rule.grant().accept(p);
            }
        }
    }
}
