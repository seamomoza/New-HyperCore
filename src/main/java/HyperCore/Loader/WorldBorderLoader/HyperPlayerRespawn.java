package HyperCore.Loader.WorldBorderLoader;

import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class HyperPlayerRespawn implements Listener {
    private final JavaPlugin plugin;

    public HyperPlayerRespawn(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Location respawnLocation = event.getRespawnLocation();
        WorldBorder worldBorder = respawnLocation.getWorld().getWorldBorder();
        worldBorder.setCenter(respawnLocation);
    }
}