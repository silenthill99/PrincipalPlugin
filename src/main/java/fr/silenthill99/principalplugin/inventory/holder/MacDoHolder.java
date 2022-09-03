package fr.silenthill99.principalplugin.inventory.holder;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.inventory.SilenthillHolder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MacDoHolder extends SilenthillHolder
{

    public ItemStack steak = new ItemBuilder(Material.COOKED_BEEF).setLore("Prix : 10€").toItemStack();

}
