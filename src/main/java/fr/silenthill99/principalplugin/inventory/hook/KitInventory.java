package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.Main;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.holder.KitHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

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
                HashMap<UUID, Double> cooldown = new HashMap<>();

                if (cooldown.containsKey(player.getUniqueId()))
                {
                    player.sendMessage(ChatColor.RED + "Il reste encore " + (System.currentTimeMillis() - cooldown.get(player.getUniqueId())));
                    return;
                }

                ItemStack telephone = new ItemBuilder(Material.BRICK).setName(ChatColor.YELLOW + "Téléphone").toItemStack();
                player.closeInventory();
                player.getInventory().addItem(telephone);
                cooldown.put(player.getUniqueId(), (double) (System.currentTimeMillis() + 1000*60*25));
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> cooldown.remove(player.getUniqueId()), 20 * 60 * 25);
                break;
            default:
                break;
        }
    }
}
