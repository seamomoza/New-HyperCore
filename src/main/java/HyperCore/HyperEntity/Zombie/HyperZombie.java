package HyperCore.HyperEntity.Zombie;

import HyperCore.Loader.ConfigLoader.ConfigManager;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class HyperZombie implements Listener {
    private final Random random = new Random();
    private final double health;
    private final double damage;

    public HyperZombie(JavaPlugin plugin) {
        ConfigManager configManager = new ConfigManager(plugin, "Zombie.yml");
        FileConfiguration config = configManager.getConfig();
        this.health = config.getDouble("attri-health");
        this.damage = config.getDouble("attri-damage");
    }


    @EventHandler
    public void onZombieSpawn(EntitySpawnEvent event) {
        if (event.getEntityType() == EntityType.ZOMBIE) {
            Zombie zombie = (Zombie) event.getEntity();
            zombie.getAttribute(Attribute.MAX_HEALTH).setBaseValue(health);
            zombie.setHealth(health);
            zombie.getAttribute(Attribute.ATTACK_DAMAGE).setBaseValue(damage);
            zombie.getAttribute(Attribute.SCALE).setBaseValue(0.000001); // Set scale to 1.0 (normal size)
        }
    }
}