package Controllers;

import Models.*;
import Utility.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class PatientController extends UserController {

    // default constructor
    public PatientController() {
    }

    // Objects and variables for data processing
    Scanner scanner = new Scanner(System.in);
    Validator validator = new Validator();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM uuuu");

    // Instantiating other classes
    // DoctorController doctorController = new DoctorController();
    AppointmentPatientController appointmentPatientController = new AppointmentPatientController();

    // These variables may need to be local scope
    String input = "";
    boolean isValidSelectionType = true;

    // @Override
    public int displayMenu(Patient patient, ArrayList<Doctor> doctors, ArrayList<Appointment> appointments) {
        int selector = 0;
        final int MAX_MENU_RANGE = 8;

        do {
            do {
                System.out.println("\nWelcome back, " + patient.getName() + "! What would you like to do today?");
                System.out.println("1. View Personal Details");
                System.out.println("2. Update Personal Info");
                System.out.println("3. Schedule An Appointment");
                System.out.println("4. Reschedule An Appointment");
                System.out.println("5. Cancel An Appointment");
                System.out.println("6. View Appointments");
                System.out.println("7. View Past Appointment Outcome Records");
                System.out.println("8. Exit");
                input = scanner.nextLine();
                isValidSelectionType = validator.validateSelectorInput(input, 1, MAX_MENU_RANGE);
            } while (!isValidSelectionType);

            selector = Integer.parseInt(input);
            switch (selector) {
                case 1:
                    viewPersonalDetails(patient);
                    break;
                case 2:
                    updatePersonalInfo(patient);
                    break;
                case 3:
                    appointmentPatientController.viewAvailAppts(patient, doctors, appointments);
                    break;
                case 4:
                    appointmentPatientController.rescheduleAppt(patient, doctors);
                    break;
                case 5:
                    appointmentPatientController.cancelAppt(patient);
                    break;
                case 6:
                    appointmentPatientController.viewAppts(patient);
                    break;
                case 7:
                    appointmentPatientController.viewApptOutcomeRec(patient);
                    break;
                case 8:
                    break; //this can be return too, doesn't matter, although it will make the while (selector != MAX_MENU_RANGE) redundant
            }
        } while (selector != MAX_MENU_RANGE);

        return 1;
    }

    public void viewPersonalDetails(Patient patient) {
//        System.out.println("\nYour personal details are as follows:");
//        System.out.println("Patient ID: " + patient.getId());
//        System.out.println("Name: " + patient.getName());
//        System.out.println("Date Of Birth: " + patient.getDateOfBirth().format(dateTimeFormatter));
//        System.out.println("Gender: " + patient.getGender());
//        System.out.println("Phone Number: " + patient.getPhoneNo());
//        System.out.println("Email: " + patient.getEmail());
//        System.out.println("Blood Type: " + patient.getBloodType());

        patient.displayMedicalRecords();
    }

    // Permitted editable fields: Phone number and email
    public void updatePersonalInfo(Patient patient) {
        int selector = 0;
        final int MAX_MENU_RANGE = 3;
        do {
            do {
                System.out.println("\nPlease select what field you would like to edit, or input 3 to return to the previous menu:");
                System.out.println("1. Phone number");
                System.out.println("2. Email");
                System.out.println("3. Exit");
                input = scanner.nextLine();
                isValidSelectionType = validator.validateSelectorInput(input, 1, MAX_MENU_RANGE);
            } while (!isValidSelectionType);

            selector = Integer.parseInt(input);
            switch (selector) {
                case 1:
                    System.out.println("Old phone number: " + patient.getPhoneNo());
                    System.out.print("New phone number: ");
                    input = scanner.nextLine();
                    // TO-DO: Validate input - both correct input type (int) and character limits (8 characters)
                    patient.setPhoneNo(Integer.parseInt(input));
                    System.out.println("Phone number successfully updated!");
                    break;
                case 2:
                    System.out.println("Old email: " + patient.getEmail());
                    System.out.print("New email: ");
                    input = scanner.nextLine();
                    // TO-DO: Validate input - both correct input type (String) and character limits (6 to 30 characters)
                    patient.setEmail(input);
                    System.out.println("Email successfully updated!");
                    break;
                case 3:
                    break;
            }
        } while (selector != MAX_MENU_RANGE);
    }

    public void login() {
    }

    public void logout() {
    }
}
