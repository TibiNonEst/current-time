package me.tibinonest.plugins.currenttime;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.ZoneId;

public class GetTimeRunnable extends BukkitRunnable {

    private final JavaPlugin plugin;

    public GetTimeRunnable(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        String configZone = plugin.getConfig().getString("time-zone");
        ZoneId zone;
        try {
            assert configZone != null;
            zone = configZone.equals("") ? ZoneId.systemDefault() : ZoneId.of(configZone);
        } catch (Exception e) {
            plugin.getLogger().warning(plugin.getConfig().getString("messages.zone-not-found"));
            this.cancel();
            return;
        }
        int time = (int) Math.floor(java.time.LocalTime.now(zone).toSecondOfDay() / 3.6);
        CurrentTime.setTime(CurrentTime.worlds, time - 6000);
    }
}
