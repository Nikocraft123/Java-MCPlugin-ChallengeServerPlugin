//PACKAGE
package de.nikocraft.challengeserver.commands;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.utils.CommandUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;


//INVENTORY COMMAND CLASS
public class InventoryCommand implements CommandExecutor, TabCompleter {

    //OVERRIDE METHODS

    //Called, if the command send
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //If the sender is not a player
        if (!(sender instanceof Player)) {
            //Send message to sender
            sender.sendMessage(CommandUtils.getConsolePrefix() + "You must be a player to manage inventories!");

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

        //Switch in argument 1
        switch (args[0].toLowerCase()) {

            case "activate":
            case "a":

                //Set the player inventory to activated
                Main.getInstance().getPlayerInventoryManager().setPlayerInventoryActive(player, true, true);

                //Save changes
                Main.getInstance().getInventoryConfig().getConfig().set("active." + player.getUniqueId().toString(), true);
                Main.getInstance().getInventoryConfig().save();

                //Send message to player
                player.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Successfully activated your inventory!");

                //Return true
                return true;

            case "deactivate":
            case "d":

                //Set the player inventory to activated
                Main.getInstance().getPlayerInventoryManager().setPlayerInventoryActive(player, false, true);

                //Save changes
                Main.getInstance().getInventoryConfig().getConfig().set("active." + player.getUniqueId().toString(), false);
                Main.getInstance().getInventoryConfig().save();

                //Send message to player
                player.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Successfully deactivated your inventory!");

                //Return true
                return true;

            case "see":
            case "s":

                //TODO

                //Return true
                return true;


            case "help":
            case "h":

                //Send help message to player
                player.sendMessage(CommandUtils.getChatPrefix() + ChatColor.AQUA + "Help for the command " + ChatColor.YELLOW + "/inventory" +
                        ChatColor.GRAY + ":\n \n" + ChatColor.AQUA + "Alias" + ChatColor.GRAY + ": " + ChatColor.YELLOW + "/inv" +
                        "\n \n" + ChatColor.AQUA + "Usage" + ChatColor.GRAY + ":\n" +
                        ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/inv help|h\n" +
                        ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/inv activate|a\n" +
                        ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/inv deactivate|d\n" +
                        ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/inv see|s <player>\n ");

                //Return true
                return true;

            default:

                //Send message to player
                player.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Invalid first argument!");

                //Return false
                return false;

        }

    }


    //Called, on typing the command in the chat
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        //Define a result list
        ArrayList<String> result = new ArrayList<>();

        //If the length of arguments is one
        if (args.length == 1) {
            result.add("activate");
            result.add("deactivate");
            result.add("see");
            result.add("help");
        }

        //Return the formatted result
        return CommandUtils.formatTapCompleterList(result, args);

    }

}
