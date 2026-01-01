package kare.kareHardSurvival.Helpers.Commands;

import kare.kareHardSurvival.Items.ItemRegistry;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CommandManager implements CommandExecutor {

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            String[] args
    ) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Players only.");
            return true;
        }

        ItemStack hand = player.getInventory().getItemInMainHand();

        if (hand.isEmpty()) {
            player.sendMessage(Component.text("You are not holding an item."));
            return true;
        }

        ItemStack upgraded = ItemRegistry.tryUpgrade(hand);

        if (upgraded.equals(hand)) {
            player.sendMessage(Component.text("That item is already up to date or cannot be migrated."));
            return true;
        }

        player.getInventory().setItemInMainHand(upgraded);
        player.sendMessage(Component.text("Item migrated successfully."));

        return true;
    }
}

