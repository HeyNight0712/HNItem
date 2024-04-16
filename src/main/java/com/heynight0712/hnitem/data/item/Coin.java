package com.heynight0712.hnitem.data.item;

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
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GOLD + "價值: " + ChatColor.YELLOW + value + " $");
        lore.add(" ");
        lore.add(ChatColor.DARK_GRAY + "右鍵- 消耗一張");
        lore.add(ChatColor.DARK_GRAY + "蹲下 + 右鍵- 消耗手上");
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
