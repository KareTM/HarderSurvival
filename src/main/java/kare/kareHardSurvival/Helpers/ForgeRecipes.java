package kare.kareHardSurvival.Helpers;

import kare.kareHardSurvival.Advancements.AdvancementManager;
import kare.kareHardSurvival.Helpers.Granter.Granter;
import kare.kareHardSurvival.Helpers.Granter.GranterBuilder;
import kare.kareHardSurvival.Items.ItemManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ForgeRecipes {
    private static final Map<NamespacedKey, ForgeRecipe> recipes = new LinkedHashMap<>() {
        {
            put(RecipeKeyList.copperAxe, new ForgeRecipe(RecipeKeyList.copperAxe, Component.text("Forged Copper Axe"),
                    ItemManager.createForgedCopperAxe(), 5, ItemManager.createHeatedCopper().asQuantity(3), ItemStack.of(Material.STICK, 2)));
            put(RecipeKeyList.copperPick, new ForgeRecipe(RecipeKeyList.copperPick, Component.text("Forged Copper Pickaxe"),
                    ItemManager.createForgedCopperPick(), 5, ItemManager.createHeatedCopper().asQuantity(3), ItemStack.of(Material.STICK, 2)));
            put(RecipeKeyList.copperShovel, new ForgeRecipe(RecipeKeyList.copperShovel, Component.text("Forged Copper Shovel"),
                    ItemManager.createForgedCopperShovel(), 3, ItemManager.createHeatedCopper().asQuantity(1), ItemStack.of(Material.STICK, 2)));
            put(RecipeKeyList.copperHammer, new ForgeRecipe(RecipeKeyList.copperHammer, Component.text("Forged Copper Hammer"),
                    ItemManager.createForgedCopperHammer(), 7, ItemManager.createHeatedCopper().asQuantity(5), ItemStack.of(Material.STICK, 2)));
            put(RecipeKeyList.copperSword, new ForgeRecipe(RecipeKeyList.copperSword, Component.text("Forged Copper Sword"),
                    ItemManager.createForgedCopperSword(), 4, ItemManager.createHeatedCopper().asQuantity(2), ItemStack.of(Material.STICK, 1)));
            put(RecipeKeyList.copperHelmet, new ForgeRecipe(RecipeKeyList.copperHelmet, Component.text("Copper Helmet"),
                    ItemStack.of(Material.COPPER_HELMET), 7, ItemManager.createHeatedCopper().asQuantity(5)));
            put(RecipeKeyList.copperChestplate, new ForgeRecipe(RecipeKeyList.copperChestplate, Component.text("Copper Chestplate"),
                    ItemStack.of(Material.COPPER_CHESTPLATE), 9, ItemManager.createHeatedCopper().asQuantity(8)));
            put(RecipeKeyList.copperLeggings, new ForgeRecipe(RecipeKeyList.copperLeggings, Component.text("Copper Leggings"),
                    ItemStack.of(Material.COPPER_LEGGINGS), 8, ItemManager.createHeatedCopper().asQuantity(7)));
            put(RecipeKeyList.copperBoots, new ForgeRecipe(RecipeKeyList.copperBoots, Component.text("Copper Boots"),
                    ItemStack.of(Material.COPPER_BOOTS), 6, ItemManager.createHeatedCopper().asQuantity(4)));
            put(RecipeKeyList.wroughtIron, new ForgeRecipe(RecipeKeyList.wroughtIron, Component.text("Wrought Iron Ingot"),
                    ItemManager.createWroughtIron(), 10, ItemManager.createIronBloom().asQuantity(1)));
            put(RecipeKeyList.ironAxe, new ForgeRecipe(RecipeKeyList.ironAxe, Component.text("Wrought Iron Axe"),
                    ItemManager.createWroughtIronAxe(), 9, ItemManager.createWroughtIron().asQuantity(3), ItemStack.of(Material.STICK, 2)));
            put(RecipeKeyList.ironPick, new ForgeRecipe(RecipeKeyList.ironPick, Component.text("Wrought Iron Pickaxe"),
                    ItemManager.createWroughtIronPick(), 9, ItemManager.createWroughtIron().asQuantity(3), ItemStack.of(Material.STICK, 2)));
            put(RecipeKeyList.ironShovel, new ForgeRecipe(RecipeKeyList.ironShovel, Component.text("Wrought Iron Shovel"),
                    ItemManager.createWroughtIronShovel(), 6, ItemManager.createWroughtIron().asQuantity(1), ItemStack.of(Material.STICK, 2)));
            put(RecipeKeyList.ironHammer, new ForgeRecipe(RecipeKeyList.ironHammer, Component.text("Wrought Iron Hammer"),
                    ItemManager.createWroughtIronHammer(), 15, ItemManager.createWroughtIron().asQuantity(5), ItemStack.of(Material.STICK, 2)));
            put(RecipeKeyList.ironSword, new ForgeRecipe(RecipeKeyList.ironSword, Component.text("Wrought Iron Sword"),
                    ItemManager.createWroughtIronSword(), 7, ItemManager.createWroughtIron().asQuantity(2), ItemStack.of(Material.STICK, 1)));
            put(RecipeKeyList.ironHelmet, new ForgeRecipe(RecipeKeyList.ironHelmet, Component.text("Wrought Iron Helmet"),
                    ItemStack.of(Material.IRON_HELMET), 16, ItemManager.createWroughtIron().asQuantity(5)));
            put(RecipeKeyList.ironChestplate, new ForgeRecipe(RecipeKeyList.ironChestplate, Component.text("Wrought Iron Chestplate"),
                    ItemStack.of(Material.IRON_CHESTPLATE), 20, ItemManager.createWroughtIron().asQuantity(8)));
            put(RecipeKeyList.ironLeggings, new ForgeRecipe(RecipeKeyList.ironLeggings, Component.text("Wrought Iron Leggings"),
                    ItemStack.of(Material.IRON_LEGGINGS), 18, ItemManager.createWroughtIron().asQuantity(7)));
            put(RecipeKeyList.ironBoots, new ForgeRecipe(RecipeKeyList.ironBoots, Component.text("Wrought Iron Boots"),
                    ItemStack.of(Material.IRON_BOOTS), 14, ItemManager.createWroughtIron().asQuantity(4)));
            put(RecipeKeyList.wroughtIronRich, new ForgeRecipe(RecipeKeyList.wroughtIronRich, Component.text("Wrought Iron Ingot"),
                    ItemManager.createWroughtIron().asQuantity(4), 20, ItemManager.createRichBloom().asQuantity(1)));
            put(RecipeKeyList.steelIngot, new ForgeRecipe(RecipeKeyList.steelIngot, Component.text("Steel Ingot"),
                    ItemManager.createSteel(), 22, ItemManager.createSteelBillet().asQuantity(1)));
            put(RecipeKeyList.steelAxe, new ForgeRecipe(RecipeKeyList.steelAxe, Component.text("Steel Axe"),
                    ItemManager.createSteelAxe(), 18, ItemManager.createSteel().asQuantity(3), ItemStack.of(Material.STICK, 2)));
            put(RecipeKeyList.steelPick, new ForgeRecipe(RecipeKeyList.steelPick, Component.text("Steel Pickaxe"),
                    ItemManager.createSteelPick(), 18, ItemManager.createSteel().asQuantity(3), ItemStack.of(Material.STICK, 2)));
            put(RecipeKeyList.steelShovel, new ForgeRecipe(RecipeKeyList.steelShovel, Component.text("Steel Shovel"),
                    ItemManager.createSteelShovel(), 12, ItemManager.createSteel().asQuantity(1), ItemStack.of(Material.STICK, 2)));
            put(RecipeKeyList.steelHammer, new ForgeRecipe(RecipeKeyList.steelHammer, Component.text("Steel Hammer"),
                    ItemManager.createSteelHammer(), 25, ItemManager.createSteel().asQuantity(5), ItemStack.of(Material.STICK, 2)));
            put(RecipeKeyList.steelSword, new ForgeRecipe(RecipeKeyList.steelSword, Component.text("Steel Sword"),
                    ItemManager.createSteelSword(), 14, ItemManager.createSteel().asQuantity(2), ItemStack.of(Material.STICK, 1)));
            put(RecipeKeyList.steelHelmet, new ForgeRecipe(RecipeKeyList.steelHelmet, Component.text("Steel Helmet"),
                    ItemManager.createSteelHelmet(), 28, ItemManager.createSteel().asQuantity(5)));
            put(RecipeKeyList.steelChestplate, new ForgeRecipe(RecipeKeyList.steelChestplate, Component.text("Steel Chestplate"),
                    ItemManager.createSteelChestplate(), 32, ItemManager.createSteel().asQuantity(8)));
            put(RecipeKeyList.steelLeggings, new ForgeRecipe(RecipeKeyList.steelLeggings, Component.text("Steel Leggings"),
                    ItemManager.createSteelLeggings(), 30, ItemManager.createSteel().asQuantity(7)));
            put(RecipeKeyList.steelBoots, new ForgeRecipe(RecipeKeyList.steelBoots, Component.text("Steel Boots"),
                    ItemManager.createSteelBoots(), 26, ItemManager.createSteel().asQuantity(4)));
        }
    };

    public static List<Granter> rules = List.of(
            GranterBuilder.of(ItemManager.createForgedCopperPick()).grant(AdvancementManager.ForgedPickaxe).build(),
            GranterBuilder.of(ItemManager.createForgedCopperHammer()).grant(AdvancementManager.ForgedHammer).build(),
            GranterBuilder.of(ItemStack.of(Material.COPPER_HELMET))
                    .addItems(List.of(ItemStack.of(Material.COPPER_CHESTPLATE), ItemStack.of(Material.COPPER_LEGGINGS),
                            ItemStack.of(Material.COPPER_BOOTS))).grant(AdvancementManager.SuitedUp).build(),
            GranterBuilder.of(ItemManager.createWroughtIron()).discover(RecipeKeyList.furnaceIron).grant(AdvancementManager.WroughtIron).build(),
            GranterBuilder.of(ItemManager.createWroughtIronHammer()).grant(AdvancementManager.IronHammer).build(),
            GranterBuilder.of(ItemManager.createWroughtIronPick()).grant(AdvancementManager.IronPick).build(),
            GranterBuilder.of(ItemStack.of(Material.IRON_HELMET))
                    .addItems(List.of(ItemStack.of(Material.IRON_CHESTPLATE), ItemStack.of(Material.IRON_LEGGINGS),
                            ItemStack.of(Material.IRON_BOOTS))).grant(AdvancementManager.IronArmor).build(),
            GranterBuilder.of(ItemManager.createSteel()).grant(AdvancementManager.SteelIngot).build(),
            GranterBuilder.of(ItemManager.createSteelHammer()).grant(AdvancementManager.SteelHammer).build(),
            GranterBuilder.of(ItemManager.createSteelPick()).grant(AdvancementManager.SteelPick).build(),
            GranterBuilder.of(ItemManager.createSteelHelmet()).addItems(List.of(ItemManager.createSteelChestplate(),
                    ItemManager.createSteelLeggings(), ItemManager.createSteelBoots())).grant(AdvancementManager.SteelArmor).build()
    );

    public static int getReduction(ItemStack item) {
        if (item == null)
            return 0;

        int tier = 0;

        var eff = item.getEnchantmentLevel(Enchantment.EFFICIENCY);
        var eff_reduction = switch (eff) {
            case 1 -> 5;
            case 2 -> 10;
            case 3 -> 13;
            case 4 -> 16;
            case 5 -> 20;
            default -> 0;
        };

        if (FlagHelper.hasFlag(item, FlagHelper.flagHammerTier1)) tier = 1;
        else if (FlagHelper.hasFlag(item, FlagHelper.flagHammerTier2)) tier = 2;
        else if (FlagHelper.hasFlag(item, FlagHelper.flagHammerTier3)) tier = 3;
        else if (FlagHelper.hasFlag(item, FlagHelper.flagHammerTier4)) tier = 4;

        return eff_reduction + switch (tier) {
            case 1 -> 0;
            case 2 -> 25;
            case 3 -> 50;
            case 4 -> 75;
            default -> -1;
        };
    }


    public static final List<NamespacedKey> recipeKeys = List.of(recipes.keySet().toArray(new NamespacedKey[0]));

    public static NamespacedKey getNextRecipeKey(NamespacedKey current) {
        if (current == null || !recipes.containsKey(current))
            return recipeKeys.getFirst();

        int idx = recipeKeys.indexOf(current);
        if (idx == -1 || idx + 1 >= recipes.size()) return recipeKeys.getFirst();
        return recipeKeys.get(idx + 1);
    }

    public static ForgeRecipe getRecipe(NamespacedKey key) {
        if (key == null || !recipes.containsKey(key))
            return null;
        return recipes.get(key);
    }

    public static List<ForgeRecipe> getAllRecipes() {
        return new ArrayList<>(recipes.values());
    }

    public static void recipeLore(ItemMeta meta, ForgeRecipe recipe) {
        meta.lore(new ArrayList<>() {
            {
                add(Component.text("Cost:").decoration(TextDecoration.ITALIC, false));
                for (ItemStack req : recipe.itemsRequirement) {
                    TextComponent text = Component.text("  " + req.getAmount() + "x ").append(req.displayName()).decoration(TextDecoration.ITALIC, false);
                    add(text);
                }
                add(Component.text(""));
                add(Component.text("Needs ").decoration(TextDecoration.ITALIC, false).
                        append(Component.text(recipe.actionCount).color(NamedTextColor.RED)).
                        append(Component.text(" actions").decoration(TextDecoration.ITALIC, false)));
                add(Component.text(""));
                add(Component.text("Click to cycle").decoration(TextDecoration.ITALIC, false));
            }
        });
    }

    public static void recipeLore(ItemMeta meta, ForgeRecipe recipe, int reduction) {
        int usedCount = Math.max((int) Math.round((100.0 - reduction) * recipe.actionCount / 100.0), 1);

        meta.lore(new ArrayList<>() {
            {
                add(Component.text("Cost:").decoration(TextDecoration.ITALIC, false));
                for (ItemStack req : recipe.itemsRequirement) {
                    TextComponent text = Component.text("  " + req.getAmount() + "x ").append(req.displayName()).decoration(TextDecoration.ITALIC, false);
                    add(text);
                }
                add(Component.text(""));
                add(Component.text("Needs ").decoration(TextDecoration.ITALIC, false).
                        append(Component.text(usedCount).color(NamedTextColor.RED)).
                        append(Component.text(" actions").decoration(TextDecoration.ITALIC, false)));
                add(Component.text(""));
                add(Component.text("Click to cycle").decoration(TextDecoration.ITALIC, false));
            }
        });
    }

    public static class ForgeRecipe {
        public NamespacedKey key;
        public Component name;
        public ItemStack output;
        public List<ItemStack> itemsRequirement;
        public int actionCount;

        public ForgeRecipe(NamespacedKey key, Component name, ItemStack output, int actionCount, ItemStack... itemsRequirement) {
            this.key = key;
            this.name = name;
            this.output = output;
            this.actionCount = actionCount;
            this.itemsRequirement = List.of(itemsRequirement);
        }
    }
}
