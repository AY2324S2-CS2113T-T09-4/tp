package workouts;

import storage.LogFile;
import ui.Output;
import utility.CustomExceptions;
import constants.ErrorConstant;
import utility.Validation;

import java.util.ArrayList;

/**
 * WorkoutLists class contains a static list of workouts, runs and gyms.
 * You cannot add a new object to the list directly.
 * It will automatically be added when you create a new Run/Gym object.
 * To retrieve the list of workouts/gym/run, you can use the static 'get' methods provided.
 */
public class WorkoutLists {
    //@@author JustinSoh
    private static final ArrayList<Workout> WORKOUTS = new ArrayList<>();
    private static final ArrayList<Run> RUNS = new ArrayList<>();
    private static final ArrayList<Gym> GYMS = new ArrayList<>();

    public WorkoutLists() {

    }

    /**
     * Returns the static list of workouts objects which contains both runs and gyms.
     * It is important to note that the list is not sorted by date (as it is optional)
     * Rather, it is ordered by when it has been created.
     *
     * @return The list of workouts.
     */
    public static ArrayList<Workout> getWorkouts() {
        return WORKOUTS;
    }

    /**
     * Returns the static list of runs objects.
     *
     * @return The list of runs.
     */
    public static ArrayList<Run> getRuns() {
        return RUNS;
    }

    /**
     * Returns the static list of gyms objects.
     *
     * @return The list of gyms.
     */
    public static ArrayList<Gym> getGyms() {
        return GYMS;
    }

    /**
     * Returns latest run.
     *
     * @return The latest Run object added.
     * @throws CustomExceptions.OutOfBounds If no runs are found in the list.
     */
    public static Run getLatestRun() throws CustomExceptions.OutOfBounds {
        if (RUNS.isEmpty()) {
            throw new CustomExceptions.OutOfBounds(ErrorConstant.RUN_EMPTY_ERROR);
        }
        return RUNS.get(RUNS.size() - 1);
    }

    /**
     * Returns latest gym.
     *
     * @return The latest Gym object added.
     * @throws CustomExceptions.OutOfBounds If no gyms are found in the list.
     */
    public static Gym getLatestGym() throws CustomExceptions.OutOfBounds {
        if (GYMS.isEmpty()) {
            throw new CustomExceptions.OutOfBounds(ErrorConstant.GYM_EMPTY_ERROR);
        }
        return GYMS.get(GYMS.size() - 1);
    }

    /**
     * Returns the number of runs in the list.
     *
     * @return The number of runs.
     */
    public static int getRunSize() {
        return RUNS.size();
    }

    /**
     * Returns the number of gyms in the list.
     *
     * @return The number of gyms.
     */
    public static int getGymSize() {
        return GYMS.size();
    }

    /**
     * Deletes Gym object based on the index that will be validated.
     *
     * @param index Index of the Gym object to be deleted.
     * @throws CustomExceptions.OutOfBounds If the index is invalid.
     */
    public static void deleteGym(int index) throws CustomExceptions.OutOfBounds {
        boolean indexIsValid = Validation.validateIndexWithinBounds(index, 0, GYMS.size());

        if (!indexIsValid) {
            throw new CustomExceptions.OutOfBounds(ErrorConstant.INVALID_INDEX_DELETE_ERROR);
        }

        Gym deletedGym = GYMS.get(index);
        Output.printDeleteGymMessage(deletedGym);
        WORKOUTS.remove(deletedGym);
        GYMS.remove(index);
        LogFile.writeLog("Removed gym with index: " + index, false);
    }

    /**
     * Deletes Run object based on the index that will be validated.
     *
     * @param index Index of the Run object to be deleted.
     * @throws CustomExceptions.OutOfBounds If the index is invalid.
     */
    public static void deleteRun(int index) throws CustomExceptions.OutOfBounds {
        assert !RUNS.isEmpty() : "Run list is empty.";
        boolean indexIsValid = Validation.validateIndexWithinBounds(index, 0, RUNS.size());
        if (!indexIsValid) {
            throw new CustomExceptions.OutOfBounds(ErrorConstant.INVALID_INDEX_DELETE_ERROR);
        }
        Run deletedRun = RUNS.get(index);
        Output.printDeleteRunMessage(deletedRun);
        WORKOUTS.remove(deletedRun);
        RUNS.remove(index);
        LogFile.writeLog("Removed run with index: " + index, false);
    }

    /**
     * Clears the workouts, runs and gyms ArrayLists.
     * Used mainly for JUnit testing to clear the list after each test.
     */
    public static void clearWorkoutsRunGym() {
        WORKOUTS.clear();
        RUNS.clear();
        GYMS.clear();
    }

    // Protected Methods

    /**
     * Only classes within the workouts package can add a new run to the list of runs.
     * This is called automatically when a new run object is created in the Run class.
     * It will also automatically add the run to the workouts list by calling addWorkout.
     *
     * @param run the Run object to be added
     */
    protected void addRun(Run run) {
        RUNS.add(run);
        addWorkout(run);
    }

    /**
     * Only classes within the workouts package can add a new gym to the list of gyms.
     * This is called automatically when a new gym object is created in the Gym class.
     * It will also automatically add the gym to the workouts list by calling addWorkout.
     *
     * @param gym the Gym object to be added.
     */
    protected void addGym(Gym gym) {
        GYMS.add(gym);
        addWorkout(gym);
    }

    // Private Methods

    /**
     * Automatically adds a workout to the list of workouts.
     *
     * @param workout Workout object to be added to the WORKOUTS lists.
     */
    private void addWorkout(Workout workout) {
        WORKOUTS.add(workout);
    }
}
