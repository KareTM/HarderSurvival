package kare.kareHardSurvival.Items;

import io.papermc.paper.datacomponent.DataComponentTypes;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class ItemRegistry {
    private static final Map<Component, Supplier<ItemStack>> FACTORIES = new HashMap<>();

    private ItemRegistry() {
    }

    public static void initialize() {
        for (Method method : ItemManager.class.getDeclaredMethods()) {

            if (!Modifier.isStatic(method.getModifiers())) continue;
            if (!method.getName().startsWith("create")) continue;
            if (!ItemStack.class.equals(method.getReturnType())) continue;
            if (method.getParameterCount() != 0) continue;

            try {
                ItemStack sample = (ItemStack) method.invoke(null);
                if (!sample.hasData(DataComponentTypes.ITEM_NAME)) continue;

                Component name = sample.getData(DataComponentTypes.ITEM_NAME);

                FACTORIES.put(name, () -> {
                    try {
                        return (ItemStack) method.invoke(null);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

            } catch (Exception ignored) {
            }
        }
    }

    public static ItemStack tryUpgrade(ItemStack item) {
        if (item == null) return null;
        if (!item.hasData(DataComponentTypes.ITEM_NAME)) return item;

        Component name = item.getData(DataComponentTypes.ITEM_NAME);
        Supplier<ItemStack> factory = FACTORIES.get(name);
        if (factory == null) return item;

        ItemStack upgraded = factory.get();

        upgraded.setData(DataComponentTypes.DAMAGE,
                item.getDataOrDefault(DataComponentTypes.DAMAGE, 0));

        if (item.hasData(DataComponentTypes.TRIM)) {
            upgraded.setData(DataComponentTypes.TRIM, item.getData(DataComponentTypes.TRIM));
        }

        if (item.hasData(DataComponentTypes.CUSTOM_NAME)) {
            upgraded.setData(DataComponentTypes.CUSTOM_NAME, item.getData(DataComponentTypes.CUSTOM_NAME));
        }

        if (item.getAmount() > 1)
            upgraded.setAmount(item.getAmount());

        upgraded.addEnchantments(item.getEnchantments());

        return upgraded;
    }
}
