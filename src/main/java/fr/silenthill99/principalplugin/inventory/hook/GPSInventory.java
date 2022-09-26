package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import fr.silenthill99.principalplugin.inventory.holder.GPSHolder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class GPSInventory extends AbstractInventory<GPSHolder> {
    public GPSInventory() {
        super(GPSHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args)
    {
        Inventory inv = Bukkit.createInventory(new GPSHolder(), 54, "GPS");
        int slot = 0;
        for (String part : Arrays.asList("Mairie", "Pôle emploi"))
        {
            inv.setItem(slot++, new ItemBuilder(Material.FILLED_MAP).setName(part).toItemStack());
        }
        inv.setItem(inv.getSize()-1, RETOUR);
        p.openInventory(inv);
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, GPSHolder holder) {
        switch(current.getType())
        {
            case FILLED_MAP:
            {
                player.closeInventory();
                if (current.getItemMeta().getDisplayName().equalsIgnoreCase("Mairie"))
                {
                    player.sendMessage("x : -141.9, y : 64.0, z : -43.9");
                }
                else if (current.getItemMeta().getDisplayName().equalsIgnoreCase("Pôle emploi"))
                {
                    player.sendMessage("x : -148.3, y : 64.0, z : 10.4");
                }
                break;
            }
            case SUNFLOWER:
            {
                InventoryManager.openInventory(player, InventoryType.TELEPHONE);
                break;
            }
        }
    }
}
