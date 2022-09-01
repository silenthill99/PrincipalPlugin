package fr.silenthill99.principalplugin.listener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
		Main.getInstance().logs.get(player.getUniqueId())
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
		Main.getInstance().logs.get(player.getUniqueId())
				.add(ChatColor.YELLOW + "[" + new Timestamp(System.currentTimeMillis()) + "] " + ChatColor.DARK_BLUE
						+ player.getName() + ChatColor.BLUE + " s'est déconnecté(e)");

	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		event.setJoinMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "+" + ChatColor.AQUA + "] " + player.getName());
		Main.getInstance().logs.remove(player.getUniqueId());
		List<String> list = new ArrayList<>();
		list.add(ChatColor.YELLOW + "[" + new Timestamp(System.currentTimeMillis()) + "] " + ChatColor.DARK_BLUE
				+ player.getName() + ChatColor.BLUE + " s'est connecté(e)");
		Main.getInstance().logs.put(player.getUniqueId(), list);
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
		Player player = (Player) event.getEntity();
		event.setCancelled(Main.getInstance().isFreeze(player));
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
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
				&& event.getClickedBlock().getType().equals(Material.DROPPER)) {
			event.setCancelled(true);
			InventoryManager.openInventory(event.getPlayer(), InventoryType.DISTRIBUTEUR);
		}
	}
}
