package fr.silenthill99.principalplugin.inventory.hook.options;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import fr.silenthill99.principalplugin.inventory.holder.options.OptionsJoueurHolder;

public class OptionsJoueurInventory extends AbstractInventory<OptionsJoueurHolder> {

	public OptionsJoueurInventory() {
		super(OptionsJoueurHolder.class);
	}

	@Override
	public void openInventory(Player p, Object... args) {
		OfflinePlayer target = (OfflinePlayer) args[0];
		Inventory options = createInventory(new OptionsJoueurHolder(target), 9, "Options | " + target.getName());
		options.setItem(0,
				new ItemBuilder(Material.ENDER_PEARL).setName(ChatColor.GREEN + "Se téléporter").toItemStack());
		options.setItem(1, new ItemBuilder(Material.ENDER_EYE).setName(ChatColor.GREEN + "Téléporter le joueur ici")
				.toItemStack());
		options.setItem(2,
				new ItemBuilder(Material.LAPIS_LAZULI).setName(ChatColor.BLUE + "Voir les logs").toItemStack());
		options.setItem(8, RETOUR);
		p.openInventory(options);
	}

	@Override
	public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, OptionsJoueurHolder holder) {
		OfflinePlayer target = holder.getTarget();
		switch (current.getType()) {
		case ENDER_PEARL: {
			if (!target.isOnline()) {
				player.sendMessage(ChatColor.RED + "Ce joueur n'est pas connecté ou n'existe pas !");
				return;
			}
			player.teleport(target.getPlayer().getLocation());
			break;
		}
		case ENDER_EYE: {
			if (!target.isOnline()) {
				player.sendMessage(ChatColor.RED + "Ce joueur n'est pas connecté ou n'existe pas !");
				return;
			}
			target.getPlayer().teleport(player.getLocation());
			break;
		}
		case LAPIS_LAZULI: {
			player.closeInventory();
			Bukkit.dispatchCommand(player, "logs " + target.getName());
			break;
		}
		case SUNFLOWER: {
			InventoryManager.openInventory(player, InventoryType.MODO_PLAYER_MENU, target);
			break;
		}
		default: {
			break;
		}
		}
	}
}
