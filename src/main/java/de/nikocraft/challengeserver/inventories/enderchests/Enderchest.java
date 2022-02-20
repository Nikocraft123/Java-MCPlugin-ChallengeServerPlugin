//PACKAGE
package de.nikocraft.challengeserver.inventories.enderchests;


//IMPORTS
import de.nikocraft.challengeserver.inventories.InventoryBuilder;
import de.nikocraft.challengeserver.utils.Base64Utils;
import org.bukkit.ChatColor;
import java.io.IOException;
import java.util.UUID;


//ENDERCHEST CLASS
public class Enderchest extends InventoryBuilder {

    //VARIABLES

    //The UUID of the owner player of the enderchest
    private final UUID uuid;

    //The index of the current opened enderchest page
    private int currentPage;


    //CONSTRUCTORS
    public Enderchest(UUID uuid) {

        //Initialize the inventory of the enderchest
        super(6*9, ChatColor.BLUE.toString() + ChatColor.BOLD + "Enderchest");

        //Set the UUID
        this.uuid = uuid;

        //Set the current page to 0
        currentPage = 0;

    }

    public Enderchest(UUID uuid, String base64, int currentPage) throws IOException {

        //Initialize the inventory of the enderchest with the base 64
        super(6*9, ChatColor.BLUE.toString() + ChatColor.BOLD + "Enderchest", base64);

        //Set the UUID
        this.uuid = uuid;

        //Set the current page
        this.currentPage = currentPage;

    }


    //METHODS

    //Get the inventory as base 64
    public String toBase64() {

        //Return the converted inventory
        return Base64Utils.convertItemStackArrayToBase64(getInventory().getContents());

    }


    //GETTERS

    //The UUID of the owner player of the enderchest
    public UUID getUuid() { return uuid; }

    //The index of the current opened enderchest page
    public int getCurrentPage() { return currentPage; }


    //SETTERS

    //The index of the current opened enderchest page
    public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }

}
