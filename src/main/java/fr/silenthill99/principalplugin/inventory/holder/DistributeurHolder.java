package fr.silenthill99.principalplugin.inventory.holder;

import fr.silenthill99.principalplugin.inventory.SilenthillHolder;
import fr.silenthill99.principalplugin.inventory.hook.DistributeurInventory.Distributeur;

import java.util.HashMap;

public class DistributeurHolder extends SilenthillHolder {

    public HashMap<Integer, Distributeur> distributeur = new HashMap<>();

}
