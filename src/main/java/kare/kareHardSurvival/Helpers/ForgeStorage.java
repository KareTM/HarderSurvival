package kare.kareHardSurvival.Helpers;

import com.jeff_media.customblockdata.CustomBlockData;
import kare.kareHardSurvival.GUI.ForgeGUI;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class ForgeStorage {
    private static final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("KareHardSurvival");
    public static final NamespacedKey hammer = new NamespacedKey(plugin, "hammer");
    public static final NamespacedKey matFirst = new NamespacedKey(plugin, "mat_1");
    public static final NamespacedKey matSecond = new NamespacedKey(plugin, "mat_2");
    public static final NamespacedKey recipe = new NamespacedKey(plugin, "selected_recipe");


    public static ForgeGUI.ForgeData load(Block block, Plugin plugin) {
        CustomBlockData cbd = new CustomBlockData(block, plugin);

        ForgeGUI.ForgeData data = new ForgeGUI.ForgeData();

        if (cbd.has(hammer))
            data.hammer = ItemStack.deserializeBytes(Objects.requireNonNull(cbd.get(hammer, PersistentDataType.BYTE_ARRAY)));
        if (cbd.has(matFirst))
            data.material1 = ItemStack.deserializeBytes(Objects.requireNonNull(cbd.get(matFirst, PersistentDataType.BYTE_ARRAY)));
        if (cbd.has(matSecond))
            data.material2 = ItemStack.deserializeBytes(Objects.requireNonNull(cbd.get(matSecond, PersistentDataType.BYTE_ARRAY)));
        if (cbd.has(recipe))
            data.selectedRecipe = new NamespacedKey(plugin, Objects.requireNonNull(cbd.get(recipe, PersistentDataType.STRING)));

        return data;
    }

    public static void save(Block block, ForgeGUI.ForgeData data, Plugin plugin) {
        CustomBlockData cbd = new CustomBlockData(block, plugin);

        if (data.hammer != null && !data.hammer.isEmpty())
            cbd.set(hammer, PersistentDataType.BYTE_ARRAY, data.hammer.serializeAsBytes());
        else
            cbd.remove(hammer);
        if (data.material1 != null && !data.material1.isEmpty())
            cbd.set(matFirst, PersistentDataType.BYTE_ARRAY, data.material1.serializeAsBytes());
        else
            cbd.remove(matFirst);
        if (data.material2 != null && !data.material2.isEmpty())
            cbd.set(matSecond, PersistentDataType.BYTE_ARRAY, data.material2.serializeAsBytes());
        else
            cbd.remove(matSecond);
        if (data.selectedRecipe != null)
            cbd.set(recipe, PersistentDataType.STRING, data.selectedRecipe.getKey());
        else
            cbd.remove(recipe);
    }
}

