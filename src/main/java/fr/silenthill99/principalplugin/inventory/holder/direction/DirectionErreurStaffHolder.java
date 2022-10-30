package fr.silenthill99.principalplugin.inventory.holder.direction;

import fr.silenthill99.principalplugin.inventory.hook.direction.DirectionErreurStaffInventory.ErreurStaff;
import org.bukkit.OfflinePlayer;

import fr.silenthill99.principalplugin.inventory.SilenthillHolder;

import java.util.HashMap;
import java.util.Map;

public class DirectionErreurStaffHolder extends SilenthillHolder {

	private final OfflinePlayer op;
	
	public DirectionErreurStaffHolder(OfflinePlayer op) {
		this.op = op;
	}
	
	public OfflinePlayer getTarget() {
		return op;
	}

	public Map<Integer, ErreurStaff> erreur_staff = new HashMap<>();
}
