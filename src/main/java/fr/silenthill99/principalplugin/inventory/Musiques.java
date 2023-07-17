package fr.silenthill99.principalplugin.inventory;

public enum Musiques
{
	GANGSTAS_PARADISE("Gangsta's Paradise"),
    HOTEL_CALIFORNIA("Hotel California"),
    ON_MELANCHOLY_HILL("On Melancholy Hill"),
	OSHI_NO_KO_IDOL("Oshi no ko - Idol"),
    POKEMON("Pokemon"),
    PUMPED_UP_KICKS("Pumped Up Kicks"),
    VIVA_LA_VIDA("Viva la vida");
	
	private final String name;
	
	Musiques(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
