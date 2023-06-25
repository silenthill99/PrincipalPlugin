package fr.silenthill99.principalplugin.inventory.hook.direction;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import fr.silenthill99.principalplugin.inventory.holder.direction.DirectionErreurStaffHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
			inv.setItem(slot++, new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + erreur_staff.getTitre()).setLore(erreur_staff.getLore()).toItemStack());

		}
        inv.setItem(18, RETOUR);
        inv.setItem(22, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(target.getName()).setName(target.getName()).toItemStack());
        p.openInventory(inv);
	}
	
	@Override
	public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, DirectionErreurStaffHolder holder) {
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
		NON_RESPECT_REGLEMENT_STAFF("Non-respect | Règlement staff"),
		NON_RESPECT_DAUTRUI("Non-respect d'autrui"),
		ABUS_DE_POUVOIR("Abus de pouvoir", "Passible d'un dérank immédiat"),
		ABUS_DE_PERMISSIONS("Abus de permissions"),
		FREE_BAN("FreeBan"),
		FREE_WARN("FreeWarn"),
		ABSENCE_NON_JUSTIFIEE("Absence non justifiée"),
		FAVORITISME("Favoritisme", "A ne pas confondre avec l'attribution de circonstances atténuantes"),
		DIVULGATION("Divulgations d'infos confidencielles"),
		CORRUPTION("Corruption"),
		SANCTION_INAPPROPRIEE("Sanction inappropriée")
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
