package de.secretcraft.chestspawn;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author StoneCat
 */
public class Chestspawn extends JavaPlugin {

    private static Chestspawn main;
    private SetSpawn myExecutor;
    
    public HashMap<Location, Inventory> chest = new HashMap<Location, Inventory>();
    BlockManager Blockspawner = new BlockManager(this);
    
    @Override
    public void onEnable() {
        Chestspawn.main = this;

        if (!this.getConfig().contains("spawn")) {
            this.getConfig().addDefault("spawn.spawnx", 0);
            this.getConfig().addDefault("spawn.spawny", 0);
            this.getConfig().addDefault("spawn.spawnz", 0);
            this.getConfig().addDefault("spawn.world", "world");
            this.getConfig().addDefault("spawn.noblock", 5);
            this.getConfig().addDefault("game.chests.gamearea", 30);
            this.getConfig().addDefault("game.chests.firstamount", 5);
            this.getConfig().addDefault("game.chests.secondamount", 10);
            this.getConfig().addDefault("game.chests.thirdamount", 15);
            this.getConfig().addDefault("game.chests.material.firstarea.amount", 2);
            this.getConfig().addDefault("game.chests.material.firstarea.0", 50);
            this.getConfig().addDefault("game.chests.material.firstarea.1", 50);
            this.getConfig().addDefault("game.chests.material.secondarea.amount", 2);
            this.getConfig().addDefault("game.chests.material.secondarea.0", 50);
            this.getConfig().addDefault("game.chests.material.secondarea.1", 50);
            this.getConfig().addDefault("game.chests.material.thirdarea.amount", 2);
            this.getConfig().addDefault("game.chests.material.thirdarea.0", 50);
            this.getConfig().addDefault("game.chests.material.thirdarea.1", 50);
            this.getConfig().options().copyDefaults(true);
            this.saveConfig();
            this.reloadConfig();
        }
        Bukkit.getPluginManager().registerEvents(new ChestManager(this), this);
        for(int i = 0; i < this.getConfig().getInt("game.chests.firstamount"); i++) {
            Blockspawner.firstspawn(1);
        }
        for(int i = 0; i < this.getConfig().getInt("game.chests.secondamount"); i++) {
            Blockspawner.firstspawn(2);
        }
        for(int i = 0; i < this.getConfig().getInt("game.chests.thirdamount"); i++) {
            Blockspawner.firstspawn(3);
        }
        myExecutor = new SetSpawn(this);
        getCommand("setspawn").setExecutor(myExecutor);
        getCommand("respawnchests").setExecutor(myExecutor);
    }

    @Override
    public void onDisable() {
        Blockspawner.allblockstoair();
        this.saveConfig();
    }
}
