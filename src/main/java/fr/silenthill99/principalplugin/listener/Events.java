package fr.silenthill99.principalplugin.listener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.destroystokyo.paper.event.player.PlayerAttackEntityCooldownResetEvent;
import fr.silenthill99.principalplugin.Variables;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.silenthill99.principalplugin.Main;
import fr.silenthill99.principalplugin.commands.Vanish;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;

@SuppressWarnings("deprecation")
public class Events implements Listener {

	@EventHandler
	public void onTchat(AsyncPlayerChatEvent event) {
		event.setCancelled(true);
		String message = event.getMessage();
		Player player = event.getPlayer();
		for (Player players : Bukkit.getOnlinePlayers()) {
			if (players.getLocation().distanceSquared(player.getLocation()) <= 100) {
				if (!players.equals(player)) {
					players.sendMessage(ChatColor.BLUE + player.getName() + " : " + ChatColor.AQUA + message);
				} else {
					players.sendMessage(ChatColor.GOLD + "vous dites : " + ChatColor.GREEN + message);
				}
			}
		}
		Variables.logs.get(player.getUniqueId())
				.add(ChatColor.YELLOW + "[" + new Timestamp(System.currentTimeMillis()) + "] " + ChatColor.DARK_BLUE
						+ player.getName() + " a dit " + ChatColor.BLUE + message);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (Vanish.getVanished().contains(player.getName()) && player.hasPermission("oxydia.vanish")) {
			Bukkit.dispatchCommand(player, "vanish off");
		} else {
			if (Vanish.getVanished().contains(player.getName())) {
				Vanish.getVanished().remove(player.getName());
			}
			for (Player players : Bukkit.getOnlinePlayers()) {
				players.showPlayer(Main.getInstance(), player);
			}
		}
		if (Main.getInstance().isFreeze(player)) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tempipban " + player.getName() + " 5d Déco en Freeze !");
			Main.getInstance().getFrozenPlayers().remove(player.getUniqueId());
		}
		event.setQuitMessage(ChatColor.AQUA + "[" + ChatColor.RED + "-" + ChatColor.AQUA + "] " + player.getName());
		Variables.logs.get(player.getUniqueId())
				.add(ChatColor.YELLOW + "[" + new Timestamp(System.currentTimeMillis()) + "] " + ChatColor.DARK_BLUE
						+ player.getName() + ChatColor.BLUE + " s'est déconnecté(e)");

	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		event.setJoinMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "+" + ChatColor.AQUA + "] " + player.getName());
		Variables.logs.remove(player.getUniqueId());
		List<String> list = new ArrayList<>();
		list.add(ChatColor.YELLOW + "[" + new Timestamp(System.currentTimeMillis()) + "] " + ChatColor.DARK_BLUE
				+ player.getName() + ChatColor.BLUE + " s'est connecté(e)");
		Variables.logs.put(player.getUniqueId(), list);
		if (!player.hasPlayedBefore()) {
			Bukkit.broadcastMessage("\n" + ChatColor.GOLD + player.getName() + ChatColor.AQUA
					+ " Vient de débarquer à Bessemer city ! Veuillez lui souhaiter la bienvenue !\n");
		}

		if (!player.getGameMode().equals(GameMode.ADVENTURE)) {
			player.setGameMode(GameMode.ADVENTURE);
		}
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "fly " + player.getName() + " off");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "god " + player.getName() + " off");

	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (Main.getInstance().isFreeze(player)) {
			event.setTo(event.getFrom());
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player)
		{
			event.setCancelled(Main.getInstance().isFreeze((Player) event.getEntity()));
			return;
		}
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		event.setCancelled(Main.getInstance().isFreeze(player));
	}

	@EventHandler
	public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
		Player player = event.getPlayer();
		Entity target = event.getRightClicked();
		if (target.getName().equals("pôle emploi")) {
			InventoryManager.openInventory(player, InventoryType.POLE_EMPLOI);
		}
		else if (target.getName().equals("Gangster"))
		{
			InventoryManager.openInventory(player, InventoryType.METIERS_ILLEGAUX);
		}
		else if (target.getName().equals("MacDo"))
		{
			InventoryManager.openInventory(player, InventoryType.MACDO);
		}
		else if (target.getName().equals("Armurier"))
		{
			InventoryManager.openInventory(player, InventoryType.ARMURERIE);
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (!player.hasPermission("oxydia.build")) {
			event.setCancelled(true);
			player.sendMessage(ChatColor.DARK_RED + "Tu ne peux pas poser de blocs !");
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (!player.hasPermission("oxydia.build")) {
			event.setCancelled(true);
			player.sendMessage(ChatColor.DARK_RED + "Tu ne peux pas casser de blocs");
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (event.getClickedBlock().getType().equals(Material.DROPPER)) {
				event.setCancelled(true);
				InventoryManager.openInventory(player, InventoryType.DISTRIBUTEUR);
			}
			else if (event.getClickedBlock().getType().equals(Material.CAULDRON))
			{
				InventoryManager.openInventory(player, InventoryType.POUBELLE);
			}
		}
	}

	@EventHandler
	public void onAttack(PlayerAttackEntityCooldownResetEvent event)
	{
		Player player = event.getPlayer();
		Entity entity = event.getAttackedEntity();
		if (!(entity instanceof Player))
		{
			return;
		}
		Player target = (Player) entity;
		float x = (float) target.getLocation().getX();
		float y = (float) target.getLocation().getY();
		float z = (float) target.getLocation().getZ();
		Variables.logs.get(target.getUniqueId()).add(ChatColor.YELLOW + "[" + new Timestamp(System.currentTimeMillis()) + "] " + ChatColor.DARK_BLUE + target.getName() + ChatColor.BLUE + " a été attaqué(e) par " + ChatColor.DARK_BLUE + player.getName() + ChatColor.AQUA + " aux coordonnées : " + ChatColor.YELLOW +"x : " + String.format("%.2f", x) + " y : " + String.format("%.2f", y) + " z : " + String.format("%.2f", z));
		Variables.logs.get(player.getUniqueId()).add(ChatColor.YELLOW + "[" + new Timestamp(System.currentTimeMillis()) + "] " + ChatColor.DARK_BLUE + player.getName() + ChatColor.BLUE + " a attaqué(e) " + ChatColor.DARK_BLUE + target.getName() + ChatColor.AQUA + " aux coordonnées : " + ChatColor.YELLOW +"x : " + String.format("%.2f", x) + " y : " + String.format("%.2f", y) + " z : " + String.format("%.2f", z));
	}
}
