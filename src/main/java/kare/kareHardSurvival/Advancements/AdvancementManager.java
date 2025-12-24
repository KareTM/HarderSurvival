package kare.kareHardSurvival.Advancements;

import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import com.fren_gor.ultimateAdvancementAPI.UltimateAdvancementAPI;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import com.fren_gor.ultimateAdvancementAPI.advancement.multiParents.MultiParentsAdvancement;
import com.fren_gor.ultimateAdvancementAPI.util.AdvancementKey;
import com.fren_gor.ultimateAdvancementAPI.util.CoordAdapter;
import kare.kareHardSurvival.Items.ItemManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class AdvancementManager {
    public static RootAdvancement Root;
    public static BaseAdvancement NoLog;
    public static BaseAdvancement Handaxe;
    public static BaseAdvancement Flint;
    public static BaseAdvancement Sticks;
    public static MultiParentsAdvancement Knife;
    public static MultiParentsAdvancement Axe;
    public static BaseAdvancement Grass;
    public static BaseAdvancement Twine;
    public static BaseAdvancement Log;
    public static BaseAdvancement Crafting;
    public static MultiParentsAdvancement Carving;
    public static MultiParentsAdvancement Pick;
    public static BaseAdvancement Campfire;
    public static BaseAdvancement Terracotta;
    public static BaseAdvancement Furnace;
    public static MultiParentsAdvancement Copper;
    public static BaseAdvancement Stonecutter;
    public static BaseAdvancement CopperPick;
    public static BaseAdvancement CopperFurnace;
    public static BaseAdvancement BulkCopper;
    public static BaseAdvancement HeatedCopper;
    public static BaseAdvancement HammerTime;
    public static MultiParentsAdvancement Forge;
    public static BaseAdvancement ForgedPickaxe;
    public static BaseAdvancement SuitedUp;
    public static BaseAdvancement ForgedHammer;
    public static BaseAdvancement CastIron;
    public static BaseAdvancement IronBurden;
    public static BaseAdvancement IronBloom;
    public static BaseAdvancement WroughtIron;
    public static BaseAdvancement IronHammer;
    public static BaseAdvancement IronPick;
    public static BaseAdvancement IronArmor;
    public static BaseAdvancement BlastFurnace;
    public static BaseAdvancement Coke;
    public static BaseAdvancement RichBloom;
    public static BaseAdvancement Nether;
    public static BaseAdvancement Padding;
    public static BaseAdvancement CopperFurnaceUpgrade;
    public static MultiParentsAdvancement Oven;
    public static BaseAdvancement SteelCharge;
    public static BaseAdvancement CarbonBurden;
    public static BaseAdvancement PigIron;
    public static MultiParentsAdvancement SteelBillet;
    public static BaseAdvancement SteelIngot;
    public static BaseAdvancement SteelHammer;
    public static BaseAdvancement SteelPick;
    public static BaseAdvancement SteelArmor;


    Plugin plugin;
    UltimateAdvancementAPI api;
    static AdvancementTab tab;
    AdvancementKey pluginKey;

    public AdvancementManager(Plugin plugin) {
        this.plugin = plugin;
        api = UltimateAdvancementAPI.getInstance(plugin);
        tab = api.createAdvancementTab(plugin.namespace());
        pluginKey = new AdvancementKey(tab.getNamespace(), "kare_hard_survival");
    }

    public void Advancement() {
        AdvancementKey no_logKey = new AdvancementKey(tab.getNamespace(), "no_log");
        AdvancementKey handaxeKey = new AdvancementKey(tab.getNamespace(), "handaxe");
        AdvancementKey flintKey = new AdvancementKey(tab.getNamespace(), "flint");
        AdvancementKey sticksKey = new AdvancementKey(tab.getNamespace(), "sticks");
        AdvancementKey knifeKey = new AdvancementKey(tab.getNamespace(), "knife");
        AdvancementKey axeKey = new AdvancementKey(tab.getNamespace(), "axe");
        AdvancementKey grassKey = new AdvancementKey(tab.getNamespace(), "grass");
        AdvancementKey twineKey = new AdvancementKey(tab.getNamespace(), "twine");
        AdvancementKey logKey = new AdvancementKey(tab.getNamespace(), "log");
        AdvancementKey craftingKey = new AdvancementKey(tab.getNamespace(), "crafting");
        AdvancementKey carvingKey = new AdvancementKey(tab.getNamespace(), "carving");
        AdvancementKey pickKey = new AdvancementKey(tab.getNamespace(), "pick");
        AdvancementKey campfireKey = new AdvancementKey(tab.getNamespace(), "campfire");
        AdvancementKey terracottaKey = new AdvancementKey(tab.getNamespace(), "terracotta");
        AdvancementKey furnaceKey = new AdvancementKey(tab.getNamespace(), "furnace");
        AdvancementKey copperKey = new AdvancementKey(tab.getNamespace(), "copper");
        AdvancementKey stonecutterKey = new AdvancementKey(tab.getNamespace(), "stonecutter");
        AdvancementKey copperPickKey = new AdvancementKey(tab.getNamespace(), "copper_pick");
        AdvancementKey copperFurnaceKey = new AdvancementKey(tab.getNamespace(), "copper_furnace");
        AdvancementKey bulkCopperKey = new AdvancementKey(tab.getNamespace(), "bulk_copper");
        AdvancementKey heatedCopperKey = new AdvancementKey(tab.getNamespace(), "heated_copper");
        AdvancementKey hammerKey = new AdvancementKey(tab.getNamespace(), "hammer");
        AdvancementKey forgeKey = new AdvancementKey(tab.getNamespace(), "forge");
        AdvancementKey forgedPickKey = new AdvancementKey(tab.getNamespace(), "forged_pickaxe");
        AdvancementKey suitedKey = new AdvancementKey(tab.getNamespace(), "suited_up");
        AdvancementKey forgedHammerKey = new AdvancementKey(tab.getNamespace(), "forged_hammer");
        AdvancementKey ironBurdenKey = new AdvancementKey(tab.getNamespace(), "iron_burden");
        AdvancementKey castIronKey = new AdvancementKey(tab.getNamespace(), "cast_iron");
        AdvancementKey ironBloomKey = new AdvancementKey(tab.getNamespace(), "iron_bloom");
        AdvancementKey wroughtIronKey = new AdvancementKey(tab.getNamespace(), "wrought_iron");
        AdvancementKey ironHammerKey = new AdvancementKey(tab.getNamespace(), "iron_hammer");
        AdvancementKey ironPickKey = new AdvancementKey(tab.getNamespace(), "iron_pick");
        AdvancementKey ironArmorKey = new AdvancementKey(tab.getNamespace(), "iron_armor");
        AdvancementKey blastFurnaceKey = new AdvancementKey(tab.getNamespace(), "blast_furnace");
        AdvancementKey cokeKey = new AdvancementKey(tab.getNamespace(), "coke");
        AdvancementKey richBloomKey = new AdvancementKey(tab.getNamespace(), "rich_bloom");
        AdvancementKey netherKey = new AdvancementKey(tab.getNamespace(), "nether");
        AdvancementKey paddingKey = new AdvancementKey(tab.getNamespace(), "padding");
        AdvancementKey ovenKey = new AdvancementKey(tab.getNamespace(), "oven");
        AdvancementKey copperUpgradeKey = new AdvancementKey(tab.getNamespace(), "copper_upgrade");
        AdvancementKey carbonBurdenKey = new AdvancementKey(tab.getNamespace(), "carbon_burden");
        AdvancementKey steelChargeKey = new AdvancementKey(tab.getNamespace(), "steel_charge");
        AdvancementKey pigIronKey = new AdvancementKey(tab.getNamespace(), "pig_iron");
        AdvancementKey steelBilletKey = new AdvancementKey(tab.getNamespace(), "steel_billet");
        AdvancementKey steelIngotKey = new AdvancementKey(tab.getNamespace(), "steel_ingot");
        AdvancementKey steelHammerKey = new AdvancementKey(tab.getNamespace(), "steel_hammer");
        AdvancementKey steelPickKey = new AdvancementKey(tab.getNamespace(), "steel_pick");
        AdvancementKey steelArmorKey = new AdvancementKey(tab.getNamespace(), "steel_armor");

        CoordAdapter coordAdapter = CoordAdapter.builder().add(pluginKey, -2f, 0f).
                add(no_logKey, -1f, 0f).
                add(handaxeKey, 0f, 0f).
                add(flintKey, 1f, 1f).
                add(sticksKey, 1f, -1f).
                add(knifeKey, 2f, 1f).
                add(axeKey, 2f, -1f).
                add(grassKey, 3f, 1f).
                add(twineKey, 5f, 1f).
                add(logKey, 3f, -1f).
                add(craftingKey, 4f, -1f).
                add(carvingKey, 5f, 0f).
                add(pickKey, 6f, 1f).
                add(campfireKey, 6f, -1f).
                add(terracottaKey, 7f, -1f).
                add(furnaceKey, 8f, -1f).
                add(copperKey, 9f, 0f).
                add(stonecutterKey, 10f, 2f).
                add(copperPickKey, 10f, -1f).
                add(copperFurnaceKey, 10f, 1f).
                add(hammerKey, 10f, 0f).
                add(heatedCopperKey, 11f, 1f).
                add(bulkCopperKey, 11f, 2f).
                add(forgeKey, 12f, 0f).
                add(forgedPickKey, 13f, 0f).
                add(suitedKey, 13f, -1f).
                add(forgedHammerKey, 13f, 1f).
                add(ironBurdenKey, 14f, 0f).
                add(castIronKey, 14f, 1f).
                add(ironBloomKey, 15f, 0f).
                add(wroughtIronKey, 16f, 0f).
                add(ironHammerKey, 17f, -1f).
                add(ironPickKey, 17f, 0f).
                add(blastFurnaceKey, 17f, 1f).
                add(ironArmorKey, 17f, 2f).
                add(netherKey, 18f, 0f).
                add(cokeKey, 18f, 1f).
                add(richBloomKey, 19f, 2f).
                add(paddingKey, 19f, 0f).
                add(copperUpgradeKey, 20f, -1f).
                add(ovenKey, 20f, 1f).
                add(steelChargeKey, 21f, 1f).
                add(carbonBurdenKey, 21f, 0f).
                add(pigIronKey, 22f, 0f).
                add(steelBilletKey, 23f, 1f).
                add(steelIngotKey, 24f, 1f).
                add(steelArmorKey, 25f, -1f).
                add(steelPickKey, 25f, 0f).
                add(steelHammerKey, 25f, 1f).
                build();


        Root = new RootAdvancement(tab, pluginKey.getKey(), new AdvancementDisplay(Material.BEEF, "Kare's Harder Survival", AdvancementFrameType.TASK,
                false, false, coordAdapter.getX(pluginKey), coordAdapter.getY(pluginKey),
                "The start of Something fun."), "textures/block/red_terracotta.png", 1);
        NoLog = new BaseAdvancement(no_logKey.getKey(), new AdvancementDisplay(Material.OAK_LOG, "That Hurts?", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(no_logKey), coordAdapter.getY(no_logKey),
                "Just try punching a tree"), Root, 1);
        Handaxe = new ParentVisibleBaseAdvancement(handaxeKey.getKey(), new AdvancementDisplay(ItemManager.createHandaxe(), "The Handaxe", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(handaxeKey), coordAdapter.getY(handaxeKey),
                "Breaking stone with your hand somehow is fine"), NoLog, 1);
        Flint = new ParentVisibleBaseAdvancement(flintKey.getKey(), new AdvancementDisplay(Material.FLINT, "Acquire Flintware", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(flintKey), coordAdapter.getY(flintKey),
                "The Handaxe can chip Flint from Stone"), Handaxe, 1);
        Sticks = new ParentVisibleBaseAdvancement(sticksKey.getKey(), new AdvancementDisplay(Material.STICK, "A Sticky Situation", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(sticksKey), coordAdapter.getY(sticksKey),
                "Chopping Leaves for Sticks with the new fancy tool"), Handaxe, 1);
        Knife = new MultiParentsAdvancement(knifeKey.getKey(), new AdvancementDisplay(ItemManager.createFlintKnife(false), "London Moment", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(knifeKey), coordAdapter.getY(knifeKey),
                "Craft a Flint Knife"), 1, Flint, Sticks);
        Axe = new MultiParentsAdvancement(axeKey.getKey(), new AdvancementDisplay(ItemManager.createFlintAxe(false), "Back to Wood", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(axeKey), coordAdapter.getY(axeKey),
                "Craft a Flint Axe"), 1, Flint, Sticks);
        Grass = new ParentVisibleBaseAdvancement(grassKey.getKey(), new AdvancementDisplay(Material.SHORT_GRASS, "Grass is Greener", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(grassKey), coordAdapter.getY(grassKey),
                "Cut Grass with your Knife"), Knife, 1);
        Twine = new ParentVisibleBaseAdvancement(twineKey.getKey(), new AdvancementDisplay(ItemManager.createTwine(), "Plant Fiber", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(twineKey), coordAdapter.getY(twineKey),
                "Craft grass into twine"), Grass, 1);
        Log = new ParentVisibleBaseAdvancement(logKey.getKey(), new AdvancementDisplay(Material.STRIPPED_OAK_LOG, "That Took Forever", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(logKey), coordAdapter.getY(logKey),
                "Chop down a tree"), Axe, 1);
        Crafting = new ParentVisibleBaseAdvancement(craftingKey.getKey(), new AdvancementDisplay(ItemManager.createCraftingTable(), "What does Unfinished Mean?", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(craftingKey), coordAdapter.getY(craftingKey),
                "Craft a crafting table"), Log, 1);
        Carving = new ParentVisibleMultiParentAdvancement(carvingKey.getKey(), new AdvancementDisplay(Material.CRAFTING_TABLE, "A Whole New World", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(carvingKey), coordAdapter.getY(carvingKey),
                "Finish carving (breaking) a Crafting Table using your Knife"), 3, Crafting, Knife);
        Pick = new ParentVisibleMultiParentAdvancement(pickKey.getKey(), new AdvancementDisplay(ItemManager.createFlintPick(), "So We Back in the Mine", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(pickKey), coordAdapter.getY(pickKey),
                "Craft a Flint Pickaxe to swing from side to side"), 1, Carving, Twine);
        Campfire = new ParentVisibleBaseAdvancement(campfireKey.getKey(), new AdvancementDisplay(Material.CAMPFIRE, "This Campfire is Lit", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(campfireKey), coordAdapter.getY(campfireKey),
                "Light a Campfire by breaking it with Flint"), Carving, 1);
        Terracotta = new ParentVisibleBaseAdvancement(terracottaKey.getKey(), new AdvancementDisplay(Material.TERRACOTTA, "Hardened Clay", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(terracottaKey), coordAdapter.getY(terracottaKey),
                "Use the Campfire to smelt Clay"), Campfire, 2);
        Furnace = new ParentVisibleBaseAdvancement(furnaceKey.getKey(), new AdvancementDisplay(ItemManager.createFurnace(), "Smelter? I Barely Even Know Her", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(furnaceKey), coordAdapter.getY(furnaceKey),
                "Craft a Terracotta Furnace, which uses Coal, Charcoal or Logs as Fuel"), Terracotta, 1);
        Copper = new ParentVisibleMultiParentAdvancement(copperKey.getKey(), new AdvancementDisplay(Material.COPPER_NUGGET, "Terrible Rates", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(copperKey), coordAdapter.getY(copperKey),
                "Smelt Raw Copper in your shiny new Terracotta Furnace"), 1, Furnace, Pick);
        Stonecutter = new ParentVisibleBaseAdvancement(stonecutterKey.getKey(), new AdvancementDisplay(Material.STONECUTTER, "Stonecutter? More Like Planksplitter", AdvancementFrameType.GOAL,
                true, true, coordAdapter.getX(stonecutterKey), coordAdapter.getY(stonecutterKey),
                "Craft a Stonecutter, which will help you split planks more efficiently as well as get sticks from them"), Copper, 1);
        CopperPick = new ParentVisibleBaseAdvancement(copperPickKey.getKey(), new AdvancementDisplay(Material.COPPER_PICKAXE, "Get an Upgrade", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(copperPickKey), coordAdapter.getY(copperPickKey),
                "Make a Crafted Copper Pickaxe, which will extract more Raw Copper from Copper Ore"), Copper, 1);
        CopperFurnace = new ParentVisibleBaseAdvancement(copperFurnaceKey.getKey(), new AdvancementDisplay(ItemManager.createFurnaceCopper(), "A Copper Plated This Furnace?", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(copperFurnaceKey), coordAdapter.getY(copperFurnaceKey),
                "The Copper Plated Furnace is both faster and more fuel efficient than a Terracotta Furnace, " +
                        "at the cost of not being able to use Logs as Fuel"), Copper, 1);
        HammerTime = new ParentVisibleBaseAdvancement(hammerKey.getKey(), new AdvancementDisplay(ItemManager.createCopperHammer(), "It's Hammer Time", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(hammerKey), coordAdapter.getY(hammerKey),
                "Make a Crafted Copper Hammer, for all your hammering needs"), Copper, 1);
        BulkCopper = new ParentVisibleBaseAdvancement(bulkCopperKey.getKey(), new AdvancementDisplay(ItemStack.of(Material.COPPER_INGOT), "Bulk Copper Smelting", AdvancementFrameType.GOAL,
                true, true, coordAdapter.getX(bulkCopperKey), coordAdapter.getY(bulkCopperKey),
                "Smelt Copper in bulk, by using a Raw Copper Block, 9x the output, 8x the time"), CopperFurnace, 1);
        HeatedCopper = new ParentVisibleBaseAdvancement(heatedCopperKey.getKey(), new AdvancementDisplay(ItemManager.createHeatedCopper(), "Things are Heating Up", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(heatedCopperKey), coordAdapter.getY(heatedCopperKey),
                "Heat up some Copper in your new Copper Plated Furnace"), CopperFurnace, 1);
        Forge = new ParentVisibleMultiParentAdvancement(forgeKey.getKey(), new AdvancementDisplay(ItemManager.createForge(), "The Tool Maker", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(forgeKey), coordAdapter.getY(forgeKey),
                "Craft a Forge, where you will spend a lot of time clicking"), 1, HeatedCopper, HammerTime);
        ForgedPickaxe = new ParentVisibleBaseAdvancement(forgedPickKey.getKey(), new AdvancementDisplay(ItemManager.createForgedCopperPick(), "Get a Second Upgrade", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(forgedPickKey), coordAdapter.getY(forgedPickKey),
                "Forge a new, and better pickaxe, unlocking iron mining"), Forge, 1);
        SuitedUp = new ParentVisibleBaseAdvancement(suitedKey.getKey(), new AdvancementDisplay(Material.COPPER_CHESTPLATE, "Suit Up", AdvancementFrameType.GOAL,
                true, true, coordAdapter.getX(suitedKey), coordAdapter.getY(suitedKey),
                "Forge an armor piece out of Copper"), Forge, 1);
        ForgedHammer = new ParentVisibleBaseAdvancement(forgedHammerKey.getKey(), new AdvancementDisplay(ItemManager.createForgedCopperHammer(), "Bang On", AdvancementFrameType.GOAL,
                true, true, coordAdapter.getX(forgedHammerKey), coordAdapter.getY(forgedHammerKey),
                "Upgrade your hammer, if ye so desire"), Forge, 1);
        IronBurden = new ParentVisibleBaseAdvancement(ironBurdenKey.getKey(), new AdvancementDisplay(ItemManager.createIronBurden(), "A Heavy Burden", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(ironBurdenKey), coordAdapter.getY(ironBurdenKey),
                "Craft an Iron Burden, using Charcoal and a Raw Iron Block"), ForgedPickaxe, 1);
        CastIron = new ParentVisibleBaseAdvancement(castIronKey.getKey(), new AdvancementDisplay(ItemManager.createCastIron(), "Casted", AdvancementFrameType.GOAL,
                true, true, coordAdapter.getX(castIronKey), coordAdapter.getY(castIronKey),
                "You may get cheaper Cast Iron, by smelting Dense Raw Iron, which is useless for tools and armor but useful for buckets and others"), ForgedPickaxe, 1);
        IronBloom = new ParentVisibleBaseAdvancement(ironBloomKey.getKey(), new AdvancementDisplay(ItemManager.createIronBloom(), "Blooming Metal", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(ironBloomKey), coordAdapter.getY(ironBloomKey),
                "Smelt an Iron Burden into an Iron Bloom, it may take a while, so stock some Fuel"), IronBurden, 1);
        WroughtIron = new ParentVisibleBaseAdvancement(wroughtIronKey.getKey(), new AdvancementDisplay(Material.IRON_INGOT, "Wrought About It", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(wroughtIronKey), coordAdapter.getY(wroughtIronKey),
                "Forge an Wrought Iron Ingot from an Iron Bloom"), IronBloom, 1);
        IronHammer = new ParentVisibleBaseAdvancement(ironHammerKey.getKey(), new AdvancementDisplay(ItemManager.createWroughtIronHammer(), "Heavy Duty", AdvancementFrameType.GOAL,
                true, true, coordAdapter.getX(ironHammerKey), coordAdapter.getY(ironHammerKey),
                "Forge an Wrought Iron Hammer, which will cut your forging times in half, or money back guarantee"), WroughtIron, 1);
        IronPick = new ParentVisibleBaseAdvancement(ironPickKey.getKey(), new AdvancementDisplay(Material.IRON_PICKAXE, "Iron Peak", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(ironPickKey), coordAdapter.getY(ironPickKey),
                "Forge an Wrought Iron Pickaxe, and watch your Raw Iron multiply"), WroughtIron, 1);
        IronArmor = new ParentVisibleBaseAdvancement(ironArmorKey.getKey(), new AdvancementDisplay(Material.IRON_CHESTPLATE, "Armored Core", AdvancementFrameType.GOAL,
                true, true, coordAdapter.getX(ironArmorKey), coordAdapter.getY(ironArmorKey),
                "Forge any piece of Iron Armor, truly wonderful protection"), WroughtIron, 1);
        BlastFurnace = new ParentVisibleBaseAdvancement(blastFurnaceKey.getKey(), new AdvancementDisplay(ItemManager.createWroughtIronBlastFurnace(), "Blast It", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(blastFurnaceKey), coordAdapter.getY(blastFurnaceKey),
                "Craft a Blast Furnace, the fastest furnace to date, cannot use Charcoal"), WroughtIron, 1);
        Coke = new ParentVisibleBaseAdvancement(cokeKey.getKey(), new AdvancementDisplay(ItemManager.createCoalCoke(), "Cooked & Charred", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(cokeKey), coordAdapter.getY(cokeKey),
                "Smelt Coal (slow) or Charcoal (slower) into Coal Coke, which burns hotter, not necessarily longer"), BlastFurnace, 1);
        RichBloom = new ParentVisibleBaseAdvancement(richBloomKey.getKey(), new AdvancementDisplay(ItemManager.createRichBloom(), "Become Rich", AdvancementFrameType.GOAL,
                true, true, coordAdapter.getX(richBloomKey), coordAdapter.getY(richBloomKey),
                "With your new Coal Coke, craft a Heavy Burden, to smelt into Rich Bloom, netting you a bulk way of forging Wrought Iron"), Coke, 1);
        Nether = new ParentVisibleBaseAdvancement(netherKey.getKey(), new AdvancementDisplay(ItemStack.of(Material.OBSIDIAN), "Down Below", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(netherKey), coordAdapter.getY(netherKey),
                "Venture down, into the depths, the Nether awaits"), IronPick, 1);
        Padding = new ParentVisibleBaseAdvancement(paddingKey.getKey(), new AdvancementDisplay(ItemManager.createPadding(), "They Put Me in a Padded Room", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(paddingKey), coordAdapter.getY(paddingKey),
                "Mine out (or smelt and craft) some Nether Bricks, to create Oven Padding for your new furnace"), Nether, 1);
        Oven = new ParentVisibleMultiParentAdvancement(ovenKey.getKey(), new AdvancementDisplay(ItemManager.createBlastOven(), "Not for Baking", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(ovenKey), coordAdapter.getY(ovenKey),
                "Craft a Blast Oven, faster, but requires improving your fuel to Coal Coke, Blaze Rods or Lava"), 1, Padding, Coke);
        CopperFurnaceUpgrade = new ParentVisibleBaseAdvancement(copperUpgradeKey.getKey(), new AdvancementDisplay(ItemManager.createCopperOven(), "Blast from the Past", AdvancementFrameType.GOAL,
                true, true, coordAdapter.getX(copperUpgradeKey), coordAdapter.getY(copperUpgradeKey),
                "Craft a Padded Copper Oven, using your Copper Plated Furnaces and this new nifty Padding, which will massively speed up the furnace, " +
                        "at the cost of losing the ability to use Charcoal as fuel"), Padding, 1);
        SteelCharge = new ParentVisibleBaseAdvancement(steelChargeKey.getKey(), new AdvancementDisplay(ItemManager.createSteelCharge(), "Charge Ahead", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(steelChargeKey), coordAdapter.getY(steelChargeKey),
                "Craft a Steel Charge, a way to turn your Wrought Iron Ingots into Steel"), Oven, 1);
        CarbonBurden = new ParentVisibleBaseAdvancement(carbonBurdenKey.getKey(), new AdvancementDisplay(ItemManager.createHighCarbonBurden(), "Burdensome", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(carbonBurdenKey), coordAdapter.getY(carbonBurdenKey),
                "Craft a High Carbon Burden, which prepares Iron Burdens for Steel processing, may be more expensive but bypasses forging Wrought Iron"), Oven, 1);
        PigIron = new ParentVisibleBaseAdvancement(pigIronKey.getKey(), new AdvancementDisplay(ItemManager.createPigIronMass(), "Oink?", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(pigIronKey), coordAdapter.getY(pigIronKey),
                "After smelting the Burden, a Pig Iron Mass is created, an intermediary step in your Steel making process"), CarbonBurden, 1);
        SteelBillet = new OneParentVisibleMultiParentAdvancement(steelBilletKey.getKey(), new AdvancementDisplay(ItemManager.createSteelBillet(), "Still a Little Raw", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(steelBilletKey), coordAdapter.getY(steelBilletKey),
                "Obtain the Steel Billet, smelting from either Steel Charge or from Pig Iron Mass"), 1, SteelCharge, PigIron);
        SteelIngot = new ParentVisibleBaseAdvancement(steelIngotKey.getKey(), new AdvancementDisplay(ItemManager.createSteel(), "Proper Steel", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(steelIngotKey), coordAdapter.getY(steelIngotKey),
                "The final step in the Steel making chain, forge your Billet into Steel Ingots"), SteelBillet, 1);
        SteelHammer = new ParentVisibleBaseAdvancement(steelHammerKey.getKey(), new AdvancementDisplay(ItemManager.createSteelHammer(), "One Hit Wonder", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(steelHammerKey), coordAdapter.getY(steelHammerKey),
                "The best hammer your skills can craft, a whooping 75% reduction"), SteelIngot, 1);
        SteelArmor = new ParentVisibleBaseAdvancement(steelArmorKey.getKey(), new AdvancementDisplay(ItemManager.createSteelChestplate(), "Protected", AdvancementFrameType.GOAL,
                true, true, coordAdapter.getX(steelArmorKey), coordAdapter.getY(steelArmorKey),
                "Armor like you have not seen before"), SteelIngot, 1);
        SteelPick = new ParentVisibleBaseAdvancement(steelPickKey.getKey(), new AdvancementDisplay(ItemManager.createSteelPick(), "All Breaker", AdvancementFrameType.TASK,
                true, true, coordAdapter.getX(steelPickKey), coordAdapter.getY(steelPickKey),
                "This handy Pickaxe can break almost anything, it may be slightly slow but it's capable"), SteelIngot, 1);

        tab.registerAdvancements(Root, NoLog, Handaxe, Flint, Sticks, Knife, Axe, Grass, Twine, Log, Crafting, Carving, Pick,
                Campfire, Terracotta, Furnace, Copper, Stonecutter, CopperPick, CopperFurnace, HammerTime, HeatedCopper, BulkCopper,
                Forge, ForgedPickaxe, SuitedUp, ForgedHammer, IronBurden, CastIron, IronBloom, WroughtIron, IronHammer, IronPick,
                IronArmor, BlastFurnace, Coke, RichBloom, Nether, Padding, CopperFurnaceUpgrade, Oven, SteelCharge, CarbonBurden,
                PigIron, SteelBillet, SteelIngot, SteelArmor, SteelHammer, SteelPick);
    }

    public static AdvancementTab getTab() {
        return tab;
    }
}