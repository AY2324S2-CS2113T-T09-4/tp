package workouts;


import utility.CustomExceptions;
import constants.ErrorConstant;
import constants.UiConstant;
import constants.WorkoutConstant;

/**
 * Represents a Run object that extends the Workout class.
 * It takes in the time and distance of the run as input.
 * It also calculates the pace of the run based on the time and distance.
 * It also formats the time and distance into a readable format.
 */
public class Run extends Workout {
    //@@author rouvinerh
    private final Integer[] times;
    private final double distance;
    private final String pace;

    /**
     * Constructs a new Run object with the time and distance from user input.
     *
     * @param stringTime The time taken for the run.
     * @param stringDistance The distance of the run.
     * @throws CustomExceptions.InvalidInput If there is invalid input in any parameters found.
     */
    public Run(String stringTime, String stringDistance) throws CustomExceptions.InvalidInput {
        times = processRunTime(stringTime);
        distance = checkDistance(stringDistance);
        pace = calculatePace();
        super.addIntoWorkoutList(this);
    }

    /**
     * Overloaded constructor that takes in time, distance and the optional date parameter from user input.
     *
     * @param stringTime The time taken for the run.
     * @param stringDistance The distance of the run.
     * @param stringDate The date of the run.
     * @throws CustomExceptions.InvalidInput If there is invalid input in any parameters found.
     */
    public Run(String stringTime, String stringDistance, String stringDate) throws CustomExceptions.InvalidInput {
        super(stringDate);
        times = processRunTime(stringTime);
        distance = checkDistance(stringDistance);
        pace = calculatePace();
        Workout workout = new Workout();
        workout.addIntoWorkoutList(this);
    }

    /**
     * Returns string format of time taken for run.
     * If there isn't an hour present, returns only mm:ss.
     * If not returns hh:mm:ss.
     *
     * @return Formatted string of the time for the run.
     */
    public String getTimes() {
        if (times[WorkoutConstant.RUN_TIME_HOUR_INDEX] > UiConstant.MIN_HOURS) {
            int hours = times[WorkoutConstant.RUN_TIME_HOUR_INDEX];
            int minutes = times[WorkoutConstant.RUN_TIME_MINUTE_INDEX];
            int seconds = times[WorkoutConstant.RUN_TIME_SECOND_INDEX];
            return String.format(WorkoutConstant.TIME_WITH_HOURS_FORMAT, hours, minutes, seconds);

        } else {
            int minutes = times[WorkoutConstant.RUN_TIME_MINUTE_INDEX];
            int seconds = times[WorkoutConstant.RUN_TIME_SECOND_INDEX];
            return String.format(WorkoutConstant.TIME_WITHOUT_HOURS_FORMAT, minutes, seconds);
        }
    }

    /**
     * Retrieves the run distance in two decimal place format.
     *
     * @return Run distance as String.
     */
    public String getDistance() {
        return String.format(WorkoutConstant.TWO_DECIMAL_PLACE_FORMAT, distance);
    }

    /**
     * Retrieves run pace.
     *
     * @return Run pace as String.
     */
    public String getPace() {
        return pace;
    }

    //@@author JustinSoh

    /**
     * Retrieves the string representation of a Run object.
     *
     * @return A formatted string representing a Run object.
     */
    @Override
    public String toString() {
        String printedDate = super.getDate();
        return String.format(WorkoutConstant.RUN_DATA_FORMAT, WorkoutConstant.RUN,
                getTimes(), getDistance(), getPace(), printedDate);
    }

    /**
     * Retrieves the string representation of a Run object when printing all history.
     * Uses WorkoutConstant.HISTORY_WORKOUTS_DATA_FORMAT to format the string.
     * Ensures that the format of the string is consistent when printing gym and run objects.
     * @return a formatted string representing a Run object.
     */
    public String getFormatForAllHistory() {
        String printedDate = super.getDate();
        return String.format(WorkoutConstant.HISTORY_WORKOUTS_DATA_FORMAT,
                WorkoutConstant.RUN,
                printedDate,
                getDistance(),
                getTimes(),
                getPace()
        );
    }

    //@@author rouvinerh
    /**
     * Method splits and validates run time input.
     *
     * @param inputTime String variable representing time taken in either hh:mm:ss or mm:ss format.
     * @return A list of integers representing the hours (if present), minutes and seconds.
     * @throws CustomExceptions.InvalidInput if the input time is not in the correct format.
     */
    protected Integer[] processRunTime(String inputTime) throws CustomExceptions.InvalidInput {
        String [] parts = inputTime.split(UiConstant.SPLIT_BY_COLON);
        int hours = WorkoutConstant.NO_HOURS_PRESENT;
        int minutes = UiConstant.MIN_MINUTES;
        int seconds = UiConstant.MIN_SECONDS;

        if (parts.length == WorkoutConstant.NUMBER_OF_PARTS_FOR_RUN_TIME) {
            minutes = Integer.parseInt(parts[WorkoutConstant.RUN_TIME_NO_HOURS_MINUTE_INDEX]);
            seconds = Integer.parseInt(parts[WorkoutConstant.RUN_TIME_NO_HOURS_SECOND_INDEX]);
        } else if (parts.length == WorkoutConstant.NUMBER_OF_PARTS_FOR_RUN_TIME_WITH_HOURS) {
            hours = Integer.parseInt(parts[WorkoutConstant.RUN_TIME_HOUR_INDEX]);
            minutes = Integer.parseInt(parts[WorkoutConstant.RUN_TIME_MINUTE_INDEX]);
            seconds = Integer.parseInt(parts[WorkoutConstant.RUN_TIME_SECOND_INDEX]);
        }

        Integer[] runTimeParts = new Integer[]{hours, minutes, seconds};
        checkRunTimeValues(runTimeParts);
        return runTimeParts;
    }

    /**
     * Checks the validity of distance value specified for the run. Returns the distance as a double if valid.
     *
     * @param stringDistance The string representation of the distance.
     * @return The run distance as a Double.
     * @throws CustomExceptions.InvalidInput If the distance is outside the valid range.
     */
    protected Double checkDistance(String stringDistance) throws CustomExceptions.InvalidInput {
        double runDistance = Double.parseDouble(stringDistance);
        if (runDistance > WorkoutConstant.MAX_RUN_DISTANCE) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.DISTANCE_TOO_LONG_ERROR);
        }

        if (runDistance <= WorkoutConstant.MIN_RUN_DISTANCE) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.ZERO_DISTANCE_ERROR);
        }
        return runDistance;
    }

    /**
     * Method calculates the pace of the run, and formats it into minutes per km.
     *
     * @return The pace of the run as a String.
     * @throws CustomExceptions.InvalidInput If the pace calculated is too large or small.
     */
    protected String calculatePace() throws CustomExceptions.InvalidInput {
        int totalSeconds = calculateTotalSeconds();
        double paceInDecimal = ((double) totalSeconds / this.distance) / UiConstant.NUM_SECONDS_IN_MINUTE;

        if (paceInDecimal > WorkoutConstant.MAX_PACE) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.MAX_PACE_ERROR);
        }
        if (paceInDecimal < WorkoutConstant.MIN_PACE) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.MIN_PACE_ERROR);
        }

        int minutes = (int) paceInDecimal;
        double remainingSeconds = paceInDecimal - minutes;
        int seconds = (int) Math.round(remainingSeconds * UiConstant.NUM_SECONDS_IN_MINUTE);

        return String.format(WorkoutConstant.RUN_PACE_FORMAT, minutes, seconds);
    }

    /**
     * Returns the total seconds based on the times taken for the run.
     *
     * @return The total number of seconds in the run.
     */
    private int calculateTotalSeconds() {
        int totalSeconds;

        if (times[0] > 0) {
            totalSeconds = this.times[WorkoutConstant.RUN_TIME_HOUR_INDEX] * UiConstant.NUM_SECONDS_IN_HOUR
                    + this.times[WorkoutConstant.RUN_TIME_MINUTE_INDEX] * UiConstant.NUM_SECONDS_IN_MINUTE
                    + this.times[WorkoutConstant.RUN_TIME_SECOND_INDEX];
        } else {
            totalSeconds = this.times[WorkoutConstant.RUN_TIME_MINUTE_INDEX] * UiConstant.NUM_SECONDS_IN_MINUTE
                    + this.times[WorkoutConstant.RUN_TIME_SECOND_INDEX];
        }
        return totalSeconds;
    }

    /**
     * Checks the hour, minute and second values for run time.
     *
     * @param runTimeParts The run time values.
     * @throws CustomExceptions.InvalidInput If the run time specified is not invalid.
     */
    private void checkRunTimeValues(Integer[] runTimeParts) throws CustomExceptions.InvalidInput {
        int hours = runTimeParts[WorkoutConstant.RUN_TIME_HOUR_INDEX];
        int minutes = runTimeParts[WorkoutConstant.RUN_TIME_MINUTE_INDEX];
        int seconds = runTimeParts[WorkoutConstant.RUN_TIME_SECOND_INDEX];

        if (hours == UiConstant.MIN_HOURS) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_HOUR_ERROR);
        }

        // minutes can always be 00
        if (minutes > UiConstant.MAX_MINUTES) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_MINUTE_ERROR);
        }

        // seconds can never be > 59
        if (seconds > UiConstant.MAX_SECONDS) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_SECOND_ERROR);
        }
        if (hours == WorkoutConstant.NO_HOURS_PRESENT) {
            // if hours not present, minutes and seconds cannot be 00
            if (minutes == UiConstant.MIN_MINUTES && seconds == UiConstant.MIN_SECONDS) {
                throw new CustomExceptions.InvalidInput(ErrorConstant.ZERO_TIME_ERROR);
            }
        }
    }
}
