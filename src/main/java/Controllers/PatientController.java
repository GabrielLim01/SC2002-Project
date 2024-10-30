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
    public void displayMenu(Patient patient, ArrayList<Doctor> doctorsList, ArrayList<Appointment> appointmentList) {
        int selector = 0;
        final int MAX_MENU_RANGE = 6;

        do {
            do {
                System.out.println("\nWelcome back, " + patient.getName() + "! What would you like to do today?");
                System.out.println("1. View Medical Record");
                System.out.println("2. Update Personal Info");
                System.out.println("3. Schedule An Appointment");
                System.out.println("4. Reschedule An Appointment");
                System.out.println("5. Cancel An Appointment");
                System.out.println("6. Exit");
                input = scanner.nextLine();
                isValidSelectionType = validator.validateSelectorInput(input, 1, MAX_MENU_RANGE);
            } while (!isValidSelectionType);

            selector = Integer.parseInt(input);
            switch (selector) {
                case 1:
                    viewMedicalRecord(patient);
                    break;
                case 2:
                    updatePersonalInfo(patient);
                    break;
                case 3:
                    appointmentPatientController.viewAvailAppts(patient, doctorsList, appointmentList);
                    break;
                case 4:
                    appointmentPatientController.rescheduleAppt(patient, doctorsList);
                    break;
                case 5:
                    appointmentPatientController.cancelAppt(patient, doctorsList);
                    break;
                case 6:
                    break; //this can be return too, doesn't matter, although it will make the while (selector != MAX_MENU_RANGE) redundant
            }
        } while (selector != MAX_MENU_RANGE);
    }

    public void viewMedicalRecord(Patient patient) {
        System.out.println("\nYour medical details are as follows:");
        System.out.println("Patient ID: " + patient.getId());
        System.out.println("Name: " + patient.getName());
        System.out.println("Date Of Birth: " + patient.getDateOfBirth().format(dateTimeFormatter));
        System.out.println("Gender: " + patient.getGender());
        System.out.println("Phone Number: " + patient.getPhoneNo());
        System.out.println("Email: " + patient.getEmail());
        System.out.println("Blood Type: " + patient.getBloodType());
        // TO-DO - implement displayMedHistory()
        // System.out.println("Past Diagnoses and Treatments: " + patient.displayMedHistory());
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
