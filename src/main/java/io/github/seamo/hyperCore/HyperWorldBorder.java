package io.github.seamo.hyperCore;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class HyperWorldBorder implements Listener {
    private final JavaPlugin plugin;

    public HyperWorldBorder(JavaPlugin plugin, FileConfiguration config) {
        this.plugin = plugin;
    }

    public void startBorderMovement() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    WorldBorder worldBorder = player.getWorld().getWorldBorder();
                    Location playerLocation = player.getLocation();
                    @NotNull Vector direction = playerLocation.getDirection().multiply(0.01);
                    Location newCenter = worldBorder.getCenter().add(direction);

                    worldBorder.setCenter(newCenter);
                }
            }
        }.runTaskTimer(plugin, 0L, 1L); // Run every tick (0.05 seconds)
    }
}