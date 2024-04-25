package com.heynight0712.hnitem.commands;

import com.heynight0712.hnitem.core.LanguageManager;
import com.heynight0712.hnitem.data.KeyManager;
import com.heynight0712.hnitem.data.database.MapDatabase;
import com.heynight0712.hnitem.utils.data.DataHandle;
import com.heynight0712.hnitem.utils.data.ItemData;
import com.heynight0712.hnitem.utils.data.ItemHandle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MapLockedCommand implements CommandExecutor {

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
            commandSender.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.MapLocked.Fail.NotItem"));
            return true;
        }

        // 檢查 是否地圖
        if (!(itemMeta instanceof MapMeta mapMeta)) {
            commandSender.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.MapLocked.Fail.NotMap"));
            return true;
        }

        MapView mapView = mapMeta.getMapView();
        // 檢查 地圖資料
        if (mapView == null) {
            commandSender.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.MapLocked.Fail.NotMapData"));
            return true;
        }

        // 檢查 是否已鎖定
        if (!mapView.isLocked()) {
            commandSender.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.MapLocked.Fail.NotLocked"));
            return true;
        }

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        List<String> lore = new ArrayList<>();
        String ownerName = null;

        // 檢查 UUID Key
        // 防止 已簽名後 直接綁定
        if (container.has(KeyManager.getUUID())) {
            ownerName = DataHandle.getPlayerName(UUID.fromString(container.get(KeyManager.getUUID(), PersistentDataType.STRING)));

            // 檢查 是否擁有者
            if (!ItemHandle.isOwner(container, player)) {
                String not_owner = LanguageManager.getString("Commands.MapLocked.Fail.NotOwner");
                not_owner = not_owner.replace("%playername%", ownerName != null ? ownerName : LanguageManager.getString("NotFoundPlayer"));
                commandSender.sendMessage(LanguageManager.title + not_owner);
                return true;
            }

            // 清空舊的
            itemMeta.setLore(null);
        }else {
            // 寫入 NBT
            container.set(KeyManager.getUUID(), PersistentDataType.STRING, player.getUniqueId().toString());
        }

        // Lore 相關重寫
        // 上面檢查並且重新添加 Lore 以免資訊不同
        String MapLocked = LanguageManager.getString("Lore.MapLocked");
        MapLocked = MapLocked.replace("%playername%", ownerName != null ? ownerName : player.getName());
        lore.add(MapLocked);

        lore.add(LanguageManager.getString("Lore.MapLockedRule"));
        itemMeta.setLore(lore);


        MapDatabase mapDatabase = new MapDatabase();
        // 檢查 是否成功綁定到數據庫
        if (strings.length > 0) {
            if (mapDatabase.addMap(mapView.getId(), player.getUniqueId().toString(), strings[0], true)) {

                String success = LanguageManager.getString("Commands.MapLocked.Success");
                success = success.replace("%mapid%", String.valueOf(mapView.getId()));

                player.sendMessage(LanguageManager.title + success);
            } else {
                player.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.MapLocked.Fail.HasLocked"));
            }
        } else {
            if (mapDatabase.addMap(mapView.getId(), player.getUniqueId().toString(), "Map", true)) {

                String success = LanguageManager.getString("Commands.MapLocked.Success");
                success = success.replace("%mapid%", String.valueOf(mapView.getId()));

                player.sendMessage(LanguageManager.title + success);
            } else {
                player.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.MapLocked.Fail.HasLocked"));
            }
        }

        // 更新手中的物品
        itemData.getItemStack().setItemMeta(itemMeta);
        player.getInventory().setItemInMainHand(itemData.getItemStack());
        return true;
    }
}
