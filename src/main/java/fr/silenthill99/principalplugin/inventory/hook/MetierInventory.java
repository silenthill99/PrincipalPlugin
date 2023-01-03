package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import fr.silenthill99.principalplugin.inventory.holder.MetierHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class MetierInventory extends AbstractInventory<MetierHolder> {
    public MetierInventory() {
        super(MetierHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args)
    {
        ItemStack tete = new ItemBuilder(Material.PLAYER_HEAD).setName(ChatColor.GOLD + "Citoyen").toItemStack();
        ItemStack benevolat = new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.GREEN + "Faire du bénévolat").toItemStack();

        MetierHolder holder = new MetierHolder();
        Inventory inv = createInventory(holder, 54, ChatColor.YELLOW + "Pôle emploi");
        int slot = 1;
        inv.setItem(0, tete);
        for (Metier metier : Metier.values())
        {
            holder.metier.put(slot, metier);
            inv.setItem(slot++, new ItemBuilder(Material.PAPER).setName(ChatColor.GOLD + metier.getTitre()).toItemStack());
        }
        inv.setItem(53, benevolat);
        p.openInventory(inv);
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, MetierHolder holder) {
        Metier metier = holder.metier.get(e.getSlot());
        switch (current.getType())
        {
            case PLAYER_HEAD:
            {
                player.closeInventory();
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent set default");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "skin clear " + player.getName());
                player.sendMessage(ChatColor.GREEN + "Vous êtes désormais citoyen");
                break;
            }
            case PAPER:
            {
                player.closeInventory();
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent set " + metier.name().toLowerCase(Locale.ROOT));
                Bukkit.dispatchCommand(player, "skin set " + metier.getUrl());
                player.sendMessage(ChatColor.GREEN + "Vous êtes désormais " + metier.getTitre() + " !");
                break;
            }
            case GREEN_WOOL:
            {
                InventoryManager.openInventory(player, InventoryType.BENEVOLAT);
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
        if (target.getName().equalsIgnoreCase("pôle emploi"))
        {
            InventoryManager.openInventory(player, InventoryType.METIER);
        }
    }

    public enum Metier
    {
        CHASSEUR("Chasseur", "http://novask.in/5448143538.png"),
        CUISINIER("Cuisinier", "http://novask.in/5921353192.png"),
        FACTEUR("Facteur", "http://novask.in/2887483160.png"),
        MEDECIN("Médecin", "http://novask.in/5290663328.png"),
        POLICIER("Policier", "http://novask.in/5911833608.png"),
        POMPIER("Pompier", "http://novask.in/5925383309.png");
        private final String titre;
        private final String url;

        Metier(String titre, String url)
        {
            this.titre = titre;
            this.url = url;
        }

        public String getTitre() {
            return this.titre;
        }

        public String getUrl() {
            return this.url;
        }
    }
}
