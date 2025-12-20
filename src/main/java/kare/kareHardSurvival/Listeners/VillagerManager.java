package kare.kareHardSurvival.Listeners;

import io.papermc.paper.datacomponent.DataComponentTypes;
import kare.kareHardSurvival.Items.ItemManager;
import kare.kareHardSurvival.Items.ItemRemapper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class VillagerManager implements Listener {
    static Plugin plugin;


    public VillagerManager(Plugin plugin) {
        VillagerManager.plugin = plugin;
    }

    List<Villager> villagersScheduled = new ArrayList<>();

    @EventHandler
    public void onAcquireTrades(VillagerAcquireTradeEvent event) {
        var villager = event.getEntityType() == EntityType.VILLAGER ? (Villager) event.getEntity() : null;
        if (villager == null) return;

        // Only Tools or Armorer
        if (villager.getProfession() != Villager.Profession.TOOLSMITH
                && villager.getProfession() != Villager.Profession.ARMORER
                && villager.getProfession() != Villager.Profession.WEAPONSMITH)
            return;

        if (villagersScheduled.contains(villager)) return;
        else villagersScheduled.add(villager);

        // Run AFTER vanilla creates the real trades
        Bukkit.getScheduler().runTask(plugin, () -> {
            var trades = villager.getRecipes();
            if (trades.isEmpty()) return;

            for (int i = 0; i < trades.size(); i++) {
                MerchantRecipe recipe = trades.get(i);

                ItemStack replacement = ItemRemapper.remap(
                        recipe.getResult(),
                        villager.getProfession(),
                        ItemRemapper.ItemSource.VILLAGER
                );

                if (replacement == null) continue;

                MerchantRecipe newTrade = new MerchantRecipe(
                        replacement,
                        recipe.getMaxUses()
                );

                for (ItemStack ingredient : recipe.getIngredients()) {
                    newTrade.addIngredient(ingredient.clone());
                }

                newTrade.setVillagerExperience(recipe.getVillagerExperience());

                villager.setRecipe(i, newTrade);
            }

            villagersScheduled.remove(villager);
        });
    }
}
