package Views;

import Controllers.*;
import Models.*;
import Utility.*;

import java.util.Scanner;

public class HMSApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Validator validator = new Validator();
        String username, password;
        boolean isValidLoginCredential = true;
        boolean isLoggedOut = false;

        // FOR TESTING PURPOSES
        Patient patient = new Patient("1", "John", "01 Jan 2001", 'F', 12345678, "john@gmail.com", "O+");
        PatientController patientController = new PatientController();
        // ArrayList<Models.Patient> patients = new ArrayList<Models.Patient>();
        // patients.add(new Models.Patient(1, "John", "01 Jan 2001", 'F', 12345678, "john@gmail.com", "O+"));

        System.out.println("Welcome to the Hospital Management System! Please input your user credentials:");
        do {
            System.out.print("USERNAME: ");
            username = scanner.nextLine();
            isValidLoginCredential = validator.validateCredential(username, "username");
        } while (!isValidLoginCredential);
        do {
            System.out.print("PASSWORD: ");
            password = scanner.nextLine();
            isValidLoginCredential = validator.validateCredential(password, "password");
        } while (!isValidLoginCredential);

        if (username.equals("User1234") && password.equals("Pass1234")) {
            System.out.println("You have successfully authenticated, congratulations!");

            // TO-DO:
            // 1. Look up user info based on username and/or password
            // 2. Retrieve this user object
            // 3. Make the following code dynamic by displaying the appropriate user menu based on the user's role
            // (...does it have to be a switch statement based on role? Wouldn't the user object itself be enough?)
            patientController.displayMenu(patient);

        } else {
            System.out.println("Oh snap! Models.User login unsuccessful!");
        }
    }
}
