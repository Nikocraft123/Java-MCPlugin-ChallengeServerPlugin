//PACKAGE
package de.nikocraft.challengeserver.challenges;


//IMPORTS
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

import java.util.List;


//ABSTRACT CHALLENGE BUILDER CLASS
public abstract class Challenge {

    //VARIABLES

    //Running
    private boolean running;


    //CONSTRUCTOR
    public Challenge() {

        //Set running to false
        running = false;

    }


    //ABSTRACT METHODS

    //Load
    public abstract void load();

    //Start
    public abstract void start();

    //Update
    public abstract void update();

    //Fast update
    public abstract void fastUpdate();

    //Tick update
    public abstract void tickUpdate();

    //End
    public abstract void end();

    //Unload
    public abstract void unload();

    //Configuration command execute
    public abstract boolean configCommandExecute(CommandSender sender, Command command, String label, String[] args, boolean isPlayer);

    //Configuration command tab complete
    public abstract List<String> configCommandTabComplete(CommandSender sender, Command command, String label, String[] args);

    //On join
    public abstract void onJoin(PlayerJoinEvent event);

    //On quit
    public abstract void onQuit(PlayerQuitEvent event);

    //On move
    public abstract void onMove(PlayerMoveEvent event);

    //On death
    public abstract void onDeath(PlayerDeathEvent event);

    //On respawn
    public abstract void onRespawn(PlayerRespawnEvent event);

    //On sleep
    public abstract void onSleep(PlayerBedEnterEvent event);

    //On enter game
    public abstract void onEnterGame(Player player);

    //On leave game
    public abstract void onLeaveGame(Player player);

    //On portal
    public abstract void onPortal(PlayerPortalEvent event);

    //On place block
    public abstract void onPlace(BlockPlaceEvent event);

    //On break block
    public abstract void onBreak(BlockBreakEvent event);

    //Get all players, that can be visited
    public abstract List<Player> getVisit();


    //GETTERS

    //Running
    public boolean isRunning() { return running; }


    //SETTERS

    //Running
    public void setRunning(boolean running) { this.running = running; }

}
