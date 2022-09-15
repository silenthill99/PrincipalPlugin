package fr.silenthill99.principalplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Action implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String msg, @NotNull String[] args)
    {
        if (!(sender instanceof Player))
        {
            System.out.println("Cette commande ne peut pas être éxécutée par la console !");
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0)
        {
            player.sendMessage(ChatColor.RED + "Veuillez faire /action <message>");
            return false;
        }

        StringBuilder message = new StringBuilder();

        for (String part : args)
        {
            message.append(part + " ");
        }

        for (Player players : Bukkit.getOnlinePlayers())
        {
            if (players.getLocation().distanceSquared(player.getLocation()) <= 100)
            {
                players.sendMessage(ChatColor.GRAY + player.getName() + " " + message);
            }
        }

        return false;
    }
}
