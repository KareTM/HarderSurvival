package kare.kareHardSurvival.Helpers.Granter;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class GranterBuilder {
    private final List<ItemStack> items = new ArrayList<>();
    private final List<Consumer<Player>> actions = new ArrayList<>();
    private final List<Predicate<Player>> conditions = new ArrayList<>();

    private GranterBuilder(ItemStack item) {
        this.items.add(item);
    }

    // Factory method
    public static GranterBuilder of(ItemStack item) {
        return new GranterBuilder(item);
    }

    // Grant an advancement
    public GranterBuilder grant(Advancement advancement) {
        actions.add(advancement::grant);
        return this;
    }

    // Discover one or more recipes
    public GranterBuilder discover(NamespacedKey... recipes) {
        actions.add(player -> {
            for (NamespacedKey key : recipes) {
                player.discoverRecipe(key);
            }
        });
        return this;
    }

    public GranterBuilder condition(Predicate<Player> condition) {
        conditions.add(condition);
        return this;
    }

    // Add support for collections of items
    public GranterBuilder addItems(Collection<ItemStack> additionalItems) {
        if (additionalItems != null) {
            this.items.addAll(additionalItems);
        }
        return this;
    }

    // Build the final CraftTrigger
    public Granter build() {
        return new Granter(items, player -> actions.forEach(action -> action.accept(player)), player -> conditions.stream().allMatch(condition -> condition.test(player)));
    }
}
