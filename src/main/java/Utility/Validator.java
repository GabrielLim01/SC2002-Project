// This class handles input validation and exception/error handling, as well as printing error/invalid input messages

package Utility;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.*;

public class Validator {

    DataProcessing dp = new DataProcessing();
    ArrayList<String> credentialList = dp.readFromCSV("Credentials.csv");

    Map<String, Map<String, String>> credentialMap = dp.getCredentialmap(credentialList);

    // default constructor
    public Validator(){}

    // TO-DO:
    // 1. Handle validation logic for incorrect username and/or password combination + corresponding output msg

    public boolean validateCredential(String input, String fieldType){

        // Regex to check if the username or password is valid
        // Conditions specified in this regex include:
        // 1. Starting character must be in the lowercase or uppercase alphabet
        // 2. The input string must be between 6 and 30 characters
        String regex = "^[A-Za-z][\\w!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]{5,29}$";

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
        // Return true if the username matches the regex

        return m.matches();
    }

    public boolean validateUsername(String username)
    {

        if (!this.validateCredential(username, "username")){
            return false;
        }

        // Check if username is valid. If valid return True
        return credentialMap.containsKey(username);
    }

    public boolean validatePassword(String username, String password)
    {
        if (!this.validateCredential(password, "password")){
            return false;
        }

        System.out.println(credentialMap.get(username).get("Password"));
        // Check if username is valid. If valid return True
        return credentialMap.get(username).get("Password").equals(password);
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

    // NOT IMPLEMENTED YET - INCOMPLETE
    // Meant to be used in methods which require updating of fields that are user data-related (phone number, email etc.)
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

    // Used to validate Yes or No (Y/N) user responses
    public boolean validateCharacterInput(String input)
    {
        // If input is empty, return false
        if (input.isEmpty()) {
            System.out.println("Please enter something, thank you.");
            return false;
        } else if (input.length() > 1){
            System.out.println("Please do not enter more than one character, thank you.");
            return false;
        }

        char firstChar = input.charAt(0);

        // Check whether the input is either Y or N
        Matcher m = Pattern.compile("^[YN]$").matcher(Character.toString(firstChar));

        if (!m.matches()){
            System.out.println("Please only input 'Y' or 'N', thank you.");
        }

        // Return true if the username matches the regex
        return m.matches();
    }

    // To be used if HashMap.containsKey fails due to object reference error
    private boolean checkKeyExist(Map<String, Map<String, String>> mapToCheck, String keyToFind){
        for(String a : mapToCheck.keySet()){
            System.out.println(a);
            System.out.println(a.equals(keyToFind));
            if (a.equals(keyToFind)){
                return true;
            }
        }
        return false;
    }

    public String findUserRole(String username){

        return credentialMap.get(username).get("Role");
    }
}
