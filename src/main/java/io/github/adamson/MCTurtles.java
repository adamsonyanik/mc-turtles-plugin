package io.github.adamson;

import io.github.adamson.recipes.MiningTurtleRecipe;
import io.github.adamson.recipes.TurtleRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class MCTurtles extends JavaPlugin {

    public static MCTurtles plugin;

    @Override
    public void onEnable() {
        plugin = this;

        getServer().addRecipe(new TurtleRecipe());
        getServer().addRecipe(new MiningTurtleRecipe());

        getServer().getPluginManager().registerEvents(new TurtlePlaceListener(), this);
        getServer().getPluginManager().registerEvents(new TurtleDestroyListener(), this);
        getServer().getPluginManager().registerEvents(new TurtleClickListener(), this);
        getServer().getPluginManager().registerEvents(new ChunkLoadListener(), this);
    }
}
