package com.heynight0712.hnitem.Hooks;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class VaultHook {
    private static Economy econ = null;

    public static boolean setupEconomy(JavaPlugin plugin) {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            plugin.getLogger().severe("未找到 Vault 默認禁用");
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            plugin.getLogger().severe("未找到 Economy 默認禁用");
            return false;
        }
        econ = rsp.getProvider();
        plugin.getLogger().info("成功掛勾 Vault");
        return true;
    }

    public static Economy getEconomy() {
        return econ;
    }
}
