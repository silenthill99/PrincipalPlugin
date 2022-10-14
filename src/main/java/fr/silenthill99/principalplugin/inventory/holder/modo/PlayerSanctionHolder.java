package fr.silenthill99.principalplugin.inventory.holder.modo;

import fr.silenthill99.principalplugin.inventory.SilenthillHolder;
import fr.silenthill99.principalplugin.inventory.hook.modo.PlayerSanctionInventory.SanctionType;
import fr.silenthill99.principalplugin.inventory.hook.modo.PlayerSanctionInventory.TempBanPage1;
import fr.silenthill99.principalplugin.inventory.hook.modo.PlayerSanctionInventory.WarnPage1;
import fr.silenthill99.principalplugin.inventory.hook.modo.PlayerSanctionInventory.WarnPage2;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;

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

	public HashMap<Integer, WarnPage1> warn_page_1 = new HashMap<>();
	public HashMap<Integer, WarnPage2> warn_page_2 = new HashMap<>();
	public HashMap<Integer, TempBanPage1> temp_ban_page_1 = new HashMap<>();
}
