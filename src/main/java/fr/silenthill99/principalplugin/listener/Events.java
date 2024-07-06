package fr.silenthill99.principalplugin.listener;

import fr.silenthill99.principalplugin.CustomFiles;
import fr.silenthill99.principalplugin.Main;
import fr.silenthill99.principalplugin.MySQL;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
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
import org.bukkit.scheduler.BukkitTask;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("deprecation")
public class Events implements Listener {

    Main main = Main.getInstance();
	Connection connection = MySQL.getInstance().getConnection();

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
				CustomFiles.LOGS.addLog(players, ChatColor.DARK_BLUE + player.getName() + " a dit " + ChatColor.BLUE + message);
			}
		}

        ArmorStand tchat = (ArmorStand) player.getWorld().spawnEntity(new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY()+1.5, player.getLocation().getZ()), EntityType.ARMOR_STAND);
		tchat.setCustomName(ChatColor.GOLD + player.getName() + " ► " + ChatColor.AQUA + message);
		tchat.setCustomNameVisible(true);
		tchat.setGravity(false);
		tchat.setInvulnerable(true);
		tchat.setInvisible(true);
		new TchatDuration(player, tchat);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) throws IOException {
		Player player = event.getPlayer();
		if (main.isVanished(player) && player.hasPermission("oxydia.vanish")) {
			Bukkit.dispatchCommand(player, "vanish off");
		} else {
			if (main.isVanished(player)) {
				main.getVanished().remove(player.getName());
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
		CustomFiles.LOGS.addLog(player, ChatColor.DARK_BLUE+ player.getName() + ChatColor.BLUE + " s'est déconnecté(e)");

	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) throws IOException {
		Player player = event.getPlayer();
		event.setJoinMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "+" + ChatColor.AQUA + "] " + player.getName());

		Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(
						"SELECT uuid, reason FROM staff_blacklist WHERE uuid = ?");
				preparedStatement.setString(1, player.getUniqueId().toString());
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next() && !player.isBanned()) {
					String reason = resultSet.getString("reason");
					Bukkit.getScheduler().runTask(main, () -> customReason(player, reason));
				}
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

		if (!player.hasPlayedBefore()) {
			Bukkit.broadcastMessage("\n" + ChatColor.GOLD + player.getName() + ChatColor.AQUA
					+ " Vient de débarquer à Bessemer city ! Veuillez lui souhaiter la bienvenue !\n");
		}

		CustomFiles.LOGS.removeLog(player);
		CustomFiles.LOGS.addLog(player, ChatColor.DARK_BLUE + player.getName() + ChatColor.BLUE + " s'est connecté(e)");

		if (!player.getGameMode().equals(GameMode.ADVENTURE)) {
			player.setGameMode(GameMode.ADVENTURE);
		}
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "fly " + player.getName() + " off");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "god " + player.getName() + " off");

	}

	private void customReason(Player player, String reason) {
		if(player.isOp()) {
			player.setOp(false);
		}
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
				"lp user " + player.getName() + " parent set default");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
				"lp user " + player.getName() + " permission clear");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ipban " + player.getName() + " " + reason);
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

	@SuppressWarnings("DataFlowIssue")
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
		CustomFiles.LOGS.addLog(target, ChatColor.DARK_BLUE + target.getName() + ChatColor.BLUE + " a été attaqué(e) par " + ChatColor.DARK_BLUE + player.getName() + ChatColor.AQUA + " aux coordonnées : " + ChatColor.YELLOW +"x : " + String.format("%.2f", x) + " y : " + String.format("%.2f", y) + " z : " + String.format("%.2f", z));
		CustomFiles.LOGS.addLog(player, ChatColor.DARK_BLUE + player.getName() + ChatColor.BLUE + " a attaqué(e) " + ChatColor.DARK_BLUE + target.getName() + ChatColor.AQUA + " aux coordonnées : " + ChatColor.YELLOW +"x : " + String.format("%.2f", x) + " y : " + String.format("%.2f", y) + " z : " + String.format("%.2f", z));
	}

	private class TchatDuration implements Runnable {
		private final Player player;
		private final ArmorStand tchat;
		private final BukkitTask task;
		public TchatDuration(Player player, ArmorStand tchat)
		{
			this.player = player;
			this.tchat = tchat;
			this.task = Bukkit.getScheduler().runTaskTimer(main, this, 0, 1);
		}

		int i = 100;
		@Override
		public void run()
		{
			i--;
			if (i == 0)
			{
				task.cancel();
				tchat.remove();
			}
			tchat.teleport(player.getLocation());
		}
	}
}
