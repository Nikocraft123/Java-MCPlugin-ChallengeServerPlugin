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


    //CONSTRUCTOR
    public ChallengeManager() {

        //Define the challenge map
        challenges = new HashMap<>();

        //Define all challenges
        challenges.put("deathrun", new DeathrunChallenge());

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

                Main.getInstance().getDeathrunChallenge().update();

            }

        }, 20, 20);

    }


    //GETTERS

    //The map of all challenges
    public Map<String, Challenge> getChallenges() { return challenges; }

}
