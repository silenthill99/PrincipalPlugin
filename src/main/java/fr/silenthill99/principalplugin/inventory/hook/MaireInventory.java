package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.Main;
import fr.silenthill99.principalplugin.Variables;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import fr.silenthill99.principalplugin.inventory.holder.MaireHolder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MaireInventory extends AbstractInventory<MaireHolder> {
    public MaireInventory() {
        super(MaireHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args)
    {
        ItemStack represrentant = new ItemBuilder(Material.WRITTEN_BOOK).setName(ChatColor.GOLD + " Se représenter").toItemStack();
        ItemStack voter = new ItemBuilder(Material.PAPER).setName(ChatColor.GOLD + "Voter").toItemStack();

        Inventory inv = createInventory(new MaireHolder(), 27, "Devenir maire");
        inv.setItem(10, represrentant);
        inv.setItem(16, voter);
        inv.setItem(inv.getSize()-1, RETOUR);
        p.openInventory(inv);
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, MaireHolder holder) {
        switch(current.getType())
        {
            case SUNFLOWER:
            {
                InventoryManager.openInventory(player, InventoryType.TELEPHONE);
                break;
            }
            case WRITTEN_BOOK:
            {
                if (!Main.isPlayerInGroup(player, "vip"))
                {
                    player.sendMessage(ChatColor.DARK_RED + "Réservé aux VIP");
                    return;
                }
                if (Variables.candidat.contains(player))
                {
                    player.sendMessage(ChatColor.RED + "Vous êtes déjà représentant !");
                    return;
                }

                Variables.candidat.add(player);
                player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Votre candidature a été postée avec succès !");
                player.closeInventory();
                break;
            }
            case PAPER:
            {
                InventoryManager.openInventory(player, InventoryType.CANDIDATS);
                break;
            }
            default:
            {
                break;
            }
        }
    }
}
