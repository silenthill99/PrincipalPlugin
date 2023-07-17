package fr.silenthill99.principalplugin.inventory.holder;

import fr.silenthill99.principalplugin.inventory.SilenthillHolder;
import fr.silenthill99.principalplugin.inventory.hook.MetierInventory.Metier;

import java.util.HashMap;

public class MetierHolder extends SilenthillHolder
{
    private final int page;

    public MetierHolder(int page)
    {
        this.page = page;
    }

    public int getPage()
    {
        return this.page;
    }
    public HashMap<Integer, Metier> metier = new HashMap<>();
}
