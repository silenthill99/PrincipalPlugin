package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.holder.GPSHolder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GPSInventory extends AbstractInventory<GPSHolder> {
    public GPSInventory() {
        super(GPSHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args)
    {
        Inventory inv = Bukkit.createInventory(new GPSHolder(), 54, "GPS");
        p.openInventory(inv);
    }
}
