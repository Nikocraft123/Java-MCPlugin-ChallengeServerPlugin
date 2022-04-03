//PACKAGE
package de.nikocraft.challengeserver.listeners;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.permissions.CustomPermissibleBase;
import de.nikocraft.challengeserver.tablists.TablistManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftHumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import java.lang.reflect.Field;


//CONNECTION LISTENER CLASS
public class ConnectionListeners implements Listener {

    //EVENT HANDLER METHODS

    //Called, if a player logged in the server
    @EventHandler
    public void onLogin(PlayerLoginEvent event) {

        try {
            //Try to set the permissible base of the player to the custom permissible base
            Field field = CraftHumanEntity.class.getDeclaredField("perm");
            field.setAccessible(true);
            field.set(event.getPlayer(), new CustomPermissibleBase(event.getPlayer()));
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            //Catch Error
            e.printStackTrace();
        }

        //Load the rank of the player
        Main.getInstance().getPermissionManager().getPlayerRank(event.getPlayer());

    }

    //Called, if a player joined
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        //Teleport the player to lobby
        event.getPlayer().teleport(new Location(Bukkit.getWorld("lobby"), 0, 0, 0));

        //Send welcome message to player
        event.getPlayer().sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + ChatColor.UNDERLINE + "Welcome on the Challenge Server!" +
                ChatColor.GREEN + ChatColor.ITALIC + " " + event.getPlayer().getName() + "\n \n" + ChatColor.DARK_PURPLE +
                "To get help, type '/info'!\nGood fun!\n ");

        //Set join message
        event.setJoinMessage(ChatColor.GRAY + ">> " + ChatColor.DARK_GREEN + ChatColor.BOLD + event.getPlayer().getName() +
                ChatColor.RESET + ChatColor.GRAY + " joined the server!");

        //Set the tablist of the player
        TablistManager.setTablistHeaderFooter(event.getPlayer());
        TablistManager.setAllPlayerTeams();

    }

    //Called, if a player quited
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        //Check if the player is in the game world
        if (event.getPlayer().getLocation().getWorld().getName().equals("world")) {

            //Save the player position
            Main.getInstance().getWorldManager().setPlayerPosition(event.getPlayer());

        }

        //Set quit message
        event.setQuitMessage(ChatColor.GRAY + "<< " + ChatColor.DARK_RED + ChatColor.BOLD + event.getPlayer().getName() +
                ChatColor.RESET + ChatColor.GRAY + " left the server!");

    }

}
