package com.heynight0712.hnplayersignature.utils.data;

import com.heynight0712.hnplayersignature.core.LanguageManager;
import com.heynight0712.hnplayersignature.data.Key;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class ItemHandle {

    public static boolean isOwner(PersistentDataContainer container, Player player) {
        String ownerUUID = container.get(Key.UUID, PersistentDataType.STRING);
        String playerUUID = player.getUniqueId().toString();
        return ownerUUID == null || ownerUUID.equals(playerUUID);
    }

    public static void addSign(PersistentDataContainer container, Player player) {
        String playerUUID = player.getUniqueId().toString();
        container.set(Key.UUID, PersistentDataType.STRING, playerUUID);
    }

    public static void addSign(PersistentDataContainer container, String playerUUID) {
        container.set(Key.UUID, PersistentDataType.STRING, playerUUID);
    }

    public static void addLore(ItemMeta itemMeta, String value) {
        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
        lore.add(LanguageManager.getString(value));
        itemMeta.setLore(lore);
    }

    public static void addLore(ItemMeta itemMeta, String path, String value) {
        if (itemMeta == null) return;

        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
        String loreName = LanguageManager.getString(path);
        lore.add(loreName.replace("%playername%", value));

        itemMeta.setLore(lore);
    }

    public static boolean removeLore(ItemMeta itemMeta) {
        if (itemMeta == null) return false;
        itemMeta.setLore(null);
        return true;
    }
}
