package com.heynight0712.hnitem.commands;

import com.heynight0712.hnitem.core.LanguageManager;
import com.heynight0712.hnitem.data.database.MapDatabase;
import com.heynight0712.hnitem.utils.data.ItemData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

public class MapTransferCommand implements CommandExecutor {

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
            commandSender.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.MapTransfer.Fail.NotItem"));
            return true;
        }

        // 檢查 是否地圖
        if (!(itemMeta instanceof MapMeta mapMeta)) {
            commandSender.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.MapTransfer.Fail.NotMap"));
            return true;
        }

        MapView mapView = mapMeta.getMapView();
        // 檢查 地圖資料
        if (mapView == null) {
            commandSender.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.MapTransfer.Fail.NotMapData"));
            return true;
        }

        // 檢查 是否已鎖定
        if (!mapView.isLocked()) {
            commandSender.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.MapTransfer.Fail.NotLocked"));
            return true;
        }

        MapDatabase mapDatabase = new MapDatabase();
        // 檢查 是否成功綁定
        if (mapDatabase.addMap(mapView.getId(), player.getUniqueId().toString())) {
            player.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.MapTransfer.Success"));
        }else {
            player.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.MapTransfer.Fail.HasLocked"));
        }
        return true;
    }
}
