
package io.github.seamo.hyperCore;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;

public class HyperEntityProtection implements Listener {
    private final boolean protectZombie;
    private final boolean protectSkeleton;

    public HyperEntityProtection(FileConfiguration config) {
        this.protectZombie = config.getBoolean("entity-protection.protect-zombie");
        this.protectSkeleton = config.getBoolean("entity-protection.protect-skeleton");
    }

    @EventHandler
    public void onEntityCombust(EntityCombustEvent event) {
        if ((protectZombie && event.getEntityType() == EntityType.ZOMBIE)) {
            event.setCancelled(protectZombie);
        }
        if ((protectSkeleton && event.getEntityType() == EntityType.SKELETON)) {
            event.setCancelled(protectSkeleton);

        }
    }
}