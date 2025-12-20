package kare.kareHardSurvival.GUI;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import kare.kareHardSurvival.Helpers.ForgeRecipes;
import kare.kareHardSurvival.Items.ItemEditor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class RecipeSelectGUI {

    private final ChestGui gui;
    private final PaginatedPane pages;
    private StaticPane nav;

    private static final int WIDTH = 7;
    private static final int HEIGHT = 4;

    public RecipeSelectGUI(
            Player player,
            NamespacedKey selected,
            Consumer<NamespacedKey> onSelect
    ) {
        gui = new ChestGui(6, "Select Recipe");
        OutlinePane bg = new OutlinePane(0, 0, 9, 6);
        var pane = ItemStack.of(Material.GRAY_STAINED_GLASS_PANE);
        var meta = pane.getItemMeta();
        meta.displayName(Component.text(""));
        pane.setItemMeta(meta);

        bg.addItem(new GuiItem(pane, e -> e.setCancelled(true)));
        bg.setRepeat(true);
        bg.setPriority(Pane.Priority.LOWEST);
        gui.addPane(bg);

        pages = new PaginatedPane(1, 1, WIDTH, HEIGHT);
        gui.addPane(pages);

        buildPages(player, onSelect);

        gui.addPane(buildNavPane(player));
    }

    private void buildPages(Player player, Consumer<NamespacedKey> onSelect) {
        List<ForgeRecipes.ForgeRecipe> recipes = ForgeRecipes.getAllRecipes();
        Iterator<ForgeRecipes.ForgeRecipe> it = recipes.iterator();

        int pageNum = 0;
        while (it.hasNext()) {
            OutlinePane page = new OutlinePane(0, 0, WIDTH, HEIGHT);

            for (int i = 0; i < WIDTH * HEIGHT && it.hasNext(); i++) {
                ForgeRecipes.ForgeRecipe recipe = it.next();

                ItemStack icon = ItemEditor.editMeta(recipe.output.clone(), meta -> {
                    meta.itemName(
                            recipe.name
                    );
                    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    ForgeRecipes.recipeLore(meta, recipe);
                });

                page.addItem(new GuiItem(icon, e -> {
                    e.setCancelled(true);
                    onSelect.accept(recipe.key);
                    player.closeInventory();
                }));
            }

            pages.addPane(pageNum, page);
            pageNum++;
        }
    }

    private StaticPane buildNavPane(Player p) {
        nav = new StaticPane(0, 5, 9, 1);
        redrawNav(p);
        return nav;
    }


    private void redrawNav(Player p) {
        nav.clear();

        boolean hasPrev = pages.getPage() > 0;
        boolean hasNext = pages.getPage() + 1 < pages.getPages();

        // Previous
        nav.addItem(new GuiItem(
                ItemEditor.editMeta(
                        ItemStack.of(hasPrev ? Material.ARROW : Material.GRAY_STAINED_GLASS_PANE),
                        m -> m.itemName(Component.text("Previous"))
                ),
                e -> {
                    e.setCancelled(true);
                    if (!hasPrev) return;

                    pages.setPage(pages.getPage() - 1);
                    redrawNav(p);
                    gui.update();
                }
        ), 0, 0);

        // Cancel
        nav.addItem(new GuiItem(
                ItemEditor.editMeta(ItemStack.of(Material.BARRIER),
                        m -> m.itemName(Component.text("Cancel"))),
                e -> {
                    e.setCancelled(true);
                    p.closeInventory();
                }
        ), 4, 0);

        // Next
        nav.addItem(new GuiItem(
                ItemEditor.editMeta(
                        ItemStack.of(hasNext ? Material.ARROW : Material.GRAY_STAINED_GLASS_PANE),
                        m -> m.itemName(Component.text("Next"))
                ),
                e -> {
                    e.setCancelled(true);
                    if (!hasNext) return;

                    pages.setPage(pages.getPage() + 1);
                    redrawNav(p);
                    gui.update();
                }
        ), 8, 0);
    }

    public void open(Player player) {
        gui.show(player);
    }
}
