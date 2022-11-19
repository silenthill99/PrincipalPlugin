package fr.silenthill99.principalplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;

public class Menu implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msgl, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Cette commande ne peut être éxécutée par la console !");
			return false;
		}

		Player player = (Player) sender;

		if (args.length == 0) {
			InventoryManager.openInventory(player, InventoryType.MODO_PLAYER_CHOOSE);
			return false;
		}

		if (args.length >= 2) {
			player.sendMessage(ChatColor.DARK_RED + "Vous avez mis trop d'arguments");
			return false;
		}

		OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
		if (target == null)
			player.sendMessage(ChatColor.RED + "Joueur introuvable !");
		else
			InventoryManager.openInventory(player, InventoryType.MODO_PLAYER_MENU, target);
		return false;
	}
}
