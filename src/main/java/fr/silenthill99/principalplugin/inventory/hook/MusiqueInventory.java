package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import fr.silenthill99.principalplugin.inventory.holder.MusiqueHolder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

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
                if (current.getItemMeta().getDisplayName().contains("Hotel California"))
                {
                    InventoryManager.openInventory(player, InventoryType.HOTEL_CALIFORNIA);
                }
                else if (current.getItemMeta().getDisplayName().contains("On Melancholy Hill"))
                {
                    InventoryManager.openInventory(player, InventoryType.ON_MELANCHOLY_HILL);
                }
                else if (current.getItemMeta().getDisplayName().contains("Pokemon"))
                {
                    InventoryManager.openInventory(player, InventoryType.POKEMON);
                }
                else if (current.getItemMeta().getDisplayName().contains("Pumped Up Kicks"))
                {
                    InventoryManager.openInventory(player, InventoryType.PUMPED_UP_KICKS);
                }
                else if (current.getItemMeta().getDisplayName().contains("Viva la vida"))
                {
                    InventoryManager.openInventory(player, InventoryType.VIVA_LA_VIDA);
                }
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
