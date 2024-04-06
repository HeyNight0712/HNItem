package com.heynight0712.hnplayersignature.listeners;

import com.heynight0712.hnplayersignature.utils.SignItem;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class BlockBreak implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        if (onBanner(block, event)) return;
    }

    private boolean onBanner(Block block, BlockBreakEvent event) {
        if (block.getState() instanceof Banner) {
            Banner banner = (Banner) block.getState();
            PersistentDataContainer container = banner.getPersistentDataContainer();
            if (container.has(SignItem.getUuidKey(), PersistentDataType.STRING)) {
                String playerUUID = container.get(SignItem.getUuidKey(), PersistentDataType.STRING);

                // 重新賦予 掉落物
                SignItem signItem = new SignItem(new ItemStack(block.getType()));
                ItemMeta itemMeta = signItem.getItem().getItemMeta();
                signItem.addSign(playerUUID, itemMeta);
                event.getBlock().getWorld().dropItemNaturally(block.getLocation(), signItem.getItem());
                event.setDropItems(false);
                return true;
            }
        }
        return false;
    }
}
