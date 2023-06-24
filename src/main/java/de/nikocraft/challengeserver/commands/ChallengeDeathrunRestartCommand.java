//PACKAGE
package de.nikocraft.challengeserver.commands;


//IMPORTS

import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.challenges.deathrun.DeathrunChallenge;
import de.nikocraft.challengeserver.challenges.deathrun.DeathrunScoreboard;
import de.nikocraft.challengeserver.tablists.TablistManager;
import de.nikocraft.challengeserver.utils.CommandUtils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


//IP COMMAND CLASS
public class ChallengeDeathrunRestartCommand implements CommandExecutor, TabCompleter {

    //OVERRIDE METHODS

    //Called, if the command send
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //If the sender is not a player
        if (!(sender instanceof Player)) {
            //Send message to sender
            sender.sendMessage(CommandUtils.getConsolePrefix() + "You must be a player to use this command!");

            //Return false
            return false;
        }

        //Check for challenge
        if (!(Main.getInstance().getChallengeManager().getActiveChallenge() instanceof DeathrunChallenge)) {
            //Send message to sender
            sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "The Challenge Deathrun isn't running!");

            //Return false
            return false;
        }
        DeathrunChallenge challenge = (DeathrunChallenge) Main.getInstance().getChallengeManager().getActiveChallenge();
        if (!Main.getInstance().getChallengeManager().getActiveChallenge().isRunning() | challenge.getCountdown() > 0) {
            //Send message to sender
            sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "The Challenge Deathrun isn't running!");

            //Return false
            return false;
        }

        //Get the player from the sender
        Player player = (Player) sender;

        //Check for died
        if (!(challenge.getDied().contains(player) | challenge.getPositions().containsKey(player))) {
            //Send message to sender
            sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "You aren't a player of the event!");

            //Return false
            return false;
        }

        //Remove from died
        challenge.getDied().remove(player);

        //Teleport the player to the spawn of the right dimension
        Location spawn = Bukkit.getWorld(challenge.getDimension()).getSpawnLocation().add(0.5, 0.5, 0.5);
        spawn.setYaw(-90);
        Main.getInstance().getMultiverseCore().teleportPlayer(player, player, spawn);

        //Add the player to the positions
        challenge.getPositions().put(player, 0);

        //Update all player scoreboards
        for (DeathrunScoreboard scoreboard : challenge.getScoreboards()) {
            scoreboard.update();
        }

        //Prepare
        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setSaturation(20);
        player.getInventory().clear();
        player.sendTitle(ChatColor.GREEN + "GO!", ChatColor.GRAY.toString() + ChatColor.ITALIC + "Good luck!", 0, 30, 10);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER, 1, 2);

        //Return true
        return true;

    }

    //Called, on typing the command in the chat
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        //Return empty list
        return new ArrayList<>();

    }

}