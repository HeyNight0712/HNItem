package com.heynight0712.hnplayersignature.listeners;

import com.heynight0712.hnplayersignature.core.LanguageManager;
import com.heynight0712.hnplayersignature.utils.SignItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.CartographyInventory;

public class Map implements Listener {
    @EventHandler
    public void onMap(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;

        // 檢查 製圖台
        if (event.getClickedInventory() instanceof CartographyInventory) {
            SignItem signItem = new SignItem(event.getInventory().getItem(0));

            if (signItem.getItem() == null) return;

            // 檢查 是否簽名
            if (signItem.hasSing() && event.getSlot() == 2) {
                Player player = (Player) event.getWhoClicked();
                String playerUUID = String.valueOf(player.getUniqueId());

                // 檢查 UUID 是否相同
                if (signItem.getSingUUID().equals(playerUUID)) return;

                event.setCancelled(true);
                event.getWhoClicked().sendMessage(LanguageManager.getString("item.not_owner"));
            }
        }
    }
}