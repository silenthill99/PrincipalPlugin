package fr.silenthill99.principalplugin;

import java.io.File;

public enum CustomFiles
{
    LOGS(new File(Main.getInstance().getDataFolder(), "logs"));

    private final File file;
    CustomFiles(File file)
    {
        this.file = file;
    }

    public File getFile()
    {
        return this.file;
    }
}
