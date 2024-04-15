package utility;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import workouts.Gym;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParserTest {
    private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static final PrintStream originalErr = System.err;
    private Parser parser;
    @BeforeAll
    public static void setUpStreams() {
        System.setErr(new PrintStream(errContent));
    }

    @AfterAll
    public static void restoreStreams() {
        System.setErr(originalErr);
    }
    @BeforeEach
    void setUp() {
        parser = new Parser();
    }

    /**
     * Tests the behaviour of the parseDate function with a correctly formatted string.
     */
    @Test
    void parseDate_correctDateInput_returnDate() {
        LocalDate result = parser.parseDate("08-03-2024");
        LocalDate expected = LocalDate.of(2024, 3, 8);
        assertEquals(expected, result);
    }

    /**
     * Tests the behaviour of the parseDate function with an incorrectly formatted string.
     * Expects null to be returned.
     */
    @Test
    void parseDate_incorrectDateInput_returnNull () {
        String input = "2024-03-08";
        LocalDate result = parser.parseDate(input);
        assertNull(result);
    }

    /**
     * Tests the behaviour of parseFormattedDate when valid LocalDate variable is passed.
     * Expects correct string date returned.
     */
    @Test
    void parseFormattedDate_correctDate_returnStringDate() {
        LocalDate date = LocalDate.of(2024, 4, 10);
        String result = parser.parseFormattedDate(date);
        String expected = "10-04-2024";
        assertEquals(expected, result);
    }

    /**
     * Tests the behaviour of parseFormattedDate when null LocalDate variable is passed.
     * Expects "NA" returned
     */
    @Test
    void parseFormattedDate_nullDate_returnNoDateString() {
        String result = parser.parseFormattedDate(null);
        String expected = "NA";
        assertEquals(expected, result);
    }

    /**
     * Tests the behaviour of parseTime when a valid time string is passed.
     * Expects correct LocalTime returned.
     */
    @Test
    void parseTime_validTime_returnCorrectTime() {
        LocalTime result = parser.parseTime("23:34");
        LocalTime expected = LocalTime.of(23, 34);
        assertEquals(expected, result);
    }

    /**
     * Tests the behaviour of parseTime when a valid time string is passed.
     * Expects correct LocalTime returned.
     */
    @Test
    void parseTime_invalidTime_returnCorrectTime() {
        parser.parseTime("60:34");
        String expected = "\u001b[31mException Caught!";
        assertTrue(errContent.toString().contains(expected));
    }


    /**
     * Tests the behaviour of correct parameters being passed to validateDate.
     * Expects the correct details to be returned as a list of strings.
     */
    @Test
    public void splitDeleteInput_correctInput_returnsCorrectDeleteValues() throws CustomExceptions.InsufficientInput,
            CustomExceptions.InvalidInput {
        String input = "/item:appointment /index:1";
        String[] expected = {"appointment", "1"};
        String[] result = parser.splitDeleteInput(input);
        assertArrayEquals(expected, result);
    }

    /**
     * Tests the behaviour of insufficient parameters being passed to validateDate.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    public void splitDeleteInput_missingParameter_throwsInsufficientParameterException() {
        String input = "/item:appointment";
        assertThrows(CustomExceptions.InsufficientInput.class, () -> parser.splitDeleteInput(input));
    }

    /**
     * Tests the behaviour of splitDeleteInput when too many forward slashes have been specified.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    public void splitDeleteInput_tooManyForwardSlashes_throwsInvalidInputException() {
        String input = "/item:appointment /index:1/";
        assertThrows(CustomExceptions.InvalidInput.class, () -> parser.splitDeleteInput(input));
    }

    /**
     * Tests the behaviour of parseDeleteInput when correct parameters are passed.
     * Expects correct parameters returned.
     */
    @Test
    public void parseDeleteInput_validParameters_expectValidDetailsReturned() {
        String input = "/item:appointment /index:1";
        String[] expected = {"appointment", "1"};
        String[] result = parser.parseDeleteInput(input);
        assertArrayEquals(expected, result);
    }


    //@@author j013n3
    /**
     * Tests the behaviour of a correctly formatted user input being passed into splitBmiInput.
     * Expects no exception to be thrown.
     */
    @Test
    void splitBmiInput_correctInput_returnsCorrectBmiValues() throws CustomExceptions.InsufficientInput,
            CustomExceptions.InvalidInput {
        String input = "/h:bmi /height:1.71 /weight:60.50 /date:19-03-2024";
        String[] expected = {"1.71", "60.50", "19-03-2024"};
        String[] result = parser.splitBmiInput(input);
        assertArrayEquals(expected, result);
    }


    /**
     * Tests the behaviour of a string with missing parameter being passed into splitBmiInput.
     * Expects InsufficientInput exception to be thrown.
     */
    @Test
    void splitBmiInput_missingParameter_throwsInsufficientInputException() {
        String input = "/h:bmi /height:1.71 /date:19-03-2024";
        assertThrows(CustomExceptions.InsufficientInput.class, () -> parser.splitBmiInput(input));
    }

    /**
     * Tests the behaviour of too many forward slashes being passed into splitBmiInput.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void splitBmiInput_tooManyForwardSlashes_throwsInvalidInputException() {
        String input = "/h:bmi /height:1.71 /weight:80.00 /date:19-03-2024 /";
        assertThrows(CustomExceptions.InvalidInput.class, () -> parser.splitBmiInput(input));
    }


    /**
     * Tests the behaviour of a correctly formatted string being passed into splitPeriodInput.
     * Expects no exception to be thrown.
     */
    @Test
    void splitPeriodInput_correctInput_noExceptionThrown() throws CustomExceptions.InvalidInput,
            CustomExceptions.InsufficientInput {
        String input = "/h:period /start:29-04-2023 /end:30-04-2023";
        String[] expected = {"29-04-2023", "30-04-2023"};
        String[] result = parser.splitPeriodInput(input);
        assertArrayEquals(expected, result);
    }

    /**
     * Tests the behaviour of a string with a missing parameter being passed into splitPeriodInput.
     * Expects InsufficientInput exception to be thrown.
     */
    @Test
    void splitPeriodInput_missingParameter_throwsInsufficientInputException() {
        String input = "/h:period /end:29-04-2023";
        assertThrows(CustomExceptions.InsufficientInput.class, () -> parser.splitPeriodInput(input));
    }

    //@@author syj02
    /**
     * Tests the behaviour of a correctly formatted string being passed into splitAppointmentInput.
     * Expects no exception to be thrown.
     */
    @Test
    void splitAppointmentInput_correctInput_noExceptionThrown() throws CustomExceptions.InsufficientInput,
            CustomExceptions.InvalidInput {
        String input = "/h:appointment /date:30-03-2024 /time:19:30 /description:test";
        String[] expected = {"30-03-2024", "19:30", "test"};
        String[] result = parser.splitAppointmentDetails(input);
        assertArrayEquals(expected, result);
    }

    /**
     * Tests the behaviour of a correctly formatted string being passed into splitAppointmentDetails.
     * Expects InsufficientInput exception to be thrown.
     */
    @Test
    void splitAppointmentInput_missingParameter_throwsInsufficientInputException() {
        String input = "/h:appointment /date:30-03-2024 /description:test";
        assertThrows(CustomExceptions.InsufficientInput.class, () -> parser.splitAppointmentDetails(input));
    }

    /**
     * Tests the behaviour of too many forward slashes being passed into splitAppointmentDetails.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void splitAppointmentInput_tooManyForwardSlashes_throwsInvalidInputException() {
        String input = "/h:appointment /date:30-03-2024 /time:19:30 /description:test/";
        assertThrows(CustomExceptions.InvalidInput.class, () -> parser.splitAppointmentDetails(input));
    }

    //@@author rouvinerh
    /**
     * Tests the behaviour of a correctly formatted string being
     * passed into parseHistoryAndLatestInput.
     * Expects no error thrown, and correct filter string returned.
     */
    @Test
    void parseHistoryAndDeleteInput_correctInput_noExceptionThrown() {
        String input = "/item:appointment";
        String result = parser.parseHistory(input);
        String expected = "appointment";
        assertEquals(expected, result);
    }

    /**
     * Tests the behaviour of an empty string being passed into parseHistoryAndLatestInput.
     * Expects null to be returned.
     */
    @Test
    void parseHistoryAndDeleteInput_emptyString_expectsNullReturned() {
        String input = "/item:";
        assertNull(parser.parseDeleteInput(input));
    }

    //@@author JustinSoh
    /**
     * Tests the behaviour of a correctly formatted string without
     * dates being passed to splitGymInput.
     * Expects the list of strings to contain the correct parameters.
     *
     * @throws CustomExceptions.InsufficientInput If there is insufficient input.
     */
    @Test
    void splitGymInput_correctInputWithoutDate_noExceptionThrown() throws CustomExceptions.InsufficientInput,
            CustomExceptions.InvalidInput {
        String input = "/e:gym /n:3";
        String[] expected = {"3", null};
        String[] result = parser.splitGymInput(input);
        assertArrayEquals(expected, result);
    }

    /**
     * Tests the behaviour of a correctly formatted string being passed to splitGymInput.
     * Expects the list of strings to contain the correct parameters.
     *
     * @throws CustomExceptions.InsufficientInput If there is insufficient input.
     */
    @Test
    void splitGymInput_correctInputWithDate_noExceptionThrown() throws CustomExceptions.InsufficientInput,
            CustomExceptions.InvalidInput {
        String input = "/e:gym /n:3 /date:29-03-2024";
        String[] expected = {"3", "29-03-2024"};
        String[] result = parser.splitGymInput(input);
        assertArrayEquals(expected, result);
    }

    /**
     * Tests the behaviour of an incorrectly formatted string with insufficient parameters
     * being passed to splitGymInput.
     * Expects an InsufficientInput exception to be thrown.
     */
    @Test
    void splitGymInput_incorrectInput_expectInsufficientInputExceptionThrown() {
        String input = "/e:gym";
        assertThrows(CustomExceptions.InsufficientInput.class, () ->
                parser.splitGymInput(input));
    }

    //@@author rouvinerh
    /**
     * Tests the behaviour of a correctly formatted string without dates being passed to splitGymInput.
     * Expects the list of strings to contain the correct parameters.
     *
     * @throws CustomExceptions.InsufficientInput If there is insufficient input.
     */
    @Test
    void splitRunInput_correctInputWithoutDate_noExceptionThrown() throws CustomExceptions.InsufficientInput,
            CustomExceptions.InvalidInput {
        String input = "/e:run /t:25:24 /d:5.15";
        String[] expected = {"25:24", "5.15", null};
        String[] result = parser.splitRunInput(input);
        assertArrayEquals(expected, result);
    }

    /**
     * Tests the behaviour of a correctly formatted string being passed to splitGymInput.
     * Expects the list of strings to contain the correct parameters.
     *
     * @throws CustomExceptions.InsufficientInput If there is insufficient input.
     */
    @Test
    void splitRunInput_correctInputWithDate_noExceptionThrown() throws CustomExceptions.InsufficientInput,
            CustomExceptions.InvalidInput {
        String input = "/e:run /d:5.15 /t:25:24 /date:29-04-2024";
        String[] expected = {"25:24", "5.15", "29-04-2024"};
        String[] result = parser.splitRunInput(input);
        assertArrayEquals(expected, result);
    }

    /**
     * Tests the behaviour of an incorrectly formatted string with insufficient parameters
     * being passed to splitGymInput.
     * Expects an InsufficientInput exception to be thrown.
     */
    @Test
    void splitRunInput_incorrectInput_expectInsufficientInputExceptionThrown() {
        String input = "/e:run /d:5.10";
        assertThrows(CustomExceptions.InsufficientInput.class, () ->
                parser.splitRunInput(input));
    }

    /**
     * Tests the behaviour of the extractSubstringFromSpecificIndex with a correct flag.
     * Expects the 'bmi' string to be extracted.
     */
    @Test
    void extractSubstringFromSpecificIndex_correctFlag_returnsCorrectSubstring() {
        String test = "/h:bmi";
        String testDelimiter = "/h:";
        String result = parser.extractSubstringFromSpecificIndex(test, testDelimiter);
        String expected = "bmi";
        assertEquals(expected, result);
    }

    //@@author JustinSoh
    /**
     * Tests the behaviour of parseGymFileInput with the correct input.
     * Ensures a Gym object is added with the correct attributes.
     */
    @Test
    void parseGymFileInput_correctInput_returnsGymObject() {
        String input = "gym:2:11-11-1997:bench press:4:10:10,20,30,40:squats:2:5:20,30";
        String input2 = "gym:2:NA:bench press:4:10:10,20,30,40:squats:2:5:20,30";

        try{
            Gym gymOutput = parser.parseGymFileInput(input);
            Gym gymOutput2 = parser.parseGymFileInput(input2);
            // make sure that there is two gym station created
            assertEquals(2, gymOutput.getStations().size());
            // make sure that the date is correct
            assertEquals("1997-11-11", gymOutput.getDate());
            assertEquals(gymOutput2.getDate(), "NA");
            // make sure the gym exercise names are correct
            assertEquals("bench press", gymOutput.getStationByIndex(0).getStationName());
            assertEquals("squats", gymOutput.getStationByIndex(1).getStationName());
            // make sure the number of sets are correct
            assertEquals(4, gymOutput.getStationByIndex(0).getNumberOfSets());
            assertEquals(2, gymOutput.getStationByIndex(1).getNumberOfSets());
            // make sure the reps of each station are correct
            assertEquals(10, gymOutput.getStationByIndex(0).getSets().get(0).getNumberOfRepetitions());
            assertEquals(5, gymOutput.getStationByIndex(1).getSets().get(0).getNumberOfRepetitions());
            // make sure the weights of each station are correct
            assertEquals(10, gymOutput.getStationByIndex(0).getSets().get(0).getWeight());
            assertEquals(20, gymOutput.getStationByIndex(0).getSets().get(1).getWeight());
            assertEquals(30, gymOutput.getStationByIndex(0).getSets().get(2).getWeight());
            assertEquals(40, gymOutput.getStationByIndex(0).getSets().get(3).getWeight());
            assertEquals(20, gymOutput.getStationByIndex(1).getSets().get(0).getWeight());
            assertEquals(30, gymOutput.getStationByIndex(1).getSets().get(1).getWeight());
        } catch (Exception e) {
            fail("Should not throw an exception");
        }
    }

    /**
     * Tests the behaviour of parseGymFileInput when invalid input strings are given.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void parseGymFileInput_incorrectInput_throwsInvalidInputException() {
        // not enough parameters
        String input1 = "gym:2:11-11-1997:bench press:4:10:10,20,30,40:squats:2:5";
        assertThrows(CustomExceptions.InvalidInput.class, () -> parser.parseGymFileInput(input1));

        // blank parameters
        String input2 = "gym:2:11-11-1997:bench press:4:10:10,20,30,40:squats:2:5:";
        assertThrows(CustomExceptions.InvalidInput.class, () -> parser.parseGymFileInput(input2));

        // station name too long
        String input3 = "gym:2:11-11-1997:AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA:4:10:10,20,30,40:squats:2:5:10,20";
        assertThrows(CustomExceptions.InvalidInput.class, () -> parser.parseGymFileInput(input3));

        // station name does not follow regex
        String input4 = "gym:2:11-11-1997:aa;:4:10:10,20,30,40:squats:2:5:10,20";
        assertThrows(CustomExceptions.InvalidInput.class, () -> parser.parseGymFileInput(input4));

        // non-numerical sets
        String input5 = "gym:2:11-11-1997:bench press:a:10:10,20,30,40:squats:2:5:10,20";
        assertThrows(CustomExceptions.InvalidInput.class, () -> parser.parseGymFileInput(input5));

        // weights size more than number of sets
        String input6 = "gym:2:11-11-1997:bench press:a:10:10,20,30,40:squats:2:5:10";
        assertThrows(CustomExceptions.InvalidInput.class, () -> parser.parseGymFileInput(input6));
    }

    // @@author rouvinerh
    /**
     * Tests the behaviour of correct inputs being passed to splitAndValidateGymStationInput
     * Expects no exceptions thrown.
     *
     * @throws CustomExceptions.InvalidInput      If there are invalid parameters specified.
     */
    @Test
    void splitAndValidateGymStationInput_validInput_correctParametersReturned() throws
            CustomExceptions.InvalidInput {
        String input = "Bench Press /s:2 /r:4 /w:10,20";
        String[] expected = {"Bench Press", "2", "4", "10,20"};
        String[] result = parser.splitGymStationInput(input);
        assertArrayEquals(expected, result);
    }

    

}
