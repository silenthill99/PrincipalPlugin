package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.holder.MacDoHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MacDoInventory extends AbstractInventory<MacDoHolder> {
    public MacDoInventory() {
        super(MacDoHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args)
    {
        MacDoHolder holder = new MacDoHolder();
        ItemStack steak = new ItemBuilder(Material.COOKED_BEEF).setLore("Prix : 10€").toItemStack();

        Inventory inv = createInventory(holder, 36, "MacDo");
        inv.setItem(0, steak);
        inv.setItem(inv.getSize()-1, CLOSE);
        p.openInventory(inv);
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, MacDoHolder holder) {
        switch (current.getType())
        {
            case COOKED_BEEF:
            {
                player.sendMessage(ChatColor.GREEN + "Vous avez acheté un steak pour 10€");
                player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF));
                Bukkit.dispatchCommand(player, "eco take 10");
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity target = event.getRightClicked();

        if (target.getName().equalsIgnoreCase("MacDo") || target.getName().equalsIgnoreCase("Philippe"))
        {
            openInventory(player);
        }
    }
}
