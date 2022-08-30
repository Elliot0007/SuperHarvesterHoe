package me.c0dev.superharvesterhoe;

import me.c0dev.superharvesterhoe.HarvesterHoe.Commands.commands;
import me.c0dev.superharvesterhoe.HarvesterHoe.Commands.getUUID;
import me.c0dev.superharvesterhoe.HarvesterHoe.Commands.wipeConfig;
import me.c0dev.superharvesterhoe.HarvesterHoe.Events.event;
import me.c0dev.superharvesterhoe.HarvesterHoe.Files.DataManager;
import me.c0dev.superharvesterhoe.HarvesterHoe.Item.items;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {


    public FileConfiguration config1 = this.getConfig();

    public static Plugin instance;
    public DataManager data;
    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("===============================");
        getLogger().info("===============================");
        getLogger().info("===============================");
        getLogger().info("              C0deV            ");
        getLogger().info("   Premium HarvesterHoe Plugin ");
        getLogger().info("===============================");
        getLogger().info("===============================");
        instance = this;
        items.init();

        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
            if (player.isOp()) {
                player.sendMessage(ChatColor.GOLD + "[!] Harvester Hoe is online!");
            }
        });
        getServer().getPluginManager().registerEvents(new event(), this);
        getServer().getPluginManager().registerEvents(new HoeUpgrades(), this);

        getCommand("giveSHarvesterHoe").setExecutor(new commands());
        getCommand("getUUID").setExecutor(new getUUID());
        getCommand("wipeConfig").setExecutor(new wipeConfig());


        // PHH Config
        this.config1.options().copyDefaults(true);
        saveConfig();



        // Harvester Hoe
        this.data = new DataManager(this);
        data.getConfig().addDefault("max_upgrade_lvl", 5);
        data.getConfig().options().copyDefaults(true);
        data.saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        // Harvester Hoe
        // Test
        this.data = new DataManager(this);
        data.saveDefaultConfig();
        data.saveConfig();


    }
    public static Plugin getInstance()
    {
        return instance;
    }
}

