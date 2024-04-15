package constants;

/**
 * WorkoutConstants class contains constants related to workout-related functionalities in the application.
 * It includes constants for workout types, flags, parameters, indices, file loading, history display,
 * and formatted strings.
 */
public class WorkoutConstant {
    public static final String NUMBER_OF_STATIONS_FLAG = "/n:";
    public static final String EXERCISE_FLAG = "/e:";
    public static final String DISTANCE_FLAG = "/d:";
    public static final String RUN_TIME_FLAG = "/t:";
    public static final String DATE_FLAG = "/date:";
    public static final String SETS_FLAG = "/s:";
    public static final String REPS_FLAG = "/r:";
    public static final String WEIGHTS_FLAG = "/w:";
    public static final String COLON = ":";
    public static final int NUMBER_OF_RUN_PARAMETERS = 3;
    public static final int NUMBER_OF_GYM_PARAMETERS = 2;
    public static final int NUMBER_OF_GYM_STATION_PARAMETERS = 4;
    public static final int NUMBER_OF_PARTS_FOR_RUN_TIME = 2;
    public static final int NUMBER_OF_PARTS_FOR_RUN_TIME_WITH_HOURS = 3;
    public static final int MAX_GYM_STATION_NAME_LENGTH = 25;
    public static final double MAX_RUN_DISTANCE = 5000.00;
    public static final double MIN_RUN_DISTANCE = 0;
    public static final double MAX_PACE = 30;
    public static final double MIN_PACE = 1;
    public static final double MAX_GYM_WEIGHT = 2850.000;
    public static final double WEIGHT_MULTIPLE = 0.125;
    public static final int MAX_GYM_STATION_NUMBER = 50;
    public static final int RUN_TIME_HOUR_INDEX = 0;
    public static final int RUN_TIME_MINUTE_INDEX = 1;
    public static final int RUN_TIME_SECOND_INDEX = 2;
    public static final int RUN_TIME_NO_HOURS_MINUTE_INDEX = 0;
    public static final int RUN_TIME_NO_HOURS_SECOND_INDEX = 1;
    public static final Integer STATION_NAME_INDEX = 0;
    public static final int NO_HOURS_PRESENT = -1;

    public static final int GYM_NUMBER_OF_STATIONS_INDEX = 0;
    public static final int GYM_DATE_INDEX = 1;
    public static final int GYM_STATION_NAME_INDEX = 0;
    public static final int GYM_STATION_SET_INDEX = 1;
    public static final int GYM_STATION_REPS_INDEX = 2;
    public static final int GYM_STATION_WEIGHTS_INDEX = 3;

    public static final int RUN_TIME_INDEX = 0;
    public static final int RUN_DISTANCE_INDEX = 1;
    public static final int RUN_DATE_INDEX = 2;
    public static final String BACK = "back";
    public static final String RUN = "run";
    public static final String GYM = "gym";
    public static final String ALL = "workouts";
    public static final String TWO_DECIMAL_PLACE_FORMAT = "%.2f";
    public static final String TWO_DIGIT_PLACE_FORMAT = "%02d";
    public static final int GYM_FILE_INDEX = 0;
    public static final int NUM_OF_STATIONS_FILE_INDEX = 1;
    public static final int DATE_FILE_INDEX = 2;

    public static final int GYM_FILE_BASE_COUNTER = 3;
    public static final int SETS_OFFSET = 1;
    public static final int REPS_OFFSET = 2;
    public static final int WEIGHTS_OFFSET = 3;
    public static final int INCREMENT_OFFSET = 4;
    public static final String HISTORY_WORKOUTS_HEADER = "Showing all workouts (runs and gyms):";
    public static final String HISTORY_WORKOUTS_DATA_FORMAT = "%-5s\t%-12s\t%-25s\t%-20s\t%-8s";
    public static final String HISTORY_WORKOUTS_HEADER_FORMAT = String.format(
            "%-6s\t%-5s\t%-12s\t%-25s\t%-20s\t%-8s", "Index",
            "Type", "Date", "[Distance (km) / Station]", "[Duration / Sets]", "Pace (min/km)");
    public static final String HISTORY_WORKOUTS_DATA_HEADER_FORMAT = "%-6s\t%s";
    public static final String RUN_DATA_FORMAT = "%-6s\t%-10s\t%-10s\t%-10s\t%-12s";
    public static final String RUN_DATA_INDEX_FORMAT = "%-6d\t%-6s";
    public static final String RUN_HEADER_INDEX_FORMAT = String.format("%-6s\t%-6s\t%-10s\t%-10s\t%-10s\t%-12s",
            "Index", "Type", "Time", "Distance", "Pace", "Date");
    public static final String RUN_DELETE_MESSAGE_FORMAT = "Removed Run entry with %s km at %s.";
    public static final String GYM_STATION_FORMAT = "%s: ";
    public static final String GYM_SET_FORMAT = "%d reps at %.3f KG";
    public static final String GYM_SET_INDEX_FORMAT = "\t- Set %d. %s";
    public static final String GYM_DELETE_MESSAGE_FORMAT = "Removed Gym entry with %d station(s).";

    public static final String INDIVIDUAL_GYM_STATION_FORMAT = "%d sets";
    public static final String RUN_HEADER = "Type\tTime\t\tDistance\tPace\t\tDate";
    public static final String ADD_RUN = "Successfully added a new run session";
    public static final String ADD_GYM = "Successfully added a new gym session";
    public static final String STATION_GYM_FORMAT = "e.g. Bench Press /s:2 /r:4 " +
            "/w:10,20";

    public static final String TIME_WITH_HOURS_FORMAT = TWO_DIGIT_PLACE_FORMAT
            + COLON + TWO_DIGIT_PLACE_FORMAT
            + COLON + TWO_DIGIT_PLACE_FORMAT;
    public static final String TIME_WITHOUT_HOURS_FORMAT = TWO_DIGIT_PLACE_FORMAT
            + COLON + TWO_DIGIT_PLACE_FORMAT;

    public static final String RUN_PACE_FORMAT = "%d:%02d/km";

    public static final int NUM_OF_SLASHES_FOR_GYM_WITH_DATE = 3;
    public static final int NUM_OF_SLASHES_FOR_GYM_WITHOUT_DATE = 2;
    public static final int NUM_OF_SLASHES_FOR_GYM_STATION = 3;

    public static final int NUM_OF_SLASHES_FOR_RUN_WITH_DATE = 4;
    public static final int NUM_OF_SLASHES_FOR_RUN_WITHOUT_DATE = 3;

}
