package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import fr.silenthill99.principalplugin.inventory.holder.IllegauxHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
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
        Inventory inv = createInventory(holder, 27, ChatColor.DARK_RED + "Métiers illégaux");
        int slot = 0;
        for (Metiers metiers : Metiers.values())
        {
            holder.metiers.put(slot, metiers);
            inv.setItem(slot++, new ItemBuilder(Material.PAPER).setName(ChatColor.DARK_RED + metiers.getName()).toItemStack());
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
                Metiers illegal = holder.metiers.get(e.getSlot());
                player.closeInventory();
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent set " + illegal.name().toLowerCase(Locale.ROOT));
                Bukkit.dispatchCommand(player, "skin set " + illegal.getUrl());
                player.sendMessage(ChatColor.RED + "Vous êtes désormais " + illegal.getName() + " !");
                break;
            }
            default:
            {
                break;
            }
        }
    }

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity target = event.getRightClicked();
        if (target.getName().equalsIgnoreCase("Gangster"))
        {
            InventoryManager.openInventory(player, InventoryType.METIERS_ILLEGAUX);
        }
    }

    public enum Metiers
    {
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
