package io.github.seamo.hyperCore;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HyperGolem implements Listener {
    @EventHandler
    public void Golem(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.IRON_GOLEM) {
            Entity golem = event.getEntity();
            golem.spawnAt(event.getDamager().getLocation());
        }
    }
}
