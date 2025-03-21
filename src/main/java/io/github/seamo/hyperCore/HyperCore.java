package io.github.seamo.hyperCore;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class HyperCore extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();
        FileConfiguration config = getConfig();

        Bukkit.getPluginManager().registerEvents(new HyperEntityProtection(config), this);
        Bukkit.getPluginManager().registerEvents(new HyperBlock(config), this);
        Bukkit.getPluginManager().registerEvents(new HyperCreeper(config), this);
        Bukkit.getPluginManager().registerEvents(new HyperSkeleton(config), this);
        Bukkit.getPluginManager().registerEvents(new HyperZombie(config), this);
        Bukkit.getPluginManager().registerEvents(new HyperWeather(this, config), this);
        Bukkit.getPluginManager().registerEvents(new HyperPlayerRespawn(this), this);
        Bukkit.getPluginManager().registerEvents(new HyperSlime(this), this);

        HyperWorldBorder worldBorder = new HyperWorldBorder(this, config);
        worldBorder.startBorderMovement();
        getCommand("wb").setExecutor(new HyperWorldBorderCommand(worldBorder));
    }
}