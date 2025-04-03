package io.github.seamo.hyperCore;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HyperEnderman implements Listener {
    private final JavaPlugin plugin;
    private int bindtime;

    public HyperEnderman(JavaPlugin plugin, FileConfiguration config) {
        this.plugin = plugin;
        this.bindtime = config.getInt("EnderMan.bind-time");
    }
    @EventHandler
    public void onEndermanTarget(EntityTargetEvent event) {
        Entity entity = event.getEntity();
        Entity target = event.getTarget();

        if (entity instanceof Enderman && target instanceof LivingEntity) {
            LivingEntity targetEntity = (LivingEntity) target;
            targetEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, bindtime * 20, 10, false, false));
            targetEntity.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, bindtime * 20, 250, false, false));
            targetEntity.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, bindtime * 20, 250, false, false));
        }
    }
}
