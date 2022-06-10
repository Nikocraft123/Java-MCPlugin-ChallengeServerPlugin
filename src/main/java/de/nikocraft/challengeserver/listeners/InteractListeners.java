//PACKAGE
package de.nikocraft.challengeserver.listeners;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;


//Hotbar LISTENER CLASS
public class InteractListeners implements Listener {

    //EVENT HANDLER METHODS

    //Called, if a player moved
    @EventHandler
    public void onMove(PlayerInteractEvent event) {

        //Get the player, action and item
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        //Check if the player is in the lobby
        if (player.getLocation().getWorld().getName().equals("lobby")) {

            //If the action is done with an item
            if (item != null) {

                //If the localized name of the item is checkpoint
                if (item.getItemMeta().getLocalizedName().equals("checkpoint")) {
                    Main.getInstance().getCommand("parkour_checkpoint").execute(player, "parkour_checkpoint", new String[]{});
                    event.setCancelled(true);
                }

                //If the localized name of the item is cancel
                if (item.getItemMeta().getLocalizedName().equals("cancel")) {
                    Main.getInstance().getCommand("parkour_cancel").execute(player, "parkour_cancel", new String[]{});
                    event.setCancelled(true);
                }

            }

        }

    }

}
