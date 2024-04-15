package com.heynight0712.hnplayersignature;

import com.heynight0712.hnplayersignature.commands.SignCommand;
import com.heynight0712.hnplayersignature.commands.SubCommand;
import com.heynight0712.hnplayersignature.core.LanguageManager;
import com.heynight0712.hnplayersignature.listeners.BlockBreak;
import com.heynight0712.hnplayersignature.listeners.BlockPlace;
import com.heynight0712.hnplayersignature.listeners.Map;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class HNPlayerSignature extends JavaPlugin {
    private static HNPlayerSignature instance;

    @Override
    public void onEnable() {
        instance = this;
        LanguageManager.saveDefaultConfig(this);


        getServer().getPluginManager().registerEvents(new Map(), this);

        getServer().getPluginManager().registerEvents(new BlockPlace(), this);
        getServer().getPluginManager().registerEvents(new BlockBreak(), this);

        registerCommands();
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        instance = null;
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

    public static HNPlayerSignature getInstance() {return instance;}
}
