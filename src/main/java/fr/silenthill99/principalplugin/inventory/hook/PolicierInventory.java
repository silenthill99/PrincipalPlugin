package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.Main;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import fr.silenthill99.principalplugin.inventory.holder.PolicierHolder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PolicierInventory extends AbstractInventory<PolicierHolder>
{
    public PolicierInventory() {
        super(PolicierHolder.class);
    }

    @Override
    public void openInventory(Player player, Object... args)
    {
        Player target = (Player) args[0];
        PolicierHolder holder = new PolicierHolder(target);

        ItemStack amende = new ItemBuilder(Material.PAPER).setName(ChatColor.BLUE + "Mettre une amende").toItemStack();

        Inventory inv = createInventory(holder, 18, ChatColor.BLUE + "Menu Policier");
        inv.setItem(0, amende);
        if (Main.isPlayerInGroup(target, "cuisinier"))
        {
            ItemStack magasin = new ItemBuilder(Material.BOOK).setName(ChatColor.GOLD + "Ouvrir le magazin").toItemStack();
            inv.setItem(inv.getSize()-1, magasin);
        }
        player.openInventory(inv);
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, PolicierHolder holder) {
        Player target = (Player) holder.getTarget();
        switch (current.getType())
        {
            case PAPER :
            {
                InventoryManager.openInventory(player, InventoryType.AMENDE, target);
                break;
            }
        }
    }

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (!(event.getRightClicked() instanceof Player)) return;
        Player target = (Player) event.getRightClicked();

        if (!Main.isPlayerInGroup(player, "policier") || player.isOp() || target.hasMetadata("NPC")) return;

        InventoryManager.openInventory(player, InventoryType.POLICIER, target);
    }
}
