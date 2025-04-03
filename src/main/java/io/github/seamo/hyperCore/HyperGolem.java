package io.github.seamo.hyperCore;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class HyperGolem implements Listener {
    @EventHandler
    public void Golem(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.IRON_GOLEM) {
            Entity golem = event.getEntity();
            golem.spawnAt(event.getDamager().getLocation());
        }
    }
    @EventHandler
    public void onZombieSpawn(EntitySpawnEvent event) {
        if (event.getEntityType() == EntityType.IRON_GOLEM) {
            IronGolem zombie = (IronGolem) event.getEntity();
            zombie.getAttribute(Attribute.MAX_HEALTH).setBaseValue(200);
            zombie.setHealth(200);
            zombie.setNoDamageTicks(0);
            zombie.getAttribute(Attribute.ATTACK_DAMAGE).setBaseValue(10);
        }
    }
}
