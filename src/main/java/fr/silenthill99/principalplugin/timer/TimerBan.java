package fr.silenthill99.principalplugin.timer;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerBan extends BukkitRunnable {

    private final OfflinePlayer player;
    private final String reason;

    public TimerBan(OfflinePlayer player, String reason) {
        this.player = player;
        this.reason = reason;
    }

    @Override
    public void run() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ipban " + player.getName() + " " + reason);
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public String getReason() {
        return reason;
    }

}
