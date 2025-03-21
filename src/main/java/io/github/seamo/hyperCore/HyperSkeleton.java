package io.github.seamo.hyperCore;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class HyperSkeleton implements Listener {
    private final double arrowTick;
    private final double health;
    private final int arrowNum;

    public HyperSkeleton(FileConfiguration config) {
        this.arrowTick = config.getDouble("Skeleton.skeleton-arrow-tick");
        this.health = config.getDouble("Skeleton.attri-health");
        this.arrowNum = config.getInt("Skeleton.skeleton-arrow-num");
    }

    @EventHandler
    public void onSkeletonTarget(EntityTargetEvent event) {
        if (event.getEntityType() == EntityType.SKELETON && event.getTarget() instanceof LivingEntity) {
            Skeleton skeleton = (Skeleton) event.getEntity();
            LivingEntity target = (LivingEntity) event.getTarget();
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (skeleton.isDead() || target.isDead() || skeleton.getTarget() != target) {
                        this.cancel();
                        return;
                    }

                    Location eyeLocation = skeleton.getEyeLocation();
                    Location targetLocation = target.getLocation().add(0, target.getHeight() / 2, 0);
                    for (int i = 0; i < arrowNum; i++) {
                        Arrow arrow = skeleton.launchProjectile(Arrow.class);
                        arrow.setVelocity(targetLocation.toVector().subtract(eyeLocation.toVector()).normalize().multiply(2));
                    }
                }
            }.runTaskTimer((Plugin) JavaPlugin.getPlugin(HyperCore.class), 0L, (long) arrowTick); // 1 tick = 0.05 seconds, 20 ticks = 1 second
        }
    }

    @EventHandler
    public void onSkeletonShoot(EntityShootBowEvent event) {
        if (event.getEntityType() == EntityType.SKELETON) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onSkeletonSpawn(EntitySpawnEvent event) {
        if (event.getEntityType() == EntityType.SKELETON) {
            Skeleton skeleton = (Skeleton) event.getEntity();
            skeleton.getAttribute(Attribute.MAX_HEALTH).setBaseValue(health);
            skeleton.setHealth(health);
        }
    }
}