package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.Main;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import fr.silenthill99.principalplugin.inventory.holder.GPSHolder;
import fr.silenthill99.principalplugin.timer.GPSTimer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class GPSInventory extends AbstractInventory<GPSHolder> {
    public GPSInventory() {
        super(GPSHolder.class);
    }

    Main main = Main.getInstance();

    public static Map<Player, BukkitTask> gpsValuables = new HashMap<>();

    @Override
    public void openInventory(Player p, Object... args)
    {
        GPSHolder holder = new GPSHolder();

        ItemStack arreter = new ItemBuilder(Material.RED_WOOL).setName(ChatColor.RED + "Arrêter le gps !").toItemStack();

        Inventory inv = createInventory(holder, 54, "GPS");
        int slot = 0;
        for (Gps gps : Gps.values())
        {
            holder.gps.put(slot, gps);
            inv.setItem(slot++, new ItemBuilder(Material.FILLED_MAP).setName(gps.getName()).toItemStack());
        }
        inv.setItem(45, arreter);
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
                GPSTimer gpsTimer = new GPSTimer(player, gps);
                if (gpsValuables.containsKey(player)) {
                    gpsValuables.get(player).cancel();
                }
                gpsValuables.put(player, gpsTimer.runTaskTimer(main, 0, 1));
                player.sendMessage(ChatColor.GREEN + "Destination : " + gps.getName());
                break;
            }
            case SUNFLOWER:
            {
                InventoryManager.openInventory(player, InventoryType.TELEPHONE);
                break;
            }
            case RED_WOOL:
            {
                if (!gpsValuables.containsKey(player)) {
                    player.sendMessage(ChatColor.RED + "Vous avez aucun itinéraires en cours !");
                    break;
                }
                if (!gpsValuables.get(player).isCancelled()) {
                    player.closeInventory();
                    gpsValuables.get(player).cancel();
                    player.sendMessage(ChatColor.RED + "Vous avez éteint votre GPS !");
                } else {
                    player.sendMessage(ChatColor.RED + "Vous avez aucun itinéraires en cours !");
                }
                break;
            }
        }
    }
    static World world = Bukkit.getWorld("world");

    public static String getArrowTo(Player p, Location loc) {
        Location playerLocation = p.getLocation();
        Vector locVector = loc.toVector().subtract(playerLocation.toVector());

        String direction = null;

        double locAngle = Math.atan2(locVector.getZ(), locVector.getX());
        double playerAngle = Math.atan2(playerLocation.getDirection().getZ(), playerLocation.getDirection().getX());

        double angle = playerAngle - locAngle;

        while (angle > Math.PI) {
            angle = angle - 2 * Math.PI;
        }

        while (angle < -Math.PI) {
            angle = angle + 2 * Math.PI;
        }

        if (angle < -2.749 || angle >= 2.749) { // -7/8 pi
            direction = "⬇";
        } else if (angle < -1.963) { // -5/8 pi
            direction = "⬊";
        } else if (angle < -1.178) { // -3/8 pi
            direction = "➡";
        } else if (angle < -0.393) { // -1/8 pi
            direction = "⬈";
        } else if (angle < 0.393) { // 1/8 pi
            direction = "⬆";
        } else if (angle < 1.178) { // 3/8 pi
            direction = "⬉";
        } else if (angle < 1.963) { // 5/8 p
            direction = "⬅";
        } else if (angle < 2.749) { // 7/8 pi
            direction = "⬋";
        }
        return direction;
    }

    public enum Gps {
        HOPITAL("Hôpital", new Location(world, 31.15, 64.0, 65.24)),
        MAGASIN_DE_MUSIQUE("Magasin de musique", new Location(world, -32.5, 64, 229.5)),
        MAIRIE("Mairie", new Location(world,-141.9, 64.0, -43.9)),
        POLE_EMPLOI("Pôle emploi", new Location(world, -148.3, 64.0, 10.4)),
        PRISON("Prison", new Location(world, -307.6, 64, -91.9));

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
