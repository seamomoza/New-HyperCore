package io.github.seamo.hyperCore;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.Random;

public class HyperZombie implements Listener {
    private final FileConfiguration config;
    private final Random random = new Random();

    public HyperZombie(FileConfiguration config) {
        this.config = config;
    }

    @EventHandler
    public void onZombieHit(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.ZOMBIE || event.getDamager().getType() == EntityType.ZOMBIE) {
            double spawnChance = config.getDouble("Zombie.spawn-Chance");
            int spawnCount = config.getInt("Zombie.spawn-Silverfish");
            if (random.nextDouble() * 100 < spawnChance) {
                Location location = event.getEntity().getLocation();
                for (int i = 0; i < spawnCount; i++) {
                    Silverfish silverfish = (Silverfish) location.getWorld().spawnEntity(location, EntityType.SILVERFISH);
                }
                location.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, location, 100);
                location.getWorld().playSound(location, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1.0F, 1.0F);
            }
        }
    }
    @EventHandler
    public void onZombieSpawn(EntitySpawnEvent event) {
        if (event.getEntityType() == EntityType.ZOMBIE) {
            double Health = config.getDouble("Zombie.attri-health");
            double Damage = config.getDouble("Zombie.attri-damage");
            Zombie zombie = (Zombie) event.getEntity();
            zombie.getAttribute(Attribute.MAX_HEALTH).setBaseValue(Health);
            zombie.getAttribute(Attribute.ATTACK_DAMAGE).setBaseValue(Damage);
        }
    }
}