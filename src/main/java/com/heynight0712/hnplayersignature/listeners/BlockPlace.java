package com.heynight0712.hnplayersignature.listeners;

import com.heynight0712.hnplayersignature.utils.data.ItemData;
import com.heynight0712.hnplayersignature.utils.data.ItemHandler;
import org.bukkit.block.Banner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class BlockPlace implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        ItemData item = new ItemData(event.getItemInHand());

        if (placeBanner(item, event)) return;

        event.getPlayer().sendMessage("其他放置");
    }

    private boolean placeBanner(ItemData item, BlockPlaceEvent event) {
        if (!item.getItem().getType().name().endsWith("_BANNER")) return false;
        Banner banner = (Banner) event.getBlockPlaced().getState();

        PersistentDataContainer container = banner.getPersistentDataContainer();

        // 保存 簽證ID
        if (item.hasUUID()) {
            container.set(ItemData.getUUIDKey(), PersistentDataType.STRING, item.getUUIDString());
        }

        // 保存 命名物品
        if (item.getItem().getItemMeta().hasDisplayName()) {
            container.set(ItemData.getDisplayNameKey(), PersistentDataType.STRING, item.getItem().getItemMeta().getDisplayName());
        }

        // 更新旗幟
        banner.update(true);
        return true;
    }
}

