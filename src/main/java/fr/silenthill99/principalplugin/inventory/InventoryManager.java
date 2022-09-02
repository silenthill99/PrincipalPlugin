package fr.silenthill99.principalplugin.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class InventoryManager implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		InventoryType.getValues().forEach((inv) -> inv.getInv().onPlayerJoin(e));
	}
	
	@EventHandler
	public void onLeft(PlayerQuitEvent e) {
		InventoryType.getValues().forEach((inv) -> inv.getInv().onPlayerLeft(e));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getClickedInventory() == null || !(e.getWhoClicked() instanceof Player))
			return;
		Player p = (Player) e.getWhoClicked();
		InventoryHolder holder = e.getClickedInventory().getHolder();
		if(holder instanceof SilenthillHolder) {
			SilenthillHolder nh = (SilenthillHolder) holder;
			for(InventoryType type : InventoryType.values()) {
				AbstractInventory inv = type.getInv();
				if(inv.isInstance(nh)) { // found inventory
					e.setCancelled(true);
					ItemStack item = e.getCurrentItem();
					if(item != null) {
						if(item.isSimilar(AbstractInventory.CLOSE))
							p.closeInventory();
						else
							inv.manageInventory(e, item, p, nh);
					}
					return;
				}
			}
		} else if(e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) && e.getInventory().getHolder() instanceof SilenthillHolder) {
			e.setCancelled(true); // when using shit click on below inv
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		InventoryType.getValues().forEach((inv) -> inv.getInv().onInteract(e));
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if(e.getInventory() == null || !(e.getPlayer() instanceof Player))
			return;
		InventoryHolder holder = e.getInventory().getHolder();
		if(!(holder instanceof SilenthillHolder))
			return;
		for(InventoryType type : InventoryType.values()) {
			AbstractInventory<?> inv = type.getInv();
			if(inv.isInstance((SilenthillHolder) holder)) {
				inv.closeInventory((Player) e.getPlayer(), e);
			}
		}
	}
	
	public static void openInventory(Player p, InventoryType type, Object... args) {
		type.getInv().openInventory(p, args);
	}
}
