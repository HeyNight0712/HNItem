package com.heynight0712.hnitem.data.item;

import com.heynight0712.hnitem.core.LanguageManager;
import com.heynight0712.hnitem.data.KeyManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class Coin {
    private final ItemStack item;

    public Coin(double value) {
        this.item = createCoinItem(value);
    }

    /**
     * 創建 CoinItem
     * @param value 金額
     * @return 返回 CoinItem
     */
    private ItemStack createCoinItem(double value) {
        ItemStack coinItem = new ItemStack(Material.PAPER);
        ItemMeta meta = coinItem.getItemMeta();

        if (meta == null) throw new RuntimeException("Failed to initialize Coin");

        List<String> lore = generateLore(value);
        meta.setLore(lore);

        meta.getPersistentDataContainer().set(KeyManager.getValue(), PersistentDataType.DOUBLE, value);

        coinItem.setItemMeta(meta);
        return coinItem;
    }

    /**
     * 獲取 Lore List
     * @param value 金額
     * @return 返回 List<String>
     */
    private List<String> generateLore(double value) {
        List<String> lore = new ArrayList<>();
        List<String> langLore = LanguageManager.getConfig().getStringList("Item.Coin.Lore");
        for (String line : langLore) {
            line = ChatColor.translateAlternateColorCodes('&', line); // 設置顏色
            String processedLine = line.replace("%value%", String.format("%.0f", value));
            lore.add(processedLine);
        }
        return lore;
    }

    /**
     * 設置數量後返回 Item
     * @param amount 數量
     * @return Coin ItemStack
     */
    public ItemStack getItem(int amount) {
        item.setAmount(amount);
        return item;
    }
}
