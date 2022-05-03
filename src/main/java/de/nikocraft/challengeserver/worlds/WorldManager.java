//PACKAGE
package de.nikocraft.challengeserver.worlds;


import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.multiverseinventories.WorldGroup;
import com.onarandombox.multiverseinventories.profile.WorldGroupManager;
import com.onarandombox.multiverseinventories.share.Sharables;
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.utils.Config;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.Arrays;

//WORLD MANAGER CLASS
public class WorldManager {

    //VARIABLES

    //The world config (from main)
    private final Config config;

    //Resetting
    private boolean resetting = false;


    //CONSTRUCTOR
    public WorldManager() {

        //Get the world config from main
        config = Main.getInstance().getWorldConfig();

    }


    //METHODS

    //Reset world
    public void resetWorld(String seed) {

        //Set resetting to true
        setResetting(true);

        //Send message
        Bukkit.broadcastMessage(getChatPrefix() + ChatColor.YELLOW + "Start world reset ...");

        //Loop for all online players
        Main.getInstance().getLogger().info(getConsolePrefix() + "Move all players to lobby ...");
        for (Player player : Bukkit.getOnlinePlayers()) {

            //Check if the player is in the game world
            if (Arrays.asList("world", "world_nether", "world_the_end").contains(player.getLocation().getWorld().getName())) {

                //Save the player position
                setPlayerPosition(player);

                //Teleport the player to lobby
                Main.getInstance().getMultiverseCore().teleportPlayer(player, player, new Location(Bukkit.getWorld("lobby"), 0.5, 100, 0.5, 0, 0));

            }

        }

        //Load world manager
        MVWorldManager worldManager = Main.getInstance().getMultiverseCore().getMVWorldManager();

        //Load group manager
        WorldGroupManager groupManager = Main.getInstance().getMultiverseInventories().getGroupManager();

        //Delete old inventory group
        Main.getInstance().getLogger().info(getConsolePrefix() + "Delete old inventory group ...");
        groupManager.removeGroup(groupManager.getGroup("world"));

        //Delete old worlds
        Main.getInstance().getLogger().info(getConsolePrefix() + "Delete old worlds ...");
        worldManager.deleteWorld("world");
        worldManager.deleteWorld("world_nether");
        worldManager.deleteWorld("world_the_end");

        //Create new worlds
        Main.getInstance().getLogger().info(getConsolePrefix() + "Create new worlds ...");
        worldManager.addWorld("world", World.Environment.NORMAL, seed, WorldType.NORMAL, true, null);
        worldManager.addWorld("world_nether", World.Environment.NETHER, seed, WorldType.NORMAL, true, null);
        worldManager.addWorld("world_the_end", World.Environment.THE_END, seed, WorldType.NORMAL, true, null);
        worldManager.getMVWorld("world").setGameMode(GameMode.SURVIVAL);
        worldManager.getMVWorld("world_nether").setGameMode(GameMode.SURVIVAL);
        worldManager.getMVWorld("world_the_end").setGameMode(GameMode.SURVIVAL);

        //Create new inventory group
        Main.getInstance().getLogger().info(getConsolePrefix() + "Create new inventory group ...");
        WorldGroup group = groupManager.newEmptyGroup("world");
        group.addWorld("world");
        group.addWorld("world_nether");
        group.addWorld("world_the_end");
        group.getShares().addAll(Sharables.allOf());
        groupManager.updateGroup(group);

        //Send message
        Bukkit.broadcastMessage(getChatPrefix() + ChatColor.GREEN + "World reset completed!");

        //Set resetting to false
        setResetting(false);

    }

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
        if (config.getConfig().contains("positions." + player.getUniqueId().toString() + ".yaw"))
            position.setYaw((float) config.getConfig().getDouble("positions." + player.getUniqueId().toString() + ".yaw"));
        if (config.getConfig().contains("positions." + player.getUniqueId().toString() + ".pitch"))
            position.setPitch((float) config.getConfig().getDouble("positions." + player.getUniqueId().toString() + ".pitch"));
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
        config.getConfig().set("positions." + player.getUniqueId().toString() + ".yaw", (double) position.getYaw());
        config.getConfig().set("positions." + player.getUniqueId().toString() + ".pitch", (double) position.getPitch());
        config.getConfig().set("positions." + player.getUniqueId().toString() + ".world", position.getWorld().getName());

        //Save the configuration to update changes
        config.save();

    }

    //Save all player positions
    public void save() {

        //Loop for all online players
        for (Player player : Bukkit.getOnlinePlayers()) {

            //Check if the player is in the game world
            if (Arrays.asList("world", "world_nether", "world_the_end").contains(player.getLocation().getWorld().getName())) {

                //Save the player position
                setPlayerPosition(player);

            }

        }

    }

    //Get the prefix of the command for the console
    public static String getConsolePrefix() {

        //Return prefix string
        return "[World] ";

    }

    //Get the prefix of the command for the chat
    public static String getChatPrefix() {

        //Return prefix string
        return ChatColor.GRAY + "[" + ChatColor.YELLOW + ChatColor.BOLD + "World" + ChatColor.RESET +
                ChatColor.GRAY + "] " + ChatColor.WHITE;

    }


    //GETTERS

    //Resetting
    public boolean isResetting() { return resetting; }


    //SETTERS

    //Resetting
    public void setResetting(boolean resetting) { this.resetting = resetting; }

}
