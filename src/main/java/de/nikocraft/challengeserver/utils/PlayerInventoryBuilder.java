//PACKAGE
package de.nikocraft.challengeserver.utils;


//IMPORTS
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;


//ABSTRACT PLAYER INVENTORY BUILDER CLASS
public abstract class PlayerInventoryBuilder {

    //VARIABLES

    //The player
    private final Player player;

    //The inventory object
    private final PlayerInventory inventory;


    //CONSTRUCTORS
    public PlayerInventoryBuilder(Player player) {

        //Set the player
        this.player = player;

        //Get the inventory
        inventory = player.getInventory();

    }


    //ABSTRACT METHODS

    //Update
    public abstract void update();

    //On open
    public abstract void onOpen(InventoryOpenEvent event);

    //On close
    public abstract void onClose(InventoryCloseEvent event);

    //On interact
    public abstract void onInteract(PlayerInteractEvent event);

    //On click
    public abstract void onClick(InventoryClickEvent event);

    //On drag
    public abstract void onDrag(InventoryDragEvent event);

    //On pickup
    public abstract void onPickup(EntityPickupItemEvent event);

    //On drop
    public abstract void onDrop(PlayerDropItemEvent event);


    //GETTERS

    //The inventory object
    public PlayerInventory getInventory() { return inventory; }

    //The player
    public Player getPlayer() {
        return player;
    }

}
