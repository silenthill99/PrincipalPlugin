package fr.silenthill99.principalplugin.inventory.holder;

import fr.silenthill99.principalplugin.inventory.SilenthillHolder;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CandidatHolder extends SilenthillHolder
{
    public Map<Integer, Player> candidats = new HashMap<>();
}
