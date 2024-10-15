package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.Items;
import fr.silenthill99.principalplugin.Main;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.holder.DistributeurHolder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DistributeurInventory extends AbstractInventory<DistributeurHolder> {

	public DistributeurInventory() {
		super(DistributeurHolder.class);
	}

	@Override
	public void openInventory(Player p, Object... args) {
		p.sendMessage(ChatColor.RED + "Attention ! Système en bêta");
		DistributeurHolder holder = new DistributeurHolder();

		Inventory inv = createInventory(holder, 27, "Distributeur");

		for (Distributeur distributeur : Distributeur.values()) {
			holder.distributeur.put(distributeur.getSlot(), distributeur);
			inv.setItem(distributeur.getSlot(), new ItemBuilder(Material.GOLD_INGOT)
					.setName(ChatColor.GREEN + distributeur.getName()).setLore(distributeur.getLore()).toItemStack());
		}

		inv.setItem(inv.getSize() - 1, CLOSE);
		p.openInventory(inv);
	}

	@Override
	public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, DistributeurHolder holder) {

		Distributeur distributeur = holder.distributeur.get(e.getSlot());

		if (!current.getType().equals(Material.GOLD_INGOT))
			return;

		if (distributeur.equals(Distributeur.DEPOT)) {
			if (e.getClick().equals(ClickType.LEFT)) {
				if (!Main.getInstance().consumeItem(player, 1, Items.ARGENT.getItems().toItemStack().getType())) {
					player.sendMessage(Component.text(ChatColor.RED + "Vous n'avez pas d'argent sur vous !"));
				} else {
					Main.getEconomy().depositPlayer(player, 100);
					player.sendMessage(Component.text(ChatColor.GREEN + "Vous avez déposé 100€"));
				}
			} else if (e.getClick().equals(ClickType.RIGHT)) {
				int stack = 0;
				for (ItemStack item : player.getInventory().getContents()) {
					if (item != null && item.isSimilar(ItemBuilder.getArgent(stack))) {
						stack += item.getAmount();
					}
				}
				if (stack == 0) {
					player.sendMessage(Component.text(ChatColor.RED + "Vous n'avez pas assez d'argent sur vous !"));
				} else {
					Main.getEconomy().depositPlayer(player, 100*stack);
					player.sendMessage(Component.text(ChatColor.GREEN + "Vous avez déposé " + 100*stack + "€"));
					player.getInventory().remove(ItemBuilder.getArgent(stack));
				}
			}
		} else if (distributeur.equals(Distributeur.RETRAIT)) {
			if (Main.getEconomy().has(player, 100)) {
				player.getInventory().addItem(Items.ARGENT.getItems().toItemStack());
				Bukkit.dispatchCommand(player, "eco take " + player.getName() + " 100");
				player.sendMessage(ChatColor.GREEN + "Vous avez retiré 100€ !");
			} else {
				player.sendMessage(Component.text(ChatColor.RED + "Vous n'avez pas assez d'argent sur votre" +
						" compte !"));
			}
		}
	}



	public enum Distributeur {
		DEPOT(10, "Déposer de l'argent", "Clique gauche pour déposer 100€", "Clique droit pour tout déposer"),
		RETRAIT(16, "Retirer de l'argent");

		private final int slot;
		private final String name;
		private final String[] lore;

		Distributeur(int slot, String name, String... lore) {
			this.slot = slot;
			this.name = name;
			this.lore = lore;
		}

		public int getSlot() {
			return slot;
		}

		public String getName() {
			return name;
		}

		public String[] getLore() {
			return lore;
		}
	}
}
