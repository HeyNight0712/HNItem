package com.heynight0712.hnitem.commands;

import com.heynight0712.hnitem.Hooks.VaultHook;
import com.heynight0712.hnitem.core.LanguageManager;
import com.heynight0712.hnitem.data.item.Coin;
import com.heynight0712.hnitem.utils.data.DataHandle;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WithdrawCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            // 檢查 指令
            if (strings.length < 2) {
                commandSender.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.Withdraw.Fail.IncompleteCommand"));
                return true;
            }

            // 檢查 <幣值>
            if (!DataHandle.isInteger(strings[0]) || !(Integer.parseInt(strings[0]) >= 0)) {
                commandSender.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.Withdraw.Fail.InvalidValue"));
                return true;
            }

            // 檢查 <數量>
            if (!DataHandle.isInteger(strings[1]) || !(Integer.parseInt(strings[1]) >= 0)) {
                commandSender.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.Withdraw.Fail.InvalidAmount"));
                return true;
            }
            // 上限 <數量>
            if (!(Integer.parseInt(strings[1]) <= 64)) {
                commandSender.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.Withdraw.Fail.ExceededAmount"));
                return true;
            }

            // 數值控制
            double value = (Integer.parseInt(strings[0]) * Integer.parseInt(strings[1]));
            Economy econ = VaultHook.getEconomy();

            // 檢查空間
            if (player.getInventory().firstEmpty() == -1) {
                commandSender.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.Withdraw.Fail.InventoryFull"));
                return true;
            }

            // 檢查 餘額
            if (!(econ.getBalance(player) >= value)) {
                commandSender.sendMessage(LanguageManager.title + LanguageManager.getString("Commands.Withdraw.Fail.InsufficientBalance"));
                return true;
            }

            // 判定成功
            EconomyResponse response = econ.withdrawPlayer(player, value);
            if (response.transactionSuccess()) {
                Coin coinItem = new Coin(Integer.parseInt(strings[0]));
                player.getInventory().addItem(coinItem.getItem(Integer.parseInt(strings[1])));

                // 輸出轉換
                String success = LanguageManager.getString("Commands.Withdraw.Success");
                success = success.replace("%value%", String.valueOf(value));
                success = success.replace("%balance%", econ.format(econ.getBalance(player)));

                commandSender.sendMessage(LanguageManager.title + success);
                return true;
            }else {
                commandSender.sendMessage(LanguageManager.title + LanguageManager.getString("Error"));
            }


            return true;
        }
        commandSender.sendMessage(LanguageManager.title + LanguageManager.getString("NotPlayer"));
        return true;
    }
}
