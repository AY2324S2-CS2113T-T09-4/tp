//@@author L5-Z
package ui;

import health.Appointment;
import health.Bmi;
import health.HealthList;
import health.Period;
import storage.DataFile;
import utility.CustomExceptions;
import constants.ErrorConstant;
import constants.UiConstant;
import constants.HealthConstant;
import constants.WorkoutConstant;
import utility.Filters.Command;
import utility.Filters.DeleteFilters;
import utility.Filters.HealthFilters;
import utility.Parser;
import utility.Filters.WorkoutFilters;
import utility.Validation;
import workouts.Workout;
import workouts.WorkoutLists;

import java.util.ArrayList;
import java.util.Scanner;
import storage.LogFile;

/**
 * Represents user input parsing and handling before providing feedback to the user.
 */
public class Handler {

    //@@author JustinSoh
    static LogFile logFile = LogFile.getInstance();
    private final Scanner in;
    private final Parser parser;
    private final DataFile dataFile;
    private final Output output;
    private final Validation validation;

    public Handler(){
        in = new Scanner(System.in);
        parser = new Parser(in);
        dataFile = new DataFile();
        output = new Output();
        validation = new Validation();
    }

    public Handler(String input){
        in = new Scanner(input); // use for JUnit Testing
        parser = new Parser(in);
        dataFile = new DataFile();
        output = new Output();
        validation = new Validation();
    }

    //@@author L5-Z
    /**
     * Processes user input and filters for valid command words from enum Command,
     * then creates the relevant object based on details entered.
     *
     * @throws IllegalArgumentException If an error occurs during command processing.
     */
    public void processInput() {
        while (in.hasNextLine()) {
            String userInput = in.nextLine().trim();
            String instruction = userInput.toUpperCase().split(UiConstant.SPLIT_BY_WHITESPACE)[0];
            LogFile.writeLog("User Input: " + userInput, false);
            assert userInput != null : "Object cannot be null";

            try {
                Command command = Command.valueOf(instruction);
                switch (command) {
                case EXIT:
                    System.out.println(UiConstant.EXIT_MESSAGE);
                    return;

                case WORKOUT:
                    handleWorkout(userInput);
                    break;

                case HEALTH:
                    handleHealth(userInput);
                    break;

                case HISTORY:
                    handleHistory(userInput);
                    break;

                case LATEST:
                    handleLatest(userInput);
                    break;

                case DELETE:
                    handleDelete(userInput);
                    break;

                case HELP:
                    output.printHelp();
                    break;

                default:
                    break; // valueOf results in immediate exception for non-match with enum Command
                }
            } catch (CustomExceptions.InvalidInput e) {
                output.printException(e.getMessage());
            } catch (IllegalArgumentException e) {
                LogFile.writeLog("Invalid Command Error: " + userInput, true);
                output.printException(ErrorConstant.INVALID_COMMAND_ERROR);
            }
        }
    }

    //@@author JustinSoh
    /**
     * Handles workout command.
     * Adds a Run or Gym object to PulsePilot.
     *
     * @param userInput The user input string.
     */
    public void handleWorkout(String userInput) {
        try {
            String typeOfWorkout = parser.extractSubstringFromSpecificIndex(userInput,
                    WorkoutConstant.EXERCISE_FLAG);
            WorkoutFilters filter = WorkoutFilters.valueOf(typeOfWorkout.toUpperCase());
            switch(filter) {
            case RUN:
                parser.parseRunInput(userInput);
                break;

            case GYM:
                parser.parseGymInput(userInput);
                break;

            default:
                break;
            }
        } catch (CustomExceptions.InvalidInput | CustomExceptions.InsufficientInput e) {
            output.printException(e.getMessage());
        } catch (IllegalArgumentException e) {
            output.printException(ErrorConstant.INVALID_WORKOUT_TYPE_ERROR);
        }
    }

    //@@author rouvinerh
    /**
     * Handles history command.
     * Show history of all valid objects recorded.
     *
     * @param userInput The user input string.
     */
    public void handleHistory(String userInput) {
        String filter = parser.parseHistory(userInput);
        if (filter != null) {
            output.printHistory(filter);
            LogFile.writeLog("Viewed history for " + filter, false);
        }
    }

    /**
     * Handles the delete command.
     * Deletes an item recorded.
     *
     * @param userInput The user input string.
     * @throws CustomExceptions.InvalidInput If the user input is invalid.
     */
    public void handleDelete(String userInput) throws CustomExceptions.InvalidInput {
        String[] parsedInputs = parser.parseDeleteInput(userInput);
        if (parsedInputs == null) {
            return;
        }
        try {
            DeleteFilters filter = DeleteFilters.valueOf(parsedInputs[0].toUpperCase());
            int index = Integer.parseInt(parsedInputs[1]) - 1;
            switch (filter) {
            case BMI:
                HealthList.deleteBmi(index);
                break;

            case PERIOD:
                HealthList.deletePeriod(index);
                break;

            case GYM:
                WorkoutLists.deleteGym(index);
                break;

            case RUN:
                WorkoutLists.deleteRun(index);
                break;

            case APPOINTMENT:
                HealthList.deleteAppointment(index);
                break;

            default:
                break;
            }
        } catch (CustomExceptions.OutOfBounds e) {
            output.printException(e.getMessage());
        }
    }

    //@@author syj02
    /**
     * Handles Health command.
     * Parses the user input to determine the type of health data and processes it accordingly.
     *
     * @param userInput The user input string.
     */
    public void handleHealth(String userInput) {
        try {
            String typeOfHealth = parser.extractSubstringFromSpecificIndex(userInput, HealthConstant.HEALTH_FLAG);
            HealthFilters filter = HealthFilters.valueOf(typeOfHealth.toUpperCase());
            switch(filter) {
            case BMI:
                parser.parseBmiInput(userInput);
                break;

            case PERIOD:
                parser.parsePeriodInput(userInput);
                break;

            case PREDICTION:
                parser.parsePredictionInput();
                break;

            case APPOINTMENT:
                parser.parseAppointmentInput(userInput);
                break;

            default:
                break;
            }
        } catch (CustomExceptions.InvalidInput | CustomExceptions.InsufficientInput | CustomExceptions.OutOfBounds e) {
            output.printException(e.getMessage());
        } catch (IllegalArgumentException e) {
            output.printException(ErrorConstant.INVALID_HEALTH_INPUT_ERROR);
        }
    }

    //@@author JustinSoh

    /**
     * Prints the latest valid object recorded.
     *
     * @param userInput The user input string.
     */
    public void handleLatest(String userInput) {
        String filter = parser.parseLatest(userInput);
        if (filter != null) {
            output.printLatest(filter);
            LogFile.writeLog("Viewed latest for " + filter, false);
        }
    }

    //@@author L5-Z

    /**
     * Get user's name, and print profile induction messages.
     */
    public void userInduction() {
        String name;
        while (true) {
            name = this.in.nextLine().trim();
            if (validation.validateIfUsernameIsValid(name)) {
                System.err.println(ErrorConstant.INVALID_USERNAME_ERROR);
            } else {
                break;
            }
        }

        DataFile.userName = name;
        System.out.println("Welcome aboard, Captain " + name);
        Output.printLine();

        System.out.println("Tips: Enter 'help' to view the pilot manual!");
        System.out.println("Initiating FTL jump sequence...");

        // DataFile.saveName(name);
        LogFile.writeLog("Name Entered: " + name, false);
        System.out.println("FTL jump completed.");
    }

    //@@author L5-Z
    /**
     * Initializes PulsePilot by printing a welcome message, loading tasks from storage,
     * and returning the tasks list.
     */
    public void initialiseBot() {
        output.printWelcomeBanner();
        LogFile.writeLog("Started bot", false);

        int status = dataFile.loadDataFile();

        if (status == 0) {
            try {
                dataFile.readDataFile(); // File read
                output.printGreeting(status, DataFile.userName);
            } catch (CustomExceptions.FileReadError e) {
                output.printException(e.getMessage());
            }
        } else {
            output.printGreeting(status, DataFile.userName);
            userInduction();
        }

        System.out.println("Terminal primed. Command inputs are now accepted...");
        Output.printLine();
    }

    /**
     * Terminates PulsePilot by saving tasks to storage, printing a goodbye message,
     * and indicating the filename where tasks are saved.
     */
    public void terminateBot() {
        LogFile.writeLog("User terminating PulsePilot", false);

        try {
            LogFile.writeLog("Attempting to save data file", false);

            String userName = DataFile.userName;
            ArrayList<Workout> workoutList = WorkoutLists.getWorkouts();
            ArrayList<Bmi> bmiList = HealthList.getBmis();
            ArrayList<Appointment> appointmentList = HealthList.getAppointments();
            ArrayList<Period> periodList = HealthList.getPeriods();
            dataFile.saveDataFile(userName, bmiList, appointmentList, periodList, workoutList);

        } catch (CustomExceptions.FileWriteError e) {
            LogFile.writeLog("File write error", true);
            output.printException(e.getMessage());
        }

        output.printGoodbyeMessage();
        // Yet to implement : Reply.printReply("Saved tasks as: " + Constant.FILE_NAME);
        LogFile.writeLog("Bot exited gracefully", false);
        System.exit(0);
    }

}
