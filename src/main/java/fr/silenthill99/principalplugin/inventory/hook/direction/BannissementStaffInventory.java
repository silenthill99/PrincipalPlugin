package fr.silenthill99.principalplugin.inventory.hook.direction;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.Main;
import fr.silenthill99.principalplugin.MySQL;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.InventoryManager;
import fr.silenthill99.principalplugin.inventory.InventoryType;
import fr.silenthill99.principalplugin.inventory.holder.direction.BannissementStaffHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class BannissementStaffInventory extends AbstractInventory<BannissementStaffHolder> {
    private final Main main = Main.getInstance();
    public BannissementStaffInventory() {
        super(BannissementStaffHolder.class);
    }

    @Override
    public void openInventory(Player player, Object... args) {
        OfflinePlayer target = (OfflinePlayer) args[0];
        BannissementStaffHolder holder = new BannissementStaffHolder(target);
        Inventory inv = createInventory(holder, 9, ChatColor.DARK_RED + "Bannissement immédiat | " + target.getName());
        int slot = 0;
        for (Sanctions sanctions : Sanctions.values()) {
            holder.sanctions.put(slot, sanctions);
            inv.setItem(slot++, new ItemBuilder(Material.REDSTONE_BLOCK).setName(ChatColor.DARK_RED + sanctions.getName()).toItemStack());
        }
        inv.setItem(inv.getSize()-1, RETOUR);
        player.openInventory(inv);
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, BannissementStaffHolder holder) {
        OfflinePlayer target = holder.getTarget();
        Sanctions sanctions = holder.sanctions.get(e.getSlot());
        switch (current.getType()) {
            case REDSTONE_BLOCK: {
                player.closeInventory();
                Bukkit.dispatchCommand(player, "lp user " + target.getName() + " permission clear");
                Bukkit.dispatchCommand(player, "lp user " + target.getName() + " parent set default");
                Bukkit.dispatchCommand(player, "ipban " + target.getName() + " " + sanctions.getReason());
//                File blacklist = new File(main.getDataFolder(), "staff_blacklist.yml");
//                YamlConfiguration config = YamlConfiguration.loadConfiguration(blacklist);
//                if (config.getConfigurationSection("bannissements") == null) config.createSection("bannissements");
//                config.getConfigurationSection("bannissements").set(String.valueOf(target.getUniqueId()), sanctions.getReason());
//                try {
//                    config.save(blacklist);
//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                }

                MySQL mysql = new MySQL();
                Connection connection = mysql.getConnection();
                Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO staff_blacklist VALUES (?, ?, ?)");
                        preparedStatement.setString(1, target.getUniqueId().toString());
                        preparedStatement.setString(2, sanctions.getReason());
                        preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                        preparedStatement.executeUpdate();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                break;
            }
            case SUNFLOWER: {
                InventoryManager.openInventory(player, InventoryType.DIRECTION_ERREUR_STAFF, target);
                break;
            }
        }
    }

    public enum Sanctions {
        CORRUPTION("Corruption", "Corruption (A ne pas débannir)"),
        DISCRIMINATION("Discrimination", "Discrimination (A ne pas débannir)");

        private final String name;
        private final String reason;

        Sanctions(String name, String reason) {
            this.name = name;
            this.reason = reason;
        }

        public String getName() {
            return this.name;
        }

        public String getReason() {
            return this.reason;
        }
    }
}
