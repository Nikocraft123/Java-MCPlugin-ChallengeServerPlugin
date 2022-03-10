//PACKAGE
package de.nikocraft.challengeserver.timers;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;


//TIMER CLASS
public class Timer {

    //VARIABLES

    //Is the timer running
    private boolean running;

    //The time of the timer
    private int time;

    //The default time, what is used to reset the timer
    private int defaultTime;

    //The mode of the timer ("count" & "stop")
    private String mode;

    //The main config (from main)
    private final Config config;

    //The ID of the run scheduler
    private int runScheduler;


    //CONSTRUCTORS
    public Timer() {

        //Get the main config from main
        config = Main.getInstance().getMainConfig();

        //Load timer data
        if (config.getConfig().contains("timer.running")) running = config.getConfig().getBoolean("timer.running"); else running = false;
        if (config.getConfig().contains("timer.time")) time = config.getConfig().getInt("timer.time"); else time = 0;
        if (config.getConfig().contains("timer.defaultTime")) defaultTime = config.getConfig().getInt("timer.defaultTime"); else defaultTime = 0;
        if (config.getConfig().contains("timer.mode")) mode = config.getConfig().getString("timer.mode"); else mode = "stop";

    }


    //METHODS

    //Save
    public void save() {

        //Save timer data
        config.getConfig().set("timer.running", isRunning());
        config.getConfig().set("timer.time", getTime());
        config.getConfig().set("timer.defaultTime", getDefaultTime());
        config.getConfig().set("timer.mode", getMode());

        //Save the config
        config.save();

    }

    //Run
    private void run() {

        //Scheduler
        runScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

            //Run
            @Override
            public void run() {

                //Return, if the timer is not running, return
                if (!isRunning()) return;

                //If the mode "stop":
                if (getMode().equals("stop")) {
                    //Time + 1
                    setTime(getTime() + 1);
                }
                //If the mode "count":
                if (getMode().equals("count")) {
                    //Time - 1
                    setTime(getTime() - 1);

                    //If the timer run off:
                    if (getTime() == 0) {

                        //Send message
                        Bukkit.broadcastMessage(getChatPrefix() + ChatColor.YELLOW +
                                ChatColor.UNDERLINE + "The timer run off!");

                        //Call timer reset
                        TimerReset();

                    }
                }

            }

        }, 1, 1);

    }



    //Get the prefix of the command for the console
    public static String getConsolePrefix() {

        //Return prefix string
        return "[Timer] ";

    }

    //Get the prefix of the command for the chat
    public static String getChatPrefix() {

        //Return prefix string
        return ChatColor.GRAY + "[" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Timer" + ChatColor.RESET +
                ChatColor.GRAY + "] " + ChatColor.WHITE;

    }

    //Timer resume
    public boolean TimerResume() {

        //Return false, if the timer is already running
        if (isRunning()) return false;

        //Set running to true
        setRunning(true);

        //Send message
        Bukkit.broadcastMessage(getChatPrefix() + "The timer has been resumed!");

        //Save the changes
        save();

        //Return true
        return true;

    }

    //Timer pause
    public boolean TimerPause() {

        //Return false, if the timer is already paused
        if (!isRunning()) return false;

        //Set running to false
        setRunning(false);

        //Send message
        Bukkit.broadcastMessage(getChatPrefix() + "The timer has been paused!");

        //Save the changes
        save();

        //Return true
        return true;

    }

    //Timer reset
    public boolean TimerReset() {

        //Return false, if the timer is already reset
        if (!isRunning() && getTime() == getDefaultTime()) return false;

        //Reset the timer
        setRunning(false);
        setTime(getDefaultTime());

        //Send message
        Bukkit.broadcastMessage(getChatPrefix() + "The timer has been reset!");

        //Save the changes
        save();

        //Return true
        return true;

    }

    //Timer mode
    public boolean TimerMode(String mode) {

        //Return false, if the mode of the timer is already this mode
        if (mode.equals(getMode())) return false;

        //Set mode
        setMode(mode);

        //Send message
        Bukkit.broadcastMessage(getChatPrefix() + "The mode of the timer has been set to " + getMode() + "!");

        //Save the changes
        save();

        //Return true
        return true;

    }

    //Timer time
    public boolean TimerTime(int time) {

        //Return false, if the time of the timer is already this time
        if (getTime() == time) return false;

        //Set time
        setTime(time);

        //Set default Time
        setDefaultTime(time);

        //Send message
        Bukkit.broadcastMessage(getChatPrefix() + "The time of the timer has been set to " + getTime() + "!");

        //Save the changes
        save();

        //Return true
        return true;

    }


    //GETTERS

    //Is the timer running
    public boolean isRunning() {
        return running;
    }

    //The time of the timer
    public int getTime() {
        return time;
    }

    //The default time, what is used to reset the timer
    public int getDefaultTime() {
        return defaultTime;
    }

    //The mode of the timer ("count" & "stop")
    public String getMode() {
        return mode;
    }

    //The ID of the run scheduler
    public int getRunScheduler() {
        return runScheduler;
    }


    //SETTERS

    //Is the timer running
    public void setRunning(boolean running) {
        this.running = running;
    }

    //The time of the timer
    public void setTime(int time) {
        this.time = time;
    }

    //The default time, what is used to reset the timer
    public void setDefaultTime(int defaultTime) {
        this.defaultTime = defaultTime;
    }

    //The ID of the run scheduler
    public void setMode(String mode) {
        this.mode = mode;
    }

}