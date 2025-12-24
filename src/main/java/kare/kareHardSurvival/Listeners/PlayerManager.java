package kare.kareHardSurvival.Listeners;

import com.fren_gor.ultimateAdvancementAPI.events.PlayerLoadingCompletedEvent;
import kare.kareHardSurvival.Advancements.AdvancementManager;
import kare.kareHardSurvival.Helpers.RecipeKeyList;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class PlayerManager implements Listener {

    @EventHandler
    public void onJoin(PlayerLoadingCompletedEvent e) {
        // Called after a player has successfully been loaded by the API
        Player p = e.getPlayer();
        // Here you can show tabs to players
        AdvancementManager.getTab().showTab(p);
        AdvancementManager.Root.grant(p);
    }

    @EventHandler
    public void onDimensionChange(PlayerChangedWorldEvent e) {
        var p = e.getPlayer();
        if (p.getWorld().getKey().equals(NamespacedKey.minecraft("the_nether"))) {
            AdvancementManager.Nether.grant(p);
            p.discoverRecipe(RecipeKeyList.ovenPadding);
        }
    }
}
