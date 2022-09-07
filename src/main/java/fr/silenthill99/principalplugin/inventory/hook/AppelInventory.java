package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import fr.silenthill99.principalplugin.inventory.holder.AppelHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AppelInventory extends AbstractInventory<AppelHolder> {
    public AppelInventory() {
        super(AppelHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args)
    {
        Inventory inv = createInventory(new AppelHolder(), 9, "Passer un appel");
        inv.setItem(8, RETOUR);
        p.openInventory(inv);
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, AppelHolder holder) {
        switch (current.getType())
        {
            case SUNFLOWER:
                InventoryManager.openInventory(player, InventoryType.TELEPHONE);
                break;
            default:
                break;
        }
    }
}
