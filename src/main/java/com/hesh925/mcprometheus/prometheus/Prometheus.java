package com.hesh925.mcprometheus.prometheus;

import com.hesh925.mcprometheus.MCPrometheus;
import com.hesh925.mcprometheus.prometheus.Meters.*;
import io.prometheus.metrics.exporter.httpserver.HTTPServer;

import java.io.IOException;

public class Prometheus {
    public final String prefix;
    private final MCPrometheus plugin;
    private HTTPServer httpServer;

    // Meters
    private final JREExporter jreExporter;
    private final Players players;
    private final Server server;
    private final Worlds worlds;
    private final TPS tps;

    public Prometheus(MCPrometheus instance) {
        this.prefix = ""; // set from config
        this.plugin = instance;
        this.jreExporter = new JREExporter(this.plugin);
        this.players = new Players(this.plugin);
        this.server = new Server(this.plugin);
        this.worlds = new Worlds(this.plugin);
        this.tps = new TPS(this.plugin);

        try { Exporter(); }
        catch (IOException e) {
            plugin.getLogger().severe(e.toString());
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }

    /**
     * Starts the Prometheus Exporter
     * @throws IOException
     */
    private void Exporter() throws IOException {
        plugin.getLogger().info("Starting Prometheus Exporter");
        httpServer = HTTPServer.builder().port(plugin.config.getInt("Exporter.Port")).buildAndStart();

        plugin.getLogger().info("Prometheus Exporter started on port " + httpServer.getPort());

    }

    public void stop() {
        plugin.getLogger().info("Stopping Prometheus Exporter");
        httpServer.stop();
        tps.stop();
    }
}
