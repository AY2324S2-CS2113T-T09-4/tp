package constants;

/**
 * ErrorConstant class contains constants for various types of errors that may occur in the application.
 * The constants are used to provide descriptive error messages to the user when errors occur.
 */
public class ErrorConstant {
    public static final String COLOR_HEADING = "\u001b[31m";

    public static final String COLOR_ENDING = "\u001b[0m";
    public static final String INVALID_INPUT_HEADER = "Invalid Input Exception: ";

    public static final String INSUFFICIENT_INPUT_HEADER = "Insufficient Input Exception: ";

    public static final String OUT_OF_BOUND_HEADER = "Out of Bounds Error: ";
    public static final String NEGATIVE_VALUE_ERROR = "Requires a positive integer!";
    public static final String INVALID_INDEX_DELETE_ERROR = "Invalid index to delete!";
    public static final String INVALID_INDEX_SEARCH_ERROR = "Given index is invalid.";

    public static final String INVALID_INDEX_ERROR = "Index must be a valid positive integer.";
    public static final String SAVE_ERROR = "File save failed. Write error occurred:";
    public static final String LOAD_ERROR = "File read error:" + "Error at ";
    public static final String CREATE_FILE_ERROR = "Unable to create file.";
    public static final String CORRUPT_ERROR = "File is corrupted!" +
            System.lineSeparator() + "Deleting 'pulsepilot_data.txt' and 'pulsepilot_hash.txt'. Try running again!" +
            System.lineSeparator() + UiConstant.PARTITION_LINE;
    public static final String DATA_INTEGRITY_ERROR = "Data file integrity compromised. Exiting.";
    public static final String MISSING_INTEGRITY_ERROR = "Key files for integrity missing. Exiting.";
    public static final String HASH_ERROR = "Error occurred while processing file hash.";
    public static final String LOAD_GYM_FORMAT_ERROR = LOAD_ERROR + "Format of gym entry is incorrect/corrupted";
    public static final String LOAD_GYM_TYPE_ERROR = LOAD_ERROR + "Format of gym type is incorrect/corrupted";
    public static final String LOAD_NUMBER_OF_STATION_ERROR = LOAD_ERROR + "Number of stations is corrupted";
    public static final String INVALID_COMMAND_ERROR = "Invalid command. Enter 'help' to view " +
            "available commands.";
    public static final String NO_DATE_SPECIFIED_ERROR = "NA";
    public static final String INVALID_DATE_ERROR = "Invalid date format. Format is DD-MM-YYYY in integers. " +
            "Make sure a valid date is entered (take note of leap years)!";
    public static final String INVALID_YEAR_ERROR = "Year has to be after 1967!";
    public static final String INVALID_LEAP_YEAR_ERROR = "29 Feb does not exist in this year!";
    public static final String PARSING_DATE_ERROR ="Error parsing date!";
    public static final String INVALID_ACTUAL_TIME_ERROR = "Invalid time format. Format is HH:MM in 24 hours format!";
    public static final String INVALID_ACTUAL_TIME_MINUTE_ERROR = "Minutes must be a positive integer " +
            " 00 and 59.";
    public static final String INVALID_ACTUAL_TIME_HOUR_ERROR = "Hours must be a positive integer between 00 and 23";
    public static final String PARSING_TIME_ERROR = "Error parsing time!";
    public static final String INSUFFICIENT_DELETE_PARAMETERS_ERROR = "Insufficient parameters for delete! " +
            "Example input: /item:item /index:index"
            + System.lineSeparator()
            + "Only input what is required! Additional characters between flags will cause errors.";
    public static final String RUN_EMPTY_ERROR = "No runs found! You need to add a run entry first!";
    public static final String GYM_EMPTY_ERROR = "No gyms found! You need to add a gym entry first!";
    public static final String WORKOUTS_EMPTY_ERROR = "No workouts found! You need to add " +
            "either a run or a gym entry first!";
    public static final String APPOINTMENT_EMPTY_ERROR = "No appointments found! You need to add an " +
            "appointment first!";
    public static final String BMI_EMPTY_ERROR = "No BMI entries found! You need to add a BMI entry first!";
    public static final String PERIOD_EMPTY_ERROR = "No periods found! You need to add a period entry first!";
    public static final String INSUFFICIENT_RUN_PARAMETERS_ERROR = "Insufficient parameters for run! "
            + "Example input: /e:run /d:5.25 /t:25:23 [/date:DATE]"
            + System.lineSeparator()
            + "Only input what is required! Additional characters between flags will cause errors.";
    public static final String INVALID_RUN_DISTANCE_ERROR = "Distance is a 2 decimal point positive number!";
    public static final String INVALID_RUN_TIME_ERROR = "Invalid time format. Format is either HH:MM:SS or " +
            "MM:SS with integers!";
    public static final String INVALID_SECOND_ERROR = "Seconds must be a positive integer between 00 and 59!";
    public static final String INVALID_HOUR_ERROR = "Hours is excluded if set to 00. Use MM:SS instead!";
    public static final String INVALID_MINUTE_ERROR = "Minutes must be a positive integer between 01 and 59!";
    public static final String INVALID_GYM_STATION_FORMAT_ERROR = "Remember that you are now adding gym station input!"
            + System.lineSeparator()
            + "Expected format: [Station Name] /s:[SETS] /r:[REPS] /w:[WEIGHTS]";
    public static final String INSUFFICIENT_GYM_PARAMETERS_ERROR = "Insufficient parameters for gym! "
            + "Example input: /e:gym /n:2 [/date:DATE]"
            + System.lineSeparator()
            + "Only input what is required! Additional characters between flags will cause errors.";
    public static final String INVALID_NUMBER_OF_STATIONS_ERROR = "Number of stations is a positive number!"
            + System.lineSeparator()
            + "For instance, '/n:a' is invalid as it is not a number"
            + INVALID_GYM_STATION_FORMAT_ERROR;
    public static final String INVALID_GYM_STATION_EMPTY_NAME_ERROR = "Gym station name cannot be blank!" +
            System.lineSeparator() +
            "Please input an station name" +
            System.lineSeparator() +
            INVALID_GYM_STATION_FORMAT_ERROR;

    public static final String INVALID_GYM_STATION_NAME_ERROR = "Gym station name can only have letters and cannot " +
            "be more than 25 characters!" +
            System.lineSeparator() +
            "Please input a shorter name." +
            System.lineSeparator() +
            INVALID_GYM_STATION_FORMAT_ERROR;

    public static final String INVALID_SETS_POSITIVE_DIGIT_ERROR = "Number of sets must be a positive integer!"
            + System.lineSeparator()
            + INVALID_GYM_STATION_FORMAT_ERROR;
    public static final String INVALID_REPS_POSITIVE_DIGIT_ERROR = "Number of reps must be a positive integer!"
            + System.lineSeparator()
            + INVALID_GYM_STATION_FORMAT_ERROR;
    public static final String INVALID_WEIGHTS_VALUE_ERROR = "The weight done for each set must "
            + "be a multiple of 0.125." 
            + System.lineSeparator() 
            + "This is because the smallest weight increment in most gyms is 0.125kg."
            + System.lineSeparator() 
            + INVALID_GYM_STATION_FORMAT_ERROR;
    public static final String MAX_STATIONS_ERROR = "Number of stations done cannot be more than 50!";

    public static final String INVALID_WEIGHTS_ARRAY_FORMAT_ERROR = "Weights array format is incorrect!"
            + System.lineSeparator()
            + "Weights must be separated by commas (with no whitespaces) " +
            "and be a positive decimal (up to 3 decimal places)"
            + System.lineSeparator()
            + INVALID_GYM_STATION_FORMAT_ERROR;
    public static final String INVALID_WEIGHTS_EMPTY_ERROR = "Weights array cannot be empty"
            + System.lineSeparator()
            + INVALID_GYM_STATION_FORMAT_ERROR;

         
    public static final String INVALID_WEIGHTS_NUMBER_ERROR = "Number of weight values must be the same as"
            + " the number of sets!"
            + System.lineSeparator()
            + "Please check the number of sets (/s:[value]) and the number of weight values (/w:value1,value2,...)"
            + System.lineSeparator()
            + INVALID_GYM_STATION_FORMAT_ERROR;
    public static final String INVALID_HEALTH_INPUT_ERROR = "Invalid input for health type! " +
            "Please input either /h:bmi, /h:period, /h:prediction or /h:appointment";
    public static final String INSUFFICIENT_BMI_PARAMETERS_ERROR = "Insufficient parameters for bmi! " +
            "Example input: /h:bmi /height:height /weight:weight /date:date"
            + System.lineSeparator()
            + "Only input what is required! Additional characters between flags will cause errors.";
    public static final String NEGATIVE_BMI_ERROR = "Bmi must be a positive value";
    public static final String NULL_BMI_ERROR = "Bmi object cannot be null.";
    public static final String EMPTY_BMI_LIST_ERROR = "BMI List is empty.";
    public static final String BMI_LIST_UNCLEARED_ERROR = "Bmi list is not cleared.";
    public static final String INVALID_HEIGHT_WEIGHT_INPUT_ERROR =
            "Height and weight should be 2 decimal place positive numbers!";
    public static final String DATE_ALREADY_EXISTS_ERROR = "A Bmi input with the same date already exists.";
    public static final String INSUFFICIENT_PERIOD_PARAMETERS_ERROR = "Insufficient parameters for period! "
            + System.lineSeparator()
            + "Example inputs: '/h:period /start:startDate' or '/h:period /start:startDate /end:endDate'"
            + System.lineSeparator()
            + "Only input what is required! Additional characters between flags will cause errors.";
    public static final String END_DATE_NOT_FOUND_ERROR = "Start date already exists! " +
            "Please add an end date to the latest period input in order to input a new period.";
    public static final String NULL_PERIOD_ERROR = "Period object cannot be null.";
    public static final String NULL_START_DATE_ERROR = "Start date of period cannot be empty.";
    public static final String INVALID_START_DATE_ERROR = "Invalid start date!";
    public static final String INVALID_END_DATE_ERROR = "Invalid end date!";
    public static final String EMPTY_PERIOD_LIST_ERROR = "Period List is empty.";
    public static final String PERIOD_LIST_UNCLEARED_ERROR = "Period list is not cleared.";
    public static final String DATE_IN_FUTURE_ERROR = "Date specified cannot be later than today's date.";
    public static final String PERIOD_END_BEFORE_START_ERROR = "Start date of period must be before end date.";
    public static final String UNABLE_TO_MAKE_PREDICTIONS_ERROR = "Insufficient period cycles to make prediction."
            + System.lineSeparator()
            + "Enter at least four period inputs for prediction of the next period's start date.";
    public static final String CURRENT_START_BEFORE_PREVIOUS_END =
            "The start date of your current period input needs to be after the end date of your previous period input."
                    + System.lineSeparator()
                    + "You may enter 'history /item:period' to check your period history.";
    public static final String INVALID_START_DATE_INPUT_ERROR = "Error is resulted by two possible reasons. "
            +System.lineSeparator()
            + "1. End date for previous period is still empty. " +
            "Add an end date before starting a new period input!"
            + System.lineSeparator()
            + "2. If you're adding an end date to the latest period input, the start dates do not match! " +
            "Enter 'history /item:period' to view existing period inputs.";
    public static final String LENGTH_MUST_BE_POSITIVE_ERROR = "Length cannot be less than 1 day.";
    public static final String INSUFFICIENT_APPOINTMENT_PARAMETERS_ERROR = "Insufficient parameters for appointment! " +
            "Example input: /h:appointment /date:date /time:time /description:description /place:place"
            + System.lineSeparator()
            + "Only input what is required! Additional characters between flags will cause errors.";
    public static final String NULL_APPOINTMENT_ERROR = "Appointment object cannot be null.";
    public static final String EMPTY_APPOINTMENT_LIST_ERROR = "Appointment list is empty.";
    public static final String APPOINTMENT_LIST_UNCLEARED_ERROR = "Appointment list is not cleared.";
    public static final String START_INDEX_NEGATIVE_ERROR = "Start index for prediction must be positive";
    public static final String END_INDEX_SMALLER_THAN_START_ERROR =
            "End index must be smaller than start index";
    public static final String NULL_DATE_ERROR = "Date of appointment cannot be empty.";
    public static final String NULL_TIME_ERROR = "Time of appointment cannot be empty.";
    public static final String DESCRIPTION_LENGTH_ERROR = "Description cannot be more than 100 characters";
    public static final String INVALID_DESCRIPTION_ERROR = "Appointment description can only " +
            "contain alphanumeric characters, spaces, inverted commas and quotes!";

    public static final String INSUFFICIENT_HISTORY_FILTER_ERROR = "Filter is missing!"
            + System.lineSeparator()
            + "Please use the 'latest' command followed by the '/item:' flag and one of the following options:"
            + System.lineSeparator()
            + "- run"
            + System.lineSeparator()
            + "- gym"
            + System.lineSeparator()
            + "- workouts"
            + System.lineSeparator()
            + "- period"
            + System.lineSeparator()
            + "- bmi"
            + System.lineSeparator()
            + "- appointment";

    public static final String INSUFFICIENT_LATEST_FILTER_ERROR = "Filter is missing!"
            + System.lineSeparator()
            + "Please use the 'latest' command followed by the '/item:' flag and one of the following options:"
            + System.lineSeparator()
            + "- run"
            + System.lineSeparator()
            + "- gym"
            + System.lineSeparator()
            + "- period"
            + System.lineSeparator()
            + "- bmi"
            + System.lineSeparator()
            + "- appointment";

    public static final String INVALID_HISTORY_FILTER_ERROR = "Filter is invalid!"
            + System.lineSeparator()
            + "Please only use the following flags"
            + System.lineSeparator()
            + "- run"
            + System.lineSeparator()
            + "- gym"
            + System.lineSeparator()
            + "- workouts"
            + System.lineSeparator()
            + "- period"
            + System.lineSeparator()
            + "- bmi"
            + System.lineSeparator()
            + "- appointment";

    public static final String INVALID_LATEST_OR_DELETE_FILTER = "Filter is invalid!"
            + System.lineSeparator()
            + "Please only use the following flags"
            + System.lineSeparator()
            + "- run"
            + System.lineSeparator()
            + "- gym"
            + System.lineSeparator()
            + "- period"
            + System.lineSeparator()
            + "- bmi"
            + System.lineSeparator()
            + "- appointment";

    public static final String TOO_MANY_SLASHES_ERROR = "Too many '/' characters specified within input. " +
            "Parameters cannot contain any '/' characters!";

    public static final String DISTANCE_TOO_LONG_ERROR = "The world's longest foot race is the Self-Transcendence "
            + "3100 Mile Race."
            + System.lineSeparator()
            + "Please enter a more realistic distance less than 5000km!";

    public static final String ZERO_HEIGHT_AND_WEIGHT_ERROR = "Height and weight must be more than 0.";
    public static final String MAX_HEIGHT_ERROR = "The tallest man, Robert Wadlow was 2.72m."
            + System.lineSeparator()
            + "Please enter a more realistic height less than 2.75m!";
    public static final String MAX_WEIGHT_ERROR = "The heaviest human being, Jon Brower Minnnoch weighed in at 635kg."
            + System.lineSeparator()
            + "Please enter a more realistic weight less than 640kg!";

    public static final String INVALID_WEIGHT_MAX_ERROR = "The heaviest object ever lifted by a human (Paul Anderson) "
            + "was 2840kg."
            + System.lineSeparator()
            + "Please enter a more realistic gym weight less than 2850kg!";
    public static final String ZERO_DISTANCE_ERROR = "Distance run cannot be 0!";
    public static final String ZERO_TIME_ERROR = "Time cannot be set to 00:00!";
    public static final String MAX_PACE_ERROR = "The calculated pace is too slow!"
            + System.lineSeparator()
            + "Pace calculated cannot be slower than 30:00/km!";
    public static final String MIN_PACE_ERROR = "The calculated pace is too fast!"
            + System.lineSeparator()
            + "Pace calculated cannot be faster than 1:00/km!";
    public static final String INVALID_USERNAME_ERROR = "\u001b[31mUsername can only contain alphanumeric characters " +
            "and spaces!\u001b[0m";
    public static final String NO_PERMISSIONS_ERROR = "Cannot read or write to current directory. Exiting.";
    public static final String INVALID_WORKOUT_TYPE_ERROR = "Invalid workout type! Please input" +
            " either /e:run or /e:gym!";
    public static final String FILE_READ_HEADER = "File Read Error: ";
    public static final String FILE_WRITE_HEADER = "File Write Error: ";
    public static final String FILE_CREATE_HEADER = "File Create Error: ";
}
