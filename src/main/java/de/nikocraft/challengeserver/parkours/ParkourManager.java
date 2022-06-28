//PACKAGE
package de.nikocraft.challengeserver.parkours;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import java.util.ArrayList;
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

        //Load the parkour list
        load();

    }


    //METHODS

    //Load parkour data
    public void load() {

        //Define the parkour list
        parkours = new ArrayList<>();

        //Loop for every parkour in the index
        for (String parkourName : config.getConfig().getStringList("indices.parkour")) {

            //Load all data
            Location start = Bukkit.getWorld("lobby").getSpawnLocation().clone();
            if (config.getConfig().contains("parkour." + parkourName + ".start.x"))
                start.setX(config.getConfig().getDouble("parkour." + parkourName + ".start.x"));
            if (config.getConfig().contains("parkour." + parkourName + ".start.y"))
                start.setY(config.getConfig().getDouble("parkour." + parkourName + ".start.y"));
            if (config.getConfig().contains("parkour." + parkourName + ".start.z"))
                start.setZ(config.getConfig().getDouble("parkour." + parkourName + ".start.z"));
            if (config.getConfig().contains("parkour." + parkourName + ".start.yaw"))
                start.setYaw((float) config.getConfig().getDouble("parkour." + parkourName + ".start.yaw"));
            if (config.getConfig().contains("parkour." + parkourName + ".start.pitch"))
                start.setPitch((float) config.getConfig().getDouble("parkour." + parkourName + ".start.pitch"));
            Location destination = Bukkit.getWorld("lobby").getSpawnLocation().clone();
            if (config.getConfig().contains("parkour." + parkourName + ".destination.x"))
                destination.setX(config.getConfig().getDouble("parkour." + parkourName + ".destination.x"));
            if (config.getConfig().contains("parkour." + parkourName + ".destination.y"))
                destination.setY(config.getConfig().getDouble("parkour." + parkourName + ".destination.y"));
            if (config.getConfig().contains("parkour." + parkourName + ".destination.z"))
                destination.setZ(config.getConfig().getDouble("parkour." + parkourName + ".destination.z"));
            if (config.getConfig().contains("parkour." + parkourName + ".destination.yaw"))
                destination.setYaw((float) config.getConfig().getDouble("parkour." + parkourName + ".destination.yaw"));
            if (config.getConfig().contains("parkour." + parkourName + ".destination.pitch"))
                destination.setPitch((float) config.getConfig().getDouble("parkour." + parkourName + ".destination.pitch"));
            boolean active = false;
            if (config.getConfig().contains("parkour." + parkourName + ".active"))
                active = config.getConfig().getBoolean("parkour." + parkourName + ".active");
            int checkpoint_count = 0;
            if (config.getConfig().contains("parkour." + parkourName + ".checkpoint_count"))
                checkpoint_count = config.getConfig().getInt("parkour." + parkourName + ".checkpoint_count");
            List<Location> checkpoints = new ArrayList<>();
            for (int i = 0; i < checkpoint_count; i++) {
                Location checkpoint = Bukkit.getWorld("lobby").getSpawnLocation().clone();
                if (config.getConfig().contains("parkour." + parkourName + ".checkpoints." + i + ".x"))
                    checkpoint.setX(config.getConfig().getDouble("parkour." + parkourName + ".checkpoints." + i + ".x"));
                if (config.getConfig().contains("parkour." + parkourName + ".checkpoints." + i + ".y"))
                    checkpoint.setY(config.getConfig().getDouble("parkour." + parkourName + ".checkpoints." + i + ".y"));
                if (config.getConfig().contains("parkour." + parkourName + ".checkpoints." + i + ".z"))
                    checkpoint.setZ(config.getConfig().getDouble("parkour." + parkourName + ".checkpoints." + i + ".z"));
                if (config.getConfig().contains("parkour." + parkourName + ".checkpoints." + i + ".yaw"))
                    checkpoint.setYaw((float) config.getConfig().getDouble("parkour." + parkourName + ".checkpoints." + i + ".yaw"));
                if (config.getConfig().contains("parkour." + parkourName + ".checkpoints." + i + ".pitch"))
                    checkpoint.setPitch((float) config.getConfig().getDouble("parkour." + parkourName + ".checkpoints." + i + ".pitch"));
                checkpoints.add(checkpoint);
            }

            //Define the parkour
            Parkour parkour = new Parkour(parkourName, start, destination, checkpoints, active);

            //Add the parkour to the list
            parkours.add(parkour);

        }

    }

    //Save parkour data
    public void save() {

        //Define parkour index
        List<String> index = new ArrayList<>();

        //Loop for every parkour
        for (Parkour parkour : parkours) {

            //Add the parkour name to the parkour index
            index.add(parkour.getName());

            //Save the parkour data to config
            config.getConfig().set("parkour." + parkour.getName() + ".start.x", parkour.getStart().getX());
            config.getConfig().set("parkour." + parkour.getName() + ".start.y", parkour.getStart().getY());
            config.getConfig().set("parkour." + parkour.getName() + ".start.z", parkour.getStart().getZ());
            config.getConfig().set("parkour." + parkour.getName() + ".start.yaw", (double) parkour.getStart().getYaw());
            config.getConfig().set("parkour." + parkour.getName() + ".start.pitch", (double) parkour.getStart().getPitch());
            config.getConfig().set("parkour." + parkour.getName() + ".destination.x", parkour.getDestination().getX());
            config.getConfig().set("parkour." + parkour.getName() + ".destination.y", parkour.getDestination().getY());
            config.getConfig().set("parkour." + parkour.getName() + ".destination.z", parkour.getDestination().getZ());
            config.getConfig().set("parkour." + parkour.getName() + ".destination.yaw", (double) parkour.getDestination().getYaw());
            config.getConfig().set("parkour." + parkour.getName() + ".destination.pitch", (double) parkour.getDestination().getPitch());
            config.getConfig().set("parkour." + parkour.getName() + ".active", parkour.isActive());
            config.getConfig().set("parkour." + parkour.getName() + ".checkpoint_count", parkour.getCheckpoints().size());
            for (Location checkpoint : parkour.getCheckpoints()) {
                config.getConfig().set("parkour." + parkour.getName() + ".checkpoints." + parkour.getCheckpoints().indexOf(checkpoint) + ".x", checkpoint.getX());
                config.getConfig().set("parkour." + parkour.getName() + ".checkpoints." + parkour.getCheckpoints().indexOf(checkpoint) + ".y", checkpoint.getY());
                config.getConfig().set("parkour." + parkour.getName() + ".checkpoints." + parkour.getCheckpoints().indexOf(checkpoint) + ".z", checkpoint.getZ());
                config.getConfig().set("parkour." + parkour.getName() + ".checkpoints." + parkour.getCheckpoints().indexOf(checkpoint) + ".yaw", checkpoint.getYaw());
                config.getConfig().set("parkour." + parkour.getName() + ".checkpoints." + parkour.getCheckpoints().indexOf(checkpoint) + ".pitch", checkpoint.getPitch());
            }

        }

        //Save the parkour index list in the config
        config.getConfig().set("indices.parkour", index);

        //Save the configuration to update changes
        config.save();

    }

    //Get parkour
    public Parkour getParkour(String name) {

        //Loop for every parkour
        for (Parkour parkour : parkours) {
            if (parkour.getName().equals(name)) return parkour;
        }

        //If no parkour found, return null
        return null;

    }


    //STATIC METHODS

    //Get the prefix of the parkour for the chat
    public static String getChatPrefix() {

        //Return prefix string
        return ChatColor.GRAY + "[" + ChatColor.DARK_GREEN + ChatColor.BOLD + "Parkour" + ChatColor.RESET +
                ChatColor.GRAY + "] " + ChatColor.WHITE;

    }


    //GETTERS

    //The array list for the parkour data
    public List<Parkour> getParkours() { return parkours; }

    //The world config (from main)
    public Config getConfig() { return config; }

}
