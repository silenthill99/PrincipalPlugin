package fr.silenthill99.principalplugin.inventory.holder.direction;

import org.bukkit.OfflinePlayer;

import fr.silenthill99.principalplugin.inventory.SilenthillHolder;

public class DirectionRankUpHolder extends SilenthillHolder {

	private final OfflinePlayer op;
	
	public DirectionRankUpHolder(OfflinePlayer op) {
		this.op = op;
	}
	
	public OfflinePlayer getTarget() {
		return op;
	}
}
