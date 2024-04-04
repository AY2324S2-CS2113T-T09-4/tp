package constants;

/**
 * ErrorConstant class contains constants for various types of errors that may occur in the application.
 * The constants are used to provide descriptive error messages to the user when errors occur.
 */
public class ErrorConstant {

    // General Errors
    public static final String NEGATIVE_VALUE_ERROR = "Requires a positive integer!";
    public static final String INVALID_INDEX_DELETE_ERROR = "Invalid index to delete!";


    // Storage Errors
    public static final String SAVE_ERROR = "File save failed. Write error occurred:";
    public static final String LOAD_ERROR = "File read error:" + "Error at ";
    public static final String CREATE_FILE_ERROR = "Unable to create file.";
    public static final String CORRUPT_ERROR = "File is corrupted!" +
            System.lineSeparator() + "Deleting 'pulsepilot_data.txt' and 'pulsepilot_hash.txt'. Try running again!" +
            System.lineSeparator() + UiConstant.PARTITION_LINE;
    public static final String DATA_INTEGRITY_ERROR = "Data file integrity compromised. Exiting.";
    public static final String MISSING_INTEGRITY_ERROR = "Key files for integrity missing. Exiting.";
    public static final String HASH_ERROR = "Error occurred while processing file hash.";

    // Storage Error for Gym
    public static final String LOAD_GYM_FORMAT_ERROR = LOAD_ERROR + "Format of gym entry is incorrect/corrupted";
    public static final String LOAD_GYM_TYPE_ERROR = LOAD_ERROR + "Format of gym type is incorrect/corrupted";
    public static final String LOAD_NUMBER_OF_STATION_ERROR = LOAD_ERROR + "Number of stations is corrupted";
    public static final String LOAD_NUMBER_OF_SETS_ERROR = LOAD_ERROR
            + "Number of weights doesn't match number of sets";

    // Input Errors
    public static final String INVALID_COMMAND_ERROR = "Invalid command. Enter 'help' to view available commands.";
    public static final String NO_DATE_SPECIFIED_ERROR = "NA";
    public static final String INVALID_ITEM_ERROR = "Invalid item specified.";
    public static final String CORRECT_FILTER_ITEM_FORMAT = "/item:run/gym/workouts/bmi/period/appointment";

    // Date errors
    public static final String INVALID_DATE_ERROR = "Invalid date format. Format is DD-MM-YYYY in integers.";
    public static final String INVALID_DAY_ERROR = "Day must be an integer between 01 and 31.";
    public static final String INVALID_MONTH_ERROR = "Month must be an integer between 01 and 12.";
    public static final String PARSING_DATE_ERROR ="Error parsing date!";

    // Time errors
    public static final String INVALID_ACTUAL_TIME_ERROR = "Invalid time format. Format is HH:MM in 24 hours format!";
    public static final String INVALID_MINUTES_ERROR = "Minutes must be a positive integer between 01 and 59.";
    public static final String INVALID_HOURS_ERROR = "Hours must be a positive integer between 1 and 23";
    public static final String PARSING_TIME_ERROR = "Error parsing time!";

    //Delete Errors
    public static final String INSUFFICIENT_DELETE_PARAMETERS_ERROR = "Insufficient parameters for delete! " +
            "Example input: /item:item /index:index";
    public static final String INVALID_INDEX_ERROR = "Index must be a valid positive integer.";

    // EXERCISE ERRORS

    // HISTORY ERRORS
    public static final String HISTORY_RUN_EMPTY_ERROR = "No runs found! You need to add a run first!";
    public static final String HISTORY_GYM_EMPTY_ERROR = "No gyms found! You need to add a gym first!";
    public static final String HISTORY_WORKOUTS_EMPTY_ERROR = "No workouts found! You need to add " +
            "either a run or a gym first";
    public static final String HISTORY_APPOINTMENT_EMPTY_ERROR = "No appointments found! You need to add an " +
            "appointment first";
    public static final String HISTORY_BMI_EMPTY_ERROR = "No bmis found! You need to add a bmi first!";
    public static final String HISTORY_PERIOD_EMPTY_ERROR = "No periods found! You need to add a period first!";

    // RUN ERRORS
    public static final String INSUFFICIENT_RUN_PARAMETERS_ERROR = "Insufficient parameters for run! "
            + "Example input: /e:run /d:5.25 /t:25:23 [/date:DATE]";
    public static final String INVALID_RUN_DISTANCE_ERROR = "Distance is a 2 decimal point positive number!";
    public static final String INVALID_RUN_TIME_ERROR = "Invalid time format. Format is either HH:MM:SS or" +
            "MM:SS with integers.";
    public static final String INVALID_SECOND_ERROR = "Seconds must be a positive integer between 00 and 59";
    public static final String INVALID_HOUR_ERROR = "Hours cannot be 0. Use MM:SS instead";
    public static final String INVALID_MINUTE_ERROR = "Minutes must be a positive integer between 01 and 59";

    // GYM ERRORS
    public static final String INSUFFICIENT_GYM_PARAMETERS_ERROR = "Insufficient parameters for gym!"
            + "Example input: /e:gym /n:2 [/date:DATE]";
    public static final String INVALID_NUMBER_OF_STATIONS_ERROR = "Number of stations is a positive number!";
    public static final String EMPTY_EXERCISE_NAME_ERROR = "Exercise name cannot be blank!";

    public static final String INVALID_EXERCISE_NAME_ERROR = "Exercise name can only have letters!";
    public static final String EXERCISE_NAME_LENGTH_ERROR = "Exercise name cannot be more than 40 characters!";
    public static final String INVALID_SETS_ERROR = "Number of sets must be a positive integer!";
    public static final String INVALID_REPS_ERROR = "Number of reps must be a positive integer!";
    public static final String INVALID_WEIGHTS_ERROR = "The weight done for each set is seperated by commas! " +
            "Example: 10,20,30";
    public static final String INVALID_WEIGHTS_ARRAY_FORMAT_ERROR = "Weights can only have integers and commas, with" +
            "no spaces! Example: 10,20,30";
    public static final String EMPTY_WEIGHTS_ARRAY_ERROR = "Weights array cannot be empty";
    public static final String GYM_WEIGHT_POSITIVE_ERROR = "Weights must be positive! e.g. /w:10,20,30";
    public static final String GYM_WEIGHT_DIGIT_ERROR = " Weights must be a number! e.g. /w:5,10,20";
    public static final String GYM_WEIGHTS_INCORRECT_NUMBER_ERROR = " Number of weight values must be the same as" +
            " the number of sets! e.g. bench press /s:2 /r:10 /w:10,20";

    // HEALTH ERRORS
    public static final String INVALID_HEALTH_INPUT_ERROR = "Invalid input for health type! " +
            "Please input either /h:bmi, /h:period, /h:prediction or /h:appointment";

    // BMI ERRORS
    public static final String INSUFFICIENT_BMI_PARAMETERS_ERROR = "Insufficient parameters for bmi! " +
            "Example input: /h:bmi /height:height /weight:weight /date:date";
    public static final String NEGATIVE_BMI_ERROR = "Bmi must be a positive value";
    public static final String NULL_BMI_ERROR = "Bmi object cannot be null.";
    public static final String EMPTY_BMI_LIST_ERROR = "BMI List is empty.";
    public static final String BMI_LIST_UNCLEARED_ERROR = "Bmi list is not cleared.";
    public static final String HEIGHT_WEIGHT_INPUT_ERROR =
            "Height and weight should be 2 decimal place positive numbers!";

    // PERIOD ERRORS
    public static final String INSUFFICIENT_PERIOD_PARAMETERS_ERROR = "Insufficient parameters for period! " +
            "Example input: /h:period /start:startDate /end:endDate";
    public static final String NULL_PERIOD_ERROR = "Period object cannot be null.";
    public static final String NULL_START_DATE_ERROR = "Start date of period cannot be empty.";
    public static final String NULL_END_DATE_ERROR = "End date of period cannot be empty.";
    public static final String INVALID_START_DATE_ERROR = "Invalid start date!";
    public static final String INVALID_END_DATE_ERROR = "Invalid end date!";
    public static final String EMPTY_PERIOD_LIST_ERROR = "Period List is empty.";
    public static final String PERIOD_LIST_UNCLEARED_ERROR = "Period list is not cleared.";
    public static final String DATE_IN_FUTURE_ERROR = "Date specified cannot be later than today's date.";
    public static final String PERIOD_END_BEFORE_START_ERROR = "Start date of period must be before end date.";
    public static final String UNABLE_TO_MAKE_PREDICTIONS_ERROR = "Insufficient period cycles to make prediction.";

    // APPOINTMENT ERRORS
    public static final String INSUFFICIENT_APPOINTMENT_PARAMETERS_ERROR = "Insufficient parameters for period! " +
            "Example input: /h:appointment /date:date /time:time /description:description /place:place";
    public static final String NULL_APPOINTMENT_ERROR = "Appointment object cannot be null.";
    public static final String EMPTY_APPOINTMENT_LIST_ERROR = "Appointment list is empty.";
    public static final String APPOINTMENT_LIST_UNCLEARED_ERROR = "Appointment list is not cleared.";
    public static final String START_INDEX_NEGATIVE_ERROR = "Start index for prediction must be positive";
    public static final String END_INDEX_GREATER_THAN_START_ERROR =
            "End index must be greater than start index is negative";
    public static final String NULL_DATE_ERROR = "Date of appointment cannot be empty.";
    public static final String NULL_TIME_ERROR = "Time of appointment cannot be empty.";
    public static final String DESCRIPTION_LENGTH_ERROR = "Description cannot be more than 100 characters";
    public static final String INVALID_DESCRIPTION_ERROR = "Appointment description can only " +
            "contain alphanumeric characters and spaces!";

    public static final String INVALID_HISTORY_FILTER_ERROR = "Missing/invalid filter used!" +
            System.lineSeparator() +
            "Use /item:run/gym/workouts/period/bmi/appointment";

    public static final String INVALID_LATEST_FILTER_ERROR = "Missing/invalid filter used!" +
            System.lineSeparator() +
            "Use /item:run/gym/period/bmi/appointment";


}
