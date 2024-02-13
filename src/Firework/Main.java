package Firework;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Main extends JavaPlugin {
    static LinkedList<Player> User=new LinkedList<>();
    static Map<Player,Locs> map=new HashMap<>();
    static Map<Player, BukkitTask> task=new HashMap<>();

    @Override
    public void onEnable() {
        if (Bukkit.getPluginCommand("fireworks") != null) {
            getLogger().info("烟花插件已启动!");
            getLogger().info("作者：PalladiumWolfram");
            Bukkit.getPluginCommand("fireworks").setExecutor(new Commander());
            Bukkit.getPluginManager().registerEvents(new Selector(),this);
            Bukkit.getPluginManager().registerEvents(new Fireworks(),this);
        }
    }
    public void onDisable(){
        getLogger().info("烟花插件已卸载!");
    }
}
