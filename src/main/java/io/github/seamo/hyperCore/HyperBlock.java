package io.github.seamo.hyperCore;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;

public class HyperBlock implements Listener {
    private final Random random = new Random();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (random.nextDouble() < 0.1) { // 0.1% chance
            Location location = event.getBlock().getLocation();
            World world = location.getWorld();
            event.setCancelled(true);

            if (world != null) {
                // Strike lightning at the block's location
                world.strikeLightning(location);

                // Spawn TNT at the block's location
                TNTPrimed tnt = (TNTPrimed) world.spawnEntity(location, EntityType.TNT);
                tnt.setFuseTicks(80);
            }
        }
    }
}