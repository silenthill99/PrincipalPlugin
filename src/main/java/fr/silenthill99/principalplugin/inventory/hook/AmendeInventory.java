package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.holder.AmendeHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AmendeInventory extends AbstractInventory<AmendeHolder>
{
    public AmendeInventory()
    {
        super(AmendeHolder.class);
    }

    @Override
    public void openInventory(Player player, Object... args)
    {
        Player target = (Player) args[0];
        AmendeHolder holder = new AmendeHolder(target);

        Inventory inv = createInventory(holder, 27, "Mettre une amende à " + target.getName());
        player.openInventory(inv);
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, AmendeHolder holder) {
        Player target = (Player) holder.getTarget();
    }
}
