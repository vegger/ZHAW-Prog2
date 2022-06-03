package ch.zhaw.prog2.io;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.logging.*;

public class LogConfiguration {
    private static final Logger logger = Logger.getLogger(LogConfiguration.class.getCanonicalName());
    // logger name: ch.zhaw.prog2.io.LogConfiguration

    public LogConfiguration() throws IOException {
        // load base configuration from config file in root resources' directory (class path root)
        InputStream logConfig = this.getClass().getClassLoader().getResourceAsStream("log.properties");
        LogManager.getLogManager().readConfiguration(logConfig);

        // create hierarchical logger for package ("ch.zhaw.prog2.io")
        // an instance has to be created once otherwise it will not be added to the logger hierarchy
        // even if configured in log.properties
        Logger.getLogger(LogConfiguration.class.getPackageName());
        // Logger hierarchy now looks like:
        // "" (root) -> "ch.zhaw.prog2.io" -> "ch.zhaw.prog2.io.LogConfiguration"

        // programmatically add a console handler to the current logger
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter()); // use a default SimpleFormatter
        consoleHandler.setLevel(Level.FINE);                // only handle messages level >= FINE
        logger.addHandler(consoleHandler);                  // add the handler to the specific logger

        // programmatically add a file handler to the current logger
        FileHandler fileHandler = new FileHandler("log_config-%g-%u.log", 1024, 5, true);
        fileHandler.setFormatter(new SimpleFormatter());    // use a default SimpleFormatter
        fileHandler.setLevel(Level.FINER);                  // only handle messages level >= FINER
        logger.addHandler(fileHandler);                     // add the handler to the specific logger
        //logger.setUseParentHandlers(false);               // if set to true (default) logging could happen twice
    }

    public void run() {
        // print logger hierarchy
        System.out.println("Log Hierarchy");
        Logger loggerNode = logger;
        do {
            System.out.println("Logger: " + loggerNode.getName() + " Level: " + loggerNode.getLevel());
            Handler[] handlers = loggerNode.getHandlers();
            for (Handler handler : handlers) {
                System.out.println("- Handler: " + handler + " Level: " + handler.getLevel());
            }
        } while ((loggerNode=loggerNode.getParent()) != null);

        logger.finest(() -> "finest: " + LocalDateTime.now());
        logger.finer(() -> "finer: " + LocalDateTime.now());
        logger.fine(() -> "fine: " + LocalDateTime.now());
        logger.info(() -> "info: " + LocalDateTime.now());
        logger.warning(() -> "warning: " + LocalDateTime.now());
        logger.severe(() -> "severe: " + LocalDateTime.now());
    }


    public static void main(String[] args) throws IOException {
        new LogConfiguration().run();
    }

}
