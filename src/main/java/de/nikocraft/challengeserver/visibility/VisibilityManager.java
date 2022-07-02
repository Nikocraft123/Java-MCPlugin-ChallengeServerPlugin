//PACKAGE
package de.nikocraft.challengeserver.visibility;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


//VISIBILITY MANAGER CLASS
public class VisibilityManager {

    //VARIABLES

    //The list of the hidden players
    private List<Player> hidden;


    //CONSTRUCTOR
    public VisibilityManager() {

        //Define the list
        hidden = new ArrayList<>();

    }


    //METHODS

    //Is a player hidden
    public boolean isHidden(Player player) {

        //Return is the player in the list
        return hidden.contains(player);

    }

    //Hide
    public void hide(Player player) {

        //Add the player to the list
        hidden.add(player);

        //Loop for all players
        for (Player p : Bukkit.getOnlinePlayers()) {

            //If it is the player itself, continue
            if (player.equals(p)) continue;

            //Hide the player
            player.hidePlayer(Main.getInstance(), p);

        }

    }

    //Show
    public void show(Player player) {

        //Remove the player from the list
        hidden.remove(player);

        //Loop for all players
        for (Player p : Bukkit.getOnlinePlayers()) {

            //If it is the player itself, continue
            if (player.equals(p)) continue;

            //Show the player
            player.showPlayer(Main.getInstance(), p);

        }

    }

}
