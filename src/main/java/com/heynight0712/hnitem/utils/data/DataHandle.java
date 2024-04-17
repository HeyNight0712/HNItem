package com.heynight0712.hnitem.utils.data;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

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

    /**
     * 判定是否圍牆上 旗幟
     * @param banner Block.getType 旗幟
     * @return 正確的 Material Banner
     */
    public static Material conversionBanner(Material banner) {
        String bannerName = banner.name();
        if (bannerName.endsWith("_WALL_BANNER")) {
            return Material.getMaterial(bannerName.replace("_WALL_BANNER", "_BANNER"));
        }
        return banner;
    }

}
