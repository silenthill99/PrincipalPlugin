package fr.silenthill99.principalplugin;

import org.bukkit.Material;

public enum Items
{
    ARGENT(new ItemBuilder(Material.PAPER).setName("Argent"));

    private final ItemBuilder item;
    Items(ItemBuilder item)
    {
        this.item = item;
    }

    public ItemBuilder item() {
        return item;
    }
}
