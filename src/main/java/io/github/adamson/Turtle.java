package io.github.adamson;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class Turtle {

    public UUID id = UUID.randomUUID();
    private final Inventory inventory;

    public Turtle() {
        this.inventory = Bukkit.createInventory(null, 3 * 9, "Turtle");
        ItemStack item = new ItemStack(Material.COAL);
        ItemMeta meta = item.getItemMeta();
        item.setItemMeta(meta);
        this.inventory.setItem(0, item);
    }

    void openInventory(Player player) {
        player.openInventory(this.inventory);
    }

    public void unload() {

    }
}
