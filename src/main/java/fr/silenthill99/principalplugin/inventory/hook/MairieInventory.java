package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import fr.silenthill99.principalplugin.inventory.holder.MairieHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MairieInventory extends AbstractInventory<MairieHolder> {
    public MairieInventory() {
        super(MairieHolder.class);
    }

    ItemStack dictature = new ItemBuilder(Material.RED_WOOL).setName(ChatColor.RED + "Régime : dictature").toItemStack();
    ItemStack democratie = new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.GREEN + "Régime : démocratie").toItemStack();

    @Override
    public void openInventory(Player p, Object... args)
    {
        MairieHolder holder = new MairieHolder();

        ItemStack lois = new ItemBuilder(Material.OAK_SIGN).setName("Lois").toItemStack();

        Inventory inv = createInventory(holder, 27, ChatColor.GREEN + "Mairie");
        inv.setItem(0, lois);
        if (holder.dictature)
        {
            inv.setItem(2, dictature);
        }
        else
        {
            inv.setItem(2, democratie);
        }
        p.openInventory(inv);
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, MairieHolder holder) {
        switch(current.getType()) {
            case RED_DYE: {
                Bukkit.broadcastMessage(ChatColor.GREEN + "Le maire est désormais en démocratie !");
                holder.dictature = false;
                player.getOpenInventory().setItem(2, democratie);
                break;
            }
            case GREEN_DYE: {
                Bukkit.broadcastMessage(ChatColor.RED + "Le maire vient de passer en dictature !");
                holder.dictature = true;
                player.getOpenInventory().setItem(2, dictature);
                break;
            }
            case OAK_SIGN: {
                InventoryManager.openInventory(player, InventoryType.LOIS);
                break;
            }
            default:
                break;
        }
    }
}
