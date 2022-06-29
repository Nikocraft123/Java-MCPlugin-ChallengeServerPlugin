//PACKAGE
package de.nikocraft.challengeserver.listeners;


//IMPORTS
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;


//ENTITY LISTENER CLASS
public class EntityListeners implements Listener {

    //EVENT HANDLER METHODS

    //Called, if an entity made damage
    @EventHandler
    public void onDamage(EntityDamageEvent event) {

        //If the entity is an end crystal
        if (event.getEntity().getType() == EntityType.ENDER_CRYSTAL) {

            //If the entity is in the lobby
            if (event.getEntity().getWorld().getName().equals("lobby")) {

                //Cancel the event
                event.setCancelled(true);

            }

        }

    }

}
