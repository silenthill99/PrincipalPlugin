package fr.silenthill99.principalplugin;

public enum Metiers {
    POLICIER("http://novask.in/5911833608.png"),
    POMPIER("http://novask.in/5925383309.png"),
    MEDECIN("http://novask.in/5290663328.png"),
    CUISINIER("http://novask.in/5921353192.png"),
    CHASSEUR("http://novask.in/5448143538.png");

    String url;

    Metiers(String url)
    {
        this.url = url;
    }

    public String url() {
        return url;
    }
}
