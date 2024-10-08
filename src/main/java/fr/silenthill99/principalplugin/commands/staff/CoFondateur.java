package fr.silenthill99.principalplugin.commands.staff;

import fr.silenthill99.principalplugin.Main;
import fr.silenthill99.principalplugin.Timer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoFondateur implements CommandExecutor
{
    Main main = Main.getInstance();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args)
    {
        if (!(sender instanceof Player))
        {
            System.out.println("Cette commande ne peut être éxécutée par la console !");
            return false;
        }

        Player player = (Player) sender;

        if (args.length != 1)
        {
            player.sendMessage(ChatColor.RED + "Veuillez faire /co-fondateur <on|off>");
            return false;
        }

        if (!args[0].equalsIgnoreCase("on") && !args[0].equalsIgnoreCase("off"))
        {
            player.sendMessage(ChatColor.RED + "\"" + args[0] + "\" n'est pas un argument valable !");
            return false;
        }

        if (args[0].equalsIgnoreCase("on"))
        {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent set co-fondateur");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "list");
            player.sendMessage(ChatColor.GREEN + "Vous avez activé le mode Co-Fondateur !");
            Bukkit.dispatchCommand(player, "skin clear");
            return false;
        }
        if (args[0].equalsIgnoreCase("off"))
        {
            if (!player.getGameMode().equals(GameMode.ADVENTURE))
            {
                player.setGameMode(GameMode.ADVENTURE);
            }
            Bukkit.dispatchCommand(player, "fly off");
            Bukkit.dispatchCommand(player, "vanish off");
            Bukkit.dispatchCommand(player, "god off");
            Bukkit.getScheduler().runTaskLater(main, new Timer(player), 20);
        }
        return false;
    }
}
