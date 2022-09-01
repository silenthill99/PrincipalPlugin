package fr.silenthill99.principalplugin.inventory.hook;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.holder.PoleEmploiHolder;

public class PoleEmploiInventory extends AbstractInventory<PoleEmploiHolder> {

	public PoleEmploiInventory() {
		super(PoleEmploiHolder.class);
	}
	
	@Override
	public void openInventory(Player p, Object... args) {
        Inventory inv = createInventory(new PoleEmploiHolder(), 54, "Choisissez un métier");
        
        inv.setItem(inv.getSize() - 1, CLOSE);
        p.openInventory(inv);
	}
}
