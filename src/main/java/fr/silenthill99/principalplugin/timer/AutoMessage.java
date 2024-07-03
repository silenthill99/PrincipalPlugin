package fr.silenthill99.principalplugin.timer;

import fr.silenthill99.principalplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Objects;

public class AutoMessage extends BukkitRunnable {
    private final Main main = Main.getInstance();
    private final List<String> messages = main.getConfig().getStringList("messages");
    private int i = 0;

    @Override
    public void run() {
        if (messages.isEmpty()) {
            cancel();
            return;
        }

        if (i >= messages.size()) {
            i = 0;
        }

        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                Objects.requireNonNull(main.getConfig().getString("prefix"))) + " " + messages.get(i));
        i++;
    }
}
