//PACKAGE
package de.nikocraft.challengeserver;


//IMPORTS
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.multiverseinventories.MultiverseInventories;
import de.nikocraft.challengeserver.animations.AnimationManager;
import de.nikocraft.challengeserver.challenges.Challenge;
import de.nikocraft.challengeserver.challenges.ChallengeManager;
import de.nikocraft.challengeserver.commands.*;
import de.nikocraft.challengeserver.inventories.enderchests.EnderchestManager;
import de.nikocraft.challengeserver.inventories.players.PlayerInventoryManager;
import de.nikocraft.challengeserver.listeners.*;
import de.nikocraft.challengeserver.parkours.ParkourManager;
import de.nikocraft.challengeserver.shop.CoinCookieInfo;
import de.nikocraft.challengeserver.shop.CoinManager;
import de.nikocraft.challengeserver.shop.CookieManager;
import de.nikocraft.challengeserver.tablists.TablistManager;
import de.nikocraft.challengeserver.timers.Timer;
import de.nikocraft.challengeserver.utils.Config;
import de.nikocraft.challengeserver.permissions.PermissionManager;
import de.nikocraft.challengeserver.visibility.VisibilityManager;
import de.nikocraft.challengeserver.worlds.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.A;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;


//MAIN CLASS
public final class Main extends JavaPlugin {

    //VARIABLES

    //The instance of the main
    private static Main instance;

    //The plugin prefix
    private static final String prefix = "[Challenge Server Plugin] ";

    //The IP of the server
    private String serverIP;

    //The permission manager of the permission system
    private PermissionManager permissionManager;

    //The enderchest manager of all player enderchests
    private EnderchestManager enderchestManager;

    //The world manager of the game world
    private WorldManager worldManager;

    //The challenge manager for all challenge engines
    private ChallengeManager challengeManager;

    //The parkour manager for all parkour in the lobby
    private ParkourManager parkourManager;

    //The player inventory manager for players in the lobby
    private PlayerInventoryManager playerInventoryManager;

    //The cookie manager of the lobby
    private CookieManager cookieManager;

    //The coin manager of the lobby
    private CoinManager coinManager;

    //The visibility manager of the lobby
    private VisibilityManager visibilityManager;

    //The animation manager of all animations is the lobby
    private AnimationManager animationManager;

    //The timer
    private Timer timer;

    //The coin and cookie info
    private CoinCookieInfo coinCookieInfo;

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

        //Register channels
        getLogger().info(getPrefix() + "Register channels ...");
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        //Load the ip
        if (mainConfig.getConfig().contains("ip")) serverIP = mainConfig.getConfig().getString("ip"); else serverIP = "Unknown";

        //Register listeners
        getLogger().info(getPrefix() + "Register listeners ...");
        Bukkit.getPluginManager().registerEvents(new ConnectionListeners(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListeners(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListeners(), this);
        Bukkit.getPluginManager().registerEvents(new InteractListeners(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryListeners(), this);
        Bukkit.getPluginManager().registerEvents(new ItemListeners(), this);
        Bukkit.getPluginManager().registerEvents(new EntityListeners(), this);

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
        getCommand("parkour_checkpoint").setExecutor(new ParkourCheckpointCommand());
        getCommand("parkour_cancel").setExecutor(new ParkourCancelCommand());
        getCommand("inventory").setExecutor(new InventoryCommand());
        getCommand("display_ip").setExecutor(new IpCommand());
        getCommand("player").setExecutor(new PlayerCommand());
        getCommand("serv").setExecutor(new ServerCommand());
        getCommand("shop").setExecutor(new ShopCommand());
        getCommand("cookies").setExecutor(new CookiesCommand());
        getCommand("coins").setExecutor(new CoinsCommand());
        getCommand("challenge_deathrun_restart").setExecutor(new ChallengeDeathrunRestartCommand());

        //TODO
        getCommand("cookie_fountain_animation").setExecutor(new CookieFountainAnimationCommand());

        //Define the permission manager
        getLogger().info(getPrefix() + "Load permission system ...");
        permissionManager = new PermissionManager();

        //Define the world manager
        getLogger().info(getPrefix() + "Load world manager ...");
        worldManager = new WorldManager();

        //Define the visibility manager
        getLogger().info(getPrefix() + "Load visibility manager ...");
        visibilityManager = new VisibilityManager();

        //Define the animation manager
        getLogger().info(getPrefix() + "Load animation manager ...");
        animationManager = new AnimationManager();

        //Define the challenge manager
        getLogger().info(getPrefix() + "Load challenge manager ...");
        challengeManager = new ChallengeManager();

        //Define the parkour manager
        getLogger().info(getPrefix() + "Load parkour manager ...");
        parkourManager = new ParkourManager();

        //Define the player inventory manager
        getLogger().info(getPrefix() + "Load player inventory manager ...");
        playerInventoryManager = new PlayerInventoryManager();

        //Define the enderchest manager
        getLogger().info(getPrefix() + "Load enderchests ...");
        enderchestManager = new EnderchestManager();

        //Define the cookie manager
        getLogger().info(getPrefix() + "Load cookies ...");
        cookieManager = new CookieManager();

        //Define the coin manager
        getLogger().info(getPrefix() + "Load coins ...");
        coinManager = new CoinManager();

        //Define the coin and cookie info
        coinCookieInfo = new CoinCookieInfo();

        //Define the timer
        getLogger().info(getPrefix() + "Load timer ...");
        timer = new Timer();

        //Update all tablist [Only for reload]
        getLogger().info(getPrefix() + "Load all tablist ...");
        TablistManager.setAllPlayerTeams();
        for (Player player : Bukkit.getOnlinePlayers()) TablistManager.setTablistHeaderFooter(player);

        //Deactivate PVP in the lobby
        Bukkit.getWorld("lobby").setPVP(false);
        Bukkit.getWorld("lobby_nether").setPVP(false);
        Bukkit.getWorld("lobby_the_end").setPVP(false);

        //Send info
        getLogger().info(getPrefix() + "Plugin enabled.");

    }

    //Called, if the plugin is disabling
    @Override
    public void onDisable() {

        //Unregister channels
        getLogger().info(getPrefix() + "Unregister channels ...");
        getServer().getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");

        //Save all enderchests
        getLogger().info(getPrefix() + "Save enderchests ...");
        enderchestManager.save();

        //Get the active challenge
        Challenge challenge = Main.getInstance().getChallengeManager().getActiveChallenge();

        //If the challenge is not null
        if (challenge != null) {

            //If the challenge is running, end it
            if (challenge.isRunning()) challenge.end();

            //Unload the challenge
            challenge.unload();

        }

        //Stop all animations
        getLogger().info(getPrefix() + "Stop animations ...");
        animationManager.stopAll();

        //Save dimensions of the players
        getLogger().info(getPrefix() + "Save game world ...");
        worldManager.save();

        //Save the timer
        getLogger().info(getPrefix() + "Save timer ...");
        timer.save();

        //Save the parkour list
        getLogger().info(getPrefix() + "Save parkour data ...");
        parkourManager.save();

        //Save player inventory active
        getLogger().info(getPrefix() + "Save player inventory active ...");
        playerInventoryManager.save();

        //Save cookies
        getLogger().info(getPrefix() + "Save cookies ...");
        cookieManager.save();

        //Save coins
        getLogger().info(getPrefix() + "Save coins ...");
        coinManager.save();

        //Save the ip
        mainConfig.getConfig().set("ip", getServerIP());

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

    //Send player
    public boolean sendPlayer(Player player, String server) {

        //Define the streams
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        //Write the message
        try {
            dataOutputStream.writeUTF("Connect");
            dataOutputStream.writeUTF(server);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        //Send the message
        player.sendPluginMessage(this, "BungeeCord", byteArrayOutputStream.toByteArray());

        //Close the streams
        try {
            dataOutputStream.close();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            return false;
        }

        //Return true
        return true;

    }


    //GETTERS

    //The instance of the main
    public static Main getInstance() { return instance; }

    //The plugin prefix
    public static String getPrefix() { return ""; }

    //The IP of the server
    public String getServerIP() { return serverIP; }

    //The permission manager of the permission system
    public PermissionManager getPermissionManager() { return permissionManager; }

    //The enderchest manager of all player enderchests
    public EnderchestManager getEnderchestManager() { return enderchestManager; }

    //The world manager of the game world
    public WorldManager getWorldManager() { return worldManager; }

    //The challenge manager for all challenge engines
    public ChallengeManager getChallengeManager() { return challengeManager; }

    //The parkour manager for all parkour in lobby
    public ParkourManager getParkourManager() { return parkourManager; }

    //The player inventory manager for players in the lobby
    public PlayerInventoryManager getPlayerInventoryManager() { return playerInventoryManager; }

    //The cookie manager of the lobby
    public CookieManager getCookieManager() { return cookieManager; }

    //The coin manager of the lobby
    public CoinManager getCoinManager() { return coinManager; }

    //The visibility manager of the lobby
    public VisibilityManager getVisibilityManager() { return visibilityManager; }

    //The animation manager of all animations is the lobby
    public AnimationManager getAnimationManager() { return animationManager; }

    //The timer
    public Timer getTimer() { return timer; }

    //The coin and cookie info
    public CoinCookieInfo getCoinCookieInfo() { return coinCookieInfo; }

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


    //SETTERS

    //The IP of the server
    public void setServerIP(String serverIP) { this.serverIP = serverIP; }

}
