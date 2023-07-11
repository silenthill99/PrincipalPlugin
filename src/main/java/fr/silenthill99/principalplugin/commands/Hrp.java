package fr.silenthill99.principalplugin.commands;

import fr.silenthill99.principalplugin.CustomFiles;
import fr.silenthill99.principalplugin.Main;
import fr.silenthill99.principalplugin.Variables;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

public class Hrp implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage("Cette commande ne peutêtre éxécutée par la console !");
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
        YamlConfiguration config = YamlConfiguration.loadConfiguration(CustomFiles.LOGS.getFile());
        List<String> messages = config.getStringList(player.getName() + ".logs");
        messages.add("&e[" + new Timestamp(System.currentTimeMillis()) + "] &1" + player.getName() + " &9a dit dans le tchat HRP &b" + message);
        config.set(player.getName() + ".logs", messages);
        try {
            config.save(CustomFiles.LOGS.getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
