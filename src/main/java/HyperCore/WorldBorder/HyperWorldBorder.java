package HyperCore.WorldBorder;

import HyperCore.Loader.ConfigLoader.ConfigManager;
import HyperCore.Loader.HyperCoreLoader.HyperCore;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class HyperWorldBorder implements Listener {
    private final Set<Player> players = new HashSet<>();
    private final double worldSpeed;

    public HyperWorldBorder(JavaPlugin plugin) {
        ConfigManager configManager = new ConfigManager(plugin, "WorldBorder.yml");
        FileConfiguration config = configManager.getConfig();
        this.worldSpeed = config.getDouble("speed");
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void startBorderMovement() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : players) {
                    WorldBorder worldBorder = player.getWorld().getWorldBorder();
                    Location playerLocation = player.getLocation();
                    @NotNull Vector direction = playerLocation.getDirection().multiply(worldSpeed);
                    Location newCenter = worldBorder.getCenter().add(direction);

                    worldBorder.setCenter(newCenter);
                    worldBorder.setSize(10); // Set the world border size to 10
                }
            }
        }.runTaskTimer(JavaPlugin.getPlugin(HyperCore.class), 0L, 1L); // Run every tick (0.05 seconds)
    }
}