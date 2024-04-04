package ui;

import health.Appointment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import constants.ErrorConstant;
import constants.UiConstant;
import utility.CustomExceptions;
import constants.WorkoutConstant;
import workouts.Gym;
import workouts.Run;
import workouts.WorkoutList;
import health.Bmi;
import health.Period;
import health.HealthList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class OutputTest {


    private static final ByteArrayInputStream inContent = new ByteArrayInputStream("".getBytes());
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static final InputStream originalIn = System.in;
    private static final PrintStream originalOut = System.out;
    private static final PrintStream originalErr = System.err;

    @BeforeAll
    public static void setUpStreams() {
        System.setIn(inContent);
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void cleanup() {
        WorkoutList.clearWorkoutsRunGym();
        HealthList.clearHealthLists();
        outContent.reset();
        Handler.destroyScanner();
        if (Handler.in == null) {
            return;
        }
        assert HandlerTest.isScannerClosed(Handler.in) : "Scanner is not closed";
    }

    @AfterAll
    public static void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        System.setIn(originalIn);
    }

    /**
     * Tests the behaviour of the printHistory function for Run objects.
     *
     * @throws CustomExceptions.InvalidInput If there are invalid parameters specified.
     */
    @Test
    void printHistory_runsOnly_expectAllRunsPrinted() throws CustomExceptions.InvalidInput {
        Run run1 = new Run("40:10", "10.3", "15-03-2024");
        Run run2 = new Run("01:59:10", "15.3");
        String expected = UiConstant.PARTITION_LINE +
                System.lineSeparator() +
                "Your run history:" +
                System.lineSeparator() +
                String.format(WorkoutConstant.RUN_HEADER_INDEX_FORMAT) +
                System.lineSeparator() +
                String.format(WorkoutConstant.RUN_DATA_INDEX_FORMAT, 1, run1) +
                System.lineSeparator() +
                String.format(WorkoutConstant.RUN_DATA_INDEX_FORMAT, 2, run2) +
                System.lineSeparator() +
                UiConstant.PARTITION_LINE +
                System.lineSeparator();
        Output.printHistory(WorkoutConstant.RUN);
        assertEquals(expected, outContent.toString());
    }

    /**
     * Tests the behaviour of the printHistory function when an invalid filter is used.
     */
    @Test
    void printHistory_invalidHistoryFilter_throwError() {
        String input = "invalidFilter";
        assertThrows(IllegalArgumentException.class, () -> Output.printHistory(input));
    }

    /**
     * Tests the behaviour of the printLatestRun function after a Run object is added.
     *
     * @throws CustomExceptions.InvalidInput If there are invalid parameters specified.
     */
    @Test
    void printLatestRun_oneRun_expectOneRunPrinted() throws CustomExceptions.InvalidInput {
        Run newRun = new Run("40:10", "10.3");
        String expected = UiConstant.PARTITION_LINE +
                System.lineSeparator() +
                "Your latest run:" +
                System.lineSeparator() +
                String.format(WorkoutConstant.RUN_HEADER_INDEX_FORMAT) +
                System.lineSeparator() +
                String.format(WorkoutConstant.RUN_DATA_INDEX_FORMAT, 1, newRun) +
                System.lineSeparator() +
                UiConstant.PARTITION_LINE +
                System.lineSeparator();
        Output.printLatestRun();
        assertEquals(expected, outContent.toString());
    }

    /**
     * Tests the behaviour of the printLatestRun function when no Runs are added.
     */
    @Test
    void printLatestRun_noRun_expectNoRunMessage() {
        String expected = UiConstant.PARTITION_LINE +
                System.lineSeparator() +
                "\u001B[31mError: " +
                ErrorConstant.HISTORY_RUN_EMPTY_ERROR +
                "\u001B[0m" +
                System.lineSeparator() +
                UiConstant.PARTITION_LINE +
                System.lineSeparator();
        Output.printLatestRun();
        assertEquals(expected, outContent.toString());
    }

    /**
     * Tests the behaviour of the printLatestGym function when two Gyms are added.
     */
    @Test
    void printLatestGym_twoGyms_expectOneGymPrinted() {
        try{
            Gym gym1 = new Gym();
            ArrayList<Integer> array1 = new ArrayList<>(Arrays.asList(1));
            ArrayList<Integer> array2 = new ArrayList<>(Arrays.asList(1,2));

            gym1.addStation("Bench Press", 1, 10, array1);
            gym1.addStation("Shoulder Press", 2, 10, array2);

            Gym gym2 = new Gym();
            gym2.addStation("Squat Press", 1, 50, array1);
            gym2.addStation("Lat Press", 2, 10, array2);


            String expected = UiConstant.PARTITION_LINE +
                    System.lineSeparator() +
                    "Your latest gym:" +
                    System.lineSeparator() +
                    "Gym Session 2 (Date: NA)" +
                    System.lineSeparator() +
                    String.format(WorkoutConstant.GYM_STATION_FORMAT, "Station 1 Squat Press") +
                    String.format(WorkoutConstant.INDIVIDUAL_GYM_STATION_FORMAT, 1) +
                    System.lineSeparator() +
                    String.format(WorkoutConstant.GYM_SET_INDEX_FORMAT, 1, "50 reps at 1 KG") +
                    System.lineSeparator() +
                    String.format(WorkoutConstant.GYM_STATION_FORMAT, "Station 2 Lat Press") +
                    String.format(WorkoutConstant.INDIVIDUAL_GYM_STATION_FORMAT, 2) +
                    System.lineSeparator() +
                    String.format(WorkoutConstant.GYM_SET_INDEX_FORMAT, 1, "10 reps at 1 KG") +
                    System.lineSeparator() +
                    String.format(WorkoutConstant.GYM_SET_INDEX_FORMAT, 2, "10 reps at 2 KG") +
                    System.lineSeparator() +
                    UiConstant.PARTITION_LINE +
                    System.lineSeparator();

            Output.printLatestGym();

            assertEquals(expected, outContent.toString());


        }  catch (CustomExceptions.InvalidInput e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tests the behaviour of the printLatestGym function when no Gyms are added.
     */
    @Test
    void printLatestGym_noGym_expectNoGymMessage() {
        String expected = UiConstant.PARTITION_LINE +
                System.lineSeparator() +
                "\u001B[31mError: " +
                ErrorConstant.HISTORY_GYM_EMPTY_ERROR +
                "\u001B[0m" +
                System.lineSeparator() +
                UiConstant.PARTITION_LINE +
                System.lineSeparator();
        Output.printLatestGym();
        assertEquals(expected, outContent.toString());
        cleanup();
    }

    /**
     * Tests the behaviour of the printLatestBmi function when two Bmi objects are added.
     */
    @Test
    void printLatestBmi_twoBmis_expectOneBmiPrinted() {
        Bmi firstBmi = new Bmi("1.75", "70.0", "18-03-2024");
        Bmi secondBmi = new Bmi("1.55", "55.0", "20-03-2024");
        HealthList.addBmi(firstBmi);
        HealthList.addBmi(secondBmi);
        Output.printLatestBmi();
        String expected = UiConstant.PARTITION_LINE +
                System.lineSeparator() +
                "2024-03-20" +
                System.lineSeparator()+
                "Your BMI is 22.89" +
                System.lineSeparator() +
                "Great! You're within normal range." +
                System.lineSeparator() +
                UiConstant.PARTITION_LINE +
                System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    /**
     * Tests the behaviour of the printLatestBmi function when two Period objects are added.
     */
    @Test
    void printLatestPeriod_twoPeriods_expectOnePeriodPrinted() {
        Period firstPeriod = new Period("09-02-2023", "16-02-2023");
        Period secondPeriod = new Period("09-03-2023", "16-03-2023");
        HealthList.addPeriod(firstPeriod);
        HealthList.addPeriod(secondPeriod);
        Output.printLatestPeriod();
        String expected = UiConstant.PARTITION_LINE +
                System.lineSeparator() +
                "Period Start: 2023-03-09 Period End: 2023-03-16" +
                System.lineSeparator() +
                "Period Length: 8 days" +
                System.lineSeparator() +
                UiConstant.PARTITION_LINE +
                System.lineSeparator();

        assertEquals(expected, outContent.toString());
    }

    /**
     * Tests the behaviour of the printLatestAppointment function when two Appointment objects are added.
     */
    @Test
    void printLatestAppointment_twoAppointments_expectOneAppointmentPrinted() {
        Appointment firstAppointment = new Appointment("29-03-2024", "17:00", "test");
        Appointment secondAppointment = new Appointment("24-01-2026", "12:00", "test2");
        HealthList.addAppointment(firstAppointment);
        HealthList.addAppointment(secondAppointment);

        Output.printLatestAppointment();
        String expected = UiConstant.PARTITION_LINE +
                System.lineSeparator() +
                "On 2026-01-24 at 12:00: test2" +
                System.lineSeparator() +
                UiConstant.PARTITION_LINE +
                System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    /**
     * Tests the behaviour of printAppointmentHistory when two Appointment objects are added.
     * Expects two Appointment objects to be pritned.
     *
     * @throws CustomExceptions.OutOfBounds If there is out of bounds access.
     * @throws CustomExceptions.InvalidInput If there is invalid input.
     */
    @Test
    void printAppointmentHistory_twoAppointments_expectTwoAppointmentsPrinted() throws
            CustomExceptions.OutOfBounds, CustomExceptions.InvalidInput {
        Appointment firstAppointment = new Appointment("29-03-2024", "17:00", "test");
        Appointment secondAppointment = new Appointment("24-01-2026", "12:00", "test2");
        HealthList.addAppointment(firstAppointment);
        HealthList.addAppointment(secondAppointment);
        Output.printAppointmentHistory();
        String expected = UiConstant.PARTITION_LINE +
                System.lineSeparator() +
                "Your Appointment history:" +
                System.lineSeparator() +
                "1. On 2024-03-29 at 17:00: test" +
                System.lineSeparator() +
                "2. On 2026-01-24 at 12:00: test2" +
                System.lineSeparator() +
                UiConstant.PARTITION_LINE +
                System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    /**
     * Tests the behaviour of the printPeriodHistory function when two Period objects are added.
     */
    @Test
    void printPeriodHistory_twoPeriods_expectTwoPeriodsPrinted() throws
            CustomExceptions.OutOfBounds, CustomExceptions.InvalidInput {
        Period firstPeriod = new Period("09-02-2023", "16-02-2023");
        Period secondPeriod = new Period("09-03-2023", "16-03-2023");
        HealthList.addPeriod(firstPeriod);
        HealthList.addPeriod(secondPeriod);
        Output.printPeriodHistory();
        String expected = UiConstant.PARTITION_LINE +
                System.lineSeparator() +
                "Your Period history:" +
                System.lineSeparator() +
                "Period Start: 2023-02-09 Period End: 2023-02-16" +
                System.lineSeparator() +
                "Period Length: 8 days" +
                System.lineSeparator() +
                "Cycle Length: 28 days" +
                System.lineSeparator() +
                "Period Start: 2023-03-09 Period End: 2023-03-16" +
                System.lineSeparator() +
                "Period Length: 8 days" +
                System.lineSeparator() +
                UiConstant.PARTITION_LINE +
                System.lineSeparator();

        assertEquals(expected, outContent.toString());
    }

    /**
     * Tests the behaviour of the printLatestBmi function when two Bmi objects are added.
     */
    @Test
    void printBmiHistory_twoBmis_expectTwoBmisPrinted() throws CustomExceptions.OutOfBounds,
            CustomExceptions.InvalidInput {
        Bmi firstBmi = new Bmi("1.75", "70.0", "18-03-2024");
        Bmi secondBmi = new Bmi("1.55", "55.0", "20-03-2024");
        HealthList.addBmi(firstBmi);
        HealthList.addBmi(secondBmi);
        Output.printBmiHistory();
        String expected = UiConstant.PARTITION_LINE +
                System.lineSeparator() +
                "Your BMI history:" +
                System.lineSeparator() +
                "2024-03-18" +
                System.lineSeparator() +
                "Your BMI is 22.86" +
                System.lineSeparator() +
                "Great! You're within normal range." +
                System.lineSeparator() +
                "2024-03-20" +
                System.lineSeparator() +
                "Your BMI is 22.89" +
                System.lineSeparator() +
                "Great! You're within normal range." +
                System.lineSeparator() +
                UiConstant.PARTITION_LINE +
                System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }


    /**
     * Tests the behaviour of the printGymHistory function, which should print both Gyms
     * added.
     */
    @Test
    void printGymHistory_correctInput_expectPrintGymHistory(){
        try{

            ArrayList<Integer> array1 = new ArrayList<>(Arrays.asList(1));
            ArrayList<Integer> array2 = new ArrayList<>(Arrays.asList(1,2));

            Gym gym1 = new Gym();
            gym1.addStation("Bench Press", 1, 50, array1);
            gym1.addStation("Shoulder Press", 2, 10, array2);

            Gym gym2 = new Gym();
            gym2.addStation("Squat Press", 1, 50, array1);
            gym2.addStation("Lat Press", 2, 10, array2);

            String expected = UiConstant.PARTITION_LINE +
                    System.lineSeparator() +
                    "Your gym history:" +
                    System.lineSeparator() +
                    "Gym Session 1 (Date: NA)"+
                    System.lineSeparator() +
                    String.format(WorkoutConstant.GYM_STATION_FORMAT, "Station 1 Bench Press") +
                    String.format(WorkoutConstant.INDIVIDUAL_GYM_STATION_FORMAT, 1) +
                    System.lineSeparator() +
                    String.format(WorkoutConstant.GYM_SET_INDEX_FORMAT, 1, "50 reps at 1 KG") +
                    System.lineSeparator() +
                    String.format(WorkoutConstant.GYM_STATION_FORMAT, "Station 2 Shoulder Press") +
                    String.format(WorkoutConstant.INDIVIDUAL_GYM_STATION_FORMAT, 2) +
                    System.lineSeparator() +
                    String.format(WorkoutConstant.GYM_SET_INDEX_FORMAT, 1, "10 reps at 1 KG") +
                    System.lineSeparator() +
                    String.format(WorkoutConstant.GYM_SET_INDEX_FORMAT, 2, "10 reps at 2 KG") +
                    System.lineSeparator() +
                    UiConstant.PARTITION_LINE +
                    System.lineSeparator() +
                    "Gym Session 2 (Date: NA)" +
                    System.lineSeparator() +
                    String.format(WorkoutConstant.GYM_STATION_FORMAT, "Station 1 Squat Press") +
                    String.format(WorkoutConstant.INDIVIDUAL_GYM_STATION_FORMAT, 1) +
                    System.lineSeparator() +
                    String.format(WorkoutConstant.GYM_SET_INDEX_FORMAT, 1, "50 reps at 1 KG") +
                    System.lineSeparator() +
                    String.format(WorkoutConstant.GYM_STATION_FORMAT, "Station 2 Lat Press") +
                    String.format(WorkoutConstant.INDIVIDUAL_GYM_STATION_FORMAT, 2) +
                    System.lineSeparator() +
                    String.format(WorkoutConstant.GYM_SET_INDEX_FORMAT, 1, "10 reps at 1 KG") +
                    System.lineSeparator() +
                    String.format(WorkoutConstant.GYM_SET_INDEX_FORMAT, 2, "10 reps at 2 KG") +
                    System.lineSeparator() +
                    UiConstant.PARTITION_LINE +
                    System.lineSeparator();
            Output.printHistory(WorkoutConstant.GYM);
            assertEquals(expected, outContent.toString());
        }  catch (CustomExceptions.InvalidInput e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Test the behaviour of the printRunHistory function, which should print both Runs and Gyms
     */
    @Test
    void printWorkoutHistory() {
        try {
            Run run1 = new Run("11:11:12", "10.24", "19-12-1923");
            Gym gym1 = new Gym("11-11-1992");
            gym1.addStation("Bench Press", 2, 4, new ArrayList<>(Arrays.asList(10,20)));
            gym1.addStation("Squat Press", 2, 4, new ArrayList<>(Arrays.asList(100,200)));

            String expectedRun1 = String.format(WorkoutConstant.HISTORY_WORKOUTS_DATA_FORMAT,
                    WorkoutConstant.RUN,
                    "1923-12-19",
                    "10.24",
                    "11:11:12",
                    "65:33/km",
                    UiConstant.DASH,
                    UiConstant.DASH,
                    UiConstant.DASH,
                    UiConstant.DASH
                    );


            String expectedGym1Set1 = String.format(WorkoutConstant.HISTORY_WORKOUTS_DATA_FORMAT,
                    WorkoutConstant.GYM,
                    "1992-11-11",
                    UiConstant.DASH,
                    UiConstant.DASH,
                    UiConstant.DASH,
                    "Bench Press",
                    "2",
                    "4,4",
                    "10,20"
            );

            String expectedGym1Set2 = String.format(WorkoutConstant.HISTORY_WORKOUTS_DATA_FORMAT,
                    UiConstant.EMPTY_STRING,
                    UiConstant.EMPTY_STRING,
                    UiConstant.EMPTY_STRING,
                    UiConstant.EMPTY_STRING,
                    UiConstant.EMPTY_STRING,
                    "Squat Press",
                    "2",
                    "4,4",
                    "100,200"
            );

            String expected2 = String.format(
                    WorkoutConstant.HISTORY_WORKOUTS_DATA_HEADER_FORMAT, "2", expectedGym1Set1)
                    + System.lineSeparator() +
                    String.format(WorkoutConstant.HISTORY_WORKOUTS_DATA_HEADER_FORMAT, "", expectedGym1Set2);


            String expected1 = String.format(WorkoutConstant.HISTORY_WORKOUTS_DATA_HEADER_FORMAT, "1", expectedRun1);

            String expected = UiConstant.PARTITION_LINE + System.lineSeparator()
                    + WorkoutConstant.HISTORY_WORKOUTS_HEADER + System.lineSeparator()
                    + WorkoutConstant.HISTORY_WORKOUTS_HEADER_FORMAT + System.lineSeparator()
                    + expected1 + System.lineSeparator()
                    + expected2 + System.lineSeparator()
                    + UiConstant.PARTITION_LINE + System.lineSeparator();

            Output.printHistory(WorkoutConstant.ALL);
            assertEquals(expected, outContent.toString());
        } catch (CustomExceptions.InvalidInput e) {
            fail("Shouldn't have failed");
        }

    }

}
