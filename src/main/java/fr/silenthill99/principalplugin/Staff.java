package fr.silenthill99.principalplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Staff implements TabCompleter
{
    private final List<String> arguments = new ArrayList<>();
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String msg, @NotNull String[] args) {
        String prefix = args[args.length - 1].toLowerCase(Locale.ROOT);
        for (String s : Arrays.asList("on", "off"))
        {
            if (prefix.isEmpty() || s.startsWith(prefix))
            {
                arguments.add(s);
            }
        }
        return arguments;
    }
}
