package io.github.adamson;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class TurtleClickListener implements Listener {

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getPlayer().isSneaking())
            return;

        Block clickedBlock = event.getClickedBlock();
        Turtle turtle = TurtleFactory.getTurtle(clickedBlock);
        if (turtle == null)
            return;

        event.setUseItemInHand(Event.Result.DENY);
        turtle.openInventory(event.getPlayer());
    }

    @EventHandler
    public void onPlayerInteractAtEntityEvent(PlayerInteractAtEntityEvent event) {
        System.out.println(event.getRightClicked().getEntityId());
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event) {
        if (event.getEntity().getType() != EntityType.SLIME)
            return;

        if (!event.getEntity().getPersistentDataContainer().has(new NamespacedKey(MCTurtles.plugin, "attached-armorstand")))
            return;

        event.setCancelled(true);

        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK || event.getDamageSource().getCausingEntity() == null || !event.getDamageSource().getCausingEntity().getType().equals(EntityType.PLAYER))
            return;

        UUID id = UUID.fromString(event.getEntity().getPersistentDataContainer().get(new NamespacedKey(MCTurtles.plugin, "attached-armorstand"), PersistentDataType.STRING));
        Entity attachedArmorStand = null;
        for (Entity e : event.getEntity().getWorld().getEntities()) {
            if (e.getUniqueId().equals(id)) {
                attachedArmorStand = e;
                break;
            }
        }
        event.getEntity().getWorld().playSound(event.getEntity().getLocation(), Sound.BLOCK_COPPER_BREAK, 0.5f, 1);
        event.getEntity().getWorld().spawnParticle(Particle.ITEM_CRACK, event.getEntity().getLocation(), 20, 0.2, 0.2, 0.2, 0.1, new ItemStack(Material.POLISHED_TUFF));
        attachedArmorStand.remove();
        event.getEntity().remove();
    }

    @EventHandler
    public void onEntityDamageEvent(EntityTargetEvent event) {
        if (event.getTarget() == null || event.getTarget().getType() != EntityType.SLIME)
            return;

        if (!event.getTarget().getPersistentDataContainer().has(new NamespacedKey(MCTurtles.plugin, "attached-armorstand")))
            return;

        event.setCancelled(true);
    }
}
