package io.github.adamson;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Turtle {

    void openInventory(Player player) {
        Inventory inv = Bukkit.createInventory(player, 3 * 9, "Turtle");

        ItemStack item = new ItemStack(Material.COAL);
        ItemMeta meta = item.getItemMeta();
        item.setItemMeta(meta);
        inv.setItem(0, item);
        
        player.openInventory(inv);
    }
}
