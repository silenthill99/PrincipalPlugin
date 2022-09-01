package fr.silenthill99.principalplugin.inventory.holder.options;

import org.bukkit.OfflinePlayer;

import fr.silenthill99.principalplugin.inventory.SilenthillHolder;

public class OptionsJoueurHolder extends SilenthillHolder {

	private final OfflinePlayer op;
	
	public OptionsJoueurHolder(OfflinePlayer op) {
		this.op = op;
	}
	
	public OfflinePlayer getTarget() {
		return op;
	}
}
