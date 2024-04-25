package com.heynight0712.hnitem.listeners;

import com.heynight0712.hnitem.Hooks.VaultHook;
import com.heynight0712.hnitem.core.LanguageManager;
import com.heynight0712.hnitem.data.KeyManager;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
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

        if (!isPaper(item) || !isRightClick(event)) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !hasEconomyValue(meta)) return;

        double value = getEconomyValue(meta);
        int amount = calculateAmount(player, item);

        Economy econ = VaultHook.getEconomy();
        EconomyResponse resp = econ.depositPlayer(player, value);

        if (resp.transactionSuccess()) {
            String message = formatMessage(value, econ.getBalance(player));
            item.setAmount(item.getAmount() - amount);
            player.sendMessage(LanguageManager.title + message);
        }else {
            player.sendMessage(LanguageManager.title + LanguageManager.getString("Error"));
        }
    }


    private boolean isPaper(ItemStack item) {
        return item.getType() == Material.PAPER;
    }


    private boolean isRightClick(PlayerInteractEvent event) {
        return event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK;
    }


    private boolean hasEconomyValue(ItemMeta meta) {
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.has(KeyManager.getValue());
    }


    private double getEconomyValue(ItemMeta meta) {
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.get(KeyManager.getValue(), PersistentDataType.DOUBLE);
    }


    private int calculateAmount(Player player, ItemStack item) {
        int amount = 1;
        if (player.isSneaking()) {
            amount = item.getAmount();
        }
        return amount;
    }


    private String formatMessage(double value, double balance) {
        String useCoinItem = LanguageManager.getString("Player.UseCoinItem");
        useCoinItem = useCoinItem.replace("%value%", String.valueOf(value));
        useCoinItem = useCoinItem.replace("%balance%", String.valueOf(balance));
        return useCoinItem;
    }
}
