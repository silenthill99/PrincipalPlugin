package fr.silenthill99.principalplugin.inventory.holder;

import fr.silenthill99.principalplugin.inventory.SilenthillHolder;
import fr.silenthill99.principalplugin.inventory.hook.GPSInventory;

import java.util.HashMap;

public class GPSHolder extends SilenthillHolder
{
    public HashMap<Integer, GPSInventory.Gps> gps = new HashMap<>();
}
