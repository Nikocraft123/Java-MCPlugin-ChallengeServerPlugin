//PACKAGE
package de.nikocraft.challengeserver;


//IMPORTS
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.multiverseinventories.MultiverseInventories;
import de.nikocraft.challengeserver.challenges.ChallengeManager;
import de.nikocraft.challengeserver.commands.*;
import de.nikocraft.challengeserver.inventories.enderchests.EnderchestManager;
import de.nikocraft.challengeserver.listeners.ChatListeners;
import de.nikocraft.challengeserver.listeners.ConnectionListeners;
import de.nikocraft.challengeserver.listeners.PlayerListeners;
import de.nikocraft.challengeserver.tablists.TablistManager;
import de.nikocraft.challengeserver.timers.Timer;
import de.nikocraft.challengeserver.utils.Config;
import de.nikocraft.challengeserver.permissions.PermissionManager;
import de.nikocraft.challengeserver.worlds.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


//MAIN CLASS
public final class Main extends JavaPlugin {

    //VARIABLES

    //The instance of the main
    private static Main instance;

    //The plugin prefix
    private static final String prefix = "[Challenge Server Plugin] ";

    //The permission manager of the permission system
    private PermissionManager permissionManager;

    //The enderchest manager of all player enderchests
    private EnderchestManager enderchestManager;

    //The world manager of the game world
    private WorldManager worldManager;

    //The challenge manager for all challenge engines
    private ChallengeManager challengeManager;

    //The timer
    private Timer timer;

    //The main configuration of the plugin
    private Config mainConfig;

    //The permission system configuration
    private Config permissionConfig;

    //The world system configuration
    private Config worldConfig;

    //The inventory configuration
    private Config inventoryConfig;

    //The multiverse api
    private MultiverseCore multiverseCore;
    private MultiverseInventories multiverseInventories;


    //OVERRIDE METHODS

    //Called, if the plugin is loading
    @Override
    public void onLoad() {

        //Set the instance to this
        instance = this;

        //Load configurations
        getLogger().info(getPrefix() + "Load configurations ...");
        String configPath = "./configs/";
        mainConfig = new Config(configPath, "MainConfig.yml");
        permissionConfig = new Config(configPath, "PermissionConfig.yml");
        worldConfig = new Config(configPath, "WorldConfig.yml");
        inventoryConfig = new Config(configPath, "InventoryConfig.yml");

        //Send info
        getLogger().info(getPrefix() + "Plugin loaded.");

    }

    //Called, if the plugin is enabling
    @Override
    public void onEnable() {

        //Load the multiverse api
        multiverseCore = (MultiverseCore) Bukkit.getPluginManager().getPlugin("Multiverse-Core");
        multiverseInventories = (MultiverseInventories) Bukkit.getPluginManager().getPlugin("Multiverse-Inventories");

        //Load the lobby world
        Bukkit.createWorld(new WorldCreator("lobby"));

        //Register listeners
        getLogger().info(getPrefix() + "Register listeners ...");
        Bukkit.getPluginManager().registerEvents(new ConnectionListeners(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListeners(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListeners(), this);

        //Register commands
        getLogger().info(getPrefix() + "Register commands ...");
        getCommand("permission").setExecutor(new PermissionCommand());
        getCommand("enderchest").setExecutor(new EnderchestCommand());
        getCommand("timer").setExecutor(new TimerCommand());
        getCommand("world").setExecutor(new WorldCommand());
        getCommand("lobby").setExecutor(new LobbyWorldCommand());
        getCommand("game").setExecutor(new GameWorldCommand());
        getCommand("parkour").setExecutor(new ParkourCommand());
        getCommand("challenge").setExecutor(new ChallengeCommand());

        //Define the permission manager
        getLogger().info(getPrefix() + "Load permission system ...");
        permissionManager = new PermissionManager();

        //Define the enderchest manager
        getLogger().info(getPrefix() + "Load enderchests ...");
        enderchestManager = new EnderchestManager();

        //Define the world manager
        getLogger().info(getPrefix() + "Load world manager ...");
        worldManager = new WorldManager();

        //Define the challenge manager
        getLogger().info(getPrefix() + "Load challenge manager ...");
        challengeManager = new ChallengeManager();

        //Define the timer
        getLogger().info(getPrefix() + "Load timer ...");
        timer = new Timer();

        //Update all tablist [Only for reload]
        getLogger().info(getPrefix() + "Load all tablist ...");
        TablistManager.setAllPlayerTeams();
        for (Player player : Bukkit.getOnlinePlayers()) TablistManager.setTablistHeaderFooter(player);

        //Send info
        getLogger().info(getPrefix() + "Plugin enabled.");

    }

    //Called, if the plugin is disabling
    @Override
    public void onDisable() {

        //Save all enderchests
        getLogger().info(getPrefix() + "Save enderchests ...");
        enderchestManager.save();

        //Save dimensions of the players
        getLogger().info(getPrefix() + "Save game world ...");
        worldManager.save();

        //Save the timer
        getLogger().info(getPrefix() + "Save timer ...");
        timer.save();

        //Save configurations
        getLogger().info(getPrefix() + "Save configurations ...");
        mainConfig.save();
        permissionConfig.save();
        worldConfig.save();
        inventoryConfig.save();

        //Send info
        getLogger().info(getPrefix() + "Plugin disabled.");

    }


    //METHODS

    //Get the ip of the server
    public static String getIp() {

        //Return the ip
        return Bukkit.getIp();

    }


    //GETTERS

    //The instance of the main
    public static Main getInstance() { return instance; }

    //The plugin prefix
    public static String getPrefix() { return ""; }

    //The permission manager of the permission system
    public PermissionManager getPermissionManager() { return permissionManager; }

    //The enderchest manager of all player enderchests
    public EnderchestManager getEnderchestManager() { return enderchestManager; }

    //The world manager of the game world
    public WorldManager getWorldManager() { return worldManager; }

    //The challenge manager for all challenge engines
    public ChallengeManager getChallengeManager() { return challengeManager; }

    //The timer
    public Timer getTimer() { return timer; }

    //The main configuration of the plugin
    public Config getMainConfig() { return mainConfig; }

    //The permission system configuration
    public Config getPermissionConfig() { return permissionConfig; }

    //The world system configuration
    public Config getWorldConfig() { return worldConfig; }

    //The inventory configuration
    public Config getInventoryConfig() { return inventoryConfig; }

    //The multiverse api
    public MultiverseCore getMultiverseCore() { return multiverseCore; }
    public MultiverseInventories getMultiverseInventories() { return multiverseInventories; }

}
