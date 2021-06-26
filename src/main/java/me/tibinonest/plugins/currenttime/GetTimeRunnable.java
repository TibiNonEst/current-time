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
        int time = (int) Math.floor(java.time.LocalTime.now(ZoneId.systemDefault()).toSecondOfDay() / 3.6);
        CurrentTime.setTime(CurrentTime.worlds, time - 6000);
    }
}
