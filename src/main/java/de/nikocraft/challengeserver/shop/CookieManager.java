//PACKAGE
package de.nikocraft.challengeserver.shop;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.utils.Config;
import org.bukkit.entity.Player;
import java.util.*;


//COOKIE MANAGER CLASS
public class CookieManager {

    //VARIABLES

    //The main config (from main)
    private final Config config;

    //The map of the cookies amount of the players
    private final Map<UUID, Integer> cookies;

    //The map of the cookies level of the players
    private final Map<UUID, Integer> levels;


    //CONSTRUCTOR
    public CookieManager() {

        //Get the world config from main
        config = Main.getInstance().getMainConfig();

        //Create the maps
        cookies = new HashMap<>();
        levels = new HashMap<>();

        //Load the parkour list
        load();

    }


    //METHODS

    //Get player cookies
    public int getCookies(Player player) {

        //If the cookie amount was found, return it
        if (cookies.containsKey(player.getUniqueId())) return cookies.get(player.getUniqueId());

        //Add the player to the maps
        cookies.put(player.getUniqueId(), 0);
        levels.put(player.getUniqueId(), 1);

        //Return 0
        return 0;

    }

    //Get player level
    public int getLevel(Player player) {

        //If the cookie level was found, return it
        if (levels.containsKey(player.getUniqueId())) return levels.get(player.getUniqueId());

        //Add the player to the maps
        cookies.put(player.getUniqueId(), 0);
        levels.put(player.getUniqueId(), 1);

        //Return 0
        return 1;

    }

    //Set player cookies
    public void setCookies(Player player, int amount) {

        //Set the amount
        cookies.put(player.getUniqueId(), amount);

        //Update the info
        Main.getInstance().getCoinCookieInfo().renderInfo(player);

    }

    //Set player level
    public void setLevel(Player player, int level) {

        //Set the level
        levels.put(player.getUniqueId(), level);

        //Update the info
        Main.getInstance().getCoinCookieInfo().renderInfo(player);

    }

    //Load cookie data
    public void load() {

        //Loop for all uuids
        for (String uuid : config.getConfig().getStringList("uuids.shop")) {

            //Load the uuid cookies amount
            if (config.getConfig().contains("shop.cookies." + uuid + ".amount"))
                cookies.put(UUID.fromString(uuid), config.getConfig().getInt("shop.cookies." + uuid + ".amount"));
            else
                cookies.put(UUID.fromString(uuid), 0);

            //Load the uuid cookies level
            if (config.getConfig().contains("shop.cookies." + uuid + ".level"))
                levels.put(UUID.fromString(uuid), config.getConfig().getInt("shop.cookies." + uuid + ".level"));
            else
                levels.put(UUID.fromString(uuid), 1);

        }

    }

    //Save parkour data
    public void save() {

        //Define uuid index
        List<String> index = new ArrayList<>();

        //Loop for every uuid
        for (UUID uuid : cookies.keySet()) {

            //Add the uuid to the index
            index.add(uuid.toString());

            //Save the uuid cookies amount
            config.getConfig().set("shop.cookies." + uuid + ".amount", cookies.get(uuid));

            //Save the uuid cookies level
            if (levels.containsKey(uuid)) config.getConfig().set("shop.cookies." + uuid + ".level", levels.get(uuid));
            else config.getConfig().set("shop.cookies." + uuid + ".level", 1);

        }

        //Save the uuid index list in the config
        config.getConfig().set("uuids.shop", index);

        //Save the configuration to update changes
        config.save();

    }

}
