package ui;

import constants.ErrorConstant;
import constants.UiConstant;
import constants.WorkoutConstant;
import constants.HealthConstant;
import utility.CustomExceptions;

import workouts.Gym;
import workouts.GymStation;
import workouts.Run;
import workouts.Workout;
import workouts.WorkoutLists;
import health.HealthList;
import health.Bmi;
import health.Period;
import health.Appointment;
import utility.Filters.HistoryAndLatestFilters;

import java.util.ArrayList;

/**
 * The Output class handles printing various messages, data, and ASCII art for the user interface.
 */
public class Output {

    //@@author L5-Z
    /**
     * Prints a horizontal line.
     */
    public static void printLine() {
        System.out.println(UiConstant.PARTITION_LINE);
    }

    /**
     * Prints the help message.
     */
    public void printHelp() {
        printLine();
        System.out.println("Commands List:");
        System.out.println();
        System.out.println("workout /e:run /d:DISTANCE /t:TIME [/date:DATE] - Add a new run");
        System.out.println("workout /e:gym /n:NUMBER_OF_STATIONS [/date:DATE] - Add a new gym workout");
        System.out.println("health /h:bmi /height:HEIGHT /weight:WEIGHT /date:DATE - Add new BMI data");
        System.out.println("health /h:period /start:START_DATE [/end:END_DATE] - Add new period data");
        System.out.println("health /h:prediction - Predicts next period's start date");
        System.out.println("health /h:appointment /date:DATE /time:TIME /description:DESCRIPTION" +
                " - Add new appointment data");

        System.out.println("history /item:[run/gym/workouts/bmi/period/appointment] - " +
                "Shows history of run/gym/workouts/bmi/period/appointment records");
        System.out.println("latest /item:[run/gym/bmi/period/appointment] - " +
                "Shows latest entry of run/gym/bmi/period/appointment records");
        System.out.println("delete /item:[run/gym/bmi/period/appointment] /index:INDEX - " +
                "Deletes a run/gym/bmi/period/appointment record");

        System.out.println("help - Show this help message");
        System.out.println("exit - Exit the program");
        printLine();
    }

    //@@author JustinSoh
    /**
     * Prints the gym station prompt.
     *
     * @param stationNumber Integer representing the current gym station index.
     */
    public void printGymStationPrompt(int stationNumber) {
        printLine();
        System.out.println("Please enter the details of station "
                + stationNumber
                + ". (Format: " + WorkoutConstant.STATION_GYM_FORMAT + ")");
        System.out.println("Enter 'back' to go back to the main menu!");
        printLine();
    }

    /**
     * Prints the text header when adding a new Run.
     *
     * @param newRun The new Run object added.
     */
    public void printAddRun(Run newRun) {
        printLine();
        System.out.println(WorkoutConstant.ADD_RUN);
        System.out.println(WorkoutConstant.RUN_HEADER);
        System.out.println(newRun);
        printLine();
    }

    //@@author j013n3
    /**
     * Prints the message when a new Bmi is added.
     *
     * @param newBmi The new Bmi object added.
     */
    public void printAddBmi(Bmi newBmi) {
        printLine();
        System.out.println(HealthConstant.BMI_ADDED_MESSAGE_PREFIX
                + newBmi.getHeight()
                + UiConstant.LINE
                + newBmi.getWeight()
                + UiConstant.LINE
                + newBmi.getDate());
        System.out.println(newBmi);
        printLine();
    }

    /**
     * Prints the message when a new Period is added.
     *
     * @param newPeriod The new Period object added.
     */
    public void printAddPeriod(Period newPeriod) {
        printLine();
        System.out.println(HealthConstant.PERIOD_ADDED_MESSAGE_PREFIX
                + newPeriod.getStartDate()
                + UiConstant.LINE
                + (newPeriod.getEndDate() == null ? ErrorConstant.NO_DATE_SPECIFIED_ERROR : newPeriod.getEndDate()));
        System.out.println(newPeriod);
        if (newPeriod.getEndDate() != null) {
            printPeriodWarning(newPeriod);
        }
        printLine();
    }

    /**
     * Prints the message when period length is not within the healthy range.
     *
     * @param newPeriod The new Period object added.
     */
    public void printPeriodWarning(Period newPeriod) {
        if (newPeriod.getPeriodLength() < 2 || newPeriod.getPeriodLength() > 7) {
            System.out.println("\u001b[31m" + HealthConstant.PERIOD_TOO_LONG_MESSAGE + "\u001b[0m");
        }
    }

    //@@author syj02
    /**
     * Prints the message when a new Appointment is added.
     *
     * @param newAppointment The new Appointment object added.
     */
    public void printAddAppointment(Appointment newAppointment) {
        printLine();
        System.out.println(HealthConstant.APPOINTMENT_ADDED_MESSAGE_PREFIX
                + newAppointment.getDate()
                + UiConstant.LINE
                + newAppointment.getTime()
                + UiConstant.LINE
                + newAppointment.getDescription());
        System.out.println(newAppointment);
        printLine();
    }

    //@@author JustinSoh
    /**
     * Prints the text header when adding a new Gym.
     *
     * @param gym The new Gym object added.
     */
    public void printAddGym(Gym gym) {
        printLine();
        System.out.println(WorkoutConstant.ADD_GYM);
        printGymStats(gym);
        printLine();
    }

    /**
     * Prints the message when user exits from entering gym station input.
     */
    public void printGymStationExit() {
        printLine();
        System.out.println("No longer entering gym input.");
        System.out.println("Exiting to main menu!");
        printLine();
    }

    //@@author rouvinerh
    /**
     * Prints the output for the history command.
     *
     * @param filter The type of item, which is set to Workouts, Run, Gym, Bmi, Period, or Appointment.
     */
    public void printHistory(String filter) {
        try {
            HistoryAndLatestFilters parsedFilter = HistoryAndLatestFilters.valueOf(filter.toUpperCase());
            switch (parsedFilter) {
            case WORKOUTS:
                printWorkoutHistory();
                break;

            case RUN:
                printRunHistory();
                break;

            case GYM:
                printGymHistory();
                break;

            case BMI:
                printBmiHistory();
                break;

            case PERIOD:
                printPeriodHistory();
                break;

            case APPOINTMENT:
                printAppointmentHistory();
                break;

            default:
                break;
            }
        } catch (CustomExceptions.OutOfBounds | CustomExceptions.InvalidInput e ) {
            printException(e.getMessage());
        } catch (IllegalArgumentException e) {
            printException(ErrorConstant.INVALID_HISTORY_FILTER_ERROR);
        }
    }

    //@@author JustinSoh
    /**
     * Prints a specified message and the exception error message.
     *
     * @param message The custom error to be printed.
     */
    public void printException(String message) {
        System.err.println("\u001b[31mException Caught!" + System.lineSeparator() + message + "\u001b[0m");
    }

    //@@author L5-Z
    /**
     * Prints the welcome banner for PulsePilot.
     */
    public void printWelcomeBanner() {
        printLine();
        printArt();
        System.out.println("Engaging orbital thrusters...");
        System.out.println("PulsePilot on standby");
        printLine();
    }

    //@@author rouvinerh
    /**
     * Prints delete run message.
     *
     * @param run Run to delete.
     */
    public static void printDeleteRunMessage(Run run){
        printLine();
        String messageString = String.format(WorkoutConstant.RUN_DELETE_MESSAGE_FORMAT,
                run.getDistance(),
                run.getPace());
        System.out.println(messageString);
        printLine();
    }

    //@@author JustinSoh
    /**
     * Prints delete gym message.
     *
     * @param gym Gym to delete
     */
    public static void printDeleteGymMessage(Gym gym){
        Output.printLine();
        String messageString = String.format(WorkoutConstant.GYM_DELETE_MESSAGE_FORMAT,
                gym.getStations().size());

        System.out.println(messageString);
        Output.printLine();
    }
    //@@author L5-Z
    /**
     * Checks whether storage file is present, and prints corresponding message.
     *
     * @param status Integer representing whether the storage file has been loaded. If set to 0, file is present. Else,
     *               file is not present.
     * @param name String representing the name of the user.
     */
    protected void printGreeting(int status, String name) {
        if (status == UiConstant.FILE_FOUND) {
            System.out.println(UiConstant.FILE_FOUND_MESSAGE + name);
            System.out.println(UiConstant.SUCCESSFUL_LOAD);
        } else {
            System.out.println(UiConstant.FILE_MISSING_MESSAGE);
        }
        printLine();
    }

    /**
     * Prints the goodbye message for PulsePilot.
     */
    protected void printGoodbyeMessage() {
        printLine();
        System.out.println("PulsePilot successful touchdown");
        System.out.println("See you soon, Captain!");
        printLine();
    }

    //@@author rouvinerh
    /**
     * Prints all Period entries recorded.
     *
     * @throws CustomExceptions.OutOfBounds  If there is access to a Period object that does not exist.
     */
    protected void printPeriodHistory() throws CustomExceptions.OutOfBounds {
        try {
            printLine();
            HealthList.printPeriodHistory();
            printLine();
        } catch (CustomExceptions.OutOfBounds e) {
            printException(e.getMessage());
        }
    }

    //@@author rouvinerh
    /**
     * Prints all Appointment entries recorded.
     *
     * @throws CustomExceptions.OutOfBounds  If there is access to an Appointment object that does not exist.
     */
    protected void printAppointmentHistory() throws CustomExceptions.OutOfBounds {
        try {
            printLine();
            HealthList.printAppointmentHistory();
            printLine();
        } catch (CustomExceptions.OutOfBounds e) {
            printException(e.getMessage());
        }
    }

    /**
     * Prints the latest Run entry recorded.
     */
    protected void printLatestRun() {
        try {
            printLine();
            Run latestRun = WorkoutLists.getLatestRun();
            String latestRunString = getFormattedRunWithIndex(WorkoutLists.getRunSize(), latestRun);
            System.out.println("Your latest run:");
            System.out.println(WorkoutConstant.RUN_HEADER_INDEX_FORMAT);
            System.out.println(latestRunString);
            printLine();
        } catch (CustomExceptions.OutOfBounds e) {
            printException(e.getMessage());
        }
    }

    //@@author JustinSoh
    /**
     * Prints the latest Gym entry recorded.
     */
    protected void printLatestGym() {
        try {
            printLine();
            Gym latestGym = WorkoutLists.getLatestGym();
            int index = WorkoutLists.getGymSize();
            System.out.println("Your latest gym:");
            System.out.println("Gym Session " + index + latestGym);
            printGymStats(latestGym);
            printLine();
        } catch (CustomExceptions.OutOfBounds e) {
            printException((e.getMessage()));
        }
    }

    //@@author j013n3
    /**
     * Prints the latest BMI entry recorded.
     */
    protected void printLatestBmi() {
        try {
            printLine();
            HealthList.printLatestBmi();
            printLine();
        } catch (CustomExceptions.OutOfBounds e) {
            printException(e.getMessage());
        }
    }

    /**
     * Prints the latest Period entry recorded.
     */
    protected void printLatestPeriod() {
        try {
            printLine();
            HealthList.printLatestPeriod();
            printLine();
        } catch (CustomExceptions.OutOfBounds e) {
            printException(e.getMessage());
        }
    }

    //@@author syj_02
    /**
     * Prints the latest Appointment entry recorded.
     */
    protected void printLatestAppointment(){
        try {
            printLine();
            HealthList.printLatestAppointment();
            printLine();
        } catch (CustomExceptions.OutOfBounds e) {
            printException(e.getMessage());
        }
    }

    //@@author rouvinerh
    /**
     * Prints the output for the latest command.
     *
     * @param filter The type of item, which is set to Run, Gym, Bmi, Period, or Appointment.
     */
    protected void printLatest(String filter) {
        try {
            HistoryAndLatestFilters parsedFilter = HistoryAndLatestFilters.valueOf(filter.toUpperCase());
            switch (parsedFilter) {
            case RUN:
                printLatestRun();
                break;

            case GYM:
                printLatestGym();
                break;

            case BMI:
                printLatestBmi();
                break;

            case PERIOD:
                printLatestPeriod();
                break;

            case APPOINTMENT:
                printLatestAppointment();
                break;

            default:
                break;
            }
        } catch (IllegalArgumentException e) {
            printException(ErrorConstant.INVALID_LATEST_OR_DELETE_FILTER);
        }
    }

    //@@author rouvinerh
    /**
     * Prints all Bmi entries recorded.
     *
     * @throws CustomExceptions.OutOfBounds  If there is access to a Bmi object that does not exist.
     */
    protected void printBmiHistory() throws CustomExceptions.OutOfBounds {
        try {
            printLine();
            HealthList.printBmiHistory();
            printLine();
        } catch (CustomExceptions.OutOfBounds e) {
            printException(e.getMessage());
        }
    }

    //@@author JustinSoh
    /**
     * Prints all Workout objects (Run and Gym) based on the time it was added.
     * The list is sorted in descending order. (Latest one first)
     *
     * @throws CustomExceptions.OutOfBounds  If index is out of bounds.
     * @throws CustomExceptions.InvalidInput If user input is invalid.
     */
    protected void printWorkoutHistory() throws CustomExceptions.OutOfBounds, CustomExceptions.InvalidInput {
        printLine();
        System.out.println(WorkoutConstant.HISTORY_WORKOUTS_HEADER);
        System.out.println(WorkoutConstant.HISTORY_WORKOUTS_HEADER_FORMAT);

        ArrayList<? extends Workout> workoutList = WorkoutLists.getWorkouts();
        if (workoutList.isEmpty()) {
            printWorkoutEmptyMessage();
        } else {
            for (int i = 0; i < workoutList.size(); i++) {
                Workout workout = workoutList.get(i);
                if (workout instanceof Run) {
                    Run run = (Run) workout;
                    String formattedRunString = run.getFormatForAllHistory();
                    System.out.printf((WorkoutConstant.HISTORY_WORKOUTS_DATA_HEADER_FORMAT) + "%n",
                            (i + 1), formattedRunString);
                } else {
                    Gym gym = (Gym) workout;
                    int numberOfStation = gym.getStations().size();
                    for (int j = 0; j < numberOfStation; j++) {
                        String gymString;
                        if (j == 0) {
                            gymString = String.format(WorkoutConstant.HISTORY_WORKOUTS_DATA_HEADER_FORMAT,
                                    (i + 1), gym.getHistoryFormatForSpecificGymStation(j));
                        } else {
                            gymString = String.format(WorkoutConstant.HISTORY_WORKOUTS_DATA_HEADER_FORMAT,
                                    "", gym.getHistoryFormatForSpecificGymStation(j));
                        }
                        System.out.println(gymString);
                    }
                }
            }
        }
        printLine();
    }

    /**
     * Prints all the Run objects added to the list.
     *
     * @throws CustomExceptions.OutOfBounds  If index is out of bounds.
     */
    protected void printRunHistory() throws CustomExceptions.OutOfBounds {
        printLine();
        System.out.println("Your run history:");
        ArrayList<Run> runList = WorkoutLists.getRuns();

        if(runList.isEmpty()){
            printRunEmptyMessage();
        } else {
            String runHeader = String.format(WorkoutConstant.RUN_HEADER_INDEX_FORMAT);
            System.out.println(runHeader);

            for (int i = 0; i < runList.size(); i++) {
                int index = i + 1;
                Run currentRun = runList.get(i);
                String output = getFormattedRunWithIndex(index, currentRun);
                System.out.println(output);
            }
        }
        printLine();
    }

    /**
     * Prints all the stations within a specified Gym object.
     *
     * @param gym The Gym object containing the GymStation objects to be printed.
     */
    protected void printGymStats(Gym gym) {
        ArrayList<GymStation> allStations = gym.getStations();
        for (int i = 0; i < allStations.size(); i++) {
            System.out.printf("Station %d %s%n", i + 1, allStations.get(i).toString());
        }
    }

    /**
     * Prints all the information for all Gym objects within the list.
     */
    protected void printGymHistory() {
        printLine();
        System.out.println("Your gym history:");
        ArrayList<Gym> gymList = WorkoutLists.getGyms();
        if (gymList.isEmpty()) {
            printGymEmptyMessage();
        } else {
            printGymList(gymList);
        }
        printLine();
    }

    /**
     * Prints a message when the Gym list is empty.
     */
    private void printGymEmptyMessage(){
        printException(ErrorConstant.GYM_EMPTY_ERROR);
    }

    /**
     * Prints a message when the Run list is empty.
     */
    private void printRunEmptyMessage(){
        printException(ErrorConstant.RUN_EMPTY_ERROR);
    }

    /**
     * Prints a message when workouts list is empty.
     */
    private void printWorkoutEmptyMessage(){
        printException(ErrorConstant.WORKOUTS_EMPTY_ERROR);
    }

    //@@author L5-Z
    /**
     * Prints an ASCII Art depicting the word 'PulsePilot'.
     */
    private void printArt() {
        System.out.println(" _              _");
        System.out.println("|_)    |  _  _ |_) o  |  _ _|_");
        System.out.println("|  |_| | _> (/_|   |  | (_) |_");
    }

    //@@author JustinSoh
    /**
     * Prints all Gym entries.
     */
    private void printGymList(ArrayList<Gym> gymList){
        for (int i = 0; i < gymList.size(); i++) {
            int index = i + 1;
            Gym currentWorkout = gymList.get(i);
            System.out.println("Gym Session " + index + currentWorkout);
            printGymStats(currentWorkout);
            if (i != gymList.size() - 1) {
                printLine();
            }
        }
    }

    /**
     * Returns the formatted string for printing runs.
     *
     * @param index The index of the run.
     * @return Formatted string for the run.
     */
    private String getFormattedRunWithIndex(int index, Run currentRun) {
        return String.format(WorkoutConstant.RUN_DATA_INDEX_FORMAT, index, currentRun);
    }

}
