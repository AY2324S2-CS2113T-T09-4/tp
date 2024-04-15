package health;

import constants.ErrorConstant;
import constants.HealthConstant;
import utility.Parser;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The Appointment class inherits from the Health class.
 * It contains information about the date, time, and description of the appointment.
 */
public class Appointment extends Health {
    //@@author syj_02
    private LocalDate date;
    private LocalTime time;
    private String description;
    private final Parser parser = new Parser();
    private final HealthList healthList = new HealthList();

    /**
     * Constructor for Appointment object.
     *
     * @param stringDate A string representing the date of the appointment.
     * @param stringTime A string representing the time of the appointment.
     * @param description A string describing the appointment.
     */
    public Appointment(String stringDate, String stringTime, String description) {
        this.date = parser.parseDate(stringDate);
        this.time = parser.parseTime(stringTime);
        this.description = description;
        healthList.addAppointment(this);
    }

    /**
     * Retrieves the date of the appointment of LocalDate type.
     *
     * @return The date of appointment.
     */
    public LocalDate getDate() {
        assert date != null : ErrorConstant.NULL_DATE_ERROR;
        return this.date;
    }

    /**
     * Retrieves the time of the appointment of LocalTime type.
     *
     * @return The time of appointment.
     */
    public LocalTime getTime() {
        assert time != null : ErrorConstant.NULL_TIME_ERROR;
        return this.time;
    }

    /**
     * Retrieves the description of the appointment of String type.
     *
     * @return The description of appointment.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the string representation of an Appointment object.
     *
     * @return A formatted string representing an Appointment object.
     */
    @Override
    public String toString() {
        return String.format(HealthConstant.PRINT_APPOINTMENT_FORMAT,
                getDate(),
                getTime(),
                getDescription());
    }
}
