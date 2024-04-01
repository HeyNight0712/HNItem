package com.heynight0712.hnplayersignature.commands;

import com.heynight0712.hnplayersignature.core.LanguageManager;
import com.heynight0712.hnplayersignature.utils.SignItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SignCommand implements CommandExecutor {
    private SignItem signItem;


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (strings.length > 0) {

                // 獲取 手上物品
                signItem = new SignItem(player.getInventory().getItemInMainHand());
                ItemMeta itemMeta = signItem.getItem().getItemMeta();

                // 檢查 是否為空
                if (itemMeta == null) {
                    player.sendMessage(LanguageManager.getString("commands.sign.error.no_item"));
                    return true;
                }

                if (strings[0].equalsIgnoreCase("add")) {
                    add(player, itemMeta);
                    return true;
                }

                if (strings[0].equalsIgnoreCase("remove")) {
                    remove(player, itemMeta);
                    return true;
                }
            }

            player.sendMessage(LanguageManager.getString("commands.sign.success"));
            return true;
        }
        commandSender.sendMessage(LanguageManager.getString("commands.sign.error.not_player"));
        return true;
    }

    // add
    private void add(Player player, ItemMeta itemMeta) {
        // 檢查是否有 Lore
        if (itemMeta.hasLore()) {
            player.sendMessage(LanguageManager.getString("commands.sign.error.has_lore"));
            return;
        }

        signItem.addSign(player, itemMeta);
        player.getInventory().setItemInMainHand(signItem.getItem());
        player.sendMessage(LanguageManager.getString("commands.sign.success"));
    }

    // remove
    private void remove(Player player, ItemMeta itemMeta) {
        if (player.isOp()) {
            signItem.removeSign(player, itemMeta, true);
        } else {
            if (!signItem.removeSign(player, itemMeta, false)) {return;}
        }

        player.sendMessage(LanguageManager.getString("commands.sign.success"));
    }
}
