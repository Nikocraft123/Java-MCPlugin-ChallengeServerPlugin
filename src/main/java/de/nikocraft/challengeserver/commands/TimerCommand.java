//PACKAGE
package de.nikocraft.challengeserver.commands;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.timers.Timer;
import de.nikocraft.challengeserver.utils.CommandUtils;
import de.nikocraft.challengeserver.utils.MathUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//TIMER COMMAND CLASS
public class TimerCommand implements CommandExecutor, TabCompleter {

    //OVERRIDE METHODS

    //Called, if the command send
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //Is the sender a player
        boolean isPlayer = sender instanceof Player;

        //Get the timer
        Timer timer = Main.getInstance().getTimer();

        //If no argument exist:
        if (args.length == 0) {

            //Send message to sender
            if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing arguments!");
            else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing arguments!");

            //Return false
            return false;

        }

        //Switch in argument 1:
        switch (args[0].toLowerCase()) {

            case "resume":

                //Call resume of the timer [If this failed]:
                if (!timer.TimerResume()) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "The timer is already running!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "The timer is already running!");

                    //Return false
                    return false;

                }

                //Return true
                return true;

            case "pause":

                //Call pause of the timer [If this failed]:
                if (!timer.TimerPause()) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "The timer is already paused!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "The timer is already paused!");

                    //Return false
                    return false;

                }

                //Return true
                return true;

            case "reset":

                //Call reset of the timer [If this failed]:
                if (!timer.TimerReset()) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "The timer is already reset!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "The timer is already reset!");

                    //Return false
                    return false;

                }

                //Return true
                return true;

            case "time":

                //If argument 2 not exist:
                if (args.length != 2) {

                    //Send the current time to sender
                    if (isPlayer) sender.sendMessage(Timer.getChatPrefix() + "The current time is " +
                            MathUtils.convertSecondsToFormattedTime(timer.getTime(), true) + " (" + timer.getTime() + ")!");
                    else sender.sendMessage(Timer.getConsolePrefix() + "The current time is " +
                            MathUtils.convertSecondsToFormattedTime(timer.getTime(), false) + " (" + timer.getTime() + ")!");

                    //Return true
                    return true;

                }

                try {
                    //Call time of the timer [If this failed]:
                    if (!timer.TimerTime(Integer.parseInt(args[1]))) {

                        //Send message to sender
                        if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "The time of the timer is already at " + args[1] + "!");
                        else sender.sendMessage(CommandUtils.getConsolePrefix() + "The time of the timer is already at " + args[1] + "!");

                        //Return false
                        return false;

                    }
                }
                catch (NumberFormatException e) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Invalid time!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Invalid time!");

                    //Return false
                    return false;

                }

                //Return true
                return true;

            case "mode":

                //If argument 2 not exist:
                if (args.length != 2) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing second argument!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing second argument!");

                    //Return false
                    return false;

                }

                //Check for invalid modes
                if (!Arrays.asList("stop", "count").contains(args[1])) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Invalid mode!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Invalid mode!");

                    //Return false
                    return false;

                }

                //Call mode of the timer [If this failed]:
                if (!timer.TimerMode(args[1])) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "The mode of the timer is already " + args[1] + "!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "The mode of the timer is already " + args[1] + "!");

                    //Return false
                    return false;

                }

                //Return true
                return true;

            case "help":
            case "h":

                //Send help message to sender
                if (isPlayer) {
                    sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.AQUA + "Help for the command " + ChatColor.YELLOW + "/timer" +
                            ChatColor.GRAY + ":\n \n" + ChatColor.AQUA + "Alias" + ChatColor.GRAY + ": " + ChatColor.YELLOW + "/tm" +
                            "\n \n" + ChatColor.AQUA + "Usage" + ChatColor.GRAY + ":\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/time help|h\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/time resume\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/time pause\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/time reset\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/time time <time>\n" +
                            ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "/time mode <stop/count>\n");
                }
                else {
                    sender.sendMessage(CommandUtils.getConsolePrefix() + "Help for the command '/timer':\n \nAlias: '/tm'" +
                            "\n \nUsage:\n" +
                            "- /time help|h\n" +
                            "- /time resume\n" +
                            "- /time pause\n" +
                            "- /time reset\n" +
                            "- /time time <time>\n" +
                            "- /time mode <stop/count>\n");
                }

                //Return true
                return true;

            default: {

                //Send message to sender
                if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Invalid first argument!");
                else sender.sendMessage(CommandUtils.getConsolePrefix() + "Invalid first argument!");

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

        //Switch in the count of arguments
        switch (args.length) {

            case 1:

                //Add commands to the list
                result.add("resume");
                result.add("pause");
                result.add("reset");
                result.add("time");
                result.add("mode");
                result.add("help");

            case 2:

                //If the first argument is "mode"
                if (args[0].equals("mode")) {
                    //Add modes to the list
                    result.add("stop");
                    result.add("count");
                }

                //If the first argument is "time"
                else if (args[0].equals("time")) {
                    //Add times to the list
                    result.add("60");
                    result.add("600");
                    result.add("1800");
                    result.add("3600");
                    result.add("7200");
                    result.add("21600");
                    result.add("43200");
                    result.add("86400");
                }

        }

        //Return the formatted result
        return CommandUtils.formatTapCompleterList(result, args);

    }

}
