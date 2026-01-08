package kare.kareHardSurvival.Helpers;

import com.jeff_media.customblockdata.CustomBlockData;
import kare.kareHardSurvival.KareHardSurvival;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class FlagHelper {
    private static final Plugin plugin = JavaPlugin.getPlugin(KareHardSurvival.class);

    public static final NamespacedKey flagSticks = new NamespacedKey(plugin, "stick_cutter");
    public static final NamespacedKey flagFlint = new NamespacedKey(plugin, "flint_extractor");
    public static final NamespacedKey flagAxe = new NamespacedKey(plugin, "log_breaker");
    public static final NamespacedKey flagGrass = new NamespacedKey(plugin, "grass_cutter");
    public static final NamespacedKey flagCarvingTool = new NamespacedKey(plugin, "carving_tool");

    public static final NamespacedKey flagRequiresCarving = new NamespacedKey(plugin, "requires_carving");
    public static final NamespacedKey flagForge = new NamespacedKey(plugin, "forge");
    public static final NamespacedKey flagNoPlace = new NamespacedKey(plugin, "no_place");
    public static final NamespacedKey flagNoBurn = new NamespacedKey(plugin, "no_burn");
    public static final NamespacedKey flagNoCraft = new NamespacedKey(plugin, "no_craft");
    public static final NamespacedKey flagCraftOnlyPlugin = new NamespacedKey(plugin, "only_plugin");
    public static final NamespacedKey flagNoSmelt = new NamespacedKey(plugin, "no_smelt");
    public static final NamespacedKey flagSmeltOnlyPlugin = new NamespacedKey(plugin, "only_plugin");

    public static final NamespacedKey flagPickTier1 = new NamespacedKey(plugin, "pick_tier_1");
    public static final NamespacedKey flagPickTier2 = new NamespacedKey(plugin, "pick_tier_2");
    public static final NamespacedKey flagPickTier3 = new NamespacedKey(plugin, "pick_tier_3");
    public static final NamespacedKey flagPickTier4 = new NamespacedKey(plugin, "pick_tier_4");
    public static final NamespacedKey flagPickTier5 = new NamespacedKey(plugin, "pick_tier_5");

    public static final NamespacedKey flagFurnace = new NamespacedKey(plugin, "furnace");
    public static final NamespacedKey flagFurnaceTier1 = new NamespacedKey(plugin, "furnace_tier_1");
    public static final NamespacedKey flagFurnaceTier2 = new NamespacedKey(plugin, "furnace_tier_2");
    public static final NamespacedKey flagFurnaceTier3 = new NamespacedKey(plugin, "furnace_tier_3");
    public static final NamespacedKey flagFurnaceTier3Alt = new NamespacedKey(plugin, "furnace_tier_3_alt");
    public static final NamespacedKey flagFurnaceTier4 = new NamespacedKey(plugin, "furnace_tier_4");

    public static final NamespacedKey flagHammer = new NamespacedKey(plugin, "hammer");
    public static final NamespacedKey flagHammerTier1 = new NamespacedKey(plugin, "hammer_tier_1");
    public static final NamespacedKey flagHammerTier2 = new NamespacedKey(plugin, "hammer_tier_2");
    public static final NamespacedKey flagHammerTier3 = new NamespacedKey(plugin, "hammer_tier_3");
    public static final NamespacedKey flagHammerTier4 = new NamespacedKey(plugin, "hammer_tier_4");

    public static final NamespacedKey flagRequiresFurnaceTier2 = new NamespacedKey(plugin, "needs_furnace_2");
    public static final NamespacedKey flagRequiresFurnaceTier4 = new NamespacedKey(plugin, "needs_furnace_4");

    public static final NamespacedKey flagProspect = new NamespacedKey(plugin, "prospect");
    public static final NamespacedKey flagProspectCooldown = new NamespacedKey(plugin, "prospect_cooldown");
    public static final NamespacedKey flagProspectBasic = new NamespacedKey(plugin, "prospect_basic");

    public static final NamespacedKey flagIFUUID = new NamespacedKey(plugin, "if-uuid");

    public static boolean hasFlag(ItemStack item, NamespacedKey key) {
        return item.getPersistentDataContainer().has(key);
    }

    public static void setFlag(ItemStack item, NamespacedKey key, Boolean value) {
        item.editPersistentDataContainer(pdc -> pdc.set(key, PersistentDataType.BOOLEAN, value));
    }

    public static boolean hasIntValue(ItemStack item, NamespacedKey key) {
        return item.getPersistentDataContainer().has(key) || item.getPersistentDataContainer().get(key, PersistentDataType.INTEGER) != null;
    }

    public static int getIntValue(ItemStack item, NamespacedKey key) {
        return item.getPersistentDataContainer().getOrDefault(key, PersistentDataType.INTEGER, 0);
    }

    public static void setIntValue(ItemStack item, NamespacedKey key, int value) {
        item.editPersistentDataContainer(pdc -> pdc.set(key, PersistentDataType.INTEGER, value));
    }

    public static boolean hasFlag(CustomBlockData data, NamespacedKey key) {
        return data.has(key);
    }

    public static void setFlag(CustomBlockData bd, NamespacedKey key, Boolean value) {
        bd.set(key, PersistentDataType.BOOLEAN, value);
    }

    public static void removeFlag(CustomBlockData bd, NamespacedKey key) {
        bd.remove(key);
    }

    public static void removeFlag(ItemStack item, NamespacedKey key) {
        item.editPersistentDataContainer(pdc -> pdc.remove(key));
    }

    public static boolean hasByteValue(CustomBlockData bd, NamespacedKey key) {
        return bd.has(key)
                || bd.get(key, PersistentDataType.BYTE) != null;
    }

    public static byte getByteValue(CustomBlockData bd, NamespacedKey key) {
        return bd.getOrDefault(key, PersistentDataType.BYTE, (byte) 0);
    }

    public static void setByteValue(CustomBlockData bd, NamespacedKey key, int value) {
        bd.set(key, PersistentDataType.BYTE, (byte) value);
    }

    public static boolean hasIntValue(CustomBlockData bd, NamespacedKey key) {
        return bd.has(key) || bd.get(key, PersistentDataType.INTEGER) != null;
    }

    public static int getIntValue(CustomBlockData bd, NamespacedKey key) {
        return bd.getOrDefault(key, PersistentDataType.INTEGER, 0);
    }

    public static void setIntValue(CustomBlockData bd, NamespacedKey key, int value) {
        bd.set(key, PersistentDataType.INTEGER, value);
    }
}
