package de.secretcraft.chestspawn;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SetSpawn implements CommandExecutor{
    private final Chestspawn plugin;

    SetSpawn(Chestspawn plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }
        
        if(cmd.getName().equalsIgnoreCase("setspawn")) {
            this.plugin.getConfig().set("spawn.spawnx", player.getLocation().getBlockX());
            this.plugin.getConfig().set("spawn.spawny", player.getLocation().getBlockY());
            this.plugin.getConfig().set("spawn.spawnz", player.getLocation().getBlockZ());
            this.plugin.getConfig().set("spawn.world", "world");
            this.plugin.saveConfig();
            this.plugin.reloadConfig();
            player.sendMessage(ChatColor.DARK_PURPLE + "Spawn wurde gesetzt!");
            player.sendMessage(ChatColor.YELLOW + "X: " + this.plugin.getConfig().getInt("spawn.spawnx") + " Y: " + this.plugin.getConfig().getInt("spawn.spawny") + " Z: " + this.plugin.getConfig().getInt("spawn.spawnz"));
            return true;
        }
        
        if(cmd.getName().equalsIgnoreCase("respawnchests")) {
            if(player.hasPermission("chestspawn.respawnchests")) {
                BlockManager Blockspawner = new BlockManager(plugin);
                Blockspawner.allblockstoair();
                for(int i = 0; i < this.plugin.getConfig().getInt("game.chests.firstamount"); i++) {
                    Blockspawner.firstspawn(1);
                }
                for(int i = 0; i < this.plugin.getConfig().getInt("game.chests.secondamount"); i++) {
                    Blockspawner.firstspawn(2);
                }
                for(int i = 0; i < this.plugin.getConfig().getInt("game.chests.thirdamount"); i++) {
                    Blockspawner.firstspawn(3);
                }
                player.sendMessage("Blöcke wurden neu gespawnt!");
            } else {
                //Nothing / Keine Fehlermeldung benötigt
            }
        }
        return false;
    }
    
}
