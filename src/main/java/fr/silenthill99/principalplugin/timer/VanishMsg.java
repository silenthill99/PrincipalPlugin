package fr.silenthill99.principalplugin.timer;

import fr.silenthill99.principalplugin.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@SuppressWarnings("deprecation")
public class VanishMsg extends BukkitRunnable {
    Main main = Main.getInstance();
    private final Player player;

    public VanishMsg(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        if (!main.isVanished(player)) cancel();
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                ChatColor.GREEN + "Vous êtes actuellement en vanish !"
        ));
    }
}
