package Utility;

import java.util.regex.*;

public class Validator {

    // default constructor
    public Validator(){}

    // TO-DO:
    // 1. Shift invalid console output messages in here - handling this requires passing an additional fieldType variable as a method parameter
    // 2. Handle validation logic for invalid username and/or password + corresponding output msg
    public boolean validateCredential(String input, String fieldType)
    {
        // Regex to check if the username or password is valid
        // Conditions specified in this regex include:
        // 1. Starting character must be in the lowercase or uppercase alphabet
        // 2. The input string must be between 6 and 30 characters
        String regex = "^[A-Za-z]\\w{5,29}$";

        // Compile the regex
        Pattern p = Pattern.compile(regex);

        // If input is empty, return false
        if (input == null) {
            return false;
        }

        // Pattern class contains matcher() method to find matching between given username and regular expression
        Matcher m = p.matcher(input);

        if (!m.matches()){
            System.out.println("Your " + fieldType + " is invalid! Please try again!");
        }
        // Return if the username matches the regex
        return m.matches();
    }

    // Checks if user input is an integer, then checks to see whether it falls within the available integer range of options for selection
    public boolean validateSelectorInput(String input, int minRange, int maxRange){
        try {
            int selector = Integer.parseInt(input);
            if (selector < minRange || selector > maxRange){
                System.out.println("Please enter a valid integer between " + minRange + " and " + maxRange + ".");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid integer!");
            return false;
        }
    }

    public boolean validateStringInput(String input, String fieldType)
    {
        // Regex to check if the username or password is valid
        // Conditions specified in this regex include:
        // 1. Starting character must be in the lowercase or uppercase alphabet
        // 2. The input string must be between 6 and 30 characters
        String regex = "^[A-Za-z]\\w{5,29}$";

        // Compile the regex
        Pattern p = Pattern.compile(regex);

        // If input is empty, return false
        if (input == null) {
            return false;
        }

        // Pattern class contains matcher() method to find matching between given username and regular expression
        Matcher m = p.matcher(input);

        if (!m.matches()){
            System.out.println("Your " + fieldType + " is invalid! Please try again!");
        }
        // Return if the username matches the regex
        return m.matches();
    }
}
