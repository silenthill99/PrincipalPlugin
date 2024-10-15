package fr.silenthill99.principalplugin;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

@SuppressWarnings("deprecation")
public class ItemBuilder
{

    public static ItemStack getArgent(int amount)
    {
        return new ItemBuilder(Material.GOLD_INGOT, amount).setName(ChatColor.GREEN + "100€").toItemStack();
    }

    private final ItemStack is;

    public ItemBuilder(Material m)
    {
        this(m, 1);
    }

    public ItemBuilder(Material m, int i)
    {
        is = new ItemStack(m, i);
    }

    public ItemBuilder(ItemStack is)
    {
        this.is = is;
    }

    public ItemBuilder clone()
    {
        return new ItemBuilder(is);
    }

    public ItemBuilder setName(String name)
    {
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setLore(String... lore)
    {
        ItemMeta im = is.getItemMeta();
        im.setLore(Arrays.asList(lore));
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setSkullOwner(String owner)
    {
        try
        {
            SkullMeta im = (SkullMeta) is.getItemMeta();
            im.setOwner(owner);
            is.setItemMeta(im);
        }
        catch (ClassCastException e)
        {
        }
        return this;
    }

    public ItemBuilder setLeatherArmorColor(Color color)
    {
        try
        {
            LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
            im.setColor(color);
            is.setItemMeta(im);
        }
        catch (ClassCastException e)
        {
        }
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(Enchantment ench, int level)
    {
        is.addUnsafeEnchantment(ench, level);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment ench, int level)
    {
        ItemMeta im = is.getItemMeta();
        im.addEnchant(ench, level, true);
        is.setItemMeta(im);
        return this;
    }
    
    public ItemBuilder addGlowingEffect() {
        ItemMeta im = is.getItemMeta();
        im.addEnchant(Enchantment.DURABILITY, 1, true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment ench)
    {
        is.removeEnchantment(ench);
        return this;
    }

    public ItemBuilder setItemMeta(ItemMeta im)
    {
        is.setItemMeta(im);
        return this;
    }




    public ItemBuilder setDurability(short durability)
    {
        is.setDurability(durability);
        return this;
    }

    public ItemStack toItemStack()
    {
        return is;
    }
}
