package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.holder.PoleEmploiHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class PoleEmploiInventory extends AbstractInventory<PoleEmploiHolder> {

	public PoleEmploiInventory() {
		super(PoleEmploiHolder.class);
	}
	
	@Override
	public void openInventory(Player p, Object... args) {
		PoleEmploiHolder holder = new PoleEmploiHolder();
		ItemStack citoyen = new ItemBuilder(Material.PLAYER_HEAD).setName(ChatColor.YELLOW + "Citoyen").toItemStack();
        Inventory inv = createInventory(new PoleEmploiHolder(), 54, "Choisissez un métier");
		inv.setItem(0, citoyen);
		int slot = 1;
		for (Metiers metiers : Metiers.values())
		{
			holder.metiers.put(slot, metiers);
			inv.setItem(slot++, new ItemBuilder(Material.PAPER).setName(ChatColor.YELLOW + metiers.getName()).toItemStack());
		}
        inv.setItem(inv.getSize() - 1, CLOSE);
        p.openInventory(inv);
	}

	@Override
	public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, PoleEmploiHolder holder) {
		Metiers metier = holder.metiers.get(e.getSlot());
		switch(current.getType())
		{
			case PLAYER_HEAD:
			{
				player.closeInventory();
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent set default");
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "skin clear " + player.getName());
				player.sendMessage(ChatColor.GREEN + "Vous êtes désormais citoyen");
				break;
			}
			case PAPER:
			{
				player.closeInventory();
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent set " + metier.name().toLowerCase(Locale.ROOT));
				Bukkit.dispatchCommand(player, "skin set " + metier.url());
				player.sendMessage(ChatColor.GREEN + "Vous êtes désormais " + metier.getName() + " !");
				break;
			}
			default:
				break;
		}
	}

	public enum Metiers {
		POLICIER("Policier", "http://novask.in/5911833608.png"),
		POMPIER("Pompier", "http://novask.in/5925383309.png"),
		MEDECIN("Médecin", "http://novask.in/5290663328.png"),
		CUISINIER("Cuisinier", "http://novask.in/5921353192.png"),
		CHASSEUR("Chasseur", "http://novask.in/5448143538.png");

		private final String name;
		private final String url;

		Metiers(String name, String url)
		{
			this.name = name;
			this.url = url;
		}

		public String getName()
		{
			return name;
		}

		public String url() {
			return url;
		}
	}

}