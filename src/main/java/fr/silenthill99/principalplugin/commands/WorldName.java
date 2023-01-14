package fr.silenthill99.principalplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldName implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage("Cette commande ne peut ^pas être éxécutée par la console !");
            return false;
        }

        Player player = (Player) sender;
        player.sendMessage(player.getWorld().getName());

        return false;
    }
}
