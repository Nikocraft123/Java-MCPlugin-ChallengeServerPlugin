//PACKAGE
package de.nikocraft.nikocraftserver;


//IMPORTS
import de.nikocraft.nikocraftserver.commands.EnderchestCommand;
import de.nikocraft.nikocraftserver.commands.PermissionCommand;
import de.nikocraft.nikocraftserver.inventories.enderchests.EnderchestManager;
import de.nikocraft.nikocraftserver.listeners.ConnectionListeners;
import de.nikocraft.nikocraftserver.tablists.TablistManager;
import de.nikocraft.nikocraftserver.utils.Config;
import de.nikocraft.nikocraftserver.permissions.PermissionManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


//MAIN CLASS
public final class Main extends JavaPlugin {

    //VARIABLES

    //The instance of the main
    private static Main instance;

    //The plugin prefix
    private static final String prefix = "[Server Main Plugin] ";

    //The permission manager of the permission system
    private PermissionManager permissionManager;

    //The enderchest manager of all player enderchests
    private EnderchestManager enderchestManager;

    //The main configuration of the plugin
    private Config mainConfig;

    //The permission system configuration
    private Config permissionConfig;

    //The world system configuration
    private Config worldConfig;

    //The inventory configuration
    private Config inventoryConfig;


    //OVERRIDE METHODS

    //Called, if the plugin is loading
    @Override
    public void onLoad() {

        //Set the instance to this
        instance = this;

        //Load configurations
        System.out.println(getPrefix() + "Load configurations ...");
        String configPath = "./configs/";
        mainConfig = new Config(configPath, "MainConfig.yml");
        permissionConfig = new Config(configPath, "PermissionConfig.yml");
        worldConfig = new Config(configPath, "WorldConfig.yml");
        inventoryConfig = new Config(configPath, "InventoryConfig.yml");

        //Send info
        System.out.println(getPrefix() + "Plugin loaded.");

    }

    //Called, if the plugin is enabling
    @Override
    public void onEnable() {

        //Register listeners
        System.out.println(getPrefix() + "Register listeners ...");
        Bukkit.getPluginManager().registerEvents(new ConnectionListeners(), this);

        //Register commands
        System.out.println(getPrefix() + "Register commands ...");
        getCommand("permission").setExecutor(new PermissionCommand());
        getCommand("enderchest").setExecutor(new EnderchestCommand());

        //Define the permission manager
        System.out.println(getPrefix() + "Load permission system ...");
        permissionManager = new PermissionManager();

        //Define the enderchest manager
        System.out.println(getPrefix() + "Load enderchests ...");
        enderchestManager = new EnderchestManager();

        //Update all tablist [Only for reload]
        System.out.println(getPrefix() + "Load all tablist ...");
        TablistManager.setAllPlayerTeams();
        for (Player player : Bukkit.getOnlinePlayers()) TablistManager.setTablistHeaderFooter(player);

        //Send info
        System.out.println(getPrefix() + "Plugin enabled.");

    }

    //Called, if the plugin is disabling
    @Override
    public void onDisable() {

        //Save all enderchests
        System.out.println(getPrefix() + "Save enderchests ...");
        enderchestManager.save();

        //Save configurations
        System.out.println(getPrefix() + "Save configurations ...");
        mainConfig.save();
        permissionConfig.save();
        worldConfig.save();
        inventoryConfig.save();

        //Send info
        System.out.println(getPrefix() + "Plugin disabled.");

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
    public static String getPrefix() { return prefix; }

    //The permission manager of the permission system
    public PermissionManager getPermissionManager() { return permissionManager; }

    //The enderchest manager of all player enderchests
    public EnderchestManager getEnderchestManager() { return enderchestManager; }

    //The main configuration of the plugin
    public Config getMainConfig() { return mainConfig; }

    //The permission system configuration
    public Config getPermissionConfig() { return permissionConfig; }

    //The world system configuration
    public Config getWorldConfig() { return worldConfig; }

    //The inventory configuration
    public Config getInventoryConfig() { return inventoryConfig; }

}
