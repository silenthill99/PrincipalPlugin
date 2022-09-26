package fr.silenthill99.principalplugin.inventory.holder;

import fr.silenthill99.principalplugin.Metiers;
import fr.silenthill99.principalplugin.inventory.SilenthillHolder;

import java.util.HashMap;
import java.util.Map;

public class PoleEmploiHolder extends SilenthillHolder
{
    public Map<Integer, Metiers> metiers = new HashMap<>();

    public PoleEmploiHolder()
    {
        for (Metiers m : Metiers.values())
        {
            metiers.put(metiers.size(), m);
        }
    }
}

