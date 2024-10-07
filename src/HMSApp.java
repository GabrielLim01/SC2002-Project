import java.util.Scanner;
import java.util.regex.*;

public class HMSApp {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String username, password;
        boolean isValidLoginCredential = true;

        System.out.println("Welcome to the Hospital Management System! Please input your user credentials:");
        do {
            System.out.print("USERNAME: ");
            username = scanner.nextLine();
            isValidLoginCredential = validateCredential(username);
            if (!isValidLoginCredential){
                System.out.println("Your username is invalid! Please try again!");
            }
        }  while (!isValidLoginCredential);
        do {
            System.out.print("PASSWORD: ");
            password = scanner.nextLine();
            isValidLoginCredential = validateCredential(password);
            if (!isValidLoginCredential){
                System.out.println("Your password is invalid! Please try again!");
            }
        }  while (!isValidLoginCredential);

        // Replace the bodies of the if and else statements with a switch statement to reflect user role distribution later
        if (username.equals("User1234") && password.equals("Pass1234")){
            System.out.println("You have successfully authenticated, congratulations!");
        } else {
            System.out.println("Oh snap! User login unsuccessful!");
        }
    }

    // Method to validate user login credentials
    // Move this validation method to a separate validator class later
    public static boolean validateCredential(String input)
    {

        // Regex to check valid username or password.
        // Conditions specified in this regex include:
        // 1. Starting character must be in the lowercase or uppercase alphabet
        // 2. The input string must be between 6 and 30 characters
        String regex = "^[A-Za-z]\\w{5,29}$";

        // Compile the regex
        Pattern p = Pattern.compile(regex);

        // If input is empty
        // return false
        if (input == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given username
        // and regular expression.
        Matcher m = p.matcher(input);

        // Return if the username
        // matched the ReGex
        return m.matches();
    }
}
