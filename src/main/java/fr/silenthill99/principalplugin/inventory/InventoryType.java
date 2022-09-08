package fr.silenthill99.principalplugin.inventory;

import java.util.Arrays;
import java.util.List;

import fr.silenthill99.principalplugin.inventory.hook.*;
import fr.silenthill99.principalplugin.inventory.hook.admin.AdminMenuInventory;
import fr.silenthill99.principalplugin.inventory.hook.admin.AdminSanctionInventory;
import fr.silenthill99.principalplugin.inventory.hook.direction.DirectionErreurStaffInventory;
import fr.silenthill99.principalplugin.inventory.hook.direction.DirectionMenuInventory;
import fr.silenthill99.principalplugin.inventory.hook.direction.DirectionRankUpInventory;
import fr.silenthill99.principalplugin.inventory.hook.direction.DirectionRankUpSuperInventory;
import fr.silenthill99.principalplugin.inventory.hook.modo.PlayerChooseInventory;
import fr.silenthill99.principalplugin.inventory.hook.modo.PlayerMenuInventory;
import fr.silenthill99.principalplugin.inventory.hook.modo.PlayerSanctionInventory;
import fr.silenthill99.principalplugin.inventory.hook.options.OptionsAdminInventory;
import fr.silenthill99.principalplugin.inventory.hook.options.OptionsJoueurInventory;

public enum InventoryType {

	ADMIN_MENU(new AdminMenuInventory()),
	ADMIN_SANCTION(new AdminSanctionInventory()),
	APPEL(new AppelInventory()),
	DIRECTION_ERREUR_STAFF(new DirectionErreurStaffInventory()),
	DIRECTION_MENU(new DirectionMenuInventory()),
	DIRECTION_RANK_UP(new DirectionRankUpInventory()),
	DIRECTION_RANK_UP_SUPER(new DirectionRankUpSuperInventory()),
	DISTRIBUTEUR(new DistributeurInventory()),
    KIT(new KitInventory()),
	MACDO(new MacDoInventory()),
	METIERS_ILLEGAUX(new IllegauxInventory()),
	MODO_PLAYER_CHOOSE(new PlayerChooseInventory()),
	MODO_PLAYER_MENU(new PlayerMenuInventory()),
	MODO_PLAYER_SANCTION(new PlayerSanctionInventory()),
	MUSIQUE(new MusiqueInventory()),
	OPTIONS_ADMIN(new OptionsAdminInventory()),
	OPTIONS_JOUEUR(new OptionsJoueurInventory()),
	POLE_EMPLOI(new PoleEmploiInventory()),
	POUBELLE(new PoubelleInventory()),
	TELEPHONE(new TelephoneInventory());
	
	private final AbstractInventory<?> inv;
	
	InventoryType(AbstractInventory<?> inv) {
		this.inv = inv;
	}
	
	public AbstractInventory<?> getInv() {
		return inv;
	}
	
	public static List<InventoryType> getValues() {
		return Arrays.asList(values());
	}
}
