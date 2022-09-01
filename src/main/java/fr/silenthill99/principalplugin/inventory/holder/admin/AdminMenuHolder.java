package fr.silenthill99.principalplugin.inventory.holder.admin;

import org.bukkit.OfflinePlayer;

import fr.silenthill99.principalplugin.inventory.SilenthillHolder;

public class AdminMenuHolder extends SilenthillHolder {

	private final OfflinePlayer op;
	
	public AdminMenuHolder(OfflinePlayer op) {
		this.op = op;
	}
	
	public OfflinePlayer getTarget() {
		return op;
	}
}
