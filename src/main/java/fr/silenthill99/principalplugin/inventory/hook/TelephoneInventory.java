package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.Items;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import fr.silenthill99.principalplugin.inventory.holder.TelephoneHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TelephoneInventory extends AbstractInventory<TelephoneHolder> {
    public TelephoneInventory()
    {
        super(TelephoneHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args) {

        ItemStack appel = new ItemBuilder(Material.BRICK).setName(ChatColor.YELLOW + "Passer un appel").toItemStack();
        ItemStack argent = new ItemBuilder(Material.GOLD_INGOT).setName(ChatColor.GREEN + "Voir son argent").toItemStack();
        ItemStack musique = new ItemBuilder(Material.JUKEBOX).setName(ChatColor.GREEN + "Ecouter de la musique").toItemStack();
        ItemStack gps = new ItemBuilder(Material.COMPASS).setName(ChatColor.GREEN + "GPS").toItemStack();
        ItemStack mairie = new ItemBuilder(Material.REPEATER).setName(ChatColor.GREEN + "Mairie").toItemStack();

        Inventory inv = createInventory(new TelephoneHolder(), 27, "Téléphone");
        inv.setItem(0, appel);
        inv.setItem(1, argent);
        inv.setItem(2, musique);
        inv.setItem(3, gps);
        inv.setItem(4, mairie);
        inv.setItem(inv.getSize()-1, CLOSE);
        p.openInventory(inv);
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, TelephoneHolder holder) {
        switch(current.getType())
        {
            case BRICK:
                InventoryManager.openInventory(player, InventoryType.APPEL);
                break;
            case GOLD_INGOT:
                player.closeInventory();
                Bukkit.dispatchCommand(player, "money");
                break;
            case JUKEBOX:
                InventoryManager.openInventory(player, InventoryType.MUSIQUE);
                break;
            case COMPASS:
            {
                player.sendMessage(ChatColor.RED + "Attention ! Système en bêta");
                InventoryManager.openInventory(player, InventoryType.GPS);
                break;
            }
            case REPEATER:
            {
                InventoryManager.openInventory(player, InventoryType.MAIRE);
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack it = e.getItem();
        Action action = e.getAction();
        if (it == null)
        {
            return;
        }
        if (it.equals(Items.TELEPHONE.getItems()) && (action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_AIR)))
        {
            InventoryManager.openInventory(player, InventoryType.TELEPHONE);
        }
    }
}
