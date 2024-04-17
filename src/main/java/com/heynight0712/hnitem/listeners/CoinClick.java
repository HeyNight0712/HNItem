package com.heynight0712.hnitem.listeners;

import com.heynight0712.hnitem.Hooks.VaultHook;
import com.heynight0712.hnitem.core.LanguageManager;
import com.heynight0712.hnitem.data.Key;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class CoinClick implements Listener {
    @EventHandler
    public void onCoinClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // 檢查 紙
        if (item.getType() != Material.PAPER) return;

        // 檢查 右鍵
        if (!checkRight(event)) {return;}

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        // 檢查 經濟
        PersistentDataContainer container = meta.getPersistentDataContainer();
        if (!container.has(Key.Value)) return;

        // 控制數量
        double value = container.get(Key.Value, PersistentDataType.DOUBLE);
        int amount = 1;

        // 檢查 蹲下
        if (player.isSneaking()) {
            value = value * item.getAmount();
            amount = item.getAmount();
        }


        Economy econ = VaultHook.getEconomy();
        EconomyResponse resp = econ.depositPlayer(player, value);

        // 判定 經濟
        if (resp.transactionSuccess()) {

            // 輸入轉換
            String UseCoinItem = LanguageManager.getString("Player.UseCoinItem");
            UseCoinItem = UseCoinItem.replace("%value%", String.valueOf(value));
            UseCoinItem = UseCoinItem.replace("%balance%", String.valueOf(econ.getBalance(player)));
            item.setAmount(item.getAmount() - amount);

            player.sendMessage(LanguageManager.title + UseCoinItem);
        }else {
            player.sendMessage(LanguageManager.title + LanguageManager.getString("Error"));
        }
    }

    /**
     * 檢查 是否 右鍵 空氣/方塊
     * @param event PlayerInteractEvent
     * @return 如果是則返回 True
     */
    private boolean checkRight(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR) return true;
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) return true;
        return false;
    }
}
