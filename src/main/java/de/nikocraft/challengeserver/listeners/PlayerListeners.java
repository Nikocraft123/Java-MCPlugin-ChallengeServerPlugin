//PACKAGE
package de.nikocraft.challengeserver.listeners;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;


//PLAYER LISTENER CLASS
public class PlayerListeners implements Listener {

    //EVENT HANDLER METHODS

    //Called, if a player moved
    @EventHandler
    public void onMove(PlayerMoveEvent event) {

        //Get the player
        Player player = event.getPlayer();

        //Check if the player is in the lobby
        if (player.getLocation().getWorld().getName().equals("lobby")) {

            //Check for void
            if (player.getLocation().getY() < 60.0) {
                Main.getInstance().getCommand("lobby").execute(player, "lobby", new String[]{});
            }

            //Check for portal
            if (player.getLocation().getX() >= -5.0 & player.getLocation().getX() <= 6.0 &
                    player.getLocation().getY() >= 100.0 & player.getLocation().getY() <= 110.0 &
                    player.getLocation().getZ() >= 39.0 & player.getLocation().getZ() <= 40.0) {
                Main.getInstance().getCommand("game").execute(player, "game", new String[]{});
            }

        }

    }

}
