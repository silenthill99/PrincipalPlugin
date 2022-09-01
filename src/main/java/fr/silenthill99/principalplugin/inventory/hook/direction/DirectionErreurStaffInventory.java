package fr.silenthill99.principalplugin.inventory.hook.direction;

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
import fr.silenthill99.principalplugin.inventory.holder.direction.DirectionErreurStaffHolder;

public class DirectionErreurStaffInventory extends AbstractInventory<DirectionErreurStaffHolder> {

	public DirectionErreurStaffInventory() {
		super(DirectionErreurStaffHolder.class);
	}

	@Override
	public void openInventory(Player p, Object... args) {
		OfflinePlayer target = (OfflinePlayer) args[0];
		
        Inventory inv = createInventory(new DirectionErreurStaffHolder(target), 27, "Erreurs staff");
        inv.setItem(0, new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Fly sans vanish").toItemStack());
        inv.setItem(1, new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "God en WZ").setLore("Uniquement si vanish désactivé !").toItemStack());
        inv.setItem(2, new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Non-respect | Règlement staff").toItemStack());
        inv.setItem(3, new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Non-respect d'autrui").toItemStack());
        inv.setItem(4, new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Abus de pouvoir").setLore("Passible d'un dérank immédiat").toItemStack());
        inv.setItem(5, new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Abus de permissions").toItemStack());
        inv.setItem(6, new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "FreeBan").toItemStack());
        inv.setItem(7, new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "FreeWarn").toItemStack());
        inv.setItem(8, new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Absence non justifiée").toItemStack());
        inv.setItem(9, new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Favoritisme").setLore("A ne pas confondre avec l'attribution de circonstances atténuantes").toItemStack());
        inv.setItem(10, new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Divulgations d'infos confidencielles").toItemStack());
        inv.setItem(11, new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Corruption").toItemStack());
        inv.setItem(18, RETOUR);
        inv.setItem(22, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(target.getName()).setName(target.getName()).toItemStack());
        p.openInventory(inv);
	}
	
	@Override
	public void manageInventory(InventoryClickEvent e, ItemStack current, Player player,
			DirectionErreurStaffHolder holder) {
        OfflinePlayer target = holder.getTarget();
		switch (current.getType()) {
		case SUNFLOWER: {
			InventoryManager.openInventory(player, InventoryType.DIRECTION_MENU, holder.getTarget());
			break;
		}
		case REDSTONE: {
			player.closeInventory();
			String name = ChatColor.stripColor(current.getItemMeta().getDisplayName());
			if (!name.isEmpty()) {
				Bukkit.dispatchCommand(player, "warn " + target.getName() + " Erreur staff : " + name);
			}
			break;
		}
		default:
			break;
		}
	}
}
