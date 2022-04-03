//PACKAGE
package de.nikocraft.challengeserver.worlds;


import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

//WORLD MANAGER CLASS
public class WorldManager {

    //VARIABLES

    //The world config (from main)
    private final Config config;


    //CONSTRUCTOR
    public WorldManager() {

        //Get the world config from main
        config = Main.getInstance().getWorldConfig();

    }


    //METHODS

    //Get player position
    public Location getPlayerPosition(Player player) {

        //Define the position with world spawn point
        Location position = Bukkit.getWorld("world").getSpawnLocation().clone();

        //If the player position contains in config, set the position to the players position
        if (config.getConfig().contains("positions." + player.getUniqueId().toString() + ".x"))
            position.setX(config.getConfig().getDouble("positions." + player.getUniqueId().toString() + ".x"));
        if (config.getConfig().contains("positions." + player.getUniqueId().toString() + ".y"))
            position.setY(config.getConfig().getDouble("positions." + player.getUniqueId().toString() + ".y"));
        if (config.getConfig().contains("positions." + player.getUniqueId().toString() + ".z"))
            position.setZ(config.getConfig().getDouble("positions." + player.getUniqueId().toString() + ".z"));
        if (config.getConfig().contains("positions." + player.getUniqueId().toString() + ".world"))
            position.setWorld(Bukkit.getWorld(config.getConfig().getString("positions." + player.getUniqueId().toString() + ".world")));

        //Return the position
        return position;

    }

    //Set player position
    public void setPlayerPosition(Player player) {

        //Get the position of the player
        Location position = player.getLocation();

        //Save the player position to config
        config.getConfig().set("positions." + player.getUniqueId().toString() + ".x", position.getX());
        config.getConfig().set("positions." + player.getUniqueId().toString() + ".y", position.getY());
        config.getConfig().set("positions." + player.getUniqueId().toString() + ".z", position.getZ());
        config.getConfig().set("positions." + player.getUniqueId().toString() + ".world", position.getWorld());

        //Save the configuration to update changes
        config.save();

    }

    //Save all player positions
    public void save() {

        //Loop for all online players
        for (Player player : Bukkit.getOnlinePlayers()) {

            //Check if the player is in the game world
            if (player.getLocation().getWorld().getName().equals("world")) {

                //Save the player position
                setPlayerPosition(player);

            }

        }

    }

}
