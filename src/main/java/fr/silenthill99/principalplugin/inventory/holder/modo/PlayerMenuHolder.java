package fr.silenthill99.principalplugin.inventory.holder.modo;

import org.bukkit.OfflinePlayer;

import fr.silenthill99.principalplugin.inventory.SilenthillHolder;

public class PlayerMenuHolder extends SilenthillHolder {

	private final OfflinePlayer op;
	
	public PlayerMenuHolder(OfflinePlayer op) {
		this.op = op;
	}
	
	public OfflinePlayer getTarget() {
		return op;
	}
}
