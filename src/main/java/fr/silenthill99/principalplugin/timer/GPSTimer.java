package fr.silenthill99.principalplugin.timer;

import fr.silenthill99.principalplugin.inventory.hook.GPSInventory;
import fr.silenthill99.principalplugin.inventory.hook.GPSInventory.Gps;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GPSTimer extends BukkitRunnable {

    private final Player player;
    private final Gps gps;

    public GPSTimer(Player player, Gps gps) {
        this.player = player;
        this.gps = gps;
    }

    @Override
    public void run() {
        player.sendTitle(ChatColor.GOLD + GPSInventory.getArrowTo(player, gps.coord()), null, 0, 20, 20);

        if (player.getLocation().distanceSquared(gps.coord()) <= 4) {
            player.sendMessage(ChatColor.GREEN + "Vous êtes arrivé à destination");
            cancel();
        }
    }
}
