package com.heynight0712.hnplayersignature;

import com.heynight0712.hnplayersignature.core.LanguageManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class HNPlayerSignature extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        LanguageManager.saveDefaultConfig(this);

        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
