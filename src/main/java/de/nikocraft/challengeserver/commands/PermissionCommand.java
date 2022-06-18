//PACKAGE
package de.nikocraft.challengeserver.commands;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.permissions.Rank;
import de.nikocraft.challengeserver.utils.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;


//PERMISSION COMMAND CLASS
public class PermissionCommand implements CommandExecutor, TabCompleter {

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

            case "player":
            case "p":

                //Handle player permission command
                return handlePlayerCommand(sender, command, label, args);

            case "rank":
            case "r":

                //Handle rank permission command
                return handleRankCommand(sender, command, label, args);

            case "help":
            case "h":

                //Send help message to sender
                if (isPlayer) {
                    sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.AQUA + "Help for the command " + ChatColor.YELLOW + "/permission" +
                            ChatColor.GRAY + ":\n \n" + ChatColor.AQUA + "Alias" + ChatColor.GRAY + ": " + ChatColor.YELLOW + "/perm" +
                            "\n \n" + ChatColor.AQUA + "Usage" + ChatColor.GRAY + ":\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/perm help|h\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/perm player|p <player-name>\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/perm player|p <player-name> add <permission>\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/perm player|p <player-name> remove <permission>\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/perm player|p <player-name> rank <rank>\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/perm rank|r <rank>\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/perm rank|r <rank> add <permission>\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/perm rank|r <rank> remove <permission>\n" + "\n ");
                }
                else {
                    sender.sendMessage(CommandUtils.getConsolePrefix() + "Help for the command '/permission':\n \nAlias: '/perm'" +
                            "\n \nUsage:\n" +
                            "- /perm help|h\n" +
                            "- /perm player|p <player-name>\n" +
                            "- /perm player|p <player-name> add <permission>\n" +
                            "- /perm player|p <player-name> remove <permission>\n" +
                            "- /perm player|p <player-name> rank <rank>\n" +
                            "- /perm rank|r <rank>\n" +
                            "- /perm rank|r <rank> add <permission>\n" +
                            "- /perm rank|r <rank> remove <permission>\n");
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
                //Add completes to the result
                result.add("player");
                result.add("rank");
                result.add("help");

                break;
            case 2:
                //Switch in argument 1
                switch (args[0].toLowerCase()) {
                    case "player":
                    case "p":
                        //For in all online players
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            //Add the player name to result
                            result.add(player.getName());
                        }
                        break;
                    case "rank":
                    case "r":
                        //For in all player ranks
                        for (Rank rank : Rank.values()) {
                            //Add the rank full name to result
                            result.add(rank.getFullRankName());
                        }
                        break;
                }

                break;
            case 3:
                //If the argument 1 is not help (h)
                if (!(args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h"))) {
                    //Add completes to result
                    result.add("add");
                    result.add("remove");
                }

                //Add rank, if the argument 1 is player (p)
                if (args[0].equalsIgnoreCase("player") || args[0].equalsIgnoreCase("p")) result.add("rank");

                break;
            case 4:
                //Switch in argument 3
                switch (args[2].toLowerCase()) {

                    case "add":
                        //Add completes to result
                        switch (args[3].split("[.]").length) {
                            case 1:
                                result.add("*");
                                result.add("!");
                                result.add("server.");
                                result.add("bukkit.");
                                result.add("spigot.");
                                result.add("minecraft.");
                                result.add("multiverse.");
                                result.add("worldedit.");
                                result.add("!server.");
                                result.add("!bukkit.");
                                result.add("!spigot.");
                                result.add("!minecraft.");
                                result.add("!multiverse.");
                                result.add("!worldedit.");
                            case 2:
                                result.add(args[3].split("[.]")[0] + ".*");
                                result.add(args[3].split("[.]")[0] + ".command.");
                            default:
                                result.add(args[3].split("[.]")[args[3].split("[.]").length - 2] + ".*");
                        }

                        break;
                    case "remove":
                        //If the argument 1 is player (p)
                        if (args[0].equalsIgnoreCase("player") || args[0].equalsIgnoreCase("p")) {
                            //If the player doesn't found, break
                            if (Bukkit.getPlayer(args[1]) == null) break;

                            //For in all permissions of the player
                            for (String perm : Main.getInstance().getPermissionManager().getPlayerExtraPermissions(Bukkit.getPlayer(args[1]))) {
                                //Add the permission to result
                                result.add(perm);
                            }
                        }
                        //Else If the argument 1 is rank (r)
                        else if (args[0].equalsIgnoreCase("rank") || args[0].equalsIgnoreCase("r")) {
                            //If the rank doesn't found, break
                            if (Rank.fromUnknownInput(args[1].toLowerCase()) == null) break;

                            //For in all permissions of the rank
                            for (String perm : Main.getInstance().getPermissionManager().getRankPermissions(Rank.fromUnknownInput(args[1].toLowerCase()))) {
                                //Add the permission to result
                                result.add(perm);
                            }
                        }

                        break;
                    case "rank":
                        //Add rank, if the argument 1 is player (p)
                        if (args[0].equalsIgnoreCase("player") || args[0].equalsIgnoreCase("p")) {
                            //For in all player ranks
                            for (Rank rank : Rank.values()) {
                                //Add rank full name to result
                                result.add(rank.getFullRankName());
                            }
                        }

                        break;
                }
        }

        //Return the formatted result
        return CommandUtils.formatTapCompleterList(result, args);

    }


    //METHODS

    //Handle player permission command
    private boolean handlePlayerCommand(CommandSender sender, Command command, String label, String[] args) {

        //Is the sender a player
        boolean isPlayer = sender instanceof Player;

        //If the argument 2 missed
        if (args.length == 1) {

            //Send message to sender
            if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing second argument!");
            else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing second argument!");

            //Return false
            return false;

        }

        //Get the targeted player
        Player target = Bukkit.getPlayer(args[1]);

        //If the targeted player doesn't found
        if (target == null) {

            //Send message to sender
            if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Cannot found the player " + ChatColor.ITALIC + args[1] + ChatColor.RED + "!");
            else sender.sendMessage(CommandUtils.getConsolePrefix() + "Cannot found the player '" + args[1] + "'!");

            //Return false
            return false;

        }

        //If only 2 arguments contains
        if (args.length == 2) {

            //Define string for all permissions of the target
            String perms = "";

            //If the sender is a player
            if (isPlayer) {
                //Load all permissions of the targeted player
                if (Main.getInstance().getPermissionManager().getPlayerExtraPermissions(target).isEmpty()) perms = ChatColor.DARK_PURPLE.toString() + ChatColor.ITALIC + "No permissions found!";
                for (String perm : Main.getInstance().getPermissionManager().getPlayerExtraPermissions(target)) {
                    perms += ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + perm + "\n";
                }

                //Send message to sender
                sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.AQUA + "Permission information of '" + target.getName() + "'" +
                        ChatColor.GRAY + ":\n \n" + ChatColor.AQUA + "Rank" + ChatColor.GRAY + ": " + Main.getInstance().getPermissionManager().getPlayerRank(target).getColoredName() +
                        "\n \n" + ChatColor.AQUA + "Extra Permissions" + ChatColor.GRAY + ":\n" + perms + "\n ");
            }
            else {
                //Load all permissions of the targeted player
                if (Main.getInstance().getPermissionManager().getPlayerExtraPermissions(target).isEmpty()) perms = "No permissions found!";
                for (String perm : Main.getInstance().getPermissionManager().getPlayerExtraPermissions(target)) {
                    perms += "- " + perm + "\n";
                }

                //Send message to sender
                sender.sendMessage(CommandUtils.getConsolePrefix() + "Permission information of '" + target.getName() + "':\n \nRank: " +
                        Main.getInstance().getPermissionManager().getPlayerRank(target).getFullRankName() + "\n \nExtra Permissions:\n" + perms + "\n ");
            }

            //Return true
            return true;

        }

        //Switch in argument 3
        switch (args[2].toLowerCase()) {

            case "add":
                //If the argument 4 missed
                if (args.length < 4) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing fourth argument!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing fourth argument!");

                    //Return false
                    return false;

                }

                //Add the permission to player (If failed)
                if (!Main.getInstance().getPermissionManager().addPlayerExtraPermission(target, args[3])) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "This player already has this permission!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "This player already has this permission!");

                    //Return false
                    return false;

                }

                //Send message to sender
                if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Added the permission '" + args[3] + "' to the player '" + target.getName() + "'!");
                else sender.sendMessage(CommandUtils.getConsolePrefix() + "Added the permission '" + args[3] + "' to the player '" + target.getName() + "'!");

                break;
            case "remove":
                //If the argument 4 missed
                if (args.length < 4) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing fourth argument!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing fourth argument!");

                    //Return false
                    return false;

                }

                //Remove the permission to player (If failed)
                if (!Main.getInstance().getPermissionManager().removePlayerExtraPermission(target, args[3])) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "This player hasn't this permission!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "This player hasn't this permission!");

                    //Return false
                    return false;

                }

                //Send message to sender
                if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Removed the permission '" + args[3] + "' of the player '" + target.getName() + "'!");
                else sender.sendMessage(CommandUtils.getConsolePrefix() + "Removed the permission '" + args[3] + "' of the player '" + target.getName() + "'!");

                break;
            case "rank":
                //If the argument 4 missed
                if (args.length < 4) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing fourth argument!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing fourth argument!");

                    //Return false
                    return false;

                }

                //If the rank doesn't found
                if (Rank.fromUnknownInput(args[3]) == null) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Invalid rank!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Invalid rank!");

                    //Return false
                    return false;

                }

                //Set the rank of the player (If failed)
                if (!Main.getInstance().getPermissionManager().setPlayerRank(target, Rank.fromUnknownInput(args[3]))) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "This player already has this rank!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "This player already has this rank!");

                    //Return false
                    return false;

                }

                //Send message to sender
                if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Given '" + target.getName() + "' the rank " + Rank.fromUnknownInput(args[3]).getColoredName() + ChatColor.GREEN + "!");
                else sender.sendMessage(CommandUtils.getConsolePrefix() + "Given '" + target.getName() + "' the rank '" + Rank.fromUnknownInput(args[3]).getFullRankName() + "'!");

                break;
            default:

                //Send message to sender
                if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Invalid third argument!");
                else sender.sendMessage(CommandUtils.getConsolePrefix() + "Invalid third argument!");

                //Return false
                return false;

        }

        //Return true
        return true;

    }

    //Handle rank permission command
    private boolean handleRankCommand(CommandSender sender, Command command, String label, String[] args) {

        //Is the sender a player
        boolean isPlayer = sender instanceof Player;

        //If the argument 2 missed
        if (args.length == 1) {

            //Send message to sender
            if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing second argument!");
            else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing second argument!");

            //Return false
            return false;

        }

        //Get the targeted rank
        Rank target = Rank.fromUnknownInput(args[1]);

        //If the targeted rank doesn't founded
        if (target == null) {

            //Send message to sender
            if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "The rank '" + args[1] + "' doesn't exist!");
            else sender.sendMessage(CommandUtils.getConsolePrefix() + "The rank '" + args[1] + "' doesn't exist!");

            //Return false
            return false;

        }

        //If only 2 arguments contains
        if (args.length == 2) {

            //Define string for permissions of the target
            String perms = "";

            //If the sender is a player
            if (isPlayer) {
                //Load all permissions of the targeted rank
                if (Main.getInstance().getPermissionManager().getRankPermissions(target).isEmpty()) perms = ChatColor.DARK_PURPLE.toString() + ChatColor.ITALIC + "No permissions found!\n";
                for (String perm : Main.getInstance().getPermissionManager().getRankPermissions(target)) {
                    perms += ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + perm + "\n";
                }

                //Send message to sender
                sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.AQUA + "Rank information of " + target.getColoredName() +
                        ChatColor.GRAY + ":\n \n" + ChatColor.AQUA + "Rank permissions" + ChatColor.GRAY + ":\n" + perms + "\n ");
            }
            else {
                //Load all permissions of the targeted rank
                if (Main.getInstance().getPermissionManager().getRankPermissions(target).isEmpty()) perms = "No permissions found!\n";
                for (String perm : Main.getInstance().getPermissionManager().getRankPermissions(target)) {
                    perms += "- " + perm + "\n";
                }

                //Send message to sender
                sender.sendMessage(CommandUtils.getConsolePrefix() + "Rank information of '" + target.getFullRankName() + "':\n \nRank permissions:\n" + perms);
            }

            //Return true
            return true;

        }

        //Switch in argument 3
        switch (args[2].toLowerCase()) {

            case "add":
                //If the argument 4 missed
                if (args.length < 4) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing fourth argument!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing fourth argument!");

                    //Return false
                    return false;

                }

                //Add the permission to rank (If failed)
                if (!Main.getInstance().getPermissionManager().addRankPermission(target, args[3])) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "This rank already has this permission!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "This rank already has this permission!");

                    //Return false
                    return false;

                }

                //Send message to sender
                if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Added the permission '" + args[3] + "' to the rank " + target.getColoredName() + ChatColor.GREEN + "!");
                else sender.sendMessage(CommandUtils.getConsolePrefix() + "Added the permission '" + args[3] + "' to the rank '" + target.getFullRankName() + "'!");

                break;
            case "remove":
                //If the argument 4 missed
                if (args.length < 4) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing fourth argument!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing fourth argument!");

                    //Return false
                    return false;

                }

                //Remove the permission to rank (If failed)
                if (!Main.getInstance().getPermissionManager().removeRankPermission(target, args[3])) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "This rank hasn't this permission!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "This rank hasn't this permission!");

                    //Return false
                    return false;

                }

                //Send message to sender
                if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Removed the permission '" + args[3] + "' of the rank " + target.getColoredName() + ChatColor.GREEN + "!");
                else sender.sendMessage(CommandUtils.getConsolePrefix() + "Removed the permission '" + args[3] + "' of the rank '" + target.getFullRankName() + "'!");

                break;
            default:

                //Send message to sender
                if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Invalid third argument!");
                else sender.sendMessage(CommandUtils.getConsolePrefix() + "Invalid third argument!");

                //Return false
                return false;

        }

        //Return true
        return true;

    }

}
