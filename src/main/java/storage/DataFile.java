//@@author L5-Z
package storage;

import java.io.FileWriter;
import java.nio.file.Files;
import java.io.IOException;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import health.Appointment;
import health.Bmi;
import health.Period;
import constants.ErrorConstant;
import ui.Output;
import utility.Parser;
import utility.Validation;
import workouts.Gym;
import workouts.Run;
import workouts.Workout;
import utility.CustomExceptions;
import constants.UiConstant;
import utility.Filters.DataType;

/**
 * Represents a DataFile object used to read and write data stored in PulsePilot to a file.
 * Handles the reading and writing of various data related to PulsePilot, including user's name,
 * health data like BMI, appointments, periods, and workout data like runs and gym sessions.
 * It provides methods to load, save, and process different types of data as well as prevent tampering via hashing.
 */
public class DataFile {
    public static String userName = null;
    private static DataFile instance = null;

    private final Output output;
    private final Validation validation;


    /**
     * Private constructor to prevent instantiation from outside the class.
     * Initializes the data file.
     */
    public DataFile() {
        output = new Output();
        validation = new Validation();
    }

    /**
     * Verifies the integrity of the data file by checking its existence and hs value.
     * If the data file already exists, checks its hash value against the expected hash value.
     * If the data file does not exist, creates a new file and logs the event.
     *
     * @param dataFile The data file to verify integrity.
     * @return Returns 0 if the file is found. Else, returns 1.
     * @throws CustomExceptions.FileCreateError If there is an error creating the data file.
     */
    public int verifyIntegrity(File dataFile) throws CustomExceptions.FileCreateError {
        try {
            if (dataFile.createNewFile()) {
                LogFile.writeLog("Created new data file", false);
                return UiConstant.FILE_NOT_FOUND;
            } else {
                LogFile.writeLog("Reading from existing data file", false);
                return UiConstant.FILE_FOUND;
            }
        } catch (IOException e) {
            throw new CustomExceptions.FileCreateError(ErrorConstant.CREATE_FILE_ERROR);
        }
    }

    /**
     * Initializes the data file to be used. Or loads the existing data file, verifies its integrity, and processes
     * its content. Exits if the file cannot be created or loaded.
     *
     * @return Returns 0 if the file is found. Else, returns 1.
     */
    public int loadDataFile() {
        int status = UiConstant.FILE_NOT_FOUND;
        validation.validateDirectoryPermissions();
        try {
            File dataFile = UiConstant.saveFile;
            File hashFile = new File(UiConstant.hashFilePath);

            if (dataFile.exists() && hashFile.exists()) {
                String expectedHash = generateFileHash(dataFile);
                String actualHash = readHashFromFile(hashFile);

                if (expectedHash.equals(actualHash)) {
                    status = verifyIntegrity(dataFile);
                } else {
                    processFail(ErrorConstant.DATA_INTEGRITY_ERROR);
                    System.exit(1);
                }
            } else if (!dataFile.exists() && !hashFile.exists()) {
                status = verifyIntegrity(dataFile);
            } else {
                processFail(ErrorConstant.MISSING_INTEGRITY_ERROR);
                System.exit(1);
            }
        } catch (CustomExceptions.FileCreateError e) {
            System.err.println(ErrorConstant.CREATE_FILE_ERROR);
            LogFile.writeLog(ErrorConstant.CREATE_FILE_ERROR, true);
            System.exit(1);
        } catch (IOException | NoSuchAlgorithmException e) {
            LogFile.writeLog("Error occurred while processing file hash: " + e.getMessage(), true);
            output.printException(ErrorConstant.HASH_ERROR);
            System.exit(1);
        }

        Path dataFilePath = Path.of(UiConstant.dataFilePath);
        assert Files.exists(dataFilePath) : "Data file does not exist.";
        return status;
    }

    /**
     * Reads data from the existing data file and processes it.
     *
     * @throws CustomExceptions.FileReadError If there is an error reading the data file.
     */
    public void readDataFile() throws CustomExceptions.FileReadError {
        int lineNumberCount = 0; // just for getting lineNumber, no other use
        try (final Scanner readFile = new Scanner(UiConstant.saveFile)) {
            LogFile.writeLog("Read begins", false);
            try {
                String[] input = readFile.nextLine().split(UiConstant.SPLIT_BY_COLON);
                String name = input[UiConstant.NAME_INDEX].trim();
                LogFile.writeLog("Processing Name", false);
                processName(name);
                LogFile.writeLog("Name Loaded", false);

            } catch (Exception e) {
                LogFile.writeLog("Data file is missing name, exiting." + e, true);
                processFail(ErrorConstant.CORRUPT_ERROR);
                System.exit(1);
            }

            while (readFile.hasNextLine()) {
                String rawInput = readFile.nextLine();
                LogFile.writeLog("Read String: " + rawInput, false);
                String[] input = rawInput.split(UiConstant.SPLIT_BY_COLON);
                String dataType = input[UiConstant.DATA_TYPE_INDEX].trim();

                LogFile.writeLog("Current DataType:" + dataType, false);
                DataType filter = DataType.valueOf(dataType);
                switch (filter) {

                case APPOINTMENT:
                    processAppointment(input);
                    break;

                case PERIOD:
                    processPeriod(input);
                    break;

                case BMI:
                    processBmi(input);
                    break;

                case GYM:
                    processGym(rawInput);
                    break;

                case RUN:
                    processRun(input);
                    break;

                default:
                    break; // valueOf results in immediate exception for non-match with enum DataType
                }
                lineNumberCount += 1;
            }
        } catch (Exception e) {
            LogFile.writeLog("Data file is missing content at line " + lineNumberCount + ", exiting." + e,
                    true);
            processFail(ErrorConstant.CORRUPT_ERROR);
            System.exit(1);
        }
    }

    /**
     * Processes the username from the data file.
     *
     * @param name The username read from the data file.
     * @throws CustomExceptions.InvalidInput If the username is invalid.
     */
    public void processName(String name) throws CustomExceptions.InvalidInput {
        if (validation.validateIfUsernameIsValid(name)) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_USERNAME_ERROR);
        }
        userName = name.trim();
    }

    /**
     * Processes an appointment entry from the input string array and adds it to the health list.
     *
     * @param input The input string array containing appointment data.
     * @throws CustomExceptions.InsufficientInput If there is insufficient input data.
     * @throws CustomExceptions.InvalidInput If there is an error in the input data format.
     */
    public void processAppointment(String[] input) throws CustomExceptions.InsufficientInput,
            CustomExceptions.InvalidInput {
        String date = input[1].trim(); // date
        String time = input[2].trim(); // time
        String formattedTime = time.replace(".", ":");
        String description = input[3].trim(); // description
        String[] checkAppointmentDetails = {date, formattedTime, description};
        validation.validateAppointmentDetails(checkAppointmentDetails);
        Appointment appointment = new Appointment(date, formattedTime, description);
    }

    /**
     * Processes a period entry from the input string array and adds it to the health list.
     *
     * @param input The input string array containing period data.
     * @throws CustomExceptions.InsufficientInput If there is insufficient input data.
     * @throws CustomExceptions.InvalidInput If there is an error in the input data format.
     */
    public void processPeriod(String[] input) throws CustomExceptions.InsufficientInput,
            CustomExceptions.InvalidInput {
        String startDate = input[1].trim(); // start
        String endDate = input[2].trim(); // end, skip 3 duration
        String[] checkPeriodInput = {startDate, endDate};
        boolean isParser = false;
        validation.validatePeriodInput(checkPeriodInput, isParser);
        if (endDate.equals(ErrorConstant.NO_DATE_SPECIFIED_ERROR)) {
            new Period(startDate);
        } else {
            new Period(startDate, endDate);
        }
    }

    /**
     * Processes a BMI entry from the input string array and adds it to the health list.
     *
     * @param input The input string array containing BMI data.
     * @throws CustomExceptions.InsufficientInput If there is insufficient input data.
     * @throws CustomExceptions.InvalidInput If there is an error in the input data format.
     */
    public void processBmi(String[] input) throws CustomExceptions.InsufficientInput, CustomExceptions.InvalidInput {
        String height = input[1].trim(); // height
        String weight = input[2].trim(); // weight
        String date = input[4].trim();// skip 3, bmi score, 4 is date
        String[] checkBmiInput = {height, weight, date};
        validation.validateBmiInput(checkBmiInput);
        new Bmi(height, weight, date);
    }

    /**
     * Processes a run entry from the input string array and adds it to the workout list.
     *
     * @param input The input string array containing run data.
     * @throws CustomExceptions.InvalidInput If there is an error in the input data format.
     * @throws CustomExceptions.InsufficientInput If there is insufficient input data.
     */
    public void processRun(String[] input) throws CustomExceptions.InvalidInput, CustomExceptions.InsufficientInput {
        String distance = input[1].trim(); // distance
        String time = input[2].trim(); // time
        String formattedTime = time.replace(".", ":");
        String date = input[3].trim(); // 3 is date
        String[] checkRunInput = {formattedTime, distance, date};
        validation.validateRunInput(checkRunInput);
        if (date.equals(ErrorConstant.NO_DATE_SPECIFIED_ERROR)) {
            new Run(formattedTime, distance);
        } else {
            new Run(formattedTime, distance, date);
        }
    }

    /**
     * Processes a gym entry from the raw input string and delegates parsing to Parser class.
     *
     * @param rawInput The raw input string containing gym data.
     * @throws CustomExceptions.InvalidInput  If there is an error in the input data format.
     * @throws CustomExceptions.FileReadError If there is an error reading the gym file.
     * @throws CustomExceptions.InsufficientInput If there is insufficient input data.
     */
    public void processGym(String rawInput) throws CustomExceptions.InvalidInput, CustomExceptions.FileReadError,
            CustomExceptions.InsufficientInput {

        Parser newParser = new Parser();
        newParser.parseGymFileInput(rawInput);
    }

    /**
     * Saves data to the data file.
     *
     * @param name                 The username to be saved.
     * @param bmiArrayList         List of BMI entries to be saved.
     * @param appointmentArrayList List of appointment entries to be saved.
     * @param periodArrayList      List of period entries to be saved.
     * @param workoutArrayList     List of workout entries to be saved.
     * @throws CustomExceptions.FileWriteError If there is an error writing to the data file.
     */
    public void saveDataFile(String name,
                             ArrayList<Bmi> bmiArrayList,
                             ArrayList<Appointment> appointmentArrayList,
                             ArrayList<Period> periodArrayList,
                             ArrayList<Workout> workoutArrayList
    ) throws CustomExceptions.FileWriteError {

        try (FileWriter dataFile = new FileWriter(UiConstant.dataFilePath)) {
            LogFile.writeLog("Attempting to write name: " + name, false);
            writeName(dataFile, name);

            writeHealthData(dataFile, bmiArrayList,
                    appointmentArrayList,
                    periodArrayList);

            writeWorkoutData(dataFile, workoutArrayList);

            LogFile.writeLog("Write end", false);

        } catch (IOException e) {
            throw new CustomExceptions.FileWriteError(ErrorConstant.SAVE_ERROR);
        }

        try (FileWriter hashFile = new FileWriter(UiConstant.hashFilePath)) {
            LogFile.writeLog("Attempting to write hash", false);
            File dataFile = UiConstant.saveFile;
            writeHashToFile(generateFileHash(dataFile));

            LogFile.writeLog("Write end", false);

        } catch (IOException | NoSuchAlgorithmException e) {
            throw new CustomExceptions.FileWriteError(ErrorConstant.SAVE_ERROR);
        }
    }

    /**
     * Writes the user's name to the data file.
     *
     * @param dataFile The FileWriter object for writing to the data file.
     * @param name     The user's name to be written to the file.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public void writeName(FileWriter dataFile, String name) throws IOException {
        dataFile.write(UiConstant.NAME_LABEL + UiConstant.SPLIT_BY_COLON + name.trim() + System.lineSeparator());
        LogFile.writeLog("Wrote name to file", false);
    }

    /**
     * Writes health-related data (BMI, appointments, periods) to the data file.
     *
     * @param dataFile             The FileWriter object for writing to the data file.
     * @param bmiArrayList         The list of BMI entries to be written.
     * @param appointmentArrayList The list of appointment entries to be written.
     * @param periodArrayList      The list of period entries to be written.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public void writeHealthData(FileWriter dataFile, ArrayList<Bmi> bmiArrayList,
                                ArrayList<Appointment> appointmentArrayList,
                                ArrayList<Period> periodArrayList) throws IOException {
        Parser newParser = new Parser();
        // Write each bmi entry in a specific format
        // bmi format: bmi:HEIGHT:WEIGHT:BMI_SCORE:DATE (NA if no date)
        if (!bmiArrayList.isEmpty()) {
            for (Bmi bmiEntry : bmiArrayList) {
                String formattedDate = newParser.parseFormattedDate(bmiEntry.getDate());

                dataFile.write(DataType.BMI + UiConstant.SPLIT_BY_COLON + bmiEntry.getHeight() +
                        UiConstant.SPLIT_BY_COLON + bmiEntry.getWeight() +
                        UiConstant.SPLIT_BY_COLON + bmiEntry.getBmiValueString() +
                        UiConstant.SPLIT_BY_COLON + formattedDate + System.lineSeparator());
            }
        }

        // Write each appointment entry in a specific format
        // appointment format: appointment:DATE:TIME:DESCRIPTION
        if (!appointmentArrayList.isEmpty()) {
            for (Appointment appointmentEntry : appointmentArrayList) {
                String formattedDate = newParser.parseFormattedDate(appointmentEntry.getDate());
                String formattedTime = String.valueOf(appointmentEntry.getTime());
                formattedTime = formattedTime.replace(":", ".");

                dataFile.write(DataType.APPOINTMENT + UiConstant.SPLIT_BY_COLON + formattedDate +
                        UiConstant.SPLIT_BY_COLON + formattedTime +
                        UiConstant.SPLIT_BY_COLON + appointmentEntry.getDescription() + System.lineSeparator());
            }
        }

        // Write each period entry in a specific format
        // period format: period:START:END:DURATION
        if (!periodArrayList.isEmpty()) {
            for (Period periodEntry : periodArrayList) {
                LogFile.writeLog("Writing period to file", false);
                String formattedStartDate = newParser.parseFormattedDate(periodEntry.getStartDate());
                String formattedEndDate = newParser.parseFormattedDate(periodEntry.getEndDate());

                dataFile.write(DataType.PERIOD + UiConstant.SPLIT_BY_COLON + formattedStartDate +
                        UiConstant.SPLIT_BY_COLON + formattedEndDate +
                        UiConstant.SPLIT_BY_COLON + periodEntry.getPeriodLength() + System.lineSeparator());
                LogFile.writeLog("Wrote period to file", false);
            }
        }

    }

    /**
     * Writes workout-related data (runs and gym sessions) to the data file.
     *
     * @param dataFile         The FileWriter object for writing to the data file.
     * @param workoutArrayList The list of workout entries to be written.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public void writeWorkoutData(FileWriter dataFile,
                                 ArrayList<Workout> workoutArrayList) throws IOException {

        // Write each run entry in a specific format
        // run format: run:DISTANCE:TIME:DATE
        if (!workoutArrayList.isEmpty()) {
            for (Workout workoutEntry : workoutArrayList) {
                if (workoutEntry instanceof Run) {
                    Run runEntry = (Run) workoutEntry;
                    String formattedDate = runEntry.getDateForFile();
                    String formattedTime = runEntry.getTimes().replace(":", ".");

                    dataFile.write(DataType.RUN + UiConstant.SPLIT_BY_COLON + runEntry.getDistance() +
                            UiConstant.SPLIT_BY_COLON + formattedTime +
                            UiConstant.SPLIT_BY_COLON + formattedDate + System.lineSeparator());
                } else if (workoutEntry instanceof Gym) {
                    Gym gymEntry = (Gym) workoutEntry;
                    String gymString = gymEntry.toFileString();
                    dataFile.write(gymString + System.lineSeparator());
                }
            }
        }
    }
    //@@author L5-Z
    /**
     * Generates the SHA-256 hash value of the pulsepilot_data.txt file.
     *
     * @param file The file for which to generate the hash.
     * @return A String representing the SHA-256 hash value of the pulsepilot_data.txt file.
     * @throws NoSuchAlgorithmException If the SHA-256 algorithm is not available.
     * @throws IOException              If an I/O error occurs while reading the file.
     */
    protected String generateFileHash(File file) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        FileInputStream fis = new FileInputStream(file);
        byte[] dataBytes = new byte[1024];

        int bytesRead;
        while ((bytesRead = fis.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, bytesRead);
        }

        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }

        fis.close();
        return sb.toString();
    }

    /**
     * Handles the failure of file hash verification.
     * This method is called when the hash value of the data file does not match the expected value.
     * It logs the error, prints the exception and  deletes the data file and hash file.
     *
     * @param errorString The error message to be logged and printed.
     */
    protected void processFail(String errorString) {
        File dataFile = UiConstant.saveFile;
        File hashFile = new File(UiConstant.hashFilePath);

        LogFile.writeLog(errorString, true);
        output.printException(errorString);

        hashFile.delete();
        dataFile.delete();
    }

    /**
     * Reads the hash value from a hash file.
     *
     * @param hashFile The hash file to read from.
     * @return The hash value read from the file.
     * @throws IOException If an I/O error occurs.
     */
    protected String readHashFromFile(File hashFile) throws IOException {
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(hashFile);
        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine());
        }
        scanner.close();
        return sb.toString();
    }

    /**
     * Writes the hash value to the hash file.
     *
     * @param hash     The hash value to write.
     * @throws IOException If an I/O error occurs.
     */
    private void writeHashToFile(String hash) throws IOException {
        FileOutputStream fos = new FileOutputStream(UiConstant.hashFilePath);
        fos.write(hash.getBytes());
        fos.close();
    }
}

