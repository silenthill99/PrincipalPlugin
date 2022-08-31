package fr.silenthill99.principalplugin.commands;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.Panel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class Menu implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage("Cette commande ne peut être éxécutée par la console !");
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0)
        {
            int slot = 0;
            Inventory menu = Bukkit.createInventory(null, 54, "Choisissez un joueur");
            for (Player players : Bukkit.getOnlinePlayers())
            {
                ItemBuilder tete = new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(players.getName()).setName(players.getName());
                menu.setItem(slot, tete.toItemStack());
                slot++;
            }
            player.openInventory(menu);
            return false;
        }

        if (args.length >= 2)
        {
            player.sendMessage(ChatColor.DARK_RED + "Vous avez mis trop d'arguments");
            return false;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        Panel.getInstance().panel_modo(player, target);
        return false;
    }
}
