package HyperCore.Loader.HyperCoreLoader;

import HyperCore.HyperEntity.Creeper.HyperCreeper;
import HyperCore.HyperEntity.EnderMan.HyperEnderMan;
import HyperCore.HyperEntity.Evoker.HyperEvoker;
import HyperCore.HyperEntity.Golem.HyperGolem;
import HyperCore.HyperEntity.Protection.HyperEntityProtection;
import HyperCore.HyperEntity.Skeleton.HyperSkeleton;
import HyperCore.HyperEntity.Slime.HyperSlime;
import HyperCore.HyperEntity.Zombie.HyperZombie;
import HyperCore.Loader.WorldBorderLoader.HyperPlayerRespawn;
import HyperCore.Weather.HyperWeather;
import HyperCore.WorldBorder.HyperWorldBorder;
import HyperCore.WorldBorder.HyperWorldBorderCommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class HyperCore extends JavaPlugin {
    @Override
    public void onEnable() {
        FileConfiguration config = getConfig();

        Bukkit.getPluginManager().registerEvents(new HyperEntityProtection(this), this);
        Bukkit.getPluginManager().registerEvents(new HyperCreeper(this), this);
        Bukkit.getPluginManager().registerEvents(new HyperSkeleton(this), this);
        Bukkit.getPluginManager().registerEvents(new HyperZombie(this), this);
        Bukkit.getPluginManager().registerEvents(new HyperWeather(this), this);
        Bukkit.getPluginManager().registerEvents(new HyperPlayerRespawn(this), this);
        Bukkit.getPluginManager().registerEvents(new HyperSlime(this), this);
        Bukkit.getPluginManager().registerEvents(new HyperGolem(this), this);
        Bukkit.getPluginManager().registerEvents(new HyperEnderMan(this), this);
        Bukkit.getPluginManager().registerEvents(new HyperEvoker(this), this);
        HyperWorldBorder worldBorder = new HyperWorldBorder(this);
        worldBorder.startBorderMovement();
        getCommand("wb").setExecutor(new HyperWorldBorderCommand(worldBorder));
    }
}