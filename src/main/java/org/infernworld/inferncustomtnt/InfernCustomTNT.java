package org.infernworld.inferncustomtnt;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.infernworld.inferncustomtnt.commands.Commands;
import org.infernworld.inferncustomtnt.listener.Events;

import java.io.File;

@Getter
public final class InfernCustomTNT extends JavaPlugin {
    private FileConfiguration config;

    @Override
    public void onEnable() {
        this.loadConfig();
        Bukkit.getPluginManager().registerEvents(new Events(this), this);
        Bukkit.getPluginCommand("inferncustomtnt").setExecutor(new Commands(this));
        welcomeMessage();
    }

    @Override
    public void onDisable() {
        welcomeMessage();
    }

    public void loadConfig() {
        final File cfg = new File(getDataFolder(), "config.yml");
        if (!cfg.exists()) {
            saveDefaultConfig();
        }
        config = YamlConfiguration.loadConfiguration(cfg);
    }

    private void welcomeMessage() {
        getLogger().info("§F ");
        getLogger().info(" §x§F§B§0§8§E§1I§x§F§6§0§E§E§2n§x§F§2§1§5§E§3f§x§E§D§1§B§E§4e§x§E§8§2§1§E§5r§x§E§3§2§8§E§6n§x§D§F§2§E§E§7C§x§D§A§3§5§E§8u§x§D§5§3§B§E§8s§x§D§1§4§1§E§9t§x§C§C§4§8§E§Ao§x§C§7§4§E§E§Bm§x§C§2§5§4§E§CT§x§B§E§5§B§E§DN§x§B§9§6§1§E§ET ");
        getLogger().info("§F ");
        getLogger().info("§F Разработчик: §x§F§B§0§8§E§1Великий Егшот §F (Егор Татаркин : EG_SH_NOT) ");
        getLogger().info("§F Для связи: ");
        getLogger().info("§F §x§F§B§0§8§E§1§n§l‖§F Telegram: §x§F§B§0§8§E§1@theegorchik");
        getLogger().info("§F §x§F§B§0§8§E§1§n§l‖§F VK: §x§F§B§0§8§E§1vk.com/egortatarkin");
        getLogger().info("§F §x§F§B§0§8§E§1§n§l‖§F Мой телеграм канал: §x§F§B§0§8§E§1 t.me/egashotina");
        getLogger().info("§F ");
    }
}
