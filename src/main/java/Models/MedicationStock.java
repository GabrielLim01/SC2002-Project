package Models;

import Models.Medication;

public class MedicationStock {
    protected Medication medication;
    protected int replenishmentRequest;

    public MedicationStock(Medication medication, int replenishmentRequest) {
        this.medication = medication;
        this.replenishmentRequest = replenishmentRequest;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public int getReplenishmentRequest(){
        return replenishmentRequest;
    }

    public void setReplenishmentRequest(int replenishmentRequest) {
        this.replenishmentRequest = replenishmentRequest;
    }
}