package HyperCore.HyperEntity.Zombie;

import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.Random;

public class HyperZombie implements Listener {
    private final FileConfiguration config;
    private final Random random = new Random();

    public HyperZombie(FileConfiguration config) {
        this.config = config;
    }


    @EventHandler
    public void onZombieSpawn(EntitySpawnEvent event) {
        if (event.getEntityType() == EntityType.ZOMBIE) {
            double health = config.getDouble("Zombie.attri-health");
            double damage = config.getDouble("Zombie.attri-damage");
            Zombie zombie = (Zombie) event.getEntity();
            zombie.getAttribute(Attribute.MAX_HEALTH).setBaseValue(health);
            zombie.setHealth(health);
            zombie.getAttribute(Attribute.ATTACK_DAMAGE).setBaseValue(damage);
            zombie.getAttribute(Attribute.SCALE).setBaseValue(0.000001); // Set scale to 1.0 (normal size)
        }
    }
}