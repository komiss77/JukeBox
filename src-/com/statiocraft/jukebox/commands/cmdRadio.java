package com.statiocraft.jukebox.commands;

import com.statiocraft.jukebox.fromapi.util.StringUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdRadio implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be performed by players!");
            return true;
        } else {
            ((Player) sender).performCommand("music radio " + StringUtil.toFormattedList(args, 0, " "));
            return true;
        }
    }
}
