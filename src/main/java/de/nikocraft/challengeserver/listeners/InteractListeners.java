//PACKAGE
package de.nikocraft.challengeserver.listeners;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;


//INTERACT LISTENER CLASS
public class InteractListeners implements Listener {

    //EVENT HANDLER METHODS

    //Called, if a player interacted
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        //Call the player inventory manager
        Main.getInstance().getPlayerInventoryManager().onInteract(event);

    }

}
