//PACKAGE
package de.nikocraft.challengeserver.inventories.menus;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.utils.InventoryBuilder;
import de.nikocraft.challengeserver.utils.ItemBuilder;
import de.nikocraft.challengeserver.utils.ItemHeadBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;


//COOKIE SHOP MENU CLASS
public class CookieShopMenu extends InventoryBuilder {

    //VARIABLES

    //The player
    private Player player;


    //CONSTRUCTORS
    public CookieShopMenu(Player player) {

        //Initialize the inventory of the enderchest
        super(6*9, ChatColor.GOLD.toString() + ChatColor.BOLD + "Cookie Shop");

        //Set the player
        this.player = player;

        //Render the menu
        render();

    }


    //METHODS

    //Render
    public void render() {

        //Background
        for (int i = 0; i < 54; i++) getInventory().setItem(i, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, 1).setDisplayName(" ").build());
        for (int i = 9; i < 45; i++) {
            if (i % 9 != 0 & (i + 1) % 9 != 0) getInventory().setItem(i, new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1).setDisplayName(" ").build());
        }

        //Info
        getInventory().setItem(4, new ItemBuilder(Material.COOKIE, 1).setDisplayName(ChatColor.GOLD.toString() + ChatColor.UNDERLINE + "Cookie Information")
                .setLore("", ChatColor.AQUA + "Player" + ChatColor.GRAY + ": " + ChatColor.YELLOW + ChatColor.ITALIC + player.getName(), "",
                        ChatColor.AQUA + "Amount" + ChatColor.GRAY + ": " + ChatColor.YELLOW + ChatColor.ITALIC + Main.getInstance().getCookieManager().getCookies(player),
                        ChatColor.AQUA + "Absolute" + ChatColor.GRAY + ": " + ChatColor.YELLOW + ChatColor.ITALIC + Main.getInstance().getCookieManager().getAbsolute(player),
                        ChatColor.AQUA + "Level" + ChatColor.GRAY + ": " + ChatColor.YELLOW + ChatColor.ITALIC + Main.getInstance().getCookieManager().getLevel(player), "").build());

        //Animations
        getInventory().setItem(10, new ItemBuilder(Material.CAULDRON, 1).setDisplayName(ChatColor.WHITE + "Cookie Fountain")
                .setLore().setLocalizedName("fountain").build());

    }

}
