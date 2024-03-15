package com.hesh925.mcprometheus.PAPI;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PAPI extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "prom";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Hesh925";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    public String onRequest(OfflinePlayer player, String identifier) {
        if (identifier.equals("rank")) {
            return "PLACEHOLDER_MCPrometheus";
        }
        return null;
    }
}