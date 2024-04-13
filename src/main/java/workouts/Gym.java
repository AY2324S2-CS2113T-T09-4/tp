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
     * Constructs a new Gym Object
     * When a new Gym object is created, it is automatically added to a list of workouts.
     * This is done through the super() call in the constructor.
     *
     */
    public Gym() {
    }

    /**
     * Overloaded constructor that takes the optional date parameter.
     * It also takes in the date parameter specified.
     *
     * @param stringDate String representing the date parameter specified.
     */
    public Gym(String stringDate) {
        super(stringDate);
        workout.addIntoWorkoutList(this);
    }

    /**
     * Adds a new GymStation object into the Gym object.
     * Before adding, it validates the input parameters.
     * It also logs the addition of the gym station into the log file.
     *
     * @param name String containing the name of the gym station.
     * @param numberOfSet String of the number of sets done.
     * @param numberOfRepetitions String of the number of repetitions done.
     * @param weights String of weights separated by commas. (e.g. "10,20,30,40")
     */
    public void addStation(String name,
                           String numberOfSet,
                           String numberOfRepetitions,
                           String weights)
            throws CustomExceptions.InsufficientInput,
            CustomExceptions.InvalidInput {
        String validExerciseName  = validateGymStationName(name);
        int validNumberOfSets = validateNumberOfSets(numberOfSet);
        int validNumberOfReps = validateNumberOfRepetitions(numberOfRepetitions);
        ArrayList<Double> validWeights = processWeightsArray(weights);

        checkIfNumberOfWeightsMatchesSets(validWeights, validNumberOfSets);
        GymStation newStation = new GymStation(validExerciseName, validNumberOfSets, validNumberOfReps, validWeights);
        appendIntoStations(newStation);
        LogFile.writeLog("Added Gym Station: " + validExerciseName, false);
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
     * This method serializes the Gym object, including all its GymStation objects, into a string. The resulting string
     * follows a specific format to ensure that it can be correctly deserialized later.
     * For more examples, refer to the GymTest method {@code toFileString_correctInput_expectedCorrectString()}.
     *
     * @return A string representing the Gym object and its GymStation objects isuitable for writing into a file.
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
     * Used when printing all the workouts. This method takes in parameters {@code index}
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

    // Private methods

    private void appendIntoStations(GymStation station) {
        stations.add(station);
    }


    // Validation Functions
    /**
     * Validates the weight string such that it only has numbers.
     *
     * @param weightsString The string representing the weights in the format "weight1,weight2,weight3..."
     * @return ArrayList of integers representing the weights in the format [weight1, weight2, weight3 ...]
     * @throws CustomExceptions.InvalidInput If an invalid weights string is passed in.
     */
    protected ArrayList<Double> processWeightsArray(String weightsString)
            throws CustomExceptions.InvalidInput {

        validateWeightString(weightsString);

        String[] weightsArray = weightsString.split(UiConstant.SPLIT_BY_COMMAS);
        ArrayList<Double> validatedWeightsArray = new ArrayList<>();

        for (String weight: weightsArray){
            boolean isValidWeight = validateWeight(weight);
            if (isValidWeight){
                validatedWeightsArray.add(Double.parseDouble(weight));
            }
        }
        return validatedWeightsArray;
    }

    /**
     * Validates the gym station name ensuring that
     * - it is not empty
     * - follows the correct pattern (UiConstant.VALID_GYM_STATION_NAME_REGEX)
     * - does not exceed the maximum length. (WorkoutConstant.MAX_GYM_STATION_NAME_LENGTH)
     *
     * @param exerciseName The string representing the gym station name
     * @return String representing the gym station name
     * @throws CustomExceptions.InvalidInput if an invalid gym station name is passed in
     * @throws CustomExceptions.InsufficientInput if an empty gym station name is passed in
     */
    protected String validateGymStationName(String exerciseName) throws CustomExceptions.InvalidInput,
            CustomExceptions.InsufficientInput {

        validateExerciseNameNotEmpty(exerciseName);
        validateExerciseNamePattern(exerciseName);
        validateExerciseNameLength(exerciseName);
        return exerciseName;
    }

    /**
     * Validates the number of sets ensuring that it is a positive integer.
     *
     * @param numberOfSets The string representing the number of sets
     * @return int representing the number of sets
     * @throws CustomExceptions.InvalidInput if an invalid number of sets is passed in
     */
    protected int validateNumberOfSets(String numberOfSets) throws CustomExceptions.InvalidInput {
        boolean isSetsValid = Validation.validateIntegerIsPositive(numberOfSets);
        if (!isSetsValid) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_SETS_POSITIVE_DIGIT_ERROR);
        }

        return Integer.parseInt(numberOfSets);
    }

    /**
     * Validates the number of repetitions ensuring that it is a positive integer.
     *
     * @param numberOfRepetitions The string representing the number of repetitions
     * @return int representing the number of repetitions
     * @throws CustomExceptions.InvalidInput if an invalid number of repetitions is passed in
     */
    protected int validateNumberOfRepetitions(String numberOfRepetitions) throws CustomExceptions.InvalidInput {

        boolean isRepsValid = Validation.validateIntegerIsPositive(numberOfRepetitions);

        if (!isRepsValid) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_REPS_POSITIVE_DIGIT_ERROR);
        }

        return Integer.parseInt(numberOfRepetitions);
    }

    /**
     * Validates the weight string ensuring that
     * - The weight is positive
     * - The weight does not exceed the maximum weight (@code WorkoutConstant.MAX_GYM_WEIGHT)
     * - The weight is a multiple of 0.125 (as that is the increment of weights in a gym)
     *
     * @param weight The string representing the weight
     * @return boolean true if the weight is valid
     * @throws CustomExceptions.InvalidInput if an invalid weight is passed in
     */
    protected boolean validateWeight(String weight) throws CustomExceptions.InvalidInput {
        try{
            double weightDouble = Double.parseDouble(weight);

            validateWeightIsPositive(weightDouble);
            validateWeightDoesNotExceedMax(weightDouble);
            validateWeightIsMultiple(weightDouble);

            return true;
        } catch (NumberFormatException e){
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_WEIGHTS_VALUE_ERROR);
        }
    }


    // Private Methods
    private void validateExerciseNameNotEmpty(String exerciseName) throws CustomExceptions.InsufficientInput {
        if (exerciseName.isEmpty()) {
            throw new CustomExceptions.InsufficientInput(ErrorConstant.INVALID_GYM_STATION_EMPTY_NAME_ERROR);
        }
    }

    private void validateExerciseNamePattern(String exerciseName) throws CustomExceptions.InvalidInput {
        if (!exerciseName.matches(UiConstant.VALID_GYM_STATION_NAME_REGEX)) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_GYM_STATION_NAME_ERROR);
        }
    }

    private void validateExerciseNameLength(String exerciseName) throws CustomExceptions.InvalidInput {
        if (exerciseName.length() > WorkoutConstant.MAX_GYM_STATION_NAME_LENGTH) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_GYM_STATION_NAME_ERROR);
        }
    }

    private void validateWeightIsPositive(double weight) throws CustomExceptions.InvalidInput {
        if (weight < WorkoutConstant.MIN_GYM_WEIGHT){
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_WEIGHTS_ARRAY_FORMAT_ERROR);
        }
    }

    private void validateWeightDoesNotExceedMax(double weight) throws CustomExceptions.InvalidInput {
        if (weight > WorkoutConstant.MAX_GYM_WEIGHT) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_WEIGHT_MAX_ERROR);
        }
    }

    private void validateWeightIsMultiple(double weight) throws CustomExceptions.InvalidInput {
        if (weight % WorkoutConstant.WEIGHT_MULTIPLE != 0 ){
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_WEIGHTS_VALUE_ERROR);
        }
    }

    private void checkIfNumberOfWeightsMatchesSets(ArrayList<Double> weights, int numberOfSets)
            throws CustomExceptions.InvalidInput {
        if (weights.size() != numberOfSets){
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_WEIGHTS_NUMBER_ERROR);
        }
    }

    private void validateWeightString(String weightsString) throws CustomExceptions.InvalidInput {
        if (weightsString.isBlank()) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_WEIGHTS_EMPTY_ERROR);
        }

        if (!weightsString.matches(UiConstant.VALID_WEIGHTS_ARRAY_REGEX)) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_WEIGHTS_ARRAY_FORMAT_ERROR);
        }
    }

    private void appendIntoStations(GymStation station) {
        stations.add(station);
    }
}
