// This class has the following methods
// 1. readFromCSV generic csv file reading method, returns an ArrayList<String>
// 2. ArrayList generation methods for the various user classes based on the contents of their respective csv files
// 3. A method to update the Doctors' appointment availability attribute with an Appointment ArrayList, done after
// their respective lists' generation

package Utility;

import Models.*;
import com.opencsv.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DataProcessing {

    // default constructor
    public DataProcessing() {
    }

    // formatters for date conversion
    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMM uuuu");

    // instantiated DateTime object for timeslot generation
    DateTime dt = new DateTime();

    // readFromCSV by Gabriel
//    public ArrayList<String> readFromCSV(String fileName) {
//
//        // getResourceAsStream tries to look for the file in the directory of the class it is invoked from, in this case, src
//        // so append test/resources/<name of file>.csv to properly locate the directory and file location
//        InputStream inputStream = DataProcessing.class.getClassLoader().getResourceAsStream(fileName);
//        // System.out.println(inputStream == null); // DEBUGGING - if false, means the file is successfully located
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//
//        ArrayList<String> dataList = new ArrayList<String>();
//
//        CSVParser parser = new CSVParserBuilder()
//                .withSeparator(',')
//                .withIgnoreQuotations(true)
//                .build();
//
//        CSVReader csvReader = new CSVReaderBuilder(reader)
//                .withSkipLines(1)       // set withSkipLines to 1 to skip the header row
//                .withCSVParser(parser)
//                .build();
//
//        // using try-with-resources functionality introduced in Java 7 to automatically close CSVReader instance at the end of code runtime
//        try (csvReader) {
//            String[] line;
//            while ((line = csvReader.readNext()) != null) {
//                dataList.add(Arrays.toString(line));
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        // DEBUGGING
//        // dataList.forEach(System.out::println);
//
//        // Expected output from Staff_List.csv
//        // [D001, John Smith, Doctor, Male, 45]
//        // [D002, Emily Clarke, Doctor, Female, 38]
//        // [P001, Mark Lee, Pharmacist, Male, 29]
//        // [A001, Sarah Lee, Administrator, Female, 40]
//
//        return dataList;
//    }

    // readFromCSV by Aloysius
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

        ArrayList<Patient> patients = new ArrayList<Patient>();

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

            patients.add(new Patient(id, name, formattedDateOfBirth, gender, phoneNo, email, bloodType));
        }
        return patients;
    }

    public ArrayList<Doctor> generateDoctorList(ArrayList<String> dataList) {

        ArrayList<Doctor> doctors = new ArrayList<Doctor>();

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
            doctors.add(new Doctor(id, name, gender, age, null, null));

        }
        return doctors;
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
        ArrayList<Pharmacist> pharmacists = new ArrayList<>();

        for (String data : dataList) {
            String[] temp = data.substring(1, data.length() - 1).split(",");

            String id = temp[0].trim();
            String name = temp[1].trim();
            char gender = 'M';
            if (!temp[3].trim().equals("Male")) {
                gender = temp[3].trim().equals("Female") ? 'F' : 'O';
            }
            int age = Integer.parseInt(temp[4].trim());

            pharmacists.add(new Pharmacist(id, name, gender, age));
        }
        return pharmacists;
    }

    public void updateDoctorsWithAppointments(ArrayList<Doctor> doctors, ArrayList<Appointment> appointments) {
        for (int i = 0; i < doctors.size(); i++) {

            ArrayList<Appointment> appointmentsSubset = new ArrayList<Appointment>();

            for (int j = 0; j < appointments.size(); j++) {
                // If the name of a doctor in the doctorsList matches the name of their corresponding name in appointment slots,
                // then those set of appointment slots belongs to that doctor
                if (doctors.get(i).getName().equals(appointments.get(j).getDoctor().getName())) {
                    // Add all these slots into a subset of the main Appointment list (the subset is itself another Appointment list)
                    appointmentsSubset.add(appointments.get(j));
                }
            }
            // Update the doctor's availability attribute with this list
            doctors.get(i).setAvailability(appointmentsSubset);
        }
    }

    public Map<String, Map<String, String>> getCredentialmap(ArrayList<String> dataList){

        Map<String, Map<String, String>> credentialMap = new HashMap<>();

        for (int i = 0; i < dataList.size(); i++){
            Map<String, String> detailsMap = new HashMap<>();
            String[] details = dataList.get(i).substring(1, dataList.get(i).length() - 1).split(",");
            detailsMap.put("FullName", details[1].trim());
            detailsMap.put("Password", details[2].trim());
            detailsMap.put("Role", details[3].trim());

            credentialMap.put(details[0].trim(), detailsMap);
        }

        return credentialMap;
    }

    // Actually I don't need this method right now
    // This method lets me get away with not having to pass ArrayList<Appointment> appointments as an argument in parent methods
    // Might need it later
//    public ArrayList<Appointment> updateAppointments(ArrayList<Doctor> doctors){
//        ArrayList<Appointment> updatedAppointments = new ArrayList<Appointment>();
//
//        for (int i = 0; i < doctors.size(); i++) {
////            for (int j = 0; j < doctors.get(i).getAvailability().size(); j++) {
////                updatedAppointments.add(doctors.get(i).getAvailability().get(j));
////            }
//            updatedAppointments.addAll(doctors.get(i).getAvailability());
//        }
//
//        return updatedAppointments;
//    }

    public MedicationInventory generateMedicationList(ArrayList<String> dataList) {

        ArrayList<MedicationStock> medicationList = new ArrayList<MedicationStock>();

        for (int i = 0; i < dataList.size(); i++) {
            // substring to remove the first and last characters which are brackets [ ]
            // then split it using a comma delimiter to allow iteration over the individual elements of data
            String[] temp = dataList.get(i).substring(1, dataList.get(i).length() - 1).split(",");
            String name = temp[0].trim();
            int initialStockInt = Integer.parseInt(temp[1].trim());
            int LowStockInt = Integer.parseInt(temp[2].trim());
            Medication med = new Medication(name, initialStockInt);
            medicationList.add(new MedicationStock(med, -1));
        }
        MedicationInventory mi = new MedicationInventory(medicationList);
        return mi;
    }
}
