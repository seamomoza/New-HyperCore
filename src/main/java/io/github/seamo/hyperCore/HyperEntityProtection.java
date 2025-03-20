package io.github.seamo.hyperCore;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;

public class HyperEntityProtection implements Listener {

    @EventHandler
    public void onEntityCombust(EntityCombustEvent event) {
        if (event.getEntityType() == EntityType.ZOMBIE || event.getEntityType() == EntityType.SKELETON) {
            event.setCancelled(true);
        }
    }
}