package io.github.adamson;

import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Turtle {

    private static final Map<UUID, Turtle> loadedTurtles = new ConcurrentHashMap<>();

    private static ItemStack skull;

    public static ItemStack getSkull() {
        if (skull == null) {
            skull = Utils.createSkull("Turtle", "turtle", CustomHeads.TURTLE);
        }
        return skull.clone();
    }

    public static Turtle spawn(Location location) {
        location = location.add(0.5, -1.25, 0.5);
        ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class, as -> {
            as.setBasePlate(false);
            as.setVisible(false);
            as.setMarker(true);
            as.setCanPickupItems(false);
            as.setGravity(false);
            as.getEquipment().setHelmet(getSkull());
        });

        Slime slime = location.getWorld().spawn(location.add(0, 1.48, 0), Slime.class, s -> {
            s.setAI(false);
            s.setSize(1);
            s.setInvisible(false);
            s.setInvulnerable(false);
            s.getPersistentDataContainer().set(new NamespacedKey(MCTurtles.plugin, "attached-armorstand"), PersistentDataType.STRING, armorStand.getUniqueId().toString());
            s.setRemoveWhenFarAway(false);
        });

        return new Turtle(slime, armorStand);
    }

    public static Turtle get(Entity slime) {
        return loadedTurtles.get(slime.getUniqueId());
    }

    public static void load(Slime slime) {
        new Turtle(slime);
    }

    public static void unloadTurtles() {
        for (Turtle t : loadedTurtles.values())
            t.unload();
    }

    public Slime slime;
    public ArmorStand attachedArmorStand;

    private final Inventory inventory;

    public BukkitTask tickTask;

    protected Turtle(Slime entity) {
        this(entity, getAttachedArmorStand(entity));
    }

    private static ArmorStand getAttachedArmorStand(Slime entity) {
        UUID id = UUID.fromString(entity.getPersistentDataContainer().get(new NamespacedKey(MCTurtles.plugin, "attached-armorstand"), PersistentDataType.STRING));

        for (ArmorStand e : entity.getWorld().getEntitiesByClass(ArmorStand.class))
            if (e.getUniqueId().equals(id))
                return e;

        return null;
    }

    protected Turtle(Slime entity, ArmorStand attachedArmorStand) {
        loadedTurtles.put(entity.getUniqueId(), this);
        System.out.println("loaded turtles: " + loadedTurtles.size());

        this.slime = entity;
        this.attachedArmorStand = attachedArmorStand;

        this.inventory = Bukkit.createInventory(null, 3 * 9, "Turtle");
        ItemStack item = new ItemStack(Material.COAL);
        ItemMeta meta = item.getItemMeta();
        item.setItemMeta(meta);
        this.inventory.setItem(0, item);

        this.startExecution();
    }

    public void startExecution() {
        this.stopExecution();
        this.tickTask = Bukkit.getScheduler().runTaskTimer(MCTurtles.plugin, this::tick, 0, 1);
    }

    public void stopExecution() {
        if (this.tickTask != null)
            this.tickTask.cancel();
    }

    protected void tick() {
        System.out.println("tick");
    }

    void openInventory(Player player) {
        player.openInventory(this.inventory);
    }

    public void destroy() {
        unload();

        slime.getWorld().dropItem(slime.getLocation(), getSkull());
        for (ItemStack itemStack : inventory.getContents())
            if (itemStack != null)
                slime.getWorld().dropItem(slime.getLocation(), itemStack);

        slime.getWorld().playSound(slime.getLocation(), Sound.BLOCK_COPPER_BREAK, 0.5f, 1);
        slime.getWorld().spawnParticle(Particle.ITEM_CRACK, slime.getLocation(), 20, 0.2, 0.2, 0.2, 0.1, new ItemStack(Material.POLISHED_TUFF));
        attachedArmorStand.remove();
        slime.remove();
    }

    public void unload() {
        this.stopExecution();
        loadedTurtles.remove(this.slime.getUniqueId());
        System.out.println("loaded turtles: " + loadedTurtles.size());
    }
}
