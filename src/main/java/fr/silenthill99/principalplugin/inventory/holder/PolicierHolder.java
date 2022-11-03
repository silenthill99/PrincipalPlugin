package fr.silenthill99.principalplugin.inventory.holder;

import fr.silenthill99.principalplugin.inventory.SilenthillHolder;
import org.bukkit.OfflinePlayer;

public class PolicierHolder extends SilenthillHolder
{
    private final OfflinePlayer target;

    public PolicierHolder(OfflinePlayer target)
    {
        this.target = target;
    }

    public OfflinePlayer getTarget()
    {
        return this.target;
    }
}
