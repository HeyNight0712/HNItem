package com.heynight0712.hnitem.utils.data;

import com.heynight0712.hnitem.core.LanguageManager;
import com.heynight0712.hnitem.data.KeyManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class ItemHandle {

    /**
     * 檢查是否傭有者
     * @param container 物品標籤
     * @param player 玩家
     * @return 是則返回 True
     */
    public static boolean isOwner(PersistentDataContainer container, Player player) {
        String ownerUUID = container.get(KeyManager.UUID, PersistentDataType.STRING);
        String playerUUID = player.getUniqueId().toString();
        return ownerUUID == null || ownerUUID.equals(playerUUID);
    }

    /**
     * 添加 lore
     * @param itemMeta 物品itemMeta
     * @param path language,yml 路徑
     * @param target 轉換 language 內容
     * @param replacement 覆蓋 target 文本
     */
    public static void addLore(ItemMeta itemMeta, String path, String target, String replacement) {
        if (itemMeta == null) return;

        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
        String loreName = LanguageManager.getString(path);
        lore.add(loreName.replace(target, replacement));

        itemMeta.setLore(lore);
    }

    /**
     * 移除 所有lore
     * @param itemMeta 物品itemMeta
     */
    public static void removeLore(ItemMeta itemMeta) {
        if (itemMeta == null) return;
        itemMeta.setLore(null);
    }


}
