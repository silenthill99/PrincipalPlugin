package fr.silenthill99.principalplugin.timer;

import fr.silenthill99.principalplugin.inventory.hook.GPSInventory;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GPSTimer extends BukkitRunnable {

    private final Player player;
    private final Location loc;

    public GPSTimer(Player player, Location loc) {
        this.player = player;
        this.loc = loc;
    }

    @Override
    public void run() {
        player.sendTitle(
                ChatColor.GOLD + GPSInventory.getArrowTo(player, loc),
                ChatColor.AQUA +  String.format("%.2f", player.getLocation().distanceSquared(loc)/100) + " m",
                0,
                20,
                20
        );

        if (player.getLocation().distanceSquared(loc) <= 4) {
            player.sendMessage(ChatColor.GREEN + "Vous êtes arrivé à destination");
            cancel();
        }
    }
}
