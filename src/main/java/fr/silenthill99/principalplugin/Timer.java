package fr.silenthill99.principalplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Timer implements Runnable
{
    private Player player;
    public Timer(Player player)
    {
        this.player = player;
    }

    @Override
    public void run()
    {
        Bukkit.dispatchCommand(player, "lp user " + player.getName() + " parent set default");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "list");
        player.sendMessage(ChatColor.GREEN + "Vous êtes désormais en mode citoyen !");
    }

    public Player player() {
        return player;
    }
}
