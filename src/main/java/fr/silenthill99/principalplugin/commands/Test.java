package fr.silenthill99.principalplugin.commands;

import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Test implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String msg, @NotNull String[] args)
    {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        InventoryManager.openInventory(player, InventoryType.TEST);
        return false;
    }
}