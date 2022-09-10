package fr.silenthill99.principalplugin.inventory.hook.musiques;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import fr.silenthill99.principalplugin.inventory.Musiques;
import fr.silenthill99.principalplugin.inventory.holder.musiques.MusiqueGestionHolder;

public class MusiqueGestionInventory extends AbstractInventory<MusiqueGestionHolder>
{
    public MusiqueGestionInventory() {
        super(MusiqueGestionHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args)
    {
        Musiques music = (Musiques) args[0];
        Inventory inv = createInventory(new MusiqueGestionHolder(music), 9, music.getName());
        inv.setItem(0, new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.GREEN + "Ecouter la musique").toItemStack());
        inv.setItem(1, new ItemBuilder(Material.ORANGE_WOOL).setName(ChatColor.GOLD + "Mettre sur pause").toItemStack());
        inv.setItem(2, new ItemBuilder(Material.RED_WOOL).setName(ChatColor.RED + "Arrêter la musique").toItemStack());
        inv.setItem(8, RETOUR);
        p.openInventory(inv);
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, MusiqueGestionHolder holder)
    {
        player.closeInventory();
        switch (current.getType())
        {
            case GREEN_WOOL:
            {
                Bukkit.dispatchCommand(player, "function " + holder.getMusic().name().toLowerCase() + ":play");
                break;
            }
            case ORANGE_WOOL:
            {
                Bukkit.dispatchCommand(player, "function " + holder.getMusic().name().toLowerCase() + ":pause");
                break;
            }
            case RED_WOOL:
            {
                Bukkit.dispatchCommand(player, "function " + holder.getMusic().name().toLowerCase() + ":stop");
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
