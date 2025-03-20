package io.github.seamo.hyperCore;

import org.bukkit.Location;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import static java.lang.Math.*;

public class HyperCreeper implements Listener {

    private int explosionTicks = 0;
    private Location explosionLocation = null;

    @EventHandler
    public void onCreeperExplode(EntityExplodeEvent event) {
        if (event.getEntityType() == EntityType.CREEPER) {
            event.setCancelled(true);
            Creeper creeper = (Creeper) event.getEntity();
            explosionLocation = creeper.getLocation();

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (explosionLocation != null) {
                        Location center = explosionLocation;
                        int ticks = ++explosionTicks;
                        double radiusPerTick = 0.5;
                        double pointPerCircum = 6.0;
                        double radius = radiusPerTick * ticks;
                        double circum = 2.0 * Math.PI * radius;
                        int pointsCount = (int) (circum / pointPerCircum);
                        double angle = 360.0 / pointsCount;

                        for (int i = 0; i < pointsCount; i++) {
                            double currentAngle = toRadians(i * angle);
                            double x = -sin(currentAngle);
                            double z = cos(currentAngle);
                            center.getWorld().createExplosion(center.getX() + x * radius, center.getY(), center.getZ() + z * radius, 4.0F, false, true);
                        }

                        if (ticks >= 100) {
                            explosionTicks = 0;
                            explosionLocation = null;
                            this.cancel();
                        }
                    }
                }
            }.runTaskTimer(JavaPlugin.getPlugin(HyperCore.class), 0, 1);
        }
    }
}