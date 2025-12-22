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

        tab.registerAdvancements(Root, NoLog, Handaxe, Flint, Sticks, Knife, Axe, Grass, Twine, Log, Crafting, Carving, Pick,
                Campfire, Terracotta, Furnace, Copper, Stonecutter, CopperPick, CopperFurnace, HammerTime, HeatedCopper, BulkCopper,
                Forge, ForgedPickaxe, SuitedUp, ForgedHammer, IronBurden, CastIron, IronBloom, WroughtIron, IronHammer, IronPick,
                IronArmor, BlastFurnace);
    }

    public static AdvancementTab getTab() {
        return tab;
    }
}