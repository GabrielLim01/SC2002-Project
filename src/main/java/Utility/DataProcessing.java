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
        try {
            // First try to read from resources folder
            InputStream inputStream = DataProcessing.class.getClassLoader().getResourceAsStream(fileName);

            // If not found in resources, try direct file path
            if (inputStream == null) {
                String filePath = "src/main/resources/" + fileName;
                inputStream = new FileInputStream(filePath);
            }

            if (inputStream == null) {
                throw new FileNotFoundException("Could not find file: " + fileName);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            ArrayList<String> dataList = new ArrayList<String>();

            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(',')
                    .withIgnoreQuotations(true)
                    .build();

            CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withSkipLines(1)
                    .withCSVParser(parser)
                    .build();

            try (csvReader) {
                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    dataList.add(Arrays.toString(line));
                }
            }

            return dataList;

        } catch (FileNotFoundException e) {
            System.err.println("Error: Could not find file " + fileName);
            System.err.println("Please ensure the file exists in src/main/resources/");
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.err.println("Error reading file " + fileName);
            throw new RuntimeException(e);
        }
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

    /**
     * Generates a list of pharmacists from CSV data
     * @param dataList ArrayList of strings containing pharmacist data
     * @return ArrayList of Pharmacist objects
     */
    public ArrayList<Pharmacist> generatePharmacistList(ArrayList<String> dataList) {
        ArrayList<Pharmacist> pharmacistList = new ArrayList<>();

        for (String data : dataList) {
            String[] temp = data.substring(1, data.length() - 1).split(",");

            String id = temp[0].trim();
            String name = temp[1].trim();
            char gender = 'M';
            if (!temp[3].trim().equals("Male")) {
                gender = temp[3].trim().equals("Female") ? 'F' : 'O';
            }
            int age = Integer.parseInt(temp[4].trim());

            pharmacistList.add(new Pharmacist(id, name, gender, age));
        }
        return pharmacistList;
    }

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

    // Actually I don't need this method right now
    // This method lets me get away with not having to pass ArrayList<Appointment> appointmentList as an argument in parent methods
    // Might need it later
//    public ArrayList<Appointment> updateAppointmentsList(ArrayList<Doctor> doctorsList){
//        ArrayList<Appointment> updatedOverallAppointmentsList = new ArrayList<Appointment>();
//
//        for (int i = 0; i < doctorsList.size(); i++) {
////            for (int j = 0; j < doctorsList.get(i).getAvailability().size(); j++) {
////                updatedOverallAppointmentsList.add(doctorsList.get(i).getAvailability().get(j));
////            }
//            updatedOverallAppointmentsList.addAll(doctorsList.get(i).getAvailability());
//        }
//
//        return updatedOverallAppointmentsList;
//    }
}
