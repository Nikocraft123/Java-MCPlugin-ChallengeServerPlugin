//PACKAGE
package de.nikocraft.challengeserver.inventories.menus;


//IMPORTS
import de.nikocraft.challengeserver.utils.InventoryBuilder;
import de.nikocraft.challengeserver.utils.ItemBuilder;
import de.nikocraft.challengeserver.utils.ItemHeadBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;


//SERVER SELECTION MENU CLASS
public class ServerSelectionMenu extends InventoryBuilder {

    //CONSTRUCTORS
    public ServerSelectionMenu() {

        //Initialize the inventory of the enderchest
        super(3*9, ChatColor.AQUA.toString() + ChatColor.BOLD + "Server Selector");

        //Set the content
        for (int i = 0; i < 27; i++) getInventory().setItem(i, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, 1).setDisplayName(" ").build());
        getInventory().setItem(10, new ItemHeadBuilder(Material.PLAYER_HEAD, 1).setDisplayName(ChatColor.RED + "Among Us")
                .setLore(ChatColor.GRAY.toString() + ChatColor.ITALIC + "Click to teleport to server.").setLocalizedName("amongus").setHead("Jur").build());
        getInventory().setItem(13, new ItemBuilder(Material.DIAMOND_SWORD, 1).setDisplayName(ChatColor.BLUE + "PVP")
                .setLore(ChatColor.GRAY.toString() + ChatColor.ITALIC + "Click to teleport to server.").setLocalizedName("pvp").build());
        getInventory().setItem(16, new ItemBuilder(Material.GRASS_BLOCK, 1).setDisplayName(ChatColor.GREEN + "Plot")
                .setLore(ChatColor.GRAY.toString() + ChatColor.ITALIC + "Click to teleport to server.").setLocalizedName("plot").build());

    }

}
