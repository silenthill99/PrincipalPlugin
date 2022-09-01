package fr.silenthill99.principalplugin.commands;

import fr.silenthill99.principalplugin.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Stagiaire implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player))
        {
            System.out.println("Cette commande ne peut pas être éxécutée par la console !");
            return false;
        }

        Player player = (Player) sender;

        if (args.length != 1)
        {
            player.sendMessage(ChatColor.RED + "Veuillez faire /stagiaire <on|off>");
            return false;
        }

        if (!args[0].equalsIgnoreCase("on") && !args[0].equalsIgnoreCase("off"))
        {
            player.sendMessage(ChatColor.RED + "\"" + args[0] + "\" n'est pas un argument valable !");
            return false;
        }

        if (args[0].equalsIgnoreCase("on"))
        {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent set modo-stagiaire");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "list");
            player.sendMessage(ChatColor.GREEN + "Vous êtes désormais en mode modérateur stagiaire !");
        }
        else if (args[0].equalsIgnoreCase("off"))
        {
            Bukkit.dispatchCommand(player, "fly off");
            Bukkit.dispatchCommand(player, "god off");
            Bukkit.dispatchCommand(player, "vanish off");
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent set default");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "list");
                player.sendMessage(ChatColor.GREEN + "Vous avez quitté le mode modérateur stagiaire !");
            }, 20);
        }

        return false;
    }
}
