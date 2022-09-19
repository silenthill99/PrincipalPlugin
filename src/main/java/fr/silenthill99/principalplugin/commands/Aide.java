package fr.silenthill99.principalplugin.commands;

import fr.silenthill99.principalplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class Aide implements CommandExecutor
{
    private HashMap<UUID, Long> cooldown = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        if (!(sender instanceof Player))
        {
            System.out.println("Cette commande ne peut pa sêtre éxécutée par la console !");
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0)
        {
            player.sendMessage(ChatColor.RED + "Veuillez faire /aide <message>");
            return false;
        }

        if (cooldown.containsKey(player.getUniqueId()))
        {
            long time = cooldown.get(player.getUniqueId()) - System.currentTimeMillis();
            if (time > 0)
            {
                player.sendMessage(ChatColor.RED + "Veuillez attendre " + ChatColor.YELLOW + Main.convertSecondsToHMmSs(time/1000) + ChatColor.RED + " pour refaire une demande d'aide !");
                return false;
            }
        }

        StringBuilder aide = new StringBuilder();

        for (String part : args)
        {
            aide.append(part + " ");
        }

        player.sendMessage(ChatColor.LIGHT_PURPLE + "Votre demande a bien été envoyée.\nVeuillez rester connecté(e), un membre du staff devrait arriver d'ici peu.");

        for (Player players : Bukkit.getOnlinePlayers())
        {
            if (Main.isPlayerInGroup(players, "modo-stagiaire"))
            {
                players.sendMessage(ChatColor.DARK_RED + "[Demande d'aide de " + player.getName() + "] " + ChatColor.GOLD + aide);
            }
        }
        cooldown.put(player.getUniqueId(), System.currentTimeMillis()+1000*60*5);

        return false;
    }
}
