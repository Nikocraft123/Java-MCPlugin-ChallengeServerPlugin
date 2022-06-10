//PACKAGE
package de.nikocraft.challengeserver.minigames;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//PARKOUR CLASS
public class Parkour {

    //VARIABLES

    //The parkour name
    private String name;

    //The parkour start
    private Location start;

    //The parkour destination
    private Location destination;

    //The checkpoint list of the parkour
    private final List<Location> checkpoints;

    //Is the parkour active
    private boolean active;

    //The player map for last checkpoint
    private final Map<String, Integer> players;


    //CONSTRUCTORS
    public Parkour(String name) {

        //Set the name
        this.name = name;

        //Define the start, destination, checkpoint list and active
        this.start = new Location(Bukkit.getWorld("lobby"), 0.5, 100, 0.5, 0, 0);
        this.destination = new Location(Bukkit.getWorld("lobby"), 0.5, 100, 0.5, 0, 0);
        this.checkpoints = new ArrayList<>();
        this.active = false;

        //Define the player map
        players = new HashMap<>();

    }

    public Parkour(String name, Location start, Location destination, List<Location> checkpoints, boolean active) {

        //Set the parkour information
        this.name = name;
        this.start = start;
        this.destination = destination;
        this.checkpoints = checkpoints;
        this.active = active;

        //Define the player map
        players = new HashMap<>();

    }


    //METHODS

    //Add parkour checkpoint
    public void addCheckpoint(Location checkpoint) {

        //Add the checkpoint to the checkpoint list
        checkpoints.add(checkpoint);

    }

    //Remove the last parkour checkpoint
    public boolean removeLastCheckpoint() {

        //If no checkpoint is available, return false
        if (checkpoints.size() == 0) return false;

        //Delete the checkpoint from config
        Main.getInstance().getParkourManager().getConfig().getConfig().set("parkour." + name + ".checkpoints." + (checkpoints.size() - 1), null);

        //Remove the checkpoint from the checkpoint list
        checkpoints.remove(checkpoints.size() - 1);

        //Return true
        return true;

    }

    //Add a player to the map
    public boolean addPlayer(Player player) {

        //If the player is already in the map, cancel
        if (players.containsKey(player.getName())) return false;

        //Add the player name to the map
        players.put(player.getName(), 0);

        //Return true
        return true;

    }

    //Remove a player from the map
    public boolean removePlayer(Player player) {

        //If the player is not in the map, cancel
        if (!players.containsKey(player.getName())) return false;

        //Remove the player name from the map
        players.remove(player.getName());

        //Return true
        return true;

    }

    //Get the checkpoint of a player
    public int getCheckpoint(Player player) {

        //If the player is not in the map, return -1
        if (!players.containsKey(player.getName())) return -1;

        //Return the player checkpoint from the map
        return players.get(player.getName());

    }

    //Set the checkpoint of a player
    public boolean setCheckpoint(Player player, int checkpoint) {

        //If the player is not in the map, return false
        if (!players.containsKey(player.getName())) return false;

        //If the player already reached this checkpoint, return false
        if (players.get(player.getName()) == checkpoint) return false;

        //Set the player checkpoint to the map
        players.put(player.getName(), checkpoint);

        //Return true
        return true;

    }

    //Handle movement
    public void handleMovement(PlayerMoveEvent event) {

        //Check for start
        if (start.distance(event.getPlayer().getLocation()) < 1.5) {

            //If the player is already in the map, cancel
            if (!players.containsKey(event.getPlayer().getName())) {

                //Remove the player from all other parkours
                for (Parkour parkour : Main.getInstance().getParkourManager().getParkours()) {
                    parkour.removePlayer(event.getPlayer());
                }

                //Add the player to the parkour
                addPlayer(event.getPlayer());

                //Set player inventory
                Main.getInstance().getParkourManager().setPlayerInventoryItems(event.getPlayer());

                //Send a message to the player
                event.getPlayer().sendMessage(ParkourManager.getChatPrefix() + ChatColor.GREEN +
                        "Welcome to this parkour! Can you finish it?\n \n" + ChatColor.YELLOW +
                        "Use the items in your hotbar to teleport to your last checkpoint or exit the parkour. " +
                        "Alternatively you can use the commands '/parkour_checkpoint' and '/parkour_cancel'.");

            }

        }

        //Check for destination
        if (destination.distance(event.getPlayer().getLocation()) < 1.5) {

            //Remove the player from the parkour
            if (removePlayer(event.getPlayer()))
                event.getPlayer().sendMessage(ParkourManager.getChatPrefix() + ChatColor.GREEN +
                        "CONGRATULATION! You finished this parkour successfully!\n");

            //Teleport the player to spawn
            Main.getInstance().getMultiverseCore().teleportPlayer(event.getPlayer(), event.getPlayer(), new Location(Bukkit.getWorld("lobby"), 0.5, 100, 0.5, 0, 0));

        }

        //Check for checkpoints
        for (Location checkpoint : checkpoints) {
            if (checkpoint.distance(event.getPlayer().getLocation()) < 1.5) {

                //Set the player checkpoint to the parkour
                if (setCheckpoint(event.getPlayer(), checkpoints.indexOf(checkpoint) + 1))
                    event.getPlayer().sendMessage(ParkourManager.getChatPrefix() + ChatColor.GREEN +
                            "CONGRATULATION! You successfully reached checkpoint " + (checkpoints.indexOf(checkpoint) + 1) + ". Hang on!\n");

            }
        }

    }


    //GETTERS

    //The parkour name
    public String getName() { return name; }

    //The parkour start
    public Location getStart() { return start; }

    //The parkour destination
    public Location getDestination() { return destination; }

    //The checkpoint list of the parkour
    public List<Location> getCheckpoints() { return checkpoints; }

    //Is the parkour active
    public boolean isActive() { return active; }


    //SETTERS

    //The parkour start
    public void setStart(Location start) { this.start = start; }

    //The parkour destination
    public void setDestination(Location destination) { this.destination = destination; }

    //Is the parkour active
    public void setActive(boolean active) { this.active = active; }

}
