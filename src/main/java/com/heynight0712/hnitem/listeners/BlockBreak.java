package com.heynight0712.hnitem.listeners;

import com.heynight0712.hnitem.core.LanguageManager;
import com.heynight0712.hnitem.data.Key;
import com.heynight0712.hnitem.utils.data.DataHandle;
import com.heynight0712.hnitem.utils.data.ItemData;
import com.heynight0712.hnitem.utils.data.ItemHandle;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class BlockBreak implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        onBanner(block, event);
    }

    private boolean onBanner(Block block, BlockBreakEvent event) {
        if (!(block.getState() instanceof Banner)) return false;
        Banner banner = (Banner) block.getState();
        PersistentDataContainer bannerContainer = banner.getPersistentDataContainer();

        // 檢查是否簽名
        if (!(bannerContainer.has(Key.UUID, PersistentDataType.STRING))) return false;

        // 重新製作物品
        ItemData itemData = new ItemData(new ItemStack(DataHandle.conversionBanner(block.getType())));

        BannerMeta bannerMeta = (BannerMeta) itemData.getItemStack().getItemMeta();
        if (bannerMeta == null) return false;

        // 返回 旗幟樣式
        bannerMeta.setPatterns(banner.getPatterns());

        // 返回 Key.UUID
        String playerUUID = bannerContainer.get(Key.UUID, PersistentDataType.STRING);
        bannerMeta.getPersistentDataContainer().set(Key.UUID, PersistentDataType.STRING, playerUUID);

        // 返回 物品名稱
        if (bannerContainer.has(Key.DisplayName, PersistentDataType.STRING)) {
            String displayName = bannerContainer.get(Key.DisplayName, PersistentDataType.STRING);
            bannerMeta.setDisplayName(displayName);
        }

        // 添加 Lore
        String playerName = DataHandle.getPlayerName(UUID.fromString(playerUUID));
        ItemHandle.addLore(bannerMeta, "Lore.Sign", "%playername%", playerName != null ? playerName : LanguageManager.getString("NotFoundPlayer"));

        // 設置 item
        itemData.getItemStack().setItemMeta(bannerMeta);
        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), itemData.getItemStack());
        event.setDropItems(false);
        return true;
    }
}
