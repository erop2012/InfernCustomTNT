package org.infernworld.inferncustomtnt.util;

import lombok.Getter;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.infernworld.inferncustomtnt.InfernCustomTNT;

import java.util.ArrayList;
import java.util.List;

public final class Config {

    private InfernCustomTNT plugin;
    @Getter
    private String mateiral, name, noPerms, soundBreack, spawnerName, playerNoFound;
    @Getter
    private int spawnerChance;
    @Getter
    private boolean isGlow;
    @Getter
    private Sound soundBreackParsed;
    @Getter
    private List<String> lore = new ArrayList<>();

    public Config(InfernCustomTNT plugin) {
        this.plugin = plugin;
        this.setupConfig();
    }

    public void setupConfig() {
        FileConfiguration cfg = plugin.getConfig();

        val item = "Item.";
        val message = "message.";
        val settings = "settings.";

        this.mateiral = cfg.getString(item + "material");
        this.name = Color.colorize(cfg.getString(item + "name"));
        this.lore = cfg.getStringList(item + "lore").stream().map(Color::colorize).toList();
        this.spawnerChance = cfg.getInt(settings + "spawner-drop-chance");
        this.soundBreack = cfg.getString(settings + "soundBreack");
        this.spawnerName = Color.colorize(cfg.getString(settings + "spawner-name"));
        this.noPerms = Color.colorize(cfg.getString(message + "no-perms"));
        this.isGlow = cfg.getBoolean(item + "ench");
        this.playerNoFound = Color.colorize(cfg.getString(message + "player-no-found"));

        if (this.soundBreack != null) {
            try {
                this.soundBreackParsed = Sound.valueOf(this.soundBreack);
            } catch (IllegalArgumentException e) {
                this.soundBreackParsed = null;
                Bukkit.getLogger().warning("Звук " + this.soundBreack + " не найден. Проверьте конфиг!");
            }
        }
    }
}
