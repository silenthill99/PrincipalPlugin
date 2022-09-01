package fr.silenthill99.principalplugin.inventory.hook.direction;

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
import fr.silenthill99.principalplugin.inventory.holder.direction.DirectionMenuHolder;

public class DirectionMenuInventory extends AbstractInventory<DirectionMenuHolder> {

	public DirectionMenuInventory() {
		super(DirectionMenuHolder.class);
	}

	@Override
	public void openInventory(Player p, Object... args) {
		OfflinePlayer target = (OfflinePlayer) args[0];
        Inventory direction = createInventory(new DirectionMenuHolder(target), 27, "Menu direction");
        direction.setItem(0, RETOUR);
        direction.setItem(1, new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Erreurs staff").toItemStack());
        direction.setItem(2, new ItemBuilder(Material.BOOK).setName(ChatColor.YELLOW + "RankUp " + target.getName()).toItemStack());
        p.openInventory(direction);
	}
	
	@Override
	public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, DirectionMenuHolder holder) {
		if(current.getType().equals(Material.SUNFLOWER))
        	InventoryManager.openInventory(player, InventoryType.ADMIN_MENU, holder.getTarget());
		else if(current.getType().equals(Material.REDSTONE))
			InventoryManager.openInventory(player, InventoryType.DIRECTION_ERREUR_STAFF, holder.getTarget());
		else if(current.getType().equals(Material.BOOK))
			InventoryManager.openInventory(player, InventoryType.DIRECTION_RANK_UP, holder.getTarget());
	}
}
