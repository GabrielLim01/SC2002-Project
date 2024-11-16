package Controllers;

import Models.*;
import Utility.DataProcessing;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Controller class for Administrator functionality in the Hospital Management System.
 * Handles staff management, appointment viewing, and inventory management.
 */
public class AdministratorController extends UserController {
    private ArrayList<Doctor> doctorList;
    private ArrayList<Pharmacist> pharmacistList;
    private MedicationInventory medicationInventory;
    private DataProcessing dataProcessor;

    /**
     * Constructor initializes lists and loads data
     */
    public AdministratorController() {
        this.dataProcessor = new DataProcessing();
        this.initialiseStaffLists();
        this.initialiseMedicationInventory();
    }

    public void login() {
    }

    public void logout() {
    }

    /**
     * Initialises staff lists from CSV files
     */
    private void initialiseStaffLists() {
        ArrayList<String> doctorData = dataProcessor.readFromCSV("Doctor_List.csv");
        ArrayList<String> pharmacistData = dataProcessor.readFromCSV("Pharmacist_List.csv");
        this.doctorList = dataProcessor.generateDoctorList(doctorData);
        this.pharmacistList = dataProcessor.generatePharmacistList(pharmacistData);
    }

    /**
     * Initialises medication inventory from CSV file
     */
    private void initialiseMedicationInventory() {
        ArrayList<String> medicationData = dataProcessor.readFromCSV("Medicine_List.csv");
        ArrayList<MedicationStock> medicationStocks = new ArrayList<>();

        for (String data : medicationData) {
            String[] temp = data.substring(1, data.length() - 1).split(",");
            String name = temp[0].trim();
            int initialStock = Integer.parseInt(temp[1].trim());
            int lowStockAlert = Integer.parseInt(temp[2].trim());

            Medication medication = new Medication(name, initialStock);
            MedicationStock stock = new MedicationStock(medication, 0);
            medicationStocks.add(stock);
        }

        this.medicationInventory = new MedicationInventory(medicationStocks);
    }

    // ===================== STAFF MANAGEMENT METHODS =====================

    /**
     * Adds a new doctor to the system
     * @param id Unique identifier for the doctor
     * @param name Doctor's full name
     * @param gender Doctor's gender (M/F/O)
     * @param age Doctor's age
     * @return true if addition successful, false otherwise
     */
    public boolean addDoctor(String id, String name, char gender, int age) {
        if (!validateStaffInput(id, name, gender, age)) {
            return false;
        }

        if (doctorList.stream().anyMatch(doc -> doc.getId().equals(id))) {
            System.out.println("Error: Doctor ID already exists.");
            return false;
        }

        Doctor newDoctor = new Doctor(id, name, gender, age, new ArrayList<>(), null);
        doctorList.add(newDoctor);
        return true;
    }

    /**
     * Adds a new pharmacist to the system
     * @param id Unique identifier for the pharmacist
     * @param name Pharmacist's full name
     * @param gender Pharmacist's gender (M/F/O)
     * @param age Pharmacist's age
     * @return true if addition successful, false otherwise
     */
    public boolean addPharmacist(String id, String name, char gender, int age) {
        if (!validateStaffInput(id, name, gender, age)) {
            return false;
        }

        if (pharmacistList.stream().anyMatch(pharm -> pharm.getId().equals(id))) {
            System.out.println("Error: Pharmacist ID already exists.");
            return false;
        }

        Pharmacist newPharmacist = new Pharmacist(id, name, gender, age);
        pharmacistList.add(newPharmacist);
        return true;
    }

    /**
     * Removes a doctor from the system
     * @param id Doctor's ID to remove
     * @return true if removal successful, false otherwise
     */
    public boolean removeDoctor(String id) {
        boolean removed = doctorList.removeIf(doc -> doc.getId().equals(id));
        if (!removed) {
            System.out.println("Error: Doctor not found.");
        }
        return removed;
    }

    /**
     * Removes a pharmacist from the system
     * @param id Pharmacist's ID to remove
     * @return true if removal successful, false otherwise
     */
    public boolean removePharmacist(String id) {
        boolean removed = pharmacistList.removeIf(pharm -> pharm.getId().equals(id));
        if (!removed) {
            System.out.println("Error: Pharmacist not found.");
        }
        return removed;
    }

    /**
     * Updates doctor information
     * @param id Doctor's ID to update
     * @param name New name (null if unchanged)
     * @param gender New gender (0 if unchanged)
     * @param age New age (-1 if unchanged)
     * @return true if update successful, false otherwise
     */
    public boolean updateDoctor(String id, String name, char gender, int age) {
        Doctor doctor = doctorList.stream()
                .filter(doc -> doc.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (doctor == null) {
            System.out.println("Error: Doctor not found.");
            return false;
        }

        if (name != null) doctor.setName(name);
        if (gender != '0') doctor.setGender(gender);
        if (age != -1) doctor.setAge(age);

        return true;
    }

    /**
     * Updates pharmacist information
     * @param id Pharmacist's ID to update
     * @param name New name (null if unchanged)
     * @param gender New gender (0 if unchanged)
     * @param age New age (-1 if unchanged)
     * @return true if update successful, false otherwise
     */
    public boolean updatePharmacist(String id, String name, char gender, int age) {
        Pharmacist pharmacist = pharmacistList.stream()
                .filter(pharm -> pharm.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (pharmacist == null) {
            System.out.println("Error: Pharmacist not found.");
            return false;
        }

        if (name != null) pharmacist.setName(name);
        if (gender != '0') pharmacist.setGender(gender);
        if (age != -1) pharmacist.setAge(age);

        return true;
    }

    // ===================== APPOINTMENT MANAGEMENT METHODS =====================

    /**
     * Views all appointments in the system
     */
    public void viewAllAppointments() {
        ArrayList<Appointment> allAppointments = new ArrayList<>();

        for (Doctor doctor : doctorList) {
            if (doctor.getAvailability() != null) {
                allAppointments.addAll(doctor.getAvailability());
            }
        }

        if (allAppointments.isEmpty()) {
            System.out.println("No appointments found in the system.");
            return;
        }

        displayAppointments(allAppointments);
    }

    /**
     * Views appointments filtered by status
     * @param status Status to filter by
     */
    public void viewAppointmentsByStatus(String status) {
        ArrayList<Appointment> allAppointments = new ArrayList<>();

        for (Doctor doctor : doctorList) {
            if (doctor.getAvailability() != null) {
                allAppointments.addAll(doctor.getAvailability().stream()
                        .filter(appt -> appt.getStatus().equals(status))
                        .collect(Collectors.toList()));
            }
        }

        if (allAppointments.isEmpty()) {
            System.out.println("No appointments found with status: " + status);
            return;
        }

        displayAppointments(allAppointments);
    }

    /**
     * Views appointments statistics
     */
    public void viewAppointmentStatistics() {
        ArrayList<Appointment> allAppointments = new ArrayList<>();

        for (Doctor doctor : doctorList) {
            if (doctor.getAvailability() != null) {
                allAppointments.addAll(doctor.getAvailability().stream()
                        .filter(appt -> !appt.getStatus().equals(Appointment.Status.AVAILABLE.toString()))
                        .collect(Collectors.toList()));
            }
        }

        if (allAppointments.isEmpty()) {
            System.out.println("No appointment data available for statistics.");
            return;
        }

        long totalAppointments = allAppointments.size();
        long pendingAppointments = allAppointments.stream()
                .filter(appt -> appt.getStatus().equals(Appointment.Status.PENDING.toString()))
                .count();
        long confirmedAppointments = allAppointments.stream()
                .filter(appt -> appt.getStatus().equals(Appointment.Status.CONFIRMED.toString()))
                .count();
        long completedAppointments = allAppointments.stream()
                .filter(appt -> appt.getStatus().equals(Appointment.Status.COMPLETED.toString()))
                .count();
        long cancelledAppointments = allAppointments.stream()
                .filter(appt -> appt.getStatus().equals(Appointment.Status.CANCELLED.toString()))
                .count();

        System.out.println("\nAppointment Statistics:");
        System.out.println("----------------------------------------");
        System.out.println("Total Appointments: " + totalAppointments);
        System.out.println("Pending Appointments: " + pendingAppointments);
        System.out.println("Confirmed Appointments: " + confirmedAppointments);
        System.out.println("Completed Appointments: " + completedAppointments);
        System.out.println("Cancelled Appointments: " + cancelledAppointments);
    }

    /**
     * Helper method to display appointments in a formatted manner
     * @param appointments List of appointments to display
     */
    private void displayAppointments(ArrayList<Appointment> appointments) {
        System.out.println("\nAppointment Details:");
        System.out.println("----------------------------------------");
        System.out.println("ID\tDoctor\t\tPatient\t\tDate\t\tTime\t\tStatus");
        System.out.println("----------------------------------------");

        appointments.forEach(appt -> {
            // Skip AVAILABLE appointments as they don't have patient information
            if (!appt.getStatus().equals(Appointment.Status.AVAILABLE.toString())) {
                System.out.printf("%s\t%s\t%s\t%s\t%s\t%s%n",
                        appt.getId(),
                        padString(appt.getDoctor().getName(), 15),
                        padString(appt.getPatient().getName(), 15),
                        appt.getDate(),
                        appt.getTime(),
                        appt.getStatus());

                // If appointment is completed, show medications
                if (appt.getStatus().equals(Appointment.Status.COMPLETED.toString()) &&
                        !appt.getMedications().isEmpty()) {
                    System.out.println("\tPrescribed Medications:");
                    appt.getMedications().forEach(med ->
                            System.out.printf("\t- %s: %d units%n",
                                    med.getName(),
                                    med.getAmount())
                    );
                }
            }
        });
    }

    // ===================== INVENTORY MANAGEMENT METHODS =====================

    /**
     * Views all medication inventory
     */
    public void viewMedicationInventory() {
        if (medicationInventory.getMedications().isEmpty()) {
            System.out.println("No medications found in inventory.");
            return;
        }

        System.out.println("\nMedication Inventory:");
        System.out.println("----------------------------------------");
        System.out.println("Name\t\tStock\tReplenishment Request");
        System.out.println("----------------------------------------");

        medicationInventory.getMedications().forEach(stock ->
                System.out.printf("%s\t%d\t%d%n",
                        padString(stock.getMedication().getName(), 15),
                        stock.getMedication().getAmount(),
                        stock.getReplenishmentRequest())
        );
    }

    /**
     * Updates stock level for a medication
     * @param medicationName Name of medication
     * @param newAmount New stock amount
     * @return true if update successful, false otherwise
     */
    public boolean updateMedicationStock(String medicationName, int newAmount) {
        if (newAmount < 0) {
            System.out.println("Error: Stock amount cannot be negative.");
            return false;
        }

        MedicationStock stock = findMedicationStock(medicationName);
        if (stock == null) {
            System.out.println("Error: Medication not found.");
            return false;
        }

        stock.getMedication().setAmount(newAmount);
        return true;
    }

    /**
     * Approves a replenishment request
     * @param medicationName Name of medication
     * @param approvedAmount Amount to approve
     * @return true if approval successful, false otherwise
     */
    public boolean approveReplenishmentRequest(String medicationName, int approvedAmount) {
        MedicationStock stock = findMedicationStock(medicationName);
        if (stock == null) {
            System.out.println("Error: Medication not found.");
            return false;
        }

        if (stock.getReplenishmentRequest() == 0) {
            System.out.println("Error: No pending replenishment request.");
            return false;
        }

        if (approvedAmount <= 0 || approvedAmount > stock.getReplenishmentRequest()) {
            System.out.println("Error: Invalid approved amount.");
            return false;
        }

        int currentAmount = stock.getMedication().getAmount();
        stock.getMedication().setAmount(currentAmount + approvedAmount);
        stock.setReplenishmentRequest(0);
        return true;
    }

    // ===================== HELPER METHODS =====================

    /**
     * Validates staff input data
     * @param id Staff ID
     * @param name Staff name
     * @param gender Staff gender
     * @param age Staff age
     * @return true if input is valid, false otherwise
     */
    private boolean validateStaffInput(String id, String name, char gender, int age) {
        if (id == null || id.trim().isEmpty()) {
            System.out.println("Error: Invalid ID.");
            return false;
        }
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Error: Invalid name.");
            return false;
        }
        if (gender != 'M' && gender != 'F' && gender != 'O') {
            System.out.println("Error: Invalid gender. Use M, F, or O.");
            return false;
        }
        if (age < 18 || age > 100) {
            System.out.println("Error: Invalid age. Must be between 18 and 100.");
            return false;
        }
        return true;
    }

    /**
     * Helper method to pad strings for consistent display formatting
     * @param str String to pad
     * @param length Desired length
     * @return Padded string
     */
    private String padString(String str, int length) {
        if (str.length() >= length) {
            return str;
        }
        return String.format("%-" + length + "s", str);
    }

    /**
     * Helper method to find medication stock by name
     * @param name Medication name
     * @return MedicationStock if found, null otherwise
     */
    private MedicationStock findMedicationStock(String name) {
        return medicationInventory.getMedications().stream()
                .filter(stock -> stock.getMedication().getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}