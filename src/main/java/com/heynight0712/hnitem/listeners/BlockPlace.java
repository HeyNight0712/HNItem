package com.heynight0712.hnitem.listeners;

import com.heynight0712.hnitem.data.KeyManager;
import com.heynight0712.hnitem.utils.data.ItemData;
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

    private void placeBanner(ItemData itemData, BlockPlaceEvent event) {
        if (!itemData.getItemStack().getType().name().endsWith("_BANNER")) return;
        Banner banner = (Banner) event.getBlockPlaced().getState();

        PersistentDataContainer bannerContainer = banner.getPersistentDataContainer();
        PersistentDataContainer itemContainer = itemData.getPersistentDataContainer();
        // 保存 簽證ID
        if (itemContainer.has(KeyManager.getUUID())) {
            bannerContainer.set(KeyManager.getUUID(), PersistentDataType.STRING, itemContainer.get(KeyManager.getUUID(), PersistentDataType.STRING));
        }

        // 保存 命名物品
        if (itemData.getItemStack().getItemMeta().hasDisplayName()) {
            bannerContainer.set(KeyManager.getDisplayName(), PersistentDataType.STRING, itemData.getItemStack().getItemMeta().getDisplayName());
        }

        // 更新旗幟
        banner.update(true);
    }
}

