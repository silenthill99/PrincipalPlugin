package fr.silenthill99.principalplugin.inventory.holder;

import fr.silenthill99.principalplugin.inventory.SilenthillHolder;
import fr.silenthill99.principalplugin.inventory.hook.IllegauxInventory;

import java.util.HashMap;

public class IllegauxHolder extends SilenthillHolder {
    public HashMap<Integer, IllegauxInventory.Metiers> metiers = new HashMap<>();
}
