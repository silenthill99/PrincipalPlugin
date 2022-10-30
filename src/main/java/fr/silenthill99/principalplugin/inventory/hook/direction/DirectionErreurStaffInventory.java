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

import java.util.Arrays;

public class DirectionErreurStaffInventory extends AbstractInventory<DirectionErreurStaffHolder> {

	public DirectionErreurStaffInventory() {
		super(DirectionErreurStaffHolder.class);
	}

	@Override
	public void openInventory(Player p, Object... args) {
		OfflinePlayer target = (OfflinePlayer) args[0];
		DirectionErreurStaffHolder holder = new DirectionErreurStaffHolder(target);
		
        Inventory inv = createInventory(holder, 27, "Erreurs staff");
		int slot = 0;
		for (ErreurStaff erreur_staff : ErreurStaff.values())
		{
			holder.erreur_staff.put(slot, erreur_staff);
			ItemStack e_s = new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + erreur_staff.getTitre()).toItemStack();
			if (erreur_staff.getLore() != null)
			{
				e_s.setLore(Arrays.asList(erreur_staff.getLore()));
			}
			inv.setItem(slot++, e_s);

		}
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
		ErreurStaff erreur_staff = holder.erreur_staff.get(e.getSlot());

		switch (current.getType()) {
		case SUNFLOWER: {
			InventoryManager.openInventory(player, InventoryType.DIRECTION_MENU, holder.getTarget());
			break;
		}
		case REDSTONE: {
			player.closeInventory();
			Bukkit.dispatchCommand(player, "warn " + target.getName() + " Erreur staff : " + erreur_staff.getTitre());
			break;
		}
		default:
			break;
		}
	}

	public enum ErreurStaff
	{
		FLY_SANS_VANISH("Fly sans vanish"),
		GOD_EN_WZ("God en WZ", "Uniquement si vanish désactivé !"),
		NON_RESPECT_REGLEMENT_STAFF("Non-respect | Règlement staff")
		;

		private final String titre;
		private final String[] lore;
		ErreurStaff(String titre, String... lore)
		{
			this.titre = titre;
			this.lore = lore;
		}
		public String getTitre()
		{
			return this.titre;
		}

		public String[] getLore()
		{
			return this.lore;
		}
	}
}
