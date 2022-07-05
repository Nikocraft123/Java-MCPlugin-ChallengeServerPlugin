//PACKAGE
package de.nikocraft.challengeserver.animations;


//IMPORTS
import de.nikocraft.challengeserver.Main;
import de.nikocraft.challengeserver.utils.AnimationBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;


//COOKIE FOUNTAIN ANIMATION CLASS
public class CookieFountainAnimation extends AnimationBuilder {

    //VARIABLES

    //Location
    private Location location;

    //Items
    private List<UUID> items;

    //Cauldron
    private UUID cauldron;


    //CONSTRUCTOR
    public CookieFountainAnimation(Location location) {

        //Initialize the animation
        super();

        //Set the location
        this.location = location;

        //Define the list
        items = new ArrayList<>();

        //Run the animation
        run();

    }


    //OVERRIDE METHODS

    //Start
    @Override
    public void start() {

        //Spawn a new cauldron
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(new Location(location.getWorld(), location.getBlockX() + 0.5, location.getBlockY() - 1.5, location.getBlockZ() + 0.5), EntityType.ARMOR_STAND);
        armorStand.addPassenger(location.getWorld().spawnFallingBlock(new Location(location.getWorld(), 0, 0, 0), Material.CAULDRON.createBlockData()));

        //Set properties
        armorStand.setInvulnerable(true);
        armorStand.setGravity(false);
        armorStand.setInvisible(true);
        ((FallingBlock) armorStand.getPassengers().get(0)).setDropItem(false);
        ((FallingBlock) armorStand.getPassengers().get(0)).setHurtEntities(false);

        //Set the UUID
        cauldron = armorStand.getUniqueId();

    }

    //Update
    @Override
    public void update() {

        //Get the cauldron
        Entity armorStand = Bukkit.getEntity(cauldron);

        //If the cauldron was found
        if (armorStand != null) {

            //Set the living time
            armorStand.getPassengers().get(0).setTicksLived(1);

        }

        //If the duration is greater than 1270
        if (getDuration() > 1270) {

            //Stop
            setStop(true);

            //End
            end();

            //Remove from manager
            Main.getInstance().getAnimationManager().removeAnimation(this);

        }

    }

    //Fast update
    @Override
    public void fastUpdate() {

        //Define to remove
        List<UUID> toRemove = new ArrayList<>();

        //Loop for all items
        for (UUID uuid : items) {

            //Get the item
            Item item = (Item) Bukkit.getEntity(uuid);

            //If the item wasn't found
            if (item == null) {

                //Remove it
                toRemove.add(uuid);

                //Continue
                continue;

            }

            //Check for life duration
            if (item.getTicksLived() > 60 | item.isOnGround()) {

                //Delete it
                item.remove();

                //Remove it
                toRemove.add(uuid);

            }

        }

        //Remove all items
        items.removeAll(toRemove);

    }

    //Tick update
    @Override
    public void tickUpdate() {

        //If the duration is less than 1200
        if (getDuration() < 1200) {

            //Define a new random
            Random random = new Random();

            //Repeat for a random amount
            for (int i = 0; i < (int) (Math.random() * 30); i++) {

                //Spawn a new item
                Item item = (Item) location.getWorld().spawnEntity(new Location(location.getWorld(), location.getBlockX() + 0.5, location.getBlockY() + 0.2, location.getBlockZ() + 0.5), EntityType.DROPPED_ITEM);

                //Set properties
                item.setPickupDelay(100);
                item.setInvulnerable(true);
                item.setItemStack(new ItemStack(Material.COOKIE, 1));
                item.setVelocity(new Vector(random.nextGaussian() / 7, random.nextFloat(1.1f, 1.5f), random.nextGaussian() / 7));
                item.addScoreboardTag("animation");
                item.addScoreboardTag("no_stack");

                //Add the item to the list
                items.add(item.getUniqueId());

            }

        }

    }

    //End
    @Override
    public void end() {

        //Loop for all items
        for (UUID uuid : items) {

            //Get the item
            Item item = (Item) Bukkit.getEntity(uuid);

            //If the item was found
            if (item != null) {

                //Delete it
                item.remove();

            }

        }

        //Get the cauldron
        Entity armorStand = Bukkit.getEntity(cauldron);

        //If the cauldron was found
        if (armorStand != null) {

            //Delete it
            armorStand.getPassengers().get(0).remove();
            armorStand.remove();

        }

    }

}
