package kare.kareHardSurvival.Helpers.Prospecting;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class ProspectActionBar {
    private static final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("KareHardSurvival");

    /**
     * Shows the top 3 prospecting results one by one, each for 50 ticks, then stops.
     */
    public static void showTopResultsOnce(Player player, List<Prospector.ProspectResult> results) {
        if (results.isEmpty()) {
            player.sendActionBar(Component.text("No notable ore traces nearby.", NamedTextColor.GRAY));
            return;
        }

        // Only the first 3 results
        List<Prospector.ProspectResult> topResults = results.size() > 3 ? results.subList(0, 3) : results;

        for (int i = 0; i < topResults.size(); i++) {
            Prospector.ProspectResult result = topResults.get(i);

            Component message = getMessage(result);

            // Schedule each message sequentially
            int delay = i * 50; // i=0 → 0 ticks, i=1 → 50 ticks, etc.
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if (player.isOnline()) {
                    player.sendActionBar(message);
                }
            }, delay);
        }
    }

    private static @NonNull Component getMessage(Prospector.ProspectResult result) {
        NamedTextColor color = switch (result.strength()) {
            case STRONG -> NamedTextColor.RED;
            case MODERATE -> NamedTextColor.GOLD;
            case FAINT -> NamedTextColor.GRAY;
            default -> NamedTextColor.WHITE;
        };

        // Full descriptive text
        String strengthText = switch (result.strength()) {
            case STRONG -> "Strong ";
            case MODERATE -> "Moderate ";
            case FAINT -> "Faint ";
            default -> "";
        };

        String relativeHintText = switch (result.hint()) {
            case UP -> "above you.";
            case DOWN -> "below you.";
            case FORWARD -> "in front of you.";
            case BACKWARD -> "behind you.";
            case LEFT -> "to the left of you.";
            case RIGHT -> "to the right of you.";
            case AROUND -> "around you.";
        };

        String fullMessage = strengthText + result.oreGroup().name().toLowerCase() + " ore traces " + relativeHintText;

        return Component.text(fullMessage, color);
    }
}
