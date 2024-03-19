package storage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import utility.Constant;

public class LogFile {
    protected static FileHandler logFileHandler = null;
    private static LogFile instance = null;
    private static final Logger logger = Logger.getLogger(LogFile.class.getName());

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private LogFile() {
        initializeLogFile();
    }

    /**
     * Returns the singleton instance of the LogFile class.
     * If the instance is null, it creates a new instance.
     *
     * @return The singleton instance of the LogFile class.
     */
    public static LogFile getInstance() {
        if (instance == null) {
            instance = new LogFile();
        }
        return instance;
    }

    /**
     * Initialises the log file to be used. Creates the log file if needed, then sets formatters.
     * Parent handlers are set to false to prevent printing of logs to terminal.
     */
    public static void initializeLogFile() {
        try {
            if (logFileHandler == null) {
                logFileHandler = new FileHandler(Constant.LOG_FILE_PATH);
                logFileHandler.setFormatter(new SimpleFormatter());
                logger.addHandler(logFileHandler);
                logger.setUseParentHandlers(false);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error setting up log file", e);
        }
        assert(logFileHandler != null);
    }

    /**
     * Writes commands used and any errors to log file.
     *
     * @param input   String representing the user's input.
     * @param isError Boolean variable to determine if log is an error.
     */
    public static void writeLog(String input, boolean isError) {
        if (isError) {
            logger.log(Level.WARNING, "Error: " + input);
        } else {
            logger.log(Level.INFO, input);
        }
    }
}