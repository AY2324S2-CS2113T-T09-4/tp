package constants;

import java.io.File;

/**
 * UiConstants class contains constants related to user-interaction-related functionalities in the application.
 * It includes constants for special characters, regular expressions, UI replies, storage paths,
 * numerical values, history management, delete operations, and split indices.
 */
public class UiConstant {
    public static final String SPLIT_BY_SLASH = "/";
    public static final String SPLIT_BY_COLON = ":";
    public static final String SPLIT_BY_WHITESPACE = " ";
    public static final String SPLIT_BY_COMMAS = ",";
    public static final String DASH = "-";
    public static final String LINE = " | ";
    public static final String PARTITION_LINE = "____________________________________________________________";
    public static final String EMPTY_STRING = "";
    public static final String FULL_STOP = ".";
    public static final String VALID_DATE_REGEX = "^\\d{2}-\\d{2}-\\d{4}$";
    public static final String VALID_TWO_DP_NUMBER_REGEX = "^\\d+\\.\\d{2}$";
    public static final String VALID_TIME_REGEX = "^\\d{2}:\\d{2}$";
    public static final String VALID_TIME_WITH_HOURS_REGEX = "^\\d{2}:\\d{2}:\\d{2}$";
    public static final String VALID_POSITIVE_INTEGER_REGEX = "^[1-9]\\d*$";
    public static final String VALID_APPOINTMENT_DESCRIPTION_REGEX = "^[0-9a-zA-Z\\s'\"]+$";
    public static final String VALID_GYM_STATION_NAME_REGEX = "^[A-Za-z\\s]+$";
    public static final String VALID_USERNAME_REGEX = "^[0-9A-Za-z\\s]+$";
    public static final String VALID_WEIGHTS_ARRAY_REGEX = "^\\d+(\\.\\d{1,3})?(,\\d+(\\.\\d{1,3})?)*$";
    public static final String EXIT_MESSAGE = "Initiating PulsePilot landing sequence...";
    public static final int DATA_TYPE_INDEX = 0;
    public static final int NAME_INDEX = 1;
    public static final String NAME_LABEL = "NAME";
    public static final String LOG_FILE_PATH = "./pulsepilot_log.txt";
    public static String dataFilePath = "./pulsepilot_data.txt";
    public static String hashFilePath = "./pulsepilot_hash.txt";
    public static File saveFile = new File(UiConstant.dataFilePath);
    public static final int FILE_FOUND = 0;
    public static final int FILE_NOT_FOUND = 1;
    public static final String FILE_FOUND_MESSAGE = "Welcome back, Captain ";
    public static final String FILE_MISSING_MESSAGE = "What is your name, voyager?";
    public static final String SUCCESSFUL_LOAD = "Prior data found. Orbit has been synchronised.";
    public static final String ITEM_FLAG = "/item:";
    public static final String INDEX_FLAG = "/index:";
    public static final int NUM_SECONDS_IN_MINUTE = 60;
    public static final int NUM_SECONDS_IN_HOUR = 3600;
    public static final int MIN_MINUTES = 0;
    public static final int MAX_MINUTES = 59;
    public static final int MAX_SECONDS = 59;
    public static final int MIN_HOURS = 0;
    public static final int MAX_HOURS = 23;
    public static final double POWER_OF_TWO = 2.0;
    public static final double ROUNDING_FACTOR = 100.0;
    public static final int NUM_DELETE_PARAMETERS = 2;
    public static final int MINIMUM_PERIOD_COUNT = 1;
    public static final int MIN_SECONDS = 0;
    public static final int NUM_OF_SLASHES_FOR_DELETE = 2;
    public static final int NUM_OF_SLASHES_FOR_LATEST_AND_HISTORY = 1;
    public static final int DELETE_ITEM_STRING_INDEX = 0;
    public static final int DELETE_ITEM_NUMBER_INDEX = 1;
    public static final int SPLIT_TIME_HOUR_INDEX = 0;
    public static final int SPLIT_TIME_MINUTES_INDEX = 1;

}
