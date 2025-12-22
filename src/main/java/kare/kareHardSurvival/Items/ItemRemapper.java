package kare.kareHardSurvival.Items;

import io.papermc.paper.datacomponent.DataComponentTypes;
import kare.kareHardSurvival.Helpers.InventoryHelpers;
import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.Random;

import static org.bukkit.entity.Villager.Profession.TOOLSMITH;
import static org.bukkit.entity.Villager.Profession.WEAPONSMITH;

public class ItemRemapper {

    private static final Random random = new Random();

    public static ItemStack remap(
            ItemStack original,
            @Nullable Villager.Profession profession,
            ItemSource source
    ) {
        if (original == null) return null;

        Material type = original.getType();

        // Exclude hoes entirely
        if (type.name().endsWith("_HOE")) return null;

        ItemStack replacement = null;

        /* =======================
           TOOLS / WEAPONS
        ======================= */

        switch (type) {

            // ---- STONE → FLINT ----
            case STONE_AXE -> replacement = resolveAxe(
                    ItemManager.createFlintAxe(true),
                    null,
                    profession,
                    source
            );
            case STONE_PICKAXE -> replacement = ItemManager.createFlintPick();
            case STONE_SHOVEL -> replacement = ItemManager.createFlintShovel();
            case STONE_SWORD -> replacement = ItemManager.createFlintKnife(true);

            // ---- IRON → CRAFTED COPPER ----
            case IRON_PICKAXE -> replacement = ItemManager.createCopperPick();
            case IRON_SHOVEL -> replacement = ItemManager.createCopperShovel();
            case IRON_SWORD -> replacement = ItemManager.createCopperSword();

            case IRON_AXE -> replacement = resolveAxe(
                    ItemManager.createCopperAxe(),
                    ItemManager.createCopperHammer(),
                    profession,
                    source
            );

            // ---- DIAMOND → FORGED COPPER ----
            case DIAMOND_PICKAXE -> replacement = ItemManager.createForgedCopperPick();
            case DIAMOND_SHOVEL -> replacement = ItemManager.createForgedCopperShovel();
            case DIAMOND_SWORD -> replacement = ItemManager.createForgedCopperSword();

            case DIAMOND_AXE -> replacement = resolveAxe(
                    ItemManager.createForgedCopperAxe(),
                    ItemManager.createForgedCopperHammer(),
                    profession,
                    source
            );
        }

        /* =======================
           ARMOR
        ======================= */

        if (replacement == null) {
            replacement = switch (type) {

                // IRON → COPPER
                case IRON_HELMET -> new ItemStack(Material.COPPER_HELMET);
                case IRON_CHESTPLATE -> new ItemStack(Material.COPPER_CHESTPLATE);
                case IRON_LEGGINGS -> new ItemStack(Material.COPPER_LEGGINGS);
                case IRON_BOOTS -> new ItemStack(Material.COPPER_BOOTS);

                // DIAMOND → IRON
                case DIAMOND_HELMET -> new ItemStack(Material.IRON_HELMET);
                case DIAMOND_CHESTPLATE -> new ItemStack(Material.IRON_CHESTPLATE);
                case DIAMOND_LEGGINGS -> new ItemStack(Material.IRON_LEGGINGS);
                case DIAMOND_BOOTS -> new ItemStack(Material.IRON_BOOTS);

                default -> null;
            };
        }

        if (replacement == null) return null;

        InventoryHelpers.copyEnchantments(original, replacement);
        handleDamage(original, replacement);
        return replacement;
    }


    /* =======================
       AXE / HAMMER RESOLUTION
    ======================= */
    private static ItemStack resolveAxe(
            ItemStack axe,
            @Nullable ItemStack hammer,
            @Nullable Villager.Profession profession,
            ItemSource source
    ) {
        // Villagers: deterministic per profession
        if (source == ItemSource.VILLAGER && profession != null) {
            if (profession == TOOLSMITH)
                return axe;
            else if (profession == WEAPONSMITH)
                return hammer != null ? hammer : axe;
        }

        // Loot: 50/50
        if (hammer == null) return axe;
        return random.nextBoolean() ? axe : hammer;
    }


    @SuppressWarnings("DataFlowIssue")
    private static void handleDamage(ItemStack from, ItemStack to) {
        if (!from.hasData(DataComponentTypes.MAX_DAMAGE) || !to.hasData(DataComponentTypes.MAX_DAMAGE)) return;
        var damagePercent = 1.0 - (from.getData(DataComponentTypes.DAMAGE) * 1.0 / from.getData(DataComponentTypes.MAX_DAMAGE));

        to.setData(DataComponentTypes.DAMAGE, (int) Math.round((1 - damagePercent) * to.getData(DataComponentTypes.MAX_DAMAGE)));
    }


    public enum ItemSource {
        VILLAGER,
        LOOT
    }
}


