package com.heynight0712.hnitem.core;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class LanguageManager {
    private static File languageFile;
    private static FileConfiguration languageConfig;

    public static void saveDefaultConfig(JavaPlugin plugin) {
        if (languageFile == null) {
            languageFile = new File(plugin.getDataFolder(), "language.yml");
            if (!languageFile.exists()) {
                languageFile.getParentFile().mkdirs();
                plugin.saveResource("language.yml", false);
            }
        }

        if (languageConfig == null) {
            languageConfig = YamlConfiguration.loadConfiguration(languageFile);
        }
    }

    public static FileConfiguration getConfig() {
        if (languageConfig == null) {
            throw new IllegalStateException("無法初始化,你有呼叫過 saveDefaultLanguageConfig?");
        }
        return languageConfig;
    }

    public static void reloadConfig(JavaPlugin plugin) {
        languageConfig = YamlConfiguration.loadConfiguration(languageFile);
    }

    public static String getString(String path) {
        String message = languageConfig.getString(path, "&7" + path);

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String getString(String path, String def) {
        String message = languageConfig.getString(path, def);

        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
