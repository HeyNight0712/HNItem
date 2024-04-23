package com.heynight0712.hnitem.commands;

import com.heynight0712.hnitem.core.LanguageManager;
import com.heynight0712.hnitem.data.KeyManager;
import com.heynight0712.hnitem.data.MapInfo;
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

import java.util.UUID;

public class SignCommand implements CommandExecutor {
    private ItemData itemData;
    private Player player;
    

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            player = (Player) commandSender;

            // 獲取 手上物品
            itemData = new ItemData(player.getInventory().getItemInMainHand());
            ItemMeta itemMeta = itemData.getItemStack().getItemMeta();
            // 檢查 是否為空
            if (itemMeta == null) {
                player.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.Sign.Fail.NotItem"));
                return true;
            }

            // 檢查是否簽名
            PersistentDataContainer container = itemData.getPersistentDataContainer();
            if (!container.has(KeyManager.UUID)) {
                add();
                return true;
            }

            // 檢查是否擁有者
            if (ItemHandle.isOwner(container, player)) {
                remove();
            } else {
                String not_owner = LanguageManager.getString("Commands.Sign.Fail.NotOwner");
                String ownerName = DataHandle.getPlayerName(UUID.fromString(container.get(KeyManager.UUID, PersistentDataType.STRING)));
                not_owner = not_owner.replace("%playername%", ownerName != null ? ownerName : LanguageManager.getString("NotFoundPlayer"));
                player.sendMessage(LanguageManager.title + not_owner);
            }

            return true;
        }
        commandSender.sendMessage(LanguageManager.title + LanguageManager.getString("NotPlayer"));
        return true;
    }

    /**
     * 添加簽名
     */
    private void add() {
        ItemMeta itemMeta = itemData.getItemStack().getItemMeta();
        if (isMapLocked(itemMeta)) return; // 過濾簽名地圖
        // add
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.set(KeyManager.UUID, PersistentDataType.STRING, player.getUniqueId().toString());
        ItemHandle.addLore(itemMeta, "Lore.Sign", "%playername%", player.getName());
        itemData.getItemStack().setItemMeta(itemMeta);

        player.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.Sign.Success.Add"));
    }

    private void remove() {
        ItemMeta itemMeta = itemData.getItemStack().getItemMeta();
        if (isMapLocked(itemMeta)) return; //過濾簽名地圖

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        itemMeta.setLore(null);
        container.remove(KeyManager.UUID);
        itemData.getItemStack().setItemMeta(itemMeta);

        player.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.Sign.Success.Remove"));
    }

    /**
     * 如果地圖已經 `鎖定` 則無法進行簽名動作
     * @return 如果是 則返回 True
     */
    private boolean isMapLocked(ItemMeta itemMeta) {
        if (!(itemMeta instanceof MapMeta mapMeta)) return false;

        MapView mapView = mapMeta.getMapView();
        if (mapView == null) return false;
        if (!mapView.isLocked()) return false;

        MapDatabase mapDatabase = new MapDatabase();
        MapInfo mapInfo = mapDatabase.getMap(mapView.getId());
        if (mapInfo == null) return false;

        player.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.Sign.Fail.HasLocked"));
        return true;
    }
}
