//PACKAGE
package de.nikocraft.challengeserver.commands;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.ArrayList;
import java.util.List;


//LOBBY WORLD COMMAND CLASS
public class LobbyWorldCommand implements CommandExecutor, TabCompleter {

    //OVERRIDE METHODS

    //Called, if the command send
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //Return the result
        return Main.getInstance().getCommand("world").execute(sender, "world", new String[]{"lobby"});

    }

    //Called, on typing the command in the chat
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        //Return empty list
        return new ArrayList<>();

    }

}