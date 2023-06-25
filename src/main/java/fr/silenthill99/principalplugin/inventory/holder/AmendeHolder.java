package fr.silenthill99.principalplugin.inventory.holder;

import fr.silenthill99.principalplugin.inventory.SilenthillHolder;
import org.bukkit.OfflinePlayer;

public class AmendeHolder extends SilenthillHolder
{
    private final OfflinePlayer target;

    public AmendeHolder(OfflinePlayer target)
    {
        this.target = target;
    }

    public OfflinePlayer getTarget()
    {
        return this.target;
    }
}
