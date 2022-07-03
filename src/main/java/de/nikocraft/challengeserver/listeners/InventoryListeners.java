//PACKAGE
package de.nikocraft.challengeserver.listeners;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;


//INVENTORY LISTENER CLASS
public class InventoryListeners implements Listener {

    //EVENT HANDLER METHODS

    //Called, if a player clicked
    @EventHandler
    public void onClick(InventoryClickEvent event) {

        //Call the player inventory manager
        Main.getInstance().getPlayerInventoryManager().onClick(event);

        //Get the player, action and item
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        //If the inventory is a custom inventory
        if (event.getView().getTitle().equals(ChatColor.AQUA.toString() + ChatColor.BOLD + "Server Selector")) {

            //If the action is done with an item
            if (item != null) {

                //If the item has an item meta
                if (item.getItemMeta() != null) {

                    //If the localized name of the item is amongus, pvp or plot
                    if (Arrays.asList("amongus", "pvp", "plot").contains(item.getItemMeta().getLocalizedName())) {
                        if (!Main.getInstance().sendPlayer(player, item.getItemMeta().getLocalizedName())) player.sendMessage(ChatColor.RED + "Server teleport failed!");
                        else player.sendMessage(ChatColor.GREEN + "Sending to '" + item.getItemMeta().getLocalizedName() + "' ...");
                    }

                }
            }

            //Cancel the event
            event.setCancelled(true);

        }

    }

    //Called, if a player dragged
    @EventHandler
    public void onDrag(InventoryDragEvent event) {

        //Call the player inventory manager
        Main.getInstance().getPlayerInventoryManager().onDrag(event);

    }

}
