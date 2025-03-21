package io.github.seamo.hyperCore;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.HashSet;
import java.util.Set;

public class HyperSlime implements Listener {
    private final Set<Slime> noSplitSlimes = new HashSet<>();

    public HyperSlime(HyperCore hyperCore) {
    }
    @EventHandler
    public void onSlimeSplit(SlimeSplitEvent evnet) {
        evnet.setCancelled(true);
    }
    @EventHandler
    public void onSlimeDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Slime slime) {
            if (noSplitSlimes.contains(slime)) {
                noSplitSlimes.remove(slime);
                return; // Do not spawn new Slimes
            }

            // New splitting logic
            int size = slime.getSize();
            for (int i = 0; i < 2; i++) {
                Slime newSlime = slime.getWorld().spawn(slime.getLocation(), Slime.class);
                newSlime.setSize(size);
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Slime slime && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.BLAZE_ROD) {
            Player player = event.getPlayer();
            slime.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, slime.getLocation(), 100);
            slime.getWorld().playSound(slime.getLocation(), Sound.ITEM_TOTEM_USE, 0.5f, 1f);
            noSplitSlimes.add(slime);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1); // Remove one Blaze Rod from the player's inventory
        }
    }
}