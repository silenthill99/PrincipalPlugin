package fr.silenthill99.principalplugin.inventory.hook.musiques;

import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import fr.silenthill99.principalplugin.inventory.holder.musiques.MusiqueGestionHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class MusiqueGestionInventory extends AbstractInventory<MusiqueGestionHolder>
{
    public MusiqueGestionInventory() {
        super(MusiqueGestionHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args)
    {
        String name = (String) args[0];
        Inventory inv = createInventory(new MusiqueGestionHolder(name), 9, name);
        inv.setItem(0, ECOUTER);
        inv.setItem(1, PAUSE);
        inv.setItem(2, ARRET);
        inv.setItem(8, RETOUR);
        p.openInventory(inv);
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, MusiqueGestionHolder holder)
    {
        String name = ChatColor.stripColor(holder.name()).replace(" ", "_").toLowerCase(Locale.ROOT);
        player.closeInventory();
        switch (current.getType())
        {
            case GREEN_WOOL:
            {
                Bukkit.dispatchCommand(player, "function " + name + ":play");
                break;
            }
            case ORANGE_WOOL:
            {
                Bukkit.dispatchCommand(player, "function " + name + ":pause");
                break;
            }
            case RED_WOOL:
            {
                Bukkit.dispatchCommand(player, "function " + name + ":stop");
                break;
            }
            case SUNFLOWER:
            {
                InventoryManager.openInventory(player, InventoryType.MUSIQUE);
                break;
            }
            default:
                break;
        }
    }
}
