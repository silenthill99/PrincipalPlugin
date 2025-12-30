package fr.silenthill99.principalplugin;

import fr.silenthill99.principalplugin.commands.*;
import fr.silenthill99.principalplugin.commands.staff.*;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.listener.Events;
import fr.silenthill99.principalplugin.timer.AutoMessage;
import fr.silenthill99.principalplugin.timer.Salaire;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

@SuppressWarnings({"DataFlowIssue", "unused"})
public final class Main extends JavaPlugin
{
    private static Economy econ;
    private Salaire salaire;
    private Map<UUID, Location> frozenPlayers;

    private static Main instance;
    public static Main getInstance()
    {
        return instance;
    }
    public int inter;

    private List<String> vanished;

    @Override
    public void onEnable()
    {
        saveDefaultConfig();
        instance = this;
        inter = 0;
        getLogger().info("Le plugin est opérationnel !");
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new Events(), this);
        pm.registerEvents(new InventoryManager(), this);
        commands();
        MySQL.getInstance();
        new AutoMessage().runTaskTimer(this, 30*20, 30*20);
        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
        }
        salaire = new Salaire();
        salaire.runTaskTimer(this, 20*60*10, 20*60*10);
        vanished = new ArrayList<>();
        frozenPlayers = new HashMap<>();
    }

    private void commands()
    {
        getCommand("action").setExecutor(new Action());
        getCommand("add-gps-coord").setExecutor(new AddGPSCoord());
        getCommand("aide").setExecutor(new Aide());
        getCommand("administrateur").setExecutor(new Administrateur());
        getCommand("administrateur").setTabCompleter(new Staff());
        getCommand("builder").setExecutor(new Builder());
        getCommand("builder").setTabCompleter(new Staff());
        getCommand("clearlag").setExecutor(new ClearLagCommand());
        getCommand("co-fondateur").setExecutor(new CoFondateur());
        getCommand("co-fondateur").setTabCompleter(new Staff());
        getCommand("developpeur").setExecutor(new Developpeur());
        getCommand("developpeur").setTabCompleter(new Staff());
        getCommand("fondateur").setExecutor(new Fondateur());
        getCommand("fondateur").setTabCompleter(new Staff());
        getCommand("hrp").setExecutor(new Hrp());
        getCommand("kit").setExecutor(new Kit());
        getCommand("logs").setExecutor(new Logs());
        getCommand("lois").setExecutor(new Lois());
        getCommand("maire").setExecutor(new Maire());
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
        getCommand("test").setExecutor(new Test());
        getCommand("vanish").setExecutor(new Vanish());
        getCommand("vanish").setTabCompleter(new Staff());
        getCommand("worldname").setExecutor(new WorldName());
        
    }

    @Override
    public void onDisable() {
        MySQL.getInstance().disconnect();
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

    public static String convertSecondsToHMmSs(long seconds) {
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        return String.format("%dh %02dmin et %02ds", h,m,s);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public List<String> getVanished() {
        return vanished;
    }

    public boolean isVanished(Player player) {
        return vanished.contains(player.getName());
    }

    public boolean consumeItem(Player player, int count, Material mat) {
        Map<Integer, ? extends ItemStack> ammo = player.getInventory().all(mat);

        int found = 0;
        for (ItemStack stack : ammo.values()) {
            if (!stack.isSimilar(Items.ARGENT.getItems().toItemStack())) continue;
            found += stack.getAmount();
        }
        if (count > found)
            return false;

        for (Integer index : ammo.keySet()) {
            ItemStack stack = ammo.get(index);
            int removed = Math.min(count, stack.getAmount());
            count -= removed;

            if (stack.getAmount() == removed)
                player.getInventory().setItem(index, null);
            else
                stack.setAmount(stack.getAmount() - removed);

            if (count <= 0)
                break;
        }
        player.updateInventory();
        return true;
    }
}
