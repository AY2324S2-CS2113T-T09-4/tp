package workouts;

import constants.ErrorConstant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utility.CustomExceptions;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class GymStationTest {

    private GymStation gymStation;

    @BeforeEach
    void setUp() {
        // initialise a gymStation object to test the methods
        try {
            gymStation = new GymStation("Bench Press",
                    "1",
                    "10",
                    "1.0");
        } catch (CustomExceptions.InsufficientInput | CustomExceptions.InvalidInput e) {
            fail("Should not have thrown error here");
        }
    }

    @AfterEach
    void cleanup() {

    }

    /**
     * Tests the behaviour of valid exercise names being passed to validateExerciseName.
     * Expects no exceptions to be thrown.
     */
    @Test
    void validateExerciseName_correctName_noExceptionThrown() {
        String input1 = "Bench Press";
        assertDoesNotThrow(() -> gymStation.validateGymStationName(input1));

        String input2 = "squat";
        assertDoesNotThrow(() -> gymStation.validateGymStationName(input2));

        String input3 = "testing exercise";
        assertDoesNotThrow(() -> gymStation.validateGymStationName(input3));
    }

    /**
     * Tests the behaviour of invalid exercise names being passed to validateExerciseName.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validateExerciseName_invalidNames_expectsInvalidInputException() {

        // numbers in name
        String input1 = "bench1";
        Exception exception = assertThrows(CustomExceptions.InvalidInput.class, ()
                -> gymStation.validateGymStationName(input1));
        assertTrue(exception.getMessage().contains(ErrorConstant.INVALID_GYM_STATION_NAME_ERROR));

        // special characters in name
        String input2 = "bench-;";
        exception = assertThrows(CustomExceptions.InvalidInput.class, () -> gymStation.validateGymStationName(input2));
        assertTrue(exception.getMessage().contains(ErrorConstant.INVALID_GYM_STATION_NAME_ERROR));

        // name length > 25 chars
        String input4 = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA ";
        exception = assertThrows(CustomExceptions.InvalidInput.class, () -> gymStation.validateGymStationName(input4));
        assertTrue(exception.getMessage().contains(ErrorConstant.INVALID_GYM_STATION_NAME_ERROR));
    }

    /**
     * Tests the behaviour of empty exercise names being passed to validateExerciseName.
     * Expects InsufficientInput exception to be thrown.
     */
    @Test
    void validateExerciseName_emptyNames_expectsInsufficientInputException() {
        Exception exception = assertThrows(CustomExceptions.InsufficientInput.class, ()
                -> gymStation.validateGymStationName(""));
        assertTrue(exception.getMessage().contains(ErrorConstant.INVALID_GYM_STATION_EMPTY_NAME_ERROR));
    }

    /**
     * Tests the behaviour of a correct weights array being passed to validateWeightsArray.
     * Expects no exception to be thrown, and the correct ArrayList of integers to be returned.
     *
     * @throws CustomExceptions.InvalidInput If the input string does not have the right format.
     */
    @Test
    void processWeightsArray_correctInput_returnCorrectArrayList() throws CustomExceptions.InvalidInput {
        String input = "1.0,2.25,50.5,60.75,0.0";
        ArrayList<Double> expected = new ArrayList<>();
        expected.add(1.0);
        expected.add(2.25);
        expected.add(50.5);
        expected.add(60.75);
        expected.add(0.0);

        ArrayList<Double> result = gymStation.processWeightsArray(input);
        assertArrayEquals(expected.toArray(), result.toArray());
    }

    /**
     * Tests the behaviour of incorrect weights array being passed to validateWeightsArray.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void processWeightsArray_invalidInput_expectInvalidInputException() {
        // negative weights
        String input1 = "-1,2";
        Exception exception = assertThrows(CustomExceptions.InvalidInput.class, () ->
                gymStation.processWeightsArray(input1));
        assertTrue(exception.getMessage().contains(ErrorConstant.INVALID_WEIGHTS_ARRAY_FORMAT_ERROR));

        // blanks
        String input = "";
        exception = assertThrows(CustomExceptions.InvalidInput.class, () ->
                gymStation.processWeightsArray(input));
        assertTrue(exception.getMessage().contains(ErrorConstant.INVALID_WEIGHTS_EMPTY_ERROR));

        // non integer weights
        String input2 = "1,a";
        exception = assertThrows(CustomExceptions.InvalidInput.class, () ->
                gymStation.processWeightsArray(input2));
        assertTrue(exception.getMessage().contains(ErrorConstant.INVALID_WEIGHTS_ARRAY_FORMAT_ERROR));

        // incorrect multiple of weights
        String input3 = "1.333,1.444,0.998";
        exception = assertThrows(CustomExceptions.InvalidInput.class, () ->
                gymStation.processWeightsArray(input3));
        assertTrue(exception.getMessage().contains(ErrorConstant.INVALID_WEIGHTS_VALUE_ERROR));

        // exceed max weights
        String input4 = "3000";
        exception = assertThrows(CustomExceptions.InvalidInput.class, () ->
                gymStation.processWeightsArray(input4));
        assertTrue(exception.getMessage().contains(ErrorConstant.INVALID_WEIGHT_MAX_ERROR));

    }

}
