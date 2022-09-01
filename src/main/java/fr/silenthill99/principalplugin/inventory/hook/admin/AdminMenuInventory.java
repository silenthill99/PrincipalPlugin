package fr.silenthill99.principalplugin.inventory.hook.admin;

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
import fr.silenthill99.principalplugin.inventory.holder.admin.AdminMenuHolder;

public class AdminMenuInventory extends AbstractInventory<AdminMenuHolder> {

	public AdminMenuInventory() {
		super(AdminMenuHolder.class);
	}

	@Override
	public void openInventory(Player p, Object... args) {
		OfflinePlayer target = (OfflinePlayer) args[0];

		Inventory admin = createInventory(new AdminMenuHolder(target), 27, "Administration");
		admin.setItem(0, new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Sanctions administratives")
				.toItemStack());
		admin.setItem(8, RETOUR);
		admin.setItem(17, new ItemBuilder(Material.CLOCK).setName(ChatColor.YELLOW + "Menu direction").toItemStack());
		p.openInventory(admin);
	}

	@Override
	public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, AdminMenuHolder holder) {
		OfflinePlayer target = holder.getTarget();
		switch (current.getType()) {
		case SUNFLOWER: {
			InventoryManager.openInventory(player, InventoryType.MODO_PLAYER_MENU, target);
			break;
		}
		case REDSTONE: {
			InventoryManager.openInventory(player, InventoryType.ADMIN_SANCTION, target);
			break;
		}
		case CLOCK: {
			if (!player.hasPermission("oxydia.direction")) {
				player.sendMessage(ChatColor.DARK_RED + "Vous n'avez pas accès à ce panel !");
				return;
			}
			InventoryManager.openInventory(player, InventoryType.DIRECTION_MENU, target);
			break;
		}
		default: {
			break;
		}
		}
	}
}
