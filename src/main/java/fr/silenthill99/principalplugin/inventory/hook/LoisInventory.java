package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.holder.LoisHolder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LoisInventory extends AbstractInventory<LoisHolder>
{
    public LoisInventory()
    {
        super(LoisHolder.class);
    }

    @Override
    public void openInventory(Player player, Object... args)
    {
        LoisHolder holder = new LoisHolder();
        Inventory inv = createInventory(holder, 9, "Définissez une loi");
        int slot = 0;
        for (Lois lois : Lois.values())
        {
            holder.lois.put(slot, lois);
            inv.setItem(slot++, new ItemBuilder(Material.OAK_SIGN).setName("Loi n°" + lois.getNumber()).toItemStack());
        }
        inv.setItem(inv.getSize()-1, CLOSE);
        player.openInventory(inv);
    }

    public enum Lois
    {
        LOI_1(1);

        private final int number;

        Lois(int number)
        {
            this.number = number;
        }

        public int getNumber()
        {
            return this.number;
        }
    }
}
