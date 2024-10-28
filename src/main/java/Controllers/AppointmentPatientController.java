package Controllers;

import Models.*;
import Utility.Validator;

import java.util.ArrayList;
import java.util.Scanner;

public class AppointmentPatientController {
    // default constructor
    public AppointmentPatientController() {
    }

    // Instantiated classes
    DoctorController doctorController = new DoctorController();
    Scanner scanner = new Scanner(System.in);
    Validator validator = new Validator();

    // View which doctors' appointment slots are available
    public void viewAvailAppts(Patient patient, ArrayList<Doctor> doctorsList, ArrayList<Appointment> appointmentList) {

        // Should the following variables be local scope or global scope?
        String input = "";
        boolean isValidInput = true;

        // Check if the patient has booked the maximum number of appointments permitted already
        if (!(patient.getCurrentAppointmentBookings() == patient.getMaxAppointmentBookings())) {
//        doctorController.viewPersonalSchedulesOfAllDoctors(doctorList);
//        System.out.println("These are the available doctors and their timeslots. Would you like to book an appointment? (Y/N)");
            for (int i = 0; i < appointmentList.size(); i++) {
                if (appointmentList.get(i).getStatus().equals(Appointment.Status.AVAILABLE.toString())) {
                    System.out.println("Appointment ID: " + appointmentList.get(i).getId());
                    System.out.println("Doctor: " + appointmentList.get(i).getDoctor().getName());
                    System.out.println("Timeslot: " + appointmentList.get(i).getDate() + " " + appointmentList.get(i).getTime());
                    System.out.print("\n");
                }
            }

            System.out.println("These are the available appointment slots. Would you like to book an appointment? (Y/N)");

            do {
                input = scanner.nextLine().trim().toUpperCase();
                isValidInput = validator.validateCharacterInput(input);
            } while (!isValidInput);

            if (input.charAt(0) == 'Y') {
                scheduleAppt(patient, doctorsList);
            }
        } else {
            System.out.println("Sorry, you have reached the maximum number of appointment bookings available!");
        }
    }

    // Choose a doctor, date and available timeslot to schedule an appointment
    public void scheduleAppt(Patient patient, ArrayList<Doctor> doctorsList) {
        // Three approaches to booking
        // bookByDoctor: Pick the doctor first, then see all their available appointment slots
        // bookByDateAndTime: See which dates/timeslots are available (at least 1 doctor), only after that THEN pick a doctor
        // bookByFullView: See a list of ALL doctors and ALL their available appointment slots to book from
        // I will be implementing via bookByDoctor for now

        bookApptByDoctor(patient, doctorsList);
        //bookByDateAndTime();
        //bookApptByFullView();

        // By the way, in HealthHub the booking system is modelled differently, the doctor you get is random and there is only
        // one timeslot for each 15-min period released at 10pm? 12am? on the previous day
    }

    // see, just for this method to get doctorsList, I have to make its two parent methods, scheduleAppt and viewAvailAppts
    // take in doctorsList as parameters
    // would really like to avoid this way of passing data if possible but I'm not aware of any other way to do it for now
    public void bookApptByDoctor(Patient patient, ArrayList<Doctor> doctorsList) {
        // Pick a doctor first

        String input = "";
        int selector = 0;
        int maxDoctorsRange = (doctorsList.size() + 1); // needs to be +1 for the output
        int maxAppointmentsRange;
        boolean isValidSelectionType = true;

        do {
            do {
                // Printed variables needs to be +1 for the output to display starting from 1 and not 0
                System.out.println("\nPlease select a doctor for the appointment:");
                for (int i = 0; i < doctorsList.size(); i++) {
                    System.out.println((i + 1) + ". " + doctorsList.get(i).getName());
                }
                System.out.println((doctorsList.size() + 1) + ". Exit");
                input = scanner.nextLine();
                isValidSelectionType = validator.validateSelectorInput(input, 1, maxDoctorsRange);
            } while (!isValidSelectionType);

            selector = (Integer.parseInt(input) - 1); // remove the additional 1

            // ignore the following code if the user selects the option to Exit or go Back
            // also needs to remove the additional 1 to prevent throwing of exceptions
            if (selector != (maxDoctorsRange - 1)) {
                Doctor doctor = doctorsList.get(selector);
                ArrayList<Appointment> appointmentList = doctor.getAvailability();
                System.out.println("\nDoctor " + doctor.getName() + " is available at the following timeslots:");
                maxAppointmentsRange = (appointmentList.size() + 1);

                do {
                    do {
                        System.out.println();
                        for (int i = 0; i < appointmentList.size(); i++) {
                            // System.out.println("Appointment ID: " + appointmentList.get(i).getId());
                            if (appointmentList.get(i).getStatus().equals(Appointment.Status.AVAILABLE.toString())) {
                                System.out.println((i + 1) + ". " + appointmentList.get(i).getDate() + " " + appointmentList.get(i).getTime());
                            }
                            // problem here - for loop incremental index is not going to be consistent with existing appointment slots' indexes
                            // once some slots start getting booked, since we are not removing/adding appt slots from the list when they
                            // are reserved, but merely setting them to some flag other than AVAILABLE in order to "hide" their existence
                        }
                        System.out.println((appointmentList.size() + 1) + ". Back");
                        System.out.println("\nPlease select your preferred timeslot for the appointment:");
                        input = scanner.nextLine();
                        isValidSelectionType = validator.validateSelectorInput(input, 1, maxAppointmentsRange);
                    } while (!isValidSelectionType);

                    selector = (Integer.parseInt(input) - 1);

                    // again, same logic as maxDoctorsRange, ignore the following code if user changes their mind
                    // about booking an appointment
                    // is it possible to abstract this entire chunk of nested do-do-while segment of code for reusability?
                    // (yes it is possible but I don't want to bother with it now)
                    if (selector != (maxAppointmentsRange - 1)) {

                        // very weak form of validation, maybe should be a try/catch block with more specific validation cases idk
                        if (appointmentList.get(selector).getStatus().equals(Appointment.Status.AVAILABLE.toString())) {

                            // set status of chosen appointment to PENDING
                            appointmentList.get(selector).setStatus(Appointment.Status.PENDING.toString());

                            // the following lines of code require more validation but I'm too lazy to write the validation for them
                            // supposed to check that all these variables and methods exist / not null and nothing is missing
                            // I am assuming they all exist here, so basically zero validation done
                            ArrayList<Appointment> temp = patient.getAppointments();
                            temp.add(appointmentList.get(selector));
                            patient.setAppointments(temp);

                            // increment the number of bookings the patient has by 1
                            patient.setCurrentAppointmentBookings(patient.getCurrentAppointmentBookings() + 1);

                            System.out.println("Appointment successfully booked! Please wait for the doctor to review your request.");
                        } else {
                            System.out.println("Something went wrong. Appointment not successfully booked.");
                        }
                        return;
                    }

                } while (selector != (maxAppointmentsRange - 1));
            }
        } while (selector != (maxDoctorsRange - 1));

    }

    // Change an existing appointment's date and/or timeslot
    // Must not have conflicts
    // Slot availability will be updated automatically
    public void rescheduleAppt() {

    }

    // Cancel appointment, and update slot availability automatically
    public void cancelAppt() {

    }

    // See status of schedule appointments
    public void viewApptStatus() {

    }

    // View past appointment outcome records
    public void viewApptOutcomeRec() {

    }
}
