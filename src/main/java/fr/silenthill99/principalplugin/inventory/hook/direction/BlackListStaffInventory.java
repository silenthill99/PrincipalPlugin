package fr.silenthill99.principalplugin.inventory.hook.direction;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.Main;
import fr.silenthill99.principalplugin.MySQL;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.holder.direction.BlackListStaffHolder;
import org.bukkit.Bukkit;
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

public class BlackListStaffInventory extends AbstractInventory<BlackListStaffHolder> {
    public BlackListStaffInventory() {
        super(BlackListStaffHolder.class);
    }

    @Override
    public void openInventory(Player player, Object... args) {
        OfflinePlayer target = (OfflinePlayer) args[0];
        BlackListStaffHolder holder = new BlackListStaffHolder(target);

        Inventory inv = createInventory(holder, 27, "Blacklist staff | " + target.getName());
        int slot = 9;
        for (BlacklistStaff blStaff : BlacklistStaff.values()) {
            holder.blStaff.put(slot, blStaff);
            inv.setItem(slot++, new ItemBuilder(Material.REDSTONE).setName(blStaff.getTitle()).toItemStack());
        }
        player.openInventory(inv);
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, BlackListStaffHolder holder) {
        OfflinePlayer target = holder.getTarget();
        BlacklistStaff blStaff = holder.blStaff.get(e.getSlot());

        MySQL mysql = new MySQL();
        Connection connection = mysql.getConnection();

        switch (current.getType()) {
            case REDSTONE: {
                player.closeInventory();
                Bukkit.dispatchCommand(player, "ipban " + target.getName() + " " + blStaff.getReason());
                long time = System.currentTimeMillis();

                Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO staff_blacklist VALUES (?, ?, ?)");
                        preparedStatement.setString(1, target.getUniqueId().toString());
                        preparedStatement.setString(2, blStaff.getReason());
                        preparedStatement.setTimestamp(3, new Timestamp(time));
                        preparedStatement.executeUpdate();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                break;
            }
        }
    }

    public enum BlacklistStaff {
        PATATES("Mange trop de patates", "Mange trop de patates")
        ;
        private final String title;
        private final String reason;

        BlacklistStaff(String title, String reason) {
            this.title = title;
            this.reason = reason;
        }

        public String getTitle() {
            return title;
        }

        public String getReason() {
            return reason;
        }
    }
}
