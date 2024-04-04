package com.heynight0712.hnplayersignature.listeners;

import com.heynight0712.hnplayersignature.utils.SignItem;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class BlockPlace implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        SignItem signItem = new SignItem(event.getItemInHand());
        Block block = event.getBlockPlaced();

        if (onBanner(signItem, block)) return;


    }

    private boolean onBanner(SignItem signItem, Block block) {
        // 檢查 手上物品 是否旗幟
        if (signItem.getItem().getItemMeta() instanceof BannerMeta) {
            // 檢查 是否 簽名旗幟
            if (signItem.hasSing()) {

                // 檢查 方塊狀態 是否旗幟
                if (block.getState() instanceof Banner) {
                    Banner banner = (Banner) block.getState();
                    PersistentDataContainer container = banner.getPersistentDataContainer();
                    container.set(signItem.getUuidKey(), PersistentDataType.STRING, signItem.getSingUUID());
                    banner.update();
                }
            }
            return true;
        }
        return false;
    }
}
