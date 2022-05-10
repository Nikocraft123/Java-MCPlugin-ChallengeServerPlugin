//PACKAGE
package de.nikocraft.challengeserver.commands;


//IMPORTS
import de.nikocraft.challengeserver.Main;
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
import java.util.Arrays;
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

                //Check resetting
                if (Main.getInstance().getWorldManager().isResetting()) {
                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Already resetting!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Already resetting!");

                    //Return true
                    return true;
                }

                //Check confirmation
                if (!args[args.length - 1].equals("confirm")) {
                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Please confirm with 'confirm' as last argument!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Please confirm with 'confirm' as as last argument!");

                    //Return false
                    return false;
                }

                //Get the seed
                String seed = null;
                if (args.length > 2) seed = args[1];

                //Reset the world
                Main.getInstance().getWorldManager().resetWorld(seed);

                //Return true
                return true;

            case "lobby":
            case "l": {

                //Switch in the count of arguments
                switch (args.length) {

                    case 1:

                        //If the sender is not a player
                        if (!isPlayer) {
                            //Send message to sender
                            sender.sendMessage(CommandUtils.getConsolePrefix() + "You must are a player to teleport to lobby world!");

                            //Return false
                            return false;
                        }

                        //Get the player from the sender
                        Player player = (Player) sender;

                        //Check if the player is in the game world
                        if (Arrays.asList("world", "world_nether", "world_the_end").contains(player.getLocation().getWorld().getName())) {

                            //Save the player position
                            Main.getInstance().getWorldManager().setPlayerPosition(player);

                        }

                        //Teleport the player to lobby
                        Main.getInstance().getMultiverseCore().teleportPlayer(sender, player, new Location(Bukkit.getWorld("lobby"), 0.5, 100, 0.5, 0, 0));

                        //Send message to sender
                        sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "You successfully teleported to lobby world!");

                        //Return true
                        return true;

                    case 2:

                        //Get the player to teleport
                        Player target = Bukkit.getPlayer(args[1]);

                        //If the targeted player doesn't found
                        if (target == null) {
                            //Send message to sender
                            if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Cannot found the player '" + ChatColor.ITALIC + args[1] + ChatColor.RED + "'!");
                            else sender.sendMessage(CommandUtils.getConsolePrefix() + "Cannot found the player '" + args[1] + "'!");

                            //Return false
                            return false;
                        }

                        //Check if the player is in the game world
                        if (Arrays.asList("world", "world_nether", "world_the_end").contains(target.getLocation().getWorld().getName())) {

                            //Save the player position
                            Main.getInstance().getWorldManager().setPlayerPosition(target);

                        }

                        //Teleport the player to lobby
                        Main.getInstance().getMultiverseCore().teleportPlayer(sender, target, new Location(Bukkit.getWorld("lobby"), 0.5, 100, 0.5, 0, 0));

                        //Send message to sender
                        if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "'" + ChatColor.ITALIC + target + ChatColor.GREEN + "' successfully teleported to lobby world!");
                        else sender.sendMessage(CommandUtils.getConsolePrefix() + "'" + target + "' successfully teleported to lobby world!");

                        //Return true
                        return true;

                }

            }
            case "game":
            case "g": {

                //Switch in the count of arguments
                switch (args.length) {

                    case 1:

                        //If the sender is not a player
                        if (!isPlayer) {
                            //Send message to sender
                            sender.sendMessage(CommandUtils.getConsolePrefix() + "You must are a player to teleport to game world!");

                            //Return false
                            return false;
                        }

                        //If the game world is resetting
                        if (Main.getInstance().getWorldManager().isResetting()) {
                            //Send message to sender
                            sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Cannot join game world because it is resetting!");

                            //Return true
                            return true;
                        }

                        //Get the player from the sender
                        Player player = (Player) sender;

                        //Check if the player is already in the game world
                        if (Arrays.asList("world", "world_nether", "world_the_end").contains(player.getLocation().getWorld().getName())) {
                            //Send message to sender
                            sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "You are already in the game world!");

                            //Return true
                            return true;
                        }

                        //Teleport the player to game world player position
                        Main.getInstance().getMultiverseCore().teleportPlayer(sender, player, Main.getInstance().getWorldManager().getPlayerPosition(player));

                        //Send message to sender
                        sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "You successfully teleported to game world!");

                        //Return true
                        return true;

                    case 2:

                        //If the game world is resetting
                        if (Main.getInstance().getWorldManager().isResetting()) {
                            //Send message to sender
                            if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Cannot join game world because it is resetting!");
                            else sender.sendMessage(CommandUtils.getConsolePrefix() + "Cannot join game world because it is resetting!");

                            //Return true
                            return true;
                        }

                        //Get the player to teleport
                        Player target = Bukkit.getPlayer(args[1]);

                        //If the targeted player doesn't found
                        if (target == null) {
                            //Send message to sender
                            if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Cannot found the player '" + ChatColor.ITALIC + args[1] + ChatColor.RED + "'!");
                            else sender.sendMessage(CommandUtils.getConsolePrefix() + "Cannot found the player '" + args[1] + "'!");

                            //Return false
                            return false;
                        }

                        //Check if the player is already in the game world
                        if (Arrays.asList("world", "world_nether", "world_the_end").contains(target.getLocation().getWorld().getName())) {
                            //Send message to sender
                            if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "The player '" + ChatColor.ITALIC + args[1] + ChatColor.RED + "' is already in the game world!");
                            else sender.sendMessage(CommandUtils.getConsolePrefix() + "The player '" + args[1] + "' is already in the game world!");

                            //Return true
                            return true;
                        }

                        //Teleport the player to game world player position
                        Main.getInstance().getMultiverseCore().teleportPlayer(sender, target, Main.getInstance().getWorldManager().getPlayerPosition(target));

                        //Send message to sender
                        if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "'" + ChatColor.ITALIC + target + ChatColor.GREEN + "' successfully teleported to game world!");
                        else sender.sendMessage(CommandUtils.getConsolePrefix() + "'" + target + "' successfully teleported to game world!");

                        //Return true
                        return true;

                }

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
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/world lobby|l <player>\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/world game|g <player>\n");
                }
                else {
                    sender.sendMessage(CommandUtils.getConsolePrefix() + "Help for the command '/world':\n \nAlias: '/wd'" +
                            "\n \nUsage:\n" +
                            "- /world help|h\n" +
                            "- /world reset|r <seed> confirm\n" +
                            "- /world lobby|l <player>\n" +
                            "- /world game|g <player>\n");
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

        //Switch in the count of arguments
        switch (args.length) {

            case 1:

                //Add commands to the list
                result.add("reset");
                result.add("lobby");
                result.add("game");
                result.add("help");

            case 2:

                //If the argument is lobby or game
                if (Arrays.asList("lobby", "game").contains(args[0])) {
                    //For in all online players
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        //Add the player name to result
                        result.add(player.getName());
                    }
                }

        }

        //Return the formatted result
        return CommandUtils.formatTapCompleterList(result, args);

    }

}
