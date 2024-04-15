package workouts;

import constants.ErrorConstant;
import constants.UiConstant;
import constants.WorkoutConstant;
import utility.CustomExceptions;
import utility.Validation;

import java.util.ArrayList;

/**
 * Represents a GymStation object.
 */
public class GymStation {
    //@@author JustinSoh
    private final String stationName;
    private final ArrayList<GymSet> sets = new ArrayList<>();
    private final int numberOfSets;

    /**
     * Constructs a new GymStation object that contains the name, weight, number of repetitions and number of sets done
     * in one station.
     *
     * @param exerciseName        The name of the gym station.
     *                            The name should not be empty and should not exceed the maximum length.
     *                            The name should also follow the pattern (UiConstant.VALID_GYM_STATION_NAME_REGEX)
     * @param numberOfSetsStr     The number of sets done.
     *                            The number of sets should be a positive integer.
     *                            The number of sets should not be empty.
     *                            The number of sets should match the number of weights provided in the weights string
     * @param numberOfRepetitions The number of repetitions done for each set.
     * @param weightsString       The weights done for each set.
     *                            The weights should be in the format "weight1,weight2,weight3..."
     * @throws CustomExceptions.InvalidInput If an invalid input is passed in.
     * @throws CustomExceptions.InsufficientInput If input is empty.
     */
    public GymStation(String exerciseName, String numberOfSetsStr, String numberOfRepetitions, String weightsString)
            throws CustomExceptions.InsufficientInput, CustomExceptions.InvalidInput {
        
        // Check input validity
        this.stationName  = validateGymStationName(exerciseName);
        this.numberOfSets = validateNumberOfSets(numberOfSetsStr);
        int validNumberOfReps = validateNumberOfRepetitions(numberOfRepetitions);
        ArrayList<Double> validWeights = processWeightsArray(weightsString);

        // Verify if the number of weights matches the number of sets
        checkIfNumberOfWeightsMatchesSets(validWeights, this.numberOfSets);

        // Process the sets and weights and add them into the gym set object
        processSets(validWeights, validNumberOfReps);
    }

    /**
     * Retrieves the station name for the GymStation object.
     *
     * @return String representing the name for the station.
     */
    public String getStationName() {
        return stationName;
    }

    /**
     * Retrieves an ArrayList of gym sets for the GymStation object.
     *
     * @return The ArrayList of GymSet objects.
     */
    public ArrayList<GymSet> getSets() {
        return sets;
    }

    /**
     * Retrieves the number sets within the GymStation.
     *
     * @return The number of sets done.
     */
    public int getNumberOfSets() {
        return numberOfSets;
    }

    /**
     * Retrieves the string representation of a GymStation object.
     *
     * @return A formatted string representing a GymStation object.
     */
    @Override
    public String toString() {
        StringBuilder returnString = new StringBuilder(String.format(WorkoutConstant.GYM_STATION_FORMAT,
                this.getStationName()) + String.format(WorkoutConstant.INDIVIDUAL_GYM_STATION_FORMAT,
                this.getNumberOfSets()));

        for (int i = 0; i < this.getNumberOfSets(); i++) {
            returnString.append(System.lineSeparator());
            returnString.append(String.format(WorkoutConstant.GYM_SET_INDEX_FORMAT, i+1 ,
                    this.getSets().get(i).toString()));
        }
        return returnString.toString();
    }

    // Protected Functions
    /**
     * Retrieves the string representation of a GymStation object for writing into a file.
     * Formats the string in the following format
     * "[Exercise Name]:[Number of Sets]:[Repetitions]:[Weights1, Weight2,Weight3 ...]"
     *
     * @return A formatted string representing a GymStation object with the format above.
     */
    protected String toFileString(){
        StringBuilder fileString = new StringBuilder();
        String stationName = getStationName();
        String numOfSets = String.valueOf(getNumberOfSets());
        String gymRepString = toRepString().split(UiConstant.SPLIT_BY_COMMAS)[0];
        String gymWeightString = toWeightString();
        fileString.append(stationName);
        fileString.append(UiConstant.SPLIT_BY_COLON);
        fileString.append(numOfSets);
        fileString.append(UiConstant.SPLIT_BY_COLON);
        fileString.append(gymRepString);
        fileString.append(UiConstant.SPLIT_BY_COLON);
        fileString.append(gymWeightString);
        return fileString.toString();
    }

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

    // Private Methods
    /**
     * Retrieves the string representation of a GymStation object with commas.
     * E.g. toRepString(",") returns "1,2,3"
     *
     * @return A formatted string representing a GymStation object with the specified delimiter.
     */
    private String toRepString() {
        StringBuilder repString = new StringBuilder();
        for (int i = 0; i < sets.size(); i++) {
            String currentRep = String.valueOf(sets.get(i).getNumberOfRepetitions());
            repString.append(currentRep);
            if (i != sets.size() - 1) {
                repString.append(UiConstant.SPLIT_BY_COMMAS);
            }
        }
        return repString.toString();
    }

    /**
     * Retrieves the string representation of a GymStation object with commas.
     * E.g. toWeightString(",") returns "10,20,30"
     *
     * @return A formatted string representing a GymStation object with the specified delimiter.
     */
    private String toWeightString(){
        StringBuilder weightString = new StringBuilder();
        for (int i = 0; i < sets.size(); i++) {
            String currentRep = String.valueOf(sets.get(i).getWeight());
            weightString.append(currentRep);
            if (i != sets.size() - 1) {
                weightString.append(UiConstant.SPLIT_BY_COMMAS);
            }
        }
        return weightString.toString();
    }

    /**
     * Validates the number of repetitions ensuring that it is a positive integer.
     *
     * @param numberOfRepetitions The string representing the number of repetitions
     * @return int representing the number of repetitions
     * @throws CustomExceptions.InvalidInput if an invalid number of repetitions is passed in
     */
    private int validateNumberOfRepetitions(String numberOfRepetitions) throws CustomExceptions.InvalidInput {
        boolean isRepsValid = Validation.validateIntegerIsPositive(numberOfRepetitions);
        if (!isRepsValid) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_REPS_POSITIVE_DIGIT_ERROR);
        }
        return Integer.parseInt(numberOfRepetitions);
    }

    /**
     * Function which adds a GymSet object to GymStation.
     *
     * @param weightsList     The weight done for the particular set.
     * @param numberOfRepetitions The number of repetitions done for the particular set.
     */
    private void processSets(ArrayList<Double> weightsList, int numberOfRepetitions) {
        for (int i = 0; i < numberOfSets; i++) {
            GymSet newSet = new GymSet(weightsList.get(i), numberOfRepetitions);
            sets.add(newSet);
        }
    }

    /**
     * Validates the weight string ensuring that
     * - The weight does not exceed the maximum weight (@code WorkoutConstant.MAX_GYM_WEIGHT)
     * - The weight is a multiple of 0.125 (as that is the increment of weights in a gym)
     *
     * @param weight The string representing the weight
     * @return boolean true  if the weight is valid
     * @throws CustomExceptions.InvalidInput if an invalid weight is passed in
     */
    private boolean validateWeight(String weight) throws CustomExceptions.InvalidInput {
        double weightDouble = Double.parseDouble(weight);
        validateWeightDoesNotExceedMax(weightDouble);
        validateWeightIsMultiple(weightDouble);
        return true;
    }


    /**
     * Validates the exercise name ensuring that it is not empty.
     *
     * @param exerciseName The name of the exercise.
     * @throws CustomExceptions.InsufficientInput if the exercise name is empty.
     */
    private void validateExerciseNameNotEmpty(String exerciseName) throws CustomExceptions.InsufficientInput {
        if (exerciseName.isEmpty()) {
            throw new CustomExceptions.InsufficientInput(ErrorConstant.INVALID_GYM_STATION_EMPTY_NAME_ERROR);
        }
    }

    /**
     * Validates the exercise name pattern ensuring that it does not contain any special characters.
     *
     * @param exerciseName The name of the exercise.
     * @throws CustomExceptions.InvalidInput if the exercise name does not match the pattern.
     */
    private void validateExerciseNamePattern(String exerciseName) throws CustomExceptions.InvalidInput {
        if (!exerciseName.matches(UiConstant.VALID_GYM_STATION_NAME_REGEX)) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_GYM_STATION_NAME_ERROR);
        }
    }

    /**
     * Validates the length of the exercise name.
     *
     * @param exerciseName The name of the exercise.
     * @throws CustomExceptions.InvalidInput if the exercise name exceeds the maximum length.
     */
    private void validateExerciseNameLength(String exerciseName) throws CustomExceptions.InvalidInput {
        if (exerciseName.length() > WorkoutConstant.MAX_GYM_STATION_NAME_LENGTH) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_GYM_STATION_NAME_ERROR);
        }
    }

    /**
     * Validates that the weight does not exceed the maximum weight.
     *
     * @param weight The weight to be validated.
     * @throws CustomExceptions.InvalidInput if the weight exceeds the maximum weight.
     */
    private void validateWeightDoesNotExceedMax(double weight) throws CustomExceptions.InvalidInput {
        if (weight > WorkoutConstant.MAX_GYM_WEIGHT) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_WEIGHT_MAX_ERROR);
        }
    }

    /**
     * Validates that the weight is a multiple of 0.125.
     *
     * @param weight The weight to be validated.
     * @throws CustomExceptions.InvalidInput if the weight is not a multiple of 0.125.
     */
    private void validateWeightIsMultiple(double weight) throws CustomExceptions.InvalidInput {
        if (weight % WorkoutConstant.WEIGHT_MULTIPLE != 0 ){
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_WEIGHTS_VALUE_ERROR);
        }
    }

    /**
     * Checks if the number of weights matches the number of sets.
     *
     * @param weights The list of weights.
     * @param numberOfSets The number of sets.
     * @throws CustomExceptions.InvalidInput if the number of weights does not match the number of sets.
     */
    private void checkIfNumberOfWeightsMatchesSets(ArrayList<Double> weights, int numberOfSets)
            throws CustomExceptions.InvalidInput {
        if (weights.size() != numberOfSets){
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_WEIGHTS_NUMBER_ERROR);
        }
    }

    /**
     * Validates the weight string ensuring that
     * - The weight string is not empty
     * - The weight string follows the correct format (UiConstant.VALID_WEIGHTS_ARRAY_REGEX)
     *
     * @param weightsString The string representing the weights
     * @throws CustomExceptions.InvalidInput if an invalid weight string is passed in
     */
    private void validateWeightString(String weightsString) throws CustomExceptions.InvalidInput {
        if (weightsString.isBlank()) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_WEIGHTS_EMPTY_ERROR);
        }

        if (!weightsString.matches(UiConstant.VALID_WEIGHTS_ARRAY_REGEX)) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_WEIGHTS_ARRAY_FORMAT_ERROR);
        }
    }
}


