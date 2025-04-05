package HyperCore.WorldBorder;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HyperWorldBorderCommand implements CommandExecutor {
    private final HyperWorldBorder hyperWorldBorder;

    public HyperWorldBorderCommand(HyperWorldBorder hyperWorldBorder) {
        this.hyperWorldBorder = hyperWorldBorder;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return false;
        }

        Player player = (Player) sender;
        hyperWorldBorder.addPlayer(player);

        // Set the world border size to 10 and center it on the player
        player.getWorld().getWorldBorder().setCenter(player.getLocation());
        player.getWorld().getWorldBorder().setSize(10);

        // Start the border movement
        hyperWorldBorder.startBorderMovement();

        return true;
    }
}