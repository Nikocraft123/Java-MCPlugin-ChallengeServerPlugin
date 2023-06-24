//PACKAGE
package de.nikocraft.challengeserver.commands;


//IMPORTS

import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.utils.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//INVENTORY COMMAND CLASS
public class VisitCommand implements CommandExecutor, TabCompleter {

    //OVERRIDE METHODS

    //Called, if the command send
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //If the sender is not a player
        if (!(sender instanceof Player)) {
            //Send message to sender
            sender.sendMessage(CommandUtils.getConsolePrefix() + "You must be a player to visit someone!");

            //Return false
            return false;
        }

        //Get the player from the sender
        Player player = (Player) sender;

        //If no arguments contains
        if (args.length == 0) {
            //Send message to player
            player.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing arguments!");

            //Return false
            return false;
        }

        //Check player
        if (!Arrays.asList("world", "world_nether", "world_the_end").contains(player.getWorld().getName())) {
            //Send message to player
            player.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "You need to be in the game world!");

            //Return false
            return false;
        }

        //Check player
        if (player.getGameMode() != GameMode.SPECTATOR) {
            //Send message to player
            player.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "You need to be in spectator mode!");

            //Return false
            return false;
        }

        //Check challenge
        if (Main.getInstance().getChallengeManager().getActiveChallenge() == null) {
            //Send message to player
            player.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "There is no challenge selected!");

            //Return false
            return false;
        }

        //Check target
        if (Bukkit.getPlayer(args[0]) == null) {
            //Send message to player
            player.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Cannot find the requested player!");

            //Return false
            return false;
        }

        //Check target
        if (!Main.getInstance().getChallengeManager().getActiveChallenge().getVisit().contains(Bukkit.getPlayer(args[0]))) {
            //Send message to player
            player.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Cannot visit the requested player!");

            //Return false
            return false;
        }

        //Teleport
        player.teleport(Bukkit.getPlayer(args[0]));

        //Send message to player
        player.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Teleported you to '" + args[0] + "'!");

        return true;

    }


    //Called, on typing the command in the chat
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        //Define a result list
        ArrayList<String> result = new ArrayList<>();

        //If the length of arguments is one
        if (args.length == 1) {
            result.add("help");
            if (Main.getInstance().getChallengeManager().getActiveChallenge() != null) {
                for (Player player : Main.getInstance().getChallengeManager().getActiveChallenge().getVisit()) {
                    result.add(player.getName());
                }
            }
        }

        //Return the formatted result
        return CommandUtils.formatTapCompleterList(result, args);

    }

}
