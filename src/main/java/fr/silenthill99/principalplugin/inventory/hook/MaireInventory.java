package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.holder.MaireHolder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MaireInventory extends AbstractInventory<MaireHolder> {
    public MaireInventory() {
        super(MaireHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args)
    {
        ItemStack lois = new ItemBuilder(Material.OAK_SIGN).setName("Lois").toItemStack();
        Inventory inv = createInventory(new MaireHolder(), 27, ChatColor.GREEN + "Mairie");
        inv.setItem(0, lois);
        p.openInventory(inv);
    }
}
