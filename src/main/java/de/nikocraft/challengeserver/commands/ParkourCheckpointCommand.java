//PACKAGE
package de.nikocraft.challengeserver.commands;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.minigames.Parkour;
import de.nikocraft.challengeserver.minigames.ParkourManager;
import de.nikocraft.challengeserver.utils.CommandUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;


//LOBBY WORLD COMMAND CLASS
public class ParkourCheckpointCommand implements CommandExecutor, TabCompleter {

    //OVERRIDE METHODS

    //Called, if the command send
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //Check for player
        if (!(sender instanceof Player)) {
            //Send message to sender
            sender.sendMessage(CommandUtils.getConsolePrefix() + "You must be a player to execute this command!");

            //Return false
            return false;
        }

        //Get the player
        Player player = (Player) sender;

        //Go through all parkours
        for (Parkour parkour : Main.getInstance().getParkourManager().getParkours()) {

            //Get the player checkpoint
            int checkpoint = parkour.getCheckpoint(player);

            //If the checkpoint is -1, continue
            if (checkpoint == -1) continue;

            //Teleport the player to the last checkpoint
            if (checkpoint == 0) {
                Main.getInstance().getMultiverseCore().teleportPlayer(player, player, parkour.getStart());
            } else
                Main.getInstance().getMultiverseCore().teleportPlayer(player, player, parkour.getCheckpoints().get(checkpoint - 1));

            //Send a message to the player
            player.sendMessage(ParkourManager.getChatPrefix() + ChatColor.DARK_PURPLE + "Try it again! You can do it! xD");

            //Return true
            return true;

        }

        //Send a message to the player
        player.sendMessage(ParkourManager.getChatPrefix() + ChatColor.RED + "You are not playing a parkour!");

        //Return false
        return false;

    }

    //Called, on typing the command in the chat
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        //Return empty list
        return new ArrayList<>();

    }

}