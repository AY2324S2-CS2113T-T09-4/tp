package health;

import java.time.LocalDate;

/**
 * The Health class represents a Health object to track user's health information.
 */
public class Health {
    //@@author j013n3
    private LocalDate date = null;

    public Health() {
    }

    /**
     * Retrieves the date of Health object of LocalDate type.
     *
     * @return The date of the Health object.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns a string containing the date of the Health object.
     *
     * @return A formatted string representing a Health object.
     */
    @Override
    public String toString(){
        return getDate().toString();
    }
}
