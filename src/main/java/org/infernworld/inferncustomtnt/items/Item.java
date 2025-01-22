package org.infernworld.inferncustomtnt.items;

import lombok.val;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.infernworld.inferncustomtnt.InfernCustomTNT;
import org.infernworld.inferncustomtnt.util.Config;

public class Item {
    private final Config cfg;
    private final InfernCustomTNT plugin;

    public Item(InfernCustomTNT plugin) {
        this.plugin = plugin;
        this.cfg = new Config(plugin);
    }

    public ItemStack item() {
        val material = cfg.getMateiral();
        val lore = cfg.getLore();
        val name = cfg.getName();

        ItemStack item = new ItemStack(Material.valueOf(material));
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(lore);
        if (cfg.isGlow()) {
            meta.addEnchant(Enchantment.LUCK, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        item.setItemMeta(meta);
        return item;
    }
}
