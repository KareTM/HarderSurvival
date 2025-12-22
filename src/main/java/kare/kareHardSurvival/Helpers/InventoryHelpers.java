package kare.kareHardSurvival.Helpers;

import io.papermc.paper.datacomponent.DataComponentTypes;
import kare.kareHardSurvival.Items.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class InventoryHelpers {
    public static ItemStack getMainhand(Player p) {
        return p.getInventory().getItem(EquipmentSlot.HAND);
    }

    public static ItemStack getOffhand(Player p) {
        return p.getInventory().getItem(EquipmentSlot.OFF_HAND);
    }

    public static void breakItem(Player p, ItemStack tool) {
        Bukkit.getPluginManager().callEvent(new PlayerItemBreakEvent(p, tool));
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
        tool.setAmount(0);
    }

    public static void damageTool(Player p, ItemStack tool) {
        tool.damage(1, p);
    }

    public static void damageTool(Player p, ItemStack tool, int damage) {
        tool.damage(damage, p);
    }

    public static ItemStack getFreshHammer(ItemStack tool) {
        if (FlagHelper.hasFlag(tool, FlagHelper.flagHammerTier1)) {
            var hammer = ItemManager.createCopperHammer();
            hammer.addEnchantments(tool.getEnchantments());
            return hammer;
        }
        else if (FlagHelper.hasFlag(tool, FlagHelper.flagHammerTier2)) {
            var hammer = ItemManager.createForgedCopperHammer();
            hammer.addEnchantments(tool.getEnchantments());
            return hammer;
        } else if (FlagHelper.hasFlag(tool, FlagHelper.flagHammerTier3)) {
            var hammer = ItemManager.createWroughtIronHammer();
            hammer.addEnchantments(tool.getEnchantments());
            return hammer;
        }

        return null;
    }

    public static ItemStack stripIFUUID(ItemStack item) {
        var attrs = item.getData(DataComponentTypes.ATTRIBUTE_MODIFIERS);
        var clone = item.clone();
        clone.editPersistentDataContainer(pdc -> pdc.remove(FlagHelper.flagIFUUID));
        if (attrs != null)
            clone.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, attrs);
        return clone;
    }
}
