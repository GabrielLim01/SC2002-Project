package Controllers;

import Models.*;
import Utility.Validator;

import java.util.ArrayList;
import java.util.Scanner;

public class AppointmentPatientController
{
    // default constructor
    public AppointmentPatientController() {
    }

    // Instantiated classes
    DoctorController doctorController = new DoctorController();
    Scanner scanner = new Scanner(System.in);
    Validator validator = new Validator();

    // View which doctors' appointment slots are available
    public void viewAvailAppts(Patient patient, ArrayList<Appointment> appointmentList){

        // Should the following variables be local scope or global scope?
        String input = "";
        boolean isValidInput = true;

//        doctorController.viewPersonalSchedulesOfAllDoctors(doctorList);
//        System.out.println("These are the available doctors and their timeslots. Would you like to book an appointment? (Y/N)");
        for (int i = 0; i < appointmentList.size(); i++) {
            System.out.println("Appointment ID: " + appointmentList.get(i).getId());
            System.out.println("Doctor: " + appointmentList.get(i).getDoctor().getName());
            System.out.println("Date and Time: " + appointmentList.get(i).getDate() + " " + appointmentList.get(i).getTime());
            System.out.print("\n");
        }

        System.out.println("These are the available appointment slots. Would you like to book an appointment? (Y/N)");

        do {
            input = scanner.nextLine().trim().toUpperCase();
            isValidInput = validator.validateCharacterInput(input);
        } while (!isValidInput);

        if (input.charAt(0) == 'Y'){
            scheduleAppt();  // INCOMPLETE
        }
    }

    // Choose a doctor, date and available timeslot to schedule an appointment
    public void scheduleAppt(){

    }

    // Change an existing appointment's date and/or timeslot
    // Must not have conflicts
    // Slot availability will be updated automatically
    public void rescheduleAppt(){

    }

    // Cancel appointment, and update slot availability automatically
    public void cancelAppt(){

    }

    // See status of schedule appointments
    public void viewApptStatus(){

    }

    // View past appointment outcome records
    public void viewApptOutcomeRec(){

    }
}
