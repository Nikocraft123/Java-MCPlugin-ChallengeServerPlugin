//PACKAGE
package de.nikocraft.challengeserver.commands;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.utils.CommandUtils;
import org.bukkit.Bukkit;
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

        //TODO
        if (args[0].equals("start")) Main.getInstance().getDeathrunChallenge().load();
        else if (args[0].equals("player")) {
            if (args.length >= 2) {
                Player player = Bukkit.getPlayer(args[1]);
                if (player == null) return false;
                Main.getInstance().getDeathrunChallenge().getPositions().put(player, 0);
            } else return false;
        }

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
                result.add("select");
                result.add("start");
                result.add("stop");
                result.add("help");
                result.add("player");

                break;

            case 2:

                //If argument 1 is "select"
                if (args[0].equals("select")) {
                    //Add the name of all challenges to the list
                    result.addAll(Main.getInstance().getChallengeManager().getChallenges().keySet());
                }
                //If argument 1 is "config"
                else if (args[0].equals("config")) {
                    //Add configurations to the list
                    result.add("time");
                }
                //If argument 1 is "player"
                else if (args[0].equals("player")) {
                    //Add players to the list
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        result.add(player.getName());
                    };
                }

                break;

            case 3:

                //If argument 1 is "config" and argument 2 is "time"
                if (args[0].equals("config") & args[1].equals("time")) {
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

                break;

        }

        //Return the formatted result
        return CommandUtils.formatTapCompleterList(result, args);

    }

}