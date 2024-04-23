package com.heynight0712.hnitem.commands;

import com.heynight0712.hnitem.core.LanguageManager;
import com.heynight0712.hnitem.data.MapInfo;
import com.heynight0712.hnitem.data.database.MapDatabase;
import com.heynight0712.hnitem.utils.data.DataHandle;
import com.heynight0712.hnitem.utils.data.ItemData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class MapCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        // 檢查 是否玩家輸入指令
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage(LanguageManager.title + LanguageManager.getString("NotPlayer"));
            return true;
        }
        
        ItemData itemData = new ItemData(player.getInventory().getItemInMainHand());
        ItemMeta itemMeta = itemData.getItemStack().getItemMeta();

        // 檢查 手上物品
        if (itemMeta == null) {
            commandSender.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.Map.Fail.NotItem"));
            return true;
        }

        // 檢查 是否地圖
        if (!(itemMeta instanceof MapMeta mapMeta)) {
            commandSender.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.Map.Fail.NotMap"));
            return true;
        }

        MapView mapView = mapMeta.getMapView();
        // 檢查 地圖資料
        if (mapView == null) {
            commandSender.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.Map.Fail.NotMapData"));
            return true;
        }

        // 檢查 是否已鎖定
        if (!mapView.isLocked()) {
            commandSender.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.Map.Fail.NotLocked"));
            return true;
        }

        MapDatabase mapDatabase = new MapDatabase();
        MapInfo mapInfo = mapDatabase.getMap(mapView.getId());

        if (mapInfo == null) {
            commandSender.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.Map.Fail.NotLocked"));
            return true;
        }

        // 輸出
        String ownerName = DataHandle.getPlayerName(UUID.fromString(mapInfo.getUuid()));
        List<String> success = LanguageManager.getConfig().getStringList("Commands.Map.Success");

        for (String line : success) {
            line = ChatColor.translateAlternateColorCodes('&', line);
            String processedLine;
            processedLine = line
                    .replace("%name%", mapInfo.getName())
                    .replace("%mapid%", String.valueOf(mapInfo.getMapID()))
                    .replace("%playername%", ownerName)
                    .replace("%locked%", String.valueOf(mapInfo.getLocked()))
                    .replace("%date%", String.valueOf(mapInfo.getDate()));

            player.sendMessage(processedLine);
        }
        return true;
    }
}
