package fr.silenthill99.principalplugin.inventory.holder.admin;

import org.bukkit.OfflinePlayer;

import fr.silenthill99.principalplugin.inventory.SilenthillHolder;

public class AdminSanctionHolder extends SilenthillHolder {

	private final OfflinePlayer op;
	
	public AdminSanctionHolder(OfflinePlayer op) {
		this.op = op;
	}
	
	public OfflinePlayer getTarget() {
		return op;
	}
}
