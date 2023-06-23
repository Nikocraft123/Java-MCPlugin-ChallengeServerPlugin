//PACKAGE
package de.nikocraft.challengeserver.challenges.deathrun;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.challenges.Challenge;
import de.nikocraft.challengeserver.challenges.ChallengeManager;
import de.nikocraft.challengeserver.utils.CommandUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.world.level.block.BlockBarrier;
import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.C;

import java.util.*;


//DEATHRUN CHALLENGE CLASS
public class DeathrunChallenge extends Challenge {

    //VARIABLES

    //List of all scoreboards
    private final List<DeathrunScoreboard> scoreboards;

    //List of all player positions
    private final Map<Player, Integer> positions;

    //List of all died players
    private final List<Player> died;

    //The distance to the border from Z 0
    private int borderDistance = 250;

    //The view distance of the border barrier
    private int barrierDistance = 40;

    //The size of the border barrier
    private int barrierSize = 20;

    //The name of the event
    private String name = "Unnamed";

    //The dimension
    private String dimension = "world";

    //Countdown
    private int countdown = 0;

    //Started
    private boolean started = false;


    //CONSTRUCTOR
    public DeathrunChallenge() {

        //Define a list for all player scoreboards
        scoreboards = new ArrayList<>();

        //Define a map for the player positions
        positions = new HashMap<>();

        //Define a list for all died players
        died = new ArrayList<>();

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

        //Set health
        for (Player player : getPositions().keySet()) player.setHealth(20);

        //Set hunger
        for (Player player : getPositions().keySet()) player.setFoodLevel(20);
        for (Player player : getPositions().keySet()) player.setSaturation(20);

        //If the countdown is not 0
        if (countdown > 0) {

            //Set running to true
            setRunning(true);

            //Return
            return;

        }

        //Broadcast message
        Bukkit.broadcastMessage(" \n" + ChallengeManager.getChatPrefix() + ChatColor.GOLD + "!CHALLENGE STARTED!\n ");
        Bukkit.broadcastMessage(ChallengeManager.getChatPrefix() + ChatColor.GREEN + "Good luck!\n ");

        //Broadcast title
        for (Player player : Bukkit.getOnlinePlayers()) player.sendTitle(ChatColor.GREEN + "GO!", ChatColor.GRAY.toString() + ChatColor.ITALIC + "Good luck!", 0, 30, 10);

        //Play sounds
        for (Player player : Bukkit.getOnlinePlayers()) player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER, 1, 2);

        //Set running and started to true
        setRunning(true);
        started = true;

        //Set the time
        Bukkit.getWorld("world").setTime(0);
        Bukkit.getWorld("world_nether").setTime(0);
        Bukkit.getWorld("world_the_end").setTime(0);

        //Kill mobs
        for (Entity entity : Bukkit.getWorld(dimension).getEntities()) {
            if (entity.getType() != EntityType.PLAYER) {
                if (entity.getLocation().distance(Bukkit.getWorld(dimension).getSpawnLocation()) < 30) {
                    entity.remove();
                }
            }
        }

        //Break glass
        for (int z = -2; z <= 2; z++) {
            for (int y = 0; y <= 2; y++) {
                Location spawn = Bukkit.getWorld(dimension).getSpawnLocation();
                Bukkit.getWorld(dimension).getBlockAt(spawn.getBlockX() + 3, spawn.getBlockY() + y, spawn.getBlockZ() + z).setType(Material.AIR);
            }
        }

        //Start the timer
        Main.getInstance().getTimer().setMode("count");
        Main.getInstance().getTimer().setRunning(true);

    }

    //Update
    @Override
    public void update() {

        //If the countdown is 0 and not started
        if (countdown <= 0 & !started) start();

        //If the countdown is not 0
        if (countdown > 0) {

            //Render title info
            if (countdown == 1) for (Player player : Bukkit.getOnlinePlayers()) player.sendTitle(ChatColor.RED + "1", ChatColor.GRAY.toString() + ChatColor.ITALIC + "Ready?", 0, 30, 0);
            if (countdown == 2) for (Player player : Bukkit.getOnlinePlayers()) player.sendTitle(ChatColor.RED + "2", ChatColor.GRAY.toString() + ChatColor.ITALIC + "Ready?", 0, 30, 0);
            if (countdown == 3) for (Player player : Bukkit.getOnlinePlayers()) player.sendTitle(ChatColor.RED + "3", ChatColor.GRAY.toString() + ChatColor.ITALIC + "Ready?", 0, 30, 0);
            if (countdown == 4) for (Player player : Bukkit.getOnlinePlayers()) player.sendTitle(ChatColor.YELLOW + "4", ChatColor.GRAY.toString() + ChatColor.ITALIC + "Ready?", 0, 30, 0);
            if (countdown == 5) for (Player player : Bukkit.getOnlinePlayers()) player.sendTitle(ChatColor.YELLOW + "5", ChatColor.GRAY.toString() + ChatColor.ITALIC + "Ready?", 0, 30, 0);
            if (countdown == 10) for (Player player : Bukkit.getOnlinePlayers()) player.sendTitle(ChatColor.GREEN + "10", ChatColor.GRAY.toString() + ChatColor.ITALIC + "Prepare ...", 0, 30, 10);

            //Render chat info
            if (countdown == 1) Bukkit.broadcastMessage(ChallengeManager.getChatPrefix() + ChatColor.RED + "1");
            if (countdown == 2) Bukkit.broadcastMessage(ChallengeManager.getChatPrefix() + ChatColor.RED + "2");
            if (countdown == 3) Bukkit.broadcastMessage(ChallengeManager.getChatPrefix() + ChatColor.RED + "3");
            if (countdown == 4) Bukkit.broadcastMessage(ChallengeManager.getChatPrefix() + ChatColor.YELLOW + "4");
            if (countdown == 5) Bukkit.broadcastMessage(ChallengeManager.getChatPrefix() + ChatColor.YELLOW + "5");
            if (countdown == 10) Bukkit.broadcastMessage(ChallengeManager.getChatPrefix() + ChatColor.GREEN + "10");
            if (countdown == 30) Bukkit.broadcastMessage(ChallengeManager.getChatPrefix() + ChatColor.GREEN + "The challenge starts in 30 seconds! Prepare ...");
            if (countdown == 60) Bukkit.broadcastMessage(ChallengeManager.getChatPrefix() + ChatColor.GREEN + "The challenge starts in a minute! Prepare ...");
            if (countdown == 300) Bukkit.broadcastMessage(ChallengeManager.getChatPrefix() + ChatColor.GREEN + "The challenge starts in 5 minutes! Prepare ...");
            if (countdown == 600) Bukkit.broadcastMessage(ChallengeManager.getChatPrefix() + ChatColor.GREEN + "The challenge starts in 10 minutes! Prepare ...");

            //Play sounds
            if (countdown == 10 | countdown <= 5) for (Player player : Bukkit.getOnlinePlayers()) player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER, 1, 1);

            //Update the countdown
            countdown--;

            //Return
            return;

        }

        //Update the position map
        for (Player player : positions.keySet()) {
            if (player.isOnline() & Arrays.asList("world", "world_nether", "world_the_end").contains(player.getWorld().getName()) & player.getLocation().getBlockX() >= 0)
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
        Bukkit.broadcastMessage(" \n" + ChallengeManager.getChatPrefix() + ChatColor.GOLD + "!CHALLENGE IS OVER!\n ");
        Bukkit.broadcastMessage(ChatColor.AQUA + "Result:");
        int position = 1;
        for (Map.Entry<Player, Integer> entry : getSortedPositions()) {
            String name = entry.getKey().getName() + ChatColor.RESET + " ";
            String positionString = ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "#" + position;
            positionString += StringUtils.repeat(" ", 8 - positionString.length());
            if (position == 1) Bukkit.broadcastMessage(positionString + ChatColor.GOLD + name + ChatColor.AQUA + entry.getValue() + ChatColor.RED + ChatColor.ITALIC + " " + (int) entry.getKey().getHealth() + "HP");
            else if (position == 2) Bukkit.broadcastMessage(positionString + ChatColor.YELLOW + name + ChatColor.AQUA + entry.getValue() + ChatColor.RED + ChatColor.ITALIC + " " + (int) entry.getKey().getHealth() + "HP");
            else if (position == 3) Bukkit.broadcastMessage(positionString + ChatColor.RED + name + ChatColor.AQUA + entry.getValue() + ChatColor.RED + ChatColor.ITALIC + " " + (int) entry.getKey().getHealth() + "HP");
            else Bukkit.broadcastMessage(positionString + ChatColor.WHITE + name + ChatColor.AQUA + entry.getValue() + ChatColor.RED + ChatColor.ITALIC + " " + (int) entry.getKey().getHealth() + "HP");
            position++;
        }
        Bukkit.broadcastMessage(" \n" + ChallengeManager.getChatPrefix() + ChatColor.GREEN + "Congratulation!\n ");

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

        //Play sounds
        for (Player player : Bukkit.getOnlinePlayers()) player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, SoundCategory.MASTER, 1, 1);

        //Set running and started to false
        setRunning(false);
        started = false;

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
                            if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing fourth argument!");
                            else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing fourth argument!");

                            //Return false
                            return false;

                        }

                        //Get the player
                        Player player = Bukkit.getPlayer(args[3]);

                        //If the player wasn't found
                        if (player == null) {

                            //Send message to sender
                            if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Cannot find the player " + ChatColor.ITALIC + args[3] + ChatColor.RED + "!");
                            else sender.sendMessage(CommandUtils.getConsolePrefix() + "Cannot find the player '" + args[3] + "'!");

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
                            if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing fourth argument!");
                            else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing fourth argument!");

                            //Return false
                            return false;

                        }

                        //Get the player
                        Player player = Bukkit.getPlayer(args[3]);

                        //If the player wasn't found
                        if (player == null) {

                            //Send message to sender
                            if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Cannot find the player " + ChatColor.ITALIC + args[3] + ChatColor.RED + "!");
                            else sender.sendMessage(CommandUtils.getConsolePrefix() + "Cannot find the player '" + args[3] + "'!");

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

            case "time": {

                //If argument 3 don't exist
                if (args.length < 3) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing third argument!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing third argument!");

                    //Return false
                    return false;

                }

                try {
                    //Set the time
                    if (!Main.getInstance().getTimer().TimerTime(Integer.parseInt(args[2]))) {

                        //Send message to sender
                        if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "The time of the timer is already at " + args[2] + "!");
                        else sender.sendMessage(CommandUtils.getConsolePrefix() + "The time of the timer is already at " + args[2] + "!");

                        //Return false
                        return false;

                    }
                } catch (NumberFormatException e) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Invalid time!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Invalid time!");

                    //Return false
                    return false;

                }

                //Return true
                return true;

            }

            case "countdown": {

                //If argument 3 don't exist
                if (args.length < 3) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing third argument!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing third argument!");

                    //Return false
                    return false;

                }

                try {

                    //Set the countdown
                    countdown = Integer.parseInt(args[2]);

                } catch (NumberFormatException e) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Invalid countdown!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Invalid countdown!");

                    //Return false
                    return false;

                }

                //Send message to sender
                if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Successfully set the countdown to '" + args[2] + "'!");
                else sender.sendMessage(CommandUtils.getConsolePrefix() + "Successfully set the countdown to '" + args[2] + "'!");

                //Return true
                return true;

            }

            case "name": {

                //If argument 3 don't exist
                if (args.length < 3) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing third argument!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing third argument!");

                    //Return false
                    return false;

                }

                //Set the name
                name = "";
                for (int i = 2; i < args.length; i++) {
                    name += args[i] + " ";
                }
                name = name.substring(0, name.length() - 1);

                //Update all player scoreboards
                for (DeathrunScoreboard scoreboard : scoreboards) {
                    scoreboard.update();
                }

                //Send message to sender
                if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Successfully set the name to '" + ChatColor.ITALIC + name + ChatColor.GREEN + "'!");
                else sender.sendMessage(CommandUtils.getConsolePrefix() + "Successfully set the name to '" + name + "'!");

                //Return true
                return true;

            }

            case "dimension": {

                //If argument 3 don't exist
                if (args.length < 3) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing third argument!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing third argument!");

                    //Return false
                    return false;

                }

                //Set the dimension
                switch (args[2].toLowerCase()) {

                    case "overworld":
                        dimension = "world";
                        break;

                    case "nether":
                        dimension = "world_nether";
                        break;

                    case "end":
                        dimension = "world_the_end";
                        break;

                    default:

                        //Send message to sender
                        if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Invalid dimension!");
                        else sender.sendMessage(CommandUtils.getConsolePrefix() + "Invalid dimension!");

                        //Return false
                        return false;

                }

                //Send message to sender
                if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Successfully set the dimension to '" + ChatColor.ITALIC + args[2].toLowerCase() + ChatColor.GREEN + "'!");
                else sender.sendMessage(CommandUtils.getConsolePrefix() + "Successfully set the dimension to '" + args[2].toLowerCase() + "'!");

                //Return true
                return true;

            }

            case "difficulty": {

                //If argument 3 don't exist
                if (args.length < 3) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing third argument!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing third argument!");

                    //Return false
                    return false;

                }

                //Set the difficulty
                switch (args[2].toLowerCase()) {

                    case "easy":
                        Bukkit.getWorld("world").setDifficulty(Difficulty.EASY);
                        Bukkit.getWorld("world_nether").setDifficulty(Difficulty.EASY);
                        Bukkit.getWorld("world_the_end").setDifficulty(Difficulty.EASY);
                        break;

                    case "normal":
                        Bukkit.getWorld("world").setDifficulty(Difficulty.NORMAL);
                        Bukkit.getWorld("world_nether").setDifficulty(Difficulty.NORMAL);
                        Bukkit.getWorld("world_the_end").setDifficulty(Difficulty.NORMAL);
                        break;

                    case "hard":
                        Bukkit.getWorld("world").setDifficulty(Difficulty.HARD);
                        Bukkit.getWorld("world_nether").setDifficulty(Difficulty.HARD);
                        Bukkit.getWorld("world_the_end").setDifficulty(Difficulty.HARD);
                        break;

                    default:

                        //Send message to sender
                        if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Invalid difficulty!");
                        else sender.sendMessage(CommandUtils.getConsolePrefix() + "Invalid difficulty!");

                        //Return false
                        return false;

                }

                //Send message to sender
                if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Successfully set the difficulty to '" + ChatColor.ITALIC + args[2].toLowerCase() + ChatColor.GREEN + "'!");
                else sender.sendMessage(CommandUtils.getConsolePrefix() + "Successfully set the difficulty to '" + args[2].toLowerCase() + "'!");

                //Return true
                return true;

            }

            case "border_distance": {

                //If argument 3 don't exist
                if (args.length < 3) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing third argument!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing third argument!");

                    //Return false
                    return false;

                }

                try {

                    //Set the border distance
                    borderDistance = Integer.parseInt(args[2]);

                } catch (NumberFormatException e) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Invalid distance!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Invalid distance!");

                    //Return false
                    return false;

                }

                //Send message to sender
                if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Successfully set the border distance to '" + args[2] + "'!");
                else sender.sendMessage(CommandUtils.getConsolePrefix() + "Successfully set the border distance to '" + args[2] + "'!");

                //Return true
                return true;

            }

            case "barrier_distance": {

                //If argument 3 don't exist
                if (args.length < 3) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing third argument!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing third argument!");

                    //Return false
                    return false;

                }

                try {

                    //Set the barrier distance
                    barrierDistance = Integer.parseInt(args[2]);

                } catch (NumberFormatException e) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Invalid distance!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Invalid distance!");

                    //Return false
                    return false;

                }

                //Send message to sender
                if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Successfully set the barrier distance to '" + args[2] + "'!");
                else sender.sendMessage(CommandUtils.getConsolePrefix() + "Successfully set the barrier distance to '" + args[2] + "'!");

                //Return true
                return true;

            }

            case "barrier_size": {

                //If argument 3 don't exist
                if (args.length < 3) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing third argument!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing third argument!");

                    //Return false
                    return false;

                }

                try {

                    //Set the barrier size
                    barrierSize = Integer.parseInt(args[2]);

                } catch (NumberFormatException e) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Invalid size!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Invalid size!");

                    //Return false
                    return false;

                }

                //Send message to sender
                if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Successfully set the barrier size to '" + args[2] + "'!");
                else sender.sendMessage(CommandUtils.getConsolePrefix() + "Successfully set the barrier size to '" + args[2] + "'!");

                //Return true
                return true;

            }

            case "pvp": {

                //If argument 3 don't exist
                if (args.length < 3) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing third argument!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing third argument!");

                    //Return false
                    return false;

                }

                //Set PVP
                switch (args[2].toLowerCase()) {

                    case "true":
                        Bukkit.getWorld("world").setPVP(true);
                        Bukkit.getWorld("world_nether").setPVP(true);
                        Bukkit.getWorld("world_the_end").setPVP(true);
                        break;

                    case "false":
                        Bukkit.getWorld("world").setPVP(false);
                        Bukkit.getWorld("world_nether").setPVP(false);
                        Bukkit.getWorld("world_the_end").setPVP(false);
                        break;

                    default:

                        //Send message to sender
                        if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Invalid value!");
                        else sender.sendMessage(CommandUtils.getConsolePrefix() + "Invalid value!");

                        //Return false
                        return false;

                }

                //Send message to sender
                if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Successfully set PVP to '" + ChatColor.ITALIC + args[2].toLowerCase() + ChatColor.GREEN + "'!");
                else sender.sendMessage(CommandUtils.getConsolePrefix() + "Successfully set PVP to '" + args[2].toLowerCase() + "'!");

                //Return true
                return true;

            }

            case "natural_regeneration": {

                //If argument 3 don't exist
                if (args.length < 3) {

                    //Send message to sender
                    if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Missing third argument!");
                    else sender.sendMessage(CommandUtils.getConsolePrefix() + "Missing third argument!");

                    //Return false
                    return false;

                }

                //Set natural regeneration
                switch (args[2].toLowerCase()) {

                    case "true":
                        Bukkit.getWorld("world").setGameRule(GameRule.NATURAL_REGENERATION, true);
                        Bukkit.getWorld("world_nether").setGameRule(GameRule.NATURAL_REGENERATION, true);
                        Bukkit.getWorld("world_the_end").setGameRule(GameRule.NATURAL_REGENERATION, true);
                        break;

                    case "false":
                        Bukkit.getWorld("world").setGameRule(GameRule.NATURAL_REGENERATION, false);
                        Bukkit.getWorld("world_nether").setGameRule(GameRule.NATURAL_REGENERATION, false);
                        Bukkit.getWorld("world_the_end").setGameRule(GameRule.NATURAL_REGENERATION, false);
                        break;

                    default:

                        //Send message to sender
                        if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "Invalid value!");
                        else sender.sendMessage(CommandUtils.getConsolePrefix() + "Invalid value!");

                        //Return false
                        return false;

                }

                //Send message to sender
                if (isPlayer) sender.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Successfully set natural regeneration to '" + ChatColor.ITALIC + args[2].toLowerCase() + ChatColor.GREEN + "'!");
                else sender.sendMessage(CommandUtils.getConsolePrefix() + "Successfully set natural regeneration to '" + args[2].toLowerCase() + "'!");

                //Return true
                return true;

            }

            case "spawn": {

                //Check for player
                if (!isPlayer) {
                    //Send message to sender
                    sender.sendMessage(CommandUtils.getConsolePrefix() + "You must be a player to execute this command!");

                    //Return false
                    return false;
                }

                //Get the player position
                Player player = (Player) sender;

                //Check if the player is in the game world
                if (!Arrays.asList("world", "world_nether", "world_the_end").contains(player.getLocation().getWorld().getName())) {

                    //Send message to player
                    player.sendMessage(CommandUtils.getChatPrefix() + ChatColor.RED + "You must be in the game world to do this!");

                    //Return false
                    return false;

                }

                //Set the world spawn
                player.getWorld().setSpawnLocation(new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), -90, 0));
                Main.getInstance().getMultiverseCore().getMVWorldManager().getMVWorld(player.getWorld().getName()).setSpawnLocation(new Location(player.getWorld(),
                        player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), -90, 0));

                //Build the spawn
                player.getWorld().getBlockAt(player.getLocation().getBlockX(), player.getLocation().getBlockY() - 1, player.getLocation().getBlockZ()).setType(Material.GOLD_BLOCK);
                for (int z = -2; z <= 2; z++) {
                    for (int y = 0; y <= 2; y++) {
                        player.getWorld().getBlockAt(player.getLocation().getBlockX() + 3, player.getLocation().getBlockY() + y, player.getLocation().getBlockZ() + z).setType(Material.GLASS);
                    }
                }
                
                //Send message to player
                player.sendMessage(CommandUtils.getChatPrefix() + ChatColor.GREEN + "Successfully set the spawn to X" +
                        player.getLocation().getBlockX() + " Y" + player.getLocation().getBlockY() + " Z" + player.getLocation().getBlockZ() + "!");

                //Return true
                return true;

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
                result.add("dimension");
                result.add("time");
                result.add("name");
                result.add("spawn");
                result.add("countdown");

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

                    case "dimension":

                        //Add values to the list
                        result.add("overworld");
                        result.add("nether");
                        result.add("end");
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

                    case "countdown":

                        //Add times to the list
                        result.add("0");
                        result.add("10");
                        result.add("30");
                        result.add("60");
                        result.add("300");
                        result.add("600");
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

        //Check if the player is in the game world
        if (Arrays.asList("world", "world_nether", "world_the_end").contains(event.getPlayer().getLocation().getWorld().getName())) {

            //Check for border
            if (event.getPlayer().getLocation().getZ() < -borderDistance)
                event.getPlayer().teleport(event.getPlayer().getLocation().add(0, 0, 1));
            if (event.getPlayer().getLocation().getZ() > borderDistance)
                event.getPlayer().teleport(event.getPlayer().getLocation().add(0, 0, -1));

        }

    }

    //On death
    @Override
    public void onDeath(PlayerDeathEvent event) {

        //Check if the player is in the game world
        if (Arrays.asList("world", "world_nether", "world_the_end").contains(event.getEntity().getLocation().getWorld().getName())) {

            //Get the player
            Player player = event.getEntity();

            //Add to died
            died.add(player);

            //Set the gamemode
            player.setGameMode(GameMode.SPECTATOR);

            //Remove the player from the positions
            getPositions().remove(player);

            //Update all player scoreboards
            for (DeathrunScoreboard scoreboard : scoreboards) {
                scoreboard.update();
            }

            //Send a message to player
            player.sendMessage(ChatColor.RED + " \nOh no! You died! Unfortunately you are out ... :(\n \n");
            ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/challenge_deathrun_restart");
            TextComponent clickText = new TextComponent(ChatColor.GRAY + "[" + ChatColor.YELLOW + "RESTART" + ChatColor.GRAY + "]\n ");
            TextComponent message = new TextComponent(ChatColor.GRAY + "Do you want to restart? Click here: ");
            clickText.setClickEvent(clickEvent);
            message.addExtra(clickText);
            player.spigot().sendMessage(message);

        }

    }

    //On respawn
    @Override
    public void onRespawn(PlayerRespawnEvent event) {

        //Check if the player is in the game world
        if (Arrays.asList("world", "world_nether", "world_the_end").contains(event.getPlayer().getLocation().getWorld().getName())) {

            //Set the respawn location
            event.setRespawnLocation(event.getPlayer().getLocation());

        }

    }

    //On sleep
    @Override
    public void onSleep(PlayerBedEnterEvent event) {

        //Check if the player is in the game world
        if (Arrays.asList("world", "world_nether", "world_the_end").contains(event.getPlayer().getLocation().getWorld().getName())) {

            //Cancel the event
            event.setCancelled(true);

            //Send a message to player
            event.getPlayer().sendMessage(ChatColor.RED + "Sorry! Sleeping is deactivated in this challenge ... :(");

        }

    }

    //On enter game
    @Override
    public void onEnterGame(Player player) {

        //If the player is not in the challenge, set the gamemode to spectator
        if (!getPositions().containsKey(player)) player.setGameMode(GameMode.SPECTATOR);

        //If the player is not in the right dimension
        if (!player.getWorld().getName().equals(dimension)) {

            //Teleport the player to the spawn of the right dimension
            Main.getInstance().getMultiverseCore().teleportPlayer(player, player, Bukkit.getWorld(dimension).getSpawnLocation().clone());

        }

    }

    //On enter game
    @Override
    public void onLeaveGame(Player player) {}

    //On portal
    @Override
    public void onPortal(PlayerPortalEvent event) {

        //Check if the player is in the game world
        if (Arrays.asList("world", "world_nether", "world_the_end").contains(event.getPlayer().getLocation().getWorld().getName())) {

            //Cancel the event
            event.setCancelled(true);

            //Send a message to player
            event.getPlayer().sendMessage(ChatColor.RED + "Sorry! Other dimensions are deactivated in this challenge ... :(");

        }

    }

    //On place block
    @Override
    public void onPlace(BlockPlaceEvent event) {}

    //On break block
    @Override
    public void onBreak(BlockBreakEvent event) {

        //Check for spawn
        if (event.getBlock().getWorld() == Bukkit.getWorld(dimension)) {
            if (event.getBlock().getLocation().distance(event.getBlock().getWorld().getSpawnLocation()) < 10) {
                if (!event.getPlayer().hasPermission("server.world.control")) {
                    event.setCancelled(true);
                }
            }
        }

        //Check if the player is in the game world
        if (Arrays.asList("world", "world_nether", "world_the_end").contains(event.getPlayer().getLocation().getWorld().getName())) {

            //If the broken block is nether roots
            if (Arrays.asList(Material.WARPED_ROOTS, Material.CRIMSON_ROOTS, Material.NETHER_SPROUTS).contains(event.getBlock().getType())) {

                //Cancel drop
                event.setDropItems(false);

                //Drop wheat
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.WHEAT));

            }

            //If the player is in the game
            if (getPositions().containsKey(event.getPlayer()) & countdown > 0) {

                //Cancel
                event.setCancelled(true);

            }

        }

    }


    //GETTERS

    //List of all scoreboards
    public List<DeathrunScoreboard> getScoreboards() { return scoreboards; }

    //List of all player positions
    public Map<Player, Integer> getPositions() { return positions; }

    //The name of the event
    public String getName() { return name; }

    //The dimension
    public String getDimension() { return dimension; }

    //Countdown
    public int getCountdown() { return countdown; }

    //List of died players
    public List<Player> getDied() { return died; }

}
