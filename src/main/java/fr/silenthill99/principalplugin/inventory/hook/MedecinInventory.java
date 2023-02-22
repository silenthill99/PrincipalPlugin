package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import fr.silenthill99.principalplugin.inventory.holder.MedecinHolder;
import fr.silenthill99.principalplugin.inventory.hook.MetierInventory.Metier;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class MedecinInventory extends AbstractInventory<MedecinHolder>
{
    public MedecinInventory()
    {
        super(MedecinHolder.class);
    }

    @Override
    public void openInventory(Player player, Object... args)
    {
        MedecinHolder holder = new MedecinHolder();

        ItemStack generaliste = new ItemBuilder(Material.PAPER).setName(ChatColor.YELLOW + "Médecin Généraliste").toItemStack();

        Inventory inv = createInventory(holder, 36, "Choisis une spécialité");
        inv.setItem(0, generaliste);
        inv.setItem(8, RETOUR);
        player.openInventory(inv);
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, MedecinHolder holder) {
        Metier metier = Metier.MEDECIN;
        switch(current.getType())
        {
            case PAPER:
            {
                player.closeInventory();
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent set " + metier.name().toLowerCase(Locale.ROOT));
                Bukkit.dispatchCommand(player, "skin set " + metier.getUrl());
                player.sendMessage(ChatColor.GREEN + "Vous êtes désormais " + metier.getTitre() + " !");
                break;
            }
            case SUNFLOWER:
            {
                InventoryManager.openInventory(player, InventoryType.METIER);
                break;
            }
        }
    }
}
