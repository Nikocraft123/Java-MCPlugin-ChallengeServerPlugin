//PACKAGE
package de.nikocraft.challengeserver.commands;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.inventories.enderchests.Enderchest;
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


//WORLD COMMAND CLASS
public class WorldCommand implements CommandExecutor, TabCompleter {

    //OVERRIDE METHODS

    //Called, if the command send
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //Is the sender a player
        boolean isPlayer = sender instanceof Player;

        //If no arguments contains
        if (args.length == 0) {

            //Send message to sender
            if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing arguments!");
            else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing arguments!");

            //Return false
            return false;

        }

        //Switch in argument 1
        switch (args[0].toLowerCase()) {

            case "reset":
            case "r":

                return true;

            case "lobby":
            case "l": {

                //If the sender is not a player
                if (!isPlayer) {
                    //Send message to sender
                    sender.sendMessage(CommandUtils.getConsolePrefix() + "You must are a player to teleport to lobby world!");

                    //Return false
                    return false;
                }

                //Get the player from the sender
                Player player = (Player) sender;

                //Save the player position
                Main.getInstance().getWorldManager().setPlayerPosition(player);

                //Teleport the player to lobby
                player.teleport(new Location(Bukkit.getWorld("lobby"), 0, 0, 0));

                //Send message to sender
                sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "You successfully teleported to lobby world!");

                //Return true
                return true;

            }
            case "game":
            case "g": {

                //If the sender is not a player
                if (!isPlayer) {
                    //Send message to sender
                    sender.sendMessage(CommandUtils.getConsolePrefix() + "You must are a player to teleport to game world!");

                    //Return false
                    return false;
                }

                //Get the player from the sender
                Player player = (Player) sender;

                //Teleport the player to game world player position
                player.teleport(Main.getInstance().getWorldManager().getPlayerPosition(player));

                //Send message to sender
                sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "You successfully teleported to game world!");

                //Return true
                return true;

            }
            case "help":
            case "h":

                //Send help message to sender
                if (isPlayer) {
                    sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.AQUA + "Help for the command " + ChatColor.YELLOW + "/world" +
                            ChatColor.GRAY + ":\n \n" + ChatColor.AQUA + "Alias" + ChatColor.GRAY + ": " + ChatColor.YELLOW + "/wd" +
                            "\n \n" + ChatColor.AQUA + "Usage" + ChatColor.GRAY + ":\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/world help|h\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/world reset|r <seed> confirm\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/world lobby|l\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/world game|g\n");
                }
                else {
                    sender.sendMessage(CommandUtils.getConsolePrefix() + "Help for the command '/world':\n \nAlias: '/wd'" +
                            "\n \nUsage:\n" +
                            "- /world help|h\n" +
                            "- /world reset|r <seed> confirm\n" +
                            "- /world lobby|l\n" +
                            "- /world game|g\n");
                }

                //Return true
                return true;

            default:

                //Send message to sender
                if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Invalid first argument!");
                else sender.sendMessage(CommandUtils.getConsolePrefix() + "Invalid first argument!");

                //Return false
                return false;

        }

    }

    //Called, on typing the command in the chat
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        //Define a result list
        ArrayList<String> result = new ArrayList<>();

        //If 1 argument exist:
        if (args.length == 1) {
            //Add commands to the list
            result.add("reset");
            result.add("lobby");
            result.add("game");
            result.add("help");
        }

        //Return the formatted result
        return CommandUtils.formatTapCompleterList(result, args);

    }

}
