//PACKAGE
package de.nikocraft.challengeserver.inventories.players;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.utils.ItemBuilder;
import de.nikocraft.challengeserver.utils.PlayerInventoryBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;


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

        //Build the visibility item
        ItemStack visibilityItem = new ItemBuilder(Material.LIME_DYE, 1)
                .setDisplayName(ChatColor.WHITE + "Players: " + ChatColor.RED.toString() + ChatColor.ITALIC + "Visible " + ChatColor.GRAY + "(Click)")
                .setLocalizedName("visible").setLore(ChatColor.GRAY.toString() + ChatColor.ITALIC + "Click to hide all players.").build();
        if (Main.getInstance().getVisibilityManager().isHidden(getPlayer())) {
            visibilityItem = new ItemBuilder(Material.GRAY_DYE, 1)
                    .setDisplayName(ChatColor.WHITE + "Players: " + ChatColor.RED.toString() + ChatColor.ITALIC + "Hidden " + ChatColor.GRAY + "(Click)")
                    .setLocalizedName("hidden").setLore(ChatColor.GRAY.toString() + ChatColor.ITALIC + "Click to show all players.").build();
        }

        //Set the item to the players inventory
        getInventory().setItem(8, visibilityItem);

    }

    //On open
    @Override
    public void onOpen(InventoryOpenEvent event) {}

    //On close
    @Override
    public void onClose(InventoryCloseEvent event) {}

    //On interact
    @Override
    public void onInteract(PlayerInteractEvent event) {

        //Get the player, action and item
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        //If the action is done with an item
        if (item != null) {

            //If the item has an item meta
            if (item.getItemMeta() != null) {

                //If the localized name of the item is visible
                if (item.getItemMeta().getLocalizedName().equals("visible")) {
                    Main.getInstance().getCommand("player").execute(player, "player", new String[]{"hide"});
                    event.setCancelled(true);
                }

                //If the localized name of the item is hidden
                if (item.getItemMeta().getLocalizedName().equals("hidden")) {
                    Main.getInstance().getCommand("player").execute(player, "player", new String[]{"show"});
                    event.setCancelled(true);
                }

            }

        }

    }

    //On click
    @Override
    public void onClick(InventoryClickEvent event) {

        //Get the player, action and item
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        //If the action is done with an item
        if (item != null) {

            //If the item has an item meta
            if (item.getItemMeta() != null) {

                //If the localized name of the item is visible
                if (item.getItemMeta().getLocalizedName().equals("visible")) {
                    Main.getInstance().getCommand("player").execute(player, "player", new String[]{"hide"});
                }

                //If the localized name of the item is hidden
                if (item.getItemMeta().getLocalizedName().equals("hidden")) {
                    Main.getInstance().getCommand("player").execute(player, "player", new String[]{"show"});
                }

            }

        }

        //Cancel the event
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
