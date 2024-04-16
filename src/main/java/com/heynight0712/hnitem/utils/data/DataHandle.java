package com.heynight0712.hnitem.utils.data;

import com.heynight0712.hnitem.core.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

public class DataHandle {
    public static String getPlayerName(UUID uuid) {
        Player ownerPlayer = Bukkit.getPlayer(uuid);
        return ownerPlayer != null ? ownerPlayer.getDisplayName() : Bukkit.getOfflinePlayer(uuid).getName();
    }

    /**
     * 判斷 數值是否為 Int
     * @param s 傳入 String
     * @return 如果是則返回 True
     */
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        }catch(NumberFormatException e) {
            return false;
        }
    }

    public static boolean old(ItemMeta itemMeta, Player player, ItemData itemData) {
        // 暫時使用的方式 舊版本
        if (itemMeta.hasLore() && !player.isOp()) {
            // 特殊檢查 舊版本使用
            List<String> lore = itemMeta.getLore();
            String loreName = LanguageManager.getString("item.lore");
            loreName = loreName.replace("%playername%", player.getName());
            int i = -1;
            for (String line : lore) {
                i++;
                if (line.contains(loreName)) {
                    lore.remove(i);
                    itemMeta.setLore(lore);
                    itemData.getItemStack().setItemMeta(itemMeta);
                    player.sendMessage(LanguageManager.getString("commands.sign.success.remove"));
                    return true;
                }
            }
            player.sendMessage(LanguageManager.getString("commands.sign.error.has_lore"));
            return true;
        }
        return false;
    }

}
