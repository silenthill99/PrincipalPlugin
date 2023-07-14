package fr.silenthill99.principalplugin.commands;

import fr.silenthill99.principalplugin.CustomFiles;
import fr.silenthill99.principalplugin.Main;
import fr.silenthill99.principalplugin.Variables;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Radio implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand( CommandSender sender, Command cmd, String msg, String[] args)
    {
        if (! (sender instanceof Player))
        {
            sender.sendMessage("Cette commande ne peut être éxécutée par la console !");
            return false;
        }

        Player player = (Player) sender;

        if (args.length < 2)
        {
            player.sendMessage(ChatColor.RED + "Erreur ! Veuillez faire /radio <15|17|18> <message>");
            return false;
        }

        switch (args[0])
        {
            case "15":
            {
                if (!Main.isPlayerInGroup(player, "medecin"))
                {
                    player.sendMessage(ChatColor.DARK_RED + "Vous n'avez pas accès à la radio des médecins");
                    return false;
                }
                StringBuilder message = new StringBuilder();

                for (String part : args)
                {
                    if (!part.equalsIgnoreCase(args[0]))
                    {
                        message.append(part + " ");
                    }
                }

                for (Player players : Bukkit.getOnlinePlayers())
                {
                    if (Main.isPlayerInGroup(players, "medecin"))
                    {
                        players.sendMessage(ChatColor.DARK_GREEN + "[Radio Médecins] " + ChatColor.WHITE + player.getName() + " : " + message);
                    }
                }
                try {
                    CustomFiles.LOGS.addLog(player, ChatColor.DARK_BLUE + player.getName() + ChatColor.BLUE + " a dit dans la radio des médecins " + ChatColor.AQUA + message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
            case "17":
            {
                if (!Main.isPlayerInGroup(player, "policier"))
                {
                    player.sendMessage(ChatColor.DARK_RED + "Vous n'avez pas accès à la radio des forces de l'ordre !");
                    return false;
                }

                StringBuilder message = new StringBuilder();

                for (String part : args)
                {
                    if (!part.equalsIgnoreCase(args[0]))
                    {
                        message.append(part + " ");
                    }
                }

                for (Player players : Bukkit.getOnlinePlayers())
                {
                    if (Main.isPlayerInGroup(players, "policier"))
                    {
                        players.sendMessage(ChatColor.BLUE + "[Radio Police] " + ChatColor.WHITE + player.getName() + " : " + message);
                    }
                }
                try {
                    CustomFiles.LOGS.addLog(player, ChatColor.DARK_BLUE + player.getName() + ChatColor.BLUE + " a dit dans la radio des forces de l'ordre " + ChatColor.AQUA + message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
            case "18":
            {
                if (!Main.isPlayerInGroup(player, "pompier"))
                {
                    player.sendMessage(ChatColor.DARK_RED + "Vous n'avez pas accès à la radio des pompiers !");
                    return false;
                }

                StringBuilder message = new StringBuilder();

                for (String part : args)
                {
                    if (!part.equalsIgnoreCase(args[0]))
                    {
                        message.append(part + " ");
                    }
                }

                for (Player players : Bukkit.getOnlinePlayers())
                {
                    if (Main.isPlayerInGroup(players, "pompier"))
                    {
                        players.sendMessage(ChatColor.DARK_RED + "[Radio Pompiers] " + ChatColor.WHITE + player.getName() + " : " + message);
                    }
                }
                try {
                    CustomFiles.LOGS.addLog(player, ChatColor.DARK_BLUE + player.getName() + ChatColor.BLUE + " a dit dans la radio des pompiers " + ChatColor.AQUA + message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
            default:
            {
                player.sendMessage(ChatColor.RED + "\"" + args[0] + "\" n'est pas un argument valable !");
                break;
            }
        }

        return false;
    }

    private final List<String> numero = new ArrayList<>();

    @Override
    public List<String> onTabComplete(CommandSender sender,Command cmd, String msg, String[] args)
    {
        String prefix = args[args.length - 1].toLowerCase(Locale.ROOT);
        for (String s : Arrays.asList("15", "17", "18"))
        {
            if (prefix.isEmpty() || s.startsWith(prefix))
            {
                numero.add(s);
            }
        }
        return numero;
    }
}
