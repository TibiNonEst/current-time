package me.tibinonest.plugins.currenttime;

import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.ZoneId;
import java.util.List;

public class GetTimeRunnable extends BukkitRunnable {
    private final List<World> worlds;
    private final ZoneId zone;

    public GetTimeRunnable(List<World> worlds, ZoneId zone) {
        this.worlds = worlds;
        this.zone = zone;
    }

    @Override
    public void run() {
        int time = (int) Math.floor(java.time.LocalTime.now(zone).toSecondOfDay() / 3.6);
        CurrentTime.setTime(worlds, time - 6000);
    }
}
