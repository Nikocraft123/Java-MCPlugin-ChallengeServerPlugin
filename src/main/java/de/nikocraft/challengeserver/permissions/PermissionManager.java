//PACKAGE
package de.nikocraft.challengeserver.permissions;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.tablists.TablistManager;
import de.nikocraft.challengeserver.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.ArrayList;


//PERMISSION MANAGER CLASS
public class PermissionManager {

    //VARIABLES

    //The permission config (from main)
    private final Config config;


    //CONSTRUCTOR
    public PermissionManager() {

        //Get the permission config from main
        config = Main.getInstance().getPermissionConfig();

    }


    //METHODS

    //Get all extra permissions of a player
    public ArrayList<String> getPlayerExtraPermissions(Player player) {

        //If the extra permission list contains in config
        if (config.getConfig().contains("player." + player.getUniqueId().toString() + ".extra_permissions")) {
            //Return the permission list from config
            return (ArrayList<String>) config.getConfig().getStringList("player." + player.getUniqueId().toString() + ".extra_permissions");
        }

        //Return a new empty arraylist
        return new ArrayList<>();

    }

    //Get all permissions of a rank
    public ArrayList<String> getRankPermissions(Rank rank) {

        //If the rank permission list contains in config
        if (config.getConfig().contains("rank." + rank.getRankName() + ".permissions")) {
            //Return the permission list from config
            return (ArrayList<String>) config.getConfig().getStringList("rank." + rank.getRankName() + ".permissions");
        }

        //Return a new empty arraylist
        return new ArrayList<>();

    }

    //Add an extra permission to a player
    public boolean addPlayerExtraPermission(Player player, String permission) {

        //If the permission already contains, return false
        if (getPlayerExtraPermissions(player).contains(permission)) return false;

        //Get player permission list
        ArrayList<String> permissions = getPlayerExtraPermissions(player);

        //Add the new permission
        permissions.add(permission);

        //Set the permission list to config
        config.getConfig().set("player." + player.getUniqueId().toString() + ".extra_permissions", permissions);

        //Save the configuration to update changes
        config.save();

        //Update the player commands
        player.updateCommands();

        //Return true
        return true;

    }

    //Remove an extra permission to a player
    public boolean removePlayerExtraPermission(Player player, String permission) {

        //If the permission doesn't contain, return false
        if (!getPlayerExtraPermissions(player).contains(permission)) return false;

        //Get player permission list
        ArrayList<String> permissions = getPlayerExtraPermissions(player);

        //Remove the permission
        permissions.remove(permission);

        //Set the permission list to config
        config.getConfig().set("player." + player.getUniqueId().toString() + ".extra_permissions", permissions);

        //Save the configuration to update changes
        config.save();

        //Update the player commands
        player.updateCommands();

        //Return true
        return true;

    }

    //Add a permission to a rank
    public boolean addRankPermission(Rank rank, String permission) {

        //If the permission already contains, return false
        if (getRankPermissions(rank).contains(permission)) return false;

        //Get the rank permission list
        ArrayList<String> permissions = getRankPermissions(rank);

        //Add the new permission
        permissions.add(permission);

        //Set the permission list to config
        config.getConfig().set("rank." + rank.getRankName() + ".permissions", permissions);

        //Save the configuration to update changes
        config.save();

        //Update the player commands
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.updateCommands();
        }

        //Return true
        return true;

    }

    //Remove a permission to a rank
    public boolean removeRankPermission(Rank rank, String permission) {

        //If the permission doesn't contains, return false
        if (!getRankPermissions(rank).contains(permission)) return false;

        //Get the rank permission list
        ArrayList<String> permissions = getRankPermissions(rank);

        //Remove the permission
        permissions.remove(permission);

        //Set the permission list to config
        config.getConfig().set("rank." + rank.getRankName() + ".permissions", permissions);

        //Save the configuration to update changes
        config.save();

        //Update the player commands
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.updateCommands();
        }

        //Return true
        return true;

    }

    //Set the rank of a player
    public boolean setPlayerRank(Player player, Rank rank) {

        //If the player has a rank in config
        if (config.getConfig().contains("player." + player.getUniqueId().toString() + ".rank")) {
            //Return false, if the current rank is equals to the new rank
            if (config.getConfig().getString("player." + player.getUniqueId().toString() + ".rank").equals(rank.getRankName())) return false;
        }

        //Set the rank
        config.getConfig().set("player." + player.getUniqueId().toString() + ".rank", rank.getRankName());

        //Save the configuration to update changes
        config.save();

        //Update the player commands
        player.updateCommands();

        //Update all tablist
        TablistManager.setAllPlayerTeams();

        //Return true
        return true;

    }

    //Get the rank of a player
    public Rank getPlayerRank(Player player) {

        //If the player has a rank in config
        if (config.getConfig().contains("player." + player.getUniqueId().toString() + ".rank")) {
            //Return the player rank from config
            return Rank.fromRankName(config.getConfig().getString("player." + player.getUniqueId().toString() + ".rank"));
        }

        //Set the rank to guest
        config.getConfig().set("player." + player.getUniqueId().toString() + ".rank", Rank.Guest.getRankName());

        //Save the configuration to update changes
        config.save();

        //Update the player commands
        player.updateCommands();

        //Return guest rank
        return Rank.Guest;

    }

}
