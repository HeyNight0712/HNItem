package com.heynight0712.hnplayersignature.utils.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DataHandle {
    public static String getPlayerName(UUID uuid) {
        Player ownerPlayer = Bukkit.getPlayer(uuid);
        String ownerName = ownerPlayer != null ? ownerPlayer.getDisplayName() : Bukkit.getOfflinePlayer(uuid).getName();
        return ownerName;
    }

}
