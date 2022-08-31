package fr.silenthill99.principalplugin;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class AdminOptionHolder implements InventoryHolder
{

    private OfflinePlayer op;

    public AdminOptionHolder(OfflinePlayer op)
    {
        this.op = op;
    }

    public OfflinePlayer getPlayer()
    {
        return this.op;
    }

    @Override
    public @NotNull Inventory getInventory()
    {
        return null;
    }
}
