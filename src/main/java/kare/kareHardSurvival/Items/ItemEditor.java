package kare.kareHardSurvival.Items;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;

public class ItemEditor {
    public static ItemStack editMeta(ItemStack item, Consumer<ItemMeta> consumer) {
        item.editMeta(consumer);
        return item;
    }

    public static ItemStack editDataComponent(ItemStack item, Consumer<ItemStack> consumer) {
        consumer.accept(item);
        return item;
    }
}
