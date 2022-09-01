package fr.silenthill99.principalplugin;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AdminOptionHolder implements InventoryHolder
{

    private final OfflinePlayer op;
    private final AdminOptionType type;

    public AdminOptionHolder(OfflinePlayer op, AdminOptionType type)
    {
        this.op = op;
        this.type = type;
    }

    public @Nullable OfflinePlayer getPlayer()
    {
        return this.op;
    }
    
    public AdminOptionType getType() {
		return type;
	}

    @Override
    public @NotNull Inventory getInventory()
    {
        return null;
    }
    
    public static enum AdminOptionType {
    	PLAYER_MENU, PLAYER_SANCTION
    }
}
