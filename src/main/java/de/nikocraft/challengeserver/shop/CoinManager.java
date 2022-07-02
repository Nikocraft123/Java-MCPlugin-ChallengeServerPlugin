//PACKAGE
package de.nikocraft.challengeserver.shop;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.utils.Config;
import org.bukkit.entity.Player;
import java.util.*;


//COIN MANAGER CLASS
public class CoinManager {

    //VARIABLES

    //The main config (from main)
    private final Config config;

    //The map of the coins of the players
    private final Map<UUID, Integer> coins;


    //CONSTRUCTOR
    public CoinManager() {

        //Get the world config from main
        config = Main.getInstance().getMainConfig();

        //Create the maps
        coins = new HashMap<>();

        //Load the parkour list
        load();

    }


    //METHODS

    //Get player coins
    public int getCoins(Player player) {

        //If the coins was found, return it
        if (coins.containsKey(player.getUniqueId())) return coins.get(player.getUniqueId());

        //Add the player to the maps
        coins.put(player.getUniqueId(), 0);

        //Return 0
        return 0;

    }

    //Set player coins
    public void setCookies(Player player, int amount) {

        //Set the amount
        coins.put(player.getUniqueId(), amount);

        //Update the info
        Main.getInstance().getCoinCookieInfo().renderInfo(player);

    }

    //Load cookie data
    public void load() {

        //Loop for all uuids
        for (String uuid : config.getConfig().getStringList("uuids.shop")) {

            //Load the uuid cookies amount
            if (config.getConfig().contains("shop.coins." + uuid + ".amount"))
                coins.put(UUID.fromString(uuid), config.getConfig().getInt("shop.coins." + uuid + ".amount"));
            else
                coins.put(UUID.fromString(uuid), 0);

        }

    }

    //Save parkour data
    public void save() {

        //Define uuid index
        List<String> index = new ArrayList<>();

        //Loop for every uuid
        for (UUID uuid : coins.keySet()) {

            //Add the uuid to the index
            index.add(uuid.toString());

            //Save the uuid cookies amount
            config.getConfig().set("shop.coins." + uuid + ".amount", coins.get(uuid));

        }

        //Save the uuid index list in the config
        config.getConfig().set("uuids.shop", index);

        //Save the configuration to update changes
        config.save();

    }

}
