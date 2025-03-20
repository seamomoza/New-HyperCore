package io.github.seamo.hyperCore;

import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class HyperWorldBorder {
    private final JavaPlugin plugin;

    public HyperWorldBorder(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void setWorldBorder(Player player) {
        setWorldBorder(player, player.getLocation());
    }

    public void setWorldBorder(Player player, Location location) {
        WorldBorder worldBorder = player.getWorld().getWorldBorder();
        worldBorder.setCenter(location);
        worldBorder.setSize(10); // 5 block radius

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline()) {
                    this.cancel();
                    return;
                }

                Location currentLocation = worldBorder.getCenter();
                Location newCenter = currentLocation.add(player.getLocation().getDirection().normalize().multiply(0.01)); // Adjust the multiplier as needed
                worldBorder.setCenter(newCenter);
            }
        }.runTaskTimer(plugin, 0, 1); // 1 tick = 0.05 seconds
    }
}