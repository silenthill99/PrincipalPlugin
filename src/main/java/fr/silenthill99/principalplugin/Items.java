package fr.silenthill99.principalplugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum Items
{
    TELEPHONE(new ItemBuilder(Material.BRICK).setName(ChatColor.YELLOW + "Téléphone")),
    ARGENT(new ItemBuilder(Material.GOLD_INGOT).setName(ChatColor.GREEN + "100€"));

    private final ItemBuilder items;

    Items(ItemBuilder items)
    {
        this.items = items;
    }

    public ItemBuilder getItems()
    {
        return this.items;
    }
}
