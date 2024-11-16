// Methods in this class are not related to any test cases at all and are merely for debugging purposes

package Utility;

import Controllers.*;
import Models.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Special {

    // Instantiation of global class objects
    Scanner scanner = new Scanner(System.in);
    Validator validator = new Validator();

    public void switchUserAccounts() {
        // Instantiation of local class objects
        DataProcessing dp = new DataProcessing();
        DateTime dt = new DateTime();
        PatientController patientController = new PatientController();
        DoctorController doctorController = new DoctorController();

        // Initialization of data structures
        ArrayList<Patient> patients = dp.generatePatientList(dp.readFromCSV("Patient_List.csv"));
        ArrayList<Doctor> doctors = dp.generateDoctorList(dp.readFromCSV("Doctor_List.csv"));
        ArrayList<Appointment> appointments = dt.generateAppointmentsList(doctors);
        dp.updateDoctorsWithAppointments(doctors, appointments);

        // Hardcoded assigning patients to doctors
        // Assign Patient 1 to Doctor 1, and Patient 2 to Doctor 2 as per the order in the csv files
        // There is no particular reason for assigning them in this particular order,
        // it's just so that the doctors have a pre-existing patient(s) to view records for (i.e. for testing purposes)
        ArrayList<Patient> patientForDoctorOne = new ArrayList<>();
        ArrayList<Patient> patientForDoctorTwo = new ArrayList<>();
        patientForDoctorOne.add(patients.get(0));
        patientForDoctorTwo.add(patients.get(1));
        doctors.get(0).setPatients(patientForDoctorOne);
        doctors.get(1).setPatients(patientForDoctorTwo);

        // Hardcoded one medical record for the first patient
        ArrayList<MedicalRecord> medicalRecords = new ArrayList<>();
        MedicalRecord medicalRecord = new MedicalRecord("MR0001", patients.get(0),
                "Diagnosed with fever, possible flu", "Prescribed Panadol for the fever");
        medicalRecords.add(medicalRecord);
        patients.get(0).setMedicalRecords(medicalRecords);

        // Initialization of local variables
        String input = "";
        int selector = 0;
        final int MAX_MENU_RANGE = 5;
        boolean isValidSelectionType = true;
        boolean isValidInput = true;

        System.out.println("Would you like to test the login/logout feature? (Y/N)");
        do {
            input = scanner.nextLine().trim().toUpperCase();
            isValidInput = validator.validateCharacterInput(input);
        } while (!isValidInput);

        if (input.charAt(0) == 'Y') {
            enableLoginFeature();
        }

        do {
            do {
                System.out.println("\nPlease select a user account type to test:");
                System.out.println("1. Patient");
                System.out.println("2. Doctor");
                System.out.println("3. Pharmacist");
                System.out.println("4. Administrator");
                System.out.println("5. Exit");
                input = scanner.nextLine();
                isValidSelectionType = validator.validateSelectorInput(input, 1, MAX_MENU_RANGE);
            } while (!isValidSelectionType);

            selector = Integer.parseInt(input);
            switch (selector) {
                case 1:
                    // big problem here - appointmentList cannot be a static parameter, has to be generated at runtime since appointment availability constantly updates
                    // will need to modify my generation method later
                    // (Oct 30, 2024 update) Actually there is no problem since I am updating the appointments lists directly
                    patientController.displayMenu(patients.get(0), doctors, appointments);
                    break;
                case 2:
                    doctorController.displayMenu(doctors.get(0));
                    break;
                case 3:
                    System.out.println("The Pharmacist feature has not yet been implemented.");
                    break;
                case 4:
                    System.out.println("The Administrator feature has not yet been implemented.");
                    break;
                case 5:
                    break;
            }

        } while (selector != MAX_MENU_RANGE);
    }

    public void enableLoginFeature() {
        String username, password;
        boolean isValidLoginCredential = true;
        boolean isLoginSuccessful = false;
        int currentLoginTries = 0;
        final int maxLoginTries = 5;
        int difference;

        System.out.println("Welcome to the Hospital Management System! Please input your user credentials:");
        do {
            do {
                System.out.print("USERNAME: ");
                System.out.println("(Hint: The username is User1234)");
                username = scanner.nextLine();
                isValidLoginCredential = validator.validateCredential(username, "username");
            } while (!isValidLoginCredential);
            do {
                System.out.print("PASSWORD: ");
                System.out.println("(Hint: The password is Pass1234)");
                password = scanner.nextLine();
                isValidLoginCredential = validator.validateCredential(password, "password");
            } while (!isValidLoginCredential);

            // Username and password are hardcoded for now for testing purposes
            if (username.equals("User1234") && password.equals("Pass1234")) {
                System.out.println("You have successfully authenticated, congratulations!");
                isLoginSuccessful = true;
            } else {
                System.out.println("Oh snap! User login unsuccessful!");
                currentLoginTries++;
                difference = maxLoginTries - currentLoginTries;
                if (difference == 0) {
                    System.out.println("You have reached the maximum number of login attempts, sorry!");
                    System.exit(0);  // terminates the program
                } else if (difference == 1) {
                    System.out.println("Warning! " + difference + " login attempt remaining!\n");
                } else {
                    System.out.println(difference + " login attempts remaining!\n");
                }
            }
        } while (!isLoginSuccessful);
    }
}
