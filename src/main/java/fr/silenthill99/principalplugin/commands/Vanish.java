package fr.silenthill99.principalplugin.commands;

import fr.silenthill99.principalplugin.Main;
import fr.silenthill99.principalplugin.timer.VanishMsg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Vanish implements CommandExecutor
{
    private final Main main = Main.getInstance();


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String msg, String[] args)
    {

        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        VanishMsg vanishMsg = new VanishMsg(player);

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
            if (main.isVanished(player))
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
            main.getVanished().add(player.getName());
            player.sendMessage(ChatColor.GREEN + "Vous êtes désormais en vanish !");
            vanishMsg.runTaskTimer(main, 0, 1);
            return false;
        }

        if (args[0].equalsIgnoreCase("off"))
        {
            if (!main.isVanished(player))
            {
                player.sendMessage(ChatColor.DARK_RED + "Votre vanish est déjà désactivé !");
                return false;
            }
            for (Player players : Bukkit.getOnlinePlayers())
            {
                players.showPlayer(Main.getInstance(), player);
            }
            main.getVanished().remove(player.getName());
            player.sendMessage(ChatColor.RED + "Vous avez désactivé votre vanish !");
            return false;
        }

        return false;
    }
}
