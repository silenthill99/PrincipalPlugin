package fr.silenthill99.principalplugin.commands.staff;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.silenthill99.principalplugin.Main;
import org.jetbrains.annotations.NotNull;

public class Builder implements CommandExecutor
{
    Main main = Main.getInstance();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String msg, String[] args)
    {
        if (!(sender instanceof Player))
        {
            System.out.println("Cette commande ne peut être éxécutée par la console !");
            return false;
        }

        Player player = (Player) sender;

        if (args.length != 1)
        {
            player.sendMessage(ChatColor.RED + "Veuillez faire /builder <on|off>");
            return false;
        }

        if (!args[0].equalsIgnoreCase("on") && !args[0].equalsIgnoreCase("off"))
        {
            player.sendMessage(ChatColor.RED + "\"" + args[0] + "\" n'est pas un argument valable !");
            return false;
        }

        if (args[0].equalsIgnoreCase("on"))
        {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent set builder");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "list");
            player.sendMessage(ChatColor.GREEN + "Vous avez activé le mode Builder !");
            Bukkit.dispatchCommand(player, "skin clear");
        }
        else if (args[0].equalsIgnoreCase("off"))
        {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "fly " + player.getName() + " off");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "god " + player.getName() + " off");
            if (main.isVanished(player))
            {
                Bukkit.dispatchCommand(player, "vanish off");
            }
            if (!player.getGameMode().equals(GameMode.ADVENTURE))
            {
                player.setGameMode(GameMode.ADVENTURE);
            }
            Bukkit.getScheduler().runTaskLater(main, () -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent set default");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "list");
                player.sendMessage(ChatColor.GREEN + "Vous avez désactivé le mode Builder !");
            }, 20);

        }
        return false;
    }
}
