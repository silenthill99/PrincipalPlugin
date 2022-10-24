package fr.silenthill99.principalplugin.inventory.hook.modo;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.Main;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import fr.silenthill99.principalplugin.inventory.holder.modo.PlayerSanctionHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("deprecation")
public class PlayerSanctionInventory extends AbstractInventory<PlayerSanctionHolder> {

	public PlayerSanctionInventory() {
		super(PlayerSanctionHolder.class);
	}

	@Override
	public void openInventory(Player p, Object... args) {
		OfflinePlayer target = (OfflinePlayer) args[0];
		SanctionType type = (SanctionType) args[1];
		int page = (int) args[2];

		PlayerSanctionHolder holder = new PlayerSanctionHolder(target, type, page);

		ItemBuilder tete = new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(target.getName());
		ItemBuilder avertir = new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.GREEN + "Avertissements");
		ItemBuilder bannir_temporairement = new ItemBuilder(Material.ORANGE_WOOL)
				.setName(ChatColor.GOLD + "Bannir temporairement");
		ItemBuilder bannir = new ItemBuilder(Material.RED_WOOL).setName(ChatColor.DARK_RED + "Bannir");
		ItemBuilder freeze = new ItemBuilder(Material.LIGHT_BLUE_WOOL).setName(ChatColor.AQUA + "Freeze");
		ItemBuilder kick = new ItemBuilder(Material.MAGENTA_WOOL).setName(ChatColor.LIGHT_PURPLE + "Kick");
		ItemBuilder tempmute = new ItemBuilder(Material.PINK_WOOL).setName(ChatColor.LIGHT_PURPLE + "TempMute");
		ItemBuilder mute = new ItemBuilder(Material.PURPLE_WOOL).setName(ChatColor.DARK_PURPLE + "Mute");
		ItemBuilder page_suivante = new ItemBuilder(Material.GREEN_DYE).setName(ChatColor.GREEN + "Page suivante");
		ItemBuilder page_precedente = new ItemBuilder(Material.RED_DYE).setName(ChatColor.RED + "Page précédente");

		Inventory sanctions = createInventory(holder, 54,
				"Sanctionner " + target.getName());
		sanctions.setItem(4, tete.toItemStack());
		sanctions.setItem(8, RETOUR);
		sanctions.setItem(10, avertir.toItemStack());
		sanctions.setItem(11, bannir_temporairement.toItemStack());
		sanctions.setItem(12, bannir.toItemStack());
		sanctions.setItem(13, freeze.toItemStack());
		sanctions.setItem(14, kick.toItemStack());
		sanctions.setItem(15, tempmute.toItemStack());
		sanctions.setItem(16, mute.toItemStack());
		for (int slot = 18; slot <= 26; slot++) {
			sanctions.setItem(slot, new ItemStack(Material.WHITE_STAINED_GLASS_PANE));
		}
		if (!type.equals(SanctionType.MENU) && (type.equals(SanctionType.WARN) && page < 2
				|| type.equals(SanctionType.BAN_TEMP) && page != 1 || type.equals(SanctionType.BAN) && page != 1
				|| type.equals(SanctionType.FREEZE) && page != 1 || type.equals(SanctionType.KICK) && page != 1)) {
			sanctions.setItem(53, page_suivante.toItemStack());
		} else {
			sanctions.setItem(53, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
		}
		if (page > 1) {
			sanctions.setItem(45, page_precedente.toItemStack());
		} else if (page == 1) {
			sanctions.setItem(45, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
		}
		for (int slot = 46; slot <= 52; slot++) {
			if (slot != 49 || type.equals(SanctionType.MENU))
				sanctions.setItem(slot, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
			else
				sanctions.setItem(slot, new ItemBuilder(Material.PAPER).setName("Page " + page).toItemStack());
		}
		switch (type) {
		case WARN: {
			avertir.addGlowingEffect();
			sanctions.setItem(10, avertir.toItemStack());
			if (page == 1) {
				int slot = 27;
				for (WarnPage1 page1 : WarnPage1.values())
				{
					holder.warn_page_1.put(slot, page1);
					sanctions.setItem(slot++, new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.DARK_GREEN + page1.getName()).toItemStack());
				}
			}
			else if (page == 2) {
				int slot = 27;
				for (WarnPage2 page2 : WarnPage2.values())
				{
					holder.warn_page_2.put(slot, page2);
					sanctions.setItem(slot++, new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.DARK_GREEN + page2.getName()).toItemStack());
				}
			}
			break;
		}
		case BAN_TEMP: {
			bannir_temporairement.addGlowingEffect();
			sanctions.setItem(11, bannir_temporairement.toItemStack());
			if (page == 1) {
				int slot = 27;
				for (TempBanPage1 page1 : TempBanPage1.values())
				{
					holder.temp_ban_page_1.put(slot, page1);
					sanctions.setItem(27, new ItemBuilder(Material.ORANGE_WOOL).setName(ChatColor.GOLD + page1.getName()).setLore(page1.getLore()).toItemStack());
				}
			}
			break;
		}
		case BAN: {
			bannir.addGlowingEffect();
			sanctions.setItem(12, bannir.toItemStack());
			if (page == 1) {
				sanctions.setItem(27, new ItemBuilder(Material.RED_WOOL)
						.setName(ChatColor.DARK_RED + "Menace d'attaque DDoS").toItemStack());
			}
			break;
		}
		case FREEZE: {
			ItemBuilder freeze_on = new ItemBuilder(Material.LIGHT_BLUE_WOOL)
					.setName(ChatColor.AQUA + "Freeze le joueur");
			ItemBuilder freeze_off = new ItemBuilder(Material.LIGHT_BLUE_WOOL)
					.setName(ChatColor.AQUA + "UnFreeze le joueur");
			freeze.addEnchantment(Enchantment.DAMAGE_ALL, 5);
			sanctions.setItem(13, freeze.toItemStack());
			sanctions.setItem(37, freeze_on.toItemStack());
			sanctions.setItem(43, freeze_off.toItemStack());
			break;
		}
		case KICK: {
			kick.addGlowingEffect();
			sanctions.setItem(14, kick.toItemStack());
			break;
		}
		case MUTE_TEMP: {
			tempmute.addGlowingEffect();
			ItemBuilder insultes_hrp = new ItemBuilder(Material.PINK_WOOL)
					.setName(ChatColor.LIGHT_PURPLE + "Insultes HRP").setLore("Durée : 2 heures");

			sanctions.setItem(15, tempmute.toItemStack());
			sanctions.setItem(27, insultes_hrp.toItemStack());
			break;
		}
		case MUTE: {
			mute.addGlowingEffect();
			sanctions.setItem(16, mute.toItemStack());
			break;
		}
		default: {
			for (int slot = 27; slot <= 44; slot++) {
				sanctions.setItem(slot, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
			}
			break;
		}
		}
		p.openInventory(sanctions);
	}

	@Override
	public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, PlayerSanctionHolder holder) {
		OfflinePlayer target = holder.getTarget();
		WarnPage1 warn_1 = holder.warn_page_1.get(e.getSlot());
		WarnPage2 warn_2 = holder.warn_page_2.get(e.getSlot());
		TempBanPage1 temp_ban_1 = holder.temp_ban_page_1.get(e.getSlot());
		int page = holder.getPage();
		switch (current.getType()) {
		case GREEN_WOOL: {
			if (current.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Avertissements")) {
				openInventory(player, target, SanctionType.WARN, 1);
				return;
			}
			String name = ChatColor.stripColor(current.getItemMeta().getDisplayName());
			if (!name.isEmpty()) {
				player.closeInventory();
				if (name.equals("Insultes HRP")) {
					Bukkit.dispatchCommand(player,
							"warn " + target.getName() + " Insultes HRP (La prochaine fois c'est un mute de 2 heures)");
				} else {
                    switch (page)
                    {
                        case 1:
                        {
                            Bukkit.dispatchCommand(player, "warn " + target.getName() + " " + warn_1.getName());
                            break;
                        }
                        case 2:
                        {
                            Bukkit.dispatchCommand(player, "warn " + target.getName() + " " + warn_2.getName());
                            break;
                        }
                        default:
                            break;
                    }
				}
			}
			return;
		}
		case ORANGE_WOOL: {
			if (current.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Bannir temporairement")) {
				openInventory(player, target, SanctionType.BAN_TEMP, 1);
				return;
			}
			player.closeInventory();
            switch (page)
            {
                case 1:
                {
                    Bukkit.dispatchCommand(player, "tempipban " + target.getName() + temp_ban_1.getDuree() + " " + temp_ban_1.getSanction());
                    break;
                }
                default:
                {
                    break;
                }
            }

			break;
		}
		case RED_WOOL: {
			if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "Bannir")) {
				openInventory(player, target, SanctionType.BAN, 1);
				return;
			}
			if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "Menace d'attaque DDoS")) {
				player.closeInventory();
				if (!Main.isPlayerInGroup(player, "moderateur")) {
					Bukkit.dispatchCommand(player, "tempipban " + target.getName() + " 100y Menace d'attaque DDoS");
					return;
				}
				Bukkit.dispatchCommand(player, "ipban " + target.getName() + " Menace d'attaque DDoS");
				return;
			}
			break;
		}
		case LIGHT_BLUE_WOOL: {
			if (current.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Freeze")) {
				openInventory(player, target, SanctionType.FREEZE, 1);
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
				target.getPlayer().sendMessage(
						ChatColor.AQUA + "Vous avez été Freeze par " + ChatColor.YELLOW + target.getName());
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
				target.getPlayer().sendMessage(
						ChatColor.AQUA + "Vous avez été UnFreeze par " + ChatColor.YELLOW + player.getName());
				return;
			}
			break;
		}
		case MAGENTA_WOOL: {
			if (current.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Kick")) {
				openInventory(player, target, SanctionType.KICK, 1);
			}
			break;
		}
		case SUNFLOWER: {
			InventoryManager.openInventory(player, InventoryType.MODO_PLAYER_MENU, target);
			break;
		}
		case GREEN_DYE: {
			openInventory(player, target, holder.getName(), page + 1);
			break;
		}
		case RED_DYE: {
			openInventory(player, target, holder.getName(), page - 1);
			break;
		}
		case PINK_WOOL: {
			if (current.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "TempMute")) {
				openInventory(player, target, SanctionType.MUTE_TEMP, 1);
				return;
			}
			if (current.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Insultes HRP")) {
				player.closeInventory();
				Bukkit.dispatchCommand(player,
						"tempmute " + target.getName() + " 2h Insultes HRP (la prochaine fois un ban de 24h)");
				return;
			}
			break;
		}
		case PURPLE_WOOL: {
			if (current.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Mute")) {
				openInventory(player, target, SanctionType.MUTE, 1);
				return;
			}
			break;
		}
		default:
			break;
		}
	}

	public enum SanctionType {
		MENU, WARN, BAN, BAN_TEMP, FREEZE, KICK, MUTE, MUTE_TEMP
	}

	public enum WarnPage1 {
		NO_FEAR_RP("NoFearRP"),
		POWER_GAMING("PowerGaming / NoPainRP"),
		META_GAMING("MétaGaming"),
		NLR("NLR"),
		RTZ("RTZ"),
		SERIOUS_RP("Non respect du Serious RP"),
		BRAQUAGE_RUE("Braquage rue"),
		BRAQUAGE_SOLO("Braquage solo"),
		HRP_EN_RP("Hrp en RP"),
		NORP("NoRP"),
		CONDUITE("Conduite NoRP"),
		VEHICULE_BATIMENT("Véhicule bâtiment"),
		FREE_TAZE("FreeTaze"),
		FREE_JAIL("FreeJail"),
		FREE_PUNCH("FreePunch"),
		FREE_SHOT("FreeShot"),
		ARMES("Armes en métier légal"),
		OBJET_ILLEGAL("Objet Illégal en métier légal");

		private final String name;
		WarnPage1(String name)
		{
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
	}

	public enum WarnPage2
	{
		INSULTES_HRP("Insultes HRP");

		private final String name;

		WarnPage2(String name)
		{
			this.name = name;
		}
		public String getName() {
			return this.name;
		}
	}

	public enum TempBanPage1
	{
		HRP_EN_MUTE("/HRP en mute", "2h", "Utilisation du /hrp en mute", "Durée : 2 heures");

		private final String name;
		private final String duree;
		private final String sanction;
		private final String[] lore;
		TempBanPage1(String name, String duree, String sanction, String... lore)
		{
			this.name = name;
			this.duree = duree;
			this.sanction = sanction;
			this.lore = lore;
		}

		public String getName() {
			return this.name;
		}

		public String getDuree() {
			return this.duree;
		}

		public String[] getLore() {
			return this.lore;
		}

		public String getSanction() {
			return this.sanction;
		}
	}
}
