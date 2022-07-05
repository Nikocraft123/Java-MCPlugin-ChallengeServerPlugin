//PACKAGE
package de.nikocraft.challengeserver.animations;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.utils.AnimationBuilder;
import java.util.ArrayList;
import java.util.List;


//ANIMATION MANAGER CLASS
public class AnimationManager {

    //Animations
    List<AnimationBuilder> animations;


    //CONSTRUCTOR
    public AnimationManager() {

        //Define animations
        animations = new ArrayList<>();

    }


    //METHODS

    //Add animation
    public void addAnimation(AnimationBuilder animation) {

        //Add it to the list
        animations.add(animation);

    }

    //Remove animation
    public void removeAnimation(AnimationBuilder animation) {

        //Remove it from the list
        animations.remove(animation);

    }

    //Stop all
    public void stopAll() {

        //Loop for all animations
        for (AnimationBuilder animation : animations) {

            //Stop the animation
            animation.setStop(true);

            //End
            animation.end();

        }
    }


    //GETTERS

    //Get animations
    public List<AnimationBuilder> getAnimations() { return animations; }

}
