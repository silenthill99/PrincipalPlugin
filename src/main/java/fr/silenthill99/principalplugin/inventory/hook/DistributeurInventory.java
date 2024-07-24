package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.Main;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.holder.DistributeurHolder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

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
					.setName(ChatColor.GREEN + distributeur.getName()).toItemStack());
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
			if (!consumeItem(player, 1, ItemBuilder.getArgent().getType())) {
				player.sendMessage(Component.text(ChatColor.RED + "Vous n'avez pas d'argent sur vous !"));
			} else {
				Main.getEconomy().depositPlayer(player, 100);
				consumeItem(player, 1, ItemBuilder.getArgent().getType());
				player.sendMessage(Component.text(ChatColor.GREEN + "Vous avez déposé 100€"));
			}
		} else if (distributeur.equals(Distributeur.RETRAIT)) {
			if (Main.getEconomy().has(player, 100)) {
				player.getInventory().addItem(ItemBuilder.getArgent());
				Bukkit.dispatchCommand(player, "eco take " + player.getName() + " 100");
				player.sendMessage(ChatColor.GREEN + "Vous avez retiré 100€ !");
			} else {
				player.sendMessage(Component.text(ChatColor.RED + "Vous n'avez pas assez d'argent sur votre" +
						" compte !"));
			}
		}
	}

	@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    public boolean consumeItem(Player player, int count, Material mat) {
		Map<Integer, ? extends ItemStack> ammo = new HashMap<>();

		int found = 0;
		for (ItemStack stack : ammo.values()) {
			if (!stack.isSimilar(ItemBuilder.getArgent())) continue;
			found += stack.getAmount();
		}
		if (count > found)
			return false;

		for (Integer index : ammo.keySet()) {
			ItemStack stack = ammo.get(index);
			int removed = Math.min(count, stack.getAmount());
			count -= removed;

			if (stack.getAmount() == removed)
				player.getInventory().setItem(index, null);
			else
				stack.setAmount(stack.getAmount() - removed);

			if (count <= 0)
				break;
		}
		player.updateInventory();
		return true;
	}
	public enum Distributeur {
		DEPOT(10, "Déposer de l'argent"),
		RETRAIT(16, "Retirer de l'argent");

		private final int slot;
		private final String name;

		Distributeur(int slot, String name) {
			this.slot = slot;
			this.name = name;
		}

		public int getSlot() {
			return slot;
		}

		public String getName() {
			return name;
		}
	}
}
