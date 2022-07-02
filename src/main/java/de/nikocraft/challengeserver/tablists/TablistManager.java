//PACKAGE
package de.nikocraft.challengeserver.tablists;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.permissions.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;


//STATIC TABLIST MANAGER CLASS
public class TablistManager {

    //STATIC METHODS

    //Set the header and footer of the tablist of a new player
    public static void setTablistHeaderFooter(Player player) {

        //Set the header of the tablist
        player.setPlayerListHeader(ChatColor.GRAY + "               [ " + ChatColor.GOLD + ChatColor.BOLD + "CHALLENGE SERVER" + ChatColor.GRAY + " ]               " +
                ChatColor.RED + ChatColor.BOLD + "\n" + "!Vorsicht ALPHA Server!" + "\n");

        //Set the footer of the tablist
        player.setPlayerListFooter("\n" + ChatColor.GRAY +
                "[ " + ChatColor.DARK_PURPLE + "IP: " + ChatColor.ITALIC + Main.getInstance().getServerIP() + ChatColor.GRAY + " ]");

    }

    //Set all player teams
    public static void setAllPlayerTeams() {

        //Set for each online player the teams
        for (Player player : Bukkit.getOnlinePlayers()) setPlayerTeams(player);

    }

    //Set player teams
    public static void setPlayerTeams(Player player) {

        //Get the scoreboard of the player
        Scoreboard scoreboard = player.getScoreboard();

        //If there is no objective health
        if (scoreboard.getObjective("health") == null) {

            //Register new health objective and set it to sidebar
            scoreboard.registerNewObjective("health", "health", "");
            scoreboard.getObjective("health").setDisplaySlot(DisplaySlot.PLAYER_LIST);
            scoreboard.getObjective("health").setRenderType(RenderType.HEARTS);

        }

        //For in all player ranks
        for (Rank rank : Rank.values()) {

            //Get the team from rank
            Team team = scoreboard.getTeam(rank.getRankId() + rank.getRankName());

            //If the team doesn't exist, register it
            if (team == null) team = scoreboard.registerNewTeam(rank.getRankId() + rank.getRankName());

            //Set the prefix of the team
            team.setPrefix(ChatColor.GRAY + "[" + rank.getColoredName() + ChatColor.GRAY + "] ");

            //Set the color of the team
            team.setColor(rank.getColor());

            //Loop for all online players
            for (Player target : Bukkit.getOnlinePlayers()) {
                //If the rank of the target is equals the current team, add it
                if (Main.getInstance().getPermissionManager().getPlayerRank(target) == rank) team.addEntry(target.getName());
            }

        }

    }

}
