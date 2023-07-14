package fr.silenthill99.principalplugin;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

public enum CustomFiles
{
    LOGS(new File(Main.getInstance().getDataFolder(), "logs.yml"));

    private final File file;
    private final YamlConfiguration config;
    CustomFiles(File file)
    {
        this.file = file;
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public File getFile()
    {
        return this.file;
    }

    public void addLog(Player player, String log) throws IOException {
        List<String> messages = config.getStringList(player.getName() + ".logs");
        messages.add("&e[" + new Timestamp(System.currentTimeMillis()) + "] " + log);
        config.set(player.getName() + ".logs", messages);
        config.save(file);
    }

    public void removeLog(Player player) throws IOException {
        List<String> messages = config.getStringList(player.getName() + ".logs");
        messages.clear();
        config.set(player.getName() + ".logs", messages);
        config.save(file);
    }
}
