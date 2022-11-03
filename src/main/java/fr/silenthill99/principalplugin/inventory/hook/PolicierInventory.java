package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.Main;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import fr.silenthill99.principalplugin.inventory.holder.PolicierHolder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;

public class PolicierInventory extends AbstractInventory<PolicierHolder>
{
    public PolicierInventory() {
        super(PolicierHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args)
    {
        Player target = (Player) args[0];
        PolicierHolder holder = new PolicierHolder(target);

        Inventory inv = createInventory(holder, 18, ChatColor.BLUE + "Menu Policier");
        p.openInventory(inv);
    }

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        if (!Main.isPlayerInGroup(player, "policier") && !player.isOp()) return;
        if (!(event.getRightClicked() instanceof Player)) return;

        Player target = (Player) event.getRightClicked();
        InventoryManager.openInventory(player, InventoryType.POLICIER, target);
    }
}
