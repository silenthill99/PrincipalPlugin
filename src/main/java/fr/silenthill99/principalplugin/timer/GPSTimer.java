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
        player.sendTitle(GPSInventory.getArrowTo(player, loc), null, 0, 20, 0);

        if (player.getLocation() == loc) {
            cancel();
            player.sendMessage(ChatColor.RED + "Vous êtes arrivé à destination");
        }
    }
}
