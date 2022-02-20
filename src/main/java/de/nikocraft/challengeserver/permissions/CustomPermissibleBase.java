//PACKAGE
package de.nikocraft.challengeserver.permissions;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissibleBase;
import java.util.ArrayList;


//CUSTOM PERMISSIBLE BASE CLASS
public class CustomPermissibleBase extends PermissibleBase {

    //VARIABLES

    //The player of the base
    private final Player player;


    //CONSTRUCTOR
    public CustomPermissibleBase(Player player) {

        //Initialize the base with the player
        super(player);

        //Set the player
        this.player = player;

    }


    //OVERRIDE METHODS

    //Check for permission of the player
    @Override
    public boolean hasPermission(String permission) {

        //Get all extra permissions of the player
        ArrayList<String> extraPermissions = Main.getInstance().getPermissionManager().getPlayerExtraPermissions(player);

        //Get the rank of the player
        Rank rank = Main.getInstance().getPermissionManager().getPlayerRank(player);

        //If the extra permission list contains not permission, return false
        if (extraPermissions.contains("!" + permission)) return false;

        //If the rank not contains the permission, return false
        if (Main.getInstance().getPermissionManager().getRankPermissions(rank).contains("!" + permission)) return false;

        //If the extra permission list contains permission, return true
        if (extraPermissions.contains(permission)) return true;

        //If the rank contains the permission, return true
        if (Main.getInstance().getPermissionManager().getRankPermissions(rank).contains(permission)) return true;

        //Return, has the rank or the extra permission list *
        return Main.getInstance().getPermissionManager().getRankPermissions(rank).contains("*") || extraPermissions.contains("*");

    }

}
