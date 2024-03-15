package com.hesh925.mcprometheus;

import io.prometheus.metrics.exporter.httpserver.HTTPServer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.hesh925.mcprometheus.PAPI.PAPI;

import java.io.File;

public final class MCPrometheus extends JavaPlugin {
    public static MCPrometheus instance;
    private HTTPServer httpServer;
    private File mainConfigFile;


    @Override
    public void onEnable() {
        instance = this;


        // get config file
        mainConfigFile = new File(getDataFolder(), "config.yml");
        if (!mainConfigFile.exists()) {
            try {
                saveResource("config/config.yml", false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(mainConfigFile);

        // Plugin startup logic


        // register PAPI
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().info("Registering PAPI");
            new PAPI().register();
        } else {
            getLogger().log(java.util.logging.Level.SEVERE, "Could not find PlaceholderAPI!!");
            getLogger().log(java.util.logging.Level.SEVERE, "Please install it to use this plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
