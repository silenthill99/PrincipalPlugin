package fr.silenthill99.principalplugin.inventory.holder.modo;

import fr.silenthill99.principalplugin.inventory.SilenthillHolder;
import fr.silenthill99.principalplugin.inventory.hook.modo.PlayerSanctionInventory.*;
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

	public HashMap<Integer, Warns> warns = new HashMap<>();
	public HashMap<Integer, TempBan> temp_ban = new HashMap<>();
	public HashMap<Integer, Ban> ban = new HashMap<>();
	public HashMap<Integer, Kick> kick = new HashMap<>();
	public HashMap<Integer, Mute> mute = new HashMap<>();
	public HashMap<Integer, Freeze> freeze = new HashMap<>();
	public HashMap<Integer, MuteTemp> muteTemp = new HashMap<>();
}
