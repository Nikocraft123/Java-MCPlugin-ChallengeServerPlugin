//PACKAGE
package de.nikocraft.challengeserver.challenges;


import org.bukkit.event.player.PlayerMoveEvent;

//ABSTRACT CHALLENGE BUILDER CLASS
public abstract class Challenge {

    //CONSTRUCTOR
    public Challenge() {

    }


    //ABSTRACT METHODS

    //Load
    public abstract void load();

    //Update
    public abstract void update();

    //Time over
    public abstract void timeOver();

    //On move
    public abstract void onMove(PlayerMoveEvent event);

}
