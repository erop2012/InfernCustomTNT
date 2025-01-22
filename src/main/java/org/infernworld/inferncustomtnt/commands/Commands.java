package org.infernworld.inferncustomtnt.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.infernworld.inferncustomtnt.InfernCustomTNT;
import org.infernworld.inferncustomtnt.items.Item;
import org.infernworld.inferncustomtnt.util.Config;
import org.jetbrains.annotations.NotNull;

public class Commands implements CommandExecutor {
    private InfernCustomTNT plugin;
    private Item item;
    private Config cfg;

    public Commands(InfernCustomTNT plugin) {
        this.plugin = plugin;
        this.item = new Item(plugin);
        this.cfg = new Config(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player players = (Player) sender;
        if (args.length == 3 && args[0].equalsIgnoreCase("give")) {

            if (!players.hasPermission("inferncustomtnt.give")) {
                players.sendMessage(cfg.getNoPerms());
                return true;
            }

            Player player = Bukkit.getPlayerExact(args[1]);
            int amount;
            if (player != null) {
                ItemStack tnt = item.item();
                amount = Integer.parseInt(args[2]);
                tnt.setAmount(amount);
                player.getInventory().addItem(tnt);
            } else {
                players.sendMessage(cfg.getPlayerNoFound());
            }
        }
        return true;
    }
}
