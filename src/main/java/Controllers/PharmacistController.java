package Controllers;

import Models.Pharmacist;
import Models.Doctor;
import Models.MedicationInventory;
import Utility.Validator;

import java.util.Scanner;
import java.util.ArrayList;

public class PharmacistController extends UserController {
    private AppointmentPharmacistController appointmentPharmacistController;
    private InventoryPharmacistController inventoryPharmacistController;

    // default constructor
    public PharmacistController(MedicationInventory medicationInventory, ArrayList<Doctor> doctors) {
        // no need to store doctors and medication in class, we can directly pass them to the required controllers.
        this.appointmentPharmacistController = new AppointmentPharmacistController(doctors);
        this.inventoryPharmacistController = new InventoryPharmacistController(medicationInventory);
    }
   
    Scanner scanner = new Scanner(System.in);
    Validator validator = new Validator();
    String input = "";
    boolean isValidSelectionType = true;

    // doctors needs to be passed to parmacist so that they can find the respective appointment within the doctor class to view it.

    public void displayMenu(Pharmacist pharmacist) {
        int selector = 0;
        final int MAX_MENU_RANGE = 6;

        do {
            do {
                System.out.println("\nWelcome back, " + pharmacist.getName() + "! What would you like to do today?");
                System.out.println("1. View Personal Details");
                System.out.println("2. View Appointment Outcome Record");
                System.out.println("3. Update Prescription Status");
                System.out.println("4. View Medication Inventory");
                System.out.println("5. Submit Replenishment Request");
                System.out.println("6. Exit");
                input = scanner.nextLine();
                isValidSelectionType = validator.validateSelectorInput(input, 1, MAX_MENU_RANGE);
            } while (!isValidSelectionType);

            selector = Integer.parseInt(input);
            switch (selector) {
                case 1:
                    viewPersonalDetails(pharmacist);
                    break;
                case 2:
                    appointmentPharmacistController.viewApptOutcomeRec();
                    break;
                case 3:
                    appointmentPharmacistController.updatePrescriptionStatus();
                    break;
                case 4:
                    inventoryPharmacistController.viewMedicationInventory();
                    break;
                case 5:
                    inventoryPharmacistController.submitReplenishmentRequest();
                    break;
                case 6:
                    break;
            }
        } while (selector != MAX_MENU_RANGE);
    }
    
    public void viewPersonalDetails(Pharmacist pharmacist) {
        System.out.println("\nYour personal details are as follows:");
        System.out.println("Pharmacist ID: " + pharmacist.getId());
        System.out.println("Name: " + pharmacist.getName());
        System.out.println("Gender: " + pharmacist.getGender());
        System.out.println("Age: " + pharmacist.getAge());
    }

    public void login() {
    }

    public void logout() {
    }
}

