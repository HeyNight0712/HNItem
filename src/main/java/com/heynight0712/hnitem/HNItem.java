package com.heynight0712.hnitem;

import com.heynight0712.hnitem.Hooks.VaultHook;
import com.heynight0712.hnitem.commands.SignCommand;
import com.heynight0712.hnitem.commands.WithdrawCommand;
import com.heynight0712.hnitem.commands.WithdrawCompleter;
import com.heynight0712.hnitem.core.LanguageManager;
import com.heynight0712.hnitem.listeners.BlockBreak;
import com.heynight0712.hnitem.listeners.BlockPlace;
import com.heynight0712.hnitem.listeners.CoinClick;
import com.heynight0712.hnitem.listeners.Map;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class HNItem extends JavaPlugin {
    private static HNItem instance;

    @Override
    public void onEnable() {
        instance = this;
        LanguageManager.saveDefaultConfig(this);

        // 依賴項目
        VaultHook.setupEconomy(this);


        getServer().getPluginManager().registerEvents(new Map(), this);

        getServer().getPluginManager().registerEvents(new BlockPlace(), this);
        getServer().getPluginManager().registerEvents(new BlockBreak(), this);

        // Vault
        if (VaultHook.getEconomy() != null) {
            getServer().getPluginManager().registerEvents(new CoinClick(), this);
        }



        registerCommands();
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        instance = null;
        // Plugin shutdown logic
    }

    private void registerCommands() {

        PluginCommand sign = this.getCommand("sign");
        if (sign != null) {
            sign.setExecutor(new SignCommand());
        }

        if (VaultHook.getEconomy() != null) {
            PluginCommand withdraw = this.getCommand("withdraw");
            if (withdraw != null) {
                withdraw.setExecutor(new WithdrawCommand());
                withdraw.setTabCompleter(new WithdrawCompleter());
            }
        }
    }

    public static HNItem getInstance() {return instance;}
}
