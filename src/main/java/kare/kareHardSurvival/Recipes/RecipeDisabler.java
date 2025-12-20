package kare.kareHardSurvival.Recipes;

import org.bukkit.NamespacedKey;
import org.bukkit.Server;

public class RecipeDisabler {
    static Server s;

    public RecipeDisabler(Server s) {
        RecipeDisabler.s = s;
    }

    public void disableRecipes() {
        s.removeRecipe(new NamespacedKey("minecraft", "stick"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "stick_from_bamboo_item"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "crafting_table"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "furnace"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "campfire"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "stonecutter"), true);

        s.removeRecipe(new NamespacedKey("minecraft", "oak_planks"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "birch_planks"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "cherry_planks"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "dark_oak_planks"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "jungle_planks"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "mangrove_planks"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "pale_oak_planks"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "spruce_planks"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "acacia_planks"), true);

        s.removeRecipe(new NamespacedKey("minecraft", "wooden_sword"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "wooden_hoe"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "wooden_pickaxe"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "wooden_shovel"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "wooden_axe"), true);

        s.removeRecipe(new NamespacedKey("minecraft", "stone_sword"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "stone_pickaxe"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "stone_shovel"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "stone_axe"), true);

        s.removeRecipe(new NamespacedKey("minecraft", "copper_sword"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "copper_pickaxe"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "copper_shovel"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "copper_axe"), true);

        s.removeRecipe(new NamespacedKey("minecraft", "copper_ingot_from_smelting_raw_copper"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "copper_ingot_from_smelting_copper_ore"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "copper_ingot_from_smelting_deepslate_copper_ore"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "copper_ingot_from_blasting_raw_copper"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "copper_ingot_from_blasting_copper_ore"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "copper_ingot_from_blasting_deepslate_copper_ore"), true);

        s.removeRecipe(new NamespacedKey("minecraft", "iron_sword"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "iron_pickaxe"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "iron_shovel"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "iron_axe"), true);

        s.removeRecipe(new NamespacedKey("minecraft", "iron_ingot_from_smelting_raw_iron"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "iron_ingot_from_smelting_iron_ore"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "iron_ingot_from_smelting_deepslate_iron_ore"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "iron_ingot_from_blasting_raw_iron"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "iron_ingot_from_blasting_iron_ore"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "iron_ingot_from_blasting_deepslate_iron_ore"), true);

        s.removeRecipe(new NamespacedKey("minecraft", "diamond_sword"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "diamond_pickaxe"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "diamond_shovel"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "diamond_axe"), true);

        s.removeRecipe(new NamespacedKey("minecraft", "copper_helmet"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "copper_chestplate"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "copper_leggings"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "copper_boots"), true);

        s.removeRecipe(new NamespacedKey("minecraft", "iron_helmet"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "iron_chestplate"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "iron_leggings"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "iron_boots"), true);

        s.removeRecipe(new NamespacedKey("minecraft", "diamond_helmet"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "diamond_chestplate"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "diamond_leggings"), true);
        s.removeRecipe(new NamespacedKey("minecraft", "diamond_boots"), true);
        
        s.removeRecipe(new NamespacedKey("minecraft", "repair_item"), true);
    }
}
