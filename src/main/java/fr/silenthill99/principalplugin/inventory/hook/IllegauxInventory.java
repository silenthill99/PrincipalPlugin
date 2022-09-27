package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.holder.IllegauxHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class IllegauxInventory extends AbstractInventory<IllegauxHolder> {
    public IllegauxInventory() {
        super(IllegauxHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args)
    {
        IllegauxHolder holder = new IllegauxHolder();

        Inventory inv = createInventory(new IllegauxHolder(), 27, ChatColor.DARK_RED + "Métiers illégaux");
        int slot = 0;
        for (Metiers metiers : Metiers.values())
        {
            holder.metiers.put(slot, metiers);
            inv.setItem(slot++, new ItemBuilder(Material.PAPER).setName(ChatColor.DARK_RED + metiers.name).toItemStack());
        }
        inv.setItem(inv.getSize()-1, CLOSE);
        p.openInventory(inv);
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, IllegauxHolder holder) {
        switch (current.getType())
        {
            case PAPER:
            {
                player.closeInventory();
                if (holder.metiers.get(e.getSlot()) == null)
                {
                    return;
                }
                Bukkit.dispatchCommand(player, "lp user " + player.getName() + " parent set " + holder.metiers.get(e.getSlot()).name().toLowerCase(Locale.ROOT));
                Bukkit.dispatchCommand(player, "skin set " + holder.metiers.get(e.getSlot()).getUrl());
                player.sendMessage(ChatColor.RED + "Vous êtes désormais " + holder.metiers.get(e.getSlot()).getName() + " !");
                break;
            }
            default:
            {
                break;
            }
        }
    }

    public enum Metiers {
        GANGSTER("Gangster", "http://novask.in/5621013128.png")
        ;

        private String name;
        private String url;

        Metiers(String name, String url)
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

        public void setName(String name)
        {
            this.name = name;
        }

        public void setUrl(String url)
        {
            this.url = url;
        }
    }
}
