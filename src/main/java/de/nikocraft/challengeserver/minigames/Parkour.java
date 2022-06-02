//PACKAGE
package de.nikocraft.challengeserver.minigames;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import java.util.ArrayList;
import java.util.List;


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


    //CONSTRUCTORS
    public Parkour(String name) {

        //Set the name
        this.name = name;

        //Define the start, destination, checkpoint list and active
        this.start = new Location(Bukkit.getWorld("lobby"), 0.5, 100, 0.5, 0, 0);
        this.destination = new Location(Bukkit.getWorld("lobby"), 0.5, 100, 0.5, 0, 0);
        this.checkpoints = new ArrayList<>();
        this.active = false;

    }

    public Parkour(String name, Location start, Location destination, List<Location> checkpoints, boolean active) {

        //Set the parkour information
        this.name = name;
        this.start = start;
        this.destination = destination;
        this.checkpoints = checkpoints;
        this.active = active;

    }


    //METHODS

    //Add parkour checkpoint
    public void addCheckpoint(Location checkpoint) {

        //Add the checkpoint to the checkpoint list
        checkpoints.add(checkpoint);

    }

    //Remove the last parkour checkpoint
    public boolean removeLastCheckpoint() {

        //Delete the checkpoint from config
        Main.getInstance().getParkourManager().getConfig().getConfig().set("parkour." + name + ".checkpoints." + (checkpoints.size() - 1), null);

        //Remove the checkpoint from the checkpoint list
        checkpoints.remove(checkpoints.size() - 1);

        //Return true
        return true;

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
