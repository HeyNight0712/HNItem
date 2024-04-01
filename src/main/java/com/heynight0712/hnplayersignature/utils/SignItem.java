package com.heynight0712.hnplayersignature.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SignItem {
    private ItemStack item;

    public SignItem(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }

    // 添加簽名
    // 需要過濾 已簽名物品/上面本身有lore
    public boolean addSign(Player player) {
        return false;
    }

    // 移除簽名
    // 需要過濾 未簽名物品/簽名物品上面不是相同玩家
    // (未來) 含 lore 物品 要刪除特定物品
    // 如果是管理員則強制移除
    public boolean removeSign(Player player, boolean admin) {
        return false;
    }
}
