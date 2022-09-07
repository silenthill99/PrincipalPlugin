package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import fr.silenthill99.principalplugin.inventory.holder.TelephoneHolder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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

        Inventory inv = createInventory(new TelephoneHolder(), 27, "Téléphone");
        inv.setItem(0, appel);
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
            default:
                break;
        }
    }

    @Override
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack it = e.getItem();
        if (it == null)
        {
            return;
        }
        if (it.getType().equals(Material.BRICK) && it.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "Téléphone"))
        {
            InventoryManager.openInventory(player, InventoryType.TELEPHONE);
        }
    }
}
