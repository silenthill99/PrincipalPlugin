package fr.silenthill99.principalplugin.inventory.holder.direction;

import fr.silenthill99.principalplugin.inventory.SilenthillHolder;
import fr.silenthill99.principalplugin.inventory.hook.direction.BannissementStaffInventory.*;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;

public class BannissementStaffHolder extends SilenthillHolder {
    private final OfflinePlayer target;

    public BannissementStaffHolder(OfflinePlayer target) {
        this.target = target;
    }

    public OfflinePlayer getTarget() {
        return this.target;
    }

    public HashMap<Integer, Sanctions> sanctions = new HashMap<>();
}
