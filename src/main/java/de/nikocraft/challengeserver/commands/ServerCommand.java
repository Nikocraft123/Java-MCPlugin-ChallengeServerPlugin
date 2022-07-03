//PACKAGE
package de.nikocraft.challengeserver.commands;


//IMPORTS
import de.nikocraft.challengeserver.inventories.menus.ServerSelectionMenu;
import de.nikocraft.challengeserver.utils.CommandUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;


//SERVER COMMAND CLASS
public class ServerCommand implements CommandExecutor, TabCompleter {

    //OVERRIDE METHODS

    //Called, if the command send
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //If the sender is not a player
        if (!(sender instanceof Player)) {
            //Send message to sender
            sender.sendMessage(CommandUtils.getConsolePrefix() + "You must be a player to open the server selection menu!");

            //Return false
            return false;
        }

        //Get the player from the sender
        Player player = (Player) sender;

        //Create the menu
        ServerSelectionMenu menu = new ServerSelectionMenu(player);

        //Open the menu
        player.openInventory(menu.getInventory());

        //Send message to player
        player.sendMessage(ChatColor.GRAY + "Open server selection menu ...");

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