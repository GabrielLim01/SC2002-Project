import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Patient extends User {
    // Objects and variables for data processing
    Scanner scanner = new Scanner(System.in);
    Validator validator = new Validator();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM uuuu");

    // These variables may need to be local scope
    String input = "";
    // int selector = 0;    selector DEFINITELY needs to be local scope...
    boolean isValidSelectionType = true;

    // attributes
    private String id;
    private String name;
    private LocalDate dateOfBirth;
    private char gender; //can be M (male), F (female), or O (other)
    private int phoneNo;
    private String email;
    private String bloodType; //it cannot simply be char, because of Rh protein (e.g. O+ / O- blood type)
    private String role;

    // default constructor
    public Patient() {
    }

    // standard constructor
    public Patient(String id, String name, String dateOfBirth, char gender, int phoneNo, String email, String bloodType) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = LocalDate.parse(dateOfBirth, dateTimeFormatter);
        this.gender = gender;
        this.phoneNo = phoneNo;
        this.email = email;
        this.bloodType = bloodType;
        this.role = Roles.PATIENT.toString();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public char getGender() {
        return gender;
    }

    public int getPhoneNo() {
        return phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getRole() {
        return role;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setPhoneNo(int phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // @Override
    public void displayMenu(Patient patient) {
        int selector = 0;
        do {
            do {
                System.out.println("\nWelcome back! What would you like to do today?");
                System.out.println("1. View Medical Record");
                System.out.println("2. Update Personal Info");
                System.out.println("3. Exit");
                input = scanner.nextLine();
                isValidSelectionType = validator.validateSelectorInput(input, 1, 3);
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
                    break; //this can be return too, doesn't matter, although it will make the while (selector != 3) redundant
            }
        } while (selector != 3);
    }

    public void viewMedicalRecord(Patient patient) {
        System.out.println("\nYour medical details are as follows:");
        System.out.println("Patient ID: " + patient.getId());
        System.out.println("Name: " + patient.getName());
        System.out.println("Date Of Birth: " + patient.getDateOfBirth());
        System.out.println("Gender: " + patient.getGender());
        System.out.println("Phone Number: " + patient.getPhoneNo());
        System.out.println("Email: " + patient.getEmail());
        System.out.println("Blood Type: " + patient.getBloodType());
        //System.out.println("Past Diagnoses and Treatments: " + patient.displayMedHistory());
    }

    // Permitted editable fields: Phone number and email
    public void updatePersonalInfo(Patient patient) {
        int selector = 0;
        do {
            do {
                System.out.println("\nPlease select what field you would like to edit, or input 3 to return to the previous menu:");
                System.out.println("1. Phone number");
                System.out.println("2. Email");
                System.out.println("3. Exit");
                input = scanner.nextLine();
                isValidSelectionType = validator.validateSelectorInput(input, 1, 3);
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
        } while (selector != 3);
    }

    public void login() {
    }

    public void logout() {
    }
};

