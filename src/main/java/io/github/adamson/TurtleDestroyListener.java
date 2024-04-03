package io.github.adamson;


import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

public class TurtleDestroyListener implements Listener {

    @EventHandler
    public void onExplode(BlockExplodeEvent event) {
        for (Block b : event.blockList())
            onDestroy(b);
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        for (Block b : event.blockList())
            onDestroy(b);
    }

    @EventHandler
    public void onBurn(BlockBurnEvent event) {
        onDestroy(event.getBlock());
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        onDestroy(event.getBlock());
    }

    @EventHandler
    public void onBlockPiston(BlockPistonExtendEvent event) {
        for (Block b : event.getBlocks())
            onDestroy(b);
    }

    @EventHandler
    public void onBlockPiston(BlockPistonRetractEvent event) {
        for (Block b : event.getBlocks())
            onDestroy(b);
    }

    void onDestroy(Block block) {
        TurtleKind kind = TurtleKind.get(block);
        if (kind == null)
            return;

        PlayerProfile ownerProfile = ((Skull) block.getState()).getOwnerProfile();

        Location location = block.getLocation();
        World world = location.getWorld();

        block.setType(Material.AIR);

        ItemStack head = new ItemStack(Material.PLAYER_HEAD);

        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + kind.displayName);

        meta.setOwnerProfile(ownerProfile);
        head.setItemMeta(meta);

        world.dropItem(location.add(0.5, 0, 0.5), head);
    }
}
