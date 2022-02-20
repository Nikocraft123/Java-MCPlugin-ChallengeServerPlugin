//PACKAGE
package de.nikocraft.nikocraftserver.utils;


//IMPORTS
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;


//CONFIGURATION CLASS
public class Config {

    //VARIABLES

    //The configuration file
    private final File file;

    //The configuration in YAML
    private final YamlConfiguration config;


    //CONSTRUCTOR
    public Config(String dirPath, String fileName) {

        //Define a directory object with the directory path
        File dir = new File(dirPath);

        //Create the directory, if it doesn't exist
        if (!dir.exists()) dir.mkdirs();

        //Define the configuration file with the file name
        file = new File(dir, fileName);

        //If the configuration file doesn't exist
        if (!file.exists()) {
            try {
                //Try to create the file
                file.createNewFile();
            } catch (IOException e) {
                //Catch Error
                e.printStackTrace();
            }
        }

        //Define and load the YAML configuration from file
        config = YamlConfiguration.loadConfiguration(file);

    }


    //METHODS

    //Save the configuration to file
    public void save() {

        try {
            //Try to save to file
            config.save(file);
        } catch (IOException e) {
            //Catch Error
            e.printStackTrace();
        }

    }


    //GETTERS

    //The configuration file
    public File getFile() {
        return file;
    }

    //The configuration in YAML
    public YamlConfiguration getConfig() {
        return config;
    }

}
