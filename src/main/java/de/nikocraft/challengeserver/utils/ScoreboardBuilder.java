//PACKAGE
package de.nikocraft.challengeserver.utils;


//IMPORTS
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;


//SCOREBOARD BUILDER CLASS
public abstract class ScoreboardBuilder {

    //VARIABLES

    //The scoreboard and objective
    private final Scoreboard scoreboard;
    private final Objective objective;

    //The player of the scoreboard
    private final Player player;


    //CONSTRUCTOR
    public ScoreboardBuilder(Player player, String displayName) {

        //Set the player
        this.player = player;

        //Get the new player scoreboard
        scoreboard = player.getScoreboard();

        //Unregister old display objective
        if (scoreboard.getObjective("display") != null) scoreboard.getObjective("display").unregister();

        //Register new display objective and set it to sidebar
        objective = scoreboard.registerNewObjective("display", "dummy", displayName);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        //Create the scoreboard
        create();

        //Update the scoreboard
        update();

    }


    //ABSTRACT METHODS

    //Create the scoreboard
    public abstract void create();

    //Update the scoreboard
    public abstract void update();


    //METHODS

    //Set the display name
    public void setDisplayName(String displayName) {

        //Set the display name to the objective
        objective.setDisplayName(displayName);

    }

    //Set a score
    public void setScore(String content, int score) {

        //Get the team
        Team team = getTeam(score);

        //If no team was found, return
        if (team == null) return;

        //Set the team prefix
        team.setPrefix(content);

        //Show score
        showScore(score);

    }

    //Remove a score
    public void removeScore(int score) {

        //Hide score
        hideScore(score);

    }

    //Get an entry name from a score
    private ScoreboardEntryName getEntryName(int score) {

        //Loop for all entry names
        for (ScoreboardEntryName entry : ScoreboardEntryName.values()) {
            if (entry.getScore() == score) return entry;
        }

        //Return null
        return null;

    }

    //Get a team from a score
    private Team getTeam(int score) {

        //Get the entry
        ScoreboardEntryName entry = getEntryName(score);

        //If no entry was found, return null
        if (entry == null) return null;

        //Get the team
        Team team = scoreboard.getEntryTeam(entry.getName());

        //If the team was found, return it
        if (team != null) return team;

        //Create a new team
        team = scoreboard.registerNewTeam(entry.name());
        team.addEntry(entry.getName());

        //Return the new team
        return team;

    }

    //Show a score
    private void showScore(int score) {

        //Get the entry
        ScoreboardEntryName entry = getEntryName(score);

        //If no entry was found, return
        if (entry == null) return;

        //If the score is already set, return
        if (objective.getScore(entry.getName()).isScoreSet()) return;

        //Set the score
        objective.getScore((entry.getName())).setScore(score);

    }

    //Hide a score
    private void hideScore(int score) {

        //Get the entry
        ScoreboardEntryName entry = getEntryName(score);

        //If no entry was found, return
        if (entry == null) return;

        //If the score is already set, return
        if (!objective.getScore(entry.getName()).isScoreSet()) return;

        //Reset the score of the content
        scoreboard.resetScores(entry.getName());

    }

    //Delete the scoreboard
    public void delete() {

        //Unregister the display objective
        objective.unregister();

    }


    //GETTERS

    //The player of the scoreboard
    public Player getPlayer() { return player; }

}
