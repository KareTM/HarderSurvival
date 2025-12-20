package kare.kareHardSurvival.Listeners;

import com.fren_gor.ultimateAdvancementAPI.events.PlayerLoadingCompletedEvent;
import kare.kareHardSurvival.Advancements.AdvancementManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class JoinManager implements Listener {

    @EventHandler
    public void onJoin(PlayerLoadingCompletedEvent e) {
        // Called after a player has successfully been loaded by the API
        Player p = e.getPlayer();
        // Here you can show tabs to players
        AdvancementManager.getTab().showTab(p);
        AdvancementManager.Root.grant(p);
    }
}
