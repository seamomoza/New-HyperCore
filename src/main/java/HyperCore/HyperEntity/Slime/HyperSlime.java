package HyperCore.HyperEntity.Slime;

import HyperCore.Loader.ConfigLoader.ConfigManager;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class HyperSlime implements Listener {
    private final Set<Slime> noSplitSlimes = new HashSet<>();
    private final int splitCount;

    public HyperSlime(JavaPlugin plugin) {
        ConfigManager configManager = new ConfigManager(plugin, "Slime.yml");
        FileConfiguration config = configManager.getConfig();
        this.splitCount = config.getInt("split-count", 2); // Default to 2 if not set
    }

    @EventHandler
    public void onSlimeSplit(SlimeSplitEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onSlimeDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Slime slime) {
            if (noSplitSlimes.contains(slime)) {
                noSplitSlimes.remove(slime);
                return; // Do not spawn new Slimes
            }

            // New splitting logic
            int size = slime.getSize();
            for (int i = 0; i < splitCount; i++) {
                Slime newSlime = slime.getWorld().spawn(slime.getLocation(), Slime.class);
                newSlime.setSize(size);
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Slime slime && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.BLAZE_ROD) {
            if (noSplitSlimes.contains(slime)) {
                return; // Do nothing if the Slime is already in the noSplitSlimes set
            }
            Player player = event.getPlayer();
            slime.getWorld().spawnParticle(Particle.FIREWORK, slime.getLocation(), 100,0.1,0.1,0.1);
            slime.getWorld().playSound(slime.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1.5f, 1f);
            noSplitSlimes.add(slime);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1); // Remove one Blaze Rod from the player's inventory
        }
    }
}