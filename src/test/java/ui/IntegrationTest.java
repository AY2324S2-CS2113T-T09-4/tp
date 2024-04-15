package ui;

import constants.ErrorConstant;
import constants.HealthConstant;
import constants.WorkoutConstant;
import health.HealthList;
import health.Period;
import utility.CustomExceptions;
import utility.Parser;
import workouts.Gym;
import workouts.Run;
import workouts.WorkoutLists;
import helper.TestHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class IntegrationTest {


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
    public void tearDown(){
        WorkoutLists.clearWorkoutsRunGym();
        HealthList.clearHealthLists();
        outContent.reset();
        errContent.reset();
    }

    @AfterAll
    public static void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        System.setIn(originalIn);
    }

    /**
     * Integration testing for adding a Bmi and Show latest when error is present
     */
    @Test
    void addBmiAndShowLatest_incorrectInput_expectErrors(){
        // Craft the input String to be passed to handler
        StringBuilder inputString = new StringBuilder();
        StringBuilder expectedString = new StringBuilder();

        // test to see if incorrect health input can be captured
        inputString.append("health /h:bmiiii /height:1.75 /weight:70.00000 /date:18-03-2024");
        inputString.append(System.lineSeparator());
        expectedString.append(TestHelper.errorInvalidCommandString(ErrorConstant.INVALID_HEALTH_INPUT_ERROR));

        inputString.append("health /h: /height:1.75 /weight:70.00 /date:22.11.2001");
        inputString.append(System.lineSeparator());
        expectedString.append(TestHelper.errorInvalidCommandString(ErrorConstant.INVALID_HEALTH_INPUT_ERROR));

        inputString.append("health /h:123123 /height:1.75 /weight:70.00 /date:22.11.2001");
        inputString.append(System.lineSeparator());
        expectedString.append(TestHelper.errorInvalidCommandString(ErrorConstant.INVALID_HEALTH_INPUT_ERROR));

        // test to see if incorrect command can be captured
        inputString.append("healthsssss /h:bmiiii /height:1.75 /weight:70.00000 /date:18-03-2024");
        inputString.append(System.lineSeparator());
        expectedString.append(TestHelper.errorInvalidCommandString(ErrorConstant.INVALID_COMMAND_ERROR));




        // test to see if incorrect slashes can be captured
        inputString.append("health /h:bmi /height:1.750000 /////weight:70.00 /date:18-03-2024");
        inputString.append(System.lineSeparator());
        expectedString.append(TestHelper.errorInvalidInputString(ErrorConstant.TOO_MANY_SLASHES_ERROR));

        // test to see if incorrect height can be captured
        inputString.append("health /h:bmi /height:1.750000 /weight:70.00 /date:18-03-2024");
        inputString.append(System.lineSeparator());
        expectedString.append(TestHelper.errorInvalidInputString(ErrorConstant.INVALID_HEIGHT_WEIGHT_INPUT_ERROR));

        // test to see if incorrect weight can be captured
        inputString.append("health /h:bmi /height:1.75 /weight:70.00000 /date:18-03-2024");
        inputString.append(System.lineSeparator());
        expectedString.append(TestHelper.errorInvalidInputString(ErrorConstant.INVALID_HEIGHT_WEIGHT_INPUT_ERROR));

        // test to see if date error can be captured (invalid date)
        inputString.append("health /h:bmi /height:1.75 /weight:70.00 /date:00-00-0000");
        inputString.append(System.lineSeparator());
        expectedString.append(TestHelper.errorInvalidInputString(ErrorConstant.INVALID_YEAR_ERROR));

        // test to see if date error can be captured (invalid date)
        inputString.append("health /h:bmi /height:1.75 /weight:70.00 /date:30-00-0000");
        inputString.append(System.lineSeparator());
        expectedString.append(TestHelper.errorInvalidInputString(ErrorConstant.INVALID_YEAR_ERROR));

        // test to see if date error can be captured (invalid date)
        inputString.append("health /h:bmi /height:1.75 /weight:70.00 /date:30-13-2000");
        inputString.append(System.lineSeparator());
        expectedString.append(TestHelper.errorInvalidInputString(ErrorConstant.INVALID_DATE_ERROR));

        // test to see if date error can be captured (invalid date)
        inputString.append("health /h:bmi /height:1.75 /weight:70.00 /date:33-11-2001");
        inputString.append(System.lineSeparator());
        expectedString.append(TestHelper.errorInvalidInputString(ErrorConstant.INVALID_DATE_ERROR));

        // test to see if incorrect date can be captured
        inputString.append("health /h:bmi /height:1.75 /weight:70.00 /date:22.11.2001");
        inputString.append(System.lineSeparator());
        expectedString.append(TestHelper.errorInvalidInputString(ErrorConstant.INVALID_DATE_ERROR));



        inputString.append("health /h:bmi /height:1.75000 /weight:70.00 /date:18-03-2024");
        inputString.append(System.lineSeparator());
        expectedString.append(TestHelper.errorInvalidInputString(ErrorConstant.INVALID_HEIGHT_WEIGHT_INPUT_ERROR));

        inputString.append("health /h:bmi /height:1.75 /weight:70.00000 /date:18-03-2024");
        inputString.append(System.lineSeparator());
        expectedString.append(TestHelper.errorInvalidInputString(ErrorConstant.INVALID_HEIGHT_WEIGHT_INPUT_ERROR));


        inputString.append("latest /item:invalidFlag");
        inputString.append(System.lineSeparator());
        expectedString.append(TestHelper.errorInvalidInputString(ErrorConstant.INVALID_LATEST_OR_DELETE_FILTER));

        inputString.append("latest /////item:");
        inputString.append(System.lineSeparator());
        expectedString.append(TestHelper.errorInvalidInputString(ErrorConstant.TOO_MANY_SLASHES_ERROR));

        inputString.append("latest /item:");
        inputString.append(System.lineSeparator());
        expectedString.append(TestHelper.errorInsufficientInput(ErrorConstant.INSUFFICIENT_LATEST_FILTER_ERROR));

        inputString.append("latest");
        inputString.append(System.lineSeparator());
        expectedString.append(TestHelper.errorInsufficientInput(ErrorConstant.INSUFFICIENT_LATEST_FILTER_ERROR));

        // Run the process to test the output
        Handler handler= new Handler(inputString.toString());
        handler.processInput();
        assertEquals(expectedString.toString(), errContent.toString());
        outContent.reset();
    }


    /**
     * Test the behaviour of print latest when given the filter period/bmi/appointment
     * This is the flow of sequence
     * 1. Add a bmi (bmi1)
     * 2. Print latest bmi (prints bmi1)
     * 3. Add a bmi (bmi2)
     * 4. Print latest bmi (prints bmi2) - latest bmi should be bmi2
     * 5. Add a period (period1)
     * 6. Print latest period (prints period1)
     * 7. Add a period (period2)
     * 8. Print latest period (prints period2) - latest period should be period2
     * 9. Add an appointment (appointment1)
     * 10. Print latest appointment (prints appointment1)
     * 11. Add an appointment (appointment2) that is earlier than appointment1
     * 12. Print latest appointment (prints appointment2) - latest appointment should be appointment1 still
     * 13. Add an appointment (appointment3) that is the latest (2026)
     * 14. Print latest appointment (prints appointment2) - latest appointment should be appointment 3
     */
    @Test
    void addHealthAndShowLatest_correctInput_expectCorrectLatestOutput() {

        // Craft the input String to be passed to handler
        String inputString = "health /h:bmi /height:1.75 /weight:70.00 /date:18-03-2024" +
                System.lineSeparator() +
                "latest /item:bmi" +
                System.lineSeparator() +
                "health /h:bmi /height: 2.00 /weight:40.00 /date:20-03-2024" +
                System.lineSeparator() +
                "latest /item:bmi" +
                System.lineSeparator() +
                "health /h:period /start:18-12-2023 /end:26-12-2023" +
                System.lineSeparator() +
                "latest /item:period" +
                System.lineSeparator() +
                "health /h:period /start:27-01-2024 /end:03-03-2024" +
                System.lineSeparator() +
                "latest /item:period" +
                System.lineSeparator() +
                "health /h:appointment /date:29-04-2025 /time:12:00 /description:knee surgery" +
                System.lineSeparator() +
                "latest /item:appointment" +
                System.lineSeparator() +
                "health /h:appointment /date:25-03-2024 /time:23:01 /description:knee surgery 2" +
                System.lineSeparator() +
                "latest /item:appointment" +
                System.lineSeparator() +
                "health /h:appointment /date:25-03-2026 /time:10:01 /description:plastic surgery" +
                System.lineSeparator() +
                "latest /item:appointment";


        // Craft the expected String to be printed
        StringBuilder expectedString = new StringBuilder();

        String addBmiString = TestHelper.addBmiOutputString("70.00",
                "1.75",
                "2024-03-18",
                22.86,
                HealthConstant.NORMAL_WEIGHT_MESSAGE);

        String latestBmiString = TestHelper.latestBmiOutputString("2024-03-18",
                22.86,
                HealthConstant.NORMAL_WEIGHT_MESSAGE);


        String addBmiString2 = TestHelper.addBmiOutputString("40.00",
                "2.00",
                "2024-03-20",
                10.00,
                HealthConstant.UNDERWEIGHT_MESSAGE);

        String latestBmiString2 = TestHelper.latestBmiOutputString("2024-03-20",
                10.00,
                HealthConstant.UNDERWEIGHT_MESSAGE);

        String addPeriodString = TestHelper.addPeriodOutputString("2023-12-18",
                "2023-12-26",
                9
        );

        String latestPeriodString = TestHelper.latestPeriodOutputString("2023-12-18",
                "2023-12-26",
                9,
                HealthConstant.DAYS_MESSAGE);

        String addPeriodString2 = TestHelper.addPeriodOutputString("2024-01-27",
                "2024-03-03",
                37
        );

        String latestPeriodString2 = TestHelper.latestPeriodOutputString("2024-01-27",
                "2024-03-03",
                37,
                HealthConstant.DAYS_MESSAGE);

        String addAppointmentString = TestHelper.addAppointmentString("2025-04-29",
                "12:00",
                "knee surgery");

        String latestAppointmentString = TestHelper.latestAppointmentOutputString("2025-04-29",
                "12:00",
                "knee surgery");

        String addAppointmentString2 = TestHelper.addAppointmentString("2024-03-25",
                "23:01",
                "knee surgery 2");

        TestHelper.latestAppointmentOutputString("2024-03-25",
                "23:01",
                "knee surgery 2");

        String addAppointmentString3 = TestHelper.addAppointmentString("2026-03-25",
                "10:01",
                "plastic surgery");

        String latestAppointmentString3 = TestHelper.latestAppointmentOutputString("2026-03-25",
                "10:01",
                "plastic surgery");


        expectedString.append(addBmiString);
        expectedString.append(latestBmiString);
        expectedString.append(addBmiString2);
        expectedString.append(latestBmiString2);
        expectedString.append(addPeriodString);
        expectedString.append(latestPeriodString);
        expectedString.append(addPeriodString2);
        expectedString.append(latestPeriodString2);
        expectedString.append(addAppointmentString);
        expectedString.append(latestAppointmentString);
        expectedString.append(addAppointmentString2);
        expectedString.append(latestAppointmentString); // the latest appointment is earlier so it should print first
        expectedString.append(addAppointmentString3);
        expectedString.append(latestAppointmentString3); // the latest appointment is earlier then appointment3

        // Run the process to test the output
        Handler handler= new Handler(inputString);
        handler.processInput();
        assertEquals(expectedString.toString(), outContent.toString());
        outContent.reset();

    }


    /**
     * Tests the behaviour of having the same expected output when saving and loading a Gym object.
     * This is tested by ensuring the print history of the Gym object is the same before and after saving and loading.
     */
    @Test
    void testSaveAndLoadGym_gymObjectInput_expectSamePrintHistory(){
        Gym newGym = new Gym();
        try {

            newGym.addStation("ExerciseA", "1", "10", "1.0");
            newGym.addStation("ExerciseB", "2", "20" , "1.0,2.0");

            // Save the expected output
            Output output = new Output();
            output.printAddGym(newGym);
            String expectedString = outContent.toString();

            // Save the string, clear the static list, and then simulate load
            String saveString = newGym.toFileString();
            tearDown();
            Parser parser = new Parser();
            Gym loadedGym = parser.parseGymFileInput(saveString);
            output.printAddGym(loadedGym);
            String outputContent = outContent.toString();

            // Expect the same value
            assertEquals(expectedString, outputContent);


        } catch (CustomExceptions.InvalidInput | CustomExceptions.FileReadError | CustomExceptions.InsufficientInput e){
            fail("Should not throw an exception");
        }

    }

    /**
     * Tests if the output of the bot when adding runs and gyms, using history and latest commands is correct.
     * Two gyms and runs are added, followed by the history and latest commands to view it.
     */
    @Test
    void testLatestDisplay_userInputsTwoGymAndRuns_expectsLatestGymAndRun(){
        String run1 = "WORKOUT /e:run /d:10.30 /t:40:10 /date:15-03-2024";
        String run2 = "WORKOUT /e:run /d:11.59 /t:30:10 /date:17-03-2024";
        String gym1 = "WORKOUT /e:gym /n:2 /date:18-03-2024";
        String gym1Station1 = "benchpress /s:2 /r:4 /w:40,60";
        String gym1Station2 = "squats /s:3 /r:4 /w:10,20,30";
        String gym2 = "WORKOUT /e:gym /n:1 /date:22-03-2024";
        String gym2Station1 = "deadlift /s:4 /r:4 /w:120,130,140,160";
        String showLatestGym = "LATEST /item:gym";
        String showLatestRun = "LATEST /item:run";
        String showHistoryGym = "HISTORY /item:gym";
        String showHistoryRun = "HISTORY /item:run";
        String showHistoryAll = "HISTORY /item:workouts";


        String inputString = run1 +System.lineSeparator() 
                + run2 +System.lineSeparator() 
                + gym1 + System.lineSeparator() 
                + gym1Station1 + System.lineSeparator() 
                + gym1Station2 + System.lineSeparator() 
                + gym2 + System.lineSeparator() 
                + gym2Station1 + System.lineSeparator() 
                + showLatestGym + System.lineSeparator() 
                + showLatestRun + System.lineSeparator() 
                + showHistoryGym + System.lineSeparator() 
                + showHistoryRun + System.lineSeparator() 
                + showHistoryAll + System.lineSeparator();

        Handler newHandler = new Handler(inputString);
        newHandler.processInput();
        String result = outContent.toString();
        tearDown();

        // Craft expected output
        try{
            Run run1Expected = new Run("40:10", "10.30", "15-03-2024");
            Run run2Expected = new Run("30:10", "11.59", "17-03-2024");

            Gym gym1expected = new Gym("18-03-2024");
            gym1expected.addStation("benchpress", "2", "4", "40.0,60.0");
            gym1expected.addStation("squats", "3", "4", "10.0,20.0,30.0");

            Gym gym2expected = new Gym("22-03-2024");
            gym2expected.addStation("deadlift",  "4",
                    "4", "120.0,130.0,140.0,160.0");

            Output output = new Output();
            output.printAddRun(run1Expected);
            output.printAddRun(run2Expected);
            output.printGymStationPrompt(1);
            output.printGymStationPrompt(2);
            output.printAddGym(gym1expected);
            output.printGymStationPrompt(1);
            output.printAddGym(gym2expected);
            output.printLatestGym();
            output.printLatestRun();
            output.printHistory(WorkoutConstant.GYM);
            output.printHistory(WorkoutConstant.RUN);
            output.printHistory(WorkoutConstant.ALL);

            String expected = outContent.toString();
            assertEquals(expected, result);

        } catch (CustomExceptions.InvalidInput | CustomExceptions.InsufficientInput e){
            fail("Shouldn't have failed");
        }

        tearDown();
    }

    /**
     * Tests the behaviour of the bot when 4 Period objects are added, expects the four periods to be reflected.
     * accordingly with a valid prediction on when the next cycle is.
     */
    @Test
    void testPrediction_userInputsFourPeriods_expectPrediction() throws CustomExceptions.InsufficientInput
            , CustomExceptions.OutOfBounds {
        String period1 = "health /h:period /start:18-12-2023 /end:26-12-2023";
        String period2 = "health /h:period /start:18-01-2024 /end:26-01-2024";
        String period3 = "health /h:period /start:21-02-2024 /end:28-02-2024";
        String period4 = "health /h:period /start:22-03-2024 /end:29-03-2024";
        String prediction = "health /h:prediction";

        String inputString = period1 + System.lineSeparator()
                + period2 +System.lineSeparator()
                + period3 + System.lineSeparator()
                + period4 + System.lineSeparator()
                + prediction + System.lineSeparator();


        Handler newHandler = new Handler(inputString);
        newHandler.processInput();
        String result = outContent.toString();
        tearDown();

        Output output = new Output();

        Period expectedPeriod1 = new Period("18-12-2023" , "26-12-2023");
        output.printAddPeriod(expectedPeriod1);

        Period expectedPeriod2 = new Period("18-01-2024" , "26-01-2024");
        output.printAddPeriod(expectedPeriod2);

        Period expectedPeriod3 = new Period("21-02-2024", "28-02-2024");
        output.printAddPeriod(expectedPeriod3);

        Period expectedPeriod4 = new Period("22-03-2024", "29-03-2024");
        output.printAddPeriod(expectedPeriod4);

        Parser parser = new Parser();
        parser.parsePredictionInput();

        String expected = outContent.toString();
        assertEquals(expected, result);

    }

    /**
     * Tests the behaviour of the bot when 3 Period objects are added and a prediction is attempted.
     * Expects an exception thrown for prediction since there are insufficient Period objects added.
     */
    @Test
    void testPrediction_userInputsThreePeriods_expectNoPredictionPrintedAndErrorMessagePrinted() {
        String period1 = "health /h:period /start:18-12-2023 /end:26-12-2023";
        String period2 = "health /h:period /start:18-01-2024 /end:26-01-2024";
        String period3 = "health /h:period /start:21-02-2024 /end:28-02-2024";
        String prediction = "health /h:prediction";

        String inputString = period1 +System.lineSeparator()
                + period2 + System.lineSeparator()
                + period3 + System.lineSeparator()
                + prediction + System.lineSeparator();

        Handler newHandler = new Handler(inputString);
        Output output = new Output();
        newHandler.processInput();
        String result = outContent.toString();
        String resultErr = errContent.toString();
        tearDown();

        Period expectedPeriod1 = new Period("18-12-2023" , "26-12-2023");
        output.printAddPeriod(expectedPeriod1);
        Period expectedPeriod2 = new Period("18-01-2024" , "26-01-2024");
        output.printAddPeriod(expectedPeriod2);
        Period expectedPeriod3 = new Period("21-02-2024", "28-02-2024");
        output.printAddPeriod(expectedPeriod3);

        String expected = outContent.toString();
        assertEquals(expected, result);

        // expect error message
        try {
            Parser parser = new Parser();
            parser.parsePredictionInput();
        } catch (CustomExceptions.InsufficientInput | CustomExceptions.OutOfBounds e) {
            output.printException(e.getMessage());
        }
        String expectedErr = errContent.toString();
        assertEquals(expectedErr, resultErr);
    }



}
