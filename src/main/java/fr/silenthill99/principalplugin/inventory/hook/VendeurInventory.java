package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.Main;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.holder.VendeurHolder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class VendeurInventory extends AbstractInventory<VendeurHolder> {
    public VendeurInventory() {
        super(VendeurHolder.class);
    }

    @Override
    public void openInventory(Player player, Object... args) {
        VendeurHolder holder = new VendeurHolder();

        ItemStack jukebox = new ItemBuilder(Material.JUKEBOX).setLore("Prix : 15€").toItemStack();

        Inventory inv = createInventory(holder, 27, ChatColor.RED + "Magasin de musique");
        inv.setItem(4, jukebox);
        player.openInventory(inv);
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, VendeurHolder holder) {
        switch (current.getType()) {
            case JUKEBOX -> {
                if (Main.getEconomy().has(player, 15)) {
                    player.getInventory().addItem(new ItemStack(Material.JUKEBOX));
                    Main.getEconomy().withdrawPlayer(player, 15);
                } else {
                    player.sendMessage(ChatColor.RED + "Vous n'avez pas assez d'arent !");
                }
            }
        }
    }

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (event.getRightClicked() instanceof Player) return;
        Player target = (Player) event.getRightClicked();

        if (!event.getHand().equals(EquipmentSlot.HAND)) return;

        if (target.getName().equals("Vendeur")) {
            openInventory(player);
        }
    }
}
