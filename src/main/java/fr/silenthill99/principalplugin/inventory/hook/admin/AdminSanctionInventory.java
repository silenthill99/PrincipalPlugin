package fr.silenthill99.principalplugin.inventory.hook.admin;

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
import fr.silenthill99.principalplugin.inventory.holder.admin.AdminSanctionHolder;

public class AdminSanctionInventory extends AbstractInventory<AdminSanctionHolder> {

	public AdminSanctionInventory() {
		super(AdminSanctionHolder.class);
	}

	@Override
	public void openInventory(Player p, Object... args) {
		OfflinePlayer target = (OfflinePlayer) args[0];

		Inventory inv = createInventory(new AdminSanctionHolder(target), 18, "Sanctions Administratives");
		inv.setItem(0, new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Fraude").toItemStack());
		inv.setItem(1, new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Attaque DDoS").toItemStack());
		inv.setItem(2, new ItemBuilder(Material.REDSTONE)
				.setName(ChatColor.DARK_RED + "Contournement de sanctions / Double compte").toItemStack());
		inv.setItem(9, RETOUR);
		p.openInventory(inv);
	}

	@Override
	public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, AdminSanctionHolder holder) {
		OfflinePlayer target = holder.getTarget();
		switch (current.getType()) {
		case REDSTONE:
			if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "Fraude")) {
				player.closeInventory();
				Bukkit.dispatchCommand(player, "ipban " + target.getName() + " Fraude");
				return;
			}
			if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "Attaque DDoS")) {
				player.closeInventory();
				Bukkit.dispatchCommand(player, "ipban " + target.getName() + " Attaque DDoS");
				return;
			}
			if (current.getItemMeta().getDisplayName()
					.equals(ChatColor.DARK_RED + "Contournement de sanctions / Double compte")) {
				player.closeInventory();
				Bukkit.dispatchCommand(player,
						"ipban " + target.getName() + " Contournement de sanctions / Double compte");
				return;
			}
			break;
		case SUNFLOWER:
			InventoryManager.openInventory(player, InventoryType.ADMIN_MENU, target);
			break;
		default:
			break;
		}
	}
}
