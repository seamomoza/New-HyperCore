package HyperCore.Loader.ConfigLoader;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    /**
     * This class is used to manage configuration files in a Bukkit plugin.
     * It handles the creation, loading, saving, and reloading of YAML configuration files.
     */
    private final JavaPlugin plugin;
    private final String fileName;
    private File file;
    private FileConfiguration config;

    public ConfigManager(JavaPlugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName.endsWith(".yml") ? fileName : fileName + ".yml";
        createAndLoad();
    }

    private void createAndLoad() {
        file = new File(plugin.getDataFolder(), "configs" + File.separator + fileName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource("configs/" + fileName, false);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(file);
    }
}

