package fr.silenthill99.principalplugin.commands;

import fr.silenthill99.principalplugin.Main;
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

        Main.getInstance().spawn.set(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
        Main.getInstance().spawn.setYaw(player.getLocation().getYaw());
        Main.getInstance().spawn.setPitch(player.getLocation().getPitch());
        Main.getInstance().spawn.setWorld(player.getWorld());

        return false;
    }
}
