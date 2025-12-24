package kare.kareHardSurvival.Helpers;

import kare.kareHardSurvival.Items.ItemManager;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class FuelHelper {
    static Plugin plugin;

    public static void setPlugin(Plugin p) {
        plugin = p;
    }

    public static final List<Material> fuelTier1 = new ArrayList<>() {
        {
            add(Material.COAL_BLOCK);
            add(Material.COAL);
            add(Material.CHARCOAL);
            addAll(Tag.LOGS.getValues());
        }
    };

    public static final int TIER1_SPEED_DIVISOR = 3;

    public static final List<Material> fuelTier2 = new ArrayList<>() {
        {
            add(Material.COAL_BLOCK);
            add(Material.COAL);
            add(Material.CHARCOAL);
            add(Material.BLAZE_ROD);
        }
    };

    public static final int TIER2_SPEED_DIVISOR = 2;

    public static final List<Material> fuelTier3 = new ArrayList<>() {
        {
            add(Material.COAL_BLOCK);
            add(Material.COAL);
            add(Material.BLAZE_ROD);
            add(Material.LAVA_BUCKET);
        }
    };

    public static final int TIER3_SPEED_DIVISOR = 2; // Blast has natural 2x, so this brings back to 1x
    public static final double TIER3_FUEL_MULT = 1.5; // Blast has natural 2x, so this brings back to 1x
    public static final double TIER3_ALT_SPEED_MULT = 1.5; // Side upgrade to Copper Furnace, to make a furnace faster than vanilla
    public static final int TIER3_ALT_FUEL_DIV = 2;  // 1.5 speed -> 12 items per coal, divide by 2 to bring in line with Tier 3

    public static final List<ItemStack> fuelTier4 = new ArrayList<>() {
        {
            add(ItemManager.createCoalCoke());
            add(ItemStack.of(Material.BLAZE_ROD));
            add(ItemStack.of(Material.LAVA_BUCKET));
        }
    };

    public static final int TIER4_SPEED_DIVISOR = 1;
}
