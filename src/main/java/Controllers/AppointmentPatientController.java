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
    public void viewAvailAppts(Patient patient, ArrayList<Doctor> doctorsList, ArrayList<Appointment> appointmentList){

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
            scheduleAppt(doctorsList);  // INCOMPLETE
        }
    }

    // Choose a doctor, date and available timeslot to schedule an appointment
    public void scheduleAppt(ArrayList<Doctor> doctorsList){
        // Three approaches to booking
        // bookByDoctor: Pick the doctor first, then see all their available appointment slots
        // bookByDateAndTime: See which dates/timeslots are available (at least 1 doctor), only after that THEN pick a doctor
        // bookByFullView: See a list of ALL doctors and ALL their available appointment slots to book from
        // I will be implementing via bookByDoctor for now
        bookApptByDoctor(doctorsList);
        //bookByDateAndTime();
        //bookApptByFullView();

        // By the way, in HealthHub the booking system is modelled differently, the doctor you get is random and there is only
        // one timeslot for each 15-min period released at 10pm? 12am? on the previous day
    }

    // see, just for this method to get doctorsList, I have to make its two parent methods, scheduleAppt and viewAvailAppts
    // take in doctorsList as parameters
    // would really like to avoid this way of passing data if possible but I'm not aware of any other way to do it for now
    public void bookApptByDoctor(ArrayList<Doctor> doctorsList){
        // Pick a doctor first
        for (int i=0; i< doctorsList.size(); i++){
            // WIP
        }
    }

    public void bookApptByFullView(){

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
