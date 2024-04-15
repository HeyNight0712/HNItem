package com.heynight0712.hnplayersignature.listeners;

import com.heynight0712.hnplayersignature.data.Key;
import com.heynight0712.hnplayersignature.utils.data.ItemData;
import org.bukkit.block.Banner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class BlockPlace implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        ItemData itemData = new ItemData(event.getItemInHand());

        placeBanner(itemData, event);

    }

    private boolean placeBanner(ItemData itemData, BlockPlaceEvent event) {
        if (!itemData.getItemStack().getType().name().endsWith("_BANNER")) return false;
        Banner banner = (Banner) event.getBlockPlaced().getState();

        PersistentDataContainer bannerContainer = banner.getPersistentDataContainer();
        PersistentDataContainer itemContainer = itemData.getPersistentDataContainer();
        // 保存 簽證ID
        if (itemContainer.has(Key.UUID)) {
            bannerContainer.set(Key.UUID, PersistentDataType.STRING, itemContainer.get(Key.UUID, PersistentDataType.STRING));
        }

        // 保存 命名物品
        if (itemData.getItemStack().getItemMeta().hasDisplayName()) {
            bannerContainer.set(Key.DisplayName, PersistentDataType.STRING, itemData.getItemStack().getItemMeta().getDisplayName());
        }

        // 更新旗幟
        banner.update(true);
        return true;
    }
}

