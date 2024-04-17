package com.heynight0712.hnitem.commands;

import com.heynight0712.hnitem.core.LanguageManager;
import com.heynight0712.hnitem.data.Key;
import com.heynight0712.hnitem.utils.data.DataHandle;
import com.heynight0712.hnitem.utils.data.ItemData;
import com.heynight0712.hnitem.utils.data.ItemHandle;
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
                player.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.Sign.Fail.NotItem"));
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
                String not_owner = LanguageManager.getString("Commands.Sign.Fail.NotOwner");
                String ownerName = DataHandle.getPlayerName(UUID.fromString(container.get(Key.UUID, PersistentDataType.STRING)));
                not_owner = not_owner.replace("%playername%", ownerName != null ? ownerName : LanguageManager.getString("NotFoundPlayer"));
                player.sendMessage(LanguageManager.title + not_owner);
            }

            return true;
        }
        commandSender.sendMessage(LanguageManager.title + LanguageManager.getString("NotPlayer"));
        return true;
    }

    /**
     * 添加簽名
     */
    private void add() {
        ItemMeta itemMeta = itemData.getItemStack().getItemMeta();

        // add
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.set(Key.UUID, PersistentDataType.STRING, player.getUniqueId().toString());
        ItemHandle.addLore(itemMeta, "Lore.Sign", "%playername%", player.getName());
        itemData.getItemStack().setItemMeta(itemMeta);

        player.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.Sign.Success.Add"));
    }

    private void remove() {
        ItemMeta itemMeta = itemData.getItemStack().getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        itemMeta.setLore(null);
        container.remove(Key.UUID);
        itemData.getItemStack().setItemMeta(itemMeta);

        player.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.Sign.Success.Remove"));
    }
}
