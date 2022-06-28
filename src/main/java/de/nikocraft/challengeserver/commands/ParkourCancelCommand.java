//PACKAGE
package de.nikocraft.challengeserver.commands;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.inventories.players.PlayerInventoryDefault;
import de.nikocraft.challengeserver.parkours.Parkour;
import de.nikocraft.challengeserver.parkours.ParkourManager;
import de.nikocraft.challengeserver.utils.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;


//LOBBY WORLD COMMAND CLASS
public class ParkourCancelCommand implements CommandExecutor, TabCompleter {

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

            //Remove the player from the list
            if (parkour.removePlayer(player)) {

                //Teleport the player to the spawn
                Main.getInstance().getMultiverseCore().teleportPlayer(player, player, new Location(Bukkit.getWorld("lobby"), 0.5, 100, 0.5, 0, 0));

                //Set player inventory mode
                Main.getInstance().getPlayerInventoryManager().setPlayerInventoryMode(player, new PlayerInventoryDefault(player), true);

                //Send a message to the player
                player.sendMessage(ParkourManager.getChatPrefix() + ChatColor.YELLOW + "Cancelled parkour.");

                //Return true
                return true;

            }

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