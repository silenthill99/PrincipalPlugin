package fr.silenthill99.principalplugin.inventory.hook.modo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import fr.silenthill99.principalplugin.inventory.holder.modo.PlayerChooseHolder;

public class PlayerChooseInventory extends AbstractInventory<PlayerChooseHolder> {

	public PlayerChooseInventory() {
		super(PlayerChooseHolder.class);
	}

	@Override
	public void openInventory(Player p, Object... args) {
		int slot = 0;
		PlayerChooseHolder holder = new PlayerChooseHolder();
		Inventory menu = createInventory(holder, 54, "Choisissez un joueur");
		for (Player players : Bukkit.getOnlinePlayers()) {
			holder.add(slot, players);
			menu.setItem(slot, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(players.getName())
					.setName(ChatColor.YELLOW + "Gérer " + players.getName()).toItemStack());
			slot++;
		}
		p.openInventory(menu);
	}
	
	@Override
	public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, PlayerChooseHolder holder) {
		Player cible = holder.get(e.getSlot());
		if(cible != null)
			InventoryManager.openInventory(player, InventoryType.MODO_PLAYER_MENU, cible);
	}
}
