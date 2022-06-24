//PACKAGE
package de.nikocraft.challengeserver.challenges.deathrun;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.challenges.Challenge;
import de.nikocraft.challengeserver.utils.CommandUtils;
import net.minecraft.world.level.block.BlockBarrier;
import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

import java.util.*;


//DEATHRUN CHALLENGE CLASS
public class DeathrunChallenge extends Challenge {

    //VARIABLES

    //List of all scoreboards
    private final List<DeathrunScoreboard> scoreboards;

    //List of all player positions
    private Map<Player, Integer> positions;

    //The distance to the border from Z 0
    private int borderDistance = 250;

    //The view distance of the border barrier
    private int barrierDistance = 40;

    //The size of the border barrier
    private int barrierSize = 20;

    //The name of the event
    private String name = "Unnamed";


    //CONSTRUCTOR
    public DeathrunChallenge() {

        //Define a list for all player scoreboards
        scoreboards = new ArrayList<>();

        //Define a map for the player positions
        positions = new HashMap<>();

    }


    //Get sorted player positions
    public List<Map.Entry<Player, Integer>> getSortedPositions() {

        //Get the positions as list
        List<Map.Entry<Player, Integer>> list = new ArrayList<>(getPositions().entrySet());

        //Sort the list
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        //Return the list
        return list;

    }


    //OVERRIDE METHODS

    //Load
    @Override
    public void load() {

        //Setup world settings
        Bukkit.getWorld("world").setPVP(false);
        Bukkit.getWorld("world_nether").setPVP(false);
        Bukkit.getWorld("world_the_end").setPVP(false);
        Bukkit.getWorld("world").setDifficulty(Difficulty.HARD);
        Bukkit.getWorld("world_nether").setDifficulty(Difficulty.HARD);
        Bukkit.getWorld("world_the_end").setDifficulty(Difficulty.HARD);
        Bukkit.getWorld("world").setGameRule(GameRule.NATURAL_REGENERATION, false);
        Bukkit.getWorld("world_nether").setGameRule(GameRule.NATURAL_REGENERATION, false);
        Bukkit.getWorld("world_the_end").setGameRule(GameRule.NATURAL_REGENERATION, false);

        //Loop for all players
        for (Player player : Bukkit.getOnlinePlayers()) {

            //Add a new scoreboard for the player to the list
            getScoreboards().add(new DeathrunScoreboard(player));

        }

    }

    //Start
    @Override
    public void start() {

        //Broadcast message
        Bukkit.broadcastMessage(ChatColor.GOLD + " \nChallenge started! Good luck!\n ");

        //Set running to true
        setRunning(true);

        //Set the time
        Bukkit.getWorld("world").setTime(0);
        Bukkit.getWorld("world_nether").setTime(0);
        Bukkit.getWorld("world_the_end").setTime(0);

        //Start the timer
        Main.getInstance().getTimer().setMode("count");
        Main.getInstance().getTimer().setRunning(true);

    }

    //Update
    @Override
    public void update() {

        //Update the position map
        for (Player player : positions.keySet()) {
            if (player.isOnline() & Arrays.asList("world", "world_nether", "world_the_end").contains(player.getWorld().getName()))
                positions.put(player, player.getLocation().getBlockX());
            else
                positions.put(player, 0);
        }

        //Update all player scoreboards
        for (DeathrunScoreboard scoreboard : scoreboards) {
            scoreboard.update();
        }

    }

    //Fast update
    @Override
    public void fastUpdate() {

        //Render border barrier
        for (Player player : Bukkit.getOnlinePlayers()) {

            //Check if the player is in the game world
            if (Arrays.asList("world", "world_nether", "world_the_end").contains(player.getWorld().getName())) {

                //Check if the player is in the barrier range
                if (player.getLocation().getZ() < -borderDistance + barrierDistance) {
                    for (int x = -barrierSize - 2; x < barrierSize + 2; x++) {
                        for (int y = -barrierSize - 2; y < barrierSize + 2; y++) {
                                Location location = new Location(player.getWorld(), player.getLocation().getBlockX() + x, player.getLocation().getBlockY() + y, -borderDistance);
                                Location center = player.getLocation().clone();
                                center.setZ(-borderDistance);
                                if (center.distance(location) < barrierSize)
                                    player.spawnParticle(Particle.BLOCK_MARKER, location.add(0.5, 0.5, 0.5), 1, Material.BARRIER.createBlockData());
                        }
                    }
                }
                if (player.getLocation().getZ() > borderDistance - barrierDistance) {
                    for (int x = -barrierSize - 2; x < barrierSize + 2; x++) {
                        for (int y = -barrierSize - 2; y < barrierSize + 2; y++) {
                            Location location = new Location(player.getWorld(), player.getLocation().getBlockX() + x, player.getLocation().getBlockY() + y, borderDistance);
                            Location center = player.getLocation().clone();
                            center.setZ(borderDistance);
                            if (center.distance(location) < barrierSize)
                                player.spawnParticle(Particle.BLOCK_MARKER, location.add(0.5, 0.5, 0.5), 1, Material.BARRIER.createBlockData());
                        }
                    }
                }

            }

        }

    }

    //Tick update
    @Override
    public void tickUpdate() {

    }

    //End
    @Override
    public void end() {

        //Update for the last time
        update();

        //Broadcast the result
        Bukkit.broadcastMessage(ChatColor.GOLD + " \n!EVENT IS OVER!\n ");
        Bukkit.broadcastMessage(ChatColor.AQUA + "Result:");
        int position = 1;
        for (Map.Entry<Player, Integer> entry : getSortedPositions()) {
            String name = entry.getKey().getName() + ChatColor.RESET + " ";
            String positionString = ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "#" + position;
            positionString += StringUtils.repeat(" ", 8 - positionString.length());
            if (position == 1) Bukkit.broadcastMessage(positionString + ChatColor.GOLD + name + ChatColor.AQUA + entry.getValue());
            else if (position == 2) Bukkit.broadcastMessage(positionString + ChatColor.YELLOW + name + ChatColor.AQUA + entry.getValue());
            else if (position == 3) Bukkit.broadcastMessage(positionString + ChatColor.RED + name + ChatColor.AQUA + entry.getValue());
            else Bukkit.broadcastMessage(positionString + ChatColor.WHITE + name + ChatColor.AQUA + entry.getValue());
            position++;
        }
        Bukkit.broadcastMessage(ChatColor.GREEN + " \nCONGRATULATION!\n");

        //Loop for all online players
        for (Player player : Bukkit.getOnlinePlayers()) {

            //Check if the player is in the game world
            if (Arrays.asList("world", "world_nether", "world_the_end").contains(player.getLocation().getWorld().getName())) {

                //Teleport the player to lobby
                Main.getInstance().getWorldManager().teleportToLobby(player, false);

            }

        }

        //Update the position map
        for (Player player : positions.keySet()) {
            positions.put(player, 0);
        }

        //Update all player scoreboards
        for (DeathrunScoreboard scoreboard : scoreboards) {
            scoreboard.update();
        }

        //Set running to false
        setRunning(false);

    }

    //Unload
    @Override
    public void unload() {

        //Loop for all scoreboards
        for (DeathrunScoreboard scoreboard : getScoreboards()) {

            //Delete the scoreboard
            scoreboard.delete();

        }

        //Clear the scoreboard list and position map
        getScoreboards().clear();
        getPositions().clear();

    }

    //Configuration command execute
    @Override
    public boolean configCommandExecute(CommandSender sender, Command command, String label, String[] args, boolean isPlayer) {

        //If argument 2 don't exist
        if (args.length < 2) {

            //Send message to sender
            if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing second argument!");
            else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing second argument!");

            //Return false
            return false;

        }

        //Switch in argument 2
        switch (args[1].toLowerCase()) {

            case "player": {

                //If argument 3 don't exist
                if (args.length < 3) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing third argument!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing third argument!");

                    //Return false
                    return false;

                }

                //Switch in argument 3
                switch (args[2].toLowerCase()) {

                    case "add": {

                        //If argument 4 don't exist
                        if (args.length < 4) {

                            //Send message to sender
                            if (isPlayer)
                                sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing fourth argument!");
                            else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing fourth argument!");

                            //Return false
                            return false;

                        }

                        //Get the player
                        Player player = Bukkit.getPlayer(args[3]);

                        //If the player wasn't found
                        if (player == null) {

                            //Send message to sender
                            if (isPlayer)
                                sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Cannot find the player " + ChatColor.ITALIC + args[3] + ChatColor.RED + "!");
                            else
                                sender.sendMessage(CommandUtils.getConsolePrefix() + "Cannot find the player '" + args[3] + "'!");

                            //Return false
                            return false;

                        }

                        //Add the player to the positions
                        getPositions().put(player, 0);

                        //Update all player scoreboards
                        for (DeathrunScoreboard scoreboard : scoreboards) {
                            scoreboard.update();
                        }

                        //Send message to sender
                        if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Successfully added '" + ChatColor.ITALIC + args[3] + ChatColor.GREEN + "' to the challenge!");
                        else sender.sendMessage(CommandUtils.getConsolePrefix() + "Successfully added '" + args[3] + "' to the challenge!");

                        //Return true
                        return true;

                    }

                    case "remove": {

                        //If argument 4 don't exist
                        if (args.length < 4) {

                            //Send message to sender
                            if (isPlayer)
                                sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing fourth argument!");
                            else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing fourth argument!");

                            //Return false
                            return false;

                        }

                        //Get the player
                        Player player = Bukkit.getPlayer(args[3]);

                        //If the player wasn't found
                        if (player == null) {

                            //Send message to sender
                            if (isPlayer)
                                sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Cannot find the player " + ChatColor.ITALIC + args[3] + ChatColor.RED + "!");
                            else
                                sender.sendMessage(CommandUtils.getConsolePrefix() + "Cannot find the player '" + args[3] + "'!");

                            //Return false
                            return false;

                        }

                        //Remove the player from the positions
                        getPositions().remove(player);

                        //Update all player scoreboards
                        for (DeathrunScoreboard scoreboard : scoreboards) {
                            scoreboard.update();
                        }

                        //Send message to sender
                        if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Successfully removed '" + ChatColor.ITALIC + args[3] + ChatColor.GREEN + "' from the challenge!");
                        else sender.sendMessage(CommandUtils.getConsolePrefix() + "Successfully removed '" + args[3] + "' from the challenge!");

                        //Return true
                        return true;

                    }

                    case "list": {

                        //Loop for all player positions
                        //TODO

                    }

                }

            }

            default:

                //Send message to sender
                if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Invalid second argument!");
                else sender.sendMessage(CommandUtils.getConsolePrefix() + "Invalid second argument!");

                //Return false
                return false;

        }

    }

    //Configuration command tab complete
    @Override
    public List<String> configCommandTabComplete(CommandSender sender, Command command, String label, String[] args) {

        //Define a result list
        ArrayList<String> result = new ArrayList<>();

        //Switch in the count of arguments
        switch (args.length) {

            case 2:

                //Add commands to the list
                result.add("player");
                result.add("border_distance");
                result.add("barrier_distance");
                result.add("barrier_size");
                result.add("pvp");
                result.add("natural_regeneration");
                result.add("difficulty");
                result.add("time");
                result.add("name");

                break;

            case 3:

                //Switch in argument 2
                switch (args[1].toLowerCase()) {

                    case "player":

                        //Add commands to the list
                        result.add("add");
                        result.add("remove");
                        result.add("list");
                        break;

                    case "border_distance":

                        //Add values to the list
                        result.add("250");
                        break;

                    case "barrier_distance":

                        //Add values to the list
                        result.add("40");
                        break;

                    case "barrier_size":

                        //Add values to the list
                        result.add("20");
                        break;

                    case "pvp":
                    case "natural_regeneration":

                        //Add values to the list
                        result.add("true");
                        result.add("false");
                        break;

                    case "difficulty":

                        //Add values to the list
                        result.add("easy");
                        result.add("normal");
                        result.add("hard");
                        break;

                    case "time":

                        //Add times to the list
                        result.add("60");
                        result.add("600");
                        result.add("1800");
                        result.add("3600");
                        result.add("7200");
                        result.add("21600");
                        result.add("43200");
                        result.add("86400");
                        break;

                }

                break;

            case 4:

                //If the argument 2 is player
                if (args[1].equalsIgnoreCase("player")) {

                    //If the argument 3 is "add"
                    if (args[2].equalsIgnoreCase("add")) {
                        //For in all online players
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            //Add the player name to result
                            result.add(player.getName());
                        }
                    }
                    //Else if the argument 3 is "remove"
                    else if (args[2].equalsIgnoreCase("remove")) {
                        //For in all player positions
                        for (Player player : getPositions().keySet()) {
                            //Add the player name to result
                            result.add(player.getName());
                        }
                    }

                }

                break;

        }

        //Return the formatted result
        return CommandUtils.formatTapCompleterList(result, args);

    }

    //On join
    public void onJoin(PlayerJoinEvent event) {

        //Add a new scoreboard for the player to the list
        getScoreboards().add(new DeathrunScoreboard(event.getPlayer()));

    }

    //On quit
    public void onQuit(PlayerQuitEvent event) {

        //Loop for all scoreboards
        for (DeathrunScoreboard scoreboard : getScoreboards()) {

            //If the player scoreboard was found
            if (scoreboard.getPlayer().equals(event.getPlayer())) {

                //Delete the scoreboard
                scoreboard.delete();

                //Remove the scoreboard from the list
                getScoreboards().remove(scoreboard);

                //Break
                break;

            }

        }

        //Remove the player from the positions
        getPositions().remove(event.getPlayer());

        //Update all player scoreboards
        for (DeathrunScoreboard scoreboard : scoreboards) {
            scoreboard.update();
        }

    }

    //On move
    @Override
    public void onMove(PlayerMoveEvent event) {

        //Check for border
        if (event.getPlayer().getLocation().getZ() < -borderDistance)
            event.getPlayer().teleport(event.getPlayer().getLocation().add(0, 0, 1));
        if (event.getPlayer().getLocation().getZ() > borderDistance)
            event.getPlayer().teleport(event.getPlayer().getLocation().add(0, 0, -1));

    }

    //On death
    @Override
    public void onDeath(PlayerDeathEvent event) {

        //Get the player
        Player player = event.getEntity();

        //Set the gamemode
        player.setGameMode(GameMode.SPECTATOR);

        //Remove the player from the positions
        getPositions().remove(player);

        //Update all player scoreboards
        for (DeathrunScoreboard scoreboard : scoreboards) {
            scoreboard.update();
        }

        //Send a message to player
        player.sendMessage(ChatColor.RED + " \nOh no! You died! Unfortunately you are out ... :(\n ");

    }

    //On respawn
    @Override
    public void onRespawn(PlayerRespawnEvent event) {

        //Set the respawn location
        event.setRespawnLocation(event.getPlayer().getLocation());

    }

    //On sleep
    @Override
    public void onSleep(PlayerBedEnterEvent event) {

        //Cancel the event
        event.setCancelled(true);

        //Send a message to player
        event.getPlayer().sendMessage(ChatColor.RED + "Sorry! Sleeping is deactivated in this challenge ... :(");

    }

    //On enter game
    @Override
    public void onEnterGame(Player player) {

        //If the player is not in the challenge, set the gamemode to spectator
        if (!getPositions().containsKey(player)) player.setGameMode(GameMode.SPECTATOR);

    }

    //On enter game
    @Override
    public void onLeaveGame(Player player) {

    }

    //On portal
    @Override
    public void onPortal(PlayerPortalEvent event) {

        //Cancel the event
        event.setCancelled(true);

        //Send a message to player
        event.getPlayer().sendMessage(ChatColor.RED + "Sorry! The nether is deactivated in this challenge ... :(");

    }


    //GETTERS

    //List of all scoreboards
    public List<DeathrunScoreboard> getScoreboards() { return scoreboards; }

    //List of all player positions
    public Map<Player, Integer> getPositions() { return positions; }

}
