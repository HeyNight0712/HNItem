package com.heynight0712.hnplayersignature.listeners;

import com.heynight0712.hnplayersignature.core.LanguageManager;
import com.heynight0712.hnplayersignature.data.Key;
import com.heynight0712.hnplayersignature.utils.data.ItemData;
import com.heynight0712.hnplayersignature.utils.data.ItemHandle;
import org.bukkit.Bukkit;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
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
        ItemData itemData = new ItemData(new ItemStack(block.getType()));

        BannerMeta bannerMeta = (BannerMeta) itemData.getItemStack().getItemMeta();
        if (bannerMeta == null) return false;
        bannerMeta.setPatterns(banner.getPatterns());

        // 添加回去 Key.UUID
        String playerUUID = bannerContainer.get(Key.UUID, PersistentDataType.STRING);
        ItemHandle.addSign(bannerMeta.getPersistentDataContainer(), playerUUID);

        // 返回名稱
        if (bannerContainer.has(Key.DisplayName, PersistentDataType.STRING)) {
            String displayName = bannerContainer.get(Key.DisplayName, PersistentDataType.STRING);
            bannerMeta.setDisplayName(displayName);
        }

        // 重新上 Lore
        Player player = Bukkit.getPlayer(UUID.fromString(playerUUID));
        String playerName = player != null ? player.getDisplayName() : Bukkit.getOfflinePlayer(UUID.fromString(playerUUID)).getName();
        String lore = LanguageManager.getString("item.lore");
        lore = lore.replace("%playername%", playerName != null ? playerName : "未知玩家");
        ItemHandle.addLore(bannerMeta, lore);


        itemData.getItemStack().setItemMeta(bannerMeta);
        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), itemData.getItemStack());
        event.setDropItems(false);

        return true;
    }
}
