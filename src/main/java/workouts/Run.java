package workouts;

import java.time.LocalDate;

import utility.CustomExceptions;
import utility.Parser;
import constants.ErrorConstant;
import constants.UiConstant;
import constants.WorkoutConstant;

/**
 * Represents a Run object.
 */
public class Run extends Workout {
    protected Integer[] times;
    protected double distance;
    protected LocalDate date = null;
    protected String pace;
    protected boolean isHourPresent;

    /**
     * Constructs a new Run object with the time and distance from user input.
     *
     * @param stringTime     The time taken for the run.
     * @param stringDistance The distance of the run.
     * @throws CustomExceptions.InvalidInput If there is invalid input.
     */
    public Run(String stringTime, String stringDistance) throws CustomExceptions.InvalidInput {
        times = splitRunTime(stringTime);
        distance = Double.parseDouble(stringDistance);
        pace = calculatePace();
        WorkoutList.addRun(this);
    }

    /**
     * Overloaded constructor that takes in time, distance and the optional date parameter from user input.
     *
     * @param stringTime     The time taken for the run.
     * @param stringDistance The distance of the run.
     * @param stringDate     The date of the run.
     * @throws CustomExceptions.InvalidInput If there is invalid input.
     */
    public Run(String stringTime, String stringDistance, String stringDate) throws CustomExceptions.InvalidInput {
        times = splitRunTime(stringTime);
        distance = Double.parseDouble(stringDistance);
        date = Parser.parseDate(stringDate);
        pace = calculatePace();
        WorkoutList.addRun(this);
    }

    /**
     * Returns string format of time taken for run.
     *
     * @return Formatted string of the time for the run.
     */
    public String getTimes() {
        if (isHourPresent) {
            return String.format(WorkoutConstant.TWO_DIGIT_PLACE_FORMAT, times[0])
                    + ":"
                    + String.format(WorkoutConstant.TWO_DIGIT_PLACE_FORMAT, times[1])
                    + ":"
                    + String.format(WorkoutConstant.TWO_DIGIT_PLACE_FORMAT, times[2]);
        } else {
            return String.format(WorkoutConstant.TWO_DIGIT_PLACE_FORMAT, times[0])
                    + ":"
                    + String.format(WorkoutConstant.TWO_DIGIT_PLACE_FORMAT, times[1]);
        }
    }

    /**
     * Retrieves run distance.
     *
     * @return Run distance.
     */
    public String getDistance() {
        return String.format(WorkoutConstant.TWO_DECIMAL_PLACE_FORMAT, distance);
    }
    /**
     * Retrieves run pace.
     *
     * @return Run pace.
     */
    public String getPace() {
        return pace;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isHourPresent() {
        return isHourPresent;
    }

    /**
     * Method parses the time format in either hh:mm:ss or mm:ss.
     * Sets {@code isHourPresent} variable to true if hours have been specified.
     * Otherwise, set to false.
     *
     * @param inputTime String variable representing time taken in either hh:mm:ss or mm:ss format
     * @return A list of integers representing the hours (if present), minutes and seconds.
     */
    public Integer[] splitRunTime(String inputTime) throws CustomExceptions.InvalidInput {

        String[] stringTimeParts = inputTime.split(":");
        int inputLength = stringTimeParts.length;
        Integer[] integerTimes = new Integer[inputLength];

        if (inputLength == UiConstant.MAX_RUNTIME_ARRAY_LENGTH) {
            this.isHourPresent = true;
            integerTimes[0] = Integer.parseInt(stringTimeParts[0]);
            integerTimes[1] = Integer.parseInt(stringTimeParts[1]);
            integerTimes[2] = Integer.parseInt(stringTimeParts[2]);
        } else if (inputLength == UiConstant.MIN_RUNTIME_ARRAY_LENGTH) {
            this.isHourPresent = false;
            integerTimes[0] = Integer.parseInt(stringTimeParts[0]);
            integerTimes[1] = Integer.parseInt(stringTimeParts[1]);
        } else {
            throw new CustomExceptions.InvalidInput(WorkoutConstant.INVALID_RUN_TIME);
        }
        return integerTimes;
    }

    /**
     * Method checks if hour has been specified, then returns total seconds.
     *
     * @return The total number of seconds in the run.
     */
    public int calculateTotalSeconds() {
        int totalSeconds;

        if (this.isHourPresent) {
            totalSeconds = this.times[0] * UiConstant.NUM_SECONDS_IN_HOUR
                    + this.times[1] * UiConstant.NUM_SECONDS_IN_MINUTE
                    + this.times[2];
        } else {
            totalSeconds = this.times[0] * 60 + this.times[1];
        }
        return totalSeconds;
    }

    /**
     * Method calculates the pace of the run, and formats it into M:SS/km.
     *
     * @return Formatted string the pace of the run.
     */
    public String calculatePace() {
        int totalSeconds = calculateTotalSeconds();
        double paceInDecimal = ((double) totalSeconds / this.distance) / UiConstant.NUM_SECONDS_IN_MINUTE;

        int minutes = (int) paceInDecimal;
        double remainingSeconds = paceInDecimal - minutes;
        int seconds = (int) Math.round(remainingSeconds * UiConstant.NUM_SECONDS_IN_MINUTE);
        return String.format("%d:%02d/km", minutes, seconds);
    }

    /**
     * Retrieves the string representation of a Run object.
     *
     * @return A formatted string representing a Run object.
     */
    @Override
    public String toString() {
        String printedDate;
        if (date != null) {
            printedDate = date.toString();
        } else {
            printedDate = ErrorConstant.NO_DATE_SPECIFIED_ERROR;
        }
        return String.format(WorkoutConstant.RUN_DATA_FORMAT, WorkoutConstant.RUN,
                getTimes(), getDistance(), getPace(), printedDate);
    }

    public String getFormatForAllHistory() {
        String printedDate;

        if (date != null) {
            printedDate = date.toString();
        } else {
            printedDate = ErrorConstant.NO_DATE_SPECIFIED_ERROR;
        }
        return String.format(WorkoutConstant.HISTORY_WORKOUTS_DATA_FORMAT,
                WorkoutConstant.RUN,
                printedDate,
                getDistance(),
                getTimes(),
                getPace(),
                "-", // Placeholder for gym sets (NA)
                "-", // Placeholder for gym reps (NA)
                "-", // Placeholder for gym weight (NA)
                "-"  // Placeholder for gym station (NA)
        );

    }

}
