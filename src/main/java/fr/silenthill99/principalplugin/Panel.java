package fr.silenthill99.principalplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Panel
{
    private static Panel instance;

    public static Panel getInstance()
    {
        if (instance == null)
        {
            instance = new Panel();
        }
        return instance;
    }
    static ItemBuilder retour = new ItemBuilder(Material.SUNFLOWER).setName(ChatColor.YELLOW + "Retour");
    public ItemBuilder whitelist_off = new ItemBuilder(Material.GREEN_DYE).setName(ChatColor.GREEN + "Activer la whitelist");
    public ItemBuilder whitelist_on = new ItemBuilder(Material.RED_DYE).setName(ChatColor.RED + "Désactiver la whitelist");
    public HashMap<Player, String> titre = new HashMap<>();
    public HashMap<Player, Integer> page = new HashMap<>();
    public void panel_modo(Player player, OfflinePlayer target)
    {

        ItemBuilder tete = new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(target.getName());
        ItemBuilder sanctions = new ItemBuilder(Material.LIME_WOOL).setName(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Sanctionner " + target.getName());
        ItemBuilder administration = new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Administration");
        ItemBuilder options = new ItemBuilder(Material.CHEST).setName(ChatColor.YELLOW + "Options");

        Inventory menu = Bukkit.createInventory(new AdminOptionHolder(target), 27, "Menu de " + target.getName());
        menu.setItem(4, tete.toItemStack());
        menu.setItem(10, sanctions.toItemStack());
        menu.setItem(13, administration.toItemStack());
        menu.setItem(16, options.toItemStack());
        player.openInventory(menu);

    }
    public void panel_modo(Player player, OfflinePlayer target, String name, int page)
    {
        titre.put(player, name);
        this.page.put(player, page);
        ItemBuilder tete = new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(target.getName());
        ItemBuilder avertir = new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.GREEN + "Avertissements");
        ItemBuilder bannir_temporairement = new ItemBuilder(Material.ORANGE_WOOL).setName(ChatColor.GOLD + "Bannir temporairement");
        ItemBuilder bannir = new ItemBuilder(Material.RED_WOOL).setName(ChatColor.DARK_RED + "Bannir");
        ItemBuilder freeze = new ItemBuilder(Material.LIGHT_BLUE_WOOL).setName(ChatColor.AQUA + "Freeze");
        ItemBuilder kick = new ItemBuilder(Material.MAGENTA_WOOL).setName(ChatColor.LIGHT_PURPLE + "Kick");
        ItemBuilder tempmute = new ItemBuilder(Material.PINK_WOOL).setName(ChatColor.LIGHT_PURPLE + "TempMute");
        ItemBuilder mute = new ItemBuilder(Material.PURPLE_WOOL).setName(ChatColor.DARK_PURPLE + "Mute");
        ItemBuilder page_suivante = new ItemBuilder(Material.GREEN_DYE).setName(ChatColor.GREEN + "Page suivante");
        ItemBuilder page_precedente = new ItemBuilder(Material.RED_DYE).setName(ChatColor.RED + "Page précédente");
        ItemBuilder numero = new ItemBuilder(Material.PAPER).setName("Page " + page);

        Inventory sanctions = Bukkit.createInventory(new AdminOptionHolder(target), 54, "Sanctionner " + target.getName());
        sanctions.setItem(4, tete.toItemStack());
        sanctions.setItem(8, Panel.retour.toItemStack());
        sanctions.setItem(10, avertir.toItemStack());
        sanctions.setItem(11, bannir_temporairement.toItemStack());
        sanctions.setItem(12, bannir.toItemStack());
        sanctions.setItem(13, freeze.toItemStack());
        sanctions.setItem(14, kick.toItemStack());
        sanctions.setItem(15, tempmute.toItemStack());
        sanctions.setItem(16, mute.toItemStack());
        for (int slot = 18; slot <= 26; slot++)
        {
            sanctions.setItem(slot, new ItemStack(Material.WHITE_STAINED_GLASS_PANE));
        }
        if (!name.equalsIgnoreCase("menu") && (name.equalsIgnoreCase("Avertir") && page < 2 || name.equalsIgnoreCase("Bannir temporairement") && page != 1 || name.equalsIgnoreCase("Bannir") && page != 1 || name.equalsIgnoreCase("Freeze") && page != 1 || name.equalsIgnoreCase("Kick") && page != 1))
        {
            sanctions.setItem(53, page_suivante.toItemStack());
        }
        else
        {
            sanctions.setItem(53, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        }
        if (page > 1)
        {
            sanctions.setItem(45, page_precedente.toItemStack());
        }
        else if (page == 1)
        {
            sanctions.setItem(45, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        }
        for (int slot = 46; slot <= 52; slot++)
        {
            if (slot != 49 || name.equalsIgnoreCase("menu")) sanctions.setItem(slot, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
            else sanctions.setItem(slot, numero.toItemStack());
        }
        switch (name)
        {
            case "Avertir":
            {
                avertir.addEnchantment(Enchantment.DAMAGE_ALL, 5);
                sanctions.setItem(10, avertir.toItemStack());
                switch (page) {
                    case 1:
                    {
                        ItemBuilder no_fear_rp = new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.DARK_GREEN + "NoFearRP");
                        ItemBuilder no_pain_rp = new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.DARK_GREEN + "PowerGaming / NoPainRP");
                        ItemBuilder metagaming = new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.DARK_GREEN + "MétaGaming");
                        ItemBuilder nlr = new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.DARK_GREEN + "NLR");
                        ItemBuilder rtz = new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.DARK_GREEN + "RTZ");
                        ItemBuilder serious_rp = new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.DARK_GREEN + "Non respect du Serious RP");
                        ItemBuilder braquage_rue = new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.DARK_GREEN + "Braquage rue");
                        ItemBuilder braquage_solo = new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.DARK_GREEN + "Braquage solo");
                        ItemBuilder hrp_en_rp = new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.DARK_GREEN + "Hrp en RP");
                        ItemBuilder no_rp = new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.DARK_GREEN + "NoRP");
                        ItemBuilder conduite_no_rp = new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.DARK_GREEN + "Conduite NoRP");
                        ItemBuilder vehicule_batiment = new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.DARK_GREEN + "Véhicule bâtiment");
                        ItemBuilder freetaze = new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.DARK_GREEN + "FreeTaze");
                        ItemBuilder freejail = new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.DARK_GREEN + "FreeJail");
                        ItemBuilder free_punch = new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.DARK_GREEN + "FreePunch");
                        ItemBuilder free_shot = new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.DARK_GREEN + "FreeShot");
                        ItemBuilder armes_en_metier_legal = new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.DARK_GREEN + "Armes en métier légal");
                        ItemBuilder objet_illegal_en_metier_legal = new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.DARK_GREEN + "Objet Illégal en métier légal");

                        sanctions.setItem(27, no_fear_rp.toItemStack());
                        sanctions.setItem(28, no_pain_rp.toItemStack());
                        sanctions.setItem(29, metagaming.toItemStack());
                        sanctions.setItem(30, nlr.toItemStack());
                        sanctions.setItem(31, rtz.toItemStack());
                        sanctions.setItem(32, serious_rp.toItemStack());
                        sanctions.setItem(33, braquage_rue.toItemStack());
                        sanctions.setItem(34, braquage_solo.toItemStack());
                        sanctions.setItem(35, hrp_en_rp.toItemStack());
                        sanctions.setItem(36, no_rp.toItemStack());
                        sanctions.setItem(37, conduite_no_rp.toItemStack());
                        sanctions.setItem(38, vehicule_batiment.toItemStack());
                        sanctions.setItem(39, freetaze.toItemStack());
                        sanctions.setItem(40, freejail.toItemStack());
                        sanctions.setItem(41, free_punch.toItemStack());
                        sanctions.setItem(42, free_shot.toItemStack());
                        sanctions.setItem(43, armes_en_metier_legal.toItemStack());
                        sanctions.setItem(44, objet_illegal_en_metier_legal.toItemStack());
                        break;
                    }
                    case 2:
                    {
                        ItemBuilder insultes_hrp = new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.DARK_GREEN + "Insultes HRP");

                        sanctions.setItem(27, insultes_hrp.toItemStack());
                    }
                    default:
                        break;
                }
                break;
            }
            case "Bannir temporairement":
            {
                bannir_temporairement.addEnchantment(Enchantment.DAMAGE_ALL, 5);
                sanctions.setItem(11, bannir_temporairement.toItemStack());
                switch(page)
                {
                    case 1:
                        ItemBuilder hrp = new ItemBuilder(Material.ORANGE_WOOL).setName(ChatColor.GOLD + "/HRP en mute").setLore("Durée : 2 heures");
                        sanctions.setItem(27, hrp.toItemStack());
                    default:
                        break;
                }
                break;
            }
            case "Bannir":
            {
                bannir.addEnchantment(Enchantment.DAMAGE_ALL, 5);
                sanctions.setItem(12, bannir.toItemStack());
                switch (page)
                {
                    case 1:
                        ItemBuilder menace_ddos = new ItemBuilder(Material.RED_WOOL).setName(ChatColor.DARK_RED + "Menace d'attaque DDoS");
                        sanctions.setItem(27, menace_ddos.toItemStack());
                        break;
                    default:
                        break;
                }
                break;
            }
            case "Freeze":
            {
                ItemBuilder freeze_on = new ItemBuilder(Material.LIGHT_BLUE_WOOL).setName(ChatColor.AQUA + "Freeze le joueur");
                ItemBuilder freeze_off = new ItemBuilder(Material.LIGHT_BLUE_WOOL).setName(ChatColor.AQUA + "UnFreeze le joueur");
                freeze.addEnchantment(Enchantment.DAMAGE_ALL, 5);
                sanctions.setItem(13, freeze.toItemStack());
                sanctions.setItem(37, freeze_on.toItemStack());
                sanctions.setItem(43, freeze_off.toItemStack());
                break;
            }
            case "Kick":
            {
                kick.addEnchantment(Enchantment.DAMAGE_ALL, 5);
                sanctions.setItem(14, kick.toItemStack());
                break;
            }
            case "TempMute":
            {
                tempmute.addEnchantment(Enchantment.DAMAGE_ALL, 5);
                ItemBuilder insultes_hrp = new ItemBuilder(Material.PINK_WOOL).setName(ChatColor.LIGHT_PURPLE + "Insultes HRP").setLore("Durée : 2 heures");

                sanctions.setItem(15, tempmute.toItemStack());
                sanctions.setItem(27, insultes_hrp.toItemStack());
                break;
            }
            case "Mute":
            {
                mute.addEnchantment(Enchantment.DAMAGE_ALL, 5);
                sanctions.setItem(16, mute.toItemStack());
                break;
            }
            default:
            {
                for (int slot = 27; slot <= 44; slot++) {
                    sanctions.setItem(slot, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
                }
                break;
            }
        }
        player.openInventory(sanctions);
    }
    public void options_joueur(Player player, OfflinePlayer target)
    {
        ItemBuilder se_teleporter = new ItemBuilder(Material.ENDER_PEARL).setName(ChatColor.GREEN + "Se téléporter");
        ItemBuilder teleporter_le_joueur = new ItemBuilder(Material.ENDER_EYE).setName(ChatColor.GREEN  + "Téléporter le joueur ici");
        ItemBuilder logs = new ItemBuilder(Material.LAPIS_LAZULI).setName(ChatColor.BLUE + "Voir les logs");

        Inventory options = Bukkit.createInventory(new AdminOptionHolder(target), 9, "Options | " + target.getName());
        options.setItem(0, se_teleporter.toItemStack());
        options.setItem(1, teleporter_le_joueur.toItemStack());
        options.setItem(2, logs.toItemStack());
        options.setItem(8, Panel.retour.toItemStack());
        player.openInventory(options);
    }
    public void options_admin(Player player)
    {
        Inventory options = Bukkit.createInventory(null, 27, "Options Administrateur");
        if (Bukkit.getServer().hasWhitelist()) options.setItem(0, whitelist_on.toItemStack());
        else options.setItem(0, whitelist_off.toItemStack());
        player.openInventory(options);
    }
    public void pole_emploi(Player player, Boolean legal)
    {
        if (legal)
        {
            Inventory pole_emploi = Bukkit.createInventory(null, 54, "Choisissez un métier");
            player.openInventory(pole_emploi);
        }
    }
    public void panel_admin(Player player, OfflinePlayer target, String title)
    {
        switch (title)
        {
            case "Sanctions administratives":
            {
                ItemBuilder fraude = new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Fraude");
                ItemBuilder ddos = new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Attaque DDoS");
                ItemBuilder contournement = new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Contournement de sanctions / Double compte");

                Inventory sanctions_administration = Bukkit.createInventory(new AdminOptionHolder(target), 18, "Sanctions Administratives");
                sanctions_administration.setItem(0, fraude.toItemStack());
                sanctions_administration.setItem(1, ddos.toItemStack());
                sanctions_administration.setItem(2, contournement.toItemStack());
                sanctions_administration.setItem(9, Panel.retour.toItemStack());
                player.openInventory(sanctions_administration);
                break;
            }
            default:
            {
                ItemBuilder sanctions_admin = new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Sanctions administratives");
                ItemBuilder direction = new ItemBuilder(Material.CLOCK).setName(ChatColor.YELLOW + "Menu direction");

                Inventory admin = Bukkit.createInventory(new AdminOptionHolder(target), 27, "Administration");
                admin.setItem(0, sanctions_admin.toItemStack());
                admin.setItem(8, Panel.retour.toItemStack());
                admin.setItem(17, direction.toItemStack());
                player.openInventory(admin);
                break;
            }
        }
    }
    public void panel_direction(Player player, OfflinePlayer target, String title)
    {
        switch (title)
        {
            case "Menu":
            {
                ItemBuilder erreurs_staff = new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Erreurs staff");
                ItemBuilder rank_up = new ItemBuilder(Material.BOOK).setName(ChatColor.YELLOW + "RankUp " + target.getName());

                Inventory direction = Bukkit.createInventory(new AdminOptionHolder(target), 27, "Menu direction");
                direction.setItem(0, Panel.retour.toItemStack());
                direction.setItem(1, erreurs_staff.toItemStack());
                direction.setItem(2, rank_up.toItemStack());
                player.openInventory(direction);
                break;
            }
            case "Erreurs staff":
            {
                ItemBuilder fly_sans_vanish = new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Fly sans vanish");
                ItemBuilder god_en_warzone = new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "God en WZ").setLore("Uniquement si vanish désactivé !");
                ItemBuilder non_respect_reglement_staff = new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Non-respect | Règlement staff");
                ItemBuilder non_respect_dautrui = new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Non-respect d'autrui");
                ItemBuilder abus_de_pouvoir = new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Abus de pouvoir").setLore("Passible d'un dérank immédiat");
                ItemBuilder abus_de_permissions = new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Abus de permissions");
                ItemBuilder freeban = new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "FreeBan");
                ItemBuilder freewarn = new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "FreeWarn");
                ItemBuilder absence_non_justifiee = new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Absence non justifiée");
                ItemBuilder favoritisme = new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Favoritisme").setLore("A ne pas confondre avec l'attribution de circonstances atténuantes");
                ItemBuilder divulgations_dinfos_confidencielles = new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Divulgations d'infos confidencielles");
                ItemBuilder corruption = new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Corruption");
                ItemBuilder tete = new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(target.getName()).setName(target.getName());

                Inventory erreurs_staff = Bukkit.createInventory(new AdminOptionHolder(target), 27, "Erreurs staff");
                erreurs_staff.setItem(0, fly_sans_vanish.toItemStack());
                erreurs_staff.setItem(1, god_en_warzone.toItemStack());
                erreurs_staff.setItem(2, non_respect_reglement_staff.toItemStack());
                erreurs_staff.setItem(3, non_respect_dautrui.toItemStack());
                erreurs_staff.setItem(4, abus_de_pouvoir.toItemStack());
                erreurs_staff.setItem(5, abus_de_permissions.toItemStack());
                erreurs_staff.setItem(6, freeban.toItemStack());
                erreurs_staff.setItem(7, freewarn.toItemStack());
                erreurs_staff.setItem(8, absence_non_justifiee.toItemStack());
                erreurs_staff.setItem(9, favoritisme.toItemStack());
                erreurs_staff.setItem(10, divulgations_dinfos_confidencielles.toItemStack());
                erreurs_staff.setItem(11, corruption.toItemStack());
                erreurs_staff.setItem(18, Panel.retour.toItemStack());
                erreurs_staff.setItem(22, tete.toItemStack());
                player.openInventory(erreurs_staff);
                break;
            }
            case "RankUp":
            {
                ItemBuilder constructeur = new ItemBuilder(Material.ORANGE_WOOL).setName(ChatColor.GOLD + "[Constructeur]");
                ItemBuilder modo_stagiaire = new ItemBuilder(Material.LIME_WOOL).setName(ChatColor.GREEN + "[Modérateur Stagiaire]");
                ItemBuilder moderateur = new ItemBuilder(Material.LIME_WOOL).setName(ChatColor.GREEN + "[Modérateur]");
                ItemBuilder administrateur = new ItemBuilder(Material.LIGHT_BLUE_WOOL).setName(ChatColor.AQUA + "[Administrateur]");
                ItemBuilder developpeur = new ItemBuilder(Material.BLUE_WOOL).setName(ChatColor.DARK_BLUE + "[" + ChatColor.BLUE + "Développeur" + ChatColor.DARK_BLUE + "]");
                ItemBuilder direction = new ItemBuilder(Material.WRITABLE_BOOK).setName(ChatColor.YELLOW + "Grade direction");
                ItemBuilder mettre_op = new ItemBuilder(Material.RED_WOOL).setName(ChatColor.RED + "Mettre op");
                ItemBuilder unrank = new ItemBuilder(Material.PAPER).setName(ChatColor.RED + "UnRank " + target.getName());
                ItemBuilder deop = new ItemBuilder(Material.RED_WOOL).setName(ChatColor.RED + "DeOp " + target.getName());

                Inventory rankup = Bukkit.createInventory(new AdminOptionHolder(target), 27, "RankUp | " + target.getName());
                rankup.setItem(0, constructeur.toItemStack());
                rankup.setItem(1, modo_stagiaire.toItemStack());
                rankup.setItem(2, moderateur.toItemStack());
                rankup.setItem(3, administrateur.toItemStack());
                rankup.setItem(4, developpeur.toItemStack());
                rankup.setItem(8, direction.toItemStack());
                rankup.setItem(18, Panel.retour.toItemStack());
                rankup.setItem(24, mettre_op.toItemStack());
                rankup.setItem(25, unrank.toItemStack());
                rankup.setItem(26, deop.toItemStack());
                player.openInventory(rankup);
                break;
            }
            case "RankUp Direction":
            {
                ItemBuilder responsable = new ItemBuilder(Material.YELLOW_WOOL).setName(ChatColor.YELLOW + "[Resp. Equipe]");
                ItemBuilder co_fondateur = new ItemBuilder(Material.RED_WOOL).setName(ChatColor.DARK_RED + "[Co-Fondateur]");
                ItemBuilder fondateur = new ItemBuilder(Material.RED_WOOL).setName(ChatColor.DARK_RED + "[Fondateur]");

                Inventory direction = Bukkit.createInventory(new AdminOptionHolder(target), 9, "Grade direction ► " + target.getName());
                direction.setItem(0, responsable.toItemStack());
                direction.setItem(1, co_fondateur.toItemStack());
                direction.setItem(2, fondateur.toItemStack());
                direction.setItem(8, retour.toItemStack());
                player.openInventory(direction);
                break;
            }
            default:
                break;
        }
    }
    public void distributeur(Player player)
    {
        player.sendMessage(ChatColor.RED + "Attention ! Système en bêta");
        ItemBuilder deposer = new ItemBuilder(Material.GOLD_INGOT).setName(ChatColor.GREEN + "Déposer de l'argent");
        ItemBuilder retirer = new ItemBuilder(Material.GOLD_INGOT).setName(ChatColor.GREEN + "Retirer de l'argent");
        Inventory distributeur = Bukkit.createInventory(null, 27, "Distributeur");
        distributeur.setItem(10, deposer.toItemStack());
        distributeur.setItem(16, retirer.toItemStack());
        player.openInventory(distributeur);
    }
}