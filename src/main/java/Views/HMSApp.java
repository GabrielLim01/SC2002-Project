package Views;

import Utility.*;

import java.util.Scanner;

public class HMSApp {

    public static void main(String[] args) {
        Driver driver = new Driver();
        DriverWithLogin driverWithLogin = new DriverWithLogin();

        Scanner scanner = new Scanner(System.in);
        Validator validator = new Validator();

        String input = "";
        int selector = 0;
        final int MAX_MENU_RANGE = 3;
        boolean isValidSelectionType = true;

        do {
            do {
                System.out.println("\nThis is the highest-level interface. You can test the HMS with or without login functionality here.");
                System.out.println("1. Test HMS without Login");
                System.out.println("2. Test HMS with Login");
                System.out.println("3. Exit");
                input = scanner.nextLine();
                isValidSelectionType = validator.validateSelectorInput(input, 1, MAX_MENU_RANGE);
            } while (!isValidSelectionType);

            selector = Integer.parseInt(input);
            switch (selector) {
                case 1:
                    driver.switchUserAccounts();
                    break;
                case 2:
                    driverWithLogin.switchUserAccounts();
                    break;
                default:
                    break;
            }

        } while (selector != MAX_MENU_RANGE);
    }
}
