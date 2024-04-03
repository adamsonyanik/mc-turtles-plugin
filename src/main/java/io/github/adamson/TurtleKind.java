package io.github.adamson;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public enum TurtleKind {
    TURTLE("turtle", "Turtle"), MINING_TURTLE("mining_turtle", "Mining Turtle");

    public final String id;
    public final String displayName;

    private TurtleKind(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public static TurtleKind get(Block block) {
        if ((block.getType() != Material.PLAYER_HEAD && block.getType() != Material.PLAYER_WALL_HEAD) || !((Skull) block.getState()).hasOwner())
            return null;

        String name = ((Skull) block.getState()).getOwnerProfile().getName();

        for (TurtleKind kind : TurtleKind.values())
            if (name.equals("mc-turtles-plugin-" + kind.id))
                return kind;

        return null;
    }

    public static TurtleKind get(ItemStack item) {
        if (item.getType() != Material.PLAYER_HEAD || !((SkullMeta) item.getItemMeta()).hasOwner())
            return null;

        String name = ((SkullMeta) item.getItemMeta()).getOwnerProfile().getName();

        for (TurtleKind kind : TurtleKind.values())
            if (name.equals("mc-turtles-plugin-" + kind.id))
                return kind;

        return null;
    }
}
