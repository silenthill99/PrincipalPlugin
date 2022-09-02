package fr.silenthill99.principalplugin.inventory.hook;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.holder.PoubelleHolder;

public class PoubelleInventory extends AbstractInventory<PoubelleHolder> {
    public PoubelleInventory() {
        super(PoubelleHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args)
    {
        Inventory inv = createInventory(new PoubelleHolder(), 9, "Poubelle");
        p.openInventory(inv);
    }
}
