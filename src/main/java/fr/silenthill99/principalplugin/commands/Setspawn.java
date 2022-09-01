package fr.silenthill99.principalplugin.commands;

import fr.silenthill99.principalplugin.Main;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Setspawn implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String msg, @NotNull String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage("Cette commande ne peut pas être éxécutée par la console !");
            return false;
        }

        Player player = (Player) sender;
        Location s = Main.getInstance().getSpawn();
        s.set(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
        s.setYaw(player.getLocation().getYaw());
        s.setPitch(player.getLocation().getPitch());
        s.setWorld(player.getWorld());
        sender.sendMessage(ChatColor.GREEN + "Spawn modifié !");
        return false;
    }
}
