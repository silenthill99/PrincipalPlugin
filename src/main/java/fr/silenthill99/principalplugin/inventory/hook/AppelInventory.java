package fr.silenthill99.principalplugin.inventory.hook;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.Main;
import fr.silenthill99.principalplugin.Variables;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import fr.silenthill99.principalplugin.inventory.holder.AppelHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.sql.Timestamp;

public class AppelInventory extends AbstractInventory<AppelHolder> {
    public AppelInventory() {
        super(AppelHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args)
    {
        ItemStack police = new ItemBuilder(Material.BLUE_WOOL).setName(ChatColor.DARK_BLUE + "Appeler la police").toItemStack();
        ItemStack pompiers = new ItemBuilder(Material.RED_WOOL).setName(ChatColor.DARK_RED + "Appeler les pompiers").toItemStack();
        ItemStack samu = new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.DARK_GREEN + "Appeler le SAMU").toItemStack();

        Inventory inv = createInventory(new AppelHolder(), 9, "Passer un appel");
        inv.setItem(0, police);
        inv.setItem(2, pompiers);
        inv.setItem(4, samu);
        inv.setItem(inv.getSize()-1, RETOUR);
        p.openInventory(inv);
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, AppelHolder holder) {
        float x = (float) player.getLocation().getX();
        float y = (float) player.getLocation().getY();
        float z = (float) player.getLocation().getZ();
        switch (current.getType())
        {
            case SUNFLOWER:
            {
                InventoryManager.openInventory(player, InventoryType.TELEPHONE);
                break;
            }
            case BLUE_WOOL:
            {
                player.closeInventory();
                player.sendMessage(ChatColor.DARK_BLUE + "Vous avez appelé la police. Veuillez rester sur place jusqu'à leur arrivée");
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (Main.isPlayerInGroup(players, "policier")) {
                        players.sendMessage(ChatColor.DARK_BLUE + "Une personne du nom de " + ChatColor.YELLOW + player.getName() + ChatColor.DARK_BLUE + " vous attend aux coordonnées " + ChatColor.YELLOW + "x: " + String.format("%.2f",x) + " y: " + String.format("%.2f", y) + " z: " + String.format("%.2f", z));
                    }
                }
                //Variables.logs.get(player.getUniqueId()).add(ChatColor.YELLOW + "[" + new Timestamp(System.currentTimeMillis()) + "]" + ChatColor.DARK_BLUE + player.getName() + ChatColor.BLUE + " a appelé la police aux coordonnées " + ChatColor.AQUA + "x: " + String.format("%.2f", x) + " y: " + String.format("%.2f", y) + " z: " + String.format("%.2f", z));
                break;
            }
            case RED_WOOL:
            {
                player.closeInventory();
                player.sendMessage(ChatColor.DARK_RED + "Vous venez de passer un appel aux pompiers. Veuillez rester à votre position jusqu'à l'arrivée des secours !");
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (Main.isPlayerInGroup(players, "pompier")) {
                        players.sendMessage(ChatColor.DARK_RED + "Vous venez de recevoir un appel de " + ChatColor.YELLOW + player.getName() + ChatColor.DARK_RED + ", il se trouve aux coordonnées " + ChatColor.YELLOW + "x: " + String.format("%.2f",x) + " y: " + String.format("%.2f",y) + " z: " + String.format("%.2f",z));
                    }
                }
                //Variables.logs.get(player.getUniqueId()).add(ChatColor.YELLOW + "[" + new Timestamp(System.currentTimeMillis()) + "]" + ChatColor.DARK_BLUE + player.getName() + ChatColor.BLUE + " a appelé les pompiers aux coordonnées " + ChatColor.AQUA + "x: " + String.format("%.2f", x) + " y: " + String.format("%.2f", y) + " z: " + String.format("%.2f", z));
                break;
            }
            case GREEN_WOOL:
            {
                player.closeInventory();
                player.sendMessage(ChatColor.DARK_GREEN + "Vous venez de lancer un appel au SAMU. Veuillez rester sur place jusqu'à l'arrivée des secours !");
                for (Player players : Bukkit.getOnlinePlayers())
                {
                    if (Main.isPlayerInGroup(players, "medecin"))
                    {
                        players.sendMessage(ChatColor.DARK_GREEN + "Une personne du nom de " + ChatColor.YELLOW + player.getName() + ChatColor.DARK_GREEN + " vous attend aux coordonnées " + ChatColor.YELLOW + "x: " + String.format("%.2f", x) + " y: " + String.format("%.2f", y) + " z: " + String.format("%.2f", z));
                    }
                }
                //Variables.logs.get(player.getUniqueId()).add(ChatColor.YELLOW + "[" + new Timestamp(System.currentTimeMillis()) + "]" + ChatColor.DARK_BLUE + player.getName() + ChatColor.BLUE + " a appelé le SAMU aux coordonnées " + ChatColor.AQUA + "x: " + String.format("%.2f", x) + " y: " + String.format("%.2f", y) + " z: " + String.format("%.2f", z));
                break;
            }
            default:
                break;
        }
    }
}
