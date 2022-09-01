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
import fr.silenthill99.principalplugin.inventory.holder.direction.DirectionRankUpSuperHolder;

public class DirectionRankUpSuperInventory extends AbstractInventory<DirectionRankUpSuperHolder> {

	public DirectionRankUpSuperInventory() {
		super(DirectionRankUpSuperHolder.class);
	}

	@Override
	public void openInventory(Player p, Object... args) {
		OfflinePlayer target = (OfflinePlayer) args[0];

		Inventory direction = createInventory(new DirectionRankUpSuperHolder(target), 9,
				"Grade direction ► " + target.getName());
		direction.setItem(0,
				new ItemBuilder(Material.YELLOW_WOOL).setName(ChatColor.YELLOW + "[Resp. Equipe]").toItemStack());
		direction.setItem(1,
				new ItemBuilder(Material.RED_WOOL).setName(ChatColor.DARK_RED + "[Co-Fondateur]").toItemStack());
		direction.setItem(2,
				new ItemBuilder(Material.RED_WOOL).setName(ChatColor.DARK_RED + "[Fondateur]").toItemStack());
		direction.setItem(8, RETOUR);
		p.openInventory(direction);
	}

	@Override
	public void manageInventory(InventoryClickEvent e, ItemStack current, Player player,
			DirectionRankUpSuperHolder holder) {
		OfflinePlayer target = holder.getTarget();
		switch (current.getType()) {
		case YELLOW_WOOL: {
			player.closeInventory();
			Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission clear");
			Bukkit.dispatchCommand(player, "lp user " + target.getName() + " parent set responsable");
			Bukkit.dispatchCommand(player, "list");
			Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission set oxydia.responsable");
			player.sendMessage(ChatColor.GREEN + target.getName() + " est désormais responsable d'équipe !");
			if (target.isOnline()) {
				target.getPlayer().sendMessage(ChatColor.GREEN + "Vous êtes désormais responsable d'équipe !");
			}
			break;
		}
		case SUNFLOWER: {
			InventoryManager.openInventory(player, InventoryType.DIRECTION_RANK_UP, target);
			break;
		}
		case RED_WOOL: {
			player.closeInventory();
			Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission clear");
			if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "[Co-Fondateur]")) {
				Bukkit.dispatchCommand(player, "lp user " + target.getName() + " parent set co-fondateur");
				Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission set oxydia.cofonfateur");
				player.sendMessage(ChatColor.GREEN + target.getName() + " est désormais co-fondateur !");
				if (target.isOnline()) {
					target.getPlayer().sendMessage(ChatColor.GREEN + "Vous êtes désormais co-fondateur !");
				}
			} else if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "[Fondateur]")) {
				Bukkit.dispatchCommand(player, "lp user " + target.getName() + " parent set fondateur");
				Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission set oxydia.fondateur");
				player.sendMessage(ChatColor.GREEN + target.getName() + " est désormais fondateur !");
				if (target.isOnline()) {
					target.getPlayer().sendMessage(ChatColor.GREEN + "Vous êtes désormais fondateur !");
				}
			}
			Bukkit.dispatchCommand(player, "list");
			break;
		}
		default:
			break;
		}
	}
}
