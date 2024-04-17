package com.heynight0712.hnitem.core;

import com.heynight0712.hnitem.HNItem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {
    private static FileConfiguration config;

    public static void saveDefaultConfig(JavaPlugin plugin) {
        plugin.saveDefaultConfig();
        config = plugin.getConfig();
    }

    public static FileConfiguration getConfig() {
        if (config == null) throw new NullPointerException("config is null");
        return config;
    }

    public static void reloadConfig() {
        config = HNItem.getInstance().getConfig();
    }
}
