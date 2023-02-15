package fr.silenthill99.principalplugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Items
{
    TELEPHONE(new ItemBuilder(Material.BRICK).setName(ChatColor.YELLOW + "Téléphone").toItemStack());

    private final ItemStack items;

    Items(ItemStack items)
    {
        this.items = items;
    }

    public ItemStack getItems()
    {
        return this.items;
    }
}
