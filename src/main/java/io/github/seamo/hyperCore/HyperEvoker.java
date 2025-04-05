package io.github.seamo.hyperCore;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Evoker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class HyperEvoker implements Listener {
    private int healtime;
    private JavaPlugin plugin;
    private final Random random = new Random();

    public HyperEvoker(JavaPlugin plugin, FileConfiguration config) {
        this.healtime = config.getInt("Evoker.heal-time");
        this.plugin = plugin;
    }


    @EventHandler
    public void onEvoker(EntityDeathEvent event) {
        if (event.getEntityType() == EntityType.EVOKER) {
            Evoker evoker = (Evoker) event.getEntity();
            if (random.nextInt(100) <= 90) {


                event.setCancelled(true);
                evoker.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, healtime * 20, 250, false, false));
                evoker.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, healtime * 20, 1, false, false));
                evoker.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, healtime * 20, 1, false, false));
                evoker.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, healtime * 20, 1, false, false));
                totem(evoker);
            }
        }
    }
    public void totem(Evoker evoker) {
        World world = evoker.getWorld();
        world.playSound(evoker.getLocation(), Sound.ITEM_TOTEM_USE, 0.5f, 1f);

        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                if (count >= 25 || evoker.isDead()) {
                    this.cancel(); // Evoker가 죽었거나 반복이 끝나면 종료
                    return;
                }

                // Evoker의 현재 위치에서 파티클 생성
                world.spawnParticle(Particle.TOTEM_OF_UNDYING, evoker.getLocation(), 25);
                count++;
            }
        }.runTaskTimer(plugin, 0L, 1L); // 0틱 시작, 1틱 간격
    }
}