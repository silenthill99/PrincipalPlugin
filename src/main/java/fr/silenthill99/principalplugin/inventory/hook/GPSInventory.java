package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.Main;
import fr.silenthill99.principalplugin.MySQL;
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class GPSInventory extends AbstractInventory<GPSHolder> {
    public GPSInventory() {
        super(GPSHolder.class);
    }

    Main main = Main.getInstance();

    public static Map<Player, BukkitTask> gpsValuables = new HashMap<>();

    private static final String WORLD_NAME = "Bessemer City V2.1.1 World";

    @Override
    public void openInventory(Player p, Object... args)
    {
        World world = Bukkit.getWorld(WORLD_NAME);
        if (world == null) {
            p.sendMessage(ChatColor.RED + "Erreur: Le monde n'est pas chargé !");
            return;
        }

        GPSHolder holder = new GPSHolder();

        ItemStack arreter = new ItemBuilder(Material.RED_WOOL).setName(ChatColor.RED + "Arrêter le gps !").toItemStack();

        Inventory inv = createInventory(holder, 54, "GPS");

        inv.setItem(45, arreter);
        inv.setItem(inv.getSize()-1, RETOUR);

        final World finalWorld = world;
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            HashMap<String, Location> locations = new HashMap<>();
            try (Connection conn = MySQL.getInstance().getConnection();
                 PreparedStatement statement = conn.prepareStatement("SELECT * FROM locations");
                 ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    String name = rs.getString("name");
                    double x = Double.parseDouble(rs.getString("x"));
                    double y = Double.parseDouble(rs.getString("y"));
                    double z = Double.parseDouble(rs.getString("z"));

                    locations.put(name, new Location(finalWorld, x, y, z));
                }

                // Retour sur le thread principal pour manipuler l'inventaire
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                    int slot = 0;
                    for (Map.Entry<String, Location> entry : locations.entrySet()) {
                        holder.gps.put(slot, locations);
                        inv.setItem(slot++, new ItemBuilder(Material.FILLED_MAP).setName(entry.getKey()).toItemStack());
                    }
                    p.openInventory(inv);
                });

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, GPSHolder holder) {
        HashMap<String, Location> gps = holder.gps.get(e.getSlot());
        switch(current.getType())
        {
            case FILLED_MAP:
            {
                player.closeInventory();

                String destinationName = current.getItemMeta().getDisplayName();
                Location loc = gps.get(destinationName);

                GPSTimer gpsTimer = new GPSTimer(player, loc);
                if (gpsValuables.containsKey(player)) {
                    gpsValuables.get(player).cancel();
                }
                gpsValuables.put(player, gpsTimer.runTaskTimer(main, 0, 1));
                player.sendMessage(ChatColor.GREEN + " Destination : " + destinationName);

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
}
