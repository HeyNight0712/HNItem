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
            commandSender.sendMessage(LanguageManager.title + LanguageManager.getString("MapTransfer.Fail.NotItem"));
            return true;
        }

        // 檢查 是否地圖
        if (!(itemMeta instanceof MapMeta mapMeta)) {
            commandSender.sendMessage(LanguageManager.title + LanguageManager.getString("MapTransfer.Fail.NotMap"));
            return true;
        }

        // 檢查 地圖資訊是否完整
        if (!mapMeta.hasMapView()) {
            commandSender.sendMessage(LanguageManager.title + LanguageManager.getString("MapTransfer.Fail.MapIncomplete"));
            return true;
        }

        MapView originalMapView = mapMeta.getMapView();

        player.sendMessage("新地图创建成功，虚拟ID:");

        MapDatabase mapDatabase = new MapDatabase();
        if (mapDatabase.addMap(originalMapView.getId(), player.getUniqueId().toString())) {
            player.sendMessage("成功綁定");
        }else {
            player.sendMessage("綁定失敗 請聯絡管理員");
        }
        return true;
    }
}
