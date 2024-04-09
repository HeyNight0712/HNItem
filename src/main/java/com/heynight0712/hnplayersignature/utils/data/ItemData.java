package com.heynight0712.hnplayersignature.utils.data;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemData {
    private final ItemStack item;
    private static NamespacedKey UUIDKey;
    private static NamespacedKey displayNameKey;

    public ItemData(ItemStack item) {
        this.item = item;
    }

    /**
     * 初始化 NamespacedKey
     * @param javaPlugin 在啟動時自動套用
     */
    public static void initPlugin(JavaPlugin javaPlugin) {
        UUIDKey = new NamespacedKey(javaPlugin, "uuid");
        displayNameKey = new NamespacedKey(javaPlugin, "displayName");
    }

    /**
     * 移除 UUIDKey
     * @return 成功移除 返回 True
     */
    public boolean removeUUIDKey(ItemMeta itemMeta) {
        if (itemMeta == null) return false;
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.remove(UUIDKey);
        item.setItemMeta(itemMeta);
        return true;
    }

    /**
     * 移除 DisplayNameKey
     * @return 成功移除 返回 True
     */
    public boolean removeDisplayNameKey(ItemMeta itemMeta) {
        if (itemMeta == null) return false;
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.remove(displayNameKey);
        item.setItemMeta(itemMeta);
        return true;
    }

    /**
     * 設置 UUID
     * 如果 多次使用 則以最新為主
     * @param UUIDString Player UUID
     * @return 成功添加 返回 True
     */
    public boolean setUUID(String UUIDString, ItemMeta itemMeta) {
        if (itemMeta == null) return false;
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.set(UUIDKey, PersistentDataType.STRING, UUIDString);
        item.setItemMeta(itemMeta);
        return true;
    }

    /**
     * 設置 DisplayName
     * 如果多次使用 則以最新為主
     * @param DisplayNameString DisplayName String
     * @return 成功添加 返回 True
     */
    public boolean setDisplayName(String DisplayNameString) {
        if (item.getItemMeta() == null) return false;
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(displayNameKey, PersistentDataType.STRING, DisplayNameString);
        item.setItemMeta(meta);
        return true;
    }

    /**
     * 獲取 UUID String
     * @return 獲取不到則返回 Null
     */
    public String getUUIDString() {
        if (item.getItemMeta() == null) return null;
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        return container.get(UUIDKey, PersistentDataType.STRING);
    }

    /**
     * 獲取 DisplayName String
     * @return 取不到則返回 Null
     */
    public String getDisplayNameString() {
        if (item.getItemMeta() == null) return null;
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        return container.get(displayNameKey, PersistentDataType.STRING);
    }

    /**
     * 獲取是否有 UUID
     * @return 獲取成功 返回 True
     */
    public boolean hasUUID() {
        if (item.getItemMeta() == null) return false;
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        return container.has(UUIDKey, PersistentDataType.STRING);
    }

    /**
     * 獲取是否有 DisplayName
     * @return 成功獲取 返回 False
     */
    public boolean hasDisplayName() {
        if (item.getItemMeta() == null) return false;
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        return container.has(displayNameKey, PersistentDataType.STRING);
    }

    public static NamespacedKey getUUIDKey() {return UUIDKey;}

    public static NamespacedKey getDisplayNameKey() {return displayNameKey;}

    public ItemStack getItem() {return item;}


}
