package com.heynight0712.hnitem.data;

import com.heynight0712.hnitem.HNItem;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class Key {
    public static final NamespacedKey UUID;
    public static final NamespacedKey DisplayName;
    public static final NamespacedKey Value;

    static {
        JavaPlugin plugin = HNItem.getInstance();
        UUID = new NamespacedKey(plugin, "UUID");
        DisplayName = new NamespacedKey(plugin, "DisplayName");
        Value = new NamespacedKey(plugin, "Value");

    }
}
