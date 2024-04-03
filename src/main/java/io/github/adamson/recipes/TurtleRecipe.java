package io.github.adamson.recipes;

import io.github.adamson.CustomHeads;
import io.github.adamson.MCTurtles;
import io.github.adamson.Utils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class TurtleRecipe extends ShapedRecipe {
    public TurtleRecipe() {
        super(new NamespacedKey(MCTurtles.plugin, "turtle"), Utils.createSkull("Turtle", "turtle", CustomHeads.TURTLE));

        this.shape("R", "C", "F");
        this.setIngredient('R', Material.REDSTONE);
        this.setIngredient('C', Material.CHEST);
        this.setIngredient('F', Material.FURNACE);
    }
}
