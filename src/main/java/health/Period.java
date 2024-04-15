package health;

import constants.ErrorConstant;
import constants.HealthConstant;
import ui.Output;
import utility.Parser;
import constants.UiConstant;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * The Period class inherits from Health class.
 * It contains information about the start date, end date, time, period length, and cycle length of the period,
 * and the methods to calculate period and cycle length and predict the next period.
 */
public class Period extends Health {
    //@@author syj02
    private LocalDate startDate;
    private LocalDate endDate;
    private long periodLength;
    private long cycleLength;

    private final Parser parser = new Parser();
    private final HealthList healthList = new HealthList();

    /**
     * Constructs a new Period object with only the start date provided.
     *
     * @param stringStartDate A string representing the start date of the period.
     */
    public Period(String stringStartDate) {
        this.startDate = parser.parseDate(stringStartDate);
        this.endDate = null;
        this.periodLength = 1;
        this.cycleLength = 0;
        healthList.addPeriod(this);
    }

    /**
     * Constructor for Period object.
     *
     * @param stringStartDate A string representing the start date of the period.
     * @param stringEndDate   A string representing the end date of the period.
     */
    public Period(String stringStartDate, String stringEndDate) {
        this.startDate = parser.parseDate(stringStartDate);
        this.endDate = parser.parseDate(stringEndDate);
        this.periodLength = calculatePeriodLength();
        this.cycleLength = 0;
        healthList.addPeriod(this);
    }

    /**
     * Gets cycle length.
     *
     * @return Cycle length as long.
     */
    public long getCycleLength() {
        return cycleLength;
    }

    /**
     * Updates the end date of the period and calculates the period length.
     *
     * @param stringEndDate A String representing the new end date of the period.
     */
    public void updateEndDate(String stringEndDate) {
        this.endDate = parser.parseDate(stringEndDate);
        this.periodLength = calculatePeriodLength();
    }

    /**
     * Retrieves the start date of the period of LocalDate type.
     *
     * @return The start date of period.
     * @throws AssertionError if the start date is null.
     */
    public LocalDate getStartDate() {
        assert startDate != null : ErrorConstant.NULL_START_DATE_ERROR;
        return startDate;
    }

    /**
     * Retrieves the end date of the period of LocalDate type.
     *
     * @return The end date of period.
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Retrieves the length of the period of long type.
     *
     * @return The period length.
     */
    public long getPeriodLength() {
        assert periodLength > HealthConstant.MIN_LENGTH : ErrorConstant.LENGTH_MUST_BE_POSITIVE_ERROR;
        return periodLength;
    }

    //@@author j013n3
    /**
     * Calculates the sum of the cycle lengths of the latest three menstrual cycles.
     *
     * @return The sum of the cycle lengths of the latest three menstrual cycles.
     */
    public long getLastThreeCycleLengths() {
        long sumOfCycleLengths = 0;
        int startIndexForPrediction = HealthConstant.LAST_CYCLE_INDEX;
        assert startIndexForPrediction >= HealthConstant.FIRST_ITEM : ErrorConstant.START_INDEX_NEGATIVE_ERROR;

        int endIndexForPrediction = HealthConstant.FIRST_CYCLE_INDEX;
        assert endIndexForPrediction >= startIndexForPrediction : ErrorConstant.END_INDEX_SMALLER_THAN_START_ERROR;

        for (int i = startIndexForPrediction; i <= endIndexForPrediction; i++) {
            sumOfCycleLengths += Objects.requireNonNull(HealthList.getPeriod(i)).cycleLength;
        }
        return sumOfCycleLengths;
    }

    /**
     * Predicts the start date of the next period based on the average cycle length.
     *
     * @return The predicted start date of the next period.
     */
    public LocalDate nextCyclePrediction() {
        long averageCycleLength = getLastThreeCycleLengths() / HealthConstant.LATEST_THREE_CYCLE_LENGTHS;
        return getStartDate().plusDays(averageCycleLength);
    }

    /**
     * Sets the cycle length of the current period based on the start date of the next period.
     *
     * @param nextStartDate The start date of the next period.
     */
    public void setCycleLength(LocalDate nextStartDate) {
        this.cycleLength = ChronoUnit.DAYS.between(getStartDate(), nextStartDate);
    }

    /**
     * Prints a message indicating the number of days until the predicted start date of the next period,
     * or how many days late the period is if the current date is after the predicted start date.
     *
     * @param nextPeriodStartDate The predicted start date of the next period.
     */
    public static void printNextCyclePrediction(LocalDate nextPeriodStartDate) {
        LocalDate today = LocalDate.now();
        long daysUntilNextPeriod = today.until(nextPeriodStartDate, ChronoUnit.DAYS);
        if (today.isBefore(nextPeriodStartDate)) {
            System.out.println(HealthConstant.PREDICTED_START_DATE_MESSAGE
                    + nextPeriodStartDate
                    + HealthConstant.COUNT_DAYS_MESSAGE
                    + daysUntilNextPeriod
                    + UiConstant.SPLIT_BY_WHITESPACE
                    + HealthConstant.DAYS_MESSAGE
                    + UiConstant.FULL_STOP);
        }

        if (today.isEqual(nextPeriodStartDate)) {
            System.out.println(HealthConstant.PREDICTED_START_DATE_MESSAGE
                    + nextPeriodStartDate
                    + HealthConstant.PREDICTED_DATE_IS_TODAY_MESSAGE);
        }

        if (today.isAfter(nextPeriodStartDate)) {
            System.out.println(HealthConstant.PREDICTED_START_DATE_MESSAGE
                    + nextPeriodStartDate
                    + HealthConstant.PERIOD_IS_LATE
                    + -daysUntilNextPeriod
                    + UiConstant.SPLIT_BY_WHITESPACE
                    + HealthConstant.DAYS_MESSAGE
                    + UiConstant.FULL_STOP);
        }
        Output.printLine();
    }

    /**
     * Returns the string representation of a Period object.
     *
     * @return A formatted string representing a Period object.
     */
    @Override
    public String toString() {
        String endDateUnit = (getEndDate() == null) ? ErrorConstant.NO_DATE_SPECIFIED_ERROR : getEndDate().toString();
        return String.format(HealthConstant.PRINT_PERIOD_FORMAT,
                getStartDate(),
                endDateUnit,
                getPeriodLength(),
                HealthConstant.DAYS_MESSAGE)
                + (this.cycleLength > HealthConstant.MIN_LENGTH ? System.lineSeparator()
                + String.format(HealthConstant.PRINT_CYCLE_FORMAT, this.cycleLength) : UiConstant.EMPTY_STRING);
    }

    /**
     * Calculates the length of the period in days.
     *
     * @return The length of the period.
     */
    protected long calculatePeriodLength() {
        if (endDate == null || startDate == null) {
            return 0;
        } else {
            // Add 1 to include both start and end dates
            return ChronoUnit.DAYS.between(getStartDate(), getEndDate()) + 1;
        }
    }
}
