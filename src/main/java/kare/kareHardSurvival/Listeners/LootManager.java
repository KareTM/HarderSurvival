package kare.kareHardSurvival.Listeners;

import com.destroystokyo.paper.loottable.LootableInventoryReplenishEvent;
import kare.kareHardSurvival.Items.ItemRemapper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseLootEvent;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class LootManager implements Listener {
    static Plugin plugin;
    public LootManager(Plugin plugin) {
        LootManager.plugin = plugin;
    }

    @EventHandler
    public void onLootGenerate(LootGenerateEvent e) {
        List<ItemStack> loot = e.getLoot();

         for (int i = 0; i < loot.size(); i++) {
            ItemStack original = loot.get(i);

            ItemStack replacement = ItemRemapper.remap(
                    original,
                    null,
                    ItemRemapper.ItemSource.LOOT
            );

            if (replacement != null) {
                e.getLoot().set(i, replacement);
            }
        }
    }

    @EventHandler
    public void onLootTrial(BlockDispenseLootEvent e) {
        List<ItemStack> loot = e.getDispensedLoot();

        for (int i = 0; i < loot.size(); i++) {
            ItemStack original = loot.get(i);

            ItemStack replacement = ItemRemapper.remap(
                    original,
                    null,
                    ItemRemapper.ItemSource.LOOT
            );

            if (replacement != null) {
                e.getDispensedLoot().set(i, replacement);
            }
        }
    }
}
