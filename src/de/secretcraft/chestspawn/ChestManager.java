package de.secretcraft.chestspawn;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ChestManager implements Listener {

    private final Chestspawn plugin;
    private Location lastlocation;

    public ChestManager(Chestspawn plugin) {
        this.plugin = plugin;
    }

    public static boolean IsEmpty(Inventory inv) {
        for (ItemStack item : inv.getContents()) {
            if (item != null) {
                if (item.getType() != Material.AIR) {
                    return false;
                }
            }
        }
        return true;
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.REDSTONE_BLOCK) {
            if (this.plugin.chest.containsKey(e.getClickedBlock().getLocation())) {
                p.openInventory((Inventory) this.plugin.chest.get(e.getClickedBlock().getLocation()));
                Inventory inv = this.plugin.chest.get(e.getClickedBlock().getLocation());
                this.lastlocation = e.getClickedBlock().getLocation();
            }
        }
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        if (this.plugin.chest.containsKey(this.lastlocation)) {
            Inventory inv = this.plugin.chest.get(this.lastlocation);
            if (IsEmpty(inv)) {
                this.plugin.chest.remove(this.lastlocation);
                lastlocation.getBlock().setType(Material.AIR);
                BlockManager blockManager = new BlockManager(plugin);
                int gamei = this.plugin.getConfig().getInt("game.chests.gamearea");
                int noblock = this.plugin.getConfig().getInt("spawn.noblock");
                int spawnx = this.plugin.getConfig().getInt("spawn.spawnx");
                if (spawnx != 0) {
                    if (spawnx > 0) {
                        noblock = spawnx + noblock;
                    }
                    if (spawnx < 0) {
                        noblock = spawnx - noblock;
                    }
                }
                int areai = gamei / 3;
                if (this.lastlocation.getBlockX() > noblock) {
                    if (this.lastlocation.getBlockX() < (areai + noblock)) {
                        blockManager.firstspawn(1);
                    }
                    if (this.lastlocation.getBlockX() < ((areai * 2) + noblock) && this.lastlocation.getBlockX() > (areai + noblock)) {
                        blockManager.firstspawn(2);
                    }
                    if (this.lastlocation.getBlockX() < ((areai * 3) + noblock) && this.lastlocation.getBlockX() > ((areai * 2) + noblock)) {
                        blockManager.firstspawn(3);
                    }
                } else {
                    areai = areai - (areai * 2);
                    if (this.lastlocation.getBlockX() > (areai - noblock)) {
                        blockManager.firstspawn(1);
                    }
                    if (this.lastlocation.getBlockX() > ((areai * 2) - noblock) && this.lastlocation.getBlockX() < (areai - noblock)) {
                        blockManager.firstspawn(2);
                    }
                    if (this.lastlocation.getBlockX() > ((areai * 3) - noblock) && this.lastlocation.getBlockX() < ((areai * 2) - noblock)) {
                        blockManager.firstspawn(3);
                    }
                }
            }
        }
    }

    @EventHandler (priority = EventPriority.LOW)
    public void onBreakBlock(BlockBreakEvent e) {
        if (e.getBlock().getType() == Material.REDSTONE_BLOCK) {
            e.setCancelled(true);
        }
    }
}
