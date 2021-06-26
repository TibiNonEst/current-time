package me.tibinonest.plugins.currenttime;

import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;

public final class CurrentTime extends JavaPlugin {

    public static List<World> worlds;
    public static BukkitTask task;
    public static Configuration config;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        config = this.getConfig();

        List<World> configWorlds = new LinkedList<>();
        config.getStringList("worlds").forEach(world -> configWorlds.add(this.getServer().getWorld(world)));
        if (configWorlds.contains(null)) {
            this.getLogger().warning(config.getString("messages.world-not-found"));
            return;
        }
        worlds = configWorlds.isEmpty() ? this.getServer().getWorlds().stream().filter(world -> world.getEnvironment().equals(World.Environment.NORMAL)).toList() : configWorlds;
        worlds.forEach(world -> world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false));

        String configZone = config.getString("time-zone");
        ZoneId zone;
        try {
            assert configZone != null;
            zone = configZone.equals("") ? ZoneId.systemDefault() : ZoneId.of(configZone);
        } catch (Exception e) {
            this.getLogger().warning(config.getString("messages.zone-not-found"));
            return;
        }
        task = new GetTimeRunnable(worlds, zone).runTaskTimer(this, 0, 20);

        List<String> worldNames = new LinkedList<>();
        worlds.forEach(world -> worldNames.add(world.getName()));
        this.getLogger().info("Current time plugin started on world(s) " + String.join(", ", worldNames) + " on time zone " + zone);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void setTime(List<World> worlds, int time) {
        worlds.forEach(world -> world.setTime(fixTime(time)));
    }

    public static int fixTime(int time) {
        return time < 0 ? 24000 + time : time;
    }
}
