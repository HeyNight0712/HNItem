package com.heynight0712.hnplayersignature.commands;

import com.heynight0712.hnplayersignature.core.LanguageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SignCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            player.sendMessage(LanguageManager.getString("commands.sign.success", "&a成功!"));
            return true;
        }
        commandSender.sendMessage(LanguageManager.getString("commands.sign.no_player", "&c這個指令只能由玩家執行"));
        return true;
    }
}
