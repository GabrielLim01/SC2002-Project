// Methods in this class are not related to any test cases at all and are merely for debugging purposes

package Utility;

import Controllers.*;
import Models.*;
import Utility.*;

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
        ArrayList<Patient> patientList = dp.generatePatientList(dp.readFromCSV("Patient_List.csv"));
        ArrayList<Doctor> doctorList = dp.generateDoctorList(dp.readFromCSV("Doctor_List.csv"));
        ArrayList<Appointment> appointmentList = dt.generateAppointmentList(doctorList);
        dp.updateDoctorsListWithAppointments(doctorList, appointmentList);

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
                    patientController.displayMenu(patientList.get(0), doctorList, appointmentList);
                    break;
                case 2:
                    doctorController.displayMenu(doctorList.get(0));
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
