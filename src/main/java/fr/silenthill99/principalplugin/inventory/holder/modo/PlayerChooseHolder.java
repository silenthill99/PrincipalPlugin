package fr.silenthill99.principalplugin.inventory.holder.modo;

import java.util.HashMap;

import org.bukkit.entity.Player;

import fr.silenthill99.principalplugin.inventory.SilenthillHolder;

public class PlayerChooseHolder extends SilenthillHolder {

	private final HashMap<Integer, Player> playerPerSlot = new HashMap<>();
	
	public void add(int slot, Player p) {
		playerPerSlot.put(slot, p);
	}
	
	public Player get(int slot) {
		return playerPerSlot.get(slot);
	}
}
