package fr.silenthill99.principalplugin.inventory;

public enum Musiques
{
    HOTEL_CALIFORNIA("Hotel California"),
    ON_MELANCHOLY_HILL("On Melancholy Hill"),
    POKEMON("Pokemon"),
    PUMPED_UP_KICKS("Pumped Up Kicks"),
    VIVA_LA_VIDA("Viva la vida");
	
	private final String name;
	
	private Musiques(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
