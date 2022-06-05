//PACKAGE
package de.nikocraft.challengeserver.listeners;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.minigames.Parkour;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
            if (player.getLocation().getY() < 40.0) {
                Main.getInstance().getMultiverseCore().teleportPlayer(player, player, new Location(Bukkit.getWorld("lobby"), 0.5, 100, 0.5, 0, 0));
            }

            //Check for lobby area exiting
            if (player.getLocation().getX() < -100.0 | player.getLocation().getX() > 100.0 |
                    player.getLocation().getZ() < -100.0 | player.getLocation().getZ() > 100.0) {
                Main.getInstance().getMultiverseCore().teleportPlayer(player, player, new Location(Bukkit.getWorld("lobby"), 0.5, 100, 0.5, 0, 0));
            }

            //Check for portal
            if (player.getLocation().getX() >= -5.0 & player.getLocation().getX() <= 6.0 &
                    player.getLocation().getY() >= 100.0 & player.getLocation().getY() <= 110.0 &
                    player.getLocation().getZ() >= 39.0 & player.getLocation().getZ() <= 40.0) {
                Main.getInstance().getCommand("game").execute(player, "game", new String[]{});
            }

            //Handle parkour
            for (Parkour parkour : Main.getInstance().getParkourManager().getParkours()) {
                parkour.handleMovement(event);
            }

        }

    }

}
