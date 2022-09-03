package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.holder.MacDoHolder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MacDoInventory extends AbstractInventory<MacDoHolder> {
    public MacDoInventory() {
        super(MacDoHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args)
    {
        Inventory inv = createInventory(new MacDoHolder(), 36, "MacDo");
        inv.setItem(inv.getSize()-1, CLOSE);
        p.openInventory(inv);
    }
}
