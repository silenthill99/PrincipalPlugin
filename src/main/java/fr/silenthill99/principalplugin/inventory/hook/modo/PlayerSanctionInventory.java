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
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerSanctionInventory extends AbstractInventory<PlayerSanctionHolder> {

	public PlayerSanctionInventory() {
		super(PlayerSanctionHolder.class);
	}

	ItemBuilder avertir = new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.GREEN + "Avertissements");
	ItemBuilder bannir_temporairement = new ItemBuilder(Material.ORANGE_WOOL).setName(ChatColor.GOLD + "Bannir temporairement");
	ItemBuilder bannir = new ItemBuilder(Material.RED_WOOL).setName(ChatColor.DARK_RED + "Bannir");
	ItemBuilder freeze = new ItemBuilder(Material.LIGHT_BLUE_WOOL).setName(ChatColor.AQUA + "Freeze");
	ItemBuilder kick = new ItemBuilder(Material.MAGENTA_WOOL).setName(ChatColor.LIGHT_PURPLE + "Kick");
	ItemBuilder tempmute = new ItemBuilder(Material.PINK_WOOL).setName(ChatColor.LIGHT_PURPLE + "TempMute");
	ItemBuilder mute = new ItemBuilder(Material.PURPLE_WOOL).setName(ChatColor.DARK_PURPLE + "Mute");

	@Override
	public void openInventory(Player p, Object... args) {
		OfflinePlayer target = (OfflinePlayer) args[0];
		SanctionType type = (SanctionType) args[1];
		int page = (int) args[2];

		PlayerSanctionHolder holder = new PlayerSanctionHolder(target, type, page);

		ItemStack tete = new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(target.getName()).toItemStack();
		ItemStack page_suivante = new ItemBuilder(Material.GREEN_DYE).setName(ChatColor.GREEN + "Page suivante").toItemStack();
		ItemStack page_precedente = new ItemBuilder(Material.RED_DYE).setName(ChatColor.RED + "Page précédente").toItemStack();

		Inventory inv = createInventory(holder, 54, "Sanctionner " + target.getName());
		inv.setItem(4, tete);
		inv.setItem(8, RETOUR);
		inv.setItem(10, avertir.toItemStack());
		inv.setItem(11, bannir_temporairement.toItemStack());
		inv.setItem(12, bannir.toItemStack());
		inv.setItem(13, freeze.toItemStack());
		inv.setItem(14, kick.toItemStack());
		inv.setItem(15, tempmute.toItemStack());
		inv.setItem(16, mute.toItemStack());
		for (int slot = 18; slot <= 26; slot++) {
			inv.setItem(slot, new ItemStack(Material.WHITE_STAINED_GLASS_PANE));
		}
		if (!type.equals(SanctionType.MENU) && (type.equals(SanctionType.WARN) && page < 2 || type.equals(SanctionType.BAN_TEMP) && page != 1 || type.equals(SanctionType.BAN) && page != 1 || type.equals(SanctionType.FREEZE) && page != 1 || type.equals(SanctionType.KICK) && page != 1)) {
			inv.setItem(53, page_suivante);
		} else {
			inv.setItem(53, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
		}
		if (page > 1) {
			inv.setItem(45, page_precedente);
		} else if (page == 1) {
			inv.setItem(45, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
		}
		for (int slot = 46; slot <= 52; slot++) {
			if (slot != 49 || type.equals(SanctionType.MENU))
				inv.setItem(slot, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
			else
				inv.setItem(slot, new ItemBuilder(Material.PAPER).setName("Page " + page).toItemStack());
		}

		switch (type)
		{
			case WARN:
			{
				avertir.addGlowingEffect();
				inv.setItem(10, avertir.toItemStack());
				int slot = 27;
				for (Warns warns : Warns.values())
				{
					if (page == warns.getPage())
					{
						holder.warns.put(slot, warns);
						inv.setItem(slot++, new ItemBuilder(Material.GREEN_WOOL).setName(ChatColor.DARK_GREEN + warns.getName()).toItemStack());
					}
				}
				break;
			}
			case BAN_TEMP:
			{
				bannir_temporairement.addGlowingEffect();
				inv.setItem(11, bannir_temporairement.toItemStack());
				int slot = 27;
				for (TempBan tempBan : TempBan.values())
				{
					if (page == tempBan.getPage())
					{
						holder.temp_ban.put(slot, tempBan);
						inv.setItem(slot++, new ItemBuilder(Material.ORANGE_WOOL).setName(ChatColor.GOLD + tempBan.getName()).setLore(tempBan.getLore()).toItemStack());
					}
				}
				break;
			}
			case BAN:
			{
				bannir.addGlowingEffect();
				inv.setItem(12, bannir.toItemStack());
				int slot = 27;
				for (Ban bans : Ban.values())
				{
					if (page == bans.getPage())
					{
						holder.ban.put(slot, bans);
						inv.setItem(slot++, new ItemBuilder(Material.RED_WOOL).setName(ChatColor.DARK_RED + bans.getTitre()).toItemStack());
					}
				}
				break;
			}
			case FREEZE:
			{
				freeze.addGlowingEffect();
				inv.setItem(13, freeze.toItemStack());
				for (Freeze freezed : Freeze.values())
				{
					holder.freeze.put(freezed.getSlot(), freezed);
					inv.setItem(freezed.getSlot(), new ItemBuilder(Material.LIGHT_BLUE_WOOL).setName(ChatColor.AQUA + freezed.getName()).toItemStack());
				}
				break;
			}
			case KICK:
			{
				kick.addGlowingEffect();
				inv.setItem(14, kick.toItemStack());
				int slot = 27;
				for (Kick kicks : Kick.values())
				{
					if (page == kicks.getPage())
					{
						holder.kick.put(slot, kicks);
						inv.setItem(slot++, new ItemBuilder(Material.MAGENTA_WOOL).setName(ChatColor.LIGHT_PURPLE + kicks.getTitre()).toItemStack());
					}
				}
				break;
			}
			case MUTE_TEMP:
			{
				tempmute.addGlowingEffect();
				inv.setItem(15, tempmute.toItemStack());
				int slot = 27;
				for (MuteTemp muteTemp : MuteTemp.values())
				{
					if (page == muteTemp.getPage())
					{
						holder.muteTemp.put(slot, muteTemp);
						inv.setItem(slot++, new ItemBuilder(Material.PINK_WOOL).setName(ChatColor.LIGHT_PURPLE + muteTemp.getName()).setLore(muteTemp.getLore()).toItemStack());
					}
				}
				break;
			}
			case MUTE:
			{
				mute.addGlowingEffect();
				inv.setItem(16, mute.toItemStack());
				int slot = 27;
				for (Mute mute : Mute.values())
				{
					if (page == mute.getPage())
					{
						holder.mute.put(slot, mute);
						inv.setItem(slot++, new ItemBuilder(Material.PURPLE_WOOL).setName(ChatColor.DARK_PURPLE + mute.getTitre()).toItemStack());
					}
				}
				break;
			}
			default:
			{
				for (int slot = 27; slot <= 44; slot++) {
					inv.setItem(slot, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
				}
				break;
			}
		}
		p.openInventory(inv);
	}

	@Override
	public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, PlayerSanctionHolder holder) {
		OfflinePlayer target = holder.getTarget();
		int page = holder.getPage();
		SanctionType type = holder.getName();

		Warns warns = holder.warns.get(e.getSlot());
		TempBan temp_ban = holder.temp_ban.get(e.getSlot());
		Ban bans = holder.ban.get(e.getSlot());
		Kick kicks = holder.kick.get(e.getSlot());
		Freeze freezed = holder.freeze.get(e.getSlot());
		MuteTemp muteTemp = holder.muteTemp.get(e.getSlot());
		Mute muted = holder.mute.get(e.getSlot());

		removeEffects();

		switch (current.getType()) {
		case GREEN_WOOL:
		{
			if (!type.equals(SanctionType.WARN))
			{
				openInventory(player, target, SanctionType.WARN, 1);
				return;
			}
			if (!holder.warns.containsKey(e.getSlot())) return;
			player.closeInventory();
			if (warns.equals(Warns.INSULTES_HRP)) {
				Bukkit.dispatchCommand(player,
						"warn " + target.getName() + " Insultes HRP (La prochaine fois c'est un mute de 2 heures)");
			}
			else
			{
				Bukkit.dispatchCommand(player, "warn " + target.getName() + " " + warns.getName());
			}
			break;
		}
		case ORANGE_WOOL:
		{
			if (!type.equals(SanctionType.BAN_TEMP))
			{
				openInventory(player, target, SanctionType.BAN_TEMP, 1);
				return;
			}
			if (!holder.temp_ban.containsKey(e.getSlot())) return;
			player.closeInventory();
			Bukkit.dispatchCommand(player, "tempipban " + target.getName() + " " + temp_ban.getDuree() + " " + temp_ban.getSanction());
			break;
		}
		case RED_WOOL:
		{
			if (!type.equals(SanctionType.BAN))
			{
				openInventory(player, target, SanctionType.BAN, 1);
				return;
			}
			if(!holder.ban.containsKey(e.getSlot())) return;
			player.closeInventory();
			if (!Main.isPlayerInGroup(player, "moderateur")) {
				Bukkit.dispatchCommand(player, "tempipban " + target.getName() + " 100y " + bans.getTitre());
				return;
			}
			Bukkit.dispatchCommand(player, "ipban " + target.getName() + " " + bans.getTitre());
			break;
		}
		case LIGHT_BLUE_WOOL:
		{
			if (!type.equals(SanctionType.FREEZE))
			{
				openInventory(player, target, SanctionType.FREEZE, 1);
				return;
			}
			if (!holder.freeze.containsKey(e.getSlot())) return;
			if (freezed.equals(Freeze.FREEZE)) {
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
			if (freezed.equals(Freeze.UN_FREEZE)) {
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
		case MAGENTA_WOOL:
		{
			if (!type.equals(SanctionType.KICK))
			{
				openInventory(player, target, SanctionType.KICK, 1);
				return;
			}
			if(!holder.kick.containsKey(e.getSlot())) return;
			Bukkit.dispatchCommand(player, "kick " + target.getName() + " " + kicks.getTitre());
			break;
		}
		case SUNFLOWER:
		{
			InventoryManager.openInventory(player, InventoryType.MODO_PLAYER_MENU, target);
			break;
		}
		case GREEN_DYE:
		{
			openInventory(player, target, type, page + 1);
			break;
		}
		case RED_DYE:
		{
			openInventory(player, target, type, page - 1);
			break;
		}
		case PINK_WOOL:
		{
			if (!type.equals(SanctionType.MUTE_TEMP)) {
				openInventory(player, target, SanctionType.MUTE_TEMP, 1);
				return;
			}
			if (!holder.muteTemp.containsKey(e.getSlot())) return;
			if (muteTemp.equals(MuteTemp.INSULTES_HRP)) {
				player.closeInventory();
				Bukkit.dispatchCommand(player, "tempmute " + target.getName() + " 2h Insultes HRP (la prochaine fois un ban de 24h)");
				return;
			}
			break;
		}
		case PURPLE_WOOL:
		{
			if (!type.equals(SanctionType.MUTE))
			{
				openInventory(player, target, SanctionType.MUTE, 1);
				return;
			}
			if (!holder.mute.containsKey(e.getSlot())) return;
			break;
		}
		default:
			break;
		}
	}

	@Override
	public void closeInventory(Player player, InventoryCloseEvent e)
	{
		removeEffects();
	}

	private void removeEffects()
	{
		avertir.removeEnchantment(Enchantment.DURABILITY);
		bannir_temporairement.removeEnchantment(Enchantment.DURABILITY);
		bannir.removeEnchantment(Enchantment.DURABILITY);
		freeze.removeEnchantment(Enchantment.DURABILITY);
		kick.removeEnchantment(Enchantment.DURABILITY);
		tempmute.removeEnchantment(Enchantment.DURABILITY);
		mute.removeEnchantment(Enchantment.DURABILITY);
	}
	public enum SanctionType
	{
		MENU, WARN, BAN, BAN_TEMP, FREEZE, KICK, MUTE, MUTE_TEMP
	}
	public enum Warns
	{
		NO_FEAR_RP(1, "NoFearRP"),
		POWER_GAMING(1, "PowerGaming / NoPainRP"),
		META_GAMING(1, "MétaGaming"),
		NLR(1, "NLR"),
		RTZ(1, "RTZ"),
		SERIOUS_RP(1, "Non respect du Serious RP"),
		BRAQUAGE_RUE(1, "Braquage rue"),
		BRAQUAGE_SOLO(1, "Braquage solo"),
		HRP_EN_RP(1, "Hrp en RP"),
		NORP(1, "NoRP"),
		CONDUITE(1, "Conduite NoRP"),
		VEHICULE_BATIMENT(1, "Véhicule bâtiment"),
		FREE_TAZE(1, "FreeTaze"),
		FREE_JAIL(1, "FreeJail"),
		FREE_PUNCH(1, "FreePunch"),
		FREE_SHOT(1, "FreeShot"),
		ARMES(1, "Armes en métier légal"),
		OBJET_ILLEGAL(1, "Objet Illégal en métier légal"),
		INSULTES_HRP(2, "Insultes HRP"),
		PUB_NORP(2, "Pub NoRP"),
		PUB_INTERDITE(2, "Publicité interdite");

		private final int page;
		private final String name;
		Warns(int page, String name)
		{
			this.name = name;
			this.page = page;
		}

		public String getName() {
			return this.name;
		}

		public int getPage()
		{
			return this.page;
		}
	}
	public enum Ban
	{
		MENACE_ATTAQUE_DDOS(1, "Menace d'attaque DDoS");

		private final int page;
		private final String titre;
		Ban(int page, String titre)
		{
			this.page = page;
			this.titre = titre;
		}

		public String getTitre() {
			return this.titre;
		}

		public int getPage()
		{
			return this.page;
		}
	}
	public enum TempBan
	{
		HRP_EN_MUTE(1, "/HRP en mute", "2h", "Utilisation du /hrp en mute", "Durée : 2 heures"),
		INTRUSION_BATIMENT_STAFF(1, "Intrusion Bâtiment staff", "1h", "Intrusion bâtiment staff", "Durée : 1 heure");

		private final int page;
		private final String name;
		private final String duree;
		private final String sanction;
		private final String[] lore;
		TempBan(int page, String name, String duree, String sanction, String... lore)
		{
			this.page = page;
			this.name = name;
			this.duree = duree;
			this.sanction = sanction;
			this.lore = lore;
		}
		public int getPage()
		{
			return this.page;
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
	public enum Freeze
	{
		FREEZE(37, "Freeze le joueur"),
		UN_FREEZE(43, "UnFreeze le joueur")
		;
		private final int slot;
		private final String name;
		Freeze(int slot, String name)
		{
			this.slot = slot;
			this.name = name;
		}

		public int getSlot()
		{
			return this.slot;
		}
		public String getName()
		{
			return this.name;
		}
	}
	public enum Kick
	{
		AFK(1, "AFK")
		;

		private final int page;
		private final String titre;

		Kick(int page, String titre)
		{
			this.page = page;
			this.titre = titre;
		}

		public int getPage()
		{
			return this.page;
		}
		public String getTitre()
		{
			return this.titre;
		}
	}
	public enum Mute
	{
		;
		private final int page;
		private final String titre;
		Mute(int page, String titre)
		{
			this.page = page;
			this.titre = titre;
		}
		public String getTitre()
		{
			return this.titre;
		}

		public int getPage()
		{
			return this.page;
		}
	}
	public enum MuteTemp
	{
		INSULTES_HRP(1, "Insultes HRP", "Durée : 2 heures")
		;
		private final int page;
		private final String name;
		private final String[] lore;
		MuteTemp(int page, String name, String... lore)
		{
			this.page = page;
			this.name = name;
			this.lore = lore;
		}

		public int getPage()
		{
			return this.page;
		}

		public String getName()
		{
			return this.name;
		}

		public String[] getLore()
		{
			return this.lore;
		}
	}
}
