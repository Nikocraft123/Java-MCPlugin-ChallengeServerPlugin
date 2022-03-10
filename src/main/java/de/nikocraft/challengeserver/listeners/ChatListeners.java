//PACKAGE
package de.nikocraft.challengeserver.listeners;


//IMPORTS

//Bukkit
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.utils.CommandUtils;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


//CHAT LISTENER CLASS
public class ChatListeners implements Listener {

    //EVENT HANDLER METHODS

    //Called, if a player write something to chat
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        //If the player hasn't the permission to write:
        if (!event.getPlayer().hasPermission("server.chat.write")) {

            //Send message to the player
            event.getPlayer().sendMessage( CommandUtils.getChatPrefix() + ChatColor.RED + "You don't have the permission to write in the chat!");

            //Cancel event
            event.setCancelled(true);
        }

        //Set the format of the message
        event.setFormat(ChatColor.GRAY + "<" + Main.getInstance().getPermissionManager().getPlayerRank(event.getPlayer()).getColor() + event.getPlayer().getName() +
                ChatColor.GRAY + "> " + ChatColor.GRAY + ChatColor.translateAlternateColorCodes('§', event.getMessage()));


    }

}
