package io.github.seamo.hyperCore;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Silverfish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HyperZombie implements Listener {

    @EventHandler
    public void onZombieHit(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.ZOMBIE || event.getDamager().getType() == EntityType.ZOMBIE) {
            Location location = event.getEntity().getLocation();
            Silverfish silverfish = (Silverfish) location.getWorld().spawnEntity(location, EntityType.SILVERFISH);
            location.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, location, 100);
            location.getWorld().playSound(location, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1.0F, 1.0F);
        }
    }
}