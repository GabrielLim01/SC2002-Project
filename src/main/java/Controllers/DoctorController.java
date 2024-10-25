package Controllers;

import Models.Doctor;
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

    // @Override
    public void displayMenu(Doctor doctor) {
        int selector = 0;
        final int MAX_MENU_RANGE = 3;

        do {
            do {
                System.out.println("\nWelcome back! What would you like to do today?");
                System.out.println("1. View Medical Record");
                System.out.println("2. View Personal Schedule");
                System.out.println("3. Exit");
                input = scanner.nextLine();
                isValidSelectionType = validator.validateSelectorInput(input, 1, MAX_MENU_RANGE);
            } while (!isValidSelectionType);

            selector = Integer.parseInt(input);
            switch (selector) {
                case 1:
                    viewPersonalDetails(doctor);
                    break;
                case 2:
                    viewPersonalSchedule(doctor);
                    break;
                case 3:
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

    public void viewPersonalSchedule(Doctor doctor){
        System.out.println("Hello, " + doctor.getName() + "! You are available at the following timeslots:");
        doctor.getAvailability().forEach(System.out::println);
    }

    public void viewPersonalSchedulesOfAllDoctors(ArrayList<Doctor> doctorList){
        for (int i = 0; i < doctorList.size(); i++) {
            System.out.println(doctorList.get(i).getName() + " is available at the following timeslots:");
            doctorList.get(i).getAvailability().forEach(System.out::println);
            System.out.print("\n");
        }
    }

    public void login() {
    }

    public void logout() {
    }
}
