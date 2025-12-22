package kare.kareHardSurvival.Helpers;

import org.bukkit.Material;
import org.bukkit.Tag;
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

    public static final List<Material> fuelTier4 = new ArrayList<>() {
        {
            add(Material.COAL_BLOCK);
            add(Material.BLAZE_ROD);
            add(Material.LAVA_BUCKET);
            // New Tier LAVA + 8 COAL BLOCKS
        }
    };

    public static final int fuelTier4Multiplier = 2;
}
