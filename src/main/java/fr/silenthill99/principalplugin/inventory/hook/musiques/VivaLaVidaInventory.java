package fr.silenthill99.principalplugin.inventory.hook.musiques;

import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import fr.silenthill99.principalplugin.inventory.holder.musiques.VivaLaVidaHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class VivaLaVidaInventory extends AbstractInventory<VivaLaVidaHolder>
{
    public VivaLaVidaInventory()
    {
        super(VivaLaVidaHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args)
    {
        Inventory inv = createInventory(new VivaLaVidaHolder(), 9, "Viva la vida");
        inv.setItem(0, ECOUTER);
        inv.setItem(1, PAUSE);
        inv.setItem(2, ARRET);
        inv.setItem(8, RETOUR);
        p.openInventory(inv);
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, VivaLaVidaHolder holder) {
        player.closeInventory();
        switch (current.getType())
        {
            case GREEN_WOOL:
            {
                player.sendMessage(ChatColor.GREEN + "Lecture en cours : Coldplay - Viva la vida");
                Bukkit.dispatchCommand(player, "function viva_la_vida:play");
                break;
            }
            case ORANGE_WOOL:
            {
                Bukkit.dispatchCommand(player, "function viva_la_vida:pause");
                break;
            }
            case RED_WOOL:
            {
                Bukkit.dispatchCommand(player, "function viva_la_vida:stop");
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
