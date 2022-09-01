package fr.silenthill99.principalplugin.inventory.hook.options;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.holder.options.OptionsAdminHolder;

public class OptionsAdminInventory extends AbstractInventory<OptionsAdminHolder> {

	public ItemStack whitelist_off = new ItemBuilder(Material.GREEN_DYE)
			.setName(ChatColor.GREEN + "Activer la whitelist").toItemStack();
	public ItemStack whitelist_on = new ItemBuilder(Material.RED_DYE).setName(ChatColor.RED + "Désactiver la whitelist")
			.toItemStack();

	public OptionsAdminInventory() {
		super(OptionsAdminHolder.class);
	}

	@Override
	public void openInventory(Player p, Object... args) {
		Inventory options = createInventory(new OptionsAdminHolder(), 27, "Options Administrateur");
		if (Bukkit.getServer().hasWhitelist())
			options.setItem(0, whitelist_on);
		else
			options.setItem(0, whitelist_off);
		p.openInventory(options);
	}

	@Override
	public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, OptionsAdminHolder holder) {
		switch (current.getType()) {
		case GREEN_DYE: {
			Bukkit.getServer().setWhitelist(true);
			Bukkit.broadcastMessage(ChatColor.GREEN + "La whitelist a été activée");
			openInventory(player); // reopen inventory
			break;
		}
		case RED_DYE:
			Bukkit.getServer().setWhitelist(false);
			Bukkit.broadcastMessage(ChatColor.GREEN + "La whitelist a été désactivée");
			openInventory(player); // reopen inventory
			break;
		default:
			break;
		}
	}
}
