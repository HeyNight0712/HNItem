package com.heynight0712.hnitem.data.item;

import com.heynight0712.hnitem.core.LanguageManager;
import com.heynight0712.hnitem.data.Key;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class Coin {
    private static ItemStack item = new ItemStack(Material.PAPER);
    private double value;

    public Coin(double value) {
        this.value = value;



        // 初始化
        ItemMeta meta = item.getItemMeta();
        if (meta == null) throw new RuntimeException("初始化自訂義 經濟實體化失敗");

        // 獲取 language.yml Coin 文黨
        List<String> lore = new ArrayList<>();
        List<String> langLore = LanguageManager.getConfig().getStringList("Item.Coin.Lore");
        for (String line : langLore) {
            line = ChatColor.translateAlternateColorCodes('&', line); // 設置顏色
            String processedLine = line.replace("%value%", String.format("%.0f", value));
            lore.add(processedLine);
        }
        meta.setLore(lore);

        // 設置 NBT 數據
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(Key.Value, PersistentDataType.DOUBLE, value);

        item.setItemMeta(meta);
    }

    public double getValue() {return value;}

    public ItemStack getItem() {return item;}

    public ItemStack getItem(int amount) {
        item.setAmount(amount);
        return item;
    }
}
