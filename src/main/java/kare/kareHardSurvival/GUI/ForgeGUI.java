package kare.kareHardSurvival.GUI;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import io.papermc.paper.datacomponent.DataComponentTypes;
import kare.kareHardSurvival.Helpers.*;
import kare.kareHardSurvival.Items.ItemEditor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ForgeGUI {
    private final ChestGui gui;
    private final Block block;
    private final ForgeData data;
    private final Plugin plugin = Bukkit.getPluginManager().getPlugin("KareHardSurvival");

    private Runnable recipeCallback;

    private static final Map<Block, ForgeGUI> existing = new HashMap<>();

    public static ForgeGUI getGUI(Block block) {
        if (existing.containsKey(block)) {
            return existing.get(block);
        }
        return new ForgeGUI(block);
    }

    public static void removeGUI(Block block) {
        existing.remove(block);
    }

    private ForgeGUI(Block block) {
        this.block = block;

        this.data = ForgeStorage.load(block, plugin);
        gui = new ChestGui(4, "Forge");

        //gui.setOnTopClick(e -> e.setCancelled(true)); // default block
        gui.setOnBottomClick(e -> {
            if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) e.setCancelled(true);
        });

        buildGui();

        existing.put(block, this);
    }

    private void buildGui() {
        assert plugin != null;
        // Background
        OutlinePane bg = new OutlinePane(0, 0, 9, 4);
        var pane = ItemStack.of(Material.GRAY_STAINED_GLASS_PANE);
        var meta = pane.getItemMeta();
        meta.itemName(Component.text(""));
        pane.setItemMeta(meta);

        bg.addItem(new GuiItem(pane, e -> e.setCancelled(true)));
        bg.setRepeat(true);
        bg.setPriority(Pane.Priority.LOWEST);
        gui.addPane(bg);

        // Hammer Slot
        var hammerWrapper = new ItemWrapper(data.hammer);
        var hammerSlot = slotItem(1, 0,
                hammerWrapper,
                item -> {
                    if (FlagHelper.hasFlag(item, FlagHelper.flagHammer) || item.isEmpty()) {
                        if (!item.isEmpty()) {
                            var metaItem = item.getItemMeta();
                            metaItem.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                            metaItem.lore(List.of(Component.text(""), Component.text("Reduces Needed Actions By: ").decoration(TextDecoration.ITALIC, false).
                                    append(Component.text(ForgeRecipes.getReduction(item) + "%").color(NamedTextColor.RED))));
                            item.setItemMeta(metaItem);
                        }
                        data.hammer = item;
                        hammerWrapper.item = data.hammer;
                        save();
                        recipeCallback.run();
                        update();

                        return true;
                    }
                    return false;
                },
                ItemEditor.editMeta(ItemStack.of(Material.LIME_STAINED_GLASS_PANE),
                        itemMeta -> itemMeta.itemName(Component.text("Insert Hammer"))));
        gui.addPane(hammerSlot.pane);

        var wrapperMat1 = new ItemWrapper(data.material1);
        var matSlot1 = slotItem(3, 0,
                wrapperMat1,
                item -> {
                    data.material1 = item;
                    wrapperMat1.item = data.material1;
                    save();
                    return true;
                },
                ItemEditor.editMeta(ItemStack.of(Material.LIME_STAINED_GLASS_PANE),
                        itemMeta -> itemMeta.itemName(Component.text("Insert Materials"))));
        // Material 1
        gui.addPane(matSlot1.pane);

        var wrapperMat2 = new ItemWrapper(data.material2);
        var matSlot2 = slotItem(4, 0,
                wrapperMat2,
                item -> {
                    data.material2 = item;
                    wrapperMat2.item = data.material2;
                    save();
                    return true;
                },
                ItemEditor.editMeta(ItemStack.of(Material.LIME_STAINED_GLASS_PANE),
                        itemMeta -> itemMeta.itemName(Component.text("Insert Materials"))));
        // Material 2
        gui.addPane(matSlot2.pane);

        var forgeMinigame = new StaticPane(4, 2, 1, 1) {
            private List<ForgeAction> sequence;
            private int index = 0;
            private boolean playing = false;
            public boolean lockIn = false;

            {
                showStartButton();
            }

            private void showStartButton() {
                removeItem(0, 0);
                addItem(new GuiItem(
                        ItemEditor.editMeta(ItemStack.of(Material.ANVIL),
                                m -> {
                                    m.itemName(Component.text("Start Forging"));
                                    m.lore(List.of(Component.text("Shift click to skip action sequence display")));
                                }
                        ),
                        this::startClick
                ), 0, 0);
            }

            private void startClick(InventoryClickEvent e) {
                e.setCancelled(true);

                if (!canStart()) {
                    showTempMessage("Missing materials or hammer!", Material.BARRIER, t -> {
                        showStartButton();
                        update();
                    });
                    return;
                }

                lockIn = true;
                generateSequence();
                index = 0;
                playing = false;

                if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                    startPlayerInput();
                } else {
                    showSequence(0);
                }
            }

            private boolean canStart() {
                if (data.hammer == null) return false;

                var recipe = ForgeRecipes.getRecipe(data.selectedRecipe);
                if (recipe == null) return false;

                for (var req : recipe.itemsRequirement) {
                    if (data.material1 != null && req.asOne().equals(InventoryHelpers.stripIFUUID(data.material1.asOne()))) {
                        if (data.material1.getAmount() < req.getAmount()) {
                            return false;
                        }
                    } else if (data.material2 != null && req.asOne().equals(InventoryHelpers.stripIFUUID(data.material2.asOne()))) {
                        if (data.material2.getAmount() < req.getAmount()) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
                return true;
            }

            // -----------------------------------
            // Sequence generation
            // -----------------------------------
            private void generateSequence() {
                var recipe = ForgeRecipes.getRecipe(data.selectedRecipe);

                long seed = recipe.key.hashCode() + (recipe.actionCount * 31L);
                Random r = new Random(seed);

                sequence = new ArrayList<>();
                var vals = ForgeAction.values();

                int usedCount = Math.max((int) Math.round((100.0 - ForgeRecipes.getReduction(data.hammer)) * recipe.actionCount / 100.0), 1);
                for (int i = 0; i < usedCount; i++)
                    sequence.add(vals[r.nextInt(vals.length)]);
            }

            // -----------------------------------
            // Showing sequence (animation)
            // -----------------------------------
            private void showSequence(int step) {
                if (step >= sequence.size()) {
                    startPlayerInput();
                    return;
                }

                removeItem(0, 0);
                showTempMessage("", Material.GRAY_STAINED_GLASS_PANE, t -> {
                    removeItem(0, 0);
                    ForgeAction action = sequence.get(step);

                    ItemStack display =
                            ItemEditor.editMeta(itemForAction(action).clone(),
                                    m -> {
                                        m.itemName(Component.text("Watch: " + action.name()));
                                        m.setRarity(ItemRarity.COMMON);
                                    });

                    addItem(new GuiItem(display, e -> e.setCancelled(true)), 0, 0);
                    update();
                }, 1);

                Bukkit.getScheduler().runTaskLater(plugin, t ->
                        showSequence(step + 1), 20);
            }

            // -----------------------------------
            // Begin listening for user input
            // -----------------------------------
            private void startPlayerInput() {
                playing = true;
                index = 0;

                removeItem(0, 0);
                addItem(new GuiItem(
                        ItemEditor.editMeta(ItemStack.of(Material.YELLOW_STAINED_GLASS_PANE),
                                m -> m.itemName(Component.text("Your turn!"))),
                        e -> e.setCancelled(true)
                ), 0, 0);

                update();
            }

            // -----------------------------------
            // Called externally by bottom buttons
            // -----------------------------------
            public void onActionButtonPressed(ForgeAction clicked) {
                if (!playing) return;
                var player = (Player) gui.getViewers().getFirst();

                if (data.hammer == null) {
                    showTempMessage("Hammer Missing!", Material.BARRIER, t -> {
                        showStartButton();
                        update();
                    });
                    playing = lockIn = false;
                    return;
                }

                var recipe = ForgeRecipes.getRecipe(data.selectedRecipe);
                var mat1Miss = data.material1 == null || data.material1.isEmpty();
                var mat2Miss = data.material2 == null || data.material2.isEmpty();
                if (recipe.itemsRequirement.size() == 2) {
                    if (mat1Miss || mat2Miss) {
                        showTempMessage("Material Missing!", Material.BARRIER, t -> {
                            showStartButton();
                            update();
                        });
                        playing = lockIn = false;
                        return;
                    }
                } else {
                    if (mat1Miss && mat2Miss) {
                        showTempMessage("Material Missing!", Material.BARRIER, t -> {
                            showStartButton();
                            update();
                        });
                        playing = lockIn = false;
                        return;
                    }
                }

                InventoryHelpers.damageToolConsiderUnbreaking(player, data.hammer);
                if (data.hammer.isEmpty()) {
                    data.hammer = null;
                    save();
                    hammerSlot.checkEmpty().run();
                    update();
                }

                ForgeAction needed = sequence.get(index);
                if (clicked != needed) {
                    playing = lockIn = false;
                    showTempMessage("Wrong Action!", Material.BARRIER, t -> {
                        showStartButton();
                        update();
                    });
                    return;
                }

                showTempMessage("Good!", Material.LIME_STAINED_GLASS_PANE, t -> {
                    removeItem(0, 0);
                    addItem(new GuiItem(
                            ItemEditor.editMeta(ItemStack.of(Material.YELLOW_STAINED_GLASS_PANE),
                                    m -> m.itemName(Component.text("Your turn!"))),
                            e -> e.setCancelled(true)
                    ), 0, 0);
                    update();
                });
                index++;

                if (index >= sequence.size()) {
                    playing = lockIn = false;
                    showTempMessage("Success!", Material.EMERALD, t -> {
                        showStartButton();
                        update();
                    });

                    for (var req : recipe.itemsRequirement) {
                        if (data.material1 != null && req.asOne().equals(InventoryHelpers.stripIFUUID(data.material1.asOne()))) {
                            data.material1.add(-req.getAmount());
                            if (data.material1.isEmpty()) matSlot1.checkEmpty.run();
                        } else if (data.material2 != null && req.asOne().equals(InventoryHelpers.stripIFUUID(data.material2.asOne()))) {
                            data.material2.add(-req.getAmount());
                            if (data.material2.isEmpty()) matSlot2.checkEmpty.run();
                        }
                    }

                    update();

                    block.getWorld().dropItemNaturally(block.getLocation().add(0, 1, 0).toCenterLocation(), recipe.output);
                    gui.getViewers().getFirst().closeInventory();
                    for (var rule : ForgeRecipes.rules) {
                        if (rule.items().contains(recipe.output))
                            rule.grant().accept(player);
                    }

                    save();

                    Bukkit.getScheduler().runTaskLater(plugin, r -> {
                        showStartButton();
                        update();
                    }, 20);
                }
            }


            // -----------------------------------
            // Utility: show temporary message
            // -----------------------------------
            private void showTempMessage(String msg, Material mat, Consumer<BukkitTask> showAfter, int... ticks) {
                removeItem(0, 0);
                addItem(new GuiItem(
                        ItemEditor.editMeta(ItemStack.of(mat),
                                m -> m.itemName(Component.text(msg))
                        ),
                        e -> e.setCancelled(true)
                ), 0, 0);
                update();

                if (ticks.length > 0) {
                    Bukkit.getScheduler().runTaskLater(plugin, showAfter, ticks[0]);
                } else {
                    Bukkit.getScheduler().runTaskLater(plugin, showAfter, 20);
                }
            }

        };
        gui.addPane(forgeMinigame);

        var recipePane = new StaticPane(6, 0, 1, 1) {
            {
                addItem(
                        new GuiItem(createRecipeSelectItem(), this::recipeSelect), 0, 0
                );

                recipeCallback = () -> {
                    this.removeItem(0, 0);
                    this.addItem(new GuiItem(createRecipeSelectItem(), this::recipeSelect), 0, 0);
                    update();
                };
            }

            private void recipeSelect(InventoryClickEvent e) {
                e.setCancelled(true);
                if (forgeMinigame.lockIn)
                    return;

                Player p = (Player) e.getWhoClicked();

                new RecipeSelectGUI(
                        p,
                        key -> {
                            data.selectedRecipe = key;
                            save();

                            removeItem(0, 0);
                            addItem(new GuiItem(createRecipeSelectItem(), this::recipeSelect), 0, 0);
                            update();

                            Bukkit.getScheduler().runTask(plugin, () -> gui.show(p));
                        }
                ).open(p);
            }
        };
        // Recipe select button
        gui.addPane(recipePane);


        gui.addPane(new StaticPane(2, 3, 5, 1) {
            {
                addItem(
                        new GuiItem(hammerActionItem, e -> {
                            e.setCancelled(true);
                            forgeMinigame.onActionButtonPressed(ForgeAction.HAMMER);
                        }), 0, 0
                );
                addItem(
                        new GuiItem(punchActionItem, e -> {
                            e.setCancelled(true);
                            forgeMinigame.onActionButtonPressed(ForgeAction.PUNCH);
                        }), 1, 0
                );

                addItem(
                        new GuiItem(tightenActionItem, e -> {
                            e.setCancelled(true);
                            forgeMinigame.onActionButtonPressed(ForgeAction.TIGHTEN);
                        }), 2, 0
                );

                addItem(
                        new GuiItem(bendActionItem, e -> {
                            e.setCancelled(true);
                            forgeMinigame.onActionButtonPressed(ForgeAction.BEND);
                        }), 3, 0
                );

                addItem(
                        new GuiItem(polishActionItem, e -> {
                            e.setCancelled(true);
                            forgeMinigame.onActionButtonPressed(ForgeAction.POLISH);
                        }), 4, 0
                );
            }
        });
    }

    private record SlotController(StaticPane pane, Runnable checkEmpty) {
    }

    private static class ItemWrapper {
        ItemStack item;

        ItemWrapper(ItemStack item) {
            this.item = item;
        }
    }

    /**
     * Small helper: creates a pane with a single editable slot.
     */
    private SlotController slotItem(int x, int y, ItemWrapper initial, Predicate<ItemStack> callback, ItemStack emptyState) {
        var pane = new StaticPane(x, y, 1, 1) {
            {
                addItem(new GuiItem(initial.item == null ? emptyState : initial.item, this::handleClick), 0, 0);
            }

            private void handleClick(InventoryClickEvent e) {
                e.setCancelled(true);

                ItemStack cursor = e.getCursor();
                ItemStack current = e.getCurrentItem();
                boolean isEmptySlot = current == null || current.isEmpty() || current.equals(emptyState);

                // 1. Cursor has an item → place it into slot
                if (!cursor.isEmpty()) {
                    ItemStack newCursor = isEmptySlot ? ItemStack.empty() : InventoryHelpers.stripIFUUID(initial.item);
                    if (!callback.test(cursor))
                        return;

                    e.getView().setCursor(newCursor);

                    // REPLACE GUI ITEM PROPERLY
                    replaceSlotItem(cursor);
                    return;
                }

                // 2. Cursor empty → pick up slot item (if not optional)
                if (!isEmptySlot) {
                    if (initial.item != data.hammer)
                        e.getView().setCursor(InventoryHelpers.stripIFUUID(initial.item));
                    else {
                        var hammer = InventoryHelpers.getFreshHammer(data.hammer);
                        if (hammer == null)
                            e.getView().setCursor(data.hammer);
                        else
                            e.getView().setCursor(hammer.damage(data.hammer.getData(DataComponentTypes.DAMAGE), e.getWhoClicked()));
                    }
                    callback.test(ItemStack.empty());

                    replaceSlotItem(emptyState);
                }
            }

            private void replaceSlotItem(ItemStack newItem) {
                // Remove the existing GuiItem
                removeItem(0, 0);

                // Add a new GuiItem WITH the handler attached again
                addItem(new GuiItem(newItem, this::handleClick), 0, 0);

                update();
            }

            public void checkEmpty() {
                var slot = Slot.fromIndex(0);
                var item = getItem(slot);
                if (item == null || item.getItem().isEmpty()) {
                    replaceSlotItem(emptyState);
                }
                assert item != null;
            }
        };


        pane.setOnClick(
                e -> {
                    pane.checkEmpty();
                    if (emptyState.isEmpty()) {
                        e.setCancelled(false);
                        callback.test(e.getCursor());
                    }
                }
        );

        return new SlotController(pane, pane::checkEmpty);
    }

    private ItemStack createRecipeSelectItem() {
        var recipe = ForgeRecipes.getRecipe(data.selectedRecipe);
        if (data.selectedRecipe == null || recipe == null) {
            cycleRecipe();
            recipe = ForgeRecipes.getRecipe(data.selectedRecipe);
        }


        ForgeRecipes.ForgeRecipe finalRecipe = recipe;
        return ItemEditor.editMeta(recipe.output.clone(), meta ->
        {
            var name = (TextComponent) finalRecipe.name;
            meta.itemName(Component.text("Selected Recipe - " + name.content()));
            ForgeRecipes.recipeLore(meta, finalRecipe, ForgeRecipes.getReduction(data.hammer));
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        });
    }

    private void cycleRecipe() {
        data.selectedRecipe = ForgeRecipes.getNextRecipeKey(data.selectedRecipe);
        save();
    }

    private void update() {
        gui.update();
    }

    private void save() {
        ForgeStorage.save(block, data, plugin);
    }

    public void open(Player p) {
        gui.show(p);
    }

    public static class ForgeData {
        public ItemStack hammer;
        public ItemStack material1;
        public ItemStack material2;
        public NamespacedKey selectedRecipe; // namespaced key
    }

    private final ItemStack hammerActionItem = ItemEditor.editMeta(ItemStack.of(Material.MACE),
            meta -> {
                meta.itemName(Component.text("Hammer"));
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                meta.setRarity(ItemRarity.COMMON);
            });
    private final ItemStack punchActionItem = ItemEditor.editMeta(ItemStack.of(Material.POINTED_DRIPSTONE),
            meta -> meta.itemName(Component.text("Punch")));
    private final ItemStack tightenActionItem = ItemEditor.editMeta(ItemStack.of(Material.SHEARS),
            meta -> meta.itemName(Component.text("Tighten")));
    private final ItemStack bendActionItem = ItemEditor.editDataComponent(ItemEditor.editMeta(ItemStack.of(Material.GOAT_HORN),
                    meta -> {
                        meta.itemName(Component.text("Bend"));
                        meta.setRarity(ItemRarity.COMMON);
                    }),
            item -> item.unsetData(DataComponentTypes.INSTRUMENT));
    private final ItemStack polishActionItem = ItemEditor.editMeta(ItemStack.of(Material.PAPER),
            meta -> meta.itemName(Component.text("Polish")));

    private enum ForgeAction {
        HAMMER,
        PUNCH,
        TIGHTEN,
        BEND,
        POLISH
    }

    private ItemStack itemForAction(ForgeAction action) {
        return switch (action) {
            case HAMMER -> hammerActionItem;
            case PUNCH -> punchActionItem;
            case TIGHTEN -> tightenActionItem;
            case BEND -> bendActionItem;
            case POLISH -> polishActionItem;
        };
    }
}
