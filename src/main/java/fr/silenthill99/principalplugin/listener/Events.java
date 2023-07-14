package fr.silenthill99.principalplugin.listener;

import fr.silenthill99.principalplugin.CustomFiles;
import fr.silenthill99.principalplugin.Main;
import fr.silenthill99.principalplugin.commands.Vanish;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.TimerTask;

@SuppressWarnings("deprecation")
public class Events implements Listener {

    Main main = Main.getInstance();

	@EventHandler
	public void onTchat(PlayerChatEvent event) throws IOException {
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
				CustomFiles.LOGS.addLog(players, "&1" + player.getName() + " &9a dit &b" + message);
			}
		}

        AreaEffectCloud tchat = (AreaEffectCloud) player.getWorld().spawnEntity(new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY()+1.5, player.getLocation().getZ()), EntityType.AREA_EFFECT_CLOUD);
		tchat.setCustomName(ChatColor.GOLD + player.getName() + " ► " + ChatColor.AQUA + message);
		tchat.setCustomNameVisible(true);
		tchat.setGravity(false);
		tchat.setParticle(Particle.BLOCK_CRACK, Material.AIR.createBlockData());
		tchat.setDuration(100);
		Bukkit.getScheduler().runTaskTimer(main, new TimerTask() {
			@Override
			public void run() {
				tchat.teleport(new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY()+1.5, player.getLocation().getZ()));
			}
		}, 0, 1);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) throws IOException {
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
		CustomFiles.LOGS.addLog(player, "&1" + player.getName() + " &9s'est déconnecté(e)");
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) throws IOException {
		Player player = event.getPlayer();
		event.setJoinMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "+" + ChatColor.AQUA + "] " + player.getName());
		CustomFiles.LOGS.removeLog(player);
		CustomFiles.LOGS.addLog(player, "&1" + player.getName() + " &9s'est connecté(e)");

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
		}
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		event.setCancelled(Main.getInstance().isFreeze(player));
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
	public void onAttack(EntityDamageByEntityEvent event) throws IOException {
		if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) return;
		Player player = (Player) event.getDamager();
		Player target = (Player) event.getEntity();
		float x = (float) target.getLocation().getX();
		float y = (float) target.getLocation().getY();
		float z = (float) target.getLocation().getZ();

		CustomFiles.LOGS.addLog(target, "&1" + target.getName() + " &9a été attaqué(e) par &1" + player.getName() + " &baux coordonnées : &ex : " + String.format("%.2f", x) + " y : " + String.format("%.2f", y) + " z : " + String.format("%.2f", z));
		CustomFiles.LOGS.addLog(player, "&1" + player.getName() + " &9a attaqué(e) &1" + target.getName() + " &baux coordonnées : &ex : " + String.format("%.2f", x) + " y : " + String.format("%.2f", y) + " z : " + String.format("%.2f", z));

	}
}
