package com.heynight0712.hnplayersignature.commands;

import com.heynight0712.hnplayersignature.core.LanguageManager;
import com.heynight0712.hnplayersignature.utils.data.ItemHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class SignCommand implements CommandExecutor {
    private ItemHandler itemHandler;


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            // 獲取 手上物品
            itemHandler = new ItemHandler(player.getInventory().getItemInMainHand());
            ItemMeta itemMeta = itemHandler.getItemData().getItem().getItemMeta();

            // 檢查 是否為空
            if (itemMeta == null) {
                player.sendMessage(LanguageManager.getString("commands.sign.error.no_item"));
                return true;
            }

            // 其他指令判定
            if (strings.length > 0) {
                if (strings[0].equalsIgnoreCase("reload")) {
                    return true;
                }
            }

            // 添加/移除 簽名
            sign(player, itemMeta);
            return true;
        }
        commandSender.sendMessage(LanguageManager.getString("commands.sign.error.not_player"));
        return true;
    }

    private void sign(Player player, ItemMeta itemMeta) {
        // 檢查是否簽名
        if (itemHandler.getItemData().hasUUID()) {
            // 檢查 是否相同 簽證ID
            if (player.getUniqueId().toString().equals(itemHandler.getItemData().getUUIDString())) {
                itemHandler.removeSign(itemMeta);
                player.sendMessage(LanguageManager.getString("commands.sign.success.remove"));
                return;
            } else {
                String not_owner = LanguageManager.getString("commands.sign.error.not_owner");
                Player ownerPlayer = Bukkit.getPlayer(UUID.fromString(itemHandler.getItemData().getUUIDString()));
                String ownerName = ownerPlayer != null ? ownerPlayer.getDisplayName() : Bukkit.getOfflinePlayer(UUID.fromString(itemHandler.getItemData().getUUIDString())).getName();
                not_owner = not_owner.replace("%playername%", ownerName != null ? ownerName : "未知玩家");
                player.sendMessage(not_owner);
                return;
            }
        }

        // 檢查 是否有 Lore
        if (itemMeta.hasLore()) {
            player.sendMessage(LanguageManager.getString("commands.sign.error.has_lore"));
            return;
        }
        // 簽名
        itemHandler.setSign(player.getUniqueId().toString(), itemMeta);
        player.sendMessage(LanguageManager.getString("commands.sign.success.add"));
        player.getInventory().setItemInMainHand(itemHandler.getItemData().getItem());
    }
}
