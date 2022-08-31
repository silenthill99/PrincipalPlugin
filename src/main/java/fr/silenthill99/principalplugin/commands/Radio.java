package fr.silenthill99.principalplugin.commands;

import fr.silenthill99.principalplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Radio implements CommandExecutor, @Nullable TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String msg, @NotNull String[] args)
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
                Main.getInstance().logs.get(player.getUniqueId()).add(ChatColor.YELLOW + "[" + new Timestamp(System.currentTimeMillis()) + "] " + ChatColor.DARK_BLUE + player.getName() + ChatColor.BLUE + " a envoyé dans la radio des médecins " + ChatColor.AQUA + message);
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
                        message.append(part);
                    }
                }

                for (Player players : Bukkit.getOnlinePlayers())
                {
                    if (Main.isPlayerInGroup(players, "policier"))
                    {
                        players.sendMessage(ChatColor.BLUE + "[Radio Police] " + ChatColor.WHITE + player.getName() + " : " + message);
                    }
                }
                Main.getInstance().logs.get(player.getUniqueId()).add(ChatColor.YELLOW + "[" + new Timestamp(System.currentTimeMillis()) + "] " + ChatColor.DARK_BLUE + player.getName() + ChatColor.BLUE + " a envoyé dans la radio de la police " + ChatColor.AQUA + message);
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
                        message.append(part);
                    }
                }

                for (Player players : Bukkit.getOnlinePlayers())
                {
                    if (Main.isPlayerInGroup(players, "pompiers"))
                    {
                        players.sendMessage(ChatColor.DARK_RED + "[Radio Pompiers] " + ChatColor.WHITE + player.getName() + " : " + message);
                    }
                }
                Main.getInstance().logs.get(player.getUniqueId()).add(ChatColor.YELLOW + "[" + new Timestamp(System.currentTimeMillis()) + "] " + ChatColor.DARK_BLUE + player.getName() + ChatColor.BLUE + " a envoyé dans la radio des pompiers " + ChatColor.AQUA + message);
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

    private List<String> numero = new ArrayList<>();

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String msg, @NotNull String[] args) {
        numero.add("15");
        numero.add("17");
        numero.add("18");
        return numero;
    }
}
