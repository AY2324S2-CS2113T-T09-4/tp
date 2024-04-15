package utility;

import constants.ErrorConstant;
import constants.HealthConstant;
import constants.UiConstant;
import constants.WorkoutConstant;
import health.Bmi;
import health.HealthList;
import ui.Output;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Objects;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Represents the validation class used to validate all inputs for PulsePilot.
 */
public class Validation {

    //@@author JustinSoh
    public Validation(){

    }

    //@@author rouvinerh
    /**
     * Validates that the input date string is correctly formatted in DD-MM-YYYY and is a valid date.
     *
     * @param date The string date from user input.
     * @throws CustomExceptions.InvalidInput If the date is invalid.
     */
    public void validateDateInput(String date) throws CustomExceptions.InvalidInput {
        if (!date.matches(UiConstant.VALID_DATE_REGEX)) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_DATE_ERROR);
        }
        String[] parts = date.split(UiConstant.DASH);
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
        if (month == 2 && day == 29 && !isLeapYear) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_LEAP_YEAR_ERROR);
        }

        if (year < 1967) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_YEAR_ERROR);
        }

        try {
            LocalDate check = LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_DATE_ERROR);
        }
    }

    /**
     * Validates the delete input details.
     *
     * @param deleteDetails An array containing the details for the delete command.
     * @throws CustomExceptions.InvalidInput If the details specified are invalid.
     * @throws CustomExceptions.InsufficientInput If empty strings are found.
     */
    public void validateDeleteInput(String[] deleteDetails) throws CustomExceptions.InvalidInput,
            CustomExceptions.InsufficientInput {
        if (isEmptyParameterPresent(deleteDetails)) {
            throw new CustomExceptions.InsufficientInput(ErrorConstant.INSUFFICIENT_DELETE_PARAMETERS_ERROR);
        }
        validateDeleteAndLatestFilter(deleteDetails[UiConstant.DELETE_ITEM_STRING_INDEX].toLowerCase());
        if (!validateIntegerIsPositive(deleteDetails[UiConstant.DELETE_ITEM_NUMBER_INDEX])) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_INDEX_ERROR);
        }
    }

    //@@author L5-Z
    /**
     * Validates whether the filter string is either 'run', 'gym', 'workouts', 'bmi', 'period' or 'appointment'.
     *
     * @param filter The filter string to be checked.
     * @throws CustomExceptions.InvalidInput If the filter string is none of them.
     */
    public void validateHistoryFilter(String filter) throws CustomExceptions.InvalidInput {
        if (filter.equals(WorkoutConstant.RUN)
                || filter.equals(WorkoutConstant.GYM)
                || filter.equals(HealthConstant.BMI)
                || filter.equals(HealthConstant.PERIOD)
                || filter.equals(HealthConstant.APPOINTMENT)
                || filter.equals(WorkoutConstant.ALL)) {
            return;
        }
        throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_HISTORY_FILTER_ERROR);
    }

    //@@author L5-Z
    /**
     * Validates whether the filter string is either 'run', 'gym', 'bmi', 'period' or 'appointment'.
     *
     * @param filter The filter string to be checked.
     * @throws CustomExceptions.InvalidInput If the filter string is none of them.
     */
    public void validateDeleteAndLatestFilter(String filter) throws CustomExceptions.InvalidInput {
        if (filter.equals(WorkoutConstant.RUN)
                || filter.equals(WorkoutConstant.GYM)
                || filter.equals(HealthConstant.BMI)
                || filter.equals(HealthConstant.PERIOD)
                || filter.equals(HealthConstant.APPOINTMENT)) {
            return;
        }
        throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_LATEST_OR_DELETE_FILTER);
    }

    //@@author j013n3
    /**
     * Validates the BMI details entered.
     *
     * @param bmiDetails An array of strings with split BMI details.
     * @throws CustomExceptions.InvalidInput If there are any errors in the details entered.
     * @throws CustomExceptions.InsufficientInput If there are empty parameters specified.
     */
    public void validateBmiInput(String[] bmiDetails) throws CustomExceptions.InvalidInput,
            CustomExceptions.InsufficientInput {
        if (isEmptyParameterPresent(bmiDetails)) {
            throw new CustomExceptions.InsufficientInput(ErrorConstant.INSUFFICIENT_BMI_PARAMETERS_ERROR);
        }

        if (!bmiDetails[HealthConstant.BMI_HEIGHT_INDEX].matches(UiConstant.VALID_TWO_DP_NUMBER_REGEX)
                || !bmiDetails[HealthConstant.BMI_WEIGHT_INDEX].matches(UiConstant.VALID_TWO_DP_NUMBER_REGEX)) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_HEIGHT_WEIGHT_INPUT_ERROR);
        }

        double height = Double.parseDouble(bmiDetails[HealthConstant.BMI_HEIGHT_INDEX]);
        double weight = Double.parseDouble(bmiDetails[HealthConstant.BMI_WEIGHT_INDEX]);
        if (height <= HealthConstant.MIN_HEIGHT || weight <= HealthConstant.MIN_WEIGHT) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.ZERO_HEIGHT_AND_WEIGHT_ERROR);
        }
        if (height > HealthConstant.MAX_HEIGHT) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.MAX_HEIGHT_ERROR);
        }
        if (weight > HealthConstant.MAX_WEIGHT) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.MAX_WEIGHT_ERROR);
        }

        validateDateInput(bmiDetails[HealthConstant.BMI_DATE_INDEX]);
        validateDateNotAfterToday(bmiDetails[HealthConstant.BMI_DATE_INDEX]);
        validateDateNotPresent(bmiDetails[HealthConstant.BMI_DATE_INDEX]);
    }

    /**
     * Validates the period details entered.
     *
     * @param periodDetails An array of strings with split period details.
     * @param isParser A boolean indicating whether the input comes from Parser.
     * @throws CustomExceptions.InvalidInput If there are any errors in the details entered.
     * @throws CustomExceptions.InsufficientInput If there are empty parameters specified.
     */
    public void validatePeriodInput(String[] periodDetails, boolean isParser) throws CustomExceptions.InvalidInput,
            CustomExceptions.InsufficientInput {
        if (isEmptyParameterPresent(periodDetails)) {
            throw new CustomExceptions.InsufficientInput(ErrorConstant.INSUFFICIENT_PERIOD_PARAMETERS_ERROR);
        }
        try {
            validateDateInput(periodDetails[HealthConstant.PERIOD_START_DATE_INDEX]);
        } catch (CustomExceptions.InvalidInput e) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_START_DATE_ERROR + e.getMessage());
        }
        try {
            if (validateDateNotEmpty(periodDetails[HealthConstant.PERIOD_END_DATE_INDEX])) {
                validateDateInput(periodDetails[HealthConstant.PERIOD_END_DATE_INDEX]);
            }
        } catch (CustomExceptions.InvalidInput e) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_END_DATE_ERROR + e.getMessage());
        }

        validateIfOnlyFromParser(isParser, periodDetails);
        validateDateNotAfterToday(periodDetails[HealthConstant.PERIOD_START_DATE_INDEX]);
        Parser parser = new Parser();
        LocalDate startDate = parser.parseDate(periodDetails[HealthConstant.PERIOD_START_DATE_INDEX]);
        if (validateDateNotEmpty(periodDetails[HealthConstant.PERIOD_END_DATE_INDEX])) {
            validateDateNotAfterToday(periodDetails[HealthConstant.PERIOD_END_DATE_INDEX]);
            LocalDate endDate = parser.parseDate(periodDetails[HealthConstant.PERIOD_END_DATE_INDEX]);
            if (startDate.isAfter(endDate)) {
                throw new CustomExceptions.InvalidInput(ErrorConstant.PERIOD_END_BEFORE_START_ERROR);
            }
        }
    }

    //@@author rouvinerh
    /**
     * Validates the run details entered.
     *
     * @param runDetails An array of strings with split run details.
     * @throws CustomExceptions.InvalidInput If the details are wrongly formatted, or if date is in future or invalid.
     * @throws CustomExceptions.InsufficientInput If empty strings are used.
     */
    public void validateRunInput(String[] runDetails) throws CustomExceptions.InvalidInput,
            CustomExceptions.InsufficientInput {
        if (isEmptyParameterPresent(runDetails)) {
            throw new CustomExceptions.InsufficientInput(ErrorConstant.INSUFFICIENT_RUN_PARAMETERS_ERROR);
        }

        if (!runDetails[WorkoutConstant.RUN_TIME_INDEX].matches(UiConstant.VALID_TIME_REGEX) &&
                !runDetails[WorkoutConstant.RUN_TIME_INDEX].matches(UiConstant.VALID_TIME_WITH_HOURS_REGEX)) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_RUN_TIME_ERROR);
        }

        if (!runDetails[WorkoutConstant.RUN_DISTANCE_INDEX].matches(UiConstant.VALID_TWO_DP_NUMBER_REGEX)) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_RUN_DISTANCE_ERROR);
        }

        if (validateDateNotEmpty(runDetails[WorkoutConstant.RUN_DATE_INDEX])) {
            validateDateInput(runDetails[WorkoutConstant.RUN_DATE_INDEX]);
            validateDateNotAfterToday(runDetails[WorkoutConstant.RUN_DATE_INDEX]);
        }
    }

    //@@author JustinSoh
    /**
     * Validates the gym details entered.
     *
     * @param gymDetails An array of strings with split Gym details.
     * @throws CustomExceptions.InvalidInput If the details specified are invalid.
     * @throws CustomExceptions.InsufficientInput If empty strings are used.
     */
    public void validateGymInput(String[] gymDetails) throws CustomExceptions.InvalidInput,
            CustomExceptions.InsufficientInput {
        if (isEmptyParameterPresent(gymDetails)) {
            throw new CustomExceptions.InsufficientInput(ErrorConstant.INSUFFICIENT_GYM_PARAMETERS_ERROR);
        }
        if (!validateIntegerIsPositive(gymDetails[WorkoutConstant.GYM_NUMBER_OF_STATIONS_INDEX])) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_NUMBER_OF_STATIONS_ERROR);
        }

        int numberOfStations = Integer.parseInt(gymDetails[WorkoutConstant.GYM_NUMBER_OF_STATIONS_INDEX]);
        if (numberOfStations > WorkoutConstant.MAX_GYM_STATION_NUMBER) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.MAX_STATIONS_ERROR);
        }

        if (validateDateNotEmpty(gymDetails[WorkoutConstant.GYM_DATE_INDEX])) {
            validateDateInput(gymDetails[WorkoutConstant.GYM_DATE_INDEX]);
            validateDateNotAfterToday(gymDetails[WorkoutConstant.GYM_DATE_INDEX]);
        }
    }

    //@@author syj02
    /**
     * Validates the appointment details entered.
     *
     * @param appointmentDetails An array of strings with split appointment details.
     * @throws CustomExceptions.InvalidInput If there are any errors in the details entered.
     * @throws CustomExceptions.InsufficientInput If date, time, or description parameters are empty or invalid.
     */
    public void validateAppointmentDetails(String[] appointmentDetails)
            throws CustomExceptions.InvalidInput, CustomExceptions.InsufficientInput {
        if (isEmptyParameterPresent(appointmentDetails)) {
            throw new CustomExceptions.InsufficientInput(ErrorConstant
                    .INSUFFICIENT_APPOINTMENT_PARAMETERS_ERROR);
        }
        validateDateInput(appointmentDetails[HealthConstant.APPOINTMENT_DATE_INDEX]);
        validateTimeInput(appointmentDetails[HealthConstant.APPOINTMENT_TIME_INDEX]);

        if (appointmentDetails[HealthConstant.APPOINTMENT_DESCRIPTION_INDEX].length()
                > HealthConstant.MAX_DESCRIPTION_LENGTH) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.DESCRIPTION_LENGTH_ERROR);
        }
        if (!appointmentDetails[HealthConstant.APPOINTMENT_DESCRIPTION_INDEX]
                .matches(UiConstant.VALID_APPOINTMENT_DESCRIPTION_REGEX)) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_DESCRIPTION_ERROR);
        }
    }

    //@@author rouvinerh
    /**
     * Checks whether the username has only alphanumeric characters and spaces.
     *
     * @param name The input name from the user
     * @return Returns true if it only has alphanumeric characters, otherwise returns false.
     */
    public boolean validateIfUsernameIsValid(String name) {
        return !name.matches(UiConstant.VALID_USERNAME_REGEX);
    }

    /**
     * Checks whether date is set to null or 'NA'. Both cases mean date is not specified.
     *
     * @param date The date string to check.
     * @return Returns true if date is specified, otherwise returns false.
     */
    public boolean validateDateNotEmpty (String date) {
        return date != null && !date.equals("NA");
    }

    //@@author j013n3
    /**
     * Validates whether the start date is before or equal to the end date of the latest period in the HealthList.
     * Throws an error if it is.
     *
     * @param dateString The string representation of the date to be validated.
     * @param latestPeriodEndDate The end date of the latest period in the HealthList.
     * @throws CustomExceptions.InvalidInput If the date specified is not after the end date of the latest period.
     */
    public void validateDateAfterLatestPeriodInput(String dateString, LocalDate latestPeriodEndDate)
            throws CustomExceptions.InvalidInput {
        Parser parser = new Parser();
        LocalDate date = parser.parseDate(dateString);

        if (latestPeriodEndDate != null && (date.isBefore(latestPeriodEndDate) || date.isEqual(latestPeriodEndDate))) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.CURRENT_START_BEFORE_PREVIOUS_END);
        }
    }

    /**
     * Validates whether the specified start date matches the start date of the latest period in the HealthList
     * and checks if end date exists.
     *
     * @param latestPeriodEndDate The end date of the latest period in the HealthList.
     * @param periodDetails       An array containing details of the current period input.
     * @throws CustomExceptions.InvalidInput If the start date does not match the start date of the latest period
     *                                        or if insufficient parameters are provided.
     */
    public void validateStartDatesTally(LocalDate latestPeriodEndDate, String[] periodDetails)
            throws CustomExceptions.InvalidInput {
        Parser parser = new Parser();
        LocalDate startDate = parser.parseDate(periodDetails[HealthConstant.PERIOD_START_DATE_INDEX]);
        LocalDate latestPeriodStartDate =
                Objects.requireNonNull(HealthList.getPeriod(HealthConstant.FIRST_ITEM)).getStartDate();

        if (latestPeriodEndDate == null) {
            if (!startDate.equals(latestPeriodStartDate)) {
                throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_START_DATE_INPUT_ERROR);
            }
            if (periodDetails[HealthConstant.PERIOD_END_DATE_INDEX] == null) {
                throw new CustomExceptions.InvalidInput(ErrorConstant.END_DATE_NOT_FOUND_ERROR );
            }
        }
    }

    /**
     * Validates input data if it comes from Parser and validates the input using two other methods from Validation.
     *
     * @param isParser       a boolean indicating whether the input comes from Parser
     * @param periodDetails  an array of strings containing period details
     * @throws CustomExceptions.InvalidInput if the input data is invalid
     */
    public void validateIfOnlyFromParser(boolean isParser, String[] periodDetails)
            throws CustomExceptions.InvalidInput {
        int sizeOfPeriodList = HealthList.getPeriodsSize();
        if (isParser && sizeOfPeriodList >= UiConstant.MINIMUM_PERIOD_COUNT) {
            LocalDate latestPeriodEndDate =
                    Objects.requireNonNull(HealthList.getPeriod(HealthConstant.FIRST_ITEM)).getEndDate();
            validateStartDatesTally(latestPeriodEndDate, periodDetails);
            validateDateAfterLatestPeriodInput(periodDetails[HealthConstant.PERIOD_START_DATE_INDEX],
                    latestPeriodEndDate);
        }
    }

    //@@author rouvinerh
    /**
     * Checks whether current directory is readable and writable. If no, print exception and exit bot.
     * If yes, do nothing.
     */
    public void validateDirectoryPermissions() {
        Path currentDirectory = Path.of("");
        boolean isValidPermissions = Files.isReadable(currentDirectory) && Files.isWritable(currentDirectory);
        if (!isValidPermissions) {
            Output output = new Output();
            output.printException(ErrorConstant.NO_PERMISSIONS_ERROR);
            System.exit(1);
        }
    }

    //@@author j013n3
    /**
     * Validates whether the specified date can be found in HealthList and throws error if it is.
     *
     * @param dateString The date of the Bmi input to be added.
     * @throws CustomExceptions.InvalidInput If the same date is found.
     */
    public void validateDateNotPresent(String dateString) throws CustomExceptions.InvalidInput {
        Parser parser = new Parser();
        LocalDate dateToVerify = parser.parseDate(dateString);
        for (Bmi bmi :  HealthList.getBmis()) {
            if (bmi.getDate().isEqual(dateToVerify)) {
                throw new CustomExceptions.InvalidInput(ErrorConstant.DATE_ALREADY_EXISTS_ERROR);
            }
        }
    }

    //@@author JustinSoh
    /**
     * Validates whether the current index provided is within the start and end.
     *
     * @param index The index to be validated.
     * @param start The starting bound.
     * @param end the ending bound (exclusive - e.g. end = 5 means index must be less than 5).
     * @return true if the index is within the bounds, false otherwise.
     */
    public static boolean validateIndexWithinBounds(int index, int start, int end) {
        return index >= start && index < end;
    }

    public static boolean validateIntegerIsPositive(String value) {
        return value.matches(UiConstant.VALID_POSITIVE_INTEGER_REGEX);
    }

    //@@author rouvinerh
    /**
     * Validates that time is in HH:MM 24 hours format, and if it is a valid time.
     *
     * @param time The String time to check.
     * @throws CustomExceptions.InvalidInput If time is formatted wrongly or is not valid.
     */
    protected void validateTimeInput(String time) throws CustomExceptions.InvalidInput {
        if (!time.matches(UiConstant.VALID_TIME_REGEX)) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_ACTUAL_TIME_ERROR);
        }
        String [] parts = time.split(UiConstant.SPLIT_BY_COLON);
        int hours = Integer.parseInt(parts[UiConstant.SPLIT_TIME_HOUR_INDEX]);
        int minutes = Integer.parseInt(parts[UiConstant.SPLIT_TIME_MINUTES_INDEX]);

        if (hours < UiConstant.MIN_HOURS || hours > UiConstant.MAX_HOURS) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_ACTUAL_TIME_HOUR_ERROR);
        }
        if (minutes < UiConstant.MIN_MINUTES || minutes > UiConstant.MAX_MINUTES) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_ACTUAL_TIME_MINUTE_ERROR);
        }
    }

    //@@author rouvinerh
    /**
     * Checks whether the list of input details contains any empty strings.
     *
     * @param input A list of strings representing command inputs.
     * @return False if it contains empty strings. Otherwise, returns true.
     */
    protected boolean isEmptyParameterPresent(String[] input) {
        for (String s : input) {
            if (s != null && s.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates whether the date specified is after today. Throws an error if it is.
     *
     * @param dateString A string representing the date.
     * @throws CustomExceptions.InvalidInput If the date specified is after today.
     */
    protected void validateDateNotAfterToday(String dateString) throws CustomExceptions.InvalidInput {
        Parser parser = new Parser();
        LocalDate date = parser.parseDate(dateString);
        if (date.isAfter(LocalDate.now())) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.DATE_IN_FUTURE_ERROR);
        }
    }
}
