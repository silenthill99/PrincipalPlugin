package fr.silenthill99.principalplugin.inventory.holder.direction;

import fr.silenthill99.principalplugin.inventory.SilenthillHolder;
import fr.silenthill99.principalplugin.inventory.hook.direction.BlackListStaffInventory.BlacklistStaff;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;

public class BlackListStaffHolder extends SilenthillHolder {

    private final OfflinePlayer target;

    public BlackListStaffHolder(OfflinePlayer target) {
        this.target = target;
    }

    public OfflinePlayer getTarget() {
        return target;
    }

    public HashMap<Integer, BlacklistStaff> blStaff = new HashMap<>();
}
