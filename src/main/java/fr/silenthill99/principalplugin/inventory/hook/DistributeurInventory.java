package fr.silenthill99.principalplugin.inventory.hook;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.Main;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.holder.DistributeurHolder;

public class DistributeurInventory extends AbstractInventory<DistributeurHolder> {

	public DistributeurInventory() {
		super(DistributeurHolder.class);
	}

	@Override
	public void openInventory(Player p, Object... args) {
		p.sendMessage(ChatColor.RED + "Attention ! Système en bêta");
		Inventory inv = createInventory(new DistributeurHolder(), 27, "Distributeur");
		inv.setItem(10,
				new ItemBuilder(Material.GOLD_INGOT).setName(ChatColor.GREEN + "Déposer de l'argent").toItemStack());
		inv.setItem(16,
				new ItemBuilder(Material.GOLD_INGOT).setName(ChatColor.GREEN + "Retirer de l'argent").toItemStack());

		inv.setItem(inv.getSize() - 1, CLOSE);
		p.openInventory(inv);
	}

	@Override
	public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, DistributeurHolder holder) {
		if (!current.getType().equals(Material.GOLD_INGOT))
			return;

		if (current.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Déposer de l'argent")) {
			if (player.getInventory().contains(ItemBuilder.getArgent())) {
				player.getInventory().removeItem(ItemBuilder.getArgent());
				Main.getInstance().economy.depositPlayer(player, 100);
				player.sendMessage(ChatColor.GREEN + "Vous avez déposé 100€ sur votre compte !");
			} else {
				player.sendMessage(ChatColor.RED + "Vous n'avez pas d'argent sur vous !");
			}
		} else if (current.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Retirer de l'argent")) {
			if (Main.getInstance().economy.has(player, 100)) {
				player.getInventory().addItem(ItemBuilder.getArgent());
				Main.getInstance().economy.withdrawPlayer(player, 100);
				player.sendMessage(ChatColor.GREEN + "Vous avez retiré 100€ !");
			} else {
				player.sendMessage(ChatColor.RED + "Vous navez pas assez d'argent !");
			}
		}
	}
}
