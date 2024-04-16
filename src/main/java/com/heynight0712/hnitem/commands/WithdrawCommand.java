package com.heynight0712.hnitem.commands;

import com.heynight0712.hnitem.Hooks.VaultHook;
import com.heynight0712.hnitem.data.item.Coin;
import com.heynight0712.hnitem.utils.data.DataHandle;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WithdrawCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            // 檢查 指令
            if (strings.length < 2) {
                commandSender.sendMessage(ChatColor.RED + "完整指令 /withdraw <幣值> <數量>");
                return true;
            }

            // 檢查 <幣值>
            if (!DataHandle.isInteger(strings[0]) || !(Integer.parseInt(strings[0]) >= 0)) {
                commandSender.sendMessage(ChatColor.RED + "這不是一個有效幣值");
                return true;
            }

            // 檢查 <數量>
            if (!DataHandle.isInteger(strings[1]) || !(Integer.parseInt(strings[1]) >= 0)) {
                commandSender.sendMessage(ChatColor.RED + "這不是一個有效數量");
                return true;
            }
            // 上限 <數量>
            if (!(Integer.parseInt(strings[1]) <= 64)) {
                commandSender.sendMessage(ChatColor.RED + "最大提領一組數量");
                return true;
            }

            // 數值控制
            Player player = (Player) commandSender;
            double value = (Integer.parseInt(strings[0]) * Integer.parseInt(strings[1]));
            Economy econ = VaultHook.getEconomy();

            // 檢查空間
            if (player.getInventory().firstEmpty() == -1) {
                commandSender.sendMessage(ChatColor.RED + "背包已滿");
                return true;
            }

            // 檢查 餘額
            if (!(econ.getBalance(player) >= value)) {
                commandSender.sendMessage(ChatColor.RED + "餘額不足");
                return true;
            }

            // 判定成功
            EconomyResponse response = econ.withdrawPlayer(player, value);
            if (response.transactionSuccess()) {
                Coin coinItem = new Coin(Integer.parseInt(strings[0]));
                player.getInventory().addItem(coinItem.getItem(Integer.parseInt(strings[1])));
                commandSender.sendMessage(String.format(ChatColor.YELLOW + "已提領 %s 餘額: %s", econ.format(value), econ.format(econ.getBalance(player))));
                return true;
            }else {
                commandSender.sendMessage(ChatColor.RED + "錯誤!!");
            }


            return true;
        }
        commandSender.sendMessage(ChatColor.RED + "這個指令只能由玩家執行");
        return true;
    }
}
