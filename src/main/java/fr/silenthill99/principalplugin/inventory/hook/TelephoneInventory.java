package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.holder.TelephoneHolder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TelephoneInventory extends AbstractInventory<TelephoneHolder> {
    public TelephoneInventory()
    {
        super(TelephoneHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args)
    {
        Inventory inv = createInventory(new TelephoneHolder(), 27, "Téléphone");
        inv.setItem(inv.getSize()-1, CLOSE);
        p.openInventory(inv);
    }
}
