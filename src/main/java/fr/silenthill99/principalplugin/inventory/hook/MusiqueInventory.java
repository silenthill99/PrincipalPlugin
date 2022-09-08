package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import fr.silenthill99.principalplugin.inventory.holder.MusiqueHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
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
        for (String name : Arrays.asList("Hotel California", "On Melancholy Hill", "Pumped Up Kicks"))
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
                String name = ChatColor.stripColor(current.getItemMeta().getDisplayName()).replace(" ", "_").toLowerCase(Locale.ROOT);
                player.closeInventory();
                Bukkit.dispatchCommand(player, "function " + name + ":play");
                break;
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
