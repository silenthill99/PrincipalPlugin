package fr.silenthill99.principalplugin.commands;

import fr.silenthill99.principalplugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Showvanish implements CommandExecutor
{
    Main main = Main.getInstance();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String msg, String[] args)
    {
        if (main.getVanished().isEmpty())
        {
            sender.sendMessage(ChatColor.RED + "Il n'y a actuellement personne en vanish !");
            return false;
        }

        sender.sendMessage("Liste des staff en vanish : ");
        for (String v : main.getVanished())
        {
            sender.sendMessage(v);
        }
        return false;
    }
}
