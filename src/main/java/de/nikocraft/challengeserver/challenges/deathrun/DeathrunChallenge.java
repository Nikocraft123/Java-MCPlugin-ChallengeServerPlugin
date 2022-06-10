//PACKAGE
package de.nikocraft.challengeserver.challenges.deathrun;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.challenges.Challenge;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.*;
import java.util.stream.Stream;


//DEATHRUN CHALLENGE CLASS
public class DeathrunChallenge extends Challenge {

    //VARIABLES

    //List of all scoreboards
    private final List<DeathrunScoreboard> scoreboards;

    //List of all player positions
    private Map<Player, Integer> positions;


    //CONSTRUCTOR
    public DeathrunChallenge() {
        scoreboards = new ArrayList<>();
        positions = new HashMap<>();
    }


    //OVERRIDE METHODS

    //Load
    @Override
    public void load() {
        Bukkit.getWorld("world").setPVP(false);
        Bukkit.getWorld("world_nether").setPVP(false);
        Bukkit.getWorld("world_the_end").setPVP(false);
        Bukkit.getWorld("world").setDifficulty(Difficulty.HARD);
        Bukkit.getWorld("world_nether").setDifficulty(Difficulty.HARD);
        Bukkit.getWorld("world_the_end").setDifficulty(Difficulty.HARD);
        Bukkit.getWorld("world").setGameRule(GameRule.NATURAL_REGENERATION, false);
        Bukkit.getWorld("world_nether").setGameRule(GameRule.NATURAL_REGENERATION, false);
        Bukkit.getWorld("world_the_end").setGameRule(GameRule.NATURAL_REGENERATION, false);
    }

    //Update
    @Override
    public void update() {
        for (Player player : positions.keySet()) {
            if (player != null)
                positions.put(player, player.getLocation().getBlockX());
        }
        for (DeathrunScoreboard scoreboard : scoreboards) {
            scoreboard.update();
        }
    }

    //Time over
    @Override
    public void timeOver() {
        Bukkit.broadcastMessage(ChatColor.GOLD + " \n!EVENT IS OVER!\n ");
        Bukkit.broadcastMessage(ChatColor.AQUA + "Result:");
        List<Map.Entry<Player, Integer>> sorted = new ArrayList<>(Main.getInstance().getDeathrunChallenge().getPositions().entrySet());
        sorted.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        int platz = 1;
        for (Map.Entry<Player, Integer> entry : sorted) {
            String distance = String.valueOf(entry.getValue());
            distance += StringUtils.repeat(" ", 9 - distance.length());
            Bukkit.broadcastMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + "#" + platz + " " + ChatColor.RED + distance + ChatColor.BLUE + entry.getKey().getName());
            platz--;
        }
        Bukkit.broadcastMessage(ChatColor.GREEN + " \nCONGRATULATION!\n ");
    }

    //On move
    @Override
    public void onMove(PlayerMoveEvent event) {
        if (event.getPlayer().getLocation().getZ() < -250) {
            event.getPlayer().teleport(event.getPlayer().getLocation().add(0, 0, 1));
        }
        if (event.getPlayer().getLocation().getZ() > 250) {
            event.getPlayer().teleport(event.getPlayer().getLocation().add(0, 0, -1));
        }
    }


    //GETTERS
    public List<DeathrunScoreboard> getScoreboards() { return scoreboards; }
    public Map<Player, Integer> getPositions() { return positions; }

}
