//PACKAGE
package de.nikocraft.challengeserver.inventories.players;


//IMPORTS
import de.nikocraft.challengeserver.utils.PlayerInventoryBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;


//PLAYER INVENTORY DEFAULT CLASS
public class PlayerInventoryDefault extends PlayerInventoryBuilder {

    //CONSTRUCTOR
    public PlayerInventoryDefault(Player player) {

        //Initialize the inventory
        super(player);

    }

    //Update
    @Override
    public void update() {

        //Clear the inventory
        getInventory().clear();

    }

    //On open
    @Override
    public void onOpen(InventoryOpenEvent event) {}

    //On close
    @Override
    public void onClose(InventoryCloseEvent event) {}

    //On interact
    @Override
    public void onInteract(PlayerInteractEvent event) {}

    //On click
    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    //On drag
    @Override
    public void onDrag(InventoryDragEvent event) {
        event.setCancelled(true);
    }

    //On pickup
    @Override
    public void onPickup(EntityPickupItemEvent event) {
        event.setCancelled(true);
    }

    //On drop
    @Override
    public void onDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    //On break
    @Override
    public void onBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }

    //On place
    @Override
    public void onPlace(BlockPlaceEvent event) {
        event.setCancelled(true);
    }

}
