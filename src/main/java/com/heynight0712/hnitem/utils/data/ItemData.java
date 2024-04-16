package com.heynight0712.hnitem.utils.data;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

public class ItemData {
    private final ItemStack itemStack;

    public ItemData(ItemStack item) {
        itemStack = item;
    }

    public PersistentDataContainer getPersistentDataContainer() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) throw new IllegalStateException("Null ItemMeta");
        return itemMeta.getPersistentDataContainer();
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
