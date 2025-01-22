package org.infernworld.inferncustomtnt.util;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

public final class SoundUtil {

    private SoundUtil() {
        throw new UnsupportedOperationException("Неизвестная ошибка util класса!");
    }

    public static void playSound(Location location, Sound sound) {
        if (sound != null) {
            World world = location.getWorld();
            if (world != null) {
                world.playSound(location, sound, 1f, 1f);
            }
        }
    }
}
