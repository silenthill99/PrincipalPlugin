package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.Main;
import fr.silenthill99.principalplugin.Variables;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.holder.CandidatHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CandidatInventory extends AbstractInventory<CandidatHolder> {
    public CandidatInventory()
    {
        super(CandidatHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args)
    {
        CandidatHolder holder = new CandidatHolder();
        Inventory inv = createInventory(holder, 54, ChatColor.DARK_GREEN + "Voter pour un candidat");
        int slot = 0;
        for (Player players : Bukkit.getOnlinePlayers())
        {
            if (Variables.candidat.contains(players))
            {
                holder.candidats.put(slot, players);
                inv.setItem(slot++, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(players.getName()).setName(ChatColor.YELLOW + "Votez " + players.getName()).toItemStack());
            }
        }
        p.openInventory(inv);
    }
}
