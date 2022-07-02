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


//PLAYER COMMAND CLASS
public class PlayerCommand implements CommandExecutor, TabCompleter {

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

            case "show":
            case "s": {

                //If the sender is not a player
                if (!(sender instanceof Player)) {
                    //Send message to sender
                    sender.sendMessage(CommandUtils.getConsolePrefix() + "You must be a player to change player visibility!");

                    //Return false
                    return false;
                }

                //Get the player from the sender
                Player player = (Player) sender;

                //Show the player
                Main.getInstance().getVisibilityManager().show(player);

                //Update the inventory
                Main.getInstance().getPlayerInventoryManager().updateInventory(player);

                //Send message to player
                player.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Successfully showed players!");

                //Return true
                return true;

            }
            case "hide":
            case "h": {

                //If the sender is not a player
                if (!(sender instanceof Player)) {
                    //Send message to sender
                    sender.sendMessage(CommandUtils.getConsolePrefix() + "You must be a player to change player visibility!");

                    //Return false
                    return false;
                }

                //Get the player from the sender
                Player player = (Player) sender;

                //Show the player
                Main.getInstance().getVisibilityManager().hide(player);

                //Update the inventory
                Main.getInstance().getPlayerInventoryManager().updateInventory(player);

                //Send message to player
                player.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Successfully hid players!");

                //Return true
                return true;

            }

            case "list":
            case "l":

                //Send message to sender
                if (isPlayer) {
                    sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.AQUA + "List of all online players" + ChatColor.GRAY + ":");
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        sender.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + player.getName());
                    }
                    if (Bukkit.getOnlinePlayers().isEmpty()) sender.sendMessage(ChatColor.DARK_PURPLE.toString() + ChatColor.ITALIC + "No players found!");
                    sender.sendMessage("\n ");
                } else {
                    sender.sendMessage(CommandUtils.getConsolePrefix() + "List of all online players:");
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        sender.sendMessage("- " + player.getName());
                    }
                    if (Bukkit.getOnlinePlayers().isEmpty()) sender.sendMessage("No players found!");
                    sender.sendMessage("\n ");
                }

                //Return true
                return true;

            case "help":

                //Send help message to sender
                if (isPlayer) {
                    sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.AQUA + "Help for the command " + ChatColor.YELLOW + "/player" +
                            ChatColor.GRAY + ":\n \n" + ChatColor.AQUA + "Alias" + ChatColor.GRAY + ": " + ChatColor.YELLOW + "/p" +
                            "\n \n" + ChatColor.AQUA + "Usage" + ChatColor.GRAY + ":\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/player help\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/player show|s\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/player hide|h\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/player list|l\n ");
                }
                else {
                    sender.sendMessage(CommandUtils.getConsolePrefix() + "Help for the command '/player':\n \nAlias: '/p'" +
                            "\n \nUsage:\n" +
                            "- /player help\n" +
                            "- /player show|s\n" +
                            "- /player hide|h\n" +
                            "- /player list|l\n");
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

        //If the length of arguments is one
        if (args.length == 1) {
            result.add("show");
            result.add("hide");
            result.add("list");
            result.add("help");
        }

        //Return the formatted result
        return CommandUtils.formatTapCompleterList(result, args);

    }

}
