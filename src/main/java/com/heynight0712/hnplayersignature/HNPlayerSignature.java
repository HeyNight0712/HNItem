package com.heynight0712.hnplayersignature;

import com.heynight0712.hnplayersignature.commands.SignCommand;
import com.heynight0712.hnplayersignature.core.LanguageManager;
import com.heynight0712.hnplayersignature.utils.SignItem;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class HNPlayerSignature extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        LanguageManager.saveDefaultConfig(this);

        // 賦予 JavaPlugin
        SignItem.initPlugin(this);

        registerCommands();
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommands() {
        getLogger().info(LanguageManager.getConfig().getString("system.register.commands", "system.register.commands"));

        PluginCommand sign = this.getCommand("hnplayersignature");
        if (sign != null) {
            sign.setExecutor(new SignCommand());
        }
    }
}
