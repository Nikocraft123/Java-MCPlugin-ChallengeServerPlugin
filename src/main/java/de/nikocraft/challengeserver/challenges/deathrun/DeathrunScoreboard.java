//PACKAGE
package de.nikocraft.challengeserver.challenges.deathrun;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.utils.ScoreboardBuilder;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


//DEATHRUN SCOREBOARD CLASS
public class DeathrunScoreboard extends ScoreboardBuilder {

    //CONSTRUCTOR
    public DeathrunScoreboard(Player player) {

        //Initialize the scoreboard
        super(player, ChatColor.GOLD.toString() + ChatColor.BOLD + "      Deathrun      ");

    }


    //OVERRIDE METHODS

    //Create
    @Override
    public void create() {
        setScore(ChatColor.GRAY + "      V1 Overworld", 15);
        setScore("", 14);
        setScore(ChatColor.DARK_PURPLE + "Initialize ...", 13);
        setScore("", 1);
        setScore(ChatColor.YELLOW + " MC Challenge Nikocraft", 0);
    }

    //Update
    @Override
    public void update() {
        List<Map.Entry<Player, Integer>> sorted = new ArrayList<>(Main.getInstance().getDeathrunChallenge().getPositions().entrySet());
        sorted.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        int score = 13;
        for (Map.Entry<Player, Integer> entry : sorted) {
            String distance = String.valueOf(entry.getValue());
            distance += StringUtils.repeat(" ", 9 - distance.length());
            setScore(ChatColor.RED + distance + ChatColor.WHITE + entry.getKey().getName(), score);
            score--;
        }
    }

}
