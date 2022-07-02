//PACKAGE
package de.nikocraft.challengeserver.listeners;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.challenges.Challenge;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;


//INTERACT LISTENER CLASS
public class InteractListeners implements Listener {

    //EVENT HANDLER METHODS

    //Called, if a player interacted
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        //Check if the player is in the lobby world
        if (Main.getInstance().getPlayerInventoryManager().isPlayerInventoryActive(event.getPlayer()) && event.getPlayer().getLocation().getWorld().getName().equals("lobby")) {

            //If the block is not null
            if (event.getClickedBlock() != null) {

                //If the block is a dragon egg
                if (event.getClickedBlock().getType().equals(Material.DRAGON_EGG)) {

                    //Cancel the event
                    event.setCancelled(true);

                }

            }

        }

        //Call the player inventory manager
        Main.getInstance().getPlayerInventoryManager().onInteract(event);

    }

    //Called, if a player broke a block
    @EventHandler
    public void onBreak(BlockBreakEvent event) {

        //Call the player inventory manager
        Main.getInstance().getPlayerInventoryManager().onBreak(event);

        //Get the active challenge
        Challenge challenge = Main.getInstance().getChallengeManager().getActiveChallenge();

        //If the challenge is not null
        if (challenge != null) {

            //Call the challenge event
            challenge.onBreak(event);

        }

    }

    //Called, if a player placed a block
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {

        //Call the player inventory manager
        Main.getInstance().getPlayerInventoryManager().onPlace(event);

        //Get the active challenge
        Challenge challenge = Main.getInstance().getChallengeManager().getActiveChallenge();

        //If the challenge is not null
        if (challenge != null) {

            //Call the challenge event
            challenge.onPlace(event);

        }

    }

}
