//PACKAGE
package de.nikocraft.challengeserver.listeners;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;


//ITEM LISTENER CLASS
public class ItemListeners implements Listener {

    //EVENT HANDLER METHODS

    //Called, if a player dropped an item
    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {

        //Call the player inventory manager
        Main.getInstance().getPlayerInventoryManager().onDrop(event);

    }

    //Called, if a player picked up an item
    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {

        //Call the player inventory manager
        Main.getInstance().getPlayerInventoryManager().onPickup(event);

    }

    //Called, if two items merged
    @EventHandler
    public void onMerge(ItemMergeEvent event) {

        //If the item has the tag "no_stack"
        if (event.getEntity().getScoreboardTags().contains("no_stack")) event.setCancelled(true);

    }

}
