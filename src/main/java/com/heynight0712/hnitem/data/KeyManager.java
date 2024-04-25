package com.heynight0712.hnitem.data;

import com.heynight0712.hnitem.HNItem;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class KeyManager {
    private static final NamespacedKey UUID;
    private static final NamespacedKey DisplayName;
    private static final NamespacedKey Value;
    private static final NamespacedKey Locked;

    static {
        JavaPlugin plugin = HNItem.getInstance();
        UUID = new NamespacedKey(plugin, "UUID");
        DisplayName = new NamespacedKey(plugin, "DisplayName");
        Value = new NamespacedKey(plugin, "Value");
        Locked = new NamespacedKey(plugin, "Locked");
    }

    public static NamespacedKey getUUID() {return UUID;}

    public static NamespacedKey getDisplayName() {return DisplayName;}

    public static NamespacedKey getValue() {return Value;}

    public static NamespacedKey getLocked() {return Locked;}
}
