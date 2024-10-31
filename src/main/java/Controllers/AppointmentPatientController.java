package Controllers;

import Models.*;
import Utility.DataProcessing;
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
    DataProcessing dp = new DataProcessing();

    // View which doctors' appointment slots are available
    public void viewAvailAppts(Patient patient, ArrayList<Doctor> doctors, ArrayList<Appointment> appointments) {

        // Don't need this method right now
        // ArrayList<Appointment> appointments = dp.updateAppointments(doctors);

        // DEBUGGING
        int count = 0;
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getStatus().equals(Appointment.Status.AVAILABLE.toString())) {
                count++;
            }
        }

        // DEBUGGING
        System.out.println("\nAVAILABLE APPOINTMENTS: " + count);

        // Should the following variables be local scope or global scope?
        String input = "";
        boolean isValidInput = true;

        // Check if the patient has booked the maximum number of appointments permitted already
        if (!(patient.getCurrentAppointmentBookings() == patient.getMaxAppointmentBookings())) {
            // DEPRECATED METHOD - NOT IN USE CURRENTLY
//        doctorController.viewPersonalSchedulesOfAllDoctors(doctors);
//        System.out.println("These are the available doctors and their timeslots. Would you like to book an appointment? (Y/N)");
            for (int i = 0; i < appointments.size(); i++) {
                if (appointments.get(i).getStatus().equals(Appointment.Status.AVAILABLE.toString())) {
                    System.out.println("Appointment ID: " + appointments.get(i).getId());
                    System.out.println("Doctor: " + appointments.get(i).getDoctor().getName());
                    System.out.println("Timeslot: " + appointments.get(i).getDate() + " " + appointments.get(i).getTime());
                    System.out.print("\n");
                }
            }

            System.out.println("These are the available appointment slots. Would you like to book an appointment? (Y/N)");

            do {
                input = scanner.nextLine().trim().toUpperCase();
                isValidInput = validator.validateCharacterInput(input);
            } while (!isValidInput);

            if (input.charAt(0) == 'Y') {
                scheduleAppt(patient, doctors);
            }
        } else {
            System.out.println("Sorry, you have reached the maximum number of appointment bookings available!");
        }
    }

    // Choose a doctor, date and available timeslot to schedule an appointment
    public void scheduleAppt(Patient patient, ArrayList<Doctor> doctors) {
        // Three approaches to booking
        // bookByDoctor: Pick the doctor first, then see all their available appointment slots
        // bookByDateAndTime: See which dates/timeslots are available (at least 1 doctor), only after that THEN pick a doctor
        // bookByFullView: See a list of ALL doctors and ALL their available appointment slots to book from
        // I will be implementing via bookByDoctor for now

        bookApptByDoctor(patient, doctors);
        //bookByDateAndTime();
        //bookApptByFullView();

        // By the way, in HealthHub the booking system is modelled differently, the doctor you get is random and there is only
        // one timeslot for each 15-min period released at 10pm? 12am? on the previous day
    }

    public void bookApptByDoctor(Patient patient, ArrayList<Doctor> doctors) {
        String input = "";
        int selector = 0;
        int maxDoctorsRange = (doctors.size() + 1); // needs to be +1 for the output
        int maxAppointmentsRange;
        boolean isValidSelectionType = true;
        boolean isConflictingTimeslot = false;

        do {
            do {
                // Printed variables needs to be +1 for the output to display starting from 1 and not 0
                System.out.println("\nPlease select a doctor for the appointment:");
                for (int i = 0; i < doctors.size(); i++) {
                    System.out.println((i + 1) + ". " + doctors.get(i).getName());
                }
                System.out.println((doctors.size() + 1) + ". Exit");
                input = scanner.nextLine();
                isValidSelectionType = validator.validateSelectorInput(input, 1, maxDoctorsRange);
            } while (!isValidSelectionType);

            selector = (Integer.parseInt(input) - 1); // remove the additional 1

            // ignore the following code if the user selects the option to Exit or go Back
            // also needs to remove the additional 1 to prevent throwing of exceptions
            if (selector != (maxDoctorsRange - 1)) {
                // Pick a doctor first
                Doctor doctor = doctors.get(selector);
                ArrayList<Appointment> appointments = doctor.getAvailability();
                System.out.println("\nDoctor " + doctor.getName() + " is available at the following timeslots:");
                maxAppointmentsRange = (appointments.size() + 1);

                do {
                    do {
                        System.out.println();
                        for (int i = 0; i < appointments.size(); i++) {
                            // System.out.println("Appointment ID: " + appointments.get(i).getId());
                            if (appointments.get(i).getStatus().equals(Appointment.Status.AVAILABLE.toString())) {
                                System.out.println((i + 1) + ". " + appointments.get(i).getDate() + " " + appointments.get(i).getTime());
                            }
                            // problem here - for loop incremental index is not going to be consistent with existing appointment slots' indexes...
                            // ...once some slots start getting booked, since we are not removing/adding appt slots from the list when they
                            // are reserved, but merely setting them to some flag other than AVAILABLE in order to "hide" their existence
                        }
                        System.out.println((appointments.size() + 1) + ". Back");
                        System.out.println("\nPlease select your preferred timeslot for the appointment:");
                        input = scanner.nextLine();
                        isValidSelectionType = validator.validateSelectorInput(input, 1, maxAppointmentsRange);
                    } while (!isValidSelectionType);

                    selector = (Integer.parseInt(input) - 1);

                    // again, same logic as maxDoctorsRange, ignore the following code if user changes their mind...
                    // ...about booking an appointment
                    // is it possible to abstract this entire chunk of nested do-do-while segment of code for reusability?
                    // (yes it is possible but I don't want to bother with it now)
                    if (selector != (maxAppointmentsRange - 1)) {

                        // Check if the user tries to pick an "invisible" index
                        // Very weak form of validation, maybe should be a try/catch block with more specific validation cases idk
                        // (Or I should just rewrite my code for the "invisible index" option to not even be a possibility...)
                        if (appointments.get(selector).getStatus().equals(Appointment.Status.AVAILABLE.toString())) {

                            // Check if the timeslot chosen conflicts with any of the patient's existing appointment slots
                            for (int i = 0; i < patient.getAppointments().size(); i++) {
                                if (appointments.get(selector).getDate().equals(patient.getAppointments().get(i).getDate()) &&
                                        appointments.get(selector).getTime().equals(patient.getAppointments().get(i).getTime()) &&
                                        !patient.getAppointments().get(i).getStatus().equals(Appointment.Status.CANCELLED.toString())) {
                                    isConflictingTimeslot = true;
                                }
                            }

                            if (!isConflictingTimeslot) {
                                // set status of chosen appointment to PENDING
                                appointments.get(selector).setStatus(Appointment.Status.PENDING.toString());
                                appointments.get(selector).setPatient(patient);

                                // the following lines of code require more validation but I'm too lazy to write the validation for them
                                // supposed to check that all these variables and methods exist / not null and nothing is missing
                                // I am assuming they all exist here, so basically zero validation done
                                ArrayList<Appointment> temp = patient.getAppointments();
                                System.out.println("DEBUGGING - PATIENT's CURRENT APPOINTMENTS BEFORE UPDATE");
                                temp.forEach(s -> System.out.println(s.getId() + " " + s.getStatus()));

                                // when I "add" this object into the temp list, am I really creating a new object or simply adding the reference to it?
                                // this distinction is very important for later code logic, because if I am adding the reference, I will have only 1 object but 2 references to it, and I want 2 of the same object instead
                                // temp.add(appointments.get(selector));

                                // I believe this is the correct way to do what I want...? I need to create a new object instead of passing a reference
                                temp.add(new Appointment(appointments.get(selector).getId(), patient,
                                        appointments.get(selector).getDoctor(), appointments.get(selector).getDate(),
                                        appointments.get(selector).getTime(), Appointment.Status.PENDING.toString()));

                                patient.setAppointments(temp);
                                System.out.println("DEBUGGING - PATIENT's CURRENT APPOINTMENTS AFTER UPDATE");
                                patient.getAppointments().forEach(s -> System.out.println(s.getId() + " " + s.getStatus()));

                                // increment the number of bookings the patient has by 1
                                patient.incrementCurrentAppointmentBookings();

                                System.out.println("Appointment successfully booked! Please wait for the doctor to review your request.");
                                return;
                            } else {
                                System.out.println("The timeslot you have selected conflicts with one of your existing appointment slots.\nPlease select another timeslot.");
                                isConflictingTimeslot = false;  // set it back to false after the logic is run
                            }
                        } else {
                            System.out.println("That appointment slot is not available for booking. Please select another timeslot.");
                        }
                    }
                } while (selector != (maxAppointmentsRange - 1));
            }
        } while (selector != (maxDoctorsRange - 1));
    }

    // Change an existing appointment's date and/or timeslot
    // Must not have conflicts
    // Slot availability will be updated automatically
    public void rescheduleAppt(Patient patient, ArrayList<Doctor> doctors) {
        String input = "";
        int selector = 0;
        boolean isValidSelectionType = true;
        boolean updateReschedulableAppointments; // every time a rescheduling is successful. this boolean value will be set to true and trigger an updating of the reschedulableAppointments list

        ArrayList<Appointment> patientAppointments = patient.getAppointments();
        ArrayList<Appointment> reschedulableAppointments = new ArrayList<Appointment>();

        do {
            updateReschedulableAppointments = false;

            for (int i = 0; i < patientAppointments.size(); i++) {
                if (patientAppointments.get(i).getStatus().equals(Appointment.Status.PENDING.toString()) ||
                        patientAppointments.get(i).getStatus().equals(Appointment.Status.CONFIRMED.toString())
                ) {
                    // For the adding of items to the reschedulableAppointments list,
                    // I am adding an object reference, not a new object, so any operations done on reschedulableAppointments
                    // will automatically update the patientAppointments's "corresponding" appointment later too (rather, they are just pointers to the same object)

                    // Adding of items use cases:
                    // Case 1: Check whether list is new/empty, allow unrestricted adding of new elements if so
                    if (reschedulableAppointments.isEmpty()) {
                        reschedulableAppointments.add(patientAppointments.get(i));
                        // System.out.println("DEBUGGING - Adding " + patientAppointments.get(i).getDate() + " " + patientAppointments.get(i).getTime());
                    } else {
                        // Case 2: Check for duplicate entries and do not add duplicate entries into the list
                        for (int j = 0; j < reschedulableAppointments.size(); j++) {
                            if (!reschedulableAppointments.get(j).getId().equals(patientAppointments.get(i).getId())) {
                                reschedulableAppointments.add(patientAppointments.get(i));
                                // System.out.println("DEBUGGING - Adding " + patientAppointments.get(i).getDate() + " " + patientAppointments.get(i).getTime());

                            }
                        }
                    }
                }
            }

            if (!reschedulableAppointments.isEmpty()) {

                int maxAppointmentsRange = (reschedulableAppointments.size() + 1);

                do {
                    System.out.println("\nWhich appointment would you like to reschedule?");
                    for (int i = 0; i < reschedulableAppointments.size(); i++) {
                        System.out.println((i + 1) + ". " + reschedulableAppointments.get(i).getDate() + " " + reschedulableAppointments.get(i).getTime());
                    }
                    System.out.println((reschedulableAppointments.size() + 1) + ". Back");

                    do {
                        input = scanner.nextLine();
                        isValidSelectionType = validator.validateSelectorInput(input, 1, maxAppointmentsRange);
                    } while (!isValidSelectionType);

                    selector = (Integer.parseInt(input) - 1);

                    if (selector != (maxAppointmentsRange - 1)) {
                        System.out.println("\nPlease select a new timeslot for your appointment!");

                        // I need to clone and modify this method - scheduleAppt() - to return a boolean to tell me if the booking is successful or not
                        // There is only one case where it will end in an error and that is if the user tries to input an "invisible" index, like inputting 1. when 1. is not a "visible" option
                        // Ideally, I should handle that error case within the scheduleAppt() method itself, but I foresee it being a pain so I am not doing that for now
                        // For the time being, I will just assume that the booking done via this method is always successful (i.e. no validation done for now, which is dangerous)

                        // boolean isSuccessful = scheduleAppt(patient, doctors);
                        // TRUE if no error
                        // FALSE if error, or user changed their mind about rescheduling
                        // Error cases:
                        // 1. User inputs an "invisible" index (which is an error case I did not bother to fix in the original method)
                        // 2. User inputs the option to go "Back", which means the rest of the code should not execute

                        // UPDATE - cloned/modified the method to return a boolean
                        boolean isSuccessful = scheduleApptWithBoolean(patient, doctors);

                        // scheduleAppt automatically increments the user's booking try at the end of the method
                        // Because this is a reschedule operation, refund the user's booking try
                        if (isSuccessful) {
                            patient.decrementCurrentAppointmentBookings();

                            // Set the status of the appointment being rescheduled to AVAILABLE in the doctor's appointment list,
                            // and remove it from the patient's list
                            ArrayList<Appointment> doctorAppointments = reschedulableAppointments.get(selector).getDoctor().getAvailability();
                            for (int i = 0; i < doctorAppointments.size(); i++) {
                                if (doctorAppointments.get(i).getId().equals(reschedulableAppointments.get(selector).getId())) {
                                    doctorAppointments.get(i).setStatus(Appointment.Status.AVAILABLE.toString());
                                    patientAppointments.remove(reschedulableAppointments.get(selector));
                                }
                            }

                            // DEBUGGING
                            System.out.println("\nDEBUGGING - Doctor's appointments and statuses");
                            doctorAppointments.forEach(s -> System.out.println(s.getId() + " " + s.getStatus()));
                            System.out.println("\nDEBUGGING - Patient's appointments and statuses");
                            patientAppointments.forEach(s -> System.out.println(s.getId() + " " + s.getStatus()));

                            // Also remove the rescheduled appointment from the locally-scoped reschedulableAppointments list
                            reschedulableAppointments.remove(reschedulableAppointments.get(selector));

                            // Now here comes the tricky part
                            // I need to somehow update rescheduleAppts from within this do/while loop
                            // Or I can also 'return' and totally exit this method, but what if the user has more than one appointment to reschedule...?
                            // Let's try this
                            updateReschedulableAppointments = true;
//                            System.out.println("\nDEBUGGING - After successful rescheduling");
//                            System.out.println("DEBUGGING - updateReschedulableAppointments status: " + updateReschedulableAppointments);

                        } else {
                            System.out.println("Rescheduling was not completed.");

                            // There should actually be two custom messages to handle the two different scenarios of rescheduling failure as mentioned above
                            // 1. - Rescheduling failed due to error ^that default message above
                            // 2. - Rescheduling "failed" due to user backing out - "Changed your mind, eh?"

                            // If I want to handle this additional custom message complexity, I need to return an int instead of a boolean, and the int will have 3 values
                            // 0 - successful
                            // 1 - unsuccessful due to error
                            // 2 - unsuccessful due to user inputting Back option
                            // (...would a String enum be better for this??)
                        }
                    }
//                    System.out.println("DEBUGGING - Right before breaking out of first/inner do-while loop");
//                    System.out.println("DEBUGGING - updateReschedulableAppointments status: " + updateReschedulableAppointments);
                } while (selector != (maxAppointmentsRange - 1) && !updateReschedulableAppointments);
            } else {
                System.out.println("There are no appointments available for you to reschedule.");
            }
//            System.out.println("DEBUGGING - Right before breaking out of second/outer do-while loop");
//            System.out.println("DEBUGGING - updateReschedulableAppointments status: " + updateReschedulableAppointments);
        } while (updateReschedulableAppointments);
    }

    // Cancel appointment, and update slot availability automatically
    public void cancelAppt(Patient patient) {
        String input = "";
        int selector = 0;
        boolean isValidSelectionType = true;
        boolean updateCancellableAppointments; // every time a cancellation is successful. this boolean value will be set to true and trigger an updating of the cancellableAppointments list

        ArrayList<Appointment> patientAppointments = patient.getAppointments();
        ArrayList<Appointment> cancellableAppointments = new ArrayList<Appointment>();

        do {
            updateCancellableAppointments = false;

            for (int i = 0; i < patientAppointments.size(); i++) {
                if (patientAppointments.get(i).getStatus().equals(Appointment.Status.PENDING.toString()) ||
                        patientAppointments.get(i).getStatus().equals(Appointment.Status.CONFIRMED.toString())
                ) {
                    // Adding of items use cases:
                    // Case 1: Check whether list is new/empty, allow unrestricted adding of new elements if so
                    if (cancellableAppointments.isEmpty()) {
                        cancellableAppointments.add(patientAppointments.get(i));
                        // System.out.println("DEBUGGING - Adding " + patientAppointments.get(i).getDate() + " " + patientAppointments.get(i).getTime());
                    } else {
                        // Case 2: Check for duplicate entries and do not add duplicate entries into the list
                        for (int j = 0; j < cancellableAppointments.size(); j++) {
                            if (!cancellableAppointments.get(j).getId().equals(patientAppointments.get(i).getId())) {
                                cancellableAppointments.add(patientAppointments.get(i));
                                // System.out.println("DEBUGGING - Adding " + patientAppointments.get(i).getDate() + " " + patientAppointments.get(i).getTime());

                            }
                        }
                    }
                }
            }

            if (!cancellableAppointments.isEmpty()) {

                int maxAppointmentsRange = (cancellableAppointments.size() + 1);

                do {
                    System.out.println("\nWhich appointment would you like to cancel?");
                    for (int i = 0; i < cancellableAppointments.size(); i++) {
                        System.out.println((i + 1) + ". " + cancellableAppointments.get(i).getDate() + " " + cancellableAppointments.get(i).getTime());
                    }
                    System.out.println((cancellableAppointments.size() + 1) + ". Back");

                    do {
                        input = scanner.nextLine();
                        isValidSelectionType = validator.validateSelectorInput(input, 1, maxAppointmentsRange);
                    } while (!isValidSelectionType);

                    selector = (Integer.parseInt(input) - 1);

                    if (selector != (maxAppointmentsRange - 1)) {

                        patient.decrementCurrentAppointmentBookings();

                        ArrayList<Appointment> doctorAppointments = cancellableAppointments.get(selector).getDoctor().getAvailability();
                        for (int i = 0; i < doctorAppointments.size(); i++) {
                            if (doctorAppointments.get(i).getId().equals(cancellableAppointments.get(selector).getId())) {
                                doctorAppointments.get(i).setStatus(Appointment.Status.AVAILABLE.toString());
                                patientAppointments.remove(cancellableAppointments.get(selector));
                            }
                        }

                        // DEBUGGING
                        System.out.println("\nDEBUGGING - Doctor's appointments and statuses");
                        doctorAppointments.forEach(s -> System.out.println(s.getId() + " " + s.getStatus()));
                        System.out.println("\nDEBUGGING - Patient's appointments and statuses");
                        patientAppointments.forEach(s -> System.out.println(s.getId() + " " + s.getStatus()));

                        System.out.println("\nAppointment successfully cancelled!");
                        cancellableAppointments.remove(cancellableAppointments.get(selector));
                        updateCancellableAppointments = true;
                    }
                } while (selector != (maxAppointmentsRange - 1) && !updateCancellableAppointments);
            } else {
                System.out.println("There are no appointments available for you to cancel.");
            }
        } while (updateCancellableAppointments);
    }

    // See status of scheduled appointments
    public void viewAppts(Patient patient) {
        if (!patient.getAppointments().isEmpty()){
            System.out.println("Your appointments are as follows:");
            patient.getAppointments().forEach(s -> System.out.println("\nDoctor: " + s.getDoctor().getName() + "\n" +
                                                                      "Timeslot: " + s.getDate() + " " + s.getTime() + "\n" +
                                                                      "Status: " + s.getStatus()));
        } else {
            System.out.println("You have no appointment history!");
        }
    }

    // View past appointment outcome records
    public void viewApptOutcomeRec(Patient patient) {
        System.out.println("この機能はまだ完成したみたい、ごめんなさいね");
    }

// ADDITIONAL METHODS NOT REQUIRED BY TEST CASES
// THESE METHODS ARE PRETTY MUCH COPIES OF EXISTING METHODS, WITH MINOR DIFFERENCES (e.g. returning a boolean value)

    public boolean scheduleApptWithBoolean(Patient patient, ArrayList<Doctor> doctors) {
        return bookApptByDoctorWithBoolean(patient, doctors);
    }

    public boolean bookApptByDoctorWithBoolean(Patient patient, ArrayList<Doctor> doctors) {
        // NEW CODE
        boolean isSuccessful = true;

        String input = "";
        int selector = 0;
        int maxDoctorsRange = (doctors.size() + 1); // needs to be +1 for the output
        int maxAppointmentsRange;
        boolean isValidSelectionType = true;
        boolean isConflictingTimeslot = false;

        do {
            do {
                // Printed variables needs to be +1 for the output to display starting from 1 and not 0
                System.out.println("\nPlease select a doctor for the appointment:");
                for (int i = 0; i < doctors.size(); i++) {
                    System.out.println((i + 1) + ". " + doctors.get(i).getName());
                }
                System.out.println((doctors.size() + 1) + ". Exit");
                input = scanner.nextLine();
                isValidSelectionType = validator.validateSelectorInput(input, 1, maxDoctorsRange);
            } while (!isValidSelectionType);

            selector = (Integer.parseInt(input) - 1);

            if (selector != (maxDoctorsRange - 1)) {
                Doctor doctor = doctors.get(selector);
                ArrayList<Appointment> appointments = doctor.getAvailability();
                System.out.println("\nDoctor " + doctor.getName() + " is available at the following timeslots:");
                maxAppointmentsRange = (appointments.size() + 1);

                do {
                    do {
                        System.out.println();
                        for (int i = 0; i < appointments.size(); i++) {
                            if (appointments.get(i).getStatus().equals(Appointment.Status.AVAILABLE.toString())) {
                                System.out.println((i + 1) + ". " + appointments.get(i).getDate() + " " + appointments.get(i).getTime());
                            }
                        }
                        System.out.println((appointments.size() + 1) + ". Back");
                        System.out.println("\nPlease select your preferred timeslot for the appointment:");
                        input = scanner.nextLine();
                        isValidSelectionType = validator.validateSelectorInput(input, 1, maxAppointmentsRange);
                    } while (!isValidSelectionType);

                    selector = (Integer.parseInt(input) - 1);

                    if (selector != (maxAppointmentsRange - 1)) {
                        if (appointments.get(selector).getStatus().equals(Appointment.Status.AVAILABLE.toString())) {

                            for (int i = 0; i < patient.getAppointments().size(); i++) {
                                if (appointments.get(selector).getDate().equals(patient.getAppointments().get(i).getDate()) &&
                                        appointments.get(selector).getTime().equals(patient.getAppointments().get(i).getTime()) &&
                                        !patient.getAppointments().get(i).getStatus().equals(Appointment.Status.CANCELLED.toString())) {
                                    isConflictingTimeslot = true;
                                }
                            }

                            if (!isConflictingTimeslot) {
                                appointments.get(selector).setStatus(Appointment.Status.PENDING.toString());
                                appointments.get(selector).setPatient(patient);

                                ArrayList<Appointment> temp = patient.getAppointments();
                                System.out.println("DEBUGGING - PATIENT's CURRENT APPOINTMENTS BEFORE UPDATE");
                                temp.forEach(s -> System.out.println(s.getId() + " " + s.getStatus()));

                                temp.add(new Appointment(appointments.get(selector).getId(), patient,
                                        appointments.get(selector).getDoctor(), appointments.get(selector).getDate(),
                                        appointments.get(selector).getTime(), Appointment.Status.PENDING.toString()));

                                patient.setAppointments(temp);
                                System.out.println("DEBUGGING - PATIENT's CURRENT APPOINTMENTS AFTER UPDATE");
                                patient.getAppointments().forEach(s -> System.out.println(s.getId() + " " + s.getStatus()));

                                patient.incrementCurrentAppointmentBookings();

                                System.out.println("Appointment successfully booked! Please wait for the doctor to review your request.");
                                return isSuccessful;   // NEW CODE
                            } else {
                                System.out.println("The timeslot you have selected conflicts with one of your existing appointment slots.\nPlease select another timeslot.");
                                isConflictingTimeslot = false;  // set it back to false after the logic is run
                            }
                        } else {
                            System.out.println("That appointment slot is not available for booking. Please select another timeslot.");
                        }
                    }
                } while (selector != (maxAppointmentsRange - 1));
            }
        } while (selector != (maxDoctorsRange - 1));

        // NEW CODE
        // // if this code executes, it means the user inputted the option to go Back
        return !isSuccessful;
    }
}
