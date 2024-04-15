package workouts;

import constants.ErrorConstant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utility.CustomExceptions;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class GymTest {

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void cleanup() {
        WorkoutLists.clearWorkoutsRunGym();
    }

    /**
     * Tests the behavior of adding a new station to the gym.
     * Verifies whether the newly added station is correctly reflected in the gym class.
     * Expected Behaviour is to add stations and sets to the gym.
     */
    @Test
    void addStation_validInput_expectAddedStation() {
        Gym newGym = new Gym();
        try{

            newGym.addStation("ExerciseOne", "1", "10", "1.0");
            newGym.addStation("ExerciseTwo", "2", "20", "1.0,2.0");
            assertEquals(2, newGym.getStations().size());

            newGym.addStation("ExerciseThree", "3", "30", "1.0,2.0,3.0");
            ArrayList<GymStation> stations = newGym.getStations();
            assertEquals(3, stations.size());

            for(int i = 0; i < stations.size(); i++){
                String stationName = stations.get(i).getStationName();
                ArrayList<GymSet> sets = stations.get(i).getSets();
                int numberOfSets = sets.size();

                if (i == 0){
                    assertEquals("ExerciseOne", stationName);
                    assertEquals(1, numberOfSets );
                    assertEquals(1.0, sets.get(0).getWeight());
                    assertEquals(10, sets.get(0).getNumberOfRepetitions());

                } else if (i == 1){
                    assertEquals("ExerciseTwo", stationName);
                    assertEquals(2, numberOfSets );
                    assertEquals(1.0, sets.get(0).getWeight());
                    assertEquals(20, sets.get(0).getNumberOfRepetitions());
                    assertEquals(2.0, sets.get(1).getWeight());
                    assertEquals(20, sets.get(1).getNumberOfRepetitions());

                } else if (i == 2){
                    assertEquals("ExerciseThree", stationName);
                    assertEquals(3, numberOfSets );
                    assertEquals(1.0, sets.get(0).getWeight());
                    assertEquals(30, sets.get(0).getNumberOfRepetitions());
                    assertEquals(2.0, sets.get(1).getWeight());
                    assertEquals(30, sets.get(1).getNumberOfRepetitions());
                    assertEquals(3.0, sets.get(2).getWeight());
                    assertEquals(30, sets.get(2).getNumberOfRepetitions());

                }
            }

        } catch (Exception e) {
            fail("Should not throw an exception");
        }
    }

    /**
     * Tests the behaviour of incorrect inputs being passed to the addStation method in Gym.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void addStation_invalidInput_expectsInvalidInputException() {
        Gym gym = new Gym();
        Exception exception;

        // number of sets is not a positive integer
        exception = assertThrows(CustomExceptions.InvalidInput.class, () ->
                gym.addStation("Bench Press", "a", "4", "1020"));
        assertTrue(exception.getMessage().contains(ErrorConstant.INVALID_SETS_POSITIVE_DIGIT_ERROR));

        // number of reps is not a positive integer
        exception = assertThrows(CustomExceptions.InvalidInput.class, () ->
                gym.addStation("Bench Press", "2", "a", "1020"));
        assertTrue(exception.getMessage().contains(ErrorConstant.INVALID_REPS_POSITIVE_DIGIT_ERROR));

        // weights does not have comma
        exception = assertThrows(CustomExceptions.InvalidInput.class, () ->
                gym.addStation("Bench Press", "2", "3", "1020"));
        assertTrue(exception.getMessage().contains(ErrorConstant.INVALID_WEIGHTS_NUMBER_ERROR));

        // weights array > maximum
        exception = assertThrows(CustomExceptions.InvalidInput.class, () ->
                gym.addStation("Bench Press", "2", "3", "10000,20"));
        assertTrue(exception.getMessage().contains(ErrorConstant.INVALID_WEIGHT_MAX_ERROR));

        // weights array > minimum
        exception = assertThrows(CustomExceptions.InvalidInput.class, () ->
                gym.addStation("Bench Press", "2", "3", "-10,-20"));
        assertTrue(exception.getMessage().contains(ErrorConstant.INVALID_WEIGHTS_ARRAY_FORMAT_ERROR));

        // weights array has letters
        exception = assertThrows(CustomExceptions.InvalidInput.class, () ->
                gym.addStation("Bench Press", "2", "3", "10,a"));
        assertTrue(exception.getMessage().contains(ErrorConstant.INVALID_WEIGHTS_ARRAY_FORMAT_ERROR));

        // weights array has spaces
        exception = assertThrows(CustomExceptions.InvalidInput.class, () ->
                gym.addStation("Bench Press", "2", "3", "10, 20"));
        assertTrue(exception.getMessage().contains(ErrorConstant.INVALID_WEIGHTS_ARRAY_FORMAT_ERROR));

        // weights array regex fail
        exception = assertThrows(CustomExceptions.InvalidInput.class, () ->
                gym.addStation("Bench Press", "2", "3", "a"));
        assertTrue(exception.getMessage().contains(ErrorConstant.INVALID_WEIGHTS_ARRAY_FORMAT_ERROR));

        // no weights specified
        exception = assertThrows(CustomExceptions.InvalidInput.class, () ->
                gym.addStation("Bench Press", "2", "3", ""));
        assertTrue(exception.getMessage().contains(ErrorConstant.INVALID_WEIGHTS_EMPTY_ERROR));

        // weights and sets do not match
        exception = assertThrows(CustomExceptions.InvalidInput.class, () ->
                gym.addStation("Bench Press", "1", "3", "10,20"));
        assertTrue(exception.getMessage().contains((ErrorConstant.INVALID_WEIGHTS_NUMBER_ERROR)));
    }

    /**
     * Test to see if getStationByIndex handles invalid index correctly by throwing an OutOfBounds exception.
     */
    @Test
    void getStationByIndex_invalidIndex_throwOutOfBoundsError(){
        Gym newGym = new Gym();
        Exception exception;
        try {
            newGym.addStation("ExerciseOne", "1", "10", "1.0");
            newGym.addStation("ExerciseTwo", "2", "20", "1.0,2.0");
            newGym.addStation("ExerciseThree", "3", "30", "1.0,2.0,3.0");

            exception = assertThrows(CustomExceptions.OutOfBounds.class, () -> newGym.getStationByIndex(-1));
            assertTrue(exception.getMessage().contains(ErrorConstant.INVALID_INDEX_SEARCH_ERROR));

            exception = assertThrows(CustomExceptions.OutOfBounds.class, () -> newGym.getStationByIndex(3));
            assertTrue(exception.getMessage().contains(ErrorConstant.INVALID_INDEX_SEARCH_ERROR));

        } catch (CustomExceptions.InvalidInput | CustomExceptions.InsufficientInput e) {
            fail("Should not throw an exception");
        }
    }

    /**
     * Test to see if getStationByIndex returns the correct station when given a valid index.
     */
    @Test
    void getStationByIndex_correctIndex_expectCorrectStation(){
        Gym newGym = new Gym();
        try {
            newGym.addStation("ExerciseOne", "1", "10", "1.0");
            newGym.addStation("ExerciseTwo", "2", "20", "1.0,2.0");
            newGym.addStation("ExerciseThree", "3", "30", "1.0,2.0,3.0");

            GymStation station1 = newGym.getStationByIndex(1);
            assertEquals("ExerciseTwo", station1.getStationName());
            assertEquals(2, station1.getSets().size());
            assertEquals(1.0, station1.getSets().get(0).getWeight());
            assertEquals(2.0, station1.getSets().get(1).getWeight());

        } catch (CustomExceptions.InvalidInput | CustomExceptions.InsufficientInput | CustomExceptions.OutOfBounds e) {
            fail("Should not throw an exception");
        }
    }


    /**
     * Test to see if toFileString method in Gym works with correct input.
     * Expects correct string to be returned.
     */
    @Test
    void toFileString_correctInput_expectedCorrectString(){
        String expected1 = "GYM:2:11-11-1997:bench press:4:4:10.0,20.0,30.0,40.0:squats:4:3:20.0,30.0,40.0,50.0";
        String expected2WithNoDate = "GYM:2:NA:bench press:4:4:10.0,20.0,30.0,40.0:squats:4:3:20.0,30.0,40.0,50.0";

        try {
            Gym newGym = new Gym("11-11-1997");
            Gym newGym2 = new Gym();

            newGym.addStation("bench press", "4", "4", "10.0,20.0,30.0,40.0");
            newGym.addStation("squats", "4", "3", "20.0,30.0,40.0,50.0");
            newGym2.addStation("bench press", "4", "4", "10.0,20.0,30.0,40.0");
            newGym2.addStation("squats", "4", "3", "20.0,30.0,40.0,50.0");

            String output = newGym.toFileString();
            String output2 = newGym2.toFileString();
            assertEquals(expected1, output);
            assertEquals(expected2WithNoDate, output2);
        } catch (CustomExceptions.InvalidInput | CustomExceptions.InsufficientInput e) {
            fail("Should not throw an exception");
        }
    }
}
