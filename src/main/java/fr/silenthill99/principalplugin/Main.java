package fr.silenthill99.principalplugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import fr.silenthill99.principalplugin.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.listener.Events;
import net.milkbowl.vault.economy.Economy;

public final class Main extends JavaPlugin
{

    public Map<UUID, List<String>> logs = new HashMap<>();
    public List<String> vanish = new ArrayList<>();
    private Map<UUID, Location> frozenPlayers = new HashMap<>();

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
        instance = this;
        setupEconomy();
        inter = 0;
        getLogger().info("Le plugin est opérationnel !");
        mysql.connect("minecraft1009.omgserv.com", 3306, "minecraft_235640", "minecraft_235640", "Mylene.10");
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new Events(), this);
        pm.registerEvents(new InventoryManager(), this);
        commands();
    }

    private void commands()
    {
        getCommand("administrateur").setExecutor(new Administrateur());
        getCommand("administrateur").setTabCompleter(new Staff());
        getCommand("builder").setExecutor(new Builder());
        getCommand("builder").setTabCompleter(new Staff());
        getCommand("developpeur").setExecutor(new Developpeur());
        getCommand("developpeur").setTabCompleter(new Staff());
        getCommand("hrp").setExecutor(new Hrp());
        getCommand("logs").setExecutor(new Logs());
        getCommand("menu").setExecutor(new Menu());
        getCommand("moderateur").setExecutor(new Moderateur());
        getCommand("moderateur").setTabCompleter(new Staff());
        getCommand("options").setExecutor(new Options());
        getCommand("radio").setExecutor(new Radio());
        getCommand("radio").setTabCompleter(new Radio());
        getCommand("responsable").setExecutor(new Responsable());
        getCommand("responsable").setTabCompleter(new Staff());
        getCommand("showvanish").setExecutor(new Showvanish());
        getCommand("stafftchat").setExecutor(new StaffTchat());
        getCommand("stagiaire").setExecutor(new Stagiaire());
        getCommand("stagiaire").setTabCompleter(new Staff());
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
    
    /**
     * Get spawn
     * 
     * @return the spawn location
     */

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
