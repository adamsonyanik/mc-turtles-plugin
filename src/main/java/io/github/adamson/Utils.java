package io.github.adamson;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

import java.net.URL;
import java.util.UUID;

public class Utils {

    public static ItemStack createSkull(String name, String id, URL texture) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);

        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + name);

        PlayerProfile customHeadPlayerProfile = Bukkit.createPlayerProfile(UUID.randomUUID(), "mc-turtles-plugin-" + id);
        customHeadPlayerProfile.getTextures().setSkin(texture);
        customHeadPlayerProfile.update();

        meta.setOwnerProfile(customHeadPlayerProfile);
        head.setItemMeta(meta);

        return head;
    }
}
