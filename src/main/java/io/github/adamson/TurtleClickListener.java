package io.github.adamson;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class TurtleClickListener implements Listener {

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getPlayer().isSneaking())
            return;

        Block clickedBlock = event.getClickedBlock();
        TurtleKind turtleKind = TurtleKind.get(clickedBlock);
        if (turtleKind == null)
            return;

        //event.setCancelled(true);
        event.setUseItemInHand(Event.Result.DENY);

        TurtleFactory.createTurtle(clickedBlock).openInventory(event.getPlayer());
    }
}
