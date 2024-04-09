package com.heynight0712.hnplayersignature.utils.data;

import com.heynight0712.hnplayersignature.core.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemHandler {
    private final ItemData itemData;

    public ItemHandler(ItemStack itemStack) {
        this.itemData = new ItemData(itemStack);
    }

    public boolean setSign(String playerUUID, ItemMeta itemMeta) {
        if (itemMeta == null) return false;

        // 設置 key
        itemData.setUUID(playerUUID, itemMeta);

        // 獲取已有 Lore
        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();

        // 獲取 PlayerName
        Player player = Bukkit.getPlayer(UUID.fromString(playerUUID));
        String playerName = player != null ? player.getDisplayName() : Bukkit.getOfflinePlayer(UUID.fromString(playerUUID)).getName();

        // 添加 Lore
        String loreName = LanguageManager.getString("item.lore");
        loreName = loreName.replace("%playername%", playerName != null ? playerName : "未知玩家");
        lore.add(loreName);

        itemMeta.setLore(lore);
        itemData.getItem().setItemMeta(itemMeta);
        return true;
    }

    public boolean removeSign(ItemMeta itemMeta) {
        if (itemMeta == null) return false;

        // 檢查 是否簽名
        if (itemData.hasUUID()) {
            itemData.removeUUIDKey(itemMeta);
        }

        // 檢查 保護命名
        if (itemData.hasDisplayName()) {
            itemData.removeDisplayNameKey(itemMeta);
        }

        itemMeta.setLore(null);
        itemData.getItem().setItemMeta(itemMeta);
        return true;
    }

    public ItemData getItemData() {return this.itemData;}

}
