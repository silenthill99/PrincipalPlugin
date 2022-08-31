package fr.silenthill99.principalplugin;

import fr.silenthill99.principalplugin.commands.*;
import fr.silenthill99.principalplugin.listener.Events;
import fr.silenthill99.principalplugin.listener.Inventaire;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Main extends JavaPlugin
{

    public Map<UUID, List<String>> logs = new HashMap<>();
    public List<String> vanish = new ArrayList<>();
    public Location spawn = new Location(Bukkit.getWorld("world"), -157.108, 65, -56.714, -76.4f, 4.2f);
    private Map<UUID, Location> frozenPlayers;

    public HashMap<UUID, Double> argent = new HashMap<>();
    private static Main instance;
    public static Main getInstance()
    {
        return instance;
    }
    public MySQL mysql = new MySQL();
    public Economy economy = null;
    public int inter;

    @Override
    public void onEnable()
    {
        saveDefaultConfig();
        instance = this;
        setupEconomy();
        inter = 0;
        getLogger().info("Le plugin est opérationnel !");
        mysql.connect("minecraft1009.omgserv.com", 3306, "minecraft_235640", "minecraft_235640", "Mylene.10");
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new Events(), this);
        pm.registerEvents(new Inventaire(), this);
        commands();
        frozenPlayers = new HashMap<>();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void commands()
    {
        getCommand("builder").setExecutor(new Builder());
        getCommand("builder").setTabCompleter(new Staff());
        getCommand("hrp").setExecutor(new Hrp());
        getCommand("logs").setExecutor(new Logs());
        getCommand("menu").setExecutor(new Menu());
        getCommand("options").setExecutor(new Options());
        getCommand("radio").setExecutor(new Radio());
        getCommand("radio").setTabCompleter(new Radio());
        getCommand("setspawn").setExecutor(new Setspawn());
        getCommand("showvanish").setExecutor(new Showvanish());
        getCommand("spawn").setExecutor(new Spawn());
        getCommand("vanish").setExecutor(new Vanish());
        getCommand("vanish").setTabCompleter(new Staff());
    }

    public static boolean isPlayerInGroup(Player player, String group) {
        return player.hasPermission("group." + group);
    }

    public static String getPlayerGroup(Player player, Collection<String> possibleGroups) {
        for (String group : possibleGroups) {
            if (player.hasPermission("group." + group)) {
                return group;
            }
        }
        return null;
    }

    public Map<UUID, Location> getFrozenPlayers()
    {
        return frozenPlayers;
    }

    public boolean isFreeze(Player player)
    {
        return getFrozenPlayers().containsKey(player.getUniqueId());
    }

    public boolean setupEconomy(){
        RegisteredServiceProvider<Economy> eco = getServer().getServicesManager().getRegistration(Economy.class);
        if (eco != null) {
            economy = eco.getProvider();
        }
        return economy != null;
    }
}
