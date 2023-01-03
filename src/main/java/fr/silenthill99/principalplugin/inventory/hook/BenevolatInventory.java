package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import fr.silenthill99.principalplugin.inventory.holder.BenevolatHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BenevolatInventory extends AbstractInventory<BenevolatHolder>
{
    public BenevolatInventory()
    {
        super(BenevolatHolder.class);
    }

    @Override
    public void openInventory(Player player, Object... args)
    {
        BenevolatHolder holder = new BenevolatHolder();
        Inventory inv = createInventory(holder, 27, "Choisis une association");
        int slot = 0;
        for (Association association : Association.values())
        {
            holder.associations.put(slot, association);
            inv.setItem(slot++, new ItemBuilder(Material.PAPER).setName(association.getName()).toItemStack());
        }
        inv.setItem(inv.getSize()-1, RETOUR);
        player.openInventory(inv);
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, BenevolatHolder holder)
    {
        Association association = holder.associations.get(e.getSlot());
        switch (current.getType())
        {
            case SUNFLOWER:
            {
                InventoryManager.openInventory(player, InventoryType.METIER);
                break;
            }
            case PAPER:
            {
                player.closeInventory();
                Bukkit.dispatchCommand(player, "skin set " + association.getUrl());
                break;
            }
            default:
            {
                break;
            }
        }
    }

    public enum Association
    {
        LA_CROIX_ROUGE(ChatColor.RED + "La croix rouge", "http://novask.in/4425683059.png")
        ;
        private final String name;
        private final String url;

        Association(String name, String url)
        {
            this.name = name;
            this.url = url;
        }

        public String getName()
        {
            return this.name;
        }

        public String getUrl()
        {
            return this.url;
        }
    }
}
