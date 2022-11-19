package fr.silenthill99.principalplugin.commands;

import fr.silenthill99.principalplugin.Variables;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Logs implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args)
    {
        if (args.length == 0)
        {
            sender.sendMessage(ChatColor.RED + "Veuillez faire /logs <joueur>");
            return false;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        if (!Variables.logs.containsKey(target.getUniqueId()))
        {
            sender.sendMessage(ChatColor.RED + "Ce joueur n'a pas de logs !");
            return false;
        }

        for (String logs : Variables.logs.get(target.getUniqueId()))
        {
            sender.sendMessage(logs);
        }
        return false;
    }
}
