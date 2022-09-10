package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import fr.silenthill99.principalplugin.inventory.Musiques;
import fr.silenthill99.principalplugin.inventory.holder.MusiqueHolder;
import fr.silenthill99.principalplugin.inventory.holder.musiques.MusiqueGestionHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

public class MusiqueInventory extends AbstractInventory<MusiqueHolder>
{
    public MusiqueInventory()
    {
        super(MusiqueHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args)
    {
        Inventory inv = createInventory(new MusiqueHolder(), 54, "Musiques");
        int slot = 0;
        for (String name : Arrays.asList("Hotel California", "On Melancholy Hill", "Pokemon", "Pumped Up Kicks", "Viva la vida"))
        {
            inv.setItem(slot++, new ItemBuilder(Material.NOTE_BLOCK).setName(ChatColor.GREEN + name).toItemStack());
        }
        inv.setItem(inv.getSize()-1, RETOUR);
        p.openInventory(inv);
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, MusiqueHolder holder)
    {
        switch (current.getType())
        {
            case NOTE_BLOCK:
            {
                MusiqueGestionHolder m_holder = new MusiqueGestionHolder(ChatColor.stripColor(current.getItemMeta().getDisplayName()));
                for (Musiques musics : Musiques.values())
                {
                    m_holder.customName.put(e.getSlot(), musics);
                }
                InventoryManager.openInventory(player, InventoryType.MUSIQUE_GESTION, m_holder.customName.get(e.getSlot()));
                break;
            }
            case SUNFLOWER:
            {
                InventoryManager.openInventory(player, InventoryType.TELEPHONE);
                break;
            }
            default:
                break;
        }
    }
}
