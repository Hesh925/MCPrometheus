package com.hesh925.mcprometheus.prometheus.Meters;

import com.hesh925.mcprometheus.MCPrometheus;
import com.hesh925.mcprometheus.Types.World;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class Worlds {

    public final MCPrometheus plugin;
    private final String prefix;
    private World[] worlds;

    public Worlds(MCPrometheus instance) {
        this.plugin = instance;

        this.prefix = this.plugin.config.getString("Exporter.PromPrefix");

        this.plugin.getLogger().info("starting join watch thing");

        this.worlds = new World[plugin.getServer().getWorlds().size()];

        int i = 0;

        for(org.bukkit.World world : plugin.getServer().getWorlds()) {
            this.worlds[i] = new World(world);
            i++;
        }

    }
}
