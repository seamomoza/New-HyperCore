package io.github.seamo.hyperCore;

import org.bukkit.Bukkit;
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
        if (args.length != 1) {
            sender.sendMessage("Usage: /wb <player>");
            return false;
        }

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage("Player not found.");
            return false;
        }

        hyperWorldBorder.addPlayer(player);
        sender.sendMessage("World border plugin applied to " + player.getName());
        return true;
    }
}