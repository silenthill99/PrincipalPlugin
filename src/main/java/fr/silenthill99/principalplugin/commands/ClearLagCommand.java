package fr.silenthill99.principalplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClearLagCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        int value = 0;

        for (Entity entities : player.getWorld().getEntities()){
            if (entities instanceof Item) {
                value++;
                Item item = (Item) entities;
                item.remove();
            }
        }

        player.sendMessage(ChatColor.GREEN.toString() + value + " entités ont été enlevées");

        return true;
    }
}
