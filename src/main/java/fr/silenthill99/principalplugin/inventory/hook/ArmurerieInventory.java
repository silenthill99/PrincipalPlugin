package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.holder.ArmurerieHolder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ArmurerieInventory extends AbstractInventory<ArmurerieHolder> {
    public ArmurerieInventory() {
        super(ArmurerieHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args)
    {
        Inventory inv = createInventory(new ArmurerieHolder(), 36, "Armurerie");
        inv.setItem(inv.getSize()-1, CLOSE);
        p.openInventory(inv);
    }
}
