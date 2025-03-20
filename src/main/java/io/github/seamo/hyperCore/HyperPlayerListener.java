package io.github.seamo.hyperCore;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class HyperPlayerListener implements Listener {
    private final HyperWorldBorder hyperWorldBorder;

    public HyperPlayerListener(JavaPlugin plugin) {
        this.hyperWorldBorder = new HyperWorldBorder(plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        hyperWorldBorder.setWorldBorder(event.getPlayer());
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        hyperWorldBorder.setWorldBorder(event.getPlayer(), event.getRespawnLocation());
    }
}