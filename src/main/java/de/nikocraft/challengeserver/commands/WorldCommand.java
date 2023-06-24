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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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

            case "clear_position":
            case "clr_pos":

                //Reset all players positions
                Main.getInstance().getWorldConfig().getConfig().set("positions", null);

                //Send message
                sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Cleared all player positions!");

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
                            sender.sendMessage(CommandUtils.getConsolePrefix() + "You must be a player to teleport to lobby world!");

                            //Return false
                            return false;
                        }

                        //Get the player from the sender
                        Player player = (Player) sender;

                        //Teleport the player to the lobby
                        Main.getInstance().getWorldManager().teleportToLobby(player, true);

                        //Return true
                        return true;

                    case 2:

                        //Get the player to teleport
                        Player target = Bukkit.getPlayer(args[1]);

                        //If the targeted player doesn't found
                        if (target == null) {
                            //Send message to sender
                            if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Cannot find the player '" + ChatColor.ITALIC + args[1] + ChatColor.RED + "'!");
                            else sender.sendMessage(CommandUtils.getConsolePrefix() + "Cannot find the player '" + args[1] + "'!");

                            //Return false
                            return false;
                        }

                        //Teleport the targeted player to the lobby
                        Main.getInstance().getWorldManager().teleportToLobby(target, false);

                        //Send message to sender
                        if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "'" + ChatColor.ITALIC + target.getName() + ChatColor.GREEN + "' successfully teleported to lobby world!");
                        else sender.sendMessage(CommandUtils.getConsolePrefix() + "'" + target.getName() + "' successfully teleported to lobby world!");

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
                            sender.sendMessage(CommandUtils.getConsolePrefix() + "You must be a player to teleport to game world!");

                            //Return false
                            return false;
                        }

                        //Get the player from the sender
                        Player player = (Player) sender;

                        //Teleport the player to the lobby
                        Main.getInstance().getWorldManager().teleportToGame(player, true);

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

                        //Teleport the player to the lobby
                        boolean resettingOrClosed = !Main.getInstance().getWorldManager().teleportToGame(target, false);

                        //If the world is resetting or closed
                        if (resettingOrClosed) {
                            //Send message to sender
                            if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Cannot join game world because it is resetting or closed!");
                            else sender.sendMessage(CommandUtils.getConsolePrefix() + "Cannot join game world because it is resetting or closed!");

                            //Return true
                            return true;
                        }

                        //Send message to sender
                        if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "'" + ChatColor.ITALIC + target.getName() + ChatColor.GREEN + "' successfully teleported to game world!");
                        else sender.sendMessage(CommandUtils.getConsolePrefix() + "'" + target.getName() + "' successfully teleported to game world!");

                        //Return true
                        return true;

                }

            }
            case "open":
            case "o":

                //If the world is already open
                if (Main.getInstance().getWorldManager().isOpen()) {
                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "The world is already open!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "The world is already open!");

                    //Return false
                    return false;
                }

                //Set the world to open
                Main.getInstance().getWorldManager().setOpen(true);

                //Send message to sender
                if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "World opened!");
                else sender.sendMessage(CommandUtils.getConsolePrefix() + "World opened!");

                //Return true
                return true;

            case "close":
            case "c":

                //If the world is already closed
                if (!Main.getInstance().getWorldManager().isOpen()) {
                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "The world is already closed!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "The world is already closed!");

                    //Return false
                    return false;
                }

                //Loop for all online players
                for (Player player : Bukkit.getOnlinePlayers()) {

                    //Check if the player is in the game world
                    if (Arrays.asList("world", "world_nether", "world_the_end").contains(player.getLocation().getWorld().getName())) {

                        //Teleport the player to lobby
                        Main.getInstance().getWorldManager().teleportToLobby(player, true);

                    }

                }

                //Set the world to closed
                Main.getInstance().getWorldManager().setOpen(false);

                //Send message to sender
                if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "World closed!");
                else sender.sendMessage(CommandUtils.getConsolePrefix() + "World closed!");

                //Return true
                return true;

            case "help":
            case "h":

                //Send help message to sender
                if (isPlayer) {
                    sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.AQUA + "Help for the command " + ChatColor.YELLOW + "/world" +
                            ChatColor.GRAY + ":\n \n" + ChatColor.AQUA + "Alias" + ChatColor.GRAY + ": " + ChatColor.YELLOW + "/wd" +
                            "\n \n" + ChatColor.AQUA + "Usage" + ChatColor.GRAY + ":\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/world help|h\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/world reset|r <seed> confirm\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/world clear_position|clr_pos\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/world lobby|l <player>\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/world game|g <player>\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/world open|o\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/world close|c\n");
                }
                else {
                    sender.sendMessage(CommandUtils.getConsolePrefix() + "Help for the command '/world':\n \nAlias: '/wd'" +
                            "\n \nUsage:\n" +
                            "- /world help|h\n" +
                            "- /world reset|r <seed> confirm\n" +
                            "- /world clear_position|clr_pos\n" +
                            "- /world lobby|l <player>\n" +
                            "- /world game|g <player>\n" +
                            "- /world open|o\n" +
                            "- /world close|c\n");
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
                result.add("clear_position");
                result.add("lobby");
                result.add("game");
                result.add("open");
                result.add("close");
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
