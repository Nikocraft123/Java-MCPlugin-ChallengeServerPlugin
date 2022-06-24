//PACKAGE
package de.nikocraft.challengeserver.challenges.deathrun;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.utils.ScoreboardBuilder;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import java.util.Map;


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
        setScore(ChatColor.GRAY + "            V2 Overworld       ", 15);
        setScore("", 14);
        setScore(ChatColor.DARK_PURPLE + "Initialize ...", 13);
        setScore("", 1);
        setScore(ChatColor.YELLOW + "      MC Challenge Nikocraft    ", 0);
    }

    //Update
    @Override
    public void update() {

        //Delete all scores
        for (int i = 2; i < 14; i++) {
            removeScore(i);
        }

        //Render all scores
        int score = 13;
        int position = 1;
        setScore(ChatColor.DARK_PURPLE + "No players ...", 13);
        for (Map.Entry<Player, Integer> entry : ((DeathrunChallenge) Main.getInstance().getChallengeManager().getActiveChallenge()).getSortedPositions()) {
            if (score < 2) break;
            String name = entry.getKey().getName() + ChatColor.RESET + " ";
            String positionString = ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "#" + position;
            positionString += StringUtils.repeat(" ", 8 - positionString.length());
            if (getPlayer().equals(entry.getKey())) name = ChatColor.UNDERLINE + name;
            if (position == 1) setScore(positionString + ChatColor.GOLD + name + ChatColor.AQUA + entry.getValue(), score);
            else if (position == 2) setScore(positionString + ChatColor.YELLOW + name + ChatColor.AQUA + entry.getValue(), score);
            else if (position == 3) setScore(positionString + ChatColor.RED + name + ChatColor.AQUA + entry.getValue(), score);
            else setScore(positionString + ChatColor.WHITE + name + ChatColor.AQUA + entry.getValue(), score);
            score--;
            position++;
        }

    }

}
