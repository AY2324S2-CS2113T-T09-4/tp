package workouts;

import constants.ErrorConstant;
import storage.LogFile;
import utility.CustomExceptions;
import constants.UiConstant;
import constants.WorkoutConstant;
import utility.Validation;

import java.util.ArrayList;

/**
 * Represents a Gym object that extends the Workout class.
 * A gym object can have multiple GymStation objects.
 *
 */
public class Gym extends Workout {
    //@@author JustinSoh

    private final ArrayList<GymStation> stations = new ArrayList<>();

    /**
     * Constructs a new Gym.
     * When a new Gym object is created, it is automatically added to a list of workouts.
     */
    public Gym() {
        super.addIntoWorkoutList(this);
    }

    /**
     * Overloaded constructor that takes the optional date parameter.
     *
     * @param stringDate String representing the date parameter specified.
     */
    public Gym(String stringDate) {
        super(stringDate);
        super.addIntoWorkoutList(this);
    }

    /**
     * Adds a new GymStation object into the Gym object.
     *
     * @param name String containing the name of the gym station.
     * @param numberOfSet String of the number of sets done.
     * @param numberOfRepetitions String of the number of repetitions done.
     * @param weights String of weights separated by commas. (e.g. "10,20,30,40")
     * @throws CustomExceptions.InsufficientInput If any of the input fields are empty.
     * @throws CustomExceptions.InvalidInput If the input fields are invalid.
     */
    public void addStation(String name,
                           String numberOfSet,
                           String numberOfRepetitions,
                           String weights)
            throws CustomExceptions.InsufficientInput,
            CustomExceptions.InvalidInput {

        GymStation newStation = new GymStation(name, numberOfSet, numberOfRepetitions, weights);
        appendIntoStations(newStation);
        LogFile.writeLog("Added Gym Station: " + name, false);
    }

    /**
     * Gets the list of GymStation objects.
     *
     * @return An ArrayList of GymStation objects.
     */
    public ArrayList<GymStation> getStations() {
        return stations;
    }

    /**
     * Retrieves the GymStation object by index.
     *
     * @param index Index of the GymStation object.
     * @return GymStation object.
     * @throws CustomExceptions.OutOfBounds If the index is out of bounds.
     */
    public GymStation getStationByIndex(int index) throws CustomExceptions.OutOfBounds {
        boolean isIndexValid = Validation.validateIndexWithinBounds(index, 0,  stations.size());
        if (!isIndexValid) {
            throw new CustomExceptions.OutOfBounds(ErrorConstant.INVALID_INDEX_SEARCH_ERROR);
        }
        return stations.get(index);
    }

    /**
     * Retrieves the string representation of a Gym object.
     *
     * @return A formatted string representing the Gym object, inclusive of the date and gym stations done.
     */

    @Override
    public String toString() {
        return String.format(" (Date: %s)", super.getDate());
    }

    /**
     * Converts the Gym object into a string format suitable for writing into a file.
     * For more examples, refer to the GymTest method toFileString_correctInput_expectedCorrectString().
     *
     * @return A string representing the Gym object and its GymStation objects unsuitable for writing into a file.
     */
    public String toFileString(){
        StringBuilder formattedString = new StringBuilder();

        // Append the type, number of stations, and date (GYM:NUM_STATIONS:DATE:)
        formattedString.append(WorkoutConstant.GYM.toUpperCase())
                .append(UiConstant.SPLIT_BY_COLON)
                .append(stations.size())
                .append(UiConstant.SPLIT_BY_COLON)
                .append(super.getDateForFile())
                .append(UiConstant.SPLIT_BY_COLON);

        int lastIndex = stations.size() - 1;
        for (int i = 0; i < stations.size(); i++) {
            formattedString.append(stations.get(i).toFileString());
            if (i != lastIndex) {
                formattedString.append(UiConstant.SPLIT_BY_COLON);
            }
        }
        return formattedString.toString();
    }

    /**
     * Used when printing all the workouts. This method takes in parameters index.
     *
     * @param index indicates which particular gymStation is being queried.
     * @return A string representing the history format for gym.
     */
    public String getHistoryFormatForSpecificGymStation(int index) {

        // Get the string format for a specific gym station
        GymStation station = getStations().get(index);
        String gymStationString = station.getStationName();
        String gymSetString = String.valueOf(station.getNumberOfSets());

        // If it is first iteration, includes dashes for irrelevant field
        String prefix = index == 0 ? WorkoutConstant.GYM : UiConstant.EMPTY_STRING;
        String date = index == 0 ? super.getDate() : UiConstant.EMPTY_STRING;

        return String.format(WorkoutConstant.HISTORY_WORKOUTS_DATA_FORMAT,
                prefix, date, gymStationString, gymSetString, UiConstant.DASH);
    }

    private void appendIntoStations(GymStation station) {
        stations.add(station);
    }
}
