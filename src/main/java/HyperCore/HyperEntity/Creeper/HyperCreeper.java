package HyperCore.HyperEntity.Creeper;

import HyperCore.Loader.ConfigLoader.ConfigManager;
import HyperCore.Loader.HyperCoreLoader.HyperCore;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import static java.lang.Math.*;

public class HyperCreeper implements Listener {
    private final double explosionRadiusPerTick;
    private final double explosionPointPerCircum;
    private final int explosionDurationTicks;
    private final float explosionPower;

    private int explosionTicks = 0;
    private Location explosionLocation = null;

    public HyperCreeper(JavaPlugin plugin) {
        ConfigManager configManager = new ConfigManager(plugin, "Creeper.yml");
        FileConfiguration config = configManager.getConfig();

        this.explosionRadiusPerTick = config.getDouble("explosion-radius-per-tick");
        this.explosionPointPerCircum = config.getDouble("explosion-point-per-circum");
        this.explosionDurationTicks = config.getInt("explosion-duration-ticks");
        this.explosionPower = (float) config.getDouble("explosion-power");
    }
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
                        double radius = explosionRadiusPerTick * ticks;
                        double circum = 2.0 * Math.PI * radius;
                        int pointsCount = (int) (circum / explosionPointPerCircum);
                        double angle = 360.0 / pointsCount;

                        for (int i = 0; i < pointsCount; i++) {
                            double currentAngle = toRadians(i * angle);
                            double x = -sin(currentAngle);
                            double z = cos(currentAngle);
                            center.getWorld().createExplosion(center.getX() + x * radius, center.getY(), center.getZ() + z * radius, explosionPower, false, true);
                        }

                        if (ticks >= explosionDurationTicks) {
                            explosionTicks = 0;
                            explosionLocation = null;
                            this.cancel();
                        }
                    }
                }
            }.runTaskTimer(JavaPlugin.getPlugin(HyperCore.class), 0, 1);
        }
    }

    @EventHandler
    public void onSkeletonHit(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Creeper creeper)) return;
        if (!(event.getDamager() instanceof Projectile projectile)) return;

        if (!(projectile.getShooter() instanceof LivingEntity shooter)) return;

        // 스켈레톤을 shooter 위치로 TP
        Location shooterLoc = shooter.getLocation();
        creeper.teleport(shooterLoc);
    }

    @EventHandler
    public void onCreeperSpawn(EntitySpawnEvent event) {
        if (event.getEntityType() == EntityType.CREEPER) {
            Creeper creeper = (Creeper) event.getEntity();
            creeper.getAttribute(Attribute.MAX_HEALTH).setBaseValue(1000);
            creeper.getAttribute(Attribute.KNOCKBACK_RESISTANCE).setBaseValue(1);
            creeper.heal(1000);
        }
    }
}