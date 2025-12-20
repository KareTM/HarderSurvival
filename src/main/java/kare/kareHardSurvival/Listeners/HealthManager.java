package kare.kareHardSurvival.Listeners;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;


public class HealthManager implements Listener {
    static final int BASE_HEALTH = 10;
    static final int MAX_HEALTH = 30;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        var pMaxHealth = p.getAttribute(Attribute.MAX_HEALTH);
        if (pMaxHealth == null)
            return;

        var newHealth = BASE_HEALTH + p.getLevel() / 2;
        pMaxHealth.setBaseValue(Math.min(newHealth, MAX_HEALTH));
    }

    @EventHandler
    public void onPlayerLevel(PlayerLevelChangeEvent e) {
        Player p = e.getPlayer();
        var pMaxHealth = p.getAttribute(Attribute.MAX_HEALTH);
        if (pMaxHealth == null)
            return;

        var newHealth = BASE_HEALTH + e.getNewLevel() / 2;
        pMaxHealth.setBaseValue(Math.min(newHealth, MAX_HEALTH));
    }
}
