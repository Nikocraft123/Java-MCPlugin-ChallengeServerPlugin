//PACKAGE
package de.nikocraft.challengeserver.shop;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.challenges.Challenge;
import de.nikocraft.challengeserver.utils.MathUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


//COIN COOKIE INFO CLASS
public class CoinCookieInfo {

    //CONSTRUCTOR
    public CoinCookieInfo() {

        //Run
        run();

    }


    //METHODS

    //Run
    private void run() {

        //Scheduler
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

            //Run
            @Override
            public void run() {

                //Loop for all online players
                for (Player player : Bukkit.getOnlinePlayers()) {

                    //Render the info
                    renderInfo(player);

                }

            }

        }, 20, 20);

    }

    //Render info
    public void renderInfo(Player player) {

        //If the player is in the lobby
        if (player.getWorld().getName().equals("lobby")) {

            //Render the actionbar
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY +
                    "« " + ChatColor.YELLOW + "Coins: " + ChatColor.GOLD + Main.getInstance().getCoinManager().getCoins(player) + ChatColor.GRAY + " | " + ChatColor.YELLOW + "Cookies: " + ChatColor.GOLD + Main.getInstance().getCookieManager().getCookies(player) + ChatColor.GRAY + " (" + ChatColor.YELLOW +
                    "Level: " + ChatColor.GOLD + Main.getInstance().getCookieManager().getLevel(player) + ChatColor.GRAY + ") »"));

        }

    }

}
