package com.hesh925.mcprometheus.prometheus.Meters;

import com.hesh925.mcprometheus.MCPrometheus;
import io.prometheus.metrics.core.metrics.Counter;
import io.prometheus.metrics.core.metrics.Gauge;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Players implements Listener {
    private final MCPrometheus plugin;
    private final String prefix;

    private Gauge Metric_Players_Online;

    private Counter Metric_Player_Deaths;

    public Players(MCPrometheus instance) {
        this.plugin = instance;
        this.prefix = this.plugin.config.getString("Exporter.PromPrefix");

        Metric_Players_Online = Gauge.builder()
                .name(prefix + "_players_online")
                .help("Players Online")
                .labelNames("world")
                .register();

        Metric_Player_Deaths = Counter.builder()
                .name(prefix + "_player_deaths")
                .help("Player Deaths")
                .register();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        Metric_Players_Online.inc();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        Metric_Players_Online.dec();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onKick(PlayerKickEvent event) {
        Metric_Players_Online.dec();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent event) {
        Metric_Player_Deaths.inc();
    }
}
