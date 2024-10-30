package Models;

import Models.MedicationStock;
import java.util.ArrayList;

public class MedicationInventory {
    protected ArrayList<MedicationStock> medications;

    public MedicationInventory(ArrayList<MedicationStock> medications) {
        this.medications = medications;
    }
    
    public ArrayList<MedicationStock> getMedications() {
        return medications;
    }

    public void setMedications(ArrayList<MedicationStock> medications) {
        this.medications = medications;
    }
}