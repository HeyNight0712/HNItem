package com.heynight0712.hnitem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class WithdrawCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();

        if (strings.length == 1) {
            list.add("Value");
        }

        if (strings.length == 2) {
            list.add("Amount");
        }

        return list;
    }
}
