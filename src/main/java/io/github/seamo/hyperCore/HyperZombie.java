package io.github.seamo.hyperCore;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.Random;

public class HyperZombie implements Listener {
    private final FileConfiguration config;
    private final Random random = new Random();

    public HyperZombie(FileConfiguration config) {
        this.config = config;
    }

    @EventHandler
    public void onZombieDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Zombie zombie)) return;

        // Attribute.SCALE 가져오기
        AttributeInstance scaleAttr = zombie.getAttribute(Attribute.SCALE);
        if (scaleAttr == null) return;

        double currentScale = scaleAttr.getBaseValue();
        double damage = event.getDamage();

        // 데미지만큼 스케일 감소 (비율은 조정 가능)
        double newScale = Math.max(0.000001, currentScale - damage * 0.05); // 너무 작아지지 않게 제한
        scaleAttr.setBaseValue(newScale);
    }



    @EventHandler
    public void onZombieSpawn(EntitySpawnEvent event) {
        if (event.getEntityType() == EntityType.ZOMBIE) {
            double health = config.getDouble("Zombie.attri-health");
            double damage = config.getDouble("Zombie.attri-damage");
            Zombie zombie = (Zombie) event.getEntity();
            zombie.getAttribute(Attribute.MAX_HEALTH).setBaseValue(health);
            zombie.setHealth(health);
            zombie.setNoDamageTicks(0);
            zombie.getAttribute(Attribute.ATTACK_DAMAGE).setBaseValue(damage);
            zombie.getAttribute(Attribute.KNOCKBACK_RESISTANCE).setBaseValue(1.0); // Set knockback resistance to 1.0 (100%)
        }
    }
}