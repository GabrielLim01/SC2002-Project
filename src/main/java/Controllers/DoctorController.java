package Controllers;

import Models.*;
import Utility.Validator;

import java.util.Scanner;
import java.util.ArrayList;

public class DoctorController extends UserController {

    // default constructor
    public DoctorController() {
    }

    // Objects and variables for data processing
    Scanner scanner = new Scanner(System.in);
    Validator validator = new Validator();

    // These variables may need to be local scope
    String input = "";
    boolean isValidSelectionType = true;

    // Instantiated classes
    AppointmentDoctorController appointmentDoctorController = new AppointmentDoctorController();

    // @Override
    public void displayMenu(Doctor doctor) {
        int selector = 0;
        final int MAX_MENU_RANGE = 6;

        do {
            do {
                System.out.println("\nWelcome back, " + doctor.getName() + "! What would you like to do today?");
                System.out.println("1. View Personal Details");
                System.out.println("2. View Patient Medical Records");
                System.out.println("3. Update Patient Medical Records");
                System.out.println("4. View Personal Schedule");
                System.out.println("5. Manage Appointment Requests");
                System.out.println("6. Exit");
                input = scanner.nextLine();
                isValidSelectionType = validator.validateSelectorInput(input, 1, MAX_MENU_RANGE);
            } while (!isValidSelectionType);

            selector = Integer.parseInt(input);
            switch (selector) {
                case 1:
                    viewPersonalDetails(doctor);
                    break;
                case 2:
                    viewPatientMedicalRecords(doctor);
                    break;
                case 3:
                    updatePatientMedicalRecords(doctor);
                    break;
                case 4:
                    viewPersonalSchedule(doctor);
                    break;
                case 5:
                    appointmentDoctorController.manageApptRequests(doctor);
                    break;
                case 6:
                    break;
            }
        } while (selector != MAX_MENU_RANGE);
    }

    // THIS IS NOT A TEST CASE FOR THE ASSIGNMENT, THIS IS AN EXTRA FEATURE
    public void viewPersonalDetails(Doctor doctor) {
        System.out.println("\nYour personal details are as follows:");
        System.out.println("Doctor ID: " + doctor.getId());
        System.out.println("Name: " + doctor.getName());
        System.out.println("Gender: " + doctor.getGender());
        System.out.println("Age: " + doctor.getAge());
    }

    public void viewPatientMedicalRecords(Doctor doctor) {
        String input = "";
        int selector = 0;
        boolean isValidSelectionType = true;
        ArrayList<Patient> patients = doctor.getPatients();

        if (!patients.isEmpty()) {
            int maxPatientsRange = (patients.size() + 1);
            System.out.println("\nWhich patient's medical record would you like to view?");
            for (int i = 0; i < doctor.getPatients().size(); i++) {
                System.out.println((i + 1) + ". " + patients.get(i).getName());
            }
            System.out.println((patients.size() + 1) + ". Back");

            do {
                input = scanner.nextLine();
                isValidSelectionType = validator.validateSelectorInput(input, 1, maxPatientsRange);
            } while (!isValidSelectionType);

            selector = (Integer.parseInt(input) - 1);

            if (selector != (maxPatientsRange - 1)) {
                patients.get(selector).displayMedicalRecords();
            }
        } else {
            System.out.println("You have no patients under your care.");
        }
    }

    public void updatePatientMedicalRecords(Doctor doctor) {
        String input = "";
        int selector = 0;
        boolean isValidSelectionType = true;
        ArrayList<Patient> patients = doctor.getPatients();

        // outer list
        if (!patients.isEmpty()) {
            int maxPatientsRange = (patients.size() + 1);
            System.out.println("\nWhich patient's medical record would you like to update?");
            for (int i = 0; i < patients.size(); i++) {
                System.out.println((i + 1) + ". " + patients.get(i).getName());
            }
            System.out.println((patients.size() + 1) + ". Back");

            do {
                input = scanner.nextLine();
                isValidSelectionType = validator.validateSelectorInput(input, 1, maxPatientsRange);
            } while (!isValidSelectionType);

            selector = (Integer.parseInt(input) - 1);

            if (selector != (maxPatientsRange - 1)) {
                ArrayList<MedicalRecord> medicalRecords = patients.get(selector).getMedicalRecords();

                // inner list - same coding structure, would like to abstract if possible
                // many other functions use a similar outer-inner list structure as well as similar logic inside them too
                if (!medicalRecords.isEmpty()) {
                    int maxRecordsRange = (medicalRecords.size() + 1);
                    System.out.println("\nWhich medical record would you like to update?");
                    for (int i = 0; i < medicalRecords.size(); i++) {
                        System.out.println((i + 1) + ". " + medicalRecords.get(i).getId());
                    }
                    System.out.println((medicalRecords.size() + 1) + ". Back");

                    do {
                        input = scanner.nextLine();
                        isValidSelectionType = validator.validateSelectorInput(input, 1, maxPatientsRange);
                    } while (!isValidSelectionType);

                    selector = (Integer.parseInt(input) - 1);

                    if (selector != (maxPatientsRange - 1)) {
                        String diagnosis, treatment;
                        System.out.println("\nPlease enter a new message for the diagnosis, or leave it empty to keep it unchanged:");
                        diagnosis = scanner.nextLine();
                        System.out.println("Please enter a new message for the treatment, or leave it empty to keep it unchanged:");
                        treatment = scanner.nextLine();

                        if (!diagnosis.isEmpty()) {
                            medicalRecords.get(selector).setDiagnosis(diagnosis);
                        }

                        if (!treatment.isEmpty()) {
                            medicalRecords.get(selector).setTreatment(treatment);
                        }

                        System.out.println("\nMedical record successfully updated.");

                    } else {
                        System.out.println("This patient has no medical records to update.");
                    }
                } else {
                    System.out.println("You have no patients under your care.");
                }
            }
        }
    }

    public void viewPersonalSchedule(Doctor doctor) {
        System.out.println("Hello, " + doctor.getName() + "! You are available at the following timeslots:");
        for (int i = 0; i < doctor.getAvailability().size(); i++) {
            if (doctor.getAvailability().get(i).getStatus().equals(Appointment.Status.AVAILABLE.toString())) {
                System.out.println(doctor.getAvailability().get(i).getDate() + " " +
                        doctor.getAvailability().get(i).getTime());
            }
        }
    }

    // DEPRECATED
    // Additional method not required in test cases
//    public void viewPersonalSchedulesOfAllDoctors(ArrayList<Doctor> doctors){
//        for (int i = 0; i < doctors.size(); i++) {
//            System.out.println(doctors.get(i).getName() + " is available at the following timeslots:");
//            doctors.get(i).getAvailability().forEach(System.out::println);
//            System.out.print("\n");
//        }
//    }

    public void login() {
    }

    public void logout() {
    }
}
