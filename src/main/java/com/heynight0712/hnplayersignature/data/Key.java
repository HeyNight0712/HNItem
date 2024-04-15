package com.heynight0712.hnplayersignature.data;

import com.heynight0712.hnplayersignature.HNPlayerSignature;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class Key {
    public static final NamespacedKey UUID;
    public static final NamespacedKey DisplayName;
    public static final NamespacedKey Vault;

    static {
        JavaPlugin plugin = HNPlayerSignature.getInstance();
        UUID = new NamespacedKey(plugin, "UUID");
        DisplayName = new NamespacedKey(plugin, "DisplayName");
        Vault = new NamespacedKey(plugin, "Vault");
    }
}
