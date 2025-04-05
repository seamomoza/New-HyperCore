package HyperCore.Loader.ConfigLoader;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ConfigManager {
    private final JavaPlugin plugin;
    private final String configFileName;
    private FileConfiguration config;

    public ConfigManager(JavaPlugin plugin, String configFileName) {
        this.plugin = plugin;
        this.configFileName = configFileName;
        loadConfig();
    }

    private void loadConfig() {
        File configFile = new File(plugin.getDataFolder(), "configs/" + configFileName);
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("configs/" + configFileName, false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public FileConfiguration getConfig() {
        return config;
    }
}