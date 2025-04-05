package HyperCore.Player;

import HyperCore.Loader.ConfigLoader.ConfigManager;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class HyperDrop implements Listener {
    private final boolean r;
    private final int min;
    private final int max;
    private final double cancelChance;
    private final Random random = new Random();

    private final Set<Block> placedBlocks = new HashSet<>();  // 플레이어가 설치한 블록을 저장할 Set

    public HyperDrop(JavaPlugin plugin) {
        ConfigManager configManager = new ConfigManager(plugin, "Drop.yml");
        FileConfiguration config = configManager.getConfig();
        this.r = config.getBoolean("random");
        this.min = config.getInt("min");
        this.max = config.getInt("max");
        this.cancelChance = config.getDouble("break-cancel-chance"); // 0.5 = 50%
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        placedBlocks.add(event.getBlock());
    }

    @EventHandler
    public void onDrop(BlockDropItemEvent event) {
        Block block = event.getBlock();

        if (placedBlocks.contains(block)) return;

        if (random.nextDouble() < cancelChance / 100) {
            block.getWorld().spawnParticle(Particle.SMOKE, block.getLocation().add(0.5, 0.5, 0.5), 10, 0.2, 0.2, 0.2, 0);
            block.getWorld().playSound(block.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 1.0f);
            event.setCancelled(true);
            return;
        }

        List<Item> droppedItems = event.getItems();
        ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();
        boolean hasSilkTouch = tool.containsEnchantment(org.bukkit.enchantments.Enchantment.SILK_TOUCH);

        for (Item item : droppedItems) {
            ItemStack originalStack = item.getItemStack();
            int baseAmount = originalStack.getAmount();

            if (hasSilkTouch) {
                // 실크 터치: 드랍 그대로 유지 (선택적으로 배수 적용 가능)
                int multiplier = r ? random.nextInt((max - min) + 1) + min : min;
                int finalAmount = baseAmount * multiplier;

                ItemStack dropStack = new ItemStack(originalStack.getType(), finalAmount);
                item.getWorld().dropItem(item.getLocation(), dropStack);
            } else {
                // 행운 적용
                int fortuneLevel = tool.containsEnchantment(Enchantment.FORTUNE)
                        ? tool.getEnchantmentLevel(Enchantment.FORTUNE)
                        : 0;

                int fortuneBonus = 0;
                if (fortuneLevel > 0) {
                    fortuneBonus = random.nextInt(fortuneLevel + 2) - 1;
                    if (fortuneBonus < 0) fortuneBonus = 0;
                }

                int totalAmount = baseAmount + fortuneBonus;
                int multiplier = r ? random.nextInt((max - min) + 1) + min : min;
                int finalAmount = totalAmount * multiplier;

                ItemStack dropStack = new ItemStack(originalStack.getType(), finalAmount);
                item.getWorld().dropItem(item.getLocation(), dropStack);
            }

            item.remove(); // 기존 드랍 제거
        }
    }
}