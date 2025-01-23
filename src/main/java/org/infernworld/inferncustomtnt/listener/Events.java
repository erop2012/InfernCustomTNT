package org.infernworld.inferncustomtnt.listener;

import lombok.val;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.infernworld.inferncustomtnt.InfernCustomTNT;
import org.infernworld.inferncustomtnt.items.Item;
import org.infernworld.inferncustomtnt.util.Config;
import org.infernworld.inferncustomtnt.util.SoundUtil;

import java.util.Random;

public class Events implements Listener {
    private Item items;
    private InfernCustomTNT plugin;
    private Config cfg;

    public Events(InfernCustomTNT plugin) {
        this.plugin = plugin;
        this.items = new Item(plugin);
        this.cfg = new Config(plugin);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        ItemStack item = e.getItemInHand();

        if (item != null && item.isSimilar(items.item())) {
            Block placedBlock = e.getBlockPlaced();
            placedBlock.setType(Material.TNT);
            placedBlock.setMetadata("InfernCustomTNT", new FixedMetadataValue(plugin, true));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e) {
        Block block = e.getClickedBlock();
        Player player = e.getPlayer();
        if (block != null && e.getAction() == Action.RIGHT_CLICK_BLOCK && player.getInventory().getItemInHand() != null) {
            ItemStack itemInHand = player.getInventory().getItemInHand();

            if (itemInHand.getType() == Material.FLINT_AND_STEEL) {
                if (block != null && block.getType() == Material.TNT) {
                    if (block.hasMetadata("InfernCustomTNT")) {
                        e.setCancelled(true);
                        TNTPrimed tntPrimed = block.getWorld().spawn(block.getLocation().add(0.5, 0.5, 0.5), TNTPrimed.class);
                        tntPrimed.setMetadata("InfernCustomTNT", new FixedMetadataValue(plugin, true));
                        block.setType(Material.AIR);
                        tntPrimed.setFuseTicks(80);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onExplode(EntityExplodeEvent e) {
        Entity entity = e.getEntity();
        if (entity instanceof TNTPrimed) {
            TNTPrimed tntPrimed = (TNTPrimed) entity;

            if (tntPrimed.hasMetadata("InfernCustomTNT")) {
                e.setCancelled(true);
                Location loc = tntPrimed.getLocation();
                loc.getWorld().createExplosion(loc, 3.0F, false, false);
                for (int x = -2; x <= 2; x++) {
                    for (int y = -2; y <= 2; y++) {
                        for (int z = -2; z <= 2; z++) {
                            Block block = loc.clone().add(x, y, z).getBlock();
                            if (block.getType() == Material.SPAWNER) {
                                CreatureSpawner cs = (CreatureSpawner) block.getState();
                                EntityType spawnerType = cs.getSpawnedType();
                                Random random = new Random();
                                if (random.nextInt(100) < cfg.getSpawnerChance()) {
                                    ItemStack dropItem = makeSpawnerItem(spawnerType);
                                    block.getWorld().dropItemNaturally(block.getLocation(), dropItem);
                                    block.setType(Material.AIR);
                                }
                            }
                        }
                    }
                }
                SoundUtil.playSound(loc, cfg.getSoundBreackParsed());
            }
        }
    }

    @EventHandler
    public void onBreakTNT(BlockBreakEvent e) {
        Block block = e.getBlock();
        if (block != null && block.getType() == Material.TNT) {
            if (block.hasMetadata("InfernCustomTNT")) {
                Location loc = e.getBlock().getLocation();
                e.setDropItems(false);
                block.getWorld().dropItemNaturally(loc, items.item());
            }
        }
    }

    private ItemStack makeSpawnerItem(EntityType spawnerType) {
        ItemStack spawnerItem = new ItemStack(Material.SPAWNER);
        BlockStateMeta meta = (BlockStateMeta) spawnerItem.getItemMeta();
        if (meta != null) {
            CreatureSpawner spawner = (CreatureSpawner) meta.getBlockState();
            spawner.setSpawnedType(spawnerType);
            meta.setBlockState(spawner);
            meta.setDisplayName(cfg.getSpawnerName().replaceAll("%entity", String.valueOf(spawnerType)));
            spawnerItem.setItemMeta(meta);
        }
        return spawnerItem;
    }
}
