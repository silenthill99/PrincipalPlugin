package fr.silenthill99.principalplugin.commands.staff;

import fr.silenthill99.principalplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Developpeur implements CommandExecutor
{
    Main main = Main.getInstance();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            System.out.println("Cette commande ne peut être éxécutée par la console !");
            return false;
        }

        Player player = (Player) sender;

        if (args.length != 1)
        {
            player.sendMessage(ChatColor.RED + "Veuillez faire /developpeur <on|off>");
            return false;
        }

        if (!args[0].equalsIgnoreCase("on") && !args[0].equalsIgnoreCase("off"))
        {
            player.sendMessage(ChatColor.RED + "\"" + args[0] + "\" n'est pas un argument valable !");
            return false;
        }

        if (args[0].equalsIgnoreCase("on"))
        {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent set developpeur");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "list");
            player.sendMessage(ChatColor.GREEN + "Vous êtes désormais en mode Développeur !");
            Bukkit.dispatchCommand(player, "skin clear");
        }
        else if (args[0].equalsIgnoreCase("off"))
        {
            if (!player.getGameMode().equals(GameMode.ADVENTURE))
            {
                player.setGameMode(GameMode.ADVENTURE);
            }
            Bukkit.dispatchCommand(player, "fly off");
            Bukkit.dispatchCommand(player, "god off");
            Bukkit.dispatchCommand(player, "vanish off");
            Bukkit.getScheduler().runTaskLater(main, () -> {
                Bukkit.dispatchCommand(player, "lp user " + player.getName() + " parent set default");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"list");
                player.sendMessage(ChatColor.GREEN + "Vous n'êtes plus en mode Développeur !");
            }, 20);
        }
        return false;
    }
}
