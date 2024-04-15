package com.heynight0712.hnplayersignature.commands;

import com.heynight0712.hnplayersignature.core.LanguageManager;
import com.heynight0712.hnplayersignature.data.Key;
import com.heynight0712.hnplayersignature.utils.data.ItemData;
import com.heynight0712.hnplayersignature.utils.data.ItemHandle;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class SignCommand implements CommandExecutor {
    private ItemData itemData;
    private Player player;


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            player = (Player) commandSender;

            // 獲取 手上物品
            itemData = new ItemData(player.getInventory().getItemInMainHand());
            ItemMeta itemMeta = itemData.getItemStack().getItemMeta();

            // 檢查 是否為空
            if (itemMeta == null) {
                player.sendMessage(LanguageManager.getString("commands.sign.error.no_item"));
                return true;
            }

            // 檢查是否簽名
            PersistentDataContainer container = itemData.getPersistentDataContainer();
            if (!container.has(Key.UUID)) {
                add();
                return true;
            }

            // 檢查是否擁有者
            if (ItemHandle.isOwner(container, player)) {
                remove();
            } else {

                String not_owner = LanguageManager.getString("commands.sign.error.not_owner");
                Player ownerPlayer = Bukkit.getPlayer(UUID.fromString(container.get(Key.UUID, PersistentDataType.STRING)));
                String ownerName = ownerPlayer != null ? ownerPlayer.getDisplayName() : Bukkit.getOfflinePlayer(UUID.fromString(container.get(Key.UUID, PersistentDataType.STRING))).getName();
                not_owner = not_owner.replace("%playername%", ownerName != null ? ownerName : "未知玩家");
                player.sendMessage(not_owner);
            }

            return true;
        }
        commandSender.sendMessage(LanguageManager.getString("commands.sign.error.not_player"));
        return true;
    }

    private void add() {
        ItemMeta itemMeta = itemData.getItemStack().getItemMeta();
        // hasLore
        if (itemMeta == null || itemMeta.hasLore()) {
            player.sendMessage(LanguageManager.getString("commands.sign.error.has_lore"));
            return;
        }

        // add
        String playerUUID = player.getName();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        ItemHandle.addSign(container, player);
        ItemHandle.addLore(itemMeta, "item.lore", playerUUID);
        itemData.getItemStack().setItemMeta(itemMeta);

        player.sendMessage(LanguageManager.getString("commands.sign.success.add"));
    }

    private void remove() {
        ItemMeta itemMeta = itemData.getItemStack().getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        itemMeta.setLore(null);
        container.remove(Key.UUID);
        itemData.getItemStack().setItemMeta(itemMeta);

        player.sendMessage(LanguageManager.getString("commands.sign.success.remove"));
    }
}
