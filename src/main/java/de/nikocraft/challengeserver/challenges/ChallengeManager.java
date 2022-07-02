//PACKAGE
package de.nikocraft.challengeserver.challenges;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.challenges.deathrun.DeathrunChallenge;
import de.nikocraft.challengeserver.utils.MathUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;


//CHALLENGE MANAGER CLASS
public class ChallengeManager {

    //The map of all challenges
    private final Map<String, Challenge> challenges;

    //Active challenge
    private String active;


    //CONSTRUCTOR
    public ChallengeManager() {

        //Define the challenge map
        challenges = new HashMap<>();

        //Define all challenges
        challenges.put("deathrun", new DeathrunChallenge());

        //Define active with an empty string
        active = "";

        //Run the update scheduler
        run();

    }


    //METHODS

    //Run
    private void run() {

        //Scheduler update
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

            //Run
            @Override
            public void run() {

                //Get the active challenge
                Challenge challenge = getActiveChallenge();

                //If the challenge is not null
                if (challenge != null) {

                    //If the challenge is running
                    if (challenge.isRunning()) {

                        //Update the challenge
                        challenge.update();

                    }

                }

            }

        }, 20, 20);

        //Scheduler fast update
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

            //Run
            @Override
            public void run() {

                //Get the active challenge
                Challenge challenge = getActiveChallenge();

                //If the challenge is not null
                if (challenge != null) {

                    //If the challenge is running
                    if (challenge.isRunning()) {

                        //Update the challenge
                        challenge.fastUpdate();

                    }

                }

            }

        }, 5, 5);

        //Scheduler tick update
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

            //Run
            @Override
            public void run() {

                //Get the active challenge
                Challenge challenge = getActiveChallenge();

                //If the challenge is not null
                if (challenge != null) {

                    //If the challenge is running
                    if (challenge.isRunning()) {

                        //Update the challenge
                        challenge.tickUpdate();

                    }

                }

            }

        }, 1, 1);

    }

    //Get active challenge
    public Challenge getActiveChallenge() {

        //Return the active challenge from the map
        return challenges.get(active);

    }

    //Set active challenge
    public void setActiveChallenge(String name) {

        //Set the name of the new active challenge
        active = name;

    }


    //STATIC METHODS

    //Get the prefix of the command for the chat
    public static String getChatPrefix() {

        //Return prefix string
        return ChatColor.GRAY + "[" + ChatColor.RED + ChatColor.BOLD + "Challenge" + ChatColor.RESET +
                ChatColor.GRAY + "] " + ChatColor.WHITE;

    }


    //GETTERS

    //The map of all challenges
    public Map<String, Challenge> getChallenges() { return challenges; }

}
