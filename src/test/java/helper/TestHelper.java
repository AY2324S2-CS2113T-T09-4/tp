package helper;

import constants.ErrorConstant;
import constants.HealthConstant;
import constants.UiConstant;

/**
 * Helper class to help with testing.
 * Contains methods to help create the output string for testing.
 * Methods that starts with add are used when objects of that type is created.
 * Methods that starts with latest are used when the latest object is printed.
 * Methods that starts with error are used when an error is thrown.
 */
public class TestHelper {
    //@@author JustinSoh
    /**
     * Helper method to help create the output string when BMI is added.
     *
     * @param weight weight of the user.
     * @param height height of the user.
     * @param date date of the user in the format yyyy-mm-dd.
     * @param bmiValue bmi value of the user in 2 decimal places.
     * @param bmiCategory the category (use the constants in HealthConstant).
     * @return add BMI output string.
     */
    public static String addBmiOutputString (String weight,
                                             String height,
                                             String date,
                                             double bmiValue,
                                             String bmiCategory){

        return UiConstant.PARTITION_LINE +
                System.lineSeparator() +
                HealthConstant.BMI_ADDED_MESSAGE_PREFIX +
                height +
                UiConstant.LINE +
                weight +
                UiConstant.LINE +
                date +
                System.lineSeparator() +
                String.format(HealthConstant.PRINT_BMI_FORMAT,
                        date, bmiValue, bmiCategory) +
                System.lineSeparator() +
                UiConstant.PARTITION_LINE +
                System.lineSeparator();
    }

    /**
     * Helper method to help create the latestString for Period.
     *
     * @param date date of the bmi in the format yyyy-mm-dd.
     * @param bmiValue bmi value of the user in 2 decimal places.
     * @param bmiMessage the category (use the constants in HealthConstant).
     * @return latest BMI output string.
     */
    public static String latestBmiOutputString(String date, double bmiValue, String bmiMessage ) {
        return UiConstant.PARTITION_LINE +
                System.lineSeparator() +
                date +
                System.lineSeparator() +
                "Your BMI is " +
                String.format("%.2f" , bmiValue) +
                System.lineSeparator() +
                bmiMessage +
                System.lineSeparator() +
                UiConstant.PARTITION_LINE +
                System.lineSeparator();
    }

    /**
     * Helper method to help create the output string when Period is added
     *
     * @param startDate    start date of the period in the format yyyy-mm-dd.
     * @param endDate      end date of the period in the format yyyy-mm-dd.
     * @param periodLength length of the period in days.
     * @return add Period output string.
     */
    public static String addPeriodOutputString (String startDate,
                                          String endDate,
                                          int periodLength){

        StringBuilder outputString = new StringBuilder();
        outputString.append(UiConstant.PARTITION_LINE);
        outputString.append(System.lineSeparator());
        outputString.append(HealthConstant.PERIOD_ADDED_MESSAGE_PREFIX);
        outputString.append(startDate);
        outputString.append(UiConstant.LINE);
        outputString.append(endDate);
        outputString.append(System.lineSeparator());
        outputString.append(String.format(HealthConstant.PRINT_PERIOD_FORMAT,
                startDate,
                endDate ,
                periodLength,
                HealthConstant.DAYS_MESSAGE));
        outputString.append(System.lineSeparator());
        if (!HealthConstant.PERIOD_TOO_LONG_MESSAGE.isBlank()) {
            outputString.append(ErrorConstant.COLOR_HEADING +
                    HealthConstant.PERIOD_TOO_LONG_MESSAGE +
                    ErrorConstant.COLOR_ENDING);
            outputString.append(System.lineSeparator());
        }
        outputString.append(UiConstant.PARTITION_LINE);
        outputString.append(System.lineSeparator());


        return outputString.toString();
    }

    /**
     * Helper method to help create the latestString for Period.
     *
     * @param startDate start date of the period in the format yyyy-mm-dd.
     * @param endDate end date of the period in the format yyyy-mm-dd.
     * @param periodLength length of the period in days.
     * @param period the "days" message (use the constants in HealthConstant).
     * @return latest Period Output string.
     */
    public static String latestPeriodOutputString(String startDate, String endDate, int periodLength, String period) {
        return UiConstant.PARTITION_LINE +
                System.lineSeparator() +
                String.format(HealthConstant.PRINT_PERIOD_FORMAT,
                        startDate,
                        endDate,
                        periodLength,
                        period) +
                System.lineSeparator() +
                UiConstant.PARTITION_LINE +
                System.lineSeparator();

    }

    /**
     * Helper method to help create the output string when Appointment is added.
     *
     * @param date date of the appointment in the format yyyy-mm-dd.
     * @param time time of the appointment in the format hh:mm.
     * @param description description of the appointment.
     * @return add Appointment output string.
     */
    public static String addAppointmentString(String date, String time, String description) {
        return UiConstant.PARTITION_LINE +
                System.lineSeparator() +
                HealthConstant.APPOINTMENT_ADDED_MESSAGE_PREFIX +
                date +
                UiConstant.LINE +
                time +
                UiConstant.LINE +
                description +
                System.lineSeparator() +
                String.format(HealthConstant.PRINT_APPOINTMENT_FORMAT, date, time, description) +
                System.lineSeparator() +
                UiConstant.PARTITION_LINE +
                System.lineSeparator();
    }

    /**
     * Helper method to help create the latestString for Appointment.
     *
     * @param date date of the appointment in the format yyyy-mm-dd.
     * @param time time of the appointment in the format hh:mm.
     * @param description description of the appointment.
     * @return latest Appointment output string.
     */
    public static String latestAppointmentOutputString(String date, String time, String description) {
        return UiConstant.PARTITION_LINE +
                System.lineSeparator() +
                String.format(HealthConstant.PRINT_APPOINTMENT_FORMAT, date, time, description) +
                System.lineSeparator() +
                UiConstant.PARTITION_LINE +
                System.lineSeparator();
    }

    /**
     * Helper method for CustomException.InvalidInput() to help create the output string when an invalid input is given.
     *
     * @param errorString the error message to be printed.
     * @return Error InvalidInput Output String.
     */
    public static String errorInvalidInputString(String errorString) {
        return ErrorConstant.COLOR_HEADING +
                "Exception Caught!" +
                System.lineSeparator() +
                ErrorConstant.COLOR_HEADING +
                ErrorConstant.INVALID_INPUT_HEADER +
                errorString +
                ErrorConstant.COLOR_ENDING +
                ErrorConstant.COLOR_ENDING +
                System.lineSeparator();
    }

    /**
     * Helper method for CustomException.OutOfBounds().
     * Used to get the out-of-bounds exception string for invalid index.
     *
     * @param errorString the error message to be printed.
     * @return Error OutOfBounds Output String.
     */
    public static String errorOutOfBoundsString(String errorString) {
        return ErrorConstant.COLOR_HEADING +
                "Exception Caught!" +
                System.lineSeparator() +
                ErrorConstant.COLOR_HEADING +
                ErrorConstant.OUT_OF_BOUND_HEADER +
                errorString +
                ErrorConstant.COLOR_ENDING +
                ErrorConstant.COLOR_ENDING +
                System.lineSeparator();
    }

    /**
     * Helper method for Invalid Command String.
     * Used for methods that prints an error rather than use Output.printException().
     *
     * @param errorString to be printed.
     * @return Error Invalid Command Output String.
     */
    public static String errorInvalidCommandString(String errorString) {
        return ErrorConstant.COLOR_HEADING +
                "Exception Caught!" +
                System.lineSeparator() +
                errorString +
                ErrorConstant.COLOR_ENDING +
                System.lineSeparator();
    }


    /**
     * Helper method for CustomException.InsufficientInput().
     * Used to get the insufficient input exception string for insufficient input.
     *
     * @param errorString the error message to be printed.
     * @return Error InsufficientInput Output String.
     */
    public static String errorInsufficientInput(String errorString){
        return ErrorConstant.COLOR_HEADING +
                "Exception Caught!" +
                System.lineSeparator() +
                ErrorConstant.COLOR_HEADING +
                ErrorConstant.INSUFFICIENT_INPUT_HEADER +
                errorString +
                ErrorConstant.COLOR_ENDING +
                ErrorConstant.COLOR_ENDING +
                System.lineSeparator();
    }

    /**
     * Helper method to help create the greeting string when file is found.
     *
     * @param name name of the person.
     * @return greeting string with name.
     */
    public static String printGreetingsFoundString(String name){
        return UiConstant.FILE_FOUND_MESSAGE +
                name +
                System.lineSeparator() +
                UiConstant.SUCCESSFUL_LOAD +
                System.lineSeparator() +
                UiConstant.PARTITION_LINE +
                System.lineSeparator();
    }

    /**
     * Helper method to help create the greeting string when file is not found.
     *
     * @return greeting string when file is not found.
     */
    public static String printGreetingNotFoundString(){
        return UiConstant.FILE_MISSING_MESSAGE +
                System.lineSeparator() +
                UiConstant.PARTITION_LINE +
                System.lineSeparator();
    }
}
