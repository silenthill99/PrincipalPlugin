package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import fr.silenthill99.principalplugin.inventory.holder.ArmurerieHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ArmurerieInventory extends AbstractInventory<ArmurerieHolder> {
    public ArmurerieInventory() {
        super(ArmurerieHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args)
    {
        ItemStack desert_eagle = new ItemBuilder(Material.WOODEN_HOE).setName(ChatColor.YELLOW + "Desert Eagle").setLore("Prix : 200€").toItemStack();

        Inventory inv = createInventory(new ArmurerieHolder(), 36, "Armurerie");
        inv.setItem(0, desert_eagle);
        inv.setItem(inv.getSize()-1, CLOSE);
        p.openInventory(inv);
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, ArmurerieHolder holder) {
        switch (current.getType())
        {
            case WOODEN_HOE:
            {
                player.closeInventory();
                player.sendMessage(ChatColor.GREEN + "Vous avez acheté un Desert Eagle à 200€");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "shot give " + player.getName() + " deagle");
                Bukkit.dispatchCommand(player, "eco take 200");
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

        if (target.getName().equalsIgnoreCase("Armurier"))
        {
            InventoryManager.openInventory(player, InventoryType.ARMURERIE);
        }
    }
}
