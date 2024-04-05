package com.hesh925.mcprometheus.prometheus.Meters;

import com.hesh925.mcprometheus.MCPrometheus;
import org.bukkit.event.Listener;

public class Server implements Listener {

        public final MCPrometheus plugin;
        private final String prefix;


        public Server(MCPrometheus instance) {
            this.plugin = instance;

            this.prefix = this.plugin.config.getString("Exporter.PromPrefix");

            this.plugin.getLogger().info("starting join watch thing");

            //Plugin[] plugins = plugin.getServer().getPluginManager().getPlugins();
            //Arrays.stream(plugins).forEach(p -> plugin.getLogger().info(p.getName()));
            //[21:22:22 INFO]: [PROM] PlaceholderAPI
            //[21:22:22 INFO]: [PROM] Prom
            //[21:22:22 INFO]: [PROM] Chunky
        }
}
