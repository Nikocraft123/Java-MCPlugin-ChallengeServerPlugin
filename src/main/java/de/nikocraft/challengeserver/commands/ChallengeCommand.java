//PACKAGE
package de.nikocraft.challengeserver.commands;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.challenges.Challenge;
import de.nikocraft.challengeserver.minigames.parkours.Parkour;
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


//CHALLENGE COMMAND CLASS
public class ChallengeCommand implements CommandExecutor, TabCompleter {

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

            case "config": {

                //Get the active challenge
                Challenge challenge = Main.getInstance().getChallengeManager().getActiveChallenge();

                //If the challenge is not null
                if (challenge != null) {

                    //Get the challenge execute
                    return challenge.configCommandExecute(sender, command, label, args, isPlayer);

                }

                //Send message to sender
                if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "No challenge selected!");
                else sender.sendMessage(CommandUtils.getConsolePrefix() + "No challenge selected!");

                //Return false
                return false;

            }

            case "select": {

                //If the argument 2 missed
                if (args.length < 2) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing second argument!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing second argument!");

                    //Return false
                    return false;

                }

                //Check for the challenge
                if (!Main.getInstance().getChallengeManager().getChallenges().containsKey(args[1].toLowerCase()) && !args[1].equalsIgnoreCase("none")) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "No challenge with the given name found!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "No challenge with the given name found!");

                    //Return false
                    return false;

                }

                //Get the active challenge
                Challenge challenge = Main.getInstance().getChallengeManager().getActiveChallenge();

                //If the challenge is null
                if (challenge != null) {

                    //If the challenge is running
                    if (challenge.isRunning()) {

                        //Send message to sender
                        if (isPlayer)
                            sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "The active challenge is running! Stop it first ...");
                        else sender.sendMessage(CommandUtils.getConsolePrefix() + "The active challenge is running! Stop it first ...");

                        //Return false
                        return false;

                    }

                    //Unload the challenge
                    challenge.unload();

                }

                //If the argument 2 is none
                if (args[1].equalsIgnoreCase("none")) {

                    //Set the active challenge to an empty string
                    Main.getInstance().getChallengeManager().setActiveChallenge("");

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Successfully unloaded the old challenge!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Successfully unloaded the old challenge!");

                }
                //Else
                else {

                    //Set the active challenge to the given name
                    Main.getInstance().getChallengeManager().setActiveChallenge(args[1].toLowerCase());

                    //Load the challenge
                    Main.getInstance().getChallengeManager().getActiveChallenge().load();

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Successfully unloaded the old and loaded the new challenge!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Successfully unloaded the old and loaded the new challenge!");

                }

                //Return true
                return true;

            }

            case "start": {

                //Get the active challenge
                Challenge challenge = Main.getInstance().getChallengeManager().getActiveChallenge();

                //If the challenge is null
                if (challenge != null) {

                    //If the challenge is running
                    if (challenge.isRunning()) {

                        //Send message to sender
                        if (isPlayer)
                            sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "The active challenge is already running!");
                        else sender.sendMessage(CommandUtils.getConsolePrefix() + "The active challenge is already running!");

                        //Return false
                        return false;

                    }

                    //Start the challenge
                    challenge.start();

                    //Return true
                    return true;

                }

                //Send message to sender
                if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "No challenge selected!");
                else sender.sendMessage(CommandUtils.getConsolePrefix() + "No challenge selected!");

                //Return false
                return false;

            }

            case "stop":

                //Get the active challenge
                Challenge challenge = Main.getInstance().getChallengeManager().getActiveChallenge();

                //If the challenge is null
                if (challenge != null) {

                    //If the challenge is running
                    if (!challenge.isRunning()) {

                        //Send message to sender
                        if (isPlayer)
                            sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "The active challenge is not running!");
                        else sender.sendMessage(CommandUtils.getConsolePrefix() + "The active challenge is not running!");

                        //Return false
                        return false;

                    }

                    //End the challenge
                    challenge.end();

                    //Reset the timer
                    Main.getInstance().getTimer().TimerReset();

                    //Return true
                    return true;

                }

                //Send message to sender
                if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "No challenge selected!");
                else sender.sendMessage(CommandUtils.getConsolePrefix() + "No challenge selected!");

                //Return false
                return false;

            case "help":
            case "h":

                //Send help message to sender
                if (isPlayer) {
                    sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.AQUA + "Help for the command " + ChatColor.YELLOW + "/challenge" +
                            ChatColor.GRAY + ":\n \n" + ChatColor.AQUA + "Alias" + ChatColor.GRAY + ": " + ChatColor.YELLOW + "/ch" +
                            "\n \n" + ChatColor.AQUA + "Usage" + ChatColor.GRAY + ":\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/challenge help|h\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/challenge config <challenge-specific>\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/challenge select <challenge-name>\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/challenge start\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/challenge stop\n ");
                }
                else {
                    sender.sendMessage(CommandUtils.getConsolePrefix() + "Help for the command '/challenge':\n \nAlias: '/ch'" +
                            "\n \nUsage:\n" +
                            "- /challenge help|h\n" +
                            "- /challenge config <challenge-specific>\n" +
                            "- /challenge select <challenge-name>\n" +
                            "- /challenge start\n" +
                            "- /challenge stop\n ");
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
                result.add("config");
                result.add("select");
                result.add("start");
                result.add("stop");
                result.add("help");

                break;

            case 2:

                //If the argument 1 is "select"
                if (args[0].equalsIgnoreCase("select")) {

                    //Add the name of all challenges to the list
                    result.addAll(Main.getInstance().getChallengeManager().getChallenges().keySet());
                    result.add("none");

                }

                break;

        }

        //If the length of arguments is greater equal 2
        if (args.length >= 2) {

            //If the argument 1 is "config"
            if (args[0].equalsIgnoreCase("config")) {

                //Get the active challenge
                Challenge challenge = Main.getInstance().getChallengeManager().getActiveChallenge();

                //If the challenge is not null
                if (challenge != null) {

                    //Get the challenge tab complete
                    return challenge.configCommandTabComplete(sender, command, label, args);

                }

            }

        }

        //Return the formatted result
        return CommandUtils.formatTapCompleterList(result, args);

    }

}