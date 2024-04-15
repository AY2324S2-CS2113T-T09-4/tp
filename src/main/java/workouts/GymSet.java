package workouts;

import constants.WorkoutConstant;

/**
 * Represents a GymSet object.
 */
public class GymSet {
    //@@author JustinSoh
    private final double weight;
    private final int numberOfRepetitions;

    /**
     * Constructs a new GymSet object using the weight and reps.
     *
     * @param weight The weight done for the set.
     * @param numberOfRepetitions The number of reps done for the set.
     */
    public GymSet(Double weight, int numberOfRepetitions){
        this.weight = weight;
        this.numberOfRepetitions = numberOfRepetitions;
    }

    public double getWeight() {
        return weight;
    }

    public int getNumberOfRepetitions() {
        return numberOfRepetitions;
    }

    /**
     * Retrieves a string representation of a GymSet object.
     *
     * @return A formatted string representing a GymSet, inclusive of the number of repetitions and weight done.
     */
    @Override
    public String toString() {
        return String.format(WorkoutConstant.GYM_SET_FORMAT, this.numberOfRepetitions, this.weight);
    }
}

