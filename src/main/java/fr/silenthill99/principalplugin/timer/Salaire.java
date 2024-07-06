package fr.silenthill99.principalplugin.timer;

import fr.silenthill99.principalplugin.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Salaire extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Main.isPlayerInGroup(player, "fondateur")) {
                player.sendMessage(Component.text(ChatColor.GREEN + "Versement de votre salaire : 150000 €"));
                Main.getEconomy().depositPlayer(player, 150000);
            }
        }
    }
}
