//PACKAGE
package de.nikocraft.challengeserver.utils;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import org.bukkit.Bukkit;


//ABSTRACT ANIMATION BUILDER CLASS
public abstract class AnimationBuilder {

    //VARIABLES

    //Scheduler IDs
    private int schedulerIdUpdate;
    private int schedulerIdFastUpdate;
    private int schedulerIdTickUpdate;

    //Stop
    private boolean stop;

    //Duration
    private int duration;


    //CONSTRUCTOR
    public AnimationBuilder() {

        //Set stop to false
        stop = false;

        //Set the duration
        duration = 0;

    }


    //ABSTRACT METHODS

    //Start
    public abstract void start();

    //Update
    public abstract void update();

    //Fast update
    public abstract void fastUpdate();

    //Tick update
    public abstract void tickUpdate();

    //End
    public abstract void end();


    //METHODS

    //Run
    public void run() {

        //Call start
        start();

        //Scheduler update
        schedulerIdUpdate = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

            //Run
            @Override
            public void run() {

                //Call update
                update();

            }

        }, 20, 20);

        //Scheduler fast update
        schedulerIdFastUpdate = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

            //Run
            @Override
            public void run() {

                //Call fast update
                fastUpdate();

            }

        }, 5, 5);

        //Scheduler tick update
        schedulerIdTickUpdate = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

            //Run
            @Override
            public void run() {

                //Check for animation stop
                if (stop) {

                    //Cancel all tasks
                    Bukkit.getScheduler().cancelTask(schedulerIdUpdate);
                    Bukkit.getScheduler().cancelTask(schedulerIdFastUpdate);
                    Bukkit.getScheduler().cancelTask(schedulerIdTickUpdate);

                }

                //Call tick update
                tickUpdate();

                //Update the duration
                duration++;

            }

        }, 1, 1);

    }


    //GETTERS

    //Stop
    public boolean isStop() { return stop; }

    //Duration
    public int getDuration() { return duration; }


    //SETTERS

    //Stop
    public void setStop(boolean stop) { this.stop = stop; }

}
