package fr.silenthill99.principalplugin;

public enum Metiers {
    POLICIER("Policier", "http://novask.in/5911833608.png"),
    POMPIER("Pompier", "http://novask.in/5925383309.png"),
    MEDECIN("Médecin", "http://novask.in/5290663328.png"),
    CUISINIER("Cuisinier", "http://novask.in/5921353192.png"),
    CHASSEUR("Chasseur", "http://novask.in/5448143538.png");

    String name;
    String url;

    Metiers(String name, String url)
    {
        this.name = name;
        this.url = url;
    }

    public String getName()
    {
        return name;
    }

    public String url() {
        return url;
    }
}
