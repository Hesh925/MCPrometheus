package com.hesh925.mcprometheus.prometheus.Meters;

import java.math.BigDecimal;
import java.util.Arrays;

import com.hesh925.mcprometheus.MCPrometheus;
import io.prometheus.metrics.core.metrics.*;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Measures and reports TPS to Prometheus.
 */
public class TPS extends BukkitRunnable {

    private final MCPrometheus plugin;
    private final String prefix;
    private final Summary Metric_Tick_Duration;
    private final Counter Metric_Low_TPS;
    private final long[] tickDurations;
    private final int sampleSize;
    private int tickIndex = 0;
    private long lastTickTime = System.currentTimeMillis();
    private double lowTPS;

    /**
     * Creates a new TPS meter.
     * @param instance The plugin instance.
     */
    public TPS(MCPrometheus instance) {
        plugin = instance;
        sampleSize = plugin.config.getInt("Exporter.TPSSamples");
        plugin.getLogger().info("TPS Samples config: " + sampleSize);
        lowTPS = plugin.config.getDouble("Exporter.LowTPS");
        plugin.getLogger().info("Low TPS setting: " + lowTPS);
        this.tickDurations = new long[sampleSize];

        prefix = plugin.config.getString("Exporter.PromPrefix");

        Metric_Tick_Duration = Summary.builder()
                .name(prefix + "_tick_duration")
                .help("Minecraft Tick Duration")
                .quantile(0.5, 0.05)
                .quantile(0.9, 0.01)
                .quantile(0.99, 0.001)
                .register();

        Metric_Low_TPS = Counter.builder()
                .name(prefix + "_low_tps")
                .help("Low TPS warnings")
                .register();

        this.runTaskTimer(plugin, 0L, 1L);
    }

    @Override
    public void run() {
        long currentTime = System.currentTimeMillis();
        long tickDuration = currentTime - lastTickTime;
        lastTickTime = currentTime;

        tickDurations[tickIndex] = tickDuration;
        tickIndex = (tickIndex + 1) % tickDurations.length;

        double averageTickTime = calculateAverageTickTime();
        double tps = 1000.0 / averageTickTime;

        if (tickDurations[5] != 0) {
            Metric_Tick_Duration.observe(tickDuration);

            if(tps < lowTPS) {
                Metric_Low_TPS.inc();
                plugin.getLogger().warning("Low TPS: " + tps);
            }
        }
    }

    private double calculateAverageTickTime() {
        long totalTickTime = 0;
        for (long duration : tickDurations) {
            totalTickTime += duration;
        }
        return (double) totalTickTime / tickDurations.length;
    }

    public void stop() {
        this.cancel();
    }
}
