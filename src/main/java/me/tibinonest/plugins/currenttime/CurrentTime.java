package me.tibinonest.plugins.currenttime;

import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.time.ZoneId;
import java.util.List;

public final class CurrentTime extends JavaPlugin {

    public static List<World> worlds;
    public static BukkitTask task;

    @Override
    public void onEnable() {
        worlds = this.getServer().getWorlds().stream().filter(world -> world.getEnvironment().equals(World.Environment.NORMAL)).toList();
        worlds.forEach(world -> world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false));
        task = new GetTimeRunnable(this).runTaskTimer(this, 0, 20);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void setTime(List<World> worlds, int time) {
        worlds.forEach(world -> world.setTime(time));
    }

    public static int fixTime(int time) {
        return time < 0 ? 24000 + time : time;
    }
}
