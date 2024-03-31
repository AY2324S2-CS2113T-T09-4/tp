package utility;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidationTest {
    /**
     * Tests the behaviour of the validateDateInput function with a correctly formatted string.
     * Expects no exception to be thrown.
     */
    @Test
    public void validateDateInput_validDate_noExceptionThrown() {
        String validDate = "09-11-2024";
        assertDoesNotThrow(() -> {
            Validation.validateDateInput(validDate);
        });
    }

    /**
     * Tests the behaviour of the validateDateInput function when an invalid day in date string is passed.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    public void validateDateInput_invalidDayFormat_throwInvalidInputException() {
        String invalidDate = "9-11-2024";

        assertThrows(CustomExceptions.InvalidInput.class, () -> {
            Validation.validateDateInput(invalidDate);
        });
    }

    /**
     * Tests the behaviour of the validateDateInput function when an invalid month in date string is passed.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    public void validateDateInput_invalidMonthFormat_throwInvalidInputException() {
        String invalidDate = "9-1-2024";
        assertThrows(CustomExceptions.InvalidInput.class, () -> {
            Validation.validateDateInput(invalidDate);
        });
    }

    /**
     * Tests the behaviour of the validateDateInput function when an invalid year in date string is passed.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    public void validateDateInput_invalidYearFormat_throwInvalidInputException() {
        String invalidDate = "9-11-24";
        assertThrows(CustomExceptions.InvalidInput.class, () -> {
            Validation.validateDateInput(invalidDate);
        });
    }

    /**
     * Tests the behaviour of the validateDateInput function when an illegal day in date string is passed.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    public void validateDateInput_illegalDayNumber_throwInvalidInputException() {
        String invalidDate = "32-11-2024";
        assertThrows(CustomExceptions.InvalidInput.class, () -> {
            Validation.validateDateInput(invalidDate);
        });
    }

    /**
     * Tests the behaviour of the validateDateInput function when 0 is passed in as day for date string.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    public void validateDateInput_zeroDayNumber_throwInvalidInputException() {
        String invalidDate = "00-11-2024";
        assertThrows(CustomExceptions.InvalidInput.class, () -> {
            Validation.validateDateInput(invalidDate);
        });
    }

    /**
     * Tests the behaviour of the validateDateInput function when an illegal month in date string is passed.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    public void validateDateInput_illegalMonthNumber_throwInvalidInputException() {
        String invalidDate = "09-13-2024";
        assertThrows(CustomExceptions.InvalidInput.class, () -> {
            Validation.validateDateInput(invalidDate);
        });
    }

    /**
     * Tests the behaviour of the validateDateInput function when 0 is passed in as month for date string.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    public void validateDateInput_zeroMonthNumber_throwInvalidInputException() {
        String invalidDate = "09-00-2024";
        assertThrows(CustomExceptions.InvalidInput.class, () -> {
            Validation.validateDateInput(invalidDate);
        });
    }

    /**
     * Tests the behaviour of the validateDateInput function when wrong delimiter is used.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    public void validateDateInput_wrongDateDelimiter_throwInvalidInputException() {
        String invalidDate = "09/12/2024";
        assertThrows(CustomExceptions.InvalidInput.class, () -> {
            Validation.validateDateInput(invalidDate);
        });
    }

    /**
     * Tests the behaviour of the validateDateInput function when the year is left out in date string.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    public void validateDateInput_invalidDateParameters_throwInvalidInputException() {
        String invalidDate = "09-12";
        assertThrows(CustomExceptions.InvalidInput.class, () -> {
            Validation.validateDateInput(invalidDate);
        });
    }

    /**
     * Tests the behaviour of correct parameters being passed to validateDate.
     * Expects no exception to be thrown.
     */
    @Test
    void validateDeleteInput_correctInput_noExceptionThrown() {
        String[] input = {"appointment", "2"};
        assertDoesNotThrow(() -> Validation.validateDeleteInput(input));
    }

    /**
     * Tests the behaviour of an invalid item string being passed to validateDeleteInput.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validateDeleteInput_invalidItem_expectInvalidInputException() {
        String[] input = {"free!", "2"};
        assertThrows(CustomExceptions.InvalidInput.class, () -> Validation.validateDeleteInput(input));
    }

    /**
     * Tests the behaviour of an invalid index string being passed to validateDeleteInput.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validateDeleteInput_invalidIndex_expectInvalidInputException() {
        String[] input = {"item", "-a"};
        assertThrows(CustomExceptions.InvalidInput.class, () -> Validation.validateDeleteInput(input));
    }

    /**
     * Tests the behaviour of an empty string being passed to validateDeleteInput.
     * Expects InsufficientInput exception to be thrown.
     */
    @Test
    void validateDeleteInput_emptyString_expectInsufficientInputException() {
        String[] input = {"item", ""};
        assertThrows(CustomExceptions.InsufficientInput.class, () -> Validation.validateDeleteInput(input));
    }

    /**
     * Tests the behaviour of correct parameters being passed into validateBmi.
     * Expects no exceptions to be thrown.
     */
    @Test
    void validateBmi_correctParameters_noExceptionThrown() {
        String[] input = {"1.71", "70.00", "22-02-2024"};
        assertDoesNotThrow(() -> Validation.validateBmiInput(input));
    }

    /**
     * Tests the behaviour of 1 decimal point weight number being passed into splitBmi.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validateBmi_oneDecimalPointWeight_throwsInvalidInputException() {
        String[] input = {"1.71", "70.0", "29-04-2024"};
        assertThrows(CustomExceptions.InvalidInput.class, () -> Validation.validateBmiInput(input));
    }

    /**
     * Tests the behaviour of 1 decimal point height number being passed into splitBmi.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validateBmi_oneDecimalPointHeight_throwsInvalidInputException() {
        String[] input = {"1.7", "70.03", "29-04-2024"};
        assertThrows(CustomExceptions.InvalidInput.class, () -> Validation.validateBmiInput(input));
    }

    /**
     * Tests the behaviour of a date far in the future is passed into splitBmi.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validateBmi_dateAfterToday_throwsInvalidInputException() {
        String[] input = {"1.70", "70.03", "28-03-2025"};
        assertThrows(CustomExceptions.InvalidInput.class, () -> Validation.validateBmiInput(input));
    }

    /**
     * Tests the behaviour of correct parameters being passed into validatePeriod.
     * Expects no exception to be thrown.
     */
    @Test
    void validatePeriod_correctParameters_noExceptionThrown()  {
        String [] input = {"23-03-2024", "30-03-2024"};
        assertDoesNotThrow(() -> Validation.validatePeriodInput(input));
    }

    /**
     * Tests the behaviour of a string with an empty string being passed into validatePeriod.
     * Expects InsufficientInput exception to be thrown.
     */
    @Test
    void validatePeriod_emptyParameter_throwsInsufficientInputException() {
        String [] input = {"", "29-03-2024"};
        assertThrows(CustomExceptions.InsufficientInput.class, () -> Validation.validatePeriodInput(input));
    }

    /**
     * Tests the behaviour of a string with invalid start date being passed into validatePeriod.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validatePeriod_invalidStartDate_throwsInvalidInputException() {
        String [] input = {"32-04-2024", "29-04-2024"};
        assertThrows(CustomExceptions.InvalidInput.class, () -> Validation.validatePeriodInput(input));
    }

    /**
     * Tests the behaviour of a string with invalid end date being passed into validatePeriod.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validatePeriod_invalidEndDate_throwsInvalidInputException() {
        String [] input = {"28-04-2024", "29-13-2024"};
        assertThrows(CustomExceptions.InvalidInput.class, () -> Validation.validatePeriodInput(input));
    }

    /**
     * Tests the behaviour of a start date far in the future being passed into validatePeriod.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validatePeriod_dateAfterToday_throwsInvalidInputException() {
        String [] input = {"28-04-2025", "29-13-2025"};
        assertThrows(CustomExceptions.InvalidInput.class, () -> Validation.validatePeriodInput(input));
    }

    /**
     * Tests the behaviour of an end date before the start date being passed into validatePeriod.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validatePeriod_endDateBeforeStartDate_throwsInvalidInputException() {
        String [] input = {"28-03-2024", "22-03-2024"};
        assertThrows(CustomExceptions.InvalidInput.class, () -> Validation.validatePeriodInput(input));
    }

    /**
     * Tests the behaviour of correct parameters being passed into validateAppointment.
     * Expects no exception to be thrown.
     */
    @Test
    void validateAppointment_correctParameters_noExceptionThrown() {
        String[] input = {"29-04-2024", "19:30", "test description"};
        assertDoesNotThrow(() -> Validation.validateAppointmentDetails(input));
    }

    /**
     * Tests the behaviour of an empty string being passed into validateAppointment.
     * Expects InsufficientInput exception to be thrown.
     */
    @Test
    void validateAppointment_emptyParameters_throwsInsufficientInputException() {
        String[] input = {"29-04-2024", "19:30", ""};
        assertThrows(CustomExceptions.InsufficientInput.class, () -> Validation.validateAppointmentDetails(input));
    }

    /**
     * Tests the behaviour of an incorrectly formatted date being passed into validateAppointment.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validateAppointment_incorrectDateFormat_throwsInvalidInputException() {
        String[] input = {"32-04-2024", "19:30", "test description"};
        assertThrows(CustomExceptions.InvalidInput.class, () -> Validation.validateAppointmentDetails(input));
    }

    /**
     * Tests the behaviour of an incorrectly formatted time being passed into validateAppointment.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validateAppointment_incorrectTimeFormat_throwsInvalidInputException() {
        String[] input = {"28-04-2024", "25:30", "test description"};
        assertThrows(CustomExceptions.InvalidInput.class, () -> Validation.validateAppointmentDetails(input));
    }

    /**
     * Tests the behaviour of a description being more than 100 characters being passed into validateAppointment.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validateAppointment_descriptionTooLong_throwsInvalidInputException() {
        String[] input = {"28-04-2024", "22:30",
                          "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                          "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"};
        assertThrows(CustomExceptions.InvalidInput.class, () -> Validation.validateAppointmentDetails(input));
    }

    /**
     * Tests the behaviour of a correctly formatted time string being passed into validateTimeInput.
     * Expects no exception to be thrown.
     */
    @Test
    void validateTimeInput_correctInput_noExceptionThrown() {
        String input = "23:50";
        assertDoesNotThrow(() -> Validation.validateTimeInput(input));
    }

    /**
     * Tests the behaviour of an incorrect time with the wrong delimiter being passed into validateTimeInput.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validateTimeInput_invalidDelimiter_throwsInvalidInputException() {
        String input = "23-50";
        assertThrows(CustomExceptions.InvalidInput.class, () -> Validation.validateTimeInput(input));
    }

    /**
     * Tests the behaviour of an incorrect time with invalid hours being passed into validateTimeInput.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validateTimeInput_invalidHours_throwsInvalidInputException() {
        String input = "24:50";
        assertThrows(CustomExceptions.InvalidInput.class, () -> Validation.validateTimeInput(input));
    }

    /**
     * Tests the behaviour of an incorrect time with invalid minutes being passed into validateTimeInput.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validateTimeInput_invalidMinutes_throwsInvalidInputException() {
        String input = "21:60";
        assertThrows(CustomExceptions.InvalidInput.class, () -> Validation.validateTimeInput(input));
    }

    /**
     * Tests the behaviour of an incorrect time with letters being passed into validateTimeInput.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validateTimeInput_invalidTimeWithLetters_throwsInvalidInputException() {
        String input = "12:2a";
        assertThrows(CustomExceptions.InvalidInput.class, () -> Validation.validateTimeInput(input));
    }


    /**
     * Tests the behaviour of an incorrect time with seconds included being passed into validateTimeInput.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validateTimeInput_invalidTimeFormat_throwsInvalidInputException() {
        String input = "21:55:44";
        assertThrows(CustomExceptions.InvalidInput.class, () -> Validation.validateTimeInput(input));
    }
}