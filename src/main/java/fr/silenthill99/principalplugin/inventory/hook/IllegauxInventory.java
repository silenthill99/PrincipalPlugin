package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.holder.IllegauxHolder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class IllegauxInventory extends AbstractInventory<IllegauxHolder> {
    public IllegauxInventory() {
        super(IllegauxHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args)
    {
        Inventory inv = createInventory(new IllegauxHolder(), 27, ChatColor.DARK_RED + "Métiers illégaux");
        p.openInventory(inv);
    }
}
