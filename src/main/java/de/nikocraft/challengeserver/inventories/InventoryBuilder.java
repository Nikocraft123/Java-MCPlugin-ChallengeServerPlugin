//PACKAGE
package de.nikocraft.nikocraftserver.inventories;


//IMPORTS
import de.nikocraft.nikocraftserver.utils.Base64Utils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import java.io.IOException;


//ABSTRACT INVENTORY BUILDER CLASS
public abstract class InventoryBuilder {

    //VARIABLES

    //The inventory object
    private final Inventory inventory;

    //The title of the inventory
    private final String title;


    //CONSTRUCTORS
    public InventoryBuilder(int size, String title) {

        //Set the title
        this.title = title;

        //Create the inventory
        inventory = Bukkit.createInventory(null, size, title);

    }

    public InventoryBuilder(int size, String title, String base64) throws IOException {

        //Set the title
        this.title = title;

        //Create the inventory
        inventory = Bukkit.createInventory(null, size, title);

        //Set the content
        inventory.setContents(Base64Utils.convertBase64ToItemStackArray(base64));

    }

    public InventoryBuilder(int size, String title, ItemStack[] items) {

        //Set the title
        this.title = title;

        //Create the inventory
        inventory = Bukkit.createInventory(null, size, title);

        //Set the content
        inventory.setContents(items);

    }


    //GETTERS

    //The inventory object
    public Inventory getInventory() { return inventory; }

    //The title of the inventory
    public String getTitle() {
        return title;
    }

}
