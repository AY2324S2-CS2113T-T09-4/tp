package workouts;

import constants.ErrorConstant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utility.CustomExceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WorkoutListsTest {
    @BeforeEach
    void setUp() {

    }

    /**
     * Clears the list of workouts/runs/gyms after each test.
     */
    @AfterEach
    void cleanup() {
        WorkoutLists.clearWorkoutsRunGym();
    }


    /**
     * Tests the behavior of adding a new run to the run list.
     * Verifies whether the newly added run is correctly reflected in the run and WorkoutList.
     */
    @Test
    void addRun_normalInput_expectAppend()  {
        try {
            Run inputRun = new Run("40:10", "10.3", "15-03-2024");

            WorkoutLists workoutListsInstance = new WorkoutLists();
            workoutListsInstance.addRun(inputRun);

            ArrayList<Run> runList = WorkoutLists.getRuns();
            ArrayList<Workout> workoutList = WorkoutLists.getWorkouts();

            Workout expectedRun = runList.get(runList.size() - 1);
            Workout expectedWorkout = workoutList.get(runList.size() - 1);

            assertEquals(inputRun, expectedRun);
            assertEquals(inputRun, expectedWorkout);

        } catch (CustomExceptions.InvalidInput e) {
            fail("Should not throw an exception.");
        }
    }


    /**
     * Tests the behavior of getting the workout list with RUN , GYM, and ALL.
     * Verifies whether the method is able to correct retrieve the list of workouts.
     */
    @Test
    void getWorkouts_properInput_expectRetrieval() {
        try {

            // Setup
            ArrayList<Gym> inputGymList = new ArrayList<>();
            ArrayList<Run> inputRunList = new ArrayList<>();

            Gym gym1 = new Gym("15-11-2023");
            gym1.addStation("Bench Press", "1", "50", "1.0");
            gym1.addStation("Shoulder Press", "2", "10", "1.0,2.0");

            Gym gym2 = new Gym("16-11-2023");
            gym2.addStation("Squat Press", "1", "50", "1.0");
            gym2.addStation("Lat Press", "2", "10", "1.0,2.0");
            gym2.addStation("Bicep curls", "1", "10", "1.0");

            Run run1 = new Run("40:10", "10.3", "15-03-2024");
            Run run2 = new Run("30:10", "20.3", "30-03-2023");


            inputGymList.add(gym1);
            inputGymList.add(gym2);
            inputRunList.add(run1);
            inputRunList.add(run2);



            ArrayList<Run> runList = WorkoutLists.getRuns();
            for(int i = 0; i < inputRunList.size(); i++) {
                Run expected = inputRunList.get(i);
                Run actual = runList.get(i);
                assertEquals(expected, actual);
            }

            ArrayList<Gym> gymList = WorkoutLists.getGyms();
            for(int i = 0; i < inputGymList.size(); i++) {
                Gym expected = inputGymList.get(i);
                Gym actual = gymList.get(i);
                assertEquals(expected, actual);
            }

            ArrayList<? extends Workout> allList = WorkoutLists.getWorkouts();
            assertEquals(gym1, allList.get(0));
            assertEquals(gym2, allList.get(1));
            assertEquals(run1, allList.get(2));
            assertEquals(run2, allList.get(3));

        } catch (CustomExceptions.InvalidInput | CustomExceptions.InsufficientInput e) {
            fail("Should not throw an exception.");
        }
    }


    /**
     * Tests the behavior of getting the latest run from the run list.
     * Expected behavior is for actual to equal to the secondRun.
     */
    @Test
    void getLatestRun_properList_correctRetrieval() {
        try {
            new Run("20:10", "10.3", "15-03-2024");
            Run secondRun = new Run("20:10", "10.3", "15-03-2024");

            Run actual = WorkoutLists.getLatestRun();
            assertEquals(secondRun, actual);
        } catch (CustomExceptions.OutOfBounds | CustomExceptions.InvalidInput e) {
            fail("Should not throw an exception");
        }
    }

    /**
     * Test the behaviour when you try to get the latest run from an empty list.
     * Expected behaviour is to raise OutOfBounds exception.
     */
    @Test
    void getLatestRun_emptyList_throwOutOfBound() {
        // Call the method or code that should throw the exception
        assertThrows(CustomExceptions.OutOfBounds.class, WorkoutLists::getLatestRun);
    }


    /**
     * Test deleting of runs with valid list and valid index.
     * Expected behaviour is to have one run left in the list.
     */
    @Test
    void deleteRun_properList_listOfSizeOne() {
        try {
            new Run("20:10", "10.3", "15-03-2024");
            new Run("20:11", "10.3", "15-03-2023");
            int index = 1;
            WorkoutLists.deleteRun(index);
            assertEquals(1, WorkoutLists.getRunSize());
        } catch (CustomExceptions.InvalidInput | CustomExceptions.OutOfBounds e) {
            fail("Should not throw an exception");
        }
    }

    /**
     * Test deleting of runs with empty list.
     * Expected behaviour is for an AssertionError to be thrown.
     */
    @Test
    void deleteRun_emptyList_throwsAssertionError() {
        assertThrows (AssertionError.class, () ->
                WorkoutLists.deleteRun(0));
    }

    /**
     * Test deleting of runs with invalid index.
     * Expected behaviour is for an OutOfBounds error to be thrown.
     */
    @Test
    void deleteRun_properListInvalidIndex_throwOutOfBoundsForRun(){
        try {
            new Run("20:10", "10.3", "15-03-2024");
        } catch (CustomExceptions.InvalidInput e) {
            fail("Should not throw an exception");
        }
        int invalidIndex = 5;
        assertThrows (CustomExceptions.OutOfBounds.class, () ->
                WorkoutLists.deleteRun(invalidIndex));
    }

    /**
     * Test deleting of gyms with valid list and valid index.
     * Expected behaviour is to delete the first gym and be left with one in the list.
     * The gym left should be the second gym in the list.
     */
    @Test
    void deleteGym_validIndex_listOfSizeOne(){
        Gym gym1 = new Gym();
        new ArrayList<>(List.of(1.0));
        new ArrayList<>(Arrays.asList(1.0,2.0));
        try {
            gym1.addStation("Bench Press", "1", "50", "1.0");
            gym1.addStation("Shoulder Press", "2", "10", "1.0,2.0");

            Gym gym2 = new Gym();
            gym2.addStation("Squat Press", "1", "50", "1.0");
            gym2.addStation("Lat Press", "2", "10", "1.0,2.0");
            gym2.addStation("Bicep curls", "1", "10", "1.0");
        } catch (CustomExceptions.InvalidInput | CustomExceptions.InsufficientInput e) {
            fail("Should not throw an exception");
        }

        int index = 0;
        try {
            WorkoutLists.deleteGym(index);
            assertEquals(1, WorkoutLists.getGymSize());
            // check to make sure that after deleting the first gym, the second gym becomes first
            assertEquals("Squat Press" , WorkoutLists.getGyms().get(0).getStationByIndex(0).getStationName());
        } catch (CustomExceptions.OutOfBounds outOfBounds) {
            fail("Should not throw an exception");
        }
    }

    /**
     * Test deleting of gym with empty list.
     * Expected behaviour is for an Out of Bound error to be thrown.
     */
    @Test
    void deleteGym_emptyList_throwsOutOfBoundsError() {
        Exception exception = assertThrows (CustomExceptions.OutOfBounds.class, () ->
                WorkoutLists.deleteGym(0));
        assertTrue(exception.getMessage().contains(ErrorConstant.INVALID_INDEX_DELETE_ERROR));
    }

    /**
     * Test deleting of gym with invalid index.
     * Expected behaviour is for an OutOfBounds error to be thrown.
     */
    @Test
    void deleteGym_invalidIndex_throwOutOfBoundsForGym() {
        Gym gym1 = new Gym();
        try {
            gym1.addStation("Bench Press", "1", "50", "1.0");
            gym1.addStation("Shoulder Press", "2", "10", "2.0,3.0");

        } catch (CustomExceptions.InvalidInput | CustomExceptions.InsufficientInput e) {
            fail("Should not throw an exception");
        }

        // test for invalid index
        int invalidIndex = 5;
        Exception exception = assertThrows (CustomExceptions.OutOfBounds.class, () ->
                WorkoutLists.deleteGym(invalidIndex));
        assertTrue(exception.getMessage().contains(ErrorConstant.INVALID_INDEX_DELETE_ERROR));

        // test for below 0 index
        int invalidIndex2 = -1;
        exception = assertThrows (CustomExceptions.OutOfBounds.class, () ->
                WorkoutLists.deleteGym(invalidIndex2));
        assertTrue(exception.getMessage().contains(ErrorConstant.INVALID_INDEX_DELETE_ERROR));
    }
}
