package fr.silenthill99.principalplugin.commands;

import fr.silenthill99.principalplugin.Main;
import fr.silenthill99.principalplugin.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddGPSCoord implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player)) {
            System.out.println("Cette commande ne peut pas âtre exécutée par la console");
            return false;
        }

        Player player = (Player) commandSender;

        if (args.length != 4) {
            player.sendMessage(ChatColor.RED + "/add-gps-coord <nom> <x> <y> <z>");
            return false;
        }

        String name = args[0];
        double x = Double.parseDouble(args[1]);
        double y = Double.parseDouble(args[2]);
        double z = Double.parseDouble(args[3]);

        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            try (Connection conn = MySQL.getInstance().getConnection();
                 PreparedStatement statement = conn.prepareStatement("INSERT INTO locations (name, x, y, z) VALUES (?, ?, ?, ?)")) {
                statement.setString(1, name);
                statement.setDouble(2, x);
                statement.setDouble(3, y);
                statement.setDouble(4, z);
                statement.executeUpdate();

                Bukkit.getScheduler().runTask(Main.getInstance(), () ->
                    player.sendMessage(ChatColor.GREEN + "Coordonnée GPS '" + name + "' ajoutée !"));
            } catch (SQLException e) {
                Bukkit.getScheduler().runTask(Main.getInstance(), () ->
                    player.sendMessage(ChatColor.RED + "Erreur lors de l'ajout de la coordonnée."));
                e.printStackTrace();
            }
        });

        return true;
    }
}
