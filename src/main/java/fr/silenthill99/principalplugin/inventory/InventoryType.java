package fr.silenthill99.principalplugin.inventory;

import fr.silenthill99.principalplugin.inventory.hook.*;
import fr.silenthill99.principalplugin.inventory.hook.admin.AdminMenuInventory;
import fr.silenthill99.principalplugin.inventory.hook.admin.AdminSanctionInventory;
import fr.silenthill99.principalplugin.inventory.hook.direction.*;
import fr.silenthill99.principalplugin.inventory.hook.modo.PlayerChooseInventory;
import fr.silenthill99.principalplugin.inventory.hook.modo.PlayerMenuInventory;
import fr.silenthill99.principalplugin.inventory.hook.modo.PlayerSanctionInventory;
import fr.silenthill99.principalplugin.inventory.hook.musiques.*;
import fr.silenthill99.principalplugin.inventory.hook.options.OptionsAdminInventory;
import fr.silenthill99.principalplugin.inventory.hook.options.OptionsJoueurInventory;

import java.util.Arrays;
import java.util.List;

public enum InventoryType {

	ADMIN_MENU(new AdminMenuInventory()),
	ADMIN_SANCTION(new AdminSanctionInventory()),
	AMENDE(new AmendeInventory()),
	APPEL(new AppelInventory()),
	ARMURERIE(new ArmurerieInventory()),
	BAN_STAFF(new BannissementStaffInventory()),
	BENEVOLAT(new BenevolatInventory()),
	CANDIDATS(new CandidatInventory()),
	DIRECTION_ERREUR_STAFF(new DirectionErreurStaffInventory()),
	DIRECTION_MENU(new DirectionMenuInventory()),
	DIRECTION_RANK_UP(new DirectionRankUpInventory()),
	DIRECTION_RANK_UP_SUPER(new DirectionRankUpSuperInventory()),
	DISTRIBUTEUR(new DistributeurInventory()),
	GPS(new GPSInventory()),
	LOIS(new LoisInventory()),
    KIT(new KitInventory()),
	MACDO(new MacDoInventory()),
	MAIRE(new MaireInventory()),
    MAIRIE(new MairieInventory()),
	MEDECIN(new MedecinInventory()),
	METIER(new MetierInventory()),
	METIERS_ILLEGAUX(new IllegauxInventory()),
	MODO_PLAYER_CHOOSE(new PlayerChooseInventory()),
	MODO_PLAYER_MENU(new PlayerMenuInventory()),
	MODO_PLAYER_SANCTION(new PlayerSanctionInventory()),
	MUSIQUE(new MusiqueInventory()),
	MUSIQUE_GESTION(new MusiqueGestionInventory()),
	OPTIONS_ADMIN(new OptionsAdminInventory()),
	OPTIONS_JOUEUR(new OptionsJoueurInventory()),
	POUBELLE(new PoubelleInventory()),
	POLICIER(new PolicierInventory()),
	TELEPHONE(new TelephoneInventory()),
	TEST(new TestInventory()),
	VENDEUR(new VendeurInventory());
	
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
