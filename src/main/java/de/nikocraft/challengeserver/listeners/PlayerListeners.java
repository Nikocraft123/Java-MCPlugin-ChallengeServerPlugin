//PACKAGE
package de.nikocraft.challengeserver.listeners;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.challenges.Challenge;
import de.nikocraft.challengeserver.parkours.Parkour;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

import java.util.*;


//PLAYER LISTENER CLASS
public class PlayerListeners implements Listener {

    //VARIABLES

    //Server teleport available
    private List<UUID> serverTeleport;


    //CONSTRUCTOR
    public PlayerListeners() {

        //Define server teleport
        serverTeleport = new ArrayList<>();

    }


    //EVENT HANDLER METHODS

    //Called, if a player moved
    @EventHandler
    public void onMove(PlayerMoveEvent event) {

        //Get the player
        Player player = event.getPlayer();

        //Get the active challenge
        Challenge challenge = Main.getInstance().getChallengeManager().getActiveChallenge();

        //If the challenge is not null
        if (challenge != null) {

            //Call the challenge event
            challenge.onMove(event);

        }

        //Check if the player is in the lobby
        if (player.getLocation().getWorld().getName().equals("lobby")) {

            //Check for void
            if (player.getLocation().getY() < 25.0) {
                Main.getInstance().getMultiverseCore().teleportPlayer(player, player, new Location(Bukkit.getWorld("lobby"), 0.5, 100, 0.5, 0, 0));
            }

            //Check for lobby area exiting
            if (player.getLocation().getX() < -300.0 | player.getLocation().getX() > 300.0 |
                    player.getLocation().getZ() < -300.0 | player.getLocation().getZ() > 300.0) {
                Main.getInstance().getMultiverseCore().teleportPlayer(player, player, new Location(Bukkit.getWorld("lobby"), 0.5, 100, 0.5, 0, 0));
            }

            //Check for portal
            if (player.getLocation().getX() >= -5.0 & player.getLocation().getX() <= 6.0 &
                    player.getLocation().getY() >= 100.0 & player.getLocation().getY() <= 110.0 &
                    player.getLocation().getZ() >= 39.0 & player.getLocation().getZ() <= 40.0 &
                    player.hasPermission("server.world.teleport")) {
                Main.getInstance().getWorldManager().teleportToGame(player, true);
            }

            //Check for among us
            if (player.getLocation().getX() >= -35.0 & player.getLocation().getX() <= -25.0 &
                    player.getLocation().getY() >= 105.0 & player.getLocation().getY() <= 110.0 &
                    player.getLocation().getZ() >= 38.0 & player.getLocation().getZ() <= 52.0 &
                    player.hasPermission("server.world.teleport")) {
                if (serverTeleport.contains(player.getUniqueId())) {
                    if (!Main.getInstance().sendPlayer(player, "amongus")) player.sendMessage(ChatColor.RED + "Server teleport failed!");
                    else player.sendMessage(ChatColor.GREEN + "Sending to 'amongus' ...");
                    serverTeleport.remove(player.getUniqueId());
                }
            }
            else {
                if (!serverTeleport.contains(player.getUniqueId())) serverTeleport.add(player.getUniqueId());
            }

            //Handle parkour
            for (Parkour parkour : Main.getInstance().getParkourManager().getParkours()) {
                parkour.handleMovement(event);
            }

        }

    }

    //Called, if a player sneaked
    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {

        //Get the player
        Player player = event.getPlayer();

        //Check for cookies
        if (player.getLocation().getX() >= -20.0 & player.getLocation().getX() <= 10.0 &
                player.getLocation().getY() >= 70.0 & player.getLocation().getY() <= 100.0 &
                player.getLocation().getZ() >= -60.0 & player.getLocation().getZ() <= -35.0 &
                event.isSneaking()) {

            //Update the cookies
            Main.getInstance().getCookieManager().setCookies(player, Main.getInstance().getCookieManager().getCookies(player) + Main.getInstance().getCookieManager().getLevel(player));
            Main.getInstance().getCookieManager().setAbsolute(player, Main.getInstance().getCookieManager().getAbsolute(player) + Main.getInstance().getCookieManager().getLevel(player));

            //Check for level upgrade
            if (Main.getInstance().getCookieManager().getAbsolute(player) >= 20 * Math.pow(Main.getInstance().getCookieManager().getLevel(player), 2))
                Main.getInstance().getCookieManager().setLevel(player, Main.getInstance().getCookieManager().getLevel(player) + 1);

        }

    }

    //Called, if a player died
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        //Get the active challenge
        Challenge challenge = Main.getInstance().getChallengeManager().getActiveChallenge();

        //If the challenge is not null
        if (challenge != null) {

            //Call the challenge event
            challenge.onDeath(event);

        }

    }

    //Called, if a player respawned
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {

        //Get the active challenge
        Challenge challenge = Main.getInstance().getChallengeManager().getActiveChallenge();

        //If the challenge is not null
        if (challenge != null) {

            //Call the challenge event
            challenge.onRespawn(event);

        }

    }

    //Called, if a player entered a bed
    @EventHandler
    public void onSleep(PlayerBedEnterEvent event) {

        //Get the active challenge
        Challenge challenge = Main.getInstance().getChallengeManager().getActiveChallenge();

        //If the challenge is not null
        if (challenge != null) {

            //Call the challenge event
            challenge.onSleep(event);

        }

    }

    //Called, if a player entered a portal
    @EventHandler
    public void onPortal(PlayerPortalEvent event) {

        //Get the active challenge
        Challenge challenge = Main.getInstance().getChallengeManager().getActiveChallenge();

        //If the challenge is not null
        if (challenge != null) {

            //Call the challenge event
            challenge.onPortal(event);

        }

    }

    //Called, if a player took a book from a lectern
    @EventHandler
    public void onTakeBook(PlayerTakeLecternBookEvent event) {

        //Check if the player is in the lobby world
        if (Main.getInstance().getPlayerInventoryManager().isPlayerInventoryActive(event.getPlayer()) && event.getPlayer().getLocation().getWorld().getName().equals("lobby")) {

            //Cancel the event
            event.setCancelled(true);

        }

    }

}
