package com.hesh925.mcprometheus.Types;

import java.util.UUID;

public class World {
    private final UUID uid;
    private final String name;
    private int loadedChunks;
    private int totalChunks;
    private int entities;
    private int players;

    private org.bukkit.World world;
    public World(org.bukkit.World world) {
        this.world = world;
        this.uid = world.getUID();
        this.name = world.getName();
        this.loadedChunks = world.getLoadedChunks().length;
        this.totalChunks = world.getChunkCount();
        this.entities = world.getEntityCount();
        this.players = world.getPlayers().size();
    }

    private void update() {
        this.loadedChunks = world.getLoadedChunks().length;
        this.totalChunks = world.getChunkCount();
        this.entities = world.getEntityCount();
        this.players = world.getPlayers().size();
    }

    public UUID getUID() {
        return this.uid;
    }

    public String getName() {
        return this.name;
    }

    public int getLoadedChunks() {
        return this.loadedChunks;
    }

    public int getTotalChunks() {
        return this.totalChunks;
    }

    public int getEntities() {
        return this.entities;
    }

    public int getPlayers() {
        return this.players;
    }

    public void setWorld(org.bukkit.World world) {
        this.world = world;
        this.update();
    }

    public org.bukkit.World getWorld() {
        return this.world;
    }


}
