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
        String username;
        String role;

        username = login();
        role = validator.findUserRole(username);

        do {
            switch (role) {
                case "Patient":
                    // big problem here - appointmentList cannot be a static parameter, has to be generated at runtime since appointment availability constantly updates
                    // will need to modify my generation method later
                    // (Oct 30, 2024 update) Actually there is no problem since I am updating the appointments lists directly
                    Patient patientUser = Helper.findPatient(patientList, username);
                    if (patientUser == null){
                        System.out.println("ERROR: UNABLE TO FIND PATIENT AFTER LOGIN!");
                        return;
                    }
                    selector = patientController.displayMenu(patientUser, doctorList, appointmentList);
                    break;
                case "Doctor":
                    Doctor doctorUser = Helper.findDoctor(doctorList, username);
                    if (doctorUser == null){
                        System.out.println("ERROR: UNABLE TO FIND DOCTOR AFTER LOGIN!");
                        return;
                    }
                    selector = doctorController.displayMenu(doctorUser);
                    break;
                case "Pharmacist":
                    System.out.println("The Pharmacist feature has not yet been implemented.");
                    break;
                case "Admin":
                    System.out.println("The Administrator feature has not yet been implemented.");
                    break;
                default:
                    break;
            }

        } while (selector < 1);
    }

    public String login() {
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
                isValidLoginCredential = validator.validateUsername(username);
            } while (!isValidLoginCredential);
            do {
                System.out.print("PASSWORD: ");
                System.out.println("(Hint: The password is Pass1234)");
                password = scanner.nextLine();
                isValidLoginCredential = validator.validatePassword(username, password);

                if (isValidLoginCredential){
                    System.out.println("You have successfully authenticated, congratulations!");
                    isLoginSuccessful = true;
                }

                else{
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
                    isLoginSuccessful = false;
                }

            } while (!isValidLoginCredential || !isLoginSuccessful);

        } while (!isLoginSuccessful);

        return username;
    }
}
