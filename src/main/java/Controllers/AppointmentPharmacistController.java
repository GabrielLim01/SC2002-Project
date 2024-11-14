package Controllers;

import Models.*;
import Utility.*;

import java.util.Scanner;
import java.util.ArrayList;

public class AppointmentPharmacistController extends AppointmentController {
    protected ArrayList<Doctor> doctors;

    // default constructor
    public AppointmentPharmacistController(ArrayList<Doctor> doctors) {
        this.doctors = doctors;
    }

    Scanner scanner = new Scanner(System.in);

    private Appointment selectAppointment() {
        Doctor doctor;
        ArrayList<Appointment> appointmentList;

        System.out.println("Select the doctor whose appointment you want to view.\n");
        for (int i=0;i<doctors.size();i++) {
            System.out.println(
                i + ": " + doctors.get(i).getName()
            );
        }

        try {
            doctor = doctors.get(scanner.nextInt());
        } catch (Exception e) {
            System.out.println("Doctor not found! Please try again.");
            throw e;
        }

        if (doctor.getAvailability() != null) {
            appointmentList = doctor.getAvailability();
        } else {
            System.out.println("Doctor " + doctor.getName() + " is not available.");
            throw new IllegalArgumentException("error");
        }

        System.out.println("Select the appointment you want to view.\n");
        System.out.println(appointmentList.size());
        for (int i=0;i<appointmentList.size();i++) {
            System.out.println(
                i +  
                ": Appointment on " + appointmentList.get(i).getDate() + 
                " at " + appointmentList.get(i).getTime()
            );
        }
        try {
            return appointmentList.get(scanner.nextInt());
        } catch (Exception e) {
            System.out.println("Appointment not found! Please try again.");
            throw e;
        }
    }

    public void viewApptOutcomeRec() {
        Appointment appointment;
        while (true) {
            try {
                appointment = selectAppointment();
                break;
            } catch (Exception e) {
                continue;
            }
        }
        printAppointment(appointment);

    }

    public void updatePrescriptionStatus() {
        Appointment appointment;
        String input = "";
        boolean isValidSelectionType = false;
        Validator validator = new Validator();
        final int MAX_MENU_RANGE = 2;

        while (true) {
            try {
                appointment = selectAppointment();
                break;
            } catch (Exception e) {
                continue;
            }
        }
        
        // clear previous input
        scanner.nextLine();

        while (!isValidSelectionType) {
            System.out.println("Select Status: 1-Dispensed 2-Completed");
            input = scanner.nextLine();
            System.out.println(input);
            isValidSelectionType = validator.validateSelectorInput(input, 1, MAX_MENU_RANGE);
        } 

        switch (Integer.parseInt(input)) {
            case 1:
                appointment.setStatus("DISPENSED");
                break;
            case 2:
                appointment.setStatus("COMPLETED");
                break;
        }

        System.out.println("Update Successful.");
    }
}
