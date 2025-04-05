package HyperCore.Weather;

import HyperCore.Loader.ConfigLoader.ConfigManager;
import HyperCore.Loader.HyperCoreLoader.HyperCore;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class HyperWeather implements Listener {

    private final double lightningChance;
    private final double damageCount;
    private final int noDamageTick;
    private final int damageTick;
    private final long thunderTick;
    private final Random random = new Random();

    public HyperWeather(JavaPlugin plugin) {
        ConfigManager configManager = new ConfigManager(plugin, "Weather.yml");
        FileConfiguration config = configManager.getConfig();
        this.lightningChance = config.getDouble("lightning-chance");
        this.damageCount = config.getDouble("damage-count");
        this.noDamageTick = config.getInt("damage-noDamageTicks");
        this.damageTick = config.getInt("damage-TickRate");
        this.thunderTick = config.getLong("Thunder-TickRate");
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        if (!world.hasStorm() && !world.isThundering()) {
            if (random.nextDouble() < lightningChance) {
                world.strikeLightning(player.getLocation());
            }
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        World world = event.getWorld();
        if (event.toWeatherState()) {
            startDamageTask(world);
            startMonsterSpawnTask(world);
        }
    }

    private void startDamageTask(World world) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!world.hasStorm() && !world.isThundering()) {
                    this.cancel();
                    return;
                }

                for (Player player : world.getPlayers()) {
                    if (world.hasStorm() && !world.isThundering()) {
                        if (isExposedToSky(player)) {
                            player.damage(damageCount);
                            player.setNoDamageTicks(noDamageTick);
                        }
                    }
                }
            }
        }.runTaskTimer(JavaPlugin.getPlugin(HyperCore.class), 0, damageTick);
    }

    private void startMonsterSpawnTask(World world) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!world.hasStorm() && !world.isThundering()) {
                    this.cancel();
                    return;
                }

                for (Player player : world.getPlayers()) {
                    if (isExposedToSky(player)) {
                        if (world.isThundering()) {
                            world.strikeLightning(player.getLocation());
                        }
                    }
                }
            }
        }.runTaskTimer(JavaPlugin.getPlugin(HyperCore.class), 0, thunderTick);
    }

    private boolean isExposedToSky(Player player) {
        for (int y = player.getLocation().getBlockY() + 1; y < player.getWorld().getMaxHeight(); y++) {
            if (player.getWorld().getBlockAt(player.getLocation().getBlockX(), y, player.getLocation().getBlockZ()).getType() != Material.AIR) {
                return false;
            }
        }
        return true;
    }
}