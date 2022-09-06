package fr.silenthill99.principalplugin.inventory.holder;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.inventory.SilenthillHolder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class PoleEmploiHolder extends SilenthillHolder
{
    public ItemStack citoyen = new ItemBuilder(Material.PLAYER_HEAD).setName(ChatColor.YELLOW + "Citoyen").toItemStack();
    public ItemStack policier = new ItemBuilder(Material.PAPER).setName(ChatColor.YELLOW + "Policier").toItemStack();
    public ItemStack pompier = new ItemBuilder(Material.PAPER).setName(ChatColor.YELLOW + "Pompier").toItemStack();
    public ItemStack medecin = new ItemBuilder(Material.PAPER).setName(ChatColor.YELLOW + "Medecin").toItemStack();
}

