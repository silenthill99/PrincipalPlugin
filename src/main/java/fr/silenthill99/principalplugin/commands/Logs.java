package fr.silenthill99.principalplugin.commands;

import fr.silenthill99.principalplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Logs implements CommandExecutor
{

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String msg, @NotNull String[] args)
    {
        if (args.length == 0)
        {
            sender.sendMessage(ChatColor.RED + "Veuillez faire /logs <joueur>");
            return false;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        if (!Main.getInstance().logs.containsKey(target.getUniqueId()))
        {
            sender.sendMessage(ChatColor.RED + "Ce joueur n'a pas de logs !");
            return false;
        }

        for (String logs : Main.getInstance().logs.get(target.getUniqueId()))
        {
            sender.sendMessage(logs);
        }
        return false;
    }
}
