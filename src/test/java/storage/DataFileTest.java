//@@author L5-Z
package storage;
import constants.UiConstant;
import health.Appointment;
import health.Bmi;
import health.Period;
import org.junit.jupiter.api.Test;
import utility.CustomExceptions;
import workouts.Gym;
import workouts.Run;
import workouts.Workout;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DataFileTest {

    @Test
    void saveDataFile_validData_writesCorrectly() throws IOException, CustomExceptions.FileWriteError,
            CustomExceptions.InvalidInput {
        // Arrange
        String name = "John Doe";
        ArrayList<Bmi> bmiArrayList = new ArrayList<>(Arrays.asList(
                new Bmi("1.7", "70.0", "01-04-2023"),
                new Bmi("1.8", "80.0", "15-04-2023")
        ));
        ArrayList<Appointment> appointmentArrayList = new ArrayList<>(Arrays.asList(
                new Appointment("01-05-2023", "10:00", "Dentist Appointment"),
                new Appointment("15-05-2023", "14:30", "Doctor's Checkup")
        ));
        ArrayList<Period> periodArrayList = new ArrayList<>(Arrays.asList(
                new Period("01-03-2023", "05-03-2023"),
                new Period("01-04-2023", "04-04-2023")
        ));


        ArrayList<Integer> array1 = new ArrayList<>(Arrays.asList(10,20,30,40));
        ArrayList<Integer> array2 = new ArrayList<>(Arrays.asList(20,30,40,50));
        Gym newGym = new Gym("11-11-1997");
        Gym newGym2 = new Gym();
        newGym.addStation("bench press", 4, 4, array1);
        newGym.addStation("squats", 4, 3, array2);
        newGym2.addStation("bench press", 4, 4, array1);
        newGym2.addStation("squats", 4, 3, array2);

        ArrayList<Workout> workoutArrayList = new ArrayList<>(List.of(
                new Run("00:30:00", "5.0", "01-04-2023"),
                newGym,
                newGym2
        ));

        // Act
        if (Files.exists(Path.of(UiConstant.DATA_FILE_PATH))) {
            Files.delete(Path.of(UiConstant.DATA_FILE_PATH));
        }
        Files.createFile(Path.of(UiConstant.DATA_FILE_PATH));
        DataFile.saveDataFile(name, bmiArrayList, appointmentArrayList, periodArrayList, workoutArrayList);

        // Assert
        List<String> lines = Files.readAllLines(Path.of(UiConstant.DATA_FILE_PATH));
        if (!lines.isEmpty()) {
            assertEquals("NAME:John Doe", lines.get(0));
            assertEquals("BMI:1.70:70.00:24.22:01-04-2023", lines.get(1));
            assertEquals("BMI:1.80:80.00:24.69:15-04-2023", lines.get(2));
            assertEquals("APPOINTMENT:01-05-2023:10.00:Dentist Appointment", lines.get(3));
            assertEquals("APPOINTMENT:15-05-2023:14.30:Doctor's Checkup", lines.get(4));
            assertEquals("PERIOD:01-03-2023:05-03-2023:5", lines.get(5));
            assertEquals("PERIOD:01-04-2023:04-04-2023:4", lines.get(6));
            assertEquals("RUN:5.00:00.30.00:01-04-2023", lines.get(7));
            assertEquals("GYM:2:11-11-1997:bench press:4:4:10,20,30,40:squats:4:3:20,30,40,50", lines.get(8));
            assertEquals("GYM:2:NA:bench press:4:4:10,20,30,40:squats:4:3:20,30,40,50", lines.get(9));
        } else {
            fail("Data file is empty");
        }
    }

    /*
    @Test
    void saveDataFile_emptyData_writesCorrectly() throws IOException, CustomExceptions.FileWriteError {
        // Arrange
        String name = "Jane Doe";
        ArrayList<Bmi> bmiArrayList = new ArrayList<>();
        ArrayList<Appointment> appointmentArrayList = new ArrayList<>();
        ArrayList<Period> periodArrayList = new ArrayList<>();
        ArrayList<Workout> workoutArrayList = new ArrayList<>();

        Path dataFilePath = tempDir.resolve("data.txt");
        File dataFile = dataFilePath.toFile();

        // Act
        DataFile.saveDataFile(name, bmiArrayList, appointmentArrayList, periodArrayList, workoutArrayList);

        // Assert
        List<String> lines = Files.readAllLines(dataFilePath);
        assertEquals("name:Jane Doe", lines.get(0));
        assertEquals(1, lines.size());
    }

     */
}
