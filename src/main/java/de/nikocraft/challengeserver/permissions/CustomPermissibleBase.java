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

        //Get the rank of the player
        Rank rank = Main.getInstance().getPermissionManager().getPlayerRank(player);

        //Get all extra permissions and rank permissions of the player
        ArrayList<String> extraPermissions = Main.getInstance().getPermissionManager().getPlayerExtraPermissions(player);
        ArrayList<String> rankPermissions = Main.getInstance().getPermissionManager().getRankPermissions(rank);

        //If the extra permission list contains not permission, return false
        if (extraPermissions.contains("!" + permission)) return false;

        //If the extra permission list contains permission, return true
        if (extraPermissions.contains(permission)) return true;

        //If the rank not contains the permission, return false
        if (rankPermissions.contains("!" + permission)) return false;

        //If the rank contains the permission, return true
        if (rankPermissions.contains(permission)) return true;

        //Check for * permissions in the extra permission list
        for (String perm : extraPermissions) {
            if (perm.startsWith("!") && perm.endsWith("*") && permission.split("[.]").length >= perm.substring(1).split("[.]").length) {
                for (int i = 0; i < perm.substring(1).split("[.]").length; i++) {
                    if (!permission.split("[.]")[i].equals(perm.substring(1).split("[.]")[i]) && !perm.substring(1).split("[.]")[i].equals("*")) break;
                    else if (perm.substring(1).split("[.]")[i].equals("*")) return false;
                }
            } else if (!perm.startsWith("!") && perm.endsWith("*") && permission.split("[.]").length >= perm.split("[.]").length) {
                for (int i = 0; i < perm.split("[.]").length; i++) {
                    if (!permission.split("[.]")[i].equals(perm.split("[.]")[i]) && !perm.split("[.]")[i].equals("*")) break;
                    else if (perm.split("[.]")[i].equals("*")) return true;
                }
            }
        }

        //Check for * permissions in the rank permission list
        for (String perm : rankPermissions) {
            if (perm.startsWith("!") && perm.endsWith("*") && permission.split("[.]").length >= perm.substring(1).split("[.]").length) {
                for (int i = 0; i < perm.substring(1).split("[.]").length; i++) {
                    if (!permission.split("[.]")[i].equals(perm.substring(1).split("[.]")[i]) && !perm.substring(1).split("[.]")[i].equals("*")) break;
                    else if (perm.substring(1).split("[.]")[i].equals("*")) return false;
                }
            } else if (!perm.startsWith("!") && perm.endsWith("*") && permission.split("[.]").length >= perm.split("[.]").length) {
                for (int i = 0; i < perm.split("[.]").length; i++) {
                    if (!permission.split("[.]")[i].equals(perm.split("[.]")[i]) && !perm.split("[.]")[i].equals("*")) break;
                    else if (perm.split("[.]")[i].equals("*")) return true;
                }
            }
        }

        //Return false
        return false;

    }

}
