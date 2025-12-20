package kare.kareHardSurvival.Items;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemAttributeModifiers;
import io.papermc.paper.datacomponent.item.ItemLore;
import io.papermc.paper.datacomponent.item.Tool;
import io.papermc.paper.datacomponent.item.Weapon;
import io.papermc.paper.datacomponent.item.attribute.AttributeModifierDisplay;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.set.RegistrySet;
import io.papermc.paper.registry.tag.TagKey;
import kare.kareHardSurvival.Helpers.FlagHelper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.util.TriState;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.BlockType;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.HashSet;

public class ItemManager {
    static Plugin plugin;

    public static void setPlugin(Plugin p) {
        plugin = p;
    }


    public static @NotNull ItemStack createHandaxe() {
        ItemStack item = ItemStack.of(Material.FLINT);
        item.setData(DataComponentTypes.MAX_DAMAGE, 10);
        item.setData(DataComponentTypes.DAMAGE, 0);
        item.setData(DataComponentTypes.TOOL, Tool.tool().defaultMiningSpeed(1.1f).build());
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Handaxe"));
        item.setData(DataComponentTypes.MAX_STACK_SIZE, 1);

        FlagHelper.setFlag(item, FlagHelper.flagSticks, true);
        FlagHelper.setFlag(item, FlagHelper.flagFlint, true);

        return item;
    }

    public static @NotNull ItemStack createFlintAxe(boolean withTwine) {
        ItemStack item = ItemStack.of(Material.STONE_AXE);
        item.unsetData(DataComponentTypes.TOOL);
        item.setData(DataComponentTypes.MAX_DAMAGE, 20);
        if (withTwine) {
            item.setData(DataComponentTypes.DAMAGE, 0);
        } else {
            item.setData(DataComponentTypes.DAMAGE, 10);
            item.setData(DataComponentTypes.LORE, ItemLore.lore().addLine(Component.text("Missing twine - Half Durability")).build());
        }
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Flint Axe"));

        var tk = TagKey.create(RegistryKey.BLOCK, Tag.MINEABLE_AXE.key());
        var blocks = Registry.BLOCK.getTag(tk);
        item.setData(DataComponentTypes.TOOL, Tool.tool().addRule(Tool.rule(blocks, 1.25f, TriState.NOT_SET)).build());

        item.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes().
                addModifier(Attribute.ATTACK_DAMAGE, new AttributeModifier(Attribute.ATTACK_DAMAGE.getKey(),
                                4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 4 Attack Damage").color(NamedTextColor.DARK_GREEN))).
                addModifier(Attribute.ATTACK_SPEED, new AttributeModifier(Attribute.ATTACK_SPEED.getKey(),
                                -3.2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 0.8 Attack Speed").color(NamedTextColor.DARK_GREEN)))
                .build());


        FlagHelper.setFlag(item, FlagHelper.flagSticks, true);
        FlagHelper.setFlag(item, FlagHelper.flagAxe, true);

        return item;
    }

    public static @NotNull ItemStack createFlintShovel() {
        ItemStack item = ItemStack.of(Material.STONE_SHOVEL);
        item.unsetData(DataComponentTypes.TOOL);
        item.setData(DataComponentTypes.MAX_DAMAGE, 20);
        item.setData(DataComponentTypes.DAMAGE, 0);

        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Flint Shovel"));

        var tk = TagKey.create(RegistryKey.BLOCK, Tag.MINEABLE_SHOVEL.key());
        var blocks = Registry.BLOCK.getTag(tk);
        item.setData(DataComponentTypes.TOOL, Tool.tool().addRule(Tool.rule(blocks, 1.25f, TriState.NOT_SET)).build());

        item.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes().
                addModifier(Attribute.ATTACK_DAMAGE, new AttributeModifier(Attribute.ATTACK_DAMAGE.getKey(),
                                1.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 1.5 Attack Damage").color(NamedTextColor.DARK_GREEN))).
                addModifier(Attribute.ATTACK_SPEED, new AttributeModifier(Attribute.ATTACK_SPEED.getKey(),
                                -3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 1 Attack Speed").color(NamedTextColor.DARK_GREEN)))
                .build());

        return item;
    }

    public static @NotNull ItemStack createFlintKnife(boolean withTwine) {
        ItemStack item = ItemStack.of(Material.STONE_SWORD);
        item.unsetData(DataComponentTypes.WEAPON);

        item.setData(DataComponentTypes.MAX_DAMAGE, 20);
        if (withTwine) {
            item.setData(DataComponentTypes.DAMAGE, 0);
        } else {
            item.setData(DataComponentTypes.DAMAGE, 10);
            item.setData(DataComponentTypes.LORE, ItemLore.lore().addLine(Component.text("Missing twine - Half Durability")).build());
        }
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Flint Knife"));

        item.setData(DataComponentTypes.WEAPON, Weapon.weapon().itemDamagePerAttack(1).build());

        item.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes().
                addModifier(Attribute.ATTACK_DAMAGE, new AttributeModifier(Attribute.ATTACK_DAMAGE.getKey(),
                        2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 2 Attack Damage").color(NamedTextColor.DARK_GREEN))).
                addModifier(Attribute.ATTACK_SPEED, new AttributeModifier(Attribute.ATTACK_SPEED.getKey(),
                                -2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 2 Attack Speed").color(NamedTextColor.DARK_GREEN)))
                .build());

        item.setData(DataComponentTypes.TOOL, Tool.tool().defaultMiningSpeed(1.25f).build());

        FlagHelper.setFlag(item, FlagHelper.flagSticks, true);
        FlagHelper.setFlag(item, FlagHelper.flagGrass, true);
        FlagHelper.setFlag(item, FlagHelper.flagCarvingTool, true);

        return item;
    }

    public static @NotNull ItemStack createFlintPick() {
        ItemStack item = ItemStack.of(Material.STONE_PICKAXE);
        item.setData(DataComponentTypes.MAX_DAMAGE, 20);
        item.setData(DataComponentTypes.DAMAGE, 0);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Flint Pickaxe"));

        var mats = getStoneBreakable();

        var blocks = RegistrySet.keySetFromValues(RegistryKey.BLOCK, mats);
        item.setData(DataComponentTypes.TOOL, Tool.tool().addRule(Tool.rule(blocks, 1.75f, TriState.TRUE)).build());

        item.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes().
                addModifier(Attribute.ATTACK_DAMAGE, new AttributeModifier(Attribute.ATTACK_DAMAGE.getKey(),
                                1.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 1.5 Attack Damage").color(NamedTextColor.DARK_GREEN))).
                addModifier(Attribute.ATTACK_SPEED, new AttributeModifier(Attribute.ATTACK_SPEED.getKey(),
                                -2.8, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 1.2 Attack Speed").color(NamedTextColor.DARK_GREEN)))
                .build());

        FlagHelper.setFlag(item, FlagHelper.flagPickTier1, true);

        return item;
    }

    public static @NotNull ItemStack createCopperPick() {
        ItemStack item = ItemStack.of(Material.COPPER_PICKAXE);
        item.setData(DataComponentTypes.MAX_DAMAGE, 80);
        item.setData(DataComponentTypes.DAMAGE, 0);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Crafted Copper Pickaxe"));

        var mats = getStoneBreakable();

        var blocks = RegistrySet.keySetFromValues(RegistryKey.BLOCK, mats);
        item.setData(DataComponentTypes.TOOL, Tool.tool().addRule(Tool.rule(blocks, 2.5f, TriState.TRUE)).build());

        item.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes().
                addModifier(Attribute.ATTACK_DAMAGE, new AttributeModifier(Attribute.ATTACK_DAMAGE.getKey(),
                                2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 2 Attack Damage").color(NamedTextColor.DARK_GREEN))).
                addModifier(Attribute.ATTACK_SPEED, new AttributeModifier(Attribute.ATTACK_SPEED.getKey(),
                                -2.8, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 1.2 Attack Speed").color(NamedTextColor.DARK_GREEN)))
                .build());

        FlagHelper.setFlag(item, FlagHelper.flagPickTier2, true);

        return item;
    }

    public static @NotNull ItemStack createCopperAxe() {
        ItemStack item = ItemStack.of(Material.COPPER_AXE);
        item.setData(DataComponentTypes.MAX_DAMAGE, 80);
        item.setData(DataComponentTypes.DAMAGE, 0);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Crafted Copper Axe"));

        var tk = TagKey.create(RegistryKey.BLOCK, Tag.MINEABLE_AXE.key());
        var blocks = Registry.BLOCK.getTag(tk);

        item.setData(DataComponentTypes.TOOL, Tool.tool().addRule(Tool.rule(blocks, 2.5f, TriState.TRUE)).build());

        item.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes().
                addModifier(Attribute.ATTACK_DAMAGE, new AttributeModifier(Attribute.ATTACK_DAMAGE.getKey(),
                                5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 5 Attack Damage").color(NamedTextColor.DARK_GREEN))).
                addModifier(Attribute.ATTACK_SPEED, new AttributeModifier(Attribute.ATTACK_SPEED.getKey(),
                                -3.2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 0.8 Attack Speed").color(NamedTextColor.DARK_GREEN)))
                .build());

        FlagHelper.setFlag(item, FlagHelper.flagSticks, true);
        FlagHelper.setFlag(item, FlagHelper.flagAxe, true);

        return item;
    }

    public static @NotNull ItemStack createCopperShovel() {
        ItemStack item = ItemStack.of(Material.COPPER_SHOVEL);
        item.setData(DataComponentTypes.MAX_DAMAGE, 80);
        item.setData(DataComponentTypes.DAMAGE, 0);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Crafted Copper Shovel"));

        var tk = TagKey.create(RegistryKey.BLOCK, Tag.MINEABLE_SHOVEL.key());
        var blocks = Registry.BLOCK.getTag(tk);

        item.setData(DataComponentTypes.TOOL, Tool.tool().addRule(Tool.rule(blocks, 2.5f, TriState.TRUE)).build());

        item.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes().
                addModifier(Attribute.ATTACK_DAMAGE, new AttributeModifier(Attribute.ATTACK_DAMAGE.getKey(),
                                2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 2 Attack Damage").color(NamedTextColor.DARK_GREEN))).
                addModifier(Attribute.ATTACK_SPEED, new AttributeModifier(Attribute.ATTACK_SPEED.getKey(),
                                -3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 1 Attack Speed").color(NamedTextColor.DARK_GREEN)))
                .build());

        return item;
    }

    public static @NotNull ItemStack createForgedCopperPick() {
        ItemStack item = ItemStack.of(Material.COPPER_PICKAXE);
        item.setData(DataComponentTypes.MAX_DAMAGE, 120);
        item.setData(DataComponentTypes.DAMAGE, 0);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Forged Copper Pickaxe"));

        var setAll = new HashSet<>(Tag.MINEABLE_PICKAXE.getValues());
        setAll.removeAll(Tag.NEEDS_IRON_TOOL.getValues());
        setAll.removeAll(Tag.NEEDS_DIAMOND_TOOL.getValues());

        var mats = new ArrayList<BlockType>();
        setAll.forEach(mat -> mats.add(Registry.BLOCK.get(mat.getKey())));

        var blocks = RegistrySet.keySetFromValues(RegistryKey.BLOCK, mats);
        item.setData(DataComponentTypes.TOOL, Tool.tool().addRule(Tool.rule(blocks, 3.5f, TriState.TRUE)).build());

        item.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes().
                addModifier(Attribute.ATTACK_DAMAGE, new AttributeModifier(Attribute.ATTACK_DAMAGE.getKey(),
                                2.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 2.5 Attack Damage").color(NamedTextColor.DARK_GREEN))).
                addModifier(Attribute.ATTACK_SPEED, new AttributeModifier(Attribute.ATTACK_SPEED.getKey(),
                                -2.8, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 1.2 Attack Speed").color(NamedTextColor.DARK_GREEN)))
                .build());

        FlagHelper.setFlag(item, FlagHelper.flagPickTier3, true);

        return item;
    }

    public static @NotNull ItemStack createForgedCopperAxe() {
        ItemStack item = ItemStack.of(Material.COPPER_AXE);
        item.setData(DataComponentTypes.MAX_DAMAGE, 120);
        item.setData(DataComponentTypes.DAMAGE, 0);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Forged Copper Axe"));

        var tk = TagKey.create(RegistryKey.BLOCK, Tag.MINEABLE_AXE.key());
        var blocks = Registry.BLOCK.getTag(tk);

        item.setData(DataComponentTypes.TOOL, Tool.tool().addRule(Tool.rule(blocks, 3.5f, TriState.TRUE)).build());

        item.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes().
                addModifier(Attribute.ATTACK_DAMAGE, new AttributeModifier(Attribute.ATTACK_DAMAGE.getKey(),
                                7, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 7 Attack Damage").color(NamedTextColor.DARK_GREEN))).
                addModifier(Attribute.ATTACK_SPEED, new AttributeModifier(Attribute.ATTACK_SPEED.getKey(),
                                -3.2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 0.8 Attack Speed").color(NamedTextColor.DARK_GREEN)))
                .build());

        return item;
    }

    public static @NotNull ItemStack createForgedCopperShovel() {
        ItemStack item = ItemStack.of(Material.COPPER_SHOVEL);
        item.setData(DataComponentTypes.MAX_DAMAGE, 120);
        item.setData(DataComponentTypes.DAMAGE, 0);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Forged Copper Shovel"));

        var tk = TagKey.create(RegistryKey.BLOCK, Tag.MINEABLE_SHOVEL.key());
        var blocks = Registry.BLOCK.getTag(tk);

        item.setData(DataComponentTypes.TOOL, Tool.tool().addRule(Tool.rule(blocks, 3.5f, TriState.TRUE)).build());

        item.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes().
                addModifier(Attribute.ATTACK_DAMAGE, new AttributeModifier(Attribute.ATTACK_DAMAGE.getKey(),
                                2.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 2.5 Attack Damage").color(NamedTextColor.DARK_GREEN))).
                addModifier(Attribute.ATTACK_SPEED, new AttributeModifier(Attribute.ATTACK_SPEED.getKey(),
                                -3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 1 Attack Speed").color(NamedTextColor.DARK_GREEN)))
                .build());

        return item;
    }

    public static @NotNull ItemStack createCopperHammer() {
        ItemStack item = ItemStack.of(Material.COPPER_AXE);
        item.unsetData(DataComponentTypes.TOOL);
        item.setData(DataComponentTypes.MAX_DAMAGE, 80);
        item.setData(DataComponentTypes.DAMAGE, 0);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Crafted Copper Hammer"));

        item.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes().
                addModifier(Attribute.ATTACK_DAMAGE, new AttributeModifier(Attribute.ATTACK_DAMAGE.getKey(),
                                7, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 7 Attack Damage").color(NamedTextColor.DARK_GREEN))).
                addModifier(Attribute.ATTACK_SPEED, new AttributeModifier(Attribute.ATTACK_SPEED.getKey(),
                                -3.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 0.5 Attack Speed").color(NamedTextColor.DARK_GREEN)))
                .build());

        FlagHelper.setFlag(item, FlagHelper.flagHammer, true);
        FlagHelper.setFlag(item, FlagHelper.flagHammerTier1, true);

        return item;
    }

    public static @NotNull ItemStack createForgedCopperHammer() {
        ItemStack item = ItemStack.of(Material.COPPER_AXE);
        item.unsetData(DataComponentTypes.TOOL);
        item.setData(DataComponentTypes.MAX_DAMAGE, 120);
        item.setData(DataComponentTypes.DAMAGE, 0);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Forged Copper Hammer"));

        item.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes().
                addModifier(Attribute.ATTACK_DAMAGE, new AttributeModifier(Attribute.ATTACK_DAMAGE.getKey(),
                                8, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 8 Attack Damage").color(NamedTextColor.DARK_GREEN))).
                addModifier(Attribute.ATTACK_SPEED, new AttributeModifier(Attribute.ATTACK_SPEED.getKey(),
                                -3.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 0.5 Attack Speed").color(NamedTextColor.DARK_GREEN)))
                .build());

        FlagHelper.setFlag(item, FlagHelper.flagHammer, true);
        FlagHelper.setFlag(item, FlagHelper.flagHammerTier2, true);

        return item;
    }

    public static @NotNull ItemStack createCopperSword() {
        ItemStack item = ItemStack.of(Material.COPPER_SWORD);
        item.setData(DataComponentTypes.MAX_DAMAGE, 80);
        item.setData(DataComponentTypes.DAMAGE, 0);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Crafted Copper Sword"));

        item.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes().
                addModifier(Attribute.ATTACK_DAMAGE, new AttributeModifier(Attribute.ATTACK_DAMAGE.getKey(),
                                4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 4 Attack Damage").color(NamedTextColor.DARK_GREEN))).
                addModifier(Attribute.ATTACK_SPEED, new AttributeModifier(Attribute.ATTACK_SPEED.getKey(),
                                -2.4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 1.6 Attack Speed").color(NamedTextColor.DARK_GREEN)))
                .build());

        return item;
    }

    public static @NotNull ItemStack createForgedCopperSword() {
        ItemStack item = ItemStack.of(Material.COPPER_SWORD);
        item.setData(DataComponentTypes.MAX_DAMAGE, 120);
        item.setData(DataComponentTypes.DAMAGE, 0);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Forged Copper Sword"));

        item.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes().
                addModifier(Attribute.ATTACK_DAMAGE, new AttributeModifier(Attribute.ATTACK_DAMAGE.getKey(),
                                5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 5 Attack Damage").color(NamedTextColor.DARK_GREEN))).
                addModifier(Attribute.ATTACK_SPEED, new AttributeModifier(Attribute.ATTACK_SPEED.getKey(),
                                -2.4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 1.6 Attack Speed").color(NamedTextColor.DARK_GREEN)))
                .build());

        return item;
    }

    public static @NotNull ItemStack createHeatedCopper() {
        ItemStack item = ItemStack.of(Material.COPPER_INGOT);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Heated Copper Ingot"));

        FlagHelper.setFlag(item, FlagHelper.flagRequiresFurnaceTier2, true);

        return item;
    }

    public static @NotNull ItemStack createTwine() {
        ItemStack item = ItemStack.of(Material.PALE_HANGING_MOSS);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Twine"));

        FlagHelper.setFlag(item, FlagHelper.flagNoPlace, true);

        return item;
    }

    public static @NotNull ItemStack createFurnaceCore() {
        ItemStack item = ItemStack.of(Material.FURNACE);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Furnace Core"));
        item.setData(DataComponentTypes.LORE, ItemLore.lore().addLine(Component.text("Crafting Component").color(NamedTextColor.RED)).build());

        FlagHelper.setFlag(item, FlagHelper.flagNoPlace, true);

        return item;
    }

    public static @NotNull ItemStack createCraftingTable() {
        ItemStack item = ItemStack.of(Material.CRAFTING_TABLE);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Unfinished Crafting Table"));
        item.setData(DataComponentTypes.LORE, ItemLore.lore().addLine(Component.text("Requires carving by knife")).build());

        FlagHelper.setFlag(item, FlagHelper.flagRequiresCarving, true);

        return item;
    }

    public static @NotNull ItemStack createFurnace() {
        ItemStack item = ItemStack.of(Material.FURNACE);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Terracotta Furnace"));
        item.setData(DataComponentTypes.CUSTOM_NAME, Component.text("Terracotta Furnace").decoration(TextDecoration.ITALIC, false));

        FlagHelper.setFlag(item, FlagHelper.flagFurnaceTier1, true);
        FlagHelper.setFlag(item, FlagHelper.flagFurnace, true);

        return item;
    }

    public static @NotNull ItemStack createFurnaceCopper() {
        ItemStack item = ItemStack.of(Material.FURNACE);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Copper Plated Furnace"));
        item.setData(DataComponentTypes.CUSTOM_NAME, Component.text("Copper Plated Furnace").decoration(TextDecoration.ITALIC, false));

        FlagHelper.setFlag(item, FlagHelper.flagFurnaceTier2, true);
        FlagHelper.setFlag(item, FlagHelper.flagFurnace, true);

        return item;
    }

    public static @NotNull ItemStack createForge() {
        ItemStack item = ItemStack.of(Material.SMITHING_TABLE);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Forge"));
        item.setData(DataComponentTypes.CUSTOM_NAME, Component.text("Forge").decoration(TextDecoration.ITALIC, false));

        FlagHelper.setFlag(item, FlagHelper.flagForge, true);
        return item;
    }

    public static @NotNull ItemStack createIronBurden() {
        ItemStack item = ItemStack.of(Material.COAL_BLOCK);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Iron Burden"));

        FlagHelper.setFlag(item, FlagHelper.flagNoPlace, true);
        FlagHelper.setFlag(item, FlagHelper.flagNoBurn, true);
        FlagHelper.setFlag(item, FlagHelper.flagNoCraft, true);

        return item;
    }

    public static @NotNull ItemStack createIronBloom() {
        ItemStack item = ItemStack.of(Material.ANCIENT_DEBRIS); // Base material for ancient debris
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Iron Bloom"));

        FlagHelper.setFlag(item, FlagHelper.flagNoPlace, true);
        FlagHelper.setFlag(item, FlagHelper.flagRequiresFurnaceTier2, true);
        FlagHelper.setFlag(item, FlagHelper.flagNoBurn, true);
        FlagHelper.setFlag(item, FlagHelper.flagNoCraft, true);
        FlagHelper.setFlag(item, FlagHelper.flagNoSmelt, true);

        return item;
    }

    public static @NotNull ItemStack createWroughtIron() {
        ItemStack item = ItemStack.of(Material.IRON_INGOT);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Wrought Iron Ingot"));

        return item;
    }

    public static @NotNull ItemStack createWroughtIronPick() {
        ItemStack item = ItemStack.of(Material.IRON_PICKAXE);
        item.setData(DataComponentTypes.MAX_DAMAGE, 240);
        item.setData(DataComponentTypes.DAMAGE, 0);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Wrought Iron Pickaxe"));

        var setAll = new HashSet<>(Tag.MINEABLE_PICKAXE.getValues());
        setAll.removeAll(Tag.NEEDS_DIAMOND_TOOL.getValues());

        var mats = new ArrayList<BlockType>();
        setAll.forEach(mat -> mats.add(Registry.BLOCK.get(mat.getKey())));

        var blocks = RegistrySet.keySetFromValues(RegistryKey.BLOCK, mats);
        item.setData(DataComponentTypes.TOOL, Tool.tool().addRule(Tool.rule(blocks, 4.5f, TriState.TRUE)).build());

        item.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes().
                addModifier(Attribute.ATTACK_DAMAGE, new AttributeModifier(Attribute.ATTACK_DAMAGE.getKey(),
                                4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 4 Attack Damage").color(NamedTextColor.DARK_GREEN))).
                addModifier(Attribute.ATTACK_SPEED, new AttributeModifier(Attribute.ATTACK_SPEED.getKey(),
                                -2.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 1.5 Attack Speed").color(NamedTextColor.DARK_GREEN)))
                .build());

        FlagHelper.setFlag(item, FlagHelper.flagPickTier4, true);

        return item;
    }

    public static @NotNull ItemStack createWroughtIronAxe() {
        ItemStack item = ItemStack.of(Material.IRON_AXE);
        item.setData(DataComponentTypes.MAX_DAMAGE, 240);
        item.setData(DataComponentTypes.DAMAGE, 0);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Wrought Iron Axe"));

        var tk = TagKey.create(RegistryKey.BLOCK, Tag.MINEABLE_AXE.key());
        var blocks = Registry.BLOCK.getTag(tk);

        item.setData(DataComponentTypes.TOOL, Tool.tool().addRule(Tool.rule(blocks, 4.5f, TriState.TRUE)).build());

        item.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes().
                addModifier(Attribute.ATTACK_DAMAGE, new AttributeModifier(Attribute.ATTACK_DAMAGE.getKey(),
                                8, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 8 Attack Damage").color(NamedTextColor.DARK_GREEN))).
                addModifier(Attribute.ATTACK_SPEED, new AttributeModifier(Attribute.ATTACK_SPEED.getKey(),
                                -3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 1 Attack Speed").color(NamedTextColor.DARK_GREEN)))
                .build());

        return item;
    }

    public static @NotNull ItemStack createWroughtIronShovel() {
        ItemStack item = ItemStack.of(Material.IRON_SHOVEL);
        item.setData(DataComponentTypes.MAX_DAMAGE, 240);
        item.setData(DataComponentTypes.DAMAGE, 0);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Wrought Iron Shovel"));

        var tk = TagKey.create(RegistryKey.BLOCK, Tag.MINEABLE_SHOVEL.key());
        var blocks = Registry.BLOCK.getTag(tk);

        item.setData(DataComponentTypes.TOOL, Tool.tool().addRule(Tool.rule(blocks, 4.5f, TriState.TRUE)).build());

        item.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes().
                addModifier(Attribute.ATTACK_DAMAGE, new AttributeModifier(Attribute.ATTACK_DAMAGE.getKey(),
                                4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 4 Attack Damage").color(NamedTextColor.DARK_GREEN))).
                addModifier(Attribute.ATTACK_SPEED, new AttributeModifier(Attribute.ATTACK_SPEED.getKey(),
                                -2.8, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 1.2 Attack Speed").color(NamedTextColor.DARK_GREEN)))
                .build());

        return item;
    }

    public static @NotNull ItemStack createWroughtIronHammer() {
        ItemStack item = ItemStack.of(Material.IRON_AXE);
        item.unsetData(DataComponentTypes.TOOL);
        item.setData(DataComponentTypes.MAX_DAMAGE, 240);
        item.setData(DataComponentTypes.DAMAGE, 0);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Wrought Iron Hammer"));

        item.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes().
                addModifier(Attribute.ATTACK_DAMAGE, new AttributeModifier(Attribute.ATTACK_DAMAGE.getKey(),
                                9, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 9 Attack Damage").color(NamedTextColor.DARK_GREEN))).
                addModifier(Attribute.ATTACK_SPEED, new AttributeModifier(Attribute.ATTACK_SPEED.getKey(),
                                -3.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 0.5 Attack Speed").color(NamedTextColor.DARK_GREEN)))
                .build());

        FlagHelper.setFlag(item, FlagHelper.flagHammer, true);
        FlagHelper.setFlag(item, FlagHelper.flagHammerTier3, true);

        return item;
    }

    public static @NotNull ItemStack createWroughtIronSword() {
        ItemStack item = ItemStack.of(Material.IRON_SWORD);
        item.setData(DataComponentTypes.MAX_DAMAGE, 240);
        item.setData(DataComponentTypes.DAMAGE, 0);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Wrought Iron Sword"));

        item.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes().
                addModifier(Attribute.ATTACK_DAMAGE, new AttributeModifier(Attribute.ATTACK_DAMAGE.getKey(),
                                6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 6 Attack Damage").color(NamedTextColor.DARK_GREEN))).
                addModifier(Attribute.ATTACK_SPEED, new AttributeModifier(Attribute.ATTACK_SPEED.getKey(),
                                -2.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        AttributeModifierDisplay.override(Component.text(" 2 Attack Speed").color(NamedTextColor.DARK_GREEN)))
                .build());

        return item;
    }

    public static @NotNull ItemStack createWroughtIronBlastFurnace() {
        ItemStack item = ItemStack.of(Material.BLAST_FURNACE);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Wrought Iron Blast Furnace"));
        item.setData(DataComponentTypes.CUSTOM_NAME, Component.text("Wrought Iron Blast Furnace").decoration(TextDecoration.ITALIC, false));

        FlagHelper.setFlag(item, FlagHelper.flagFurnaceTier3, true);
        FlagHelper.setFlag(item, FlagHelper.flagFurnace, true);

        return item;
    }

    private static @NonNull ArrayList<BlockType> getStoneBreakable() {
        var setAll = new HashSet<>(Tag.MINEABLE_PICKAXE.getValues());
        setAll.removeAll(Tag.NEEDS_IRON_TOOL.getValues());
        setAll.removeAll(Tag.NEEDS_DIAMOND_TOOL.getValues());
        setAll.remove(Material.IRON_ORE);
        setAll.remove(Material.DEEPSLATE_IRON_ORE);
        setAll.remove(Material.RAW_IRON_BLOCK);
        setAll.remove(Material.IRON_BLOCK);


        var mats = new ArrayList<BlockType>();
        setAll.forEach(mat -> mats.add(Registry.BLOCK.get(mat.getKey())));
        return mats;
    }
}
