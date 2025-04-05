package HyperCore.HyperEntity.Golem;

import HyperCore.Loader.ConfigLoader.ConfigManager;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class HyperGolem implements Listener {
    private final double damage;
    private final double health;
    private final double scale;

    public HyperGolem(JavaPlugin plugin) {
        ConfigManager configManager = new ConfigManager(plugin, "Golem.yml");
        FileConfiguration config = configManager.getConfig();
        this.damage = config.getDouble("attri-damage");
        this.health = config.getDouble("attri-health");
        this.scale = config.getDouble("attri-size");
    }

    @EventHandler
    public void Golem(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.IRON_GOLEM) {
            Entity golem = event.getEntity();
            golem.spawnAt(event.getDamager().getLocation());
        }
    }
    @EventHandler
    public void onGolemSpawn(EntitySpawnEvent event) {
        if (event.getEntityType() == EntityType.IRON_GOLEM) {
            IronGolem zombie = (IronGolem) event.getEntity();
            zombie.getAttribute(Attribute.MAX_HEALTH).setBaseValue(health);
            zombie.getAttribute(Attribute.SCALE).setBaseValue(scale);
            zombie.setHealth(200);
            zombie.setNoDamageTicks(0);
            zombie.getAttribute(Attribute.ATTACK_DAMAGE).setBaseValue(damage);
        }
    }
}
