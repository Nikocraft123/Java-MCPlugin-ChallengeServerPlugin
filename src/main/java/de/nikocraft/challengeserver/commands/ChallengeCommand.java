//PACKAGE
package de.nikocraft.challengeserver.commands;


//IMPORTS
import de.nikocraft.challengeserver.challenges.Challenge;
import de.nikocraft.challengeserver.utils.CommandUtils;
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

        //Return true
        return true;

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
                result.add("start");
                result.add("stop");
                result.add("help");

            case 2:

                //If argument 1 is "config"
                if (args[0].equals("config")) {
                    //For in all challenges
                    for (Challenge challenge : Challenge.values()) {
                        //Add the name of the challenge to the list
                        result.add(challenge.name().toLowerCase());
                    }
                }

            case 3:

                //If argument 1 is "config"
                if (args[0].equals("config")) {
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