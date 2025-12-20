package kare.kareHardSurvival.Listeners;

import kare.kareHardSurvival.Advancements.AdvancementManager;
import kare.kareHardSurvival.Helpers.Granter.Granter;
import kare.kareHardSurvival.Helpers.Granter.GranterBuilder;
import kare.kareHardSurvival.Helpers.RecipeKeyList;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;


public class ItemPickupManager implements Listener {
    static Plugin plugin;

    public ItemPickupManager(Plugin plugin) {
        ItemPickupManager.plugin = plugin;
    }

    List<Granter> rules = List.of(
            GranterBuilder.of(ItemStack.of(Material.TERRACOTTA)).discover(RecipeKeyList.furnace)
                    .grant(AdvancementManager.Terracotta).condition(p -> AdvancementManager.Terracotta.getProgression(p) != 0).build(),
            GranterBuilder.of(ItemStack.of(Material.COBBLESTONE)).discover(RecipeKeyList.furnaceCore).build(),
            GranterBuilder.of(ItemStack.of(Material.RAW_IRON)).discover(RecipeKeyList.ironBurden).build()
    );

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player p)) return;

        var picked = event.getItem().getItemStack().getType();

        for (var rule : rules) {
            for (var item : rule.items()) {
                if (item.getType().equals(picked) && rule.condition().test(p))
                    rule.grant().accept(p);
            }
        }
    }
}
