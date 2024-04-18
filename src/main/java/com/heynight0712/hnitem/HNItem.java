package com.heynight0712.hnitem;

import com.heynight0712.hnitem.Hooks.VaultHook;
import com.heynight0712.hnitem.commands.MapTransferCommand;
import com.heynight0712.hnitem.commands.SignCommand;
import com.heynight0712.hnitem.commands.WithdrawCommand;
import com.heynight0712.hnitem.commands.WithdrawCompleter;
import com.heynight0712.hnitem.core.ConfigManager;
import com.heynight0712.hnitem.core.LanguageManager;
import com.heynight0712.hnitem.data.DatabaseManager;
import com.heynight0712.hnitem.data.database.MapDatabase;
import com.heynight0712.hnitem.listeners.BlockBreak;
import com.heynight0712.hnitem.listeners.BlockPlace;
import com.heynight0712.hnitem.listeners.CoinClick;
import com.heynight0712.hnitem.listeners.Map;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class HNItem extends JavaPlugin {
    private static HNItem instance;
    private static DatabaseManager databaseManager;

    @Override
    public void onEnable() {
        instance = this;
        ConfigManager.saveDefaultConfig(this);
        LanguageManager.saveDefaultConfig(this);
        initDataBase();

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
        try {
            databaseManager.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void registerCommands() {

        // sign
        PluginCommand sign = this.getCommand("sign");
        if (sign != null) {
            sign.setExecutor(new SignCommand());
        }

        // withdraw
        if (VaultHook.getEconomy() != null) {
            PluginCommand withdraw = this.getCommand("withdraw");
            if (withdraw != null) {
                withdraw.setExecutor(new WithdrawCommand());
                withdraw.setTabCompleter(new WithdrawCompleter());
            }
        }

        // maptransfer
        PluginCommand maptransfer = this.getCommand("maptransfer");
        if (maptransfer != null) {
            maptransfer.setExecutor(new MapTransferCommand());
        }
    }

    // 初始化資料庫
    private void initDataBase() {
        try {
            databaseManager = new DatabaseManager(getDataFolder().getAbsolutePath() + "/.db");

            // Map 功能
            MapDatabase mapDatabase = new MapDatabase();
            mapDatabase.createMapTable();
        } catch (SQLException e) {
            getLogger().severe("Database could not be created!");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    public static HNItem getInstance() {return instance;}

    public static DatabaseManager getDatabase() {return databaseManager;}
}
