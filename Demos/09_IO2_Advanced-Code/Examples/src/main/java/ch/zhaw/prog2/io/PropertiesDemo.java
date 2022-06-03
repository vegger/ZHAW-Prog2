package ch.zhaw.prog2.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Properties;

public class PropertiesDemo {
    // create empty properties object
    private final Properties config = new Properties();

    public static void main(String[] args) {
        Path configPath = Path.of(args.length>=1 ? args[0] : "./demo/config.properties");
        new PropertiesDemo().initConfig(configPath);
    }

    public void initConfig(Path configPath) {
        // load properties from config file
        try (Reader configReader = Files.newBufferedReader(configPath)){
            config.load(configReader);
        } catch (IOException e) {
            System.out.println("No configuration found. Load defaults.");
            // load properties from resource file
            try (InputStream defaultConfig = this.getClass().getClassLoader().getResourceAsStream("config.properties")) {
                config.load(defaultConfig);
            } catch (IOException ioException) {
                System.err.println("No default config. Create manual config.");
                config.setProperty("name", this.getClass().getSimpleName());
                config.setProperty("version", "1.0");
                config.setProperty("date.created", LocalDate.now().toString());
            }
        }

        // lookup properties
        System.out.println("App Name: " + config.getProperty("name"));
        // returns null, because not available
        System.out.println("App Version: " + config.getProperty("app.version"));
        // provide default value if not found
        System.out.println("Last used: " + config.getProperty("date.used", LocalDate.now().toString()));

        // set property
        config.setProperty("date.used", LocalDate.now().toString());

        // store properties to a file
        try (Writer configWriter = Files.newBufferedWriter(configPath)) {
            config.store(configWriter, "Application Configuration");
        } catch (IOException e) {
            System.err.println("Failed to write configuration: " + e.getMessage());
        }

        // get the names of all properties
        System.out.println("\nAPP PROPERTIES");
        for (String propertyName : config.stringPropertyNames()) {
            System.out.printf("- Property: key = %s, value = %s%n", propertyName, config.getProperty(propertyName));
        }

        // get and output all system properties to PrintStream
        System.out.println("\nSYSTEM PROPERTIES");
        Properties systemProperties = System.getProperties();
        systemProperties.list(System.out);
    }
}
