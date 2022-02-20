//PACKAGE
package de.nikocraft.challengeserver.commands;

//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.inventories.enderchests.Enderchest;
import de.nikocraft.challengeserver.utils.CommandUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//ENDERCHEST COMMAND CLASS
public class EnderchestCommand implements CommandExecutor {

    //OVERRIDE METHODS

    //Called, if the command send
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //If the sender is not a player
        if (!(sender instanceof Player)) {
            //Send message to sender
            sender.sendMessage(CommandUtils.getConsolePrefix() + "You must are a player to open a enderchest!");

            //Return false
            return false;
        }

        //Get the player from the sender
        Player player = (Player) sender;

        //Get the enderchest of the player
        Enderchest enderchest = Main.getInstance().getEnderchestManager().getEnderchest(player.getUniqueId());

        //Open the enderchest inventory for the player
        player.openInventory(enderchest.getInventory());

        //Return true
        return true;

    }
}
