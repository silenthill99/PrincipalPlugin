package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.Items;
import fr.silenthill99.principalplugin.Main;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.holder.KitHolder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class KitInventory extends AbstractInventory<KitHolder> {

    HashMap<UUID, Long> cooldown = new HashMap<>();
    public KitInventory() {
        super(KitHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args)
    {
        ItemStack depart = new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.DARK_GREEN + "Kit de départ").setLore("Toutes les 24 heures").toItemStack();
        Inventory inv = createInventory(new KitHolder(), 27, "Kits");
        inv.setItem(0, depart);
        p.openInventory(inv);
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, KitHolder holder) {
        switch (current.getType())
        {
            case GREEN_WOOL:
            {
                if (cooldown.containsKey(player.getUniqueId())) {
                    long time = cooldown.get(player.getUniqueId()) - System.currentTimeMillis();
                    if (time > 0) {
                        player.sendMessage(ChatColor.RED + "Il reste encore " + Main.convertSecondsToHMmSs(time / 1000));
                        return;
                    }
                }

                /*ItemStack telephone = new ItemBuilder(Material.BRICK).setName(ChatColor.YELLOW + "Téléphone").toItemStack();*/
                player.closeInventory();
                player.getInventory().addItem(Items.TELEPHONE.getItems().toItemStack());
                player.getInventory().addItem(ItemBuilder.getArgent(30));
                cooldown.put(player.getUniqueId(), System.currentTimeMillis() + 1000 * 3600 * 24);
                break;
            }
            default:
                break;
        }
    }
}
