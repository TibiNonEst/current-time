package me.tibinonest.plugins.currenttime;

import org.bstats.bukkit.Metrics;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public final class CurrentTime extends JavaPlugin {

    public static List<World> worlds;
    public static BukkitTask task;
    public static Configuration config;


    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        handleEnable();

        this.getCommand("currenttime").setExecutor(new ReloadCommand(this));

        Metrics metrics = new Metrics(this, 11982);
    }

    @Override
    public void onDisable() {
        if (task != null) task.cancel();
    }

    public boolean handleEnable() {
        config = this.getConfig();

        List<World> configWorlds = new LinkedList<>();
        config.getStringList("worlds").forEach(world -> configWorlds.add(this.getServer().getWorld(world)));
        if (configWorlds.contains(null)) {
            this.getLogger().warning(config.getString("messages.world-not-found"));
            return false;
        }
        worlds = configWorlds.isEmpty() ? this.getServer().getWorlds().stream().filter(world -> world.getEnvironment().equals(World.Environment.NORMAL)).collect(Collectors.toList()) : configWorlds;
        worlds.forEach(world -> world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false));

        String configZone = config.getString("time-zone");
        ZoneId zone;
        try {
            assert configZone != null;
            zone = configZone.equals("") ? ZoneId.systemDefault() : ZoneId.of(configZone);
        } catch (Exception e) {
            this.getLogger().warning(config.getString("messages.zone-not-found"));
            return false;
        }
        task = new GetTimeRunnable(worlds, zone).runTaskTimer(this, 0, 20);

        List<String> worldNames = new LinkedList<>();
        worlds.forEach(world -> worldNames.add(world.getName()));
        this.getLogger().info("Current time plugin started on world(s) " + String.join(", ", worldNames) + " on time zone " + zone);

        return true;
    }

    public void handleReload(CommandSender sender) {
        if (task != null) task.cancel();

        this.getLogger().info("Reloading...");
        this.reloadConfig();
        boolean success = handleEnable();
        sender.sendMessage("CurrentTime " + (success ? "reloaded!" : "could not be reloaded."));
    }

    public static void setTime(List<World> worlds, int time) {
        worlds.forEach(world -> world.setTime(fixTime(time)));
    }

    public static int fixTime(int time) {
        return time < 0 ? 24000 + time : time;
    }
}
