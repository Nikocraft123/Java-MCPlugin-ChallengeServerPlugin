//PACKAGE
package de.nikocraft.nikocraftserver.inventories.enderchests;


//IMPORTS
import de.nikocraft.nikocraftserver.Main;
import de.nikocraft.nikocraftserver.utils.Config;
import java.io.IOException;
import java.util.*;


//ENDERCHEST MANAGER CLASS
public class EnderchestManager {

    //VARIABLES

    //The UUID enderchest map of all players enderchests
    private final Map<UUID, Enderchest> enderchests;

    //The inventory config (from main)
    private final Config config;


    //CONSTRUCTOR
    public EnderchestManager() {

        //Define the enderchest map
        enderchests = new HashMap<>();

        //Get the inventory config from main
        config = Main.getInstance().getInventoryConfig();

        //Load all enderchests
        load();

    }


    //METHODS

    //Load all enderchests
    private void load() {

        //If the UUID list contains in the config
        List<String> uuids = config.getConfig().getStringList("uuids.enderchest");

        //Loop in the UUID list
        for (String s : uuids) {

            //Get the base 64 and current page of the enderchest from config
            String base64 = config.getConfig().getString("enderchest." + s + ".page1");
            int current_page = config.getConfig().getInt("enderchest." + s + ".current_page");

            //Get the UUID from string
            UUID uuid = UUID.fromString(s);

            try {
                //Try to load the enderchest and put it into the enderchest map
                enderchests.put(uuid, new Enderchest(uuid, base64, current_page));
            } catch (IOException e) {
                //Catch Error
                e.printStackTrace();
            }

        }

    }

    //Save all enderchests
    public void save() {

        //Define UUID list
        List<String> uuids = new ArrayList<>();

        //Add all UUIDs to the UUID the list as string
        for (UUID uuid : enderchests.keySet()) uuids.add(uuid.toString());

        //Save the UUID list in the config
        config.getConfig().set("uuids.enderchest", uuids);

        //Loop for all enderchests
        for (UUID uuid : enderchests.keySet()) {

            //Save the current opened page
            config.getConfig().set("enderchest." + uuid.toString() + ".current_page", enderchests.get(uuid).getCurrentPage());

            //Save the current page inventory
            config.getConfig().set("enderchest." + uuid.toString() + ".page1", enderchests.get(uuid).toBase64());
            //TODO

        }

    }

    //Get a enderchest of a player from UUID
    public Enderchest getEnderchest(UUID uuid) {

        //If the enderchest found, return it
        if (enderchests.containsKey(uuid)) return enderchests.get(uuid);

        //Create a new enderchest
        Enderchest enderchest = new Enderchest(uuid);

        //Put the new enderchest into the enderchest map
        enderchests.put(uuid, enderchest);

        //Return the new enderchest
        return enderchest;

    }

    //Set a enderchest of a player
    public void setEnderchest(UUID uuid, Enderchest enderchest) {

        //Put the enderchest into the enderchest map
        enderchests.put(uuid, enderchest);

    }

}
