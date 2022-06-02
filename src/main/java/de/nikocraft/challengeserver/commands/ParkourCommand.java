//PACKAGE
package de.nikocraft.challengeserver.commands;


//IMPORTS
import de.nikocraft.challengeserver.utils.CommandUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.ArrayList;
import java.util.List;


//PARKOUR COMMAND CLASS
public class ParkourCommand implements CommandExecutor, TabCompleter {

    //OVERRIDE METHODS

    //Called, if the command send
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

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
                result.add("info");
                result.add("create");
                result.add("delete");
                result.add("start");
                result.add("destination");
                result.add("checkpoint_add");
                result.add("checkpoint_remove");
                result.add("activate");
                result.add("deactivate");
                result.add("help");

        }

        //Return the formatted result
        return CommandUtils.formatTapCompleterList(result, args);

    }

}
