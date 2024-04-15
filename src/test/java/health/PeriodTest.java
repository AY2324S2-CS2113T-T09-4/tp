package health;

import constants.ErrorConstant;
import constants.UiConstant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import utility.CustomExceptions;
import constants.HealthConstant;
import utility.Parser;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PeriodTest {
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void cleanup() {
        System.setOut(originalOut);
        HealthList.clearHealthLists();
        outContent.reset();
    }

    /**
     * Tests the behaviour of toString in Period class.
     */
    @Test
    void calculatePeriodLength_printsCorrectPeriod() {
        Period period = new Period("09-03-2022", "16-03-2022");
        String expected = "Period Start: "
                + period.getStartDate()
                + " Period End: "
                + period.getEndDate()
                + System.lineSeparator()
                + "Period Length: "
                + period.getPeriodLength()
                + " day(s)"
                + System.lineSeparator();

        System.out.println(period);
        assertEquals(expected, outContent.toString());
    }

    /**
     * Tests the behaviour of the showLatestPeriod method and whether it prints
     * the last Period object added.
     */
    @Test
    void printLatestPeriod_twoPeriodInputs_printCorrectPeriod() throws CustomExceptions.OutOfBounds {
        Period firstPeriod = new Period("09-02-2023", "16-02-2023");
        Period secondPeriod = new Period("09-03-2023", "16-03-2023");

        String expected = "Period Start: "
                + secondPeriod.getStartDate()
                + " Period End: "
                + secondPeriod.getEndDate()
                + System.lineSeparator()
                + "Period Length: "
                + secondPeriod.getPeriodLength()
                + " day(s)"
                + System.lineSeparator();

        HealthList.printLatestPeriod();
        assertEquals(expected, outContent.toString());
    }

    /**
     * Tests the behaviour of the showPeriodHistory method and whether it prints
     * the period history correctly.
     */
    @Test
    void showPeriodHistory_twoInputs_printCorrectPeriodHistory() throws CustomExceptions.OutOfBounds {
        Period firstPeriod = new Period("10-04-2023", "16-04-2023");
        Period secondPeriod = new Period("09-05-2023", "16-05-2023");

        String expected = "Your Period history:"
                + System.lineSeparator()
                + "1. Period Start: "
                + secondPeriod.getStartDate()
                + " Period End: "
                + secondPeriod.getEndDate()
                + System.lineSeparator()
                + "Period Length: "
                + secondPeriod.getPeriodLength()
                + " day(s)"
                + System.lineSeparator()
                + "2. Period Start: "
                + firstPeriod.getStartDate()
                + " Period End: "
                + firstPeriod.getEndDate()
                + System.lineSeparator()
                + "Period Length: "
                + firstPeriod.getPeriodLength()
                + " day(s)"
                + System.lineSeparator()
                + "Cycle Length: "
                + firstPeriod.getCycleLength()
                + " day(s)"
                + System.lineSeparator();

        HealthList.printPeriodHistory();
        assertEquals(expected, outContent.toString());
    }

    /**
     * Tests deleting of periods with valid list and valid index.
     * Expected behaviour is to have one periods entry left in the list.
     *
     * @throws CustomExceptions.OutOfBounds If the index is invalid.
     */
    @Test
    void deletePeriod_properList_listOfSizeOne() throws CustomExceptions.OutOfBounds {
        new Period("10-04-2024", "16-04-2024");
        new Period("09-05-2024", "16-05-2024");

        int index = 1;
        HealthList.deletePeriod(index);
        assertEquals(1, HealthList.getPeriodsSize());
    }

    /**
     * Tests deleting of period with empty list.
     * Expected behaviour is for an AssertionError to be thrown.
     */
    @Test
    void deletePeriod_emptyList_throwsCustomExceptions() {
        assertThrows(CustomExceptions.OutOfBounds.class, () ->
                HealthList.deletePeriod(0));
    }

    /**
     * Tests deleting of period with invalid index.
     * Expected behaviour is for an OutOfBounds error to be thrown.
     */
    @Test
    void deletePeriod_properListInvalidIndex_throwOutOfBoundsForBmi() {
        Period firstPeriod = new Period("10-04-2024", "16-04-2024");

        int invalidIndex = 5;
        assertThrows(CustomExceptions.OutOfBounds.class, () ->
                HealthList.deletePeriod(invalidIndex));
    }
    /**
     * Tests the behaviour of the predictNextPeriodStartDate function and whether it prints
     * correct predicted start date.
     */
    @Test
    void predictNextPeriodStartDate_sufficientInputs_printCorrectPredictedDate() throws CustomExceptions.OutOfBounds{
        Period firstPeriod = new Period("09-12-2023", "16-12-2023");
        Period secondPeriod = new Period("09-01-2024", "16-01-2024");
        Period thirdPeriod = new Period("10-02-2024", "16-02-2024");
        Period fourthPeriod = new Period("09-03-2024", "14-03-2024");

        long expectedCycleLength = (31+ 32 + 28) / HealthConstant.LATEST_THREE_CYCLE_LENGTHS;
        LocalDate expected = fourthPeriod.getStartDate().plusDays(expectedCycleLength);
        LocalDate result = HealthList.predictNextPeriodStartDate();
        assertEquals(expected, result);
    }

    /**
     * Tests the behaviour of the printNextCyclePrediction function and whether it prints
     * the predicted date with period is late message.
     */
    @Test
    void printNextCyclePrediction_afterToday_printPeriodIsLate() {
        LocalDate today = LocalDate.now();
        LocalDate predictedDate = today.minusDays(5);

        String expected = HealthConstant.PREDICTED_START_DATE_MESSAGE
                + predictedDate
                + HealthConstant.PERIOD_IS_LATE
                + "5 "
                + HealthConstant.DAYS_MESSAGE
                + UiConstant.FULL_STOP
                + System.lineSeparator()
                + UiConstant.PARTITION_LINE
                + System.lineSeparator();

        Period.printNextCyclePrediction(predictedDate);
        assertEquals(expected, outContent.toString());
    }

    /**
     * Tests the behaviour of the printNextCyclePrediction function and whether it prints
     * the predicted date and the number of days to predicted date.
     */
    @Test
    void printNextCyclePrediction_beforeToday_printNumberOfDaysToPredictedDate() {
        LocalDate today = LocalDate.now();
        LocalDate predictedDate = today.plusDays(10);

        String expected = HealthConstant.PREDICTED_START_DATE_MESSAGE
                + predictedDate
                + ", in 10 "
                + HealthConstant.DAYS_MESSAGE
                + UiConstant.FULL_STOP
                + System.lineSeparator()
                + UiConstant.PARTITION_LINE
                + System.lineSeparator();

        Period.printNextCyclePrediction(predictedDate);
        assertEquals(expected, outContent.toString());
    }

    /**
     * Tests the behaviour of the printLatestThreeCycles method and whether it prints
     * the latest three period objects only.
     */
    @Test
    void printLatestThreeCycles_fourInputs_printsThreePeriodObjectsOnly() {
        Period firstPeriod = new Period("09-01-2024", "16-01-2024");
        Period secondPeriod = new Period("10-02-2024", "16-02-2024");
        Period thirdPeriod = new Period("09-03-2024", "14-03-2024");
        Period fourthPeriod = new Period("09-04-2024", "16-04-2024");

        String expected = UiConstant.PARTITION_LINE
                + System.lineSeparator()
                + "Period Start: "
                + fourthPeriod.getStartDate()
                + " Period End: "
                + fourthPeriod.getEndDate()
                + System.lineSeparator()
                + "Period Length: "
                + fourthPeriod.getPeriodLength()
                + " day(s)"
                + System.lineSeparator()
                + "Period Start: "
                + thirdPeriod.getStartDate()
                + " Period End: "
                + thirdPeriod.getEndDate()
                + System.lineSeparator()
                + "Period Length: "
                + thirdPeriod.getPeriodLength()
                + " day(s)"
                + System.lineSeparator()
                + "Cycle Length: "
                + thirdPeriod.getCycleLength()
                + " day(s)"
                + System.lineSeparator()
                + "Period Start: "
                + secondPeriod.getStartDate()
                + " Period End: "
                + secondPeriod.getEndDate()
                + System.lineSeparator()
                + "Period Length: "
                + secondPeriod.getPeriodLength()
                + " day(s)"
                + System.lineSeparator()
                + "Cycle Length: "
                + secondPeriod.getCycleLength()
                + " day(s)"
                + System.lineSeparator();

        HealthList.printLatestThreeCycles();
        assertEquals(expected, outContent.toString());
    }

    /**
     * Test getPeriod without of bounds index.
     * Expected behaviour is for null return.
     */
    @Test
    void getPeriod_emptyPeriodList_expectNull() {
        Period period = new Period("09-01-2024", "16-01-2024");
        Period result = HealthList.getPeriod(1);

        assertNull(result);
    }

    /**
     * Test deleting of period with invalid negative index.
     * Expected behaviour is for an OutOfBounds error to be thrown.
     */
    @Test
    void deletePeriod_negativeIndex_throwOutOfBoundsForPeriod() {
        int invalidIndex = -1;
        CustomExceptions.OutOfBounds exception = assertThrows(
                CustomExceptions.OutOfBounds.class,
                () -> HealthList.deletePeriod(invalidIndex)
        );
        String expected = "\u001b[31mOut of Bounds Error: "
                + ErrorConstant.PERIOD_EMPTY_ERROR
                + "\u001b[0m";
        assertEquals(expected, exception.getMessage());
    }

    /**
     * Test prediction of period with empty period list.
     * Expected behaviour is for an OutOfBounds error to be thrown.
     */
    @Test
    void predictNextPeriodStartDate_emptyPeriodList_throwOutOfBoundsForPeriod() {
        CustomExceptions.OutOfBounds exception = assertThrows(
                CustomExceptions.OutOfBounds.class,
                HealthList::predictNextPeriodStartDate
        );
        String expected = "\u001b[31mOut of Bounds Error: "
                + ErrorConstant.PERIOD_EMPTY_ERROR
                + "\u001b[0m";
        assertEquals(expected, exception.getMessage());
    }

    /**
     * Test Period constructor without end date.
     * Expected behaviour is to add a Period object without end date.
     */
    @Test
    public void periodConstructor_expectCreatePeriodWithoutEndDate() {
        Period period = new Period("03-04-2024");

        Parser parser = new Parser();
        assertEquals(parser.parseDate("03-04-2024"), period.getStartDate());
        assertNull(period.getEndDate());
        assertEquals(1, period.getPeriodLength());

        String expected = "Period Start: "
                + period.getStartDate()
                + " Period End: NA"
                + System.lineSeparator()
                + "Period Length: "
                + period.getPeriodLength()
                + " day(s)"
                + System.lineSeparator();

        System.out.println(period);
        assertEquals(expected, outContent.toString());
    }

    /**
     * Test update end date of Period object without an end date.
     * Expected behaviour is to update the end date of the Period object.
     */
    @Test
    public void updateEndDate_expectUpdatePeriodWithEndDate () {
        Period period = new Period("03-04-2024");

        Parser parser = new Parser();
        assertEquals(parser.parseDate("03-04-2024"), period.getStartDate());
        assertNull(period.getEndDate());
        assertEquals(1, period.getPeriodLength());

        period.updateEndDate("05-04-2024");
        assertEquals(parser.parseDate("05-04-2024"), period.getEndDate());

        String expected = "Period Start: "
                + period.getStartDate()
                + " Period End: "
                + period.getEndDate()
                + System.lineSeparator()
                + "Period Length: "
                + period.getPeriodLength()
                + " day(s)"
                + System.lineSeparator();

        System.out.println(period);
        assertEquals(expected, outContent.toString());
    }

    /**
     * Test calculation of length of the period in days when end date is null.
     * Expected behaviour is 0 return.
     */
    @Test
    void calculatePeriodLength_nullEndDate_expectZeroReturn() {
        Period period = new Period("03-04-2024");
        assertEquals(0, period.calculatePeriodLength());
    }


}
