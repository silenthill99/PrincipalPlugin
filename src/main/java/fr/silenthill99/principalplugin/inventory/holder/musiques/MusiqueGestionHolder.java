package fr.silenthill99.principalplugin.inventory.holder.musiques;

import fr.silenthill99.principalplugin.inventory.Musiques;
import fr.silenthill99.principalplugin.inventory.SilenthillHolder;

import java.util.HashMap;

public class MusiqueGestionHolder extends SilenthillHolder
{

    public HashMap<Integer, Musiques> customName = new HashMap<>();
    private String name;

    public MusiqueGestionHolder(String name)
    {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
