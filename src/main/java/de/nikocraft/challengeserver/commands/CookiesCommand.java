//PACKAGE
package de.nikocraft.challengeserver.commands;


//IMPORTS
import de.nikocraft.challengeserver.Main;
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


//COOKIES COMMAND CLASS
public class CookiesCommand implements CommandExecutor, TabCompleter {

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

        //If the argument 1 is help
        if (args[0].equalsIgnoreCase("help") | args[0].equalsIgnoreCase("h")) {

            //Send help message to sender
            if (isPlayer) {
                sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.AQUA + "Help for the command " + ChatColor.YELLOW + "/cookies" +
                        ChatColor.GRAY + ":\n \n" + ChatColor.AQUA + "Alias" + ChatColor.GRAY + ": " + ChatColor.YELLOW + "/ck" +
                        "\n \n" + ChatColor.AQUA + "Usage" + ChatColor.GRAY + ":\n" +
                        ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/cookies help\n" +
                        ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/cookies <player>\n" +
                        ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/cookies <player> amount <amount>\n" +
                        ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/cookies <player> absolute <amount>\n" +
                        ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/cookies <player> level <level>\n ");
            }
            else {
                sender.sendMessage(CommandUtils.getConsolePrefix() + "Help for the command '/cookies':\n \nAlias: '/ck'" +
                        "\n \nUsage:\n" +
                        "/cookies help\n" +
                        "/cookies <player>\n" +
                        "/cookies <player> amount <amount>\n" +
                        "/cookies <player> absolute <amount>\n" +
                        "/cookies <player> level <level>\n ");
            }

            //Return true
            return true;

        }

        //Get the targeted player
        Player target = Bukkit.getPlayer(args[0]);

        //If the targeted player doesn't found
        if (target == null) {

            //Send message to sender
            if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Cannot find the player " + ChatColor.ITALIC + args[1] + ChatColor.RED + "!");
            else sender.sendMessage(CommandUtils.getConsolePrefix() + "Cannot find the player '" + args[1] + "'!");

            //Return false
            return false;

        }

        //If there is no more argument
        if (args.length <= 1) {

            //Send help message to sender
            if (isPlayer) {
                sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.AQUA + "Cookie information of the player " + ChatColor.YELLOW + args[0] +
                        ChatColor.GRAY + ":\n \n" + ChatColor.AQUA + "Amount" + ChatColor.GRAY + ": " + ChatColor.YELLOW + Main.getInstance().getCookieManager().getCookies(target) +
                        ChatColor.AQUA + "\nAbsolute" + ChatColor.GRAY + ": " + ChatColor.YELLOW + Main.getInstance().getCookieManager().getAbsolute(target) +
                        ChatColor.AQUA + "\nLevel" + ChatColor.GRAY + ": " + ChatColor.YELLOW + Main.getInstance().getCookieManager().getLevel(target) + "\n ");
            }
            else {
                sender.sendMessage(CommandUtils.getConsolePrefix() + "Cookie information of the player '" + args[0] + "':\n \n" + "Amount: " + Main.getInstance().getCookieManager().getCookies(target) +
                        "\nAbsolute: " + Main.getInstance().getCookieManager().getAbsolute(target) +
                        "\nLevel: " + Main.getInstance().getCookieManager().getLevel(target) + "\n ");
            }

            //Return true
            return true;

        }
        else {

            //Switch in argument 2
            switch (args[1].toLowerCase()) {

                case "amount":
                case "a": {

                    //If there is no more argument
                    if (args.length <= 2) {
                        //Send message to sender
                        if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing third argument!");
                        else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing third argument!");

                        //Return false
                        return false;
                    }

                    try {

                        //Set the amount
                        Main.getInstance().getCookieManager().setCookies(target, Long.parseLong(args[2]));

                    }
                    catch (NumberFormatException e) {

                        //Send message to sender
                        if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Invalid number!");
                        else sender.sendMessage(CommandUtils.getConsolePrefix() + "Invalid number!");

                        //Return false
                        return false;

                    }

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Successfully set the amount of cookies of the player " + args[0] + " to " + args[2] + "!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Successfully set the amount of cookies of the player " + args[0] + " to " + args[2] + "!");

                    //Return true
                    return true;

                }
                case "absolute": {

                    //If there is no more argument
                    if (args.length <= 2) {
                        //Send message to sender
                        if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing third argument!");
                        else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing third argument!");

                        //Return false
                        return false;
                    }

                    try {

                        //Set the absolute amount
                        Main.getInstance().getCookieManager().setAbsolute(target, Long.parseLong(args[2]));

                    }
                    catch (NumberFormatException e) {

                        //Send message to sender
                        if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Invalid number!");
                        else sender.sendMessage(CommandUtils.getConsolePrefix() + "Invalid number!");

                        //Return false
                        return false;

                    }

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Successfully set the absolute amount of cookies of the player " + args[0] + " to " + args[2] + "!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Successfully set the absolute amount of cookies of the player " + args[0] + " to " + args[2] + "!");

                    //Return true
                    return true;

                }
                case "level":
                case "l": {

                    //If there is no more argument
                    if (args.length <= 2) {
                        //Send message to sender
                        if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing third argument!");
                        else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing third argument!");

                        //Return false
                        return false;
                    }

                    try {

                        //Set the amount
                        Main.getInstance().getCookieManager().setLevel(target, Integer.parseInt(args[2]));

                    }
                    catch (NumberFormatException e) {

                        //Send message to sender
                        if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Invalid number!");
                        else sender.sendMessage(CommandUtils.getConsolePrefix() + "Invalid number!");

                        //Return false
                        return false;

                    }

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Successfully set the cookie level of the player " + args[0] + " to " + args[2] + "!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Successfully set the cookie level of the player " + args[0] + " to " + args[2] + "!");

                    //Return true
                    return true;

                }
                default:

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Invalid second argument!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Invalid second argument!");

                    //Return false
                    return false;

            }

        }

    }

    //Called, on typing the command in the chat
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        //Define a result list
        ArrayList<String> result = new ArrayList<>();

        //If the length of arguments is one
        if (args.length == 1) {
            result.add("help");
            for (Player player : Bukkit.getOnlinePlayers()) {
                result.add(player.getName());
            }
        }

        //If the length of arguments is two
        else if (args.length == 2) {
            result.add("amount");
            result.add("absolute");
            result.add("level");
        }

        //Return the formatted result
        return CommandUtils.formatTapCompleterList(result, args);

    }

}
