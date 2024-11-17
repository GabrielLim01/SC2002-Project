// Methods in this class are not related to any test cases at all and are merely for debugging purposes

package Utility;

import Controllers.*;
import Models.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Driver class for testing functionality of the Hospital Management System
 */
public class Driver {
    private Scanner scanner;
    private Validator validator;
    private DataProcessing dp;
    private DateTime dt;
    private AdministratorController adminController;

    /**
     * Constructor initializes required objects
     */
    public Driver() {
        this.scanner = new Scanner(System.in);
        this.validator = new Validator();
        this.dp = new DataProcessing();
        this.dt = new DateTime();
        this.adminController = new AdministratorController();
    }

    /**
     * Main method to switch between different user account tests
     */
    public void switchUserAccounts() {
        // Initialize controllers
        PatientController patientController = new PatientController();
        DoctorController doctorController = new DoctorController();

        // Initialize data structures
        ArrayList<Patient> patients = dp.generatePatientList(dp.readFromCSV("Patient_List.csv"));
        ArrayList<Doctor> doctors = dp.generateDoctorList(dp.readFromCSV("Doctor_List.csv"));
        ArrayList<Pharmacist> pharmacists = dp.generatePharmacistList(dp.readFromCSV("Pharmacist_List.csv"));
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

        // Pharmacist stuff
        MedicationInventory mi = dp.generateMedicationList(dp.readFromCSV("Medicine_List.csv"));
        PharmacistController pc = new PharmacistController(mi, doctors);

        // Local variables
        String input = "";
        int selector = 0;
        final int MAX_MENU_RANGE = 5;
        boolean isValidSelectionType = true;
        boolean isValidInput = true;

        // Test login feature
        System.out.println("Would you like to test the login/logout feature? (Y/N)");
        do {
            input = scanner.nextLine().trim().toUpperCase();
            isValidInput = validator.validateCharacterInput(input);
        } while (!isValidInput);

        if (input.charAt(0) == 'Y') {
            enableLoginFeature();
        }

        // Main menu loop
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
                    patientController.displayMenu(patients.get(0), doctors, appointments);
                    break;
                case 2:
                    doctorController.displayMenu(doctors.get(0));
                    break;
                case 3:
                    pc.displayMenu(pharmacists.get(0));
                    break;
                case 4:
                    testAdministratorFunctionality();
                    break;
                case 5:
                    break;
            }
        } while (selector != MAX_MENU_RANGE);
    }

    /**
     * Tests administrator functionality
     */
    private void testAdministratorFunctionality() {
        String input = "";
        int selector = 0;
        final int MAX_MENU_RANGE = 4;
        boolean isValidSelectionType = true;

        do {
            do {
                System.out.println("\nAdministrator Test Menu:");
                System.out.println("1. Test Staff Management");
                System.out.println("2. Test Appointment Management");
                System.out.println("3. Test Inventory Management");
                System.out.println("4. Back");
                input = scanner.nextLine();
                isValidSelectionType = validator.validateSelectorInput(input, 1, MAX_MENU_RANGE);
            } while (!isValidSelectionType);

            selector = Integer.parseInt(input);
            switch (selector) {
                case 1:
                    testStaffManagement();
                    break;
                case 2:
                    testAppointmentManagement();
                    break;
                case 3:
                    testInventoryManagement();
                    break;
                case 4:
                    break;
            }
        } while (selector != MAX_MENU_RANGE);
    }

    /**
     * Tests staff management functionality
     */
    private void testStaffManagement() {
        System.out.println("\nTesting Staff Management:");

        try {
            // Test 1: Add Doctor
            System.out.println("\nTest 1: Adding new doctor");
            boolean addDoctorResult = adminController.addDoctor("TD003", "Test Doctor 3", 'M', 45);
            System.out.println("Add Doctor Test: " + (addDoctorResult ? "PASSED" : "FAILED"));

            // Test 2: Add Pharmacist
            System.out.println("\nTest 2: Adding new pharmacist");
            boolean addPharmacistResult = adminController.addPharmacist("TP003", "Test Pharmacist 3", 'F', 32);
            System.out.println("Add Pharmacist Test: " + (addPharmacistResult ? "PASSED" : "FAILED"));

            // Test 3: Update Doctor
            System.out.println("\nTest 3: Updating doctor");
            boolean updateDoctorResult = adminController.updateDoctor("TD003", "Updated Doctor 3", 'M', 46);
            System.out.println("Update Doctor Test: " + (updateDoctorResult ? "PASSED" : "FAILED"));

            // Test 4: Remove Doctor
            System.out.println("\nTest 4: Removing doctor");
            boolean removeDoctorResult = adminController.removeDoctor("TD003");
            System.out.println("Remove Doctor Test: " + (removeDoctorResult ? "PASSED" : "FAILED"));

        } catch (Exception e) {
            System.out.println("Error during staff management tests: " + e.getMessage());
        }
    }

    /**
     * Tests appointment management functionality
     */
    private void testAppointmentManagement() {
        System.out.println("\nTesting Appointment Management:");

        try {
            // Test 1: View All Appointments
            System.out.println("\nTest 1: Viewing all appointments");
            adminController.viewAllAppointments();
            System.out.println("View All Appointments Test: PASSED");

            // Test 2: View Appointments by Status
            System.out.println("\nTest 2: Viewing appointments by status");
            adminController.viewAppointmentsByStatus("CONFIRMED");
            System.out.println("View Appointments by Status Test: PASSED");

            // Test 3: View Appointment Statistics
            System.out.println("\nTest 3: Viewing appointment statistics");
            adminController.viewAppointmentStatistics();
            System.out.println("View Appointment Statistics Test: PASSED");

        } catch (Exception e) {
            System.out.println("Error during appointment management tests: " + e.getMessage());
        }
    }

    /**
     * Tests inventory management functionality
     */
    private void testInventoryManagement() {
        System.out.println("\nTesting Inventory Management:");

        try {
            // Test 1: View Inventory
            System.out.println("\nTest 1: Viewing inventory");
            adminController.viewMedicationInventory();
            System.out.println("View Inventory Test: PASSED");

            // Test 2: Update Stock
            System.out.println("\nTest 2: Updating stock");
            boolean updateStockResult = adminController.updateMedicationStock("Paracetamol", 150);
            System.out.println("Update Stock Test: " + (updateStockResult ? "PASSED" : "FAILED"));

        } catch (Exception e) {
            System.out.println("Error during inventory management tests: " + e.getMessage());
        }
    }

    /**
     * Tests login functionality
     */
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

            if (username.equals("User1234") && password.equals("Pass1234")) {
                System.out.println("You have successfully authenticated, congratulations!");
                isLoginSuccessful = true;
            } else {
                System.out.println("Oh snap! User login unsuccessful!");
                currentLoginTries++;
                difference = maxLoginTries - currentLoginTries;
                if (difference == 0) {
                    System.out.println("You have reached the maximum number of login attempts, sorry!");
                    System.exit(0);
                } else if (difference == 1) {
                    System.out.println("Warning! " + difference + " login attempt remaining!\n");
                } else {
                    System.out.println(difference + " login attempts remaining!\n");
                }
            }
        } while (!isLoginSuccessful);
    }
}