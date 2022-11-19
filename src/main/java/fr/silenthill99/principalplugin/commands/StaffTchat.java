package fr.silenthill99.principalplugin.commands;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffTchat implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            System.out.println("Cette commande ne peut être éxécutée par la console !");
            return false;
        }

        Player player = (Player) sender;

        LuckPerms api = LuckPermsProvider.get();
        User user = api.getUserManager().getUser(player.getName());

        if (args.length == 0)
        {
            player.sendMessage(ChatColor.RED + "Vuillez faire /stafftchat <message>");
            return false;
        }

        StringBuilder bc = new StringBuilder();

        for (String part : args)
        {
            bc.append(part + " ");
        }

        for (Player players : Bukkit.getOnlinePlayers())
        {
            if (players.hasPermission("oxydia.staff"))
            {
                players.sendMessage(ChatColor.GRAY + "[Tchat du Staff] " + user.getCachedData().getMetaData().getPrefix().replace("&", "§") + player.getName() + " : " + ChatColor.GREEN + bc);
            }
        }

        return false;
    }
}
