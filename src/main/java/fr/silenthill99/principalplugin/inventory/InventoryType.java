package fr.silenthill99.principalplugin.inventory;

import java.util.Arrays;
import java.util.List;

import fr.silenthill99.principalplugin.inventory.hook.DistributeurInventory;
import fr.silenthill99.principalplugin.inventory.hook.PoleEmploiInventory;
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
	MODO_PLAYER_CHOOSE(new PlayerChooseInventory()),
	MODO_PLAYER_MENU(new PlayerMenuInventory()),
	MODO_PLAYER_SANCTION(new PlayerSanctionInventory()),
	OPTIONS_JOUEUR(new OptionsJoueurInventory()),
	OPTIONS_ADMIN(new OptionsAdminInventory()),
	DIRECTION_MENU(new DirectionMenuInventory()),
	DIRECTION_ERREUR_STAFF(new DirectionErreurStaffInventory()),
	DIRECTION_RANK_UP(new DirectionRankUpInventory()),
	DIRECTION_RANK_UP_SUPER(new DirectionRankUpSuperInventory()),
	POLE_EMPLOI(new PoleEmploiInventory()),
	DISTRIBUTEUR(new DistributeurInventory());
	
	private final AbstractInventory<?> inv;
	
	private InventoryType(AbstractInventory<?> inv) {
		this.inv = inv;
	}
	
	public AbstractInventory<?> getInv() {
		return inv;
	}
	
	public static List<InventoryType> getValues() {
		return Arrays.asList(values());
	}
}
