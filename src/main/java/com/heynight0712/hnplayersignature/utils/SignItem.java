package com.heynight0712.hnplayersignature.utils;

import com.heynight0712.hnplayersignature.core.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SignItem {
    private ItemStack item;
    private static JavaPlugin plugin;
    private static NamespacedKey uuidKey;

    public SignItem(ItemStack item) {
        this.item = item;
    }

    // 添加簽名
    public boolean addSign(Player player, ItemMeta itemMeta) {
        if (itemMeta == null) return false;

        // 獲取 元數據
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.set(uuidKey, PersistentDataType.STRING, player.getUniqueId().toString());

        // 獲取已有 lore
        List<String> lore = new ArrayList<>();
        if (itemMeta.hasLore()) {
            lore = itemMeta.getLore();
        }

        // 設置 lore
        String loreName = LanguageManager.getString("item.lore");
        loreName = loreName.replace("%playername%", player.getName());
        lore.add(loreName);

        // 寫入 item
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return true;
    }

    // 移除簽名
    // 需要過濾 未簽名物品/簽名物品上面不是相同玩家
    // (未來) 含 lore 物品 要刪除特定物品
    // 如果是管理員則強制移除
    public boolean removeSign(Player player, ItemMeta itemMeta, boolean admin) {
        if (itemMeta == null) return false;

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        String uuidString = container.get(uuidKey, PersistentDataType.STRING);

        // 檢查 uuid相同 或者 管理員
        if (uuidString == null) {
            player.sendMessage(LanguageManager.getString("commands.sign.error.no_sign"));
            return false;
        }

        if (!admin || !uuidString.equals(player.getUniqueId().toString())) {
            String not_owner = LanguageManager.getString("commands.sign.error.not_owner");
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(uuidString));
            not_owner = not_owner.replace("%playername%", offlinePlayer.getName());
            player.sendMessage(not_owner);
            return false;
        }

        List<String> lore = itemMeta.getLore();
        lore.remove(lore.size() -1);

        container.remove(uuidKey);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return true;
    }

    public ItemStack getItem() {
        return item;
    }

    // 初始化
    public static void initPlugin(JavaPlugin javaPlugin) {
        plugin = javaPlugin;
        uuidKey = new NamespacedKey(plugin, "uuid");
    }
}
