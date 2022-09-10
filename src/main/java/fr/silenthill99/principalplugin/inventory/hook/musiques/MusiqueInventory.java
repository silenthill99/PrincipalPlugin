package fr.silenthill99.principalplugin.inventory.hook.musiques;

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
import fr.silenthill99.principalplugin.inventory.holder.musiques.MusiqueHolder;

public class MusiqueInventory extends AbstractInventory<MusiqueHolder>
{
    public MusiqueInventory()
    {
        super(MusiqueHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args)
    {
    	MusiqueHolder holder = new MusiqueHolder();
        Inventory inv = createInventory(holder, 54, "Musiques");
        int slot = 0;
        for(Musiques music : Musiques.values())
        {
        	holder.musics.put(slot, music);
            inv.setItem(slot++, new ItemBuilder(Material.NOTE_BLOCK).setName(ChatColor.GREEN + music.getName()).toItemStack());
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
                InventoryManager.openInventory(player, InventoryType.MUSIQUE_GESTION, holder.musics.get(e.getSlot()));
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
