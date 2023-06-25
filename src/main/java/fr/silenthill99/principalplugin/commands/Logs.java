package fr.silenthill99.principalplugin.commands;

import fr.silenthill99.principalplugin.CustomFiles;
import fr.silenthill99.principalplugin.Variables;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class Logs implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args)
    {
        if (args.length == 0)
        {
            sender.sendMessage(ChatColor.RED + "Veuillez faire /logs <joueur>");
            return false;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        YamlConfiguration config = YamlConfiguration.loadConfiguration(CustomFiles.LOGS.getFile());
        ConfigurationSection configurationSection = config.getConfigurationSection(target.getName() + ".");

        if (configurationSection == null)
        {
            sender.sendMessage(ChatColor.RED + "Ce joueur n'a pas de logs !");
            return false;
        }
        sender.sendMessage(ChatColor.RED + "Le système de logs est en cours de redéveloppement");
        return false;
    }
}
