//PACKAGE
package de.nikocraft.challengeserver.listeners;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;


//INVENTORY LISTENER CLASS
public class InventoryListeners implements Listener {

    //EVENT HANDLER METHODS

    //Called, if a player clicked
    @EventHandler
    public void onClick(InventoryClickEvent event) {

        //Call the player inventory manager
        Main.getInstance().getPlayerInventoryManager().onClick(event);

    }

}
