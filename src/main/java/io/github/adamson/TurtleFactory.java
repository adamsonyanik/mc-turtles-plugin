package io.github.adamson;

import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TurtleFactory {
    private static Map<UUID, Turtle> loadedTurtles = new HashMap<>();

    public static void loadTurtle(World world, int x, int y, int z) {
        world.getBlockAt(x, y, z);
        Turtle turtle = new Turtle();
        loadedTurtles.put(turtle.id, turtle);
    }

    public static void unloadTurtle(UUID turtleId) {
        loadedTurtles.get(turtleId).unload();
        loadedTurtles.remove(turtleId);
    }

    public static Turtle getTurtle(Block block) {
        // get id from pdc and lookup in loadedTurtles
        return null;
    }
}
