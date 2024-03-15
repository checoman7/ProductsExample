package Util;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties = new Properties();

    static {
        try {
            InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties") ;
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while reading config.properties file, Check test/java/Util/ConfigReader");
        }
    }

    public static String getBaseURI() {
        return properties.getProperty("baseURI");
    }
    public static String getMockedUrl() {
        return properties.getProperty("mockedURL");
    }

    public static Boolean isMocked() {
        return Boolean.parseBoolean(properties.getProperty("isMocked"));
    }


}
