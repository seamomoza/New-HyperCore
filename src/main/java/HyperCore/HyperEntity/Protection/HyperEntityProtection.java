
package HyperCore.HyperEntity.Protection;

import HyperCore.Loader.ConfigLoader.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class HyperEntityProtection implements Listener {
    private final boolean protectZombie;
    private final boolean protectSkeleton;

    public HyperEntityProtection(JavaPlugin plugin) {
        ConfigManager configManager = new ConfigManager(plugin, "Entity-Protection.yml");
        FileConfiguration config = configManager.getConfig();
        this.protectZombie = config.getBoolean("protect-zombie");
        this.protectSkeleton = config.getBoolean("protect-skeleton");
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