package fr.silenthill99.principalplugin.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import fr.silenthill99.principalplugin.Main;

public class Vanish implements CommandExecutor
{

    private static final List<String> vanished = new ArrayList<>();
    public static List<String> getVanished() {
		return vanished;
	}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String msg, @NotNull String[] args)
    {

        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        if (args.length != 1)
        {
            player.sendMessage(ChatColor.RED + "Veuillez faire /vanish <on|off>");
            return false;
        }

        if (!args[0].equalsIgnoreCase("on") && !args[0].equalsIgnoreCase("off"))
        {
            player.sendMessage(ChatColor.RED + "\"" + args[0] + "\" n'est pas un argument valable");
            return false;
        }

        if (args[0].equalsIgnoreCase("on"))
        {
            if (vanished.contains(player.getName()))
            {
                player.sendMessage(ChatColor.DARK_RED + "Vous êtes déjà en vanish !");
                return false;
            }
            for (Player players : Bukkit.getOnlinePlayers())
            {
                if (!players.hasPermission("oxydia.vanish.show"))
                {
                    players.hidePlayer(Main.getInstance(), player);
                }
            }
            vanished.add(player.getName());
            player.sendMessage(ChatColor.GREEN + "Vous êtes désormais en vanish !");
            return false;
        }

        if (args[0].equalsIgnoreCase("off"))
        {
            if (!vanished.contains(player.getName()))
            {
                player.sendMessage(ChatColor.DARK_RED + "Votre vanish est déjà désactivé !");
                return false;
            }
            for (Player players : Bukkit.getOnlinePlayers())
            {
                players.showPlayer(Main.getInstance(), player);
            }
            vanished.remove(player.getName());
            player.sendMessage(ChatColor.RED + "Vous avez désactivé votre vanish !");
            return false;
        }

        return false;
    }
}
