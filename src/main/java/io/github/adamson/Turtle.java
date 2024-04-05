package io.github.adamson;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Turtle {

    private static final Map<UUID, Turtle> loadedTurtles = new HashMap<>();

    public static Turtle spawn(Location location) {
        ArmorStand as = location.getWorld().spawn(location, ArmorStand.class);

        as.setBasePlate(false);
        as.setVisible(false);
        as.setMarker(true);
        as.setCanPickupItems(false);
        as.setGravity(false);
        as.getEquipment().setHelmet(new ItemStack(Material.PLAYER_HEAD));

        Slime slime = (Slime) location.getWorld().spawnEntity(location.add(0, 1.48, 0), EntityType.SLIME, false);
        slime.setAI(false);
        slime.setSize(1);
        slime.setInvisible(false);
        slime.setInvulnerable(false);
        slime.getPersistentDataContainer().set(new NamespacedKey(MCTurtles.plugin, "attached-armorstand"), PersistentDataType.STRING, as.getUniqueId().toString());
        slime.setRemoveWhenFarAway(false);

        return new Turtle(slime, as);
    }

    public static Turtle get(Entity slime) {
        return loadedTurtles.get(slime.getUniqueId());
    }

    public static void unloadTurtles() {
        for (Turtle t : loadedTurtles.values())
            t.unload();
    }

    public Slime slime;
    public ArmorStand attachedArmorStand;

    private final Inventory inventory;

    public Turtle(Slime entity) {
        this(entity, getAttachedArmorStand(entity));
    }

    private static ArmorStand getAttachedArmorStand(Slime entity) {
        UUID id = UUID.fromString(entity.getPersistentDataContainer().get(new NamespacedKey(MCTurtles.plugin, "attached-armorstand"), PersistentDataType.STRING));

        for (ArmorStand e : entity.getWorld().getEntitiesByClass(ArmorStand.class))
            if (e.getUniqueId().equals(id))
                return e;

        return null;
    }

    public Turtle(Slime entity, ArmorStand attachedArmorStand) {
        loadedTurtles.put(entity.getUniqueId(), this);
        System.out.println("loaded turtles: " + loadedTurtles.size());

        this.slime = entity;
        this.attachedArmorStand = attachedArmorStand;

        this.inventory = Bukkit.createInventory(null, 3 * 9, "Turtle");
        ItemStack item = new ItemStack(Material.COAL);
        ItemMeta meta = item.getItemMeta();
        item.setItemMeta(meta);
        this.inventory.setItem(0, item);
    }

    void openInventory(Player player) {
        player.openInventory(this.inventory);
    }

    public void destroy() {
        unload();

        /*TurtleKind kind = TurtleKind.get(block);
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

        world.dropItem(location.add(0.5, 0, 0.5), head);*/

        slime.getWorld().playSound(slime.getLocation(), Sound.BLOCK_COPPER_BREAK, 0.5f, 1);
        slime.getWorld().spawnParticle(Particle.ITEM_CRACK, slime.getLocation(), 20, 0.2, 0.2, 0.2, 0.1, new ItemStack(Material.POLISHED_TUFF));
        attachedArmorStand.remove();
        slime.remove();
    }

    public void unload() {
        loadedTurtles.remove(this.slime.getUniqueId());
        System.out.println("loaded turtles: " + loadedTurtles.size());
    }
}
