package Controllers;

import Models.MedicationInventory;
import Utility.Validator;
import java.util.Scanner;

public class InventoryPharmacistController {
    private MedicationInventory medicationInventory;

    Scanner scanner = new Scanner(System.in);
    
    public InventoryPharmacistController(MedicationInventory medicationInventory) {
        this.medicationInventory = medicationInventory;
    }

    Validator validator = new Validator();
    String input = "";
    boolean isValidSelectionType = true;

    public void viewMedicationInventory() {
        System.out.println("These are the medications in the medication inventory:");
        for (int i = 0; i < medicationInventory.getMedications().size(); i++) {
            System.out.printf("%s: %d\n", medicationInventory.getMedications().get(i).getMedication().getName(), medicationInventory.getMedications().get(i).getMedication().getAmount());
        }
    }

    public void submitReplenishmentRequest() {
        System.out.println("Select which medication you would like to replenish:");
        for (int i = 0; i < medicationInventory.getMedications().size(); i++) {
            System.out.printf("%d: %s\n", i, medicationInventory.getMedications().get(i).getMedication().getName());
        }

        int x = scanner.nextInt();
        System.out.println("Input the amount to replenish:");
        int amt =  scanner.nextInt();
        medicationInventory.getMedications().get(x).setReplenishmentRequest(amt);
    }
}