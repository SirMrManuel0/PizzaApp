package de.sirmrmanuel0.logic;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static final String CONFIG_FILE_PATH = "lookUps.config";
    private static final String[] LOOK_UP = new String[]{
            "background",
            "logo1",
            "logo2",
            "pizzenPack",
            "pizzenDir",
            "srcDir"
    };

    public static final int BACKGROUND = 0;
    public static final int LOGO_1 = 1;
    public static final int LOGO_2 = 2;
    public static final int PIZZEN_PACK = 3;
    public static final int PIZZEN_DIR = 4;
    public static final int SRC_DIR = 5;


    public static String readConfig(int key) throws javax.naming.ConfigurationException {
        String keyStr = "";
        try{
            keyStr = LOOK_UP[key];
        } catch (Exception e){
            throw new javax.naming.ConfigurationException("Please use a valid key!");
        }

        try {
            Properties properties = new Properties();
            properties.load(new FileReader(CONFIG_FILE_PATH));
            String value = properties.getProperty(keyStr);
            if (value == null){
                throw new javax.naming.ConfigurationException("Key: " + keyStr + " of: " + key + " has no value in the config file.");
            }
            return value;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    class ConfigurationException extends Exception {
        public ConfigurationException(String message) {
            super(message);
        }
    }
}

