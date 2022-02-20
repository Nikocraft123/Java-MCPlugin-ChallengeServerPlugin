//PACKAGE
package de.nikocraft.nikocraftserver.tablists;


//IMPORTS
import de.nikocraft.nikocraftserver.Main;
import de.nikocraft.nikocraftserver.permissions.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;


//STATIC TABLIST MANAGER CLASS
public class TablistManager {

    //STATIC METHODS

    //Set the header and footer of the tablist of a new player
    public static void setTablistHeaderFooter(Player player) {

        //Set the header of the tablist
        player.setPlayerListHeader(ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "            " + ChatColor.GRAY +
                " [ " + ChatColor.GOLD + ChatColor.BOLD + "NIKOCRAFT SERVER" + ChatColor.GRAY + " ] " + ChatColor.DARK_GRAY +
                ChatColor.STRIKETHROUGH + "            " +
                ChatColor.RED + ChatColor.BOLD + "\n" + "!Vorsicht BETA Server!" + "\n");

        //Set the footer of the tablist
        player.setPlayerListFooter("\n" + ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "            " + ChatColor.GRAY +
                " [ " + ChatColor.DARK_PURPLE + "IP: " + ChatColor.ITALIC + Main.getIp() + ChatColor.GRAY + " ] " + ChatColor.DARK_GRAY +
                ChatColor.STRIKETHROUGH + "            ");

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
