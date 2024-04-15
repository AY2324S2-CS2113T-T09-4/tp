package health;

import constants.UiConstant;
import storage.LogFile;
import utility.CustomExceptions;
import constants.ErrorConstant;
import constants.HealthConstant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import ui.Output;

/**
 * The HealthList class inherits from ArrayList.
 * It contains the individual lists of Bmi, Appointment, and Period objects.
 * Methods to get, add and print Health objects are listed here.
 */
public class HealthList extends ArrayList<Health> {
    //@@author j013n3
    static LogFile logFile = LogFile.getInstance();
    private static final ArrayList<Bmi> BMIS = new ArrayList<>();
    private static final ArrayList<Period> PERIODS = new ArrayList<>();

    private static final ArrayList<Appointment> APPOINTMENTS = new ArrayList<>();

    public HealthList() {

    }

    /**
     * Retrieves all Bmi objects within BMIS.
     *
     * @return The BMIS array list.
     */
    public static ArrayList<Bmi> getBmis() {
        return BMIS;
    }

    /**
     * Retrieves all Period objects within PERIODS.
     *
     * @return The PERIODS array list.
     */
    public static ArrayList<Period> getPeriods() {
        return PERIODS;
    }

    /**
     * Retrieves all Appointment objects within APPOINTMENTS.
     *
     * @return The APPOINTMENTS array list.
     */
    public static ArrayList<Appointment> getAppointments() {
        return APPOINTMENTS;
    }

    /**
     * Retrieves the Period object at a specified index.
     *
     * @param index The index of the Period object.
     * @return The Period object at the specified index, or null if the index is out of bounds.
     */
    public static Period getPeriod(int index) {
        if (index < HealthConstant.FIRST_ITEM || index >= PERIODS.size()) {
            return null;
        }
        return PERIODS.get(index);
    }

    /**
     * Retrieves the number of Period objects recorded.
     *
     * @return The number of Period objects recorded.
     */
    public static int getPeriodSize() {
        return PERIODS.size();
    }

    //@@author L5-Z
    /**
     * Retrieves size of BMIS list.
     *
     * @return Size of BMIS list.
     */
    public static int getBmisSize() {
        return BMIS.size();
    }

    /**
     * Retrieves size of PERIODS list.
     *
     * @return Size of PERIODS list.
     */
    public static int getPeriodsSize() {
        return PERIODS.size();
    }

    //@@author L5-Z
    /**
     * Deletes Bmi object based on a specified index and prints delete message if successful.
     *
     * @param index Index of the Bmi object to be deleted.
     * @throws CustomExceptions.OutOfBounds If the index of the Bmi object given does not exist.
     */
    public static void deleteBmi(int index) throws CustomExceptions.OutOfBounds {
        if (index < HealthConstant.FIRST_ITEM) {
            throw new CustomExceptions.OutOfBounds(ErrorConstant.BMI_EMPTY_ERROR);
        } else if (index >= BMIS.size()) {
            throw new CustomExceptions.OutOfBounds(ErrorConstant.INVALID_INDEX_DELETE_ERROR);
        }
        assert !BMIS.isEmpty() : ErrorConstant.EMPTY_BMI_LIST_ERROR;
        Bmi deletedBmi = BMIS.get(index);
        Output.printLine();
        System.out.printf((HealthConstant.LOG_DELETE_BMI_FORMAT) + System.lineSeparator(),
                deletedBmi.getBmiValueDouble(),
                deletedBmi.getDate());
        Output.printLine();
        BMIS.remove(index);
        LogFile.writeLog(HealthConstant.BMI_REMOVED_MESSAGE_PREFIX + index, false);
    }

    /**
     * Deletes Period object based on a specified index and prints delete message if successful.
     *
     * @param index Index of the Period object to be deleted.
     * @throws CustomExceptions.OutOfBounds If the index of the Period object given does not exist.
     */
    public static void deletePeriod(int index) throws CustomExceptions.OutOfBounds {
        if (index < HealthConstant.FIRST_ITEM) {
            throw new CustomExceptions.OutOfBounds(ErrorConstant.PERIOD_EMPTY_ERROR);
        } else if(index >= PERIODS.size()) {
            throw new CustomExceptions.OutOfBounds(ErrorConstant.INVALID_INDEX_DELETE_ERROR);
        }
        assert !PERIODS.isEmpty() : ErrorConstant.EMPTY_PERIOD_LIST_ERROR;
        Period deletedPeriod = PERIODS.get(index);
        String endDateUnit = (deletedPeriod.getEndDate() == null) ?
                ErrorConstant.NO_DATE_SPECIFIED_ERROR : deletedPeriod.getEndDate().toString();
        Output.printLine();
        System.out.printf((HealthConstant.LOG_DELETE_PERIOD_FORMAT) + System.lineSeparator(),
                deletedPeriod.getStartDate(),
                endDateUnit);
        PERIODS.remove(index);
        Output.printLine();
        LogFile.writeLog(HealthConstant.PERIOD_REMOVED_MESSAGE_PREFIX + index, false);
    }

    //@@author syj02

    /**
     * Deletes Appointment object based on a specified index and prints delete message if successful.
     *
     * @param index Index of the Appointment object to be deleted.
     * @throws CustomExceptions.OutOfBounds If the index of the Appointment object given does not exist.
     */
    public static void deleteAppointment(int index) throws CustomExceptions.OutOfBounds {
        if (index < HealthConstant.FIRST_ITEM) {
            throw new CustomExceptions.OutOfBounds(ErrorConstant.APPOINTMENT_EMPTY_ERROR);
        } else if (index >= APPOINTMENTS.size()) {
            throw new CustomExceptions.OutOfBounds(ErrorConstant.INVALID_INDEX_DELETE_ERROR);
        }
        assert !APPOINTMENTS.isEmpty() : ErrorConstant.EMPTY_APPOINTMENT_LIST_ERROR;
        Appointment deletedAppointment = APPOINTMENTS.get(index);
        Output.printLine();
        System.out.printf((HealthConstant.LOG_DELETE_APPOINTMENT_FORMAT) + System.lineSeparator(),
                deletedAppointment.getDate(),
                deletedAppointment.getTime(),
                deletedAppointment.getDescription());
        Output.printLine();
        APPOINTMENTS.remove(index);
        LogFile.writeLog(HealthConstant.APPOINTMENT_REMOVED_MESSAGE_PREFIX + index, false);
        if (!APPOINTMENTS.isEmpty()) {
            printAppointmentHistory();
        }
    }

    /**
     * Prints the latest Bmi object added.
     *
     * @throws CustomExceptions.OutOfBounds if BMIS is empty.
     * @throws AssertionError If BMIS is empty.
     */
    public static void printLatestBmi() throws CustomExceptions.OutOfBounds {
        if (BMIS.isEmpty()) {
            throw new CustomExceptions.OutOfBounds(ErrorConstant.BMI_EMPTY_ERROR);
        }
        assert !BMIS.isEmpty() : ErrorConstant.EMPTY_BMI_LIST_ERROR;
        System.out.println(BMIS.get(HealthConstant.FIRST_ITEM));
    }

    //@@author j013n3
    /**
     * Prints the latest Period object added.
     *
     * @throws CustomExceptions.OutOfBounds If PERIODS is empty.
     * @throws AssertionError If PERIODS is empty.
     */
    public static void printLatestPeriod() throws CustomExceptions.OutOfBounds {
        if (PERIODS.isEmpty()) {
            throw new CustomExceptions.OutOfBounds(ErrorConstant.PERIOD_EMPTY_ERROR);
        }
        assert !PERIODS.isEmpty() : ErrorConstant.EMPTY_PERIOD_LIST_ERROR;
        System.out.println(PERIODS.get(HealthConstant.FIRST_ITEM));
    }

    //@@author syj_02
    /**
     * Prints the latest Appointment object added.
     *
     * @throws CustomExceptions.OutOfBounds If Appointment list is empty.
     * @throws AssertionError If APPOINTMENTS is empty.
     */
    public static void printLatestAppointment() throws CustomExceptions.OutOfBounds {
        if (APPOINTMENTS.isEmpty()) {
            throw new CustomExceptions.OutOfBounds(ErrorConstant.APPOINTMENT_EMPTY_ERROR);
        }
        assert !APPOINTMENTS.isEmpty() : ErrorConstant.EMPTY_APPOINTMENT_LIST_ERROR;
        int index = APPOINTMENTS.size() - 1;
        System.out.println(APPOINTMENTS.get(index));
    }

    /**
     * Prints all the Bmi objects recorded.
     *
     * @throws CustomExceptions.OutOfBounds if BMI list is empty.
     * @throws AssertionError If BMIS list is empty.
     */
    public static void printBmiHistory() throws CustomExceptions.OutOfBounds {
        if (BMIS.isEmpty()) {
            throw new CustomExceptions.OutOfBounds(ErrorConstant.BMI_EMPTY_ERROR);
        }
        assert !BMIS.isEmpty() : ErrorConstant.EMPTY_BMI_LIST_ERROR;
        int index = 1;
        System.out.println(HealthConstant.BMI_HISTORY_HEADER);
        for (Bmi bmi : BMIS) {
            System.out.print(index + UiConstant.FULL_STOP + UiConstant.SPLIT_BY_WHITESPACE);
            System.out.println(bmi);
            index += 1;
        }
    }

    //@@author j013n3
    /**
     * Prints all the Period objects recorded.
     *
     * @throws CustomExceptions.OutOfBounds If PERIODS list is empty.
     * @throws AssertionError If PERIODS list is empty.
     */
    public static void printPeriodHistory() throws CustomExceptions.OutOfBounds {
        if (PERIODS.isEmpty()) {
            throw new CustomExceptions.OutOfBounds(ErrorConstant.PERIOD_EMPTY_ERROR);
        }
        assert !PERIODS.isEmpty() : ErrorConstant.EMPTY_PERIOD_LIST_ERROR;
        int index = 1;
        System.out.println(HealthConstant.PERIOD_HISTORY_HEADER);
        for (Period period : PERIODS) {
            System.out.print(index + UiConstant.FULL_STOP + UiConstant.SPLIT_BY_WHITESPACE);
            System.out.println(period);
            index += 1;
        }
    }

    //@@author syj02
    /**
     * Prints all the Appointment objects recorded.
     *
     * @throws utility.CustomExceptions.OutOfBounds If APPOINTMENTS list is empty.
     * @throws AssertionError If APPOINTMENTS list is empty.
     */
    public static void printAppointmentHistory() throws CustomExceptions.OutOfBounds {
        if (APPOINTMENTS.isEmpty()) {
            throw new CustomExceptions.OutOfBounds(ErrorConstant.APPOINTMENT_EMPTY_ERROR);
        }
        assert !APPOINTMENTS.isEmpty() : ErrorConstant.EMPTY_APPOINTMENT_LIST_ERROR;
        int index = 1;
        System.out.println(HealthConstant.APPOINTMENT_HISTORY_HEADER);
        for (Appointment appointment : APPOINTMENTS) {
            System.out.print(index + UiConstant.FULL_STOP + UiConstant.SPLIT_BY_WHITESPACE);
            System.out.println(appointment);
            index += 1;
        }
    }

    //@@author L5-Z
    /**
     * Clears PERIODS, BMIS and APPOINTMENTS lists.
     *
     * @throws AssertionError If PERIODS, BMIS and APPOINTMENTS lists are not empty.
     */
    public static void clearHealthLists() {
        PERIODS.clear();
        BMIS.clear();
        APPOINTMENTS.clear();
        assert BMIS.isEmpty() : ErrorConstant.BMI_LIST_UNCLEARED_ERROR;
        assert PERIODS.isEmpty() : ErrorConstant.PERIOD_LIST_UNCLEARED_ERROR;
        assert APPOINTMENTS.isEmpty() : ErrorConstant.APPOINTMENT_LIST_UNCLEARED_ERROR;
    }

    //@@author j013n3
    /**
     * Prints the last three Period objects added to PERIODS.
     */
    public static void printLatestThreeCycles() {
        Output.printLine();
        int startIndex = HealthConstant.FIRST_ITEM;
        int endIndex = HealthConstant.LATEST_THREE_CYCLE_LENGTHS;
        assert startIndex >= HealthConstant.FIRST_ITEM : ErrorConstant.START_INDEX_NEGATIVE_ERROR;

        for (int i = startIndex; i < endIndex; i++) {
            System.out.println(PERIODS.get(i));
        }

    }

    /**
     * Predicts the start date of the next period based on the average cycle length of the last three cycles.
     *
     * @return The predicted start date of the next period.
     * @throws AssertionError If PERIODS is empty.
     * @throws CustomExceptions.OutOfBounds If PERIODS is empty.
     */
    public static LocalDate predictNextPeriodStartDate() throws CustomExceptions.OutOfBounds {
        if (PERIODS.isEmpty()) {
            throw new CustomExceptions.OutOfBounds(ErrorConstant.PERIOD_EMPTY_ERROR);
        }
        assert !PERIODS.isEmpty() : ErrorConstant.EMPTY_PERIOD_LIST_ERROR;
        Period latestPeriod = PERIODS.get(HealthConstant.FIRST_ITEM);
        return latestPeriod.nextCyclePrediction();
    }

    //@@author j013n3
    /**
     * Adds a Bmi object to BMIS.
     *
     * @param bmi Bmi object.
     * @throws AssertionError If Bmi object is null.
     */
    protected void addBmi(Bmi bmi) {
        assert bmi != null : ErrorConstant.NULL_BMI_ERROR;
        BMIS.add(bmi);
        // bmi sorted from latest to earliest date
        BMIS.sort(Comparator.comparing(Bmi::getDate).reversed());
    }

    //@@author syj02
    /**
     * Adds a Period object to PERIODS.
     *
     * @param period Period object to be added.
     * @throws AssertionError If Period object is null.
     */
    protected void addPeriod(Period period) {
        assert period != null : ErrorConstant.NULL_PERIOD_ERROR;

        PERIODS.add(period);

        PERIODS.sort(Comparator.comparing(Period::getStartDate).reversed());

        int size = PERIODS.size();
        if (size > HealthConstant.MIN_SIZE_FOR_COMPARISON) {
            for (int i = size - 1; i > HealthConstant.FIRST_ITEM; i--) {
                Period newerPeriod = PERIODS.get(i -  1);
                Period olderPeriod = PERIODS.get(i);
                olderPeriod.setCycleLength(newerPeriod.getStartDate());
            }
        }
    }

    /**
     * Adds an Appointment to APPOINTMENTS.
     * Sorts all Appointment objects in APPOINTMENTS by date and time of the appointments with
     * the earliest appointment at the top.
     *
     * @param appointment Appointment object.
     * @throws AssertionError If Appointment object is null.
     */
    protected void addAppointment(Appointment appointment) {
        assert appointment != null : ErrorConstant.NULL_APPOINTMENT_ERROR;
        APPOINTMENTS.add(appointment);
        APPOINTMENTS.sort(Comparator.comparing(Appointment::getDate).thenComparing(Appointment::getTime));
    }
}
