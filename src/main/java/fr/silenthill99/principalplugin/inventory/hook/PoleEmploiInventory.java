package fr.silenthill99.principalplugin.inventory.hook;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.holder.PoleEmploiHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class PoleEmploiInventory extends AbstractInventory<PoleEmploiHolder> {

	public PoleEmploiInventory() {
		super(PoleEmploiHolder.class);
	}
	
	@Override
	public void openInventory(Player p, Object... args) {
        Inventory inv = createInventory(new PoleEmploiHolder(), 54, "Choisissez un métier");
		PoleEmploiHolder holder = (PoleEmploiHolder) inv.getHolder();
		inv.setItem(0, holder.citoyen);
		inv.setItem(1, holder.policier);
		inv.setItem(2, holder.pompier);
		inv.setItem(3, holder.medecin);
        inv.setItem(inv.getSize() - 1, CLOSE);
        p.openInventory(inv);
	}

	@Override
	public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, PoleEmploiHolder holder) {
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
			default:
				String grade = ChatColor.stripColor(current.getItemMeta().getDisplayName()).toLowerCase(Locale.ROOT);
				if (grade.equalsIgnoreCase("policier"))
				{
					changeMetier(player, grade, "http://novask.in/5911833608.png");
				}
				else if (grade.equalsIgnoreCase("pompier"))
				{
					changeMetier(player, grade, "http://novask.in/5925383309.png");
				}
				else if (grade.equalsIgnoreCase("medecin"))
				{
					changeMetier(player, grade, "http://novask.in/5290663328.png");
				}
				break;
		}
	}

	public void changeMetier(Player player, String metier, String url)
	{
		player.closeInventory();
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent set " + metier);
		Bukkit.dispatchCommand(player, "skin set " + url);
		player.sendMessage(ChatColor.GREEN + "Vous êtes désormais " + metier + " !");
	}
}