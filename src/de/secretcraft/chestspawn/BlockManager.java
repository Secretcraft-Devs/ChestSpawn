package de.secretcraft.chestspawn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BlockManager {
    private Chestspawn plugin;

    BlockManager(Chestspawn plugin) {
        this.plugin = plugin;
    }
    
    public static int myRandom(int limit) {
	Random rnd = new Random();
        return rnd.nextInt(limit);
    }
    
    public void firstspawn(int todo) {
        int gamei = this.plugin.getConfig().getInt("game.chests.gamearea");
        int areai = gamei / 3;
        int locX = myRandom(areai * 2) - areai;
        if(locX == 0) //Wenn Random 0 neu machen
            locX = myRandom(areai * 2) - areai;
        if(locX == 0) //Wenn Random wieder 0 nochmal neu
            locX = myRandom(areai * 2) - areai;
        int locY = 50;
        int locZ = myRandom(areai * 2) - areai;
        if(locZ == 0) //Wenn Random 0 neu machen
            locZ = myRandom(areai * 2) - areai;
        if(locZ == 0) //Wenn Random wieder 0 nochmal neu
            locZ = myRandom(areai * 2) - areai;
        
        if(todo == 2) {
            if(locX > 0)
                locX = locX + areai;
            if(locX < 0)
                locX = locX - areai;
            if(locZ > 0)
                locZ = locZ + areai;
            if(locZ < 0)
                locZ = locZ - areai;
        }
        if(todo == 3) {
            if(locX > 0)
                locX = locX + (areai * 2);
            if(locX < 0)
                locX = locX - (areai * 2);
            if(locZ > 0)
                locZ = locZ + (areai * 2);
            if(locZ < 0)
                locZ = locZ - (areai * 2);
        }
        locX = locX + this.plugin.getConfig().getInt("spawn.spawnx");;
        locZ = locZ + this.plugin.getConfig().getInt("spawn.spawny");
        if(locX > 0) 
            locX = locX + this.plugin.getConfig().getInt("spawn.noblock");
        if(locX < 0)
            locX = locX - this.plugin.getConfig().getInt("spawn.noblock");
        if(locZ > 0)
            locZ = locZ + this.plugin.getConfig().getInt("spawn.noblock");
        if(locZ < 0)
            locZ = locZ - this.plugin.getConfig().getInt("spawn.noblock");
        
        
        Location spawnlocation = new Location(plugin.getServer().getWorld(plugin.getConfig().getString("spawn.world")), locX, locY, locZ);
        locY = spawnlocation.getWorld().getHighestBlockYAt(locX, locZ);
        Location location = new Location(plugin.getServer().getWorld(plugin.getConfig().getString("spawn.world")), locX, locY, locZ);
        location.getBlock().setType(Material.REDSTONE_BLOCK);
        plugin.getLogger().log(Level.INFO , "Block gesetzt an:" + locX + " , " + locY + " , " + locZ);
        
        Random rnd = new Random();
        int n = 1;
        n = rnd.nextInt(6);
        Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST);
        List<ItemStack> items = new ArrayList<ItemStack>();

        if(todo == 1) {
            for(int i = 0; i < this.plugin.getConfig().getInt("game.chests.material.firstarea.amount"); i++) {
                //items.add(new ItemStack(Material.getMaterial(this.plugin.getConfig().getString("game.chests.material.firstarea." + i).toUpperCase())));
                items.add(new ItemStack(this.plugin.getConfig().getInt("game.chests.material.firstarea." + i)));
            } 
        }
        if(todo == 2) {
            for(int i = 0; i < this.plugin.getConfig().getInt("game.chests.material.secondarea.amount"); i++) {
                //items.add(new ItemStack(Material.getMaterial(this.plugin.getConfig().getString("game.chests.material.secondarea." + i).toUpperCase())));
                items.add(new ItemStack(this.plugin.getConfig().getInt("game.chests.material.secondarea." + i)));
            }
        }
        if(todo == 3) {
            for(int i = 0; i < this.plugin.getConfig().getInt("game.chests.material.thirdarea.amount"); i++) {
                //items.add(new ItemStack(Material.getMaterial(this.plugin.getConfig().getString("game.chests.material.thirdarea." + i).toUpperCase())));
                items.add(new ItemStack(this.plugin.getConfig().getInt("game.chests.material.thirdarea." + i)));
            }
        }
        
        while (n != 0) {
            n--;
            Random rnd2 = new Random();
            Random rnd3 = new Random();

            int n3 = rnd3.nextInt(27);
            int n2 = rnd2.nextInt(items.size());

            inv.setItem(n3, (ItemStack)items.get(n2));
        }
        this.plugin.chest.put(location, inv);
    }
    
    public void allblockstoair() {
        for(Location loc : this.plugin.chest.keySet()) {
            loc.getBlock().setType(Material.AIR);
        }
    }
    
}
