package com.heynight0712.hnplayersignature;

import com.heynight0712.hnplayersignature.commands.SignCommand;
import com.heynight0712.hnplayersignature.commands.SubCommand;
import com.heynight0712.hnplayersignature.core.LanguageManager;
import com.heynight0712.hnplayersignature.listeners.Map;
import com.heynight0712.hnplayersignature.utils.SignItem;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class HNPlayerSignature extends JavaPlugin {

    @Override
    public void onEnable() {
        LanguageManager.saveDefaultConfig(this);

        // 賦予 JavaPlugin
        SignItem.initPlugin(this);

        getServer().getPluginManager().registerEvents(new Map(), this);

        registerCommands();
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommands() {
        String command = LanguageManager.getString("system.command");

        PluginCommand sign = this.getCommand("hnplayersignature");
        if (sign != null) {
            sign.setExecutor(new SignCommand());
            sign.setTabCompleter(new SubCommand());
            command = command.replace("%command%", String.valueOf(sign.getAliases()));
            getServer().getConsoleSender().sendMessage(command);
        }
    }
}
