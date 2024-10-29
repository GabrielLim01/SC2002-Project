// This class has the following methods
// 1. readFromCSV generic csv file reading method, returns an ArrayList<String>
// 2. ArrayList generation methods for the various user classes based on the contents of their respective csv files
// 3. A method to update the Doctors' appointment availability attribute with an Appointment ArrayList, done after
// their respective lists' generation

package Utility;

import Models.*;
import com.opencsv.*;

import java.io.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class DataProcessing {

    // default constructor
    public DataProcessing() {
    }

    // formatters for date conversion
    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMM uuuu");

    // instantiated DateTime object for timeslot generation
    DateTime dt = new DateTime();

    public ArrayList<String> readFromCSV(String fileName) {

        // getResourceAsStream tries to look for the file in the directory of the class it is invoked from, in this case, src
        // so append test/resources/<name of file>.csv to properly locate the directory and file location
        InputStream inputStream = DataProcessing.class.getClassLoader().getResourceAsStream(fileName);
        // System.out.println(inputStream == null); // DEBUGGING - if false, means the file is successfully located
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        ArrayList<String> dataList = new ArrayList<String>();

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(true)
                .build();

        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(1)       // set withSkipLines to 1 to skip the header row
                .withCSVParser(parser)
                .build();

        // using try-with-resources functionality introduced in Java 7 to automatically close CSVReader instance at the end of code runtime
        try (csvReader) {
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                dataList.add(Arrays.toString(line));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // DEBUGGING
        // dataList.forEach(System.out::println);

        // Expected output from Staff_List.csv
        // [D001, John Smith, Doctor, Male, 45]
        // [D002, Emily Clarke, Doctor, Female, 38]
        // [P001, Mark Lee, Pharmacist, Male, 29]
        // [A001, Sarah Lee, Administrator, Female, 40]

        return dataList;
    }

    public ArrayList<Patient> generatePatientList(ArrayList<String> dataList) {

        ArrayList<Patient> patientList = new ArrayList<Patient>();

        for (int i = 0; i < dataList.size(); i++) {
            // substring to remove the first and last characters which are brackets [ ]
            // then split it using a comma delimiter to allow iteration over the individual elements of data
            String[] temp = dataList.get(i).substring(1, dataList.get(i).length() - 1).split(",");

            // DEBUGGING
//            for (int j = 0; j < temp.length; j++) {
//                System.out.println(temp[j]);
//            }

            String id = temp[0].trim();
            String name = temp[1].trim();
            LocalDate preformattedDateOfBirth = LocalDate.parse(temp[2].trim(), inputFormatter);
            String formattedDateOfBirth = preformattedDateOfBirth.format(outputFormatter);
            // System.out.println(formattedDateOfBirth);

            char gender = 'M';
            if (!temp[3].trim().equals("Male")) {
                if (temp[3].trim().equals("Female")) {
                    gender = 'F';
                } else {
                    gender = 'O';
                }
            }

            int phoneNo = 12345678; // hardcoded for now, since they don't have their individual phone numbers in the csv file
            String bloodType = temp[4].trim();
            String email = temp[5].trim();

            patientList.add(new Patient(id, name, formattedDateOfBirth, gender, phoneNo, email, bloodType));
        }
        return patientList;
    }

    public ArrayList<Doctor> generateDoctorList(ArrayList<String> dataList) {

        ArrayList<Doctor> doctorList = new ArrayList<Doctor>();

        for (int i = 0; i < dataList.size(); i++) {
            // substring to remove the first and last characters which are brackets [ ]
            // then split it using a comma delimiter to allow iteration over the individual elements of data
            String[] temp = dataList.get(i).substring(1, dataList.get(i).length() - 1).split(",");
            String id = temp[0].trim();
            String name = temp[1].trim();
            char gender = 'M';
            if (!temp[3].trim().equals("Male")) {
                if (temp[3].trim().equals("Female")) {
                    gender = 'F';
                } else {
                    gender = 'O';
                }
            }
            int age = Integer.parseInt(temp[4].trim());
            doctorList.add(new Doctor(id, name, gender, age, null));

        }
        return doctorList;
    }

    // This method is like what, INNER JOIN of two tables in an SQL context?
    // Basically the doctors don't have their availability variable initialized at runtime because they have to wait
    // for the appointments list to generate first, only after that can they be updated with their appointments timeslots
    // - which is what this method is doing

    // Might be more efficient with arraylist of arraylists???

    public void updateDoctorsListWithAppointments(ArrayList<Doctor> doctorsList, ArrayList<Appointment> appointmentsList) {
        for (int i = 0; i < doctorsList.size(); i++) {

            ArrayList<Appointment> appointmentsListSubset = new ArrayList<Appointment>();

            for (int j = 0; j < appointmentsList.size(); j++) {
                // If the name of a doctor in the doctorsList matches the name of their corresponding name in appointment slots,
                // then those set of appointment slots belongs to that doctor
                if (doctorsList.get(i).getName().equals(appointmentsList.get(j).getDoctor().getName())) {
                    // Add all these slots into a subset of the main Appointment list (the subset is itself another Appointment list)
                    appointmentsListSubset.add(appointmentsList.get(j));
                }
            }
            // Update the doctor's availability attribute with this list
            doctorsList.get(i).setAvailability(appointmentsListSubset);
        }
    }

    public ArrayList<Appointment> updateAppointmentsList(ArrayList<Doctor> doctorsList){
        ArrayList<Appointment> updatedOverallAppointmentsList = new ArrayList<Appointment>();

        for (int i = 0; i < doctorsList.size(); i++) {
//            for (int j = 0; j < doctorsList.get(i).getAvailability().size(); j++) {
//                updatedOverallAppointmentsList.add(doctorsList.get(i).getAvailability().get(j));
//            }
            updatedOverallAppointmentsList.addAll(doctorsList.get(i).getAvailability());
        }

        return updatedOverallAppointmentsList;
    }
}
