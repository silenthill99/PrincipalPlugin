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
import fr.silenthill99.principalplugin.inventory.holder.direction.DirectionRankUpHolder;

public class DirectionRankUpInventory extends AbstractInventory<DirectionRankUpHolder> {

	public DirectionRankUpInventory() {
		super(DirectionRankUpHolder.class);
	}

	@Override
	public void openInventory(Player p, Object... args) {
		OfflinePlayer target = (OfflinePlayer) args[0];
        Inventory rankup = createInventory(new DirectionRankUpHolder(target), 27, "RankUp | " + target.getName());
        rankup.setItem(0, new ItemBuilder(Material.ORANGE_WOOL).setName(ChatColor.GOLD + "[Constructeur]").toItemStack());
        rankup.setItem(1, new ItemBuilder(Material.LIME_WOOL).setName(ChatColor.GREEN + "[Modérateur Stagiaire]").toItemStack());
        rankup.setItem(2, new ItemBuilder(Material.LIME_WOOL).setName(ChatColor.GREEN + "[Modérateur]").toItemStack());
        rankup.setItem(3, new ItemBuilder(Material.LIGHT_BLUE_WOOL).setName(ChatColor.AQUA + "[Administrateur]").toItemStack());
        rankup.setItem(4, new ItemBuilder(Material.BLUE_WOOL).setName(ChatColor.DARK_BLUE + "[" + ChatColor.BLUE + "Développeur" + ChatColor.DARK_BLUE + "]").toItemStack());
        rankup.setItem(8, new ItemBuilder(Material.WRITABLE_BOOK).setName(ChatColor.YELLOW + "Grade direction").toItemStack());
        rankup.setItem(18, RETOUR);
        rankup.setItem(24, new ItemBuilder(Material.RED_WOOL).setName(ChatColor.RED + "Mettre op").toItemStack());
        rankup.setItem(25, new ItemBuilder(Material.PAPER).setName(ChatColor.RED + "UnRank " + target.getName()).toItemStack());
        rankup.setItem(26, new ItemBuilder(Material.RED_WOOL).setName(ChatColor.RED + "DeOp " + target.getName()).toItemStack());
        p.openInventory(rankup);
	}
	
	@Override
	public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, DirectionRankUpHolder holder) {
		OfflinePlayer target = holder.getTarget();
		switch (current.getType()) {
		case SUNFLOWER: {
			InventoryManager.openInventory(player, InventoryType.DIRECTION_MENU, holder.getTarget());
			break;
		}
		case RED_WOOL: {
			if (current.getItemMeta().getDisplayName().equals(ChatColor.RED + "Mettre op")) {
				if (!target.isOp()) {
					player.closeInventory();
					target.setOp(true);
					player.sendMessage(ChatColor.GREEN + target.getName() + " est désormais opérateur du serveur !");
					if (target.isOnline()) {
						target.getPlayer().sendMessage(ChatColor.GREEN + "Vous êtes désormais opérateur du serveur !");
					}
				} else {
					player.sendMessage(ChatColor.RED + target.getName() + " est déjà opérateur !");
				}
			} else if (current.getItemMeta().getDisplayName().equals(ChatColor.RED + "DeOp " + target.getName())) {
				if (target.isOp()) {
					target.setOp(false);
					player.sendMessage(ChatColor.DARK_RED + target.getName() + " a perdu ses privilèges d'opérateur !");
					if (target.isOnline()) {
						target.getPlayer()
								.sendMessage(ChatColor.DARK_RED + "Vous avez perdu vos privilèges d'opérateur !");
					}
				} else {
					player.sendMessage(ChatColor.RED + target.getName() + " n'est pas op !");
				}
			}
			break;
		}
		case PAPER: {
			player.closeInventory();
			Bukkit.dispatchCommand(player, "lp user " + target.getName() + " parent set default");
			if (target.isOnline()) {
				Bukkit.dispatchCommand(player, "kick " + target.getName() + " UnRank");
			}
			Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission clear");
			break;
		}
		case ORANGE_WOOL: {
			player.closeInventory();
			Bukkit.dispatchCommand(player, "lp user " + target.getName() + " parent set builder");
			Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission clear");
			Bukkit.dispatchCommand(player, "list");
			Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission set oxydia.builder");
			player.sendMessage(ChatColor.GREEN + target.getName() + " est désormais builder !");
			if (target.isOnline()) {
				target.getPlayer().sendMessage(ChatColor.GREEN + "Vous êtes désormais builder !");
			}
			break;
		}
		case LIME_WOOL: {
			if (current.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "[Modérateur Stagiaire]")) {
				player.closeInventory();
				Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission clear");
				Bukkit.dispatchCommand(player, "lp user " + target.getName() + " parent set modo-stagiaire");
				Bukkit.dispatchCommand(player, "list");
				Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission set oxydia.stagiaire");
				player.sendMessage(ChatColor.GREEN + target.getName() + " est désormais modérateur stagiaire !");
				if (target.isOnline()) {
					target.getPlayer().sendMessage(ChatColor.GREEN + "Vous êtes désormais modérateur stagiaire !");
				}
			} else if (current.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "[Modérateur]")) {
				player.closeInventory();
				Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission clear");
				Bukkit.dispatchCommand(player, "lp user " + target.getName() + " parent set moderateur");
				Bukkit.dispatchCommand(player, "list");
				Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission set oxydia.modo");
				player.sendMessage(ChatColor.GREEN + target.getName() + " est désormais modérateur !");
				if (target.isOnline()) {
					target.getPlayer().sendMessage(ChatColor.GREEN + "Vousêtes désormais modérateur !");
				}
			}
			break;
		}
		case LIGHT_BLUE_WOOL: {
			player.closeInventory();
			Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission clear");
			Bukkit.dispatchCommand(player, "lp user " + target.getName() + " parent set administrateur");
			Bukkit.dispatchCommand(player, "list");
			Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission set oxydia.admin");
			player.sendMessage(ChatColor.GREEN + target.getName() + " est désormais administrateur !");
			if (target.isOnline()) {
				target.getPlayer().sendMessage(ChatColor.GREEN + "Vous êtes désormais administrateur !");
			}
			break;
		}
		case BLUE_WOOL: {
			player.closeInventory();
			Bukkit.dispatchCommand(player, "lp user " + target.getName() + " parent set developpeur");
			Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission clear");
			Bukkit.dispatchCommand(player, "list");
			Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission set oxydia.developpeur");
			player.sendMessage(ChatColor.GREEN + target.getName() + " est désormais développeur !");
			if (target.isOnline()) {
				target.getPlayer().sendMessage(ChatColor.GREEN + "Vous êtes désormais développeur !");
			}
			break;
		}
		case WRITABLE_BOOK: {
			InventoryManager.openInventory(player, InventoryType.DIRECTION_RANK_UP_SUPER, holder.getTarget());
			break;
		}
		default:
			break;
		}
	}
}
