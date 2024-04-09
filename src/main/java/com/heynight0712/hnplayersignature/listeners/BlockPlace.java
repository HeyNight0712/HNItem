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
        ItemHandler itemHandler = new ItemHandler(event.getItemInHand());
        if (itemHandler.getItemData().hasUUID()) {
            if (!itemHandler.getItemData().getItem().getType().name().endsWith("_BANNER")) return;

            Banner banner = (Banner) event.getBlockPlaced().getState();
            PersistentDataContainer container = banner.getPersistentDataContainer();
            container.set(ItemData.getUUIDKey(), PersistentDataType.STRING, itemHandler.getItemData().getUUIDString());
            banner.update(true);
            event.getPlayer().sendMessage("放置簽名 成功!");

        }
    }
}

