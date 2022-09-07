package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.holder.KitHolder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class KitInventory extends AbstractInventory<KitHolder> {
    public KitInventory() {
        super(KitHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args)
    {
        ItemStack depart = new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.DARK_GREEN + "Kit de départ").toItemStack();
        Inventory inv = createInventory(new KitHolder(), 27, "Kits");
        inv.setItem(0, depart);
        p.openInventory(inv);
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, KitHolder holder) {
        switch (current.getType())
        {
            case GREEN_WOOL:
                ItemStack telephone = new ItemBuilder(Material.BRICK).setName(ChatColor.YELLOW + "Téléphone").toItemStack();
                player.closeInventory();
                player.getInventory().addItem(telephone);
                break;
            default:
                break;
        }
    }
}
