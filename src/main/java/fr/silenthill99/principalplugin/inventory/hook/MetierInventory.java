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
        int page = (int) args[0];
        MetierHolder holder = new MetierHolder(page);

        ItemStack tete = new ItemBuilder(Material.PLAYER_HEAD).setName(ChatColor.GOLD + "Citoyen").toItemStack();
        ItemStack benevolat = new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.GREEN + "Faire du bénévolat").toItemStack();
        ItemStack suivant = new ItemBuilder(Material.GREEN_DYE).setName(ChatColor.GREEN + "Page suivante").toItemStack();
        ItemStack precedent = new ItemBuilder(Material.RED_DYE).setName(ChatColor.RED + "Page précédente").toItemStack();

        Inventory inv = createInventory(holder, 54, ChatColor.YELLOW + "Pôle emploi");
        int slot = 0;

        if (page < 2)
        {
            inv.setItem(53, suivant);
        }
        else
            inv.setItem(45, precedent);

        if (page == 1)
        {
            inv.setItem(0, tete);
            inv.setItem(44, benevolat);
            slot = 1;
        }
        for (Metier metier : Metier.values())
        {
            if (metier.getPage() == page)
            {
                holder.metier.put(slot, metier);
                inv.setItem(slot++, new ItemBuilder(Material.PAPER).setName(ChatColor.GOLD + metier.getTitre()).toItemStack());
            }
        }
        p.openInventory(inv);
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, MetierHolder holder) {
        Metier metier = holder.metier.get(e.getSlot());
        int page = holder.getPage();

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
                if (metier == Metier.MEDECIN)
                {
                    InventoryManager.openInventory(player, InventoryType.MEDECIN);
                    return;
                }
                player.closeInventory();
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent set " + metier.name().toLowerCase(Locale.ROOT));
                Bukkit.dispatchCommand(player, "skin set " + metier.getUrl());
                player.sendMessage(ChatColor.GREEN + "Vous êtes désormais " + metier.getTitre() + " !");
                break;
            }
            case GREEN_WOOL:
            {
                InventoryManager.openInventory(player, InventoryType.BENEVOLAT);
                player.sendMessage(ChatColor.RED + "[Rappel] " + ChatColor.GREEN + "En faisant du bénévolat, vous n'êtes pas censés être payés. Par conséquent, votre salaire sera identique à celui d'un citoyen.");
                break;
            }
            case GREEN_DYE:
            {
                openInventory(player, page + 1);
                break;
            }
            case RED_DYE:
            {
                openInventory(player, page - 1 );
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
            openInventory(player, 1);
        }
    }

    public enum Metier
    {
        CHASSEUR("Chasseur", "http://novask.in/5448143538.png", 1),
        CUISINIER("Cuisinier", "http://novask.in/5921353192.png", 1),
        FACTEUR("Facteur", "http://novask.in/2887483160.png", 1),
        MEDECIN("Médecin", "http://novask.in/5290663328.png", 1),
        POLICIER("Policier", "http://novask.in/5911833608.png", 1),
        POMPIER("Pompier", "http://novask.in/5925383309.png", 1),
        UBEREATS("Coursier Uber Eats", "http://novask.in/5259253120.png", 1);
        private final String titre;
        private final String url;
        private final int page;

        Metier(String titre, String url, int page)
        {
            this.titre = titre;
            this.url = url;
            this.page = page;
        }

        public String getTitre() {
            return this.titre;
        }

        public String getUrl() {
            return this.url;
        }

        public int getPage()
        {
            return this.page;
        }
    }
}
