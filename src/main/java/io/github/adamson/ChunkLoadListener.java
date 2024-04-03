package io.github.adamson;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class ChunkLoadListener implements Listener {

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        System.out.println("loaded chunk " + event.getChunk().getX() + ", " + event.getChunk().getZ());
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        System.out.println("unloaded chunk " + event.getChunk().getX() + ", " + event.getChunk().getZ());
    }
}
