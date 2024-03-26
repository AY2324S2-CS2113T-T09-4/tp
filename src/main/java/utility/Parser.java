package utility;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents the parser used for PulsePilot
 */
public class Parser {

    /**
     * Parses and converts String date to a LocalDate variable.
     * @param dateTime String representing the date.
     * @return LocalDate variable representing the date.
     *
     * @throws DateTimeParseException If there is an error parsing the date.
     */
    public static LocalDate parseDate(String dateTime) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate formattedDate = null;
        try {
            formattedDate = LocalDate.parse(dateTime, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
        }
        return formattedDate;
    }

    public static LocalTime parseTime(String stringTime) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime formattedTime = null;
        try {
            formattedTime = LocalTime.parse(stringTime, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing time: " + e.getMessage());
        }
        return formattedTime;
    }
}
