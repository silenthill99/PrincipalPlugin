package fr.silenthill99.principalplugin.inventory.hook.modo;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.Main;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import fr.silenthill99.principalplugin.inventory.holder.modo.PlayerMenuHolder;
import fr.silenthill99.principalplugin.inventory.hook.modo.PlayerSanctionInventory.SanctionType;

public class PlayerMenuInventory extends AbstractInventory<PlayerMenuHolder> {

    public PlayerMenuInventory() {
        super(PlayerMenuHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args) {
        OfflinePlayer target = (OfflinePlayer) args[0];

        Inventory menu = createInventory(new PlayerMenuHolder(target), 27, "Menu de " + target.getName());
        menu.setItem(0, new ItemBuilder(Material.YELLOW_WOOL).setName("Voir le rôle du joueur").toItemStack());
        menu.setItem(4, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(target.getName()).toItemStack());
        menu.setItem(10, new ItemBuilder(Material.LIME_WOOL)
                .setName(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Sanctionner " + target.getName()).toItemStack());
        menu.setItem(13,
                new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Administration").toItemStack());
        menu.setItem(16, new ItemBuilder(Material.CHEST).setName(ChatColor.YELLOW + "Options").toItemStack());
        p.openInventory(menu);
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, PlayerMenuHolder holder) {
        OfflinePlayer target = holder.getTarget();

        switch (current.getType()) {
            case LIME_WOOL:
                InventoryManager.openInventory(player, InventoryType.MODO_PLAYER_SANCTION, target, SanctionType.MENU, 1);
                break;
            case CHEST:
                InventoryManager.openInventory(player, InventoryType.OPTIONS_JOUEUR, target);
                break;
            case REDSTONE:
                if (!Main.isPlayerInGroup(player, "administrateur")) {
                    player.sendMessage(ChatColor.DARK_RED + "Vous n'avez pas accès à cette catégorie");
                    return;
                }
                InventoryManager.openInventory(player, InventoryType.ADMIN_MENU, holder.getTarget());
                break;
            case YELLOW_WOOL: {
                player.closeInventory();
                player.sendMessage(target.getUniqueId().toString());
                break;
            }
            default:
                break;
        }
    }
}
