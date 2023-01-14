package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import fr.silenthill99.principalplugin.inventory.holder.GPSHolder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
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
                player.teleport(gps.coord());
                break;
            }
            case SUNFLOWER:
            {
                InventoryManager.openInventory(player, InventoryType.TELEPHONE);
                break;
            }
        }
    }
    static World world = Bukkit.getWorld("world");

    public enum Gps
    {

        HOPITAL("Hôpital", new Location(world, 31.15, 64.0, 65.24)),
        MAIRIE("Mairie", new Location(world,-141.9, 64.0, -43.9)),
        POLE_EMPLOI("Pôle emploi", new Location(world, -148.3, 64.0, 10.4));
        private final String name;
        private final Location coord;

        Gps(String name, Location coord) {
            this.name = name;
            this.coord = coord;
        }

        public String getName() {
            return name;
        }

        public Location coord() {
            return coord;
        }
    }
}
