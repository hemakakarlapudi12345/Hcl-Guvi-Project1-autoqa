package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    
    static {
        try {
            properties = new Properties();
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file", e);
        }
    }
    
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public static String getBrowser() {
        return getProperty("browser");
    }
    
    public static String getUrl() {
        return getProperty("url");
    }
    
    public static int getImplicitWait() {
        return Integer.parseInt(getProperty("implicitWait"));
    }
    
    public static String getTestDataPath() {
        return getProperty("testDataPath");
    }
}
