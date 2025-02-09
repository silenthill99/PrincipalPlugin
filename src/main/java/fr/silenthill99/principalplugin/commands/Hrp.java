package fr.silenthill99.principalplugin.commands;

import fr.silenthill99.principalplugin.CustomFiles;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Hrp implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String msg, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage("Cette commande ne peut être éxécutée par la console !");
            return false;
        }

        Player player = (Player) sender;

        LuckPerms api = LuckPermsProvider.get();
        User user = api.getUserManager().getUser(player.getName());

        if (args.length == 0)
        {
            player.sendMessage(ChatColor.RED + "Erreur ! Veuillez faire /hrp <message>");
            return false;
        }

        StringBuilder message = new StringBuilder();

        for (String part : args)
        {
            message.append(part + " ");
        }

        Bukkit.broadcastMessage(ChatColor.DARK_RED + "[/HRP] " + user.getCachedData().getMetaData().getPrefix().replace("&", "§") + player.getName() + ChatColor.WHITE + " : " + message);
        try {
            CustomFiles.LOGS.addLog(player, "&1" + player.getName() + " &9a dit dans le tchat HRP &b" + message);
        } catch (IOException e) {
            player.sendMessage(ChatColor.RED + "Erreur lors du chargement des logs !");
        }

        return false;
    }
}
