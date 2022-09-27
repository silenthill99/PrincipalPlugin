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

public class GPSInventory extends AbstractInventory<GPSHolder> {
    public GPSInventory() {
        super(GPSHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args)
    {
        GPSHolder holder = new GPSHolder();
        Inventory inv = Bukkit.createInventory(holder, 54, "GPS");
        int slot = 0;
        for (Gps gps : Gps.values())
        {
            holder.gps.put(slot, gps);
            inv.setItem(slot++, new ItemBuilder(Material.FILLED_MAP).setName(gps.getName()).toItemStack());
        }
        inv.setItem(inv.getSize()-1, RETOUR);
        p.openInventory(inv);
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, GPSHolder holder) {
        Gps gps = holder.gps.get(e.getSlot());
        switch(current.getType())
        {
            case FILLED_MAP:
            {
                player.closeInventory();
                player.sendMessage(gps.coord());
                break;
            }
            case SUNFLOWER:
            {
                InventoryManager.openInventory(player, InventoryType.TELEPHONE);
                break;
            }
        }
    }
    public enum Gps
    {
        MAIRIE("Mairie", "x : -141.9, y : 64.0, z : -43.9"),
        POLE_EMPLOI("Pôle emploi", "x : -148.3, y : 64.0, z : 10.4");
        private final String name;
        private final String coord;

        Gps(String name, String coord) {
            this.name = name;
            this.coord = coord;
        }

        public String getName() {
            return name;
        }

        public String coord() {
            return coord;
        }
    }
}
