//PACKAGE
package de.nikocraft.challengeserver.minigames;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;


//PARKOUR MANAGER CLASS
public class ParkourManager {

    //VARIABLES

    //The world config (from main)
    private final Config config;

    //The array list for the parkour data
    private List<Parkour> parkours;


    //CONSTRUCTOR
    public ParkourManager() {

        //Get the world config from main
        config = Main.getInstance().getWorldConfig();

    }


    //METHODS

    //Save parkour data
    public void save() {

        //Loop for every parkour
        for (Parkour parkour : parkours) {

            //Save the parkour data to config


        }

    }

}
