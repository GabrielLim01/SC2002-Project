package Controllers;

import Models.*;
import Utility.*;

import java.util.Scanner;
import java.util.ArrayList;

public class AppointmentDoctorController {

    // default constructor
    public AppointmentDoctorController() {
    }

    Scanner scanner = new Scanner(System.in);
    Validator validator = new Validator();

    public void handleApptRequests(Doctor doctor) {

        // variables for data processing and validation
        String input = "";
        int selector = 0;
        final int MAX_MENU_RANGE = 3;
        boolean isValidSelectionType = true;
        int numberOfNonPendingAppts = 0;    // this is only used to display a "You have no pending appointments message", and there is probably a better way to check for that

        ArrayList<Appointment> appointmentList = new ArrayList<Appointment>();

        if (doctor.getAvailability() != null) {
            appointmentList = doctor.getAvailability();
        } else {
            System.out.println("Doctor " + doctor.getName() + " is not available.");
        }

        // The current implementation of the appointment approval/rejection system is such that the program will iterate over
        // every appointment whose status is PENDING, in other words, the doctor has to approve/reject things one-by-one, and in a fixed order
        // If I have some time later, I can consider changing the implementation of this to be a list of pending slots to choose from,
        // and the doctor can simply input their selected index of the timeslot to choose it and immediately approve/reject it rather than go through all of them one-by-one
        // (actually I can do that now but eh, lazy)
        for (int i = 0; i < appointmentList.size(); i++) {
            if (appointmentList.get(i).getStatus().equals(Appointment.Status.PENDING.toString())) {
                System.out.println("\nAppointment ID: " + appointmentList.get(i).getId());
                System.out.println("Timeslot: " + appointmentList.get(i).getDate() + " " + appointmentList.get(i).getTime());
                System.out.println("Patient: " + appointmentList.get(i).getPatient().getName());

                do {
                    do {
                        System.out.println("\nWould you like to approve the above patient's appointment booking request?");
                        System.out.println("1. Approve");
                        System.out.println("2. Reject");
                        System.out.println("3. Exit");
                        input = scanner.nextLine();
                        isValidSelectionType = validator.validateSelectorInput(input, 1, MAX_MENU_RANGE);
                    } while (!isValidSelectionType);

                    selector = Integer.parseInt(input);
                    switch (selector) {
                        case 1: {
                            // there are actually two instances of appointment slots to set to CONFIRMED here
                            // one of them is in the doctor's own Availability list
                            // the other is in the patient's own Appointments list
                            // here, we set the doctor's side to CONFIRMED first
                            appointmentList.get(i).setStatus(Appointment.Status.CONFIRMED.toString());
                            System.out.println("DEBUGGING - DOCTOR APPT STATUS - " + appointmentList.get(i).getStatus());

                            // in order to find the patient's side of the corresponding appointment, we need to iterate over all their appointments (which is limited by the MAX_APPOINTMENT_BOOKINGS attribute in the Patient class)
                            // it's hardcoded to be 1 for now (as of Oct 29, 2024), but can be bigger like 2 or 3 in the future
                            // (Oct 29, 2024 further update) hardcoded value for bookings changed to 2
                            ArrayList<Appointment> patientAppointments = appointmentList.get(i).getPatient().getAppointments();
                            for (int j = 0; j < patientAppointments.size(); j++) {

                                // the Appointment ID is used here as the foreign key to match the doctor's side and the patient's side of the "same" appointment slot
                                // remember, there are actually two separate instances of the "same" appointment slot, saved to different lists - one for the patient's side and one for the doctor's side
                                // ideally, I would have a pointer for both sides pointing to the same appointment slot object (so just need to keep track of 1 object overall), but idk how to achieve that properly in here for now
                                if (appointmentList.get(i).getId().equals(patientAppointments.get(j).getId()) &&
                                        // additional check for CANCELLED status (because the user is able to re-book the same appointment slot)
                                        // iteration will skip over same ID but already marked as cancelled
                                        !patientAppointments.get(j).getStatus().equals(Appointment.Status.CANCELLED.toString())) {
                                    patientAppointments.get(j).setStatus(Appointment.Status.CONFIRMED.toString());
                                    System.out.println("DEBUGGING - PATIENT APPT STATUS - " + patientAppointments.get(i).getStatus());
                                }
                            }
                            // Update the two lists
                            doctor.setAvailability(appointmentList);
                            appointmentList.get(i).getPatient().setAppointments(patientAppointments);

                            // DEBUGGING
                            System.out.println("\nDEBUGGING - Doctor's appointments and statuses");
                            appointmentList.forEach(s -> System.out.println(s.getId() + " " + s.getStatus()));
                            System.out.println("\nDEBUGGING - Patient's appointments and statuses");
                            appointmentList.get(i).getPatient().getAppointments().forEach(s -> System.out.println(s.getId() + " " + s.getStatus()));

                            System.out.println("Appointment has been confirmed!");
                            break;
                        }
                        case 2: {
                            // set the appointment slot in the doctor's availability list back to AVAILABLE
                            appointmentList.get(i).setStatus(Appointment.Status.AVAILABLE.toString());
                            System.out.println("DEBUGGING - DOCTOR APPT STATUS - " + appointmentList.get(i).getStatus());

                            ArrayList<Appointment> patientAppointments = appointmentList.get(i).getPatient().getAppointments();

                            // set the appointment slot in the patient's appointments booking list to REJECTED
                            // same iteration logic as above
                            for (int j = 0; j < patientAppointments.size(); j++) {
                                if (appointmentList.get(i).getId().equals(patientAppointments.get(j).getId())) {
                                    patientAppointments.get(j).setStatus(Appointment.Status.CANCELLED.toString());
                                    System.out.println("DEBUGGING - PATIENT APPT STATUS - " + patientAppointments.get(i).getStatus());
                                }
                            }

                            // Update the two lists
                            doctor.setAvailability(appointmentList);
                            appointmentList.get(i).getPatient().setAppointments(patientAppointments);

                            // DEBUGGING
                            System.out.println("\nDEBUGGING - Doctor's appointments and statuses");
                            appointmentList.forEach(s -> System.out.println(s.getId() + " " + s.getStatus()));
                            System.out.println("\nDEBUGGING - Patient's appointments and statuses");
                            appointmentList.get(i).getPatient().getAppointments().forEach(s -> System.out.println(s.getId() + " " + s.getStatus()));

                            // Decrement the patient's current appointment bookings by 1 to allow the patient to book once more
                            appointmentList.get(i).getPatient().decrementCurrentAppointmentBookings();

                            System.out.println("Appointment has been rejected!");
                            break;
                        }
                        case 3: {
                            break;
                        }
                    }

                    // this is a bit lazy but I want to break out of this do while loop if the input is either 1 or 2
                    // surely there is a better way to implement this rather than hardcoding it this way, but whatever
                    if (selector == 1 || selector == 2) {
                        break;
                    }
                } while (selector != MAX_MENU_RANGE);
            } else {
                numberOfNonPendingAppts++;
            }
        }

        if (appointmentList.size() == numberOfNonPendingAppts) {
            System.out.println("You have no pending appointments to approve or reject!");
        }
    }
}
