// src/main/java/io/github/seamo/hyperCore/HyperBlock.java
package io.github.seamo.hyperCore;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;

public class HyperBlock implements Listener {
    private final Random random = new Random();
    private final double lightningChance;
    private final int tntFuseTicks;

    public HyperBlock(FileConfiguration config) {
        this.lightningChance = config.getDouble("block.lightning-chance");
        this.tntFuseTicks = config.getInt("block.tnt-fuse-ticks");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (random.nextDouble() < lightningChance) {
            Location location = event.getBlock().getLocation();
            World world = location.getWorld();
            event.setCancelled(true);

            if (world != null) {
                world.strikeLightning(location);
                TNTPrimed tnt = (TNTPrimed) world.spawnEntity(location, EntityType.TNT);
                tnt.setFuseTicks(tntFuseTicks);
            }
        }
    }
}