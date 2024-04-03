package io.github.adamson;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.block.data.Rotatable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.meta.SkullMeta;

public class TurtlePlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        TurtleKind kind = TurtleKind.get(event.getItemInHand());
        if (kind == null)
            return;

        Block block = event.getBlock();
        Material originalMaterial = block.getType();

        block.setType(Material.PLAYER_HEAD);

        Skull skull = (Skull) block.getState();
        skull.setOwnerProfile(((SkullMeta) event.getItemInHand().getItemMeta()).getOwnerProfile());

        Rotatable skullBlockData = (Rotatable) skull.getBlockData();
        skullBlockData.setRotation(BlockFace.WEST);
        skull.setBlockData(skullBlockData);

        skull.update();

        if (originalMaterial == Material.PLAYER_WALL_HEAD) {
            block.setType(Material.AIR);
            Bukkit.getScheduler().runTaskLater(MCTurtles.plugin, () -> {
                block.setType(Material.PLAYER_HEAD);
                skull.update();
            }, 2);
        }
    }
}
