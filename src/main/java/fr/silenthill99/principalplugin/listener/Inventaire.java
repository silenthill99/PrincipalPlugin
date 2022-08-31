package fr.silenthill99.principalplugin.listener;

import fr.silenthill99.principalplugin.*;
import javafx.scene.layout.Pane;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Inventaire implements Listener
{
    @EventHandler
    public void onClick(InventoryClickEvent event)
    {
        Inventory inv = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        ItemStack current = event.getCurrentItem();
        event.setCancelled(Main.getInstance().isFreeze(player));
        if (event.getView().getTitle().equals("Choisissez un joueur"))
        {
            event.setCancelled(true);
            switch (current.getType())
            {
                case PLAYER_HEAD:
                    Player target = Bukkit.getPlayer(current.getItemMeta().getDisplayName());
                    Panel.getInstance().panel_modo(player, target);
                    break;
                default:
                    break;
            }
        }
        if (event.getView().getTitle().startsWith("Menu de "))
        {
            event.setCancelled(true);
            AdminOptionHolder holder = (AdminOptionHolder) inv.getHolder();
            OfflinePlayer target = holder.getPlayer();
            switch (current.getType())
            {
                case LIME_WOOL:
                {
                    Panel.getInstance().panel_modo(player, target, "menu", 1);
                    break;
                }
                case CHEST:
                {
                    Panel.getInstance().options_joueur(player, target);
                    break;
                }
                case REDSTONE:
                {
                    if (!Main.isPlayerInGroup(player, "administrateur"))
                    {
                        player.sendMessage(ChatColor.DARK_RED + "Vous n'avez pas accès à cette catégorie");
                        return;
                    }
                    Panel.getInstance().panel_admin(player, target, "Menu");
                }
                default:
                    break;
            }
        }
        if (event.getView().getTitle().startsWith("Sanctionner "))
        {
            event.setCancelled(true);
            AdminOptionHolder holder = (AdminOptionHolder) inv.getHolder();
            OfflinePlayer target = holder.getPlayer();
            int page2;
            switch (current.getType())
            {
                case GREEN_WOOL:
                {
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Avertissements"))
                    {
                        if (!Panel.getInstance().titre.get(player).equalsIgnoreCase("Avertir"))
                        {
                            Panel.getInstance().panel_modo(player, target, "Avertir", 1);
                        }
                        return;
                    }
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_GREEN + "NoFearRP"))
                    {
                        player.closeInventory();
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " NoFearRP");
                        return;
                    }
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_GREEN + "PowerGaming / NoPainRP"))
                    {
                        player.closeInventory();
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " PowerGaming / NoPainRP");
                        return;
                    }
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_GREEN + "MétaGaming"))
                    {
                        player.closeInventory();
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " MétaGaming");
                        return;
                    }
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_GREEN + "NLR"))
                    {
                        player.closeInventory();
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " NLR");
                        return;
                    }
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_GREEN + "RTZ"))
                    {
                        player.closeInventory();
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " RTZ");
                        return;
                    }
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_GREEN + "Non respect du Serious RP"))
                    {
                        player.closeInventory();
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " Non respect du Serious RP");
                        return;
                    }
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_GREEN + "Braquage rue"))
                    {
                        player.closeInventory();
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " Braquage rue");
                        return;
                    }
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_GREEN + "Braquage solo"))
                    {
                        player.closeInventory();
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " Braquage solo");
                        return;
                    }
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_GREEN + "Hrp en RP"))
                    {
                        player.closeInventory();
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " Hrp en RP");
                        return;
                    }
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_GREEN + "NoRP"))
                    {
                        player.closeInventory();
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " NoRP");
                        return;
                    }
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_GREEN + "Conduite NoRP"))
                    {
                        player.closeInventory();
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " Conduite NoRP");
                        return;
                    }
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_GREEN + "Véhicule bâtiment"))
                    {
                        player.closeInventory();
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " Véhicule bâtiment");
                        return;
                    }
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_GREEN + "FreeTaze"))
                    {
                        player.closeInventory();
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + "FreeTaze");
                        return;
                    }
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_GREEN + "FreeJail"))
                    {
                        player.closeInventory();
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " FreeJail");
                        return;
                    }
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_GREEN + "FreePunch"))
                    {
                        player.closeInventory();
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " FreePunch");
                        return;
                    }
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_GREEN + "FreeShot"))
                    {
                        player.closeInventory();
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " FreeShot");
                        return;
                    }
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_GREEN + "Armes en métier légal"))
                    {
                        player.closeInventory();
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " Armes en métier légal");
                        return;
                    }
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_GREEN + "Insultes HRP"))
                    {
                        player.closeInventory();
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " Insultes HRP (La prochaine fois c'est un mute de 2 heures)");
                        return;
                    }
                    break;
                }
                case ORANGE_WOOL:
                {
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Bannir temporairement")) {
                        if (!Panel.getInstance().titre.get(player).equalsIgnoreCase("Bannir temporairement")) {
                            Panel.getInstance().panel_modo(player, target, "Bannir temporairement", 1);
                        }
                        return;
                    }
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "/HRP en mute"))
                    {
                        player.closeInventory();
                        Bukkit.dispatchCommand(player, "tempipban " + target.getName() + " 2h Utilisation du /hrp en mute");
                        return;
                    }
                    break;
                }
                case RED_WOOL:
                {
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "Bannir"))
                    {
                        if (!Panel.getInstance().titre.get(player).equalsIgnoreCase("Bannir")) {
                            Panel.getInstance().panel_modo(player, target, "Bannir", 1);
                        }
                        return;
                    }
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "Menace d'attaque DDoS"))
                    {
                        player.closeInventory();
                        if (!Main.isPlayerInGroup(player, "moderateur"))
                        {
                            Bukkit.dispatchCommand(player, "tempipban " + target.getName() + " 100y Menace d'attaque DDoS");
                            return;
                        }
                        Bukkit.dispatchCommand(player, "ipban " + target.getName() + " Menace d'attaque DDoS");
                        return;
                    }
                    break;
                }
                case LIGHT_BLUE_WOOL:
                {
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Freeze")) {
                        if (!Panel.getInstance().titre.get(player).equalsIgnoreCase("Freeze")) {
                            Panel.getInstance().panel_modo(player, target, "Freeze", 1);
                        }
                        return;
                    }
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Freeze le joueur")) {
                        if (!target.isOnline()) {
                            player.sendMessage(ChatColor.RED + "Vous ne pouvez pas freeze des joueurs hors ligne !");
                            return;
                        }
                        if (Main.getInstance().isFreeze(target.getPlayer())) {
                            player.sendMessage(ChatColor.RED + "Ce joueur est déjà Freeze !");
                            return;
                        }
                        player.closeInventory();
                        Main.getInstance().getFrozenPlayers().put(target.getUniqueId(), target.getPlayer().getLocation());
                        player.sendMessage(ChatColor.AQUA + "Vous avez Freeze " + ChatColor.YELLOW + target.getName());
                        target.getPlayer().sendMessage(ChatColor.AQUA + "Vous avez été Freeze par " + ChatColor.YELLOW + target.getName());
                    }
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "UnFreeze le joueur")) {
                        if (!target.isOnline()) {
                            player.sendMessage(ChatColor.RED + "Ce joueur n'est pas connecté ou n'existe pas !");
                            return;
                        }
                        if (!Main.getInstance().isFreeze(target.getPlayer())) {
                            player.sendMessage(ChatColor.RED + "Ce joueur est déjà UnFreeze !");
                            return;
                        }
                        player.closeInventory();
                        Main.getInstance().getFrozenPlayers().remove(target.getUniqueId());
                        player.sendMessage(ChatColor.AQUA + "Vous avez UnFreeze " + ChatColor.YELLOW + target.getName());
                        target.getPlayer().sendMessage(ChatColor.AQUA + "Vous avez été UnFreeze par " + ChatColor.YELLOW + player.getName());
                        return;
                    }
                    break;
                }
                case MAGENTA_WOOL:
                {
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Kick")) {
                        if (!Panel.getInstance().titre.get(player).equalsIgnoreCase("Kick")) {
                            Panel.getInstance().panel_modo(player, target, "Kick", 1);
                        }
                    }
                    break;
                }
                case SUNFLOWER:
                {
                    Panel.getInstance().panel_modo(player, target);
                    break;
                }
                case GREEN_DYE:
                {
                    page2 = Panel.getInstance().page.get(player) + 1;
                    Panel.getInstance().panel_modo(player, target, Panel.getInstance().titre.get(player), page2);
                    break;
                }
                case RED_DYE:
                {
                    page2 = Panel.getInstance().page.get(player) - 1;
                    Panel.getInstance().panel_modo(player, target, Panel.getInstance().titre.get(player), page2);
                    break;
                }
                case PINK_WOOL:
                {
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "TempMute"))
                    {
                        if (!Panel.getInstance().titre.get(player).equalsIgnoreCase("TempMute"))
                        {
                            Panel.getInstance().panel_modo(player, target, "TempMute", 1);
                        }
                        return;
                    }
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Insultes HRP"))
                    {
                        player.closeInventory();
                        Bukkit.dispatchCommand(player, "tempmute " + target.getName() + " 2h Insultes HRP (la prochaine fois un ban de 24h)");
                        return;
                    }
                    break;
                }
                case PURPLE_WOOL:
                {
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Mute"))
                    {
                        if (!Panel.getInstance().titre.get(player).equalsIgnoreCase("Mute"))
                        {
                            Panel.getInstance().panel_modo(player, target, "Mute", 1);
                        }
                        return;
                    }
                    break;
                }
                default:
                    break;
            }
        }
        if (event.getView().getTitle().startsWith("Options | "))
        {
            event.setCancelled(true);
            AdminOptionHolder holder = (AdminOptionHolder) inv.getHolder();
            OfflinePlayer target = holder.getPlayer();
            switch (current.getType())
            {
                case ENDER_PEARL:
                {
                    if (!target.isOnline()) {
                        player.sendMessage(ChatColor.RED + "Ce joueur n'est pas connecté ou n'existe pas !");
                        return;
                    }
                    player.teleport(target.getPlayer().getLocation());
                    break;
                }
                case ENDER_EYE:
                {
                    if (!target.isOnline())
                    {
                        player.sendMessage(ChatColor.RED + "Ce joueur n'est pas connecté ou n'existe pas !");
                        return;
                    }
                    target.getPlayer().teleport(player.getLocation());
                    break;
                }
                case LAPIS_LAZULI:
                {
                    player.closeInventory();
                    Bukkit.dispatchCommand(player, "logs " + target.getName());
                    break;
                }
                case SUNFLOWER:
                {
                    Panel.getInstance().panel_modo(player, target);
                    break;
                }
                default:
                {
                    break;
                }
            }
        }
        if (event.getView().getTitle().equals("Options Administrateur"))
        {
            event.setCancelled(true);
            switch (current.getType())
            {
                case GREEN_DYE:
                {
                    player.getOpenInventory().setItem(0, Panel.getInstance().whitelist_on.toItemStack());
                    Bukkit.getServer().setWhitelist(true);
                    Bukkit.broadcastMessage(ChatColor.GREEN + "La whitelist a été activée");
                    break;
                }
                case RED_DYE:
                    player.getOpenInventory().setItem(0, Panel.getInstance().whitelist_off.toItemStack());
                    Bukkit.getServer().setWhitelist(false);
                    Bukkit.broadcastMessage(ChatColor.GREEN + "La whitelist a été désactivée");
                    break;
                default:
                    break;
            }
            return;
        }
        if (event.getView().getTitle().equals("Administration"))
        {
            event.setCancelled(true);
            AdminOptionHolder holder = (AdminOptionHolder) inv.getHolder();
            OfflinePlayer target = holder.getPlayer();
            switch (current.getType())
            {
                case SUNFLOWER:
                {
                    Panel.getInstance().panel_modo(player, target);
                    break;
                }
                case REDSTONE:
                {
                    Panel.getInstance().panel_admin(player, target, "Sanctions administratives");
                    break;
                }
                case CLOCK:
                {
                    if (!player.hasPermission("oxydia.direction"))
                    {
                        player.sendMessage(ChatColor.DARK_RED + "Vous n'avez pas accès à ce panel !");
                        return;
                    }
                    Panel.getInstance().panel_direction(player, target, "Menu");
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }
        if (event.getView().getTitle().equals("Sanctions Administratives"))
        {
            AdminOptionHolder holder = (AdminOptionHolder) inv.getHolder();
            OfflinePlayer target = holder.getPlayer();
            switch (current.getType())
            {
                case REDSTONE:
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "Fraude"))
                    {
                        player.closeInventory();
                        Bukkit.dispatchCommand(player, "ipban " + target.getName() + " Fraude");
                        return;
                    }
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "Attaque DDoS"))
                    {
                        player.closeInventory();
                        Bukkit.dispatchCommand(player, "ipban " + target.getName() + " Attaque DDoS");
                        return;
                    }
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "Contournement de sanctions / Double compte"))
                    {
                        player.closeInventory();
                        Bukkit.dispatchCommand(player, "ipban " + target.getName() + " Contournement de sanctions / Double compte");
                        return;
                    }
                    break;
                case SUNFLOWER:
                    Panel.getInstance().panel_admin(player, target, "Menu");
                    break;
                default:
                    break;
            }
            return;
        }
        if (event.getView().getTitle().equals("Menu direction"))
        {
            AdminOptionHolder holder = (AdminOptionHolder) inv.getHolder();
            OfflinePlayer target = holder.getPlayer();
            event.setCancelled(true);
            switch (current.getType())
            {
                case SUNFLOWER:
                {
                    Panel.getInstance().panel_admin(player, target, "Menu");
                    break;
                }
                case REDSTONE:
                    Panel.getInstance().panel_direction(player, target, "Erreurs staff");
                    break;
                case BOOK:
                {
                    Panel.getInstance().panel_direction(player, target, "RankUp");
                    break;
                }
                default:
                {
                    break;
                }
            }
        }
        if (event.getView().getTitle().equals("Erreurs staff"))
        {
            event.setCancelled(true);
            AdminOptionHolder holder = (AdminOptionHolder) inv.getHolder();
            OfflinePlayer target = holder.getPlayer();
            switch (current.getType())
            {
                case SUNFLOWER:
                {
                    Panel.getInstance().panel_direction(player, target, "Menu");
                    break;
                }
                case REDSTONE:
                {
                    player.closeInventory();
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "Fly sans vanish"))
                    {
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " Erreur staff : fly sans vanish");
                    }
                    else if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "God en WZ"))
                    {
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " Erreur staff : god en wz");
                    }
                    else if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "Non-respect | Règlement staff"))
                    {
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " Erreur staff : Non-respect | Règlement staff");
                    }
                    else if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "Non-respect d'autrui"))
                    {
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " Erreur staff : Non-respect d'autrui");
                    }
                    else if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "Abus de pouvoir"))
                    {
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " Erreur staff : Abus de pouvoir");
                    }
                    else if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "Abus de permissions"))
                    {
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " Erreur staff : Abus de permissions");
                    }
                    else if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "FreeBan"))
                    {
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " Erreur staff : FreeBan");
                    }
                    else if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "FreeWarn"))
                    {
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " Erreur staff : FreeWarn");
                    }
                    else if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "Absence non justifiée"))
                    {
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " Erreur staff : Absence non justifiée");
                    }
                    else if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "Favoritisme"))
                    {
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " Erreur staff : Favoritisme");
                    }
                    else if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "Divulgations d'infos confidencielles"))
                    {
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " Erreur staff : divulgation d'infos confidencielles");
                    }
                    else if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "Corruption"))
                    {
                        Bukkit.dispatchCommand(player, "warn " + target.getName() + " Erreur staff : Corruption");
                    }
                    break;
                }
                default:
                    break;
            }
            return;
        }
        if (event.getView().getTitle().equals("Distributeur"))
        {
            event.setCancelled(true);
            switch (current.getType())
            {
                case GOLD_INGOT:
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Déposer de l'argent"))
                    {
                        if (player.getInventory().contains(ItemBuilder.getArgent()))
                        {
                            player.getInventory().removeItem(ItemBuilder.getArgent());
                            Main.getInstance().economy.depositPlayer(player, 100);
                            player.sendMessage(ChatColor.GREEN + "Vous avez déposé 100€ sur votre compte !");
                        }
                        else
                        {
                            player.sendMessage(ChatColor.RED + "Vous n'avez pas d'argent sur vous !");
                        }
                    }
                    else if (current.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Retirer de l'argent"))
                    {
                        if (Main.getInstance().economy.has(player, 100))
                        {
                            player.getInventory().addItem(ItemBuilder.getArgent());
                            Main.getInstance().economy.withdrawPlayer(player, 100);
                            player.sendMessage(ChatColor.GREEN + "Vous avez retiré 100€ !");
                        }
                        else
                        {
                            player.sendMessage(ChatColor.RED + "Vous navez pas assez d'argent !");
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        if (event.getView().getTitle().startsWith("RankUp | "))
        {
            event.setCancelled(true);
            AdminOptionHolder holder = (AdminOptionHolder) inv.getHolder();
            OfflinePlayer target = holder.getPlayer();
            switch (current.getType())
            {
                case SUNFLOWER:
                {
                    Panel.getInstance().panel_direction(player, target, "Menu");
                    break;
                }
                case RED_WOOL:
                {
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.RED + "Mettre op"))
                    {
                        if (!target.isOp())
                        {
                            player.closeInventory();
                            target.setOp(true);
                            player.sendMessage(ChatColor.GREEN + target.getName() + " est désormais opérateur du serveur !");
                            if (target.isOnline())
                            {
                                target.getPlayer().sendMessage(ChatColor.GREEN + "Vous êtes désormais opérateur du serveur !");
                            }
                        }
                        else
                        {
                            player.sendMessage(ChatColor.RED + target.getName() + " est déjà opérateur !");
                        }
                    }
                    else if (current.getItemMeta().getDisplayName().equals(ChatColor.RED + "DeOp " + target.getName()))
                    {
                        if (target.isOp())
                        {
                            target.setOp(false);
                            player.sendMessage(ChatColor.DARK_RED + target.getName() + " a perdu ses privilèges d'opérateur !");
                            if (target.isOnline())
                            {
                                target.getPlayer().sendMessage(ChatColor.DARK_RED + "Vous avez perdu vos privilèges d'opérateur !");
                            }
                        }
                        else
                        {
                            player.sendMessage(ChatColor.RED + target.getName() + " n'est pas op !");
                        }
                    }
                    break;
                }
                case PAPER:
                {
                    player.closeInventory();
                    Bukkit.dispatchCommand(player, "lp user " + target.getName() + " parent set default");
                    if (target.isOnline())
                    {
                        Bukkit.dispatchCommand(player, "kick " + target.getName() + " UnRank");
                    }
                    Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission clear");
                    break;
                }
                case ORANGE_WOOL:
                {
                    player.closeInventory();
                    Bukkit.dispatchCommand(player, "lp user " + target.getName() + " parent set builder");
                    Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission clear");
                    Bukkit.dispatchCommand(player, "list");
                    Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission set oxydia.builder");
                    player.sendMessage(ChatColor.GREEN + target.getName() + " est désormais builder !");
                    if (target.isOnline()) {
                        target.getPlayer().sendMessage(ChatColor.GREEN + "Vous êtes désormais builder !");
                    }
                    break;
                }
                case LIME_WOOL:
                {
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "[Modérateur Stagiaire]"))
                    {
                        player.closeInventory();
                        Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission clear");
                        Bukkit.dispatchCommand(player, "lp user " + target.getName() + " parent set modo-stagiaire");
                        Bukkit.dispatchCommand(player, "list");
                        Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission set oxydia.stagiaire");
                        player.sendMessage(ChatColor.GREEN + target.getName() + " est désormais modérateur stagiaire !");
                        if (target.isOnline())
                        {
                            target.getPlayer().sendMessage(ChatColor.GREEN  + "Vous êtes désormais modérateur stagiaire !");
                        }
                    }
                    else if (current.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "[Modérateur]"))
                    {
                        player.closeInventory();
                        Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission clear");
                        Bukkit.dispatchCommand(player, "lp user " + target.getName() + " parent set moderateur");
                        Bukkit.dispatchCommand(player, "list");
                        Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission set oxydia.modo");
                        player.sendMessage(ChatColor.GREEN + target.getName() + " est désormais modérateur !");
                        if (target.isOnline())
                        {
                            target.getPlayer().sendMessage(ChatColor.GREEN + "Vousêtes désormais modérateur !");
                        }
                    }
                    break;
                }
                case LIGHT_BLUE_WOOL:
                {
                    player.closeInventory();
                    Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission clear");
                    Bukkit.dispatchCommand(player, "lp user " + target.getName() + " parent set administrateur");
                    Bukkit.dispatchCommand(player, "list");
                    Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission set oxydia.admin");
                    player.sendMessage(ChatColor.GREEN + target.getName() + " est désormais administrateur !");
                    if (target.isOnline())
                    {
                        target.getPlayer().sendMessage(ChatColor.GREEN + "Vous êtes désormais administrateur !");
                    }
                    break;
                }
                case BLUE_WOOL:
                {
                    player.closeInventory();
                    Bukkit.dispatchCommand(player, "lp user " + target.getName() + " parent set developpeur");
                    Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission clear");
                    Bukkit.dispatchCommand(player, "list");
                    Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission set oxydia.developpeur");
                    player.sendMessage(ChatColor.GREEN + target.getName() + " est désormais développeur !");
                    if (target.isOnline())
                    {
                        target.getPlayer().sendMessage(ChatColor.GREEN + "Vous êtes désormais développeur !");
                    }
                    break;
                }
                case WRITABLE_BOOK:
                {
                    Panel.getInstance().panel_direction(player, target, "RankUp Direction");
                    break;
                }
                default:
                    break;
            }
        }
        if (event.getView().getTitle().startsWith("Grade direction ► "))
        {
            AdminOptionHolder holder = (AdminOptionHolder) inv.getHolder();
            OfflinePlayer target = holder.getPlayer();
            event.setCancelled(true);
            switch (current.getType())
            {
                case YELLOW_WOOL:
                {
                    player.closeInventory();
                    Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission clear");
                    Bukkit.dispatchCommand(player, "lp user " + target.getName() + " parent set responsable");
                    Bukkit.dispatchCommand(player, "list");
                    Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission set oxydia.responsable");
                    player.sendMessage(ChatColor.GREEN + target.getName() + " est désormais responsable d'équipe !");
                    if (target.isOnline()) {
                        target.getPlayer().sendMessage(ChatColor.GREEN + "Vous êtes désormais responsable d'équipe !");
                    }
                    break;
                }
                case SUNFLOWER:
                {
                    Panel.getInstance().panel_direction(player, target, "RankUp");
                    break;
                }
                case RED_WOOL:
                {
                    player.closeInventory();
                    Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission clear");
                    if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "[Co-Fondateur]"))
                    {
                        Bukkit.dispatchCommand(player, "lp user " + target.getName() + " parent set co-fondateur");
                        Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission set oxydia.cofonfateur");
                        player.sendMessage(ChatColor.GREEN + target.getName() + " est désormais co-fondateur !");
                        if (target.isOnline())
                        {
                            target.getPlayer().sendMessage(ChatColor.GREEN + "Vous êtes désormais co-fondateur !");
                        }
                    }
                    else if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "[Fondateur]"))
                    {
                        Bukkit.dispatchCommand(player, "lp user " + target.getName() + " parent set fondateur");
                        Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission set oxydia.fondateur");
                        player.sendMessage(ChatColor.GREEN + target.getName() + " est désormais fondateur !");
                        if (target.isOnline())
                        {
                            target.getPlayer().sendMessage(ChatColor.GREEN + "Vous êtes désormais fondateur !");
                        }
                    }
                    Bukkit.dispatchCommand(player, "list");
                    break;
                }
                default:
                    break;
            }
        }
    }
}