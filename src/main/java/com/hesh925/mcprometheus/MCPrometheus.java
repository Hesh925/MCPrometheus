package com.hesh925.mcprometheus;

import com.hesh925.mcprometheus.prometheus.Prometheus;
import io.prometheus.metrics.exporter.httpserver.HTTPServer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class MCPrometheus extends JavaPlugin {
    public static MCPrometheus instance;

    public static YamlConfiguration config;

    private Prometheus prom_instance;
    private File mainConfigFile;
    private static final String ConfigPath = "Prom.yml";


 @Override
 public void onEnable() {
     instance = this;

     mainConfigFile = new File(getDataFolder(), ConfigPath);
     if (!mainConfigFile.exists()) saveResource(ConfigPath, false);

     getLogger().info("Loading config");

     config = YamlConfiguration.loadConfiguration(mainConfigFile);

     prom_instance = new Prometheus(this);
 }

    @Override
    public void onDisable() {
        prom_instance.stop();
        // Plugin shutdown logic
    }
}
