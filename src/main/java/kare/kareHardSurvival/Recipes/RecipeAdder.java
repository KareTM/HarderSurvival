package kare.kareHardSurvival.Recipes;

import kare.kareHardSurvival.Helpers.RecipeKeyList;
import kare.kareHardSurvival.Items.ItemManager;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.Tag;
import org.bukkit.inventory.*;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdder {
    Server s;
    Plugin plugin;

    public RecipeAdder(Server s, Plugin p) {
        this.s = s;
        this.plugin = p;
    }

    public void addAllRecipes() {
        s.addRecipe(flintAxeSimple());
        s.addRecipe(flintKnifeSimple());
        s.addRecipe(unfinishedCraftingTable());
        s.addRecipe(twine());
        s.addRecipe(flintKnife());
        s.addRecipe(flintAxe());
        s.addRecipe(flintPick());
        s.addRecipe(flintShovel());
        s.addRecipe(campfire());
        s.addRecipe(terracotta());
        s.addRecipe(furnace());
        s.addRecipe(copperNugget());
        s.addRecipe(copperIngot());
        s.addRecipe(copperNuggetBlast());
        s.addRecipe(copperIngotBlast());
        s.addRecipe(copperPick());
        s.addRecipe(copperAxe());
        s.addRecipe(copperShovel());
        s.addRecipe(furnaceCore());
        s.addRecipe(furnaceCopper());
        s.addRecipe(heatedCopper());
        s.addRecipe(heatedCopperBlast());
        s.addRecipe(copperHammer());
        s.addRecipe(copperSword());
        s.addRecipe(forge());
        s.addRecipe(stonecutter());
        s.addRecipe(ironBloom());
        s.addRecipe(ironBloomBlast());
        s.addRecipe(castIron());
        s.addRecipe(castIronBlast());
        s.addRecipe(ironBurden());
        s.addRecipe(denseIronOre());
        s.addRecipe(furnaceIron());
        s.addRecipe(coalCoke());
        s.addRecipe(charcoalCoke());
        s.addRecipe(heavyBurden());
        s.addRecipe(richBloom());
        s.addRecipe(ovenPadding());
        s.addRecipe(furnaceNether());
        s.addRecipe(furnaceCopperUpgrade());
        s.addRecipe(steelCharge());
        s.addRecipe(steelBillet());
        s.addRecipe(highCarbonBurden());
        s.addRecipe(pigIronMass());
        s.addRecipe(steelBilletFromPigIron());

        for (var recipe : planksSplitting())
            s.addRecipe(recipe);
        for (var recipe : planksCutting())
            s.addRecipe(recipe);
        for (var recipe : sticks())
            s.addRecipe(recipe);
    }

    ShapedRecipe flintAxeSimple() {
        var item = ItemManager.createFlintAxe(false);
        ShapedRecipe recipe = new ShapedRecipe(RecipeKeyList.flintAxeSimple, item);
        recipe.shape("FF", "FS");
        recipe.setIngredient('F', Material.FLINT);
        recipe.setIngredient('S', Material.STICK);

        return recipe;
    }

    ShapedRecipe flintAxe() {
        var item = ItemManager.createFlintAxe(true);
        ShapedRecipe recipe = new ShapedRecipe(RecipeKeyList.flintAxe, item);
        recipe.shape("FFT", "FS ", " S ");
        recipe.setIngredient('F', Material.FLINT);
        recipe.setIngredient('S', Material.STICK);
        recipe.setIngredient('T', new RecipeChoice.ExactChoice(ItemManager.createTwine()));

        return recipe;
    }

    ShapedRecipe flintKnifeSimple() {
        var item = ItemManager.createFlintKnife(false);
        ShapedRecipe recipe = new ShapedRecipe(RecipeKeyList.flintKnifeSimple, item);
        recipe.shape("F ", "S ");
        recipe.setIngredient('F', Material.FLINT);
        recipe.setIngredient('S', Material.STICK);

        return recipe;
    }

    ShapedRecipe flintKnife() {
        var item = ItemManager.createFlintKnife(true);
        ShapedRecipe recipe = new ShapedRecipe(RecipeKeyList.flintKnife, item);
        recipe.shape("F  ", "S  ", "T  ");
        recipe.setIngredient('F', Material.FLINT);
        recipe.setIngredient('S', Material.STICK);
        recipe.setIngredient('T', new RecipeChoice.ExactChoice(ItemManager.createTwine()));

        return recipe;
    }

    ShapedRecipe unfinishedCraftingTable() {

        var item = ItemManager.createCraftingTable();
        ShapedRecipe recipe = new ShapedRecipe(RecipeKeyList.unfinishedCraftingTable, item);
        recipe.shape("PP", "PP");
        recipe.setIngredient('P', new RecipeChoice.MaterialChoice(Tag.PLANKS));

        return recipe;
    }

    ShapedRecipe flintPick() {
        var item = ItemManager.createFlintPick();
        ShapedRecipe recipe = new ShapedRecipe(RecipeKeyList.flintPick, item);
        recipe.shape("FFF", "TS ", " S ");
        recipe.setIngredient('F', Material.FLINT);
        recipe.setIngredient('S', Material.STICK);
        recipe.setIngredient('T', new RecipeChoice.ExactChoice(ItemManager.createTwine()));
        return recipe;
    }

    ShapedRecipe flintShovel() {
        var item = ItemManager.createFlintShovel();
        ShapedRecipe recipe = new ShapedRecipe(RecipeKeyList.flintShovel, item);
        recipe.shape(" F ", "TS ", " S ");
        recipe.setIngredient('F', Material.FLINT);
        recipe.setIngredient('S', Material.STICK);
        recipe.setIngredient('T', new RecipeChoice.ExactChoice(ItemManager.createTwine()));
        return recipe;
    }

    ShapelessRecipe twine() {
        var item = ItemManager.createTwine();
        ShapelessRecipe recipe = new ShapelessRecipe(RecipeKeyList.twine, item);
        recipe.addIngredient(Material.SHORT_GRASS);
        recipe.addIngredient(Material.SHORT_GRASS);
        recipe.addIngredient(Material.SHORT_GRASS);

        return recipe;
    }

    ShapedRecipe campfire() {
        var item = ItemStack.of(Material.CAMPFIRE);

        ShapedRecipe recipe = new ShapedRecipe(RecipeKeyList.campfire, item);
        recipe.shape(" S ", "SLS");
        recipe.setIngredient('S', Material.STICK);
        recipe.setIngredient('L', new RecipeChoice.MaterialChoice(Tag.LOGS));

        return recipe;
    }

    CampfireRecipe terracotta() {
        var item = ItemStack.of(Material.TERRACOTTA);

        return new CampfireRecipe(RecipeKeyList.terracotta, item, Material.CLAY, 0.3f, 600);
    }

    ShapedRecipe furnace() {
        var item = ItemManager.createFurnace();

        ShapedRecipe recipe = new ShapedRecipe(RecipeKeyList.furnace, item);
        recipe.shape("TTT", "T T", "TTT");
        recipe.setIngredient('T', Material.TERRACOTTA);

        return recipe;
    }

    FurnaceRecipe copperNugget() {
        var item = ItemStack.of(Material.COPPER_NUGGET);

        return new FurnaceRecipe(RecipeKeyList.copperNugget, item, Material.RAW_COPPER, 0.5f, 200);
    }

    FurnaceRecipe copperIngot() {
        var item = ItemManager.createCopper();

        return new FurnaceRecipe(RecipeKeyList.copperIngot, item, Material.RAW_COPPER_BLOCK, 2.5f, 1600);
    }

    BlastingRecipe copperNuggetBlast() {
        var item = ItemStack.of(Material.COPPER_NUGGET);

        return new BlastingRecipe(RecipeKeyList.copperNuggetBlast, item, Material.RAW_COPPER, 0.5f, 100);
    }

    BlastingRecipe copperIngotBlast() {
        var item = ItemStack.of(Material.COPPER_INGOT);

        return new BlastingRecipe(RecipeKeyList.copperIngotBlast, item, Material.RAW_COPPER_BLOCK, 2.5f, 800);
    }

    ShapedRecipe copperPick() {
        var item = ItemManager.createCopperPick();

        ShapedRecipe recipe = new ShapedRecipe(RecipeKeyList.copperPick, item);
        recipe.shape("CCC", "TS ", " S ");
        recipe.setIngredient('S', Material.STICK);
        recipe.setIngredient('T', new RecipeChoice.ExactChoice(ItemManager.createTwine()));
        recipe.setIngredient('C', Material.COPPER_INGOT);
        return recipe;
    }

    ShapedRecipe copperAxe() {
        var item = ItemManager.createCopperAxe();

        ShapedRecipe recipe = new ShapedRecipe(RecipeKeyList.copperAxe, item);
        recipe.shape("CCT", "CS ", " S ");
        recipe.setIngredient('S', Material.STICK);
        recipe.setIngredient('T', new RecipeChoice.ExactChoice(ItemManager.createTwine()));
        recipe.setIngredient('C', Material.COPPER_INGOT);
        return recipe;
    }

    ShapedRecipe copperShovel() {
        var item = ItemManager.createCopperShovel();

        ShapedRecipe recipe = new ShapedRecipe(RecipeKeyList.copperShovel, item);
        recipe.shape(" C ", "TS ", " S ");
        recipe.setIngredient('S', Material.STICK);
        recipe.setIngredient('T', new RecipeChoice.ExactChoice(ItemManager.createTwine()));
        recipe.setIngredient('C', Material.COPPER_INGOT);
        return recipe;
    }

    ShapedRecipe furnaceCore() {
        var item = ItemManager.createFurnaceCore();

        ShapedRecipe recipe = new ShapedRecipe(RecipeKeyList.furnaceCore, item);
        recipe.shape("CCC", "C C", "CCC");
        recipe.setIngredient('C', Material.COBBLESTONE);

        return recipe;
    }

    ShapedRecipe furnaceCopper() {
        var item = ItemManager.createFurnaceCopper();

        ShapedRecipe recipe = new ShapedRecipe(RecipeKeyList.furnaceCopper, item);
        recipe.shape("CCC", "CFC", "CCC");
        recipe.setIngredient('C', Material.COPPER_INGOT);
        recipe.setIngredient('F',new RecipeChoice.ExactChoice(ItemManager.createFurnaceCore()));

        return recipe;
    }

    FurnaceRecipe heatedCopper() {
        var item = ItemManager.createHeatedCopper();

        return new FurnaceRecipe(RecipeKeyList.heatedCopper, item, Material.COPPER_INGOT, 0.1f, 200);
    }

    BlastingRecipe heatedCopperBlast() {
        var item = ItemManager.createHeatedCopper();

        return new BlastingRecipe(RecipeKeyList.heatedCopperBlast, item, Material.COPPER_INGOT, 0.1f, 100);
    }

    ShapedRecipe copperHammer() {
        var item = ItemManager.createCopperHammer();

        ShapedRecipe recipe = new ShapedRecipe(RecipeKeyList.copperHammer, item);
        recipe.shape("CCC", "CSC", " ST");
        recipe.setIngredient('S', Material.STICK);
        recipe.setIngredient('C', Material.COPPER_INGOT);
        recipe.setIngredient('T', new RecipeChoice.ExactChoice(ItemManager.createTwine()));
        return recipe;
    }

    ShapedRecipe copperSword() {
        var item = ItemManager.createCopperSword();

        ShapedRecipe recipe = new ShapedRecipe(RecipeKeyList.copperSword, item);
        recipe.shape(" C ", " C ", " ST");
        recipe.setIngredient('S', Material.STICK);
        recipe.setIngredient('C', Material.COPPER_INGOT);
        recipe.setIngredient('T', new RecipeChoice.ExactChoice(ItemManager.createTwine()));
        return recipe;
    }

    ShapedRecipe forge() {
        var item = ItemManager.createForge();
        ShapedRecipe recipe = new ShapedRecipe(RecipeKeyList.forge, item);
        recipe.shape("CCC", "CTC", "CCC");
        recipe.setIngredient('T', Material.CRAFTING_TABLE);
        recipe.setIngredient('C', new RecipeChoice.ExactChoice(ItemManager.createHeatedCopper()));
        return recipe;
    }

    ShapedRecipe stonecutter() {
        var item = ItemStack.of(Material.STONECUTTER);
        ShapedRecipe recipe = new ShapedRecipe(RecipeKeyList.stonecutter, item);
        recipe.shape(" C ", "SSS");
        recipe.setIngredient('S', Material.STONE);
        recipe.setIngredient('C', Material.COPPER_INGOT);

        return recipe;
    }

    ShapedRecipe ironBurden() {
        var item = ItemManager.createIronBurden();
        ShapedRecipe recipe = new ShapedRecipe(RecipeKeyList.ironBurden, item);
        recipe.shape(" C ", "CIC", " C ");
        recipe.setIngredient('I', Material.RAW_IRON_BLOCK);
        recipe.setIngredient('C', Material.CHARCOAL);

        return recipe;
    }

    ShapedRecipe denseIronOre() {
        var item = ItemManager.createDenseIronOre();
        ShapedRecipe recipe = new ShapedRecipe(RecipeKeyList.denseIron, item);
        recipe.shape("II", "II");
        recipe.setIngredient('I', Material.RAW_IRON);

        return recipe;
    }

    FurnaceRecipe castIron() {
        var item = ItemManager.createCastIron();

        return new FurnaceRecipe(RecipeKeyList.castIron, item, new RecipeChoice.ExactChoice(ItemManager.createDenseIronOre()), 0.75f, 400);
    }

    FurnaceRecipe ironBloom() {
        var item = ItemManager.createIronBloom();

        return new FurnaceRecipe(RecipeKeyList.ironBloom, item, new RecipeChoice.ExactChoice(ItemManager.createIronBurden()), 1.0f, 800);
    }

    BlastingRecipe castIronBlast() {
        var item = ItemManager.createCastIron();

        return new BlastingRecipe(RecipeKeyList.castIronBlast, item, new RecipeChoice.ExactChoice(ItemManager.createDenseIronOre()), 0.75f, 200);
    }

    BlastingRecipe ironBloomBlast() {
        var item = ItemManager.createIronBloom();

        return new BlastingRecipe(RecipeKeyList.ironBloomBlast, item, new RecipeChoice.ExactChoice(ItemManager.createIronBurden()), 1.0f, 400);
    }

    ShapedRecipe furnaceIron() {
        var item = ItemManager.createWroughtIronBlastFurnace();

        ShapedRecipe recipe = new ShapedRecipe(RecipeKeyList.furnaceIron, item);
        recipe.shape("CCC", "CFC", "CCC");
        recipe.setIngredient('C', new RecipeChoice.ExactChoice(ItemManager.createWroughtIron()));
        recipe.setIngredient('F', new RecipeChoice.ExactChoice(ItemManager.createFurnaceCore()));

        return recipe;
    }

    BlastingRecipe coalCoke() {
        var item = ItemManager.createCoalCoke();

        return new BlastingRecipe(RecipeKeyList.coalCoke, item, new RecipeChoice.ExactChoice(ItemStack.of(Material.COAL)), 0.2f, 800);
    }

    BlastingRecipe charcoalCoke() {
        var item = ItemManager.createCoalCoke();

        return new BlastingRecipe(RecipeKeyList.charcoalCoke, item, new RecipeChoice.ExactChoice(ItemStack.of(Material.CHARCOAL)), 0.2f, 1000);
    }

    ShapedRecipe heavyBurden() {
        var item = ItemManager.createHeavyBurden();
        ShapedRecipe recipe = new ShapedRecipe(RecipeKeyList.heavyBurden, item);
        recipe.shape("CIC", "ICI", "CIC");
        recipe.setIngredient('I', Material.RAW_IRON_BLOCK);
        recipe.setIngredient('C', new RecipeChoice.ExactChoice(ItemManager.createCoalCoke()));

        return recipe;
    }

    BlastingRecipe richBloom() {
        var item = ItemManager.createRichBloom();

        return new BlastingRecipe(RecipeKeyList.richBloom, item, new RecipeChoice.ExactChoice(ItemManager.createHeavyBurden()), 2.0f, 800);
    }

    ShapedRecipe ovenPadding() {
        var item = ItemManager.createPadding();
        ShapedRecipe recipe = new ShapedRecipe(RecipeKeyList.ovenPadding, item);
        recipe.shape("CIC", "ICI", "CIC");
        recipe.setIngredient('I', new RecipeChoice.ExactChoice(ItemManager.createWroughtIron()));
        recipe.setIngredient('C', Material.NETHER_BRICKS);

        return recipe;
    }

    ShapedRecipe furnaceNether() {
        var item = ItemManager.createBlastOven();

        ShapedRecipe recipe = new ShapedRecipe(RecipeKeyList.furnaceNether, item);
        recipe.shape("PPP", "PFP", "PPP");
        recipe.setIngredient('P', new RecipeChoice.ExactChoice(ItemManager.createPadding()));
        recipe.setIngredient('F', new RecipeChoice.ExactChoice(ItemManager.createFurnaceCore()));

        return recipe;
    }

    ShapedRecipe furnaceCopperUpgrade() {
        var item = ItemManager.createCopperOven();

        ShapedRecipe recipe = new ShapedRecipe(RecipeKeyList.furnaceCopperUpgrade, item);
        recipe.shape(" P ", "PFP", " P ");
        recipe.setIngredient('P', new RecipeChoice.ExactChoice(ItemManager.createPadding()));
        recipe.setIngredient('F', new RecipeChoice.ExactChoice(ItemManager.createFurnaceCopper()));

        return recipe;
    }

    ShapedRecipe steelCharge() {
        var item = ItemManager.createSteelCharge();
        ShapedRecipe recipe = new ShapedRecipe(RecipeKeyList.steelCharge, item);
        recipe.shape(" C ", "CIC", " C ");
        recipe.setIngredient('I', new RecipeChoice.ExactChoice(ItemManager.createWroughtIron()));
        recipe.setIngredient('C', Material.COAL);

        return recipe;
    }

    BlastingRecipe steelBillet() {
        var item = ItemManager.createSteelBillet();

        return new BlastingRecipe(RecipeKeyList.steelBillet, item, new RecipeChoice.ExactChoice(ItemManager.createSteelCharge()), 3.0f, 1600);
    }

    ShapedRecipe highCarbonBurden() {
        var item = ItemManager.createHighCarbonBurden();
        ShapedRecipe recipe = new ShapedRecipe(RecipeKeyList.highCarbonBurden, item);
        recipe.shape("CCC", "CBC", "CCC");
        recipe.setIngredient('B', new RecipeChoice.ExactChoice(ItemManager.createIronBurden()));
        recipe.setIngredient('C', Material.COAL);

        return recipe;
    }

    BlastingRecipe pigIronMass() {
        var item = ItemManager.createPigIronMass();

        return new BlastingRecipe(RecipeKeyList.pigIronMass, item, new RecipeChoice.ExactChoice(ItemManager.createHighCarbonBurden()), 1.0f, 800);
    }

    BlastingRecipe steelBilletFromPigIron() {
        var item = ItemManager.createSteelBillet();

        return new BlastingRecipe(RecipeKeyList.steelBilletFromPigIron, item, new RecipeChoice.ExactChoice(ItemManager.createPigIronMass()), 3.0f, 2000);
    }

    List<ShapelessRecipe> planksSplitting() {
        Material[] logs = {
                Material.OAK_LOG,
                Material.SPRUCE_LOG,
                Material.BIRCH_LOG,
                Material.JUNGLE_LOG,
                Material.ACACIA_LOG,
                Material.DARK_OAK_LOG,
                Material.MANGROVE_LOG,
                Material.CHERRY_LOG,
                Material.PALE_OAK_LOG
        };

        List<ShapelessRecipe> planks = new ArrayList<>();
        List<NamespacedKey> keys = new ArrayList<>();

        for (Material log : logs) {
            String baseName = log.name()
                    .replace("_LOG", "");

            Material stripped = Material.matchMaterial("STRIPPED_" + baseName + "_LOG");
            if (stripped == null) continue;

            Material plank = Material.matchMaterial(baseName + "_PLANKS");
            if (plank == null) continue;

            ItemStack result = ItemStack.of(plank);

            NamespacedKey key = new NamespacedKey(plugin, "split_" + baseName.toLowerCase());
            keys.add(key);

            ShapelessRecipe recipe = new ShapelessRecipe(key, result);
            recipe.addIngredient(stripped);

            NamespacedKey keyL = new NamespacedKey(plugin, "split_" + log.name().toLowerCase());
            keys.add(keyL);

            ShapelessRecipe recipeL = new ShapelessRecipe(keyL, result.add());
            recipeL.addIngredient(log);

            recipe.setGroup("planks");
            recipeL.setGroup("planks");

            planks.add(recipe);
            planks.add(recipeL);
        }

        RecipeKeyList.planks = keys.toArray(new NamespacedKey[0]);
        return planks;
    }

    List<StonecuttingRecipe> planksCutting() {
        Material[] logs = {
                Material.OAK_LOG,
                Material.SPRUCE_LOG,
                Material.BIRCH_LOG,
                Material.JUNGLE_LOG,
                Material.ACACIA_LOG,
                Material.DARK_OAK_LOG,
                Material.MANGROVE_LOG,
                Material.CHERRY_LOG,
                Material.PALE_OAK_LOG
        };

        List<StonecuttingRecipe> planks = new ArrayList<>();
        List<NamespacedKey> keys = new ArrayList<>();

        for (Material log : logs) {
            String baseName = log.name()
                    .replace("_LOG", "");

            Material stripped = Material.matchMaterial("STRIPPED_" + baseName + "_LOG");
            if (stripped == null) continue;

            Material plank = Material.matchMaterial(baseName + "_PLANKS");
            if (plank == null) continue;

            ItemStack result = ItemStack.of(plank, 2);

            NamespacedKey key = new NamespacedKey(plugin, "stonecut_" + baseName.toLowerCase());
            keys.add(key);

            StonecuttingRecipe recipe = new StonecuttingRecipe(key, result, stripped);

            NamespacedKey keyL = new NamespacedKey(plugin, "stonecut_" + log.name().toLowerCase());
            keys.add(keyL);

            StonecuttingRecipe recipeL = new StonecuttingRecipe(keyL, result.add(2), log);

            recipe.setGroup("planks");
            recipeL.setGroup("planks");

            planks.add(recipe);
            planks.add(recipeL);
        }

        RecipeKeyList.planksCut = keys.toArray(new NamespacedKey[0]);
        return planks;
    }

    List<StonecuttingRecipe> sticks() {
        Material[] planks = {
                Material.OAK_PLANKS,
                Material.SPRUCE_PLANKS,
                Material.BIRCH_PLANKS,
                Material.JUNGLE_PLANKS,
                Material.ACACIA_PLANKS,
                Material.DARK_OAK_PLANKS,
                Material.MANGROVE_PLANKS,
                Material.CHERRY_PLANKS,
                Material.PALE_OAK_PLANKS
        };

        List<StonecuttingRecipe> stickArray = new ArrayList<>();
        List<NamespacedKey> keys = new ArrayList<>();

        for (Material plank : planks) {
            String baseName = plank.name();

            ItemStack result = ItemStack.of(Material.STICK, 4);

            NamespacedKey key = new NamespacedKey(plugin, "stonecut_" + baseName.toLowerCase() +"_to_sticks");
            keys.add(key);

            StonecuttingRecipe recipe = new StonecuttingRecipe(key, result, plank);
            recipe.setGroup("sticks");

            stickArray.add(recipe);
        }

        RecipeKeyList.sticksCut = keys.toArray(new NamespacedKey[0]);
        return stickArray;
    }
}
