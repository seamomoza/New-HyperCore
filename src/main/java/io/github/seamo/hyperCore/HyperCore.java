package io.github.seamo.hyperCore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class HyperCore extends JavaPlugin {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new HyperSkeleton(), this);
        Bukkit.getPluginManager().registerEvents(new HyperCreeper(), this);
        Bukkit.getPluginManager().registerEvents(new HyperZombie(), this);
        Bukkit.getPluginManager().registerEvents(new HyperEntityProtection(), this);
        Bukkit.getPluginManager().registerEvents(new HyperBlock(), this);
        Bukkit.getPluginManager().registerEvents(new HyperPlayerListener(this), this);
        Bukkit.getPluginManager().registerEvents(new HyperWeather(this), this);
        //하이퍼 월드보더 설정
        Bukkit.getOnlinePlayers().forEach(player -> new HyperWorldBorder(this).setWorldBorder(player));
    }
}