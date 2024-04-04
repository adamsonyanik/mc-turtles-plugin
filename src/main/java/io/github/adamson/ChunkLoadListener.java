package io.github.adamson;

import com.jeff_media.morepersistentdatatypes.DataType;
import org.bukkit.Chunk;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import java.util.Map;
import java.util.UUID;

public class ChunkLoadListener implements Listener {

    private static NamespacedKey turtlesNamespaceKey = new NamespacedKey(MCTurtles.plugin, "turtles");

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        loadTurtlesFromChunk(event.getChunk());
    }

    public static void loadTurtlesFromChunk(Chunk chunk) {
        System.out.println("loaded chunk " + chunk.getX() + ", " + chunk.getZ());
        if (!chunk.getPersistentDataContainer().has(turtlesNamespaceKey))
            return;

        Map<UUID, int[]> turtles = chunk.getPersistentDataContainer().get(turtlesNamespaceKey, DataType.asMap(DataType.UUID, DataType.INTEGER_ARRAY));

        for (Map.Entry<UUID, int[]> entry : turtles.entrySet())
            TurtleFactory.loadTurtle(chunk.getWorld(), entry.getValue()[0], entry.getValue()[1], entry.getValue()[2]);
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        unloadTurtlesFromChunk(event.getChunk());
    }

    public static void unloadTurtlesFromChunk(Chunk chunk) {
        System.out.println("unloaded chunk " + chunk.getX() + ", " + chunk.getZ());

        if (!chunk.getPersistentDataContainer().has(turtlesNamespaceKey))
            return;

        Map<UUID, int[]> turtles = chunk.getPersistentDataContainer().get(turtlesNamespaceKey, DataType.asMap(DataType.UUID, DataType.INTEGER_ARRAY));

        for (Map.Entry<UUID, int[]> entry : turtles.entrySet())
            TurtleFactory.unloadTurtle(entry.getKey());
    }
}
