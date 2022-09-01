package fr.silenthill99.principalplugin.inventory.holder.modo;

import org.bukkit.OfflinePlayer;

import fr.silenthill99.principalplugin.inventory.SilenthillHolder;
import fr.silenthill99.principalplugin.inventory.hook.modo.PlayerSanctionInventory.SanctionType;

public class PlayerSanctionHolder extends SilenthillHolder {

	private final OfflinePlayer op;
	private final SanctionType type;
	private final int page;
	
	public PlayerSanctionHolder(OfflinePlayer op, SanctionType type, int page) {
		this.op = op;
		this.type = type;
		this.page = page;
	}
	
	public OfflinePlayer getTarget() {
		return op;
	}
	
	public int getPage() {
		return page;
	}

	public SanctionType getName() {
		return type;
	}
}
