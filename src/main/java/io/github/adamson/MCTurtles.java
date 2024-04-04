package io.github.adamson;

import io.github.adamson.recipes.MiningTurtleRecipe;
import io.github.adamson.recipes.TurtleRecipe;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
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

        for (World w : Bukkit.getWorlds())
            for (Chunk c : w.getLoadedChunks())
                ChunkLoadListener.loadTurtlesFromChunk(c);

        getServer().getPluginManager().registerEvents(new ChunkLoadListener(), this);

        ArmorStand as = getServer().getWorld("world").spawn(new Location(getServer().getWorld("world"), 20, 80.5, -126.5), ArmorStand.class);

        as.setBasePlate(false);
        as.setVisible(false);
        as.setMarker(true);
        as.setCanPickupItems(false);
        as.setGravity(false);
        as.getEquipment().setHelmet(new ItemStack(Material.PLAYER_HEAD));

        Slime slime = (Slime) getServer().getWorld("world").spawnEntity(new Location(getServer().getWorld("world"), 20, 81.98, -126.5), EntityType.SLIME, false);
        slime.setAI(false);
        slime.setSize(1);
        slime.setInvisible(true);
        slime.setInvulnerable(false);
        slime.getPersistentDataContainer().set(new NamespacedKey(this, "attached-armorstand"), PersistentDataType.STRING, as.getUniqueId().toString());
    }

    @Override
    public void onDisable() {
        for (World w : Bukkit.getWorlds())
            for (Chunk c : w.getLoadedChunks())
                ChunkLoadListener.unloadTurtlesFromChunk(c);
    }
}
