package ch.zhaw.prog2.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.logging.*;

public class UniverseLoggingDemo {
    private static final Logger logger = Logger.getLogger("UniverseLogger"); // Obtain a suitable Logger instance

    public static void main(String[] args) throws IOException {
        InputStream logConfig = UniverseLoggingDemo.class.getClassLoader().getResourceAsStream("log.properties");
        LogManager.getLogManager().readConfiguration(logConfig);

        logger.setLevel(Level.ALL);             // accept log records with all log levels
        logger.finer("Starting up …");     // debugging message using finer level convenience method*)
        try {
            logger.fine("Try to compute size of universe");  // debugging message using the fine level method
            Number size = sizeOfUniverse();
            // info level status message using placeholder and parameters to build the record
            logger.log(Level.INFO, "Success! Found {0} elements.", size); // content of size is inserted at {0}
        } catch (Exception ex) {
            // error message submitting the exception to output in log handler
            logger.log(Level.WARNING, "Failed to compute size of universe", ex);
        }
        logger.finer("Shutting down…");             // another debugging message using the finer level method*)
    }

    private static final Random random = new Random();

    private static Number sizeOfUniverse() throws Exception {
        long size = random.nextLong();
        if (size < 1000) {
            throw new Exception("Universe is to small: " + size);
        }
        return size;
    }
}
