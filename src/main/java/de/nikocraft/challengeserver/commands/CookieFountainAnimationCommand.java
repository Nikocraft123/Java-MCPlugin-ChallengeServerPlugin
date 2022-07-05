package de.nikocraft.challengeserver.commands;

import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.animations.CookieFountainAnimation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CookieFountainAnimationCommand implements CommandExecutor {

    //TODO

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Main.getInstance().getAnimationManager().addAnimation(new CookieFountainAnimation(((Player) sender).getLocation()));
        return true;
    }
}
