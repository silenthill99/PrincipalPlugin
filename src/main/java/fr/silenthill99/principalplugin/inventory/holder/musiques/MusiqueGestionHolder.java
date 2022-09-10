package fr.silenthill99.principalplugin.inventory.holder.musiques;

import fr.silenthill99.principalplugin.inventory.Musiques;
import fr.silenthill99.principalplugin.inventory.SilenthillHolder;

public class MusiqueGestionHolder extends SilenthillHolder
{

    private Musiques music;

    public MusiqueGestionHolder(Musiques music)
    {
        this.music = music;
    }

    public Musiques getMusic() {
		return music;
	}
}
