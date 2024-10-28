// This class contains input validation examples/boilerplates for the various validation use cases
// Depending on your use case (e.g. integer selection, Y/N option), use the appropriate boilerplate accordingly

package Utility;

public class ValidatorBoilerplate {

    // For all instances of getting user input and validating said input, these two objects need to be instantiated
    // at the very top of the class (but still within the class itself)

//    Scanner scanner = new Scanner(System.in);
//    Validator validator = new Validator();

    // Remember to import these as well (put them above the class definition but below the main package)
//    import Controllers.*;
//    import Models.*;
//    import Utility.*;
//    import java.util.Scanner;


    // INPUT VALIDATION BOILERPLATES (ordered here via most used at the top -> least used at the bottom)

    // 1. "Option selection via integer" validation (Used in most, if not all menu option-based methods)

//    String input = "";
//    int selector = 0;
//    final int MAX_MENU_RANGE = 3;
//    boolean isValidSelectionType = true;
//
//     do {
//        do {
//            System.out.println("\nPlease select a user account type to test:");
//            System.out.println("1. Patient");
//            System.out.println("2. Doctor");
//            System.out.println("3. Exit");
//            input = scanner.nextLine();
//            isValidSelectionType = validator.validateSelectorInput(input, 1, MAX_MENU_RANGE);
//        } while (!isValidSelectionType);
//
//        selector = Integer.parseInt(input);
//        switch (selector) {
//            case 1:
//                break;
//            case 2:
//                break;
//            case 3:
//                break;
//        }
//
//    } while (selector != MAX_MENU_RANGE);


    // 2. validating Yes or No via (Y/N) character input (Used in menus and submenus with only 2 choices)

//    String input = "";
//    boolean isValidInput = true;
//
//        System.out.println("Would you like to test the login/logout feature? (Y/N)");
//        do {
//        input = scanner.nextLine().trim().toUpperCase();
//        isValidInput = validator.validateCharacterInput(input);
//    } while (!isValidInput);
//
//        if (input.charAt(0) == 'Y') {
//        enableLoginFeature();
//    }


    // 3. validateCredential (Login functionality only)

//    String username, password;
//    boolean isValidLoginCredential = true;
//    boolean isLoginSuccessful = false;
//    int currentLoginTries = 0;
//    final int maxLoginTries = 5;
//    int difference;
//
//        System.out.println("Welcome to the Hospital Management System! Please input your user credentials:");
//        do {
//        do {
//            System.out.print("USERNAME: ");
//            System.out.println("(Hint: The username is User1234)");
//            username = scanner.nextLine();
//            isValidLoginCredential = validator.validateCredential(username, "username");
//        } while (!isValidLoginCredential);
//        do {
//            System.out.print("PASSWORD: ");
//            System.out.println("(Hint: The password is Pass1234)");
//            password = scanner.nextLine();
//            isValidLoginCredential = validator.validateCredential(password, "password");
//        } while (!isValidLoginCredential);
//
//        // Username and password are hardcoded for now for testing purposes
//        if (username.equals("User1234") && password.equals("Pass1234")) {
//            System.out.println("You have successfully authenticated, congratulations!");
//            isLoginSuccessful = true;
//        } else {
//            System.out.println("Oh snap! User login unsuccessful!");
//            currentLoginTries++;
//            difference = maxLoginTries - currentLoginTries;
//            if (difference == 0) {
//                System.out.println("You have reached the maximum number of login attempts, sorry!");
//                System.exit(0);  // terminates the program
//            } else if (difference == 1) {
//                System.out.println("Warning! " + difference + " login attempt remaining!\n");
//            } else {
//                System.out.println(difference + " login attempts remaining!\n");
//            }
//        }
//    } while (!isLoginSuccessful);
}
