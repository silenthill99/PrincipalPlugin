package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.Main;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.holder.MacDoHolder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MacDoInventory extends AbstractInventory<MacDoHolder> {
    public MacDoInventory() {
        super(MacDoHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args)
    {
        Inventory inv = createInventory(new MacDoHolder(), 36, "MacDo");
        MacDoHolder holder = (MacDoHolder) inv.getHolder();
        inv.setItem(0, holder.steak);
        inv.setItem(inv.getSize()-1, CLOSE);
        p.openInventory(inv);
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, MacDoHolder holder) {
        switch (current.getType())
        {
            case COOKED_BEEF:
            {
                if (Main.getInstance().economy.has(player, 10.0f))
                {
                    player.sendMessage(ChatColor.GREEN + "Vous avez acheté un steak pour 10€");
                    player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF));
                    Main.getInstance().economy.withdrawPlayer(player, 10.0f);
                }
                else
                {
                    player.sendMessage(ChatColor.RED + "Vous n'avez pas assez d'argent !");
                }
                break;
            }
            default:
                break;
        }
    }
}
