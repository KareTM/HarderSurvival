package kare.kareHardSurvival.Helpers.Granter;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

public record Granter(Collection<ItemStack> items, Consumer<Player> grant, Predicate<Player> condition) { }

