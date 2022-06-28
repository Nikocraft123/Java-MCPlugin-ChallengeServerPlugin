//PACKAGE
package de.nikocraft.challengeserver.commands;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.parkours.Parkour;
import de.nikocraft.challengeserver.utils.CommandUtils;
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


//PARKOUR COMMAND CLASS
public class ParkourCommand implements CommandExecutor, TabCompleter {

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

        //Get the argument 2, if available
        String parkourName = null;
        if (args.length >= 2) parkourName = args[1].toLowerCase();

        //Switch in argument 1
        switch (args[0].toLowerCase()) {

            case "list":

                //Define string for all parkours
                String parkours = "";

                //Send help message to sender
                if (isPlayer) {
                    if (Main.getInstance().getParkourManager().getParkours().isEmpty()) parkours = ChatColor.DARK_PURPLE.toString() + ChatColor.ITALIC + "No parkour found!\n";
                    for (Parkour parkour : Main.getInstance().getParkourManager().getParkours()) {
                        parkours += ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + parkour.getName() + "\n";
                    }
                    sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.AQUA + "Parkour list" + ChatColor.GRAY + ":\n" + parkours);
                }
                else {
                    if (Main.getInstance().getParkourManager().getParkours().isEmpty()) parkours = "No parkour found!\n";
                    for (Parkour parkour : Main.getInstance().getParkourManager().getParkours()) {
                        parkours += "- " + parkour.getName() + "\n";
                    }
                    sender.sendMessage(CommandUtils.getConsolePrefix() + "Parkour list:\n" + parkours);
                }

                //Return true
                return true;

            case "info":

                //Call info command
                return commandInfo(sender, command, label, args, parkourName, isPlayer);

            case "create":

                //Call create command
                return commandCreate(sender, command, label, args, parkourName, isPlayer);

            case "delete":

                //Call delete command
                return commandDelete(sender, command, label, args, parkourName, isPlayer);

            case "start":
            case "destination":

                //Call start and destination command
                return commandStartAndDestination(sender, command, label, args, parkourName, isPlayer);

            case "checkpoint":

                //Call checkpoint command
                return commandCheckpoint(sender, command, label, args, parkourName, isPlayer);

            case "remove_last_checkpoint":

                //Call remove last checkpoint command
                return commandRemoveLastCheckpoint(sender, command, label, args, parkourName, isPlayer);

            case "activate":
            case "deactivate":

                //Call active command
                return commandActive(sender, command, label, args, parkourName, isPlayer);

            case "help":
            case "h":

                //Send help message to sender
                if (isPlayer) {
                    sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.AQUA + "Help for the command " + ChatColor.YELLOW + "/parkour" +
                            ChatColor.GRAY + ":\n \n" + ChatColor.AQUA + "Alias" + ChatColor.GRAY + ": " + ChatColor.YELLOW + "/pk" +
                            "\n \n" + ChatColor.AQUA + "Usage" + ChatColor.GRAY + ":\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/parkour help|h\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/parkour info <parkour-name>\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/parkour create <parkour-name>\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/parkour delete <parkour-name>\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/parkour start <parkour-name>\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/parkour destination <parkour-name>\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/parkour checkpoint <parkour-name>\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/parkour remove_last_checkpoint <parkour-name>\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/parkour activate <parkour-name>\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/parkour deactivate <parkour-name>\n" + "\n ");
                }
                else {
                    sender.sendMessage(CommandUtils.getConsolePrefix() + "Help for the command '/parkour':\n \nAlias: '/pk'" +
                            "\n \nUsage:\n" +
                            "- /parkour help|h\n" +
                            "- /parkour info <parkour-name>\n" +
                            "- /parkour create <parkour-name>\n" +
                            "- /parkour delete <parkour-name>\n" +
                            "- /parkour start <parkour-name>\n" +
                            "- /parkour destination <parkour-name>\n" +
                            "- /parkour checkpoint <parkour-name>\n" +
                            "- /parkour remove_last_checkpoint <parkour-name>\n" +
                            "- /parkour activate <parkour-name>\n" +
                            "- /parkour deactivate <parkour-name>\n" + "\n ");
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
                result.add("list");
                result.add("info");
                result.add("create");
                result.add("delete");
                result.add("start");
                result.add("destination");
                result.add("checkpoint");
                result.add("remove_last_checkpoint");
                result.add("activate");
                result.add("deactivate");
                result.add("help");

            case 2:
                //Check for first argument
                if (Arrays.asList("info", "delete", "start", "destination", "checkpoint", "remove_last_checkpoint", "activate", "deactivate").contains(args[0])) {
                    for (Parkour parkour : Main.getInstance().getParkourManager().getParkours()) {
                        result.add(parkour.getName());
                    }
                }

        }

        //Return the formatted result
        return CommandUtils.formatTapCompleterList(result, args);

    }


    //METHODS

    //Command info
    private boolean commandInfo(CommandSender sender, Command command, String label, String[] args, String parkourName, boolean isPlayer) {

        //Check for parkour
        if (Main.getInstance().getParkourManager().getParkour(parkourName) == null) {
            //Send message to sender
            if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Invalid second argument!");
            else sender.sendMessage(CommandUtils.getConsolePrefix() + "Invalid second argument!");

            //Return false
            return false;
        }

        //Get the parkour
        Parkour parkour = Main.getInstance().getParkourManager().getParkour(parkourName);

        //Define string for all checkpoints
        String checkpoints = "";

        //Send help message to sender
        if (isPlayer) {
            if (parkour.getCheckpoints().isEmpty()) checkpoints = ChatColor.DARK_PURPLE.toString() + ChatColor.ITALIC + "No checkpoint found!\n";
            for (Location checkpoint : parkour.getCheckpoints()) {
                checkpoints += ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + checkpoint.getX() + " | " +
                        checkpoint.getY() + " | " + checkpoint.getZ() + " (" + checkpoint.getYaw() + " | " + checkpoint.getPitch() + ")\n";
            }
            sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.AQUA + "Information of the parkour " + ChatColor.GOLD + parkourName + ChatColor.GRAY + ":\n" +
                    ChatColor.AQUA + "Start " + ChatColor.GRAY + "XYZ (Yaw | Pitch)" + ChatColor.GRAY + ": " + ChatColor.YELLOW + parkour.getStart().getX() + " | " +
                    parkour.getStart().getY() + " | " + parkour.getStart().getZ() + " (" + parkour.getStart().getYaw() + " | " + parkour.getStart().getPitch() + ")\n" +
                    ChatColor.AQUA + "Destination " + ChatColor.GRAY + "XYZ (Yaw | Pitch)" + ChatColor.GRAY + ": " + ChatColor.YELLOW + parkour.getDestination().getX() + " | " +
                    parkour.getDestination().getY() + " | " + parkour.getDestination().getZ() + " (" + parkour.getDestination().getYaw() + " | " + parkour.getDestination().getPitch() + ")\n" +
                    ChatColor.AQUA + "Active" + ChatColor.GRAY + ": " + ChatColor.YELLOW + parkour.isActive() + "\n" +
                    ChatColor.AQUA + "Checkpoints " + ChatColor.GRAY + "XYZ (Yaw | Pitch)" + ChatColor.GRAY + ":\n" + checkpoints);
        }
        else {
            if (parkour.getCheckpoints().isEmpty()) checkpoints = "No checkpoint found!\n";
            for (Location checkpoint : parkour.getCheckpoints()) {
                checkpoints += "- " + checkpoint.getX() + " | " +
                        checkpoint.getY() + " | " + checkpoint.getZ() + " (" + checkpoint.getYaw() + " | " + checkpoint.getPitch() + ")\n";
            }
            sender.sendMessage(CommandUtils.getConsolePrefix() + "Information of the parkour '" + parkourName + "':\n" +
                    "Start [XYZ (Yaw | Pitch)]: " + parkour.getStart().getX() + " | " +
                    parkour.getStart().getY() + " | " + parkour.getStart().getZ() + " (" + parkour.getStart().getYaw() + " | " + parkour.getStart().getPitch() + ")\n" +
                    "Destination [XYZ (Yaw | Pitch)]: " + parkour.getDestination().getX() + " | " +
                    parkour.getDestination().getY() + " | " + parkour.getDestination().getZ() + " (" + parkour.getDestination().getYaw() + " | " + parkour.getDestination().getPitch() + ")\n" +
                    "Active: " + parkour.isActive() + "\n" +
                    "Checkpoints [XYZ (Yaw | Pitch)]:\n" + checkpoints);
        }

        //Return true
        return true;

    }

    //Command create
    private boolean commandCreate(CommandSender sender, Command command, String label, String[] args, String parkourName, boolean isPlayer) {

        //Check for second argument
        if (parkourName == null) {
            //Send message to sender
            if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing second argument!");
            else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing second argument!");

            //Return false
            return false;
        }

        //Check for parkour
        if (Main.getInstance().getParkourManager().getParkour(parkourName) != null) {
            //Send message to sender
            if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "This parkour already exist!");
            else sender.sendMessage(CommandUtils.getConsolePrefix() + "This parkour already exist!");

            //Return false
            return false;
        }

        //Create a new parkour
        Parkour parkour = new Parkour(parkourName);

        //Add the parkour to the list
        Main.getInstance().getParkourManager().getParkours().add(parkour);

        //Send message to sender
        if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Successfully created new parkour " + ChatColor.GOLD + parkourName + ChatColor.GREEN + "!");
        else sender.sendMessage(CommandUtils.getConsolePrefix() + "Successfully created new parkour '" + parkourName + "'!");

        //Return true
        return true;

    }

    //Command delete
    private boolean commandDelete(CommandSender sender, Command command, String label, String[] args, String parkourName, boolean isPlayer) {

        //Check for second argument
        if (parkourName == null) {
            //Send message to sender
            if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing second argument!");
            else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing second argument!");

            //Return false
            return false;
        }

        //Check for parkour
        if (Main.getInstance().getParkourManager().getParkour(parkourName) == null) {
            //Send message to sender
            if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Invalid second argument!");
            else sender.sendMessage(CommandUtils.getConsolePrefix() + "Invalid second argument!");

            //Return false
            return false;
        }

        //Delete the parkour from config
        Main.getInstance().getParkourManager().getConfig().getConfig().set("parkour." + parkourName, null);

        //Remove the parkour from the parkour list
        Main.getInstance().getParkourManager().getParkours().remove(Main.getInstance().getParkourManager().getParkour(parkourName));

        //Send message to sender
        if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Successfully deleted parkour " + ChatColor.GOLD + parkourName + ChatColor.GREEN + "!");
        else sender.sendMessage(CommandUtils.getConsolePrefix() + "Successfully deleted parkour '" + parkourName + "'!");

        //Return true
        return true;

    }

    //Command start and destination
    private boolean commandStartAndDestination(CommandSender sender, Command command, String label, String[] args, String parkourName, boolean isPlayer) {

        //Check for second argument
        if (parkourName == null) {
            //Send message to sender
            if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing second argument!");
            else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing second argument!");

            //Return false
            return false;
        }

        //Check for player
        if (!isPlayer) {
            //Send message to sender
            sender.sendMessage(CommandUtils.getConsolePrefix() + "You must be a player to execute this command!");

            //Return false
            return false;
        }

        //Check for parkour
        if (Main.getInstance().getParkourManager().getParkour(parkourName) == null) {
            //Send message to sender
            sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Invalid second argument!");

            //Return false
            return false;
        }

        //Get the parkour
        Parkour parkour = Main.getInstance().getParkourManager().getParkour(parkourName);

        //Get the player position
        Player player = (Player) sender;

        //Set the start or destination position of the parkour
        if (args[0].equalsIgnoreCase("start")) parkour.setStart(player.getLocation());
        else parkour.setDestination(player.getLocation());

        //Send message to sender
        sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Successfully set the " + args[0].toLowerCase() + " position of parkour " + ChatColor.GOLD + parkourName +
                ChatColor.GREEN + " to " + ChatColor.YELLOW + player.getLocation().getX() + " | " + player.getLocation().getY() + " | " + player.getLocation().getZ() +
                " (" + player.getLocation().getYaw() + " | " + player.getLocation().getPitch() + ")" + ChatColor.GREEN + "!");

        //Return true
        return true;

    }

    //Command checkpoint
    private boolean commandCheckpoint(CommandSender sender, Command command, String label, String[] args, String parkourName, boolean isPlayer) {

        //Check for second argument
        if (parkourName == null) {
            //Send message to sender
            if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing second argument!");
            else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing second argument!");

            //Return false
            return false;
        }

        //Check for player
        if (!isPlayer) {
            //Send message to sender
            sender.sendMessage(CommandUtils.getConsolePrefix() + "You must be a player to execute this command!");

            //Return false
            return false;
        }

        //Check for parkour
        if (Main.getInstance().getParkourManager().getParkour(parkourName) == null) {
            //Send message to sender
            sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Invalid second argument!");

            //Return false
            return false;
        }

        //Get the parkour
        Parkour parkour = Main.getInstance().getParkourManager().getParkour(parkourName);

        //Get the player position
        Player player = (Player) sender;

        //Add the checkpoint to the parkour
        parkour.addCheckpoint(player.getLocation());

        //Send message to sender
        sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Successfully added checkpoint to parkour " + ChatColor.GOLD + parkourName +
                ChatColor.GREEN + " at " + ChatColor.YELLOW + player.getLocation().getX() + " | " + player.getLocation().getY() + " | " + player.getLocation().getZ() +
                " (" + player.getLocation().getYaw() + " | " + player.getLocation().getPitch() + ")" + ChatColor.GREEN + "!");

        //Return true
        return true;

    }

    //Command remove last checkpoint
    private boolean commandRemoveLastCheckpoint(CommandSender sender, Command command, String label, String[] args, String parkourName, boolean isPlayer) {

        //Check for second argument
        if (parkourName == null) {
            //Send message to sender
            if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing second argument!");
            else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing second argument!");

            //Return false
            return false;
        }

        //Check for player
        if (!isPlayer) {
            //Send message to sender
            sender.sendMessage(CommandUtils.getConsolePrefix() + "You must be a player to execute this command!");

            //Return false
            return false;
        }

        //Check for parkour
        if (Main.getInstance().getParkourManager().getParkour(parkourName) == null) {
            //Send message to sender
            sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Invalid second argument!");

            //Return false
            return false;
        }

        //Get the parkour
        Parkour parkour = Main.getInstance().getParkourManager().getParkour(parkourName);

        //Remove the last checkpoint
        if (parkour.removeLastCheckpoint()) {
            //Send message to sender
            sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Successfully removed last checkpoint of parkour " + ChatColor.GOLD + parkourName + ChatColor.GREEN + "!");

            //Return true
            return true;
        }
        else {
            //Send message to sender
            sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "No checkpoint of parkour " + ChatColor.GOLD + parkourName + ChatColor.RED + " available!");

            //Return false
            return false;
        }

    }

    //Command destination
    private boolean commandActive(CommandSender sender, Command command, String label, String[] args, String parkourName, boolean isPlayer) {

        //Check for second argument
        if (parkourName == null) {
            //Send message to sender
            if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing second argument!");
            else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing second argument!");

            //Return false
            return false;
        }

        //Check for parkour
        if (Main.getInstance().getParkourManager().getParkour(parkourName) == null) {
            //Send message to sender
            sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Invalid second argument!");

            //Return false
            return false;
        }

        //Get the parkour
        Parkour parkour = Main.getInstance().getParkourManager().getParkour(parkourName);

        //Activate or deactivate the parkour
        parkour.setActive(args[0].equalsIgnoreCase("activate"));

        //Send message to sender
        sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Successfully " + args[0].toLowerCase() + "d the parkour " + ChatColor.GOLD + parkourName + ChatColor.GREEN + "!");

        //Return true
        return true;

    }

}
