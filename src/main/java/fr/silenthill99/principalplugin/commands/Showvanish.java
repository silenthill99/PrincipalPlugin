package fr.silenthill99.principalplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Showvanish implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String msg, @NotNull String[] args)
    {
        if (Vanish.getVanished().isEmpty())
        {
            sender.sendMessage(ChatColor.RED + "Il n'y a actuellement personne en vanish !");
            return false;
        }

        sender.sendMessage("Liste des staff en vanish : ");
        for (String v : Vanish.getVanished())
        {
            sender.sendMessage(v);
        }
        return false;
    }
}
