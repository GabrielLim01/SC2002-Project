package Utility;

import Models.*;
import com.opencsv.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class DataProcessing {
    // formatters for date conversion
    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMM uuuu");

    // default constructor
    public DataProcessing() {
    }

    // TEST FEATURE - Read from csv file, write into ArrayList and print entries
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
        // [D001, John Smith, Models.Doctor, Male, 45]
        // [D002, Emily Clarke, Models.Doctor, Female, 38]
        // [P001, Mark Lee, Models.Pharmacist, Male, 29]
        // [A001, Sarah Lee, Models.Administrator, Female, 40]

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

            int phoneNo = 12345678;
            String bloodType = temp[4].trim();
            String email = temp[5].trim();

            patientList.add(new Patient(id, name, formattedDateOfBirth, gender, phoneNo, email, bloodType));

        }
        return patientList;
    }

    public ArrayList<Doctor> generateDoctorList(ArrayList<String> dataList) {

        ArrayList<Doctor> doctorList = new ArrayList<Doctor>();

        for (int i = 0; i < dataList.size(); i++) {
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
            doctorList.add(new Doctor(id, name, gender, age));

        }
        return doctorList;
    }
}
