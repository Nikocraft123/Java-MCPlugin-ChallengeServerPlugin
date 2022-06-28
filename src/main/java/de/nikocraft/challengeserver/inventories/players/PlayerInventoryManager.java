//PACKAGE
package de.nikocraft.challengeserver.inventories.players;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.utils.PlayerInventoryBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import java.util.HashMap;
import java.util.Map;


//INVENTORY MANAGER CLASS
public class PlayerInventoryManager {

    //The map of all player inventories
    private final Map<Player, PlayerInventoryBuilder> inventories;

    //The map of all player inventory active
    private final Map<Player, Boolean> inventoryActive;


    //CONSTRUCTOR
    public PlayerInventoryManager() {

        //Define the inventory and active map
        inventories = new HashMap<>();
        inventoryActive = new HashMap<>();

        //Loop for all online players
        for (Player player : Bukkit.getOnlinePlayers()) {

            //Set the inventory manager for the player
            setPlayerInventoryMode(player, new PlayerInventoryDefault(player), false);
            if (Main.getInstance().getInventoryConfig().getConfig().contains("active." + player.getUniqueId().toString()))
                setPlayerInventoryActive(player, Main.getInstance().getInventoryConfig().getConfig().getBoolean("active." + player.getUniqueId().toString()), true);
            else
                setPlayerInventoryActive(player, true, true);

        }

    }


    //METHODS

    //Set the player inventory mode
    public void setPlayerInventoryMode(Player player, PlayerInventoryBuilder inventory, boolean update) {

        //Set the inventory mode
        inventories.put(player, inventory);

        //Update the inventory
        if (update) updateInventory(player);

    }

    //Get the player inventory mode
    public PlayerInventoryBuilder getPlayerInventoryMode(Player player) {

        //Return the inventory mode
        return inventories.get(player);

    }

    //Set the player inventory active
    public void setPlayerInventoryActive(Player player, boolean active, boolean update) {

        //Set the player inventory active
        inventoryActive.put(player, active);

        //Update the inventory
        if (update) updateInventory(player);

    }

    //Is the player inventory active
    public boolean isPlayerInventoryActive(Player player) {

        //If inventory active is null, return true
        if (inventoryActive.get(player) == null) return true;

        //Return the player inventory active
        return inventoryActive.get(player);

    }

    //Remove a player from the inventory and active map
    public boolean removePlayer(Player player) {

        //If the player is in the inventory map, remove the player name from the inventory map
        if (inventories.containsKey(player)) inventories.remove(player);

        //If the player is not in the active map, remove the player name from the inventory map
        if (inventoryActive.containsKey(player)) {
            Main.getInstance().getInventoryConfig().getConfig().set("active." + player.getUniqueId().toString(), isPlayerInventoryActive(player));
            Main.getInstance().getInventoryConfig().save();
            inventoryActive.remove(player);
        }

        //Return true
        return true;

    }

    //Update player inventory
    public void updateInventory(Player player) {

        //If the inventory of the player is active and the player is in the lobby
        if (isPlayerInventoryActive(player) && player.getWorld().getName().equals("lobby")) {

            //Update the inventory
            inventories.get(player).update();

            //Update the client player inventory
            player.updateInventory();

        }

    }

    //Save
    public void save() {

        //Loop for all online players
        for (Player player : Bukkit.getOnlinePlayers()) {

            //Remove the player
            removePlayer(player);

        }

    }

    //On open
    public void onOpen(InventoryOpenEvent event) {

        //If the inventory of the player is active and the player is in the lobby
        if (isPlayerInventoryActive((Player) event.getPlayer()) && event.getPlayer().getWorld().getName().equals("lobby")) {

            //Call on open of the inventory mode
            getPlayerInventoryMode((Player) event.getPlayer()).onOpen(event);

        }

    }

    //On close
    public void onClose(InventoryCloseEvent event) {

        //If the inventory of the player is active and the player is in the lobby
        if (isPlayerInventoryActive((Player) event.getPlayer()) && event.getPlayer().getWorld().getName().equals("lobby")) {

            //Call on close of the inventory mode
            getPlayerInventoryMode((Player) event.getPlayer()).onClose(event);

        }

    }

    //On interact
    public void onInteract(PlayerInteractEvent event) {

        //If the inventory of the player is active and the player is in the lobby
        if (isPlayerInventoryActive(event.getPlayer()) && event.getPlayer().getWorld().getName().equals("lobby")) {

            //Call on interact of the inventory mode
            getPlayerInventoryMode(event.getPlayer()).onInteract(event);

        }

    }

    //On click
    public void onClick(InventoryClickEvent event) {

        //If the inventory of the player is active and the player is in the lobby
        if (isPlayerInventoryActive((Player) event.getWhoClicked()) && event.getWhoClicked().getWorld().getName().equals("lobby")) {

            //Call on click of the inventory mode
            getPlayerInventoryMode((Player) event.getWhoClicked()).onClick(event);

        }

    }

    //On drag
    public void onDrag(InventoryDragEvent event) {

        //If the inventory of the player is active and the player is in the lobby
        if (isPlayerInventoryActive((Player) event.getWhoClicked()) && event.getWhoClicked().getWorld().getName().equals("lobby")) {

            //Call on drag of the inventory mode
            getPlayerInventoryMode((Player) event.getWhoClicked()).onDrag(event);

        }
    }

    //On pickup
    public void onPickup(EntityPickupItemEvent event) {

        //If the entity is not a player
        if (!(event.getEntity() instanceof Player)) return;

        //If the inventory of the player is active and the player is in the lobby
        if (isPlayerInventoryActive((Player) event.getEntity()) && event.getEntity().getWorld().getName().equals("lobby")) {

            //Call on open of the inventory mode
            getPlayerInventoryMode((Player) event.getEntity()).onPickup(event);

        }

    }

    //On drop
    public void onDrop(PlayerDropItemEvent event) {

        //If the inventory of the player is active and the player is in the lobby
        if (isPlayerInventoryActive(event.getPlayer()) && event.getPlayer().getWorld().getName().equals("lobby")) {

            //Call on open of the inventory mode
            getPlayerInventoryMode(event.getPlayer()).onDrop(event);

        }

    }

    //On break
    public void onBreak(BlockBreakEvent event) {

        //If the inventory of the player is active and the player is in the lobby
        if (isPlayerInventoryActive(event.getPlayer()) && event.getPlayer().getWorld().getName().equals("lobby")) {

            //Call on open of the inventory mode
            getPlayerInventoryMode(event.getPlayer()).onBreak(event);

        }

    }

    //On place
    public void onPlace(BlockPlaceEvent event) {

        //If the inventory of the player is active and the player is in the lobby
        if (isPlayerInventoryActive(event.getPlayer()) && event.getPlayer().getWorld().getName().equals("lobby")) {

            //Call on open of the inventory mode
            getPlayerInventoryMode(event.getPlayer()).onPlace(event);

        }

    }

}
