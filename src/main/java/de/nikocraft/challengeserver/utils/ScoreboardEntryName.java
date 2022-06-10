//PACKAGE
package de.nikocraft.challengeserver.utils;


import org.bukkit.ChatColor;

//SCOREBOARD ENTRY ENUM
public enum ScoreboardEntryName {

    //ENTRIES
    ENTRY_0(0, ChatColor.DARK_PURPLE.toString()),
    ENTRY_1(1, ChatColor.GRAY.toString()),
    ENTRY_2(2, ChatColor.GOLD.toString()),
    ENTRY_3(3, ChatColor.YELLOW.toString()),
    ENTRY_4(4, ChatColor.DARK_RED.toString()),
    ENTRY_5(5, ChatColor.DARK_GREEN.toString()),
    ENTRY_6(6, ChatColor.GREEN.toString()),
    ENTRY_7(7, ChatColor.RED.toString()),
    ENTRY_8(8, ChatColor.BLUE.toString()),
    ENTRY_9(9, ChatColor.AQUA.toString()),
    ENTRY_10(10, ChatColor.BLACK.toString()),
    ENTRY_11(11, ChatColor.DARK_AQUA.toString()),
    ENTRY_12(12, ChatColor.DARK_BLUE.toString()),
    ENTRY_13(13, ChatColor.DARK_GRAY.toString()),
    ENTRY_14(14, ChatColor.LIGHT_PURPLE.toString()),
    ENTRY_15(15, ChatColor.WHITE.toString());


    //VARIABLES

    //The entry id
    private final int score;

    //The entry name
    private final String name;


    //CONSTRUCTOR
    ScoreboardEntryName(int score, String name) {
        this.score = score;
        this.name = name;
    }


    //GETTERS

    //The entry id
    public int getScore() { return score; }

    //The entry name
    public String getName() { return name; }

}
