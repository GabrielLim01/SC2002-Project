package Controllers;

import Models.Doctor;
import Utility.Validator;

import java.util.Scanner;

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

    // @Override
    public void displayMenu(Doctor doctor) {
        int selector = 0;
        do {
            do {
                System.out.println("\nWelcome back! What would you like to do today?");
                System.out.println("1. View Medical Record");
                System.out.println("2. Exit");
                input = scanner.nextLine();
                isValidSelectionType = validator.validateSelectorInput(input, 1, 2);
            } while (!isValidSelectionType);

            selector = Integer.parseInt(input);
            switch (selector) {
                case 1:
                    viewPersonalDetails(doctor);
                    break;
                case 2:
                    break;
            }
        } while (selector != 2);
    }

    public void viewPersonalDetails(Doctor doctor) {
        System.out.println("\nYour personal details are as follows:");
        System.out.println("Doctor ID: " + doctor.getId());
        System.out.println("Name: " + doctor.getName());
        System.out.println("Gender: " + doctor.getGender());
        System.out.println("Age: " + doctor.getAge());
    }

    public void login() {
    }

    public void logout() {
    }
}
