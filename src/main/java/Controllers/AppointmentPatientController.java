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
    public void viewAvailAppts(Patient patient, ArrayList<Doctor> doctorsList, ArrayList<Appointment> appointmentList) {

        // Don't need this method right now
        // ArrayList<Appointment> appointmentList = dp.updateAppointmentsList(doctorsList);

        // DEBUGGING
        int count = 0;
        for (int i = 0; i < appointmentList.size(); i++) {
            if (appointmentList.get(i).getStatus().equals(Appointment.Status.AVAILABLE.toString())) {
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
                            // problem here - for loop incremental index is not going to be consistent with existing appointment slots' indexes...
                            // ...once some slots start getting booked, since we are not removing/adding appt slots from the list when they
                            // are reserved, but merely setting them to some flag other than AVAILABLE in order to "hide" their existence
                        }
                        System.out.println((appointmentList.size() + 1) + ". Back");
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

                        // very weak form of validation, maybe should be a try/catch block with more specific validation cases idk
                        if (appointmentList.get(selector).getStatus().equals(Appointment.Status.AVAILABLE.toString())) {

                            // set status of chosen appointment to PENDING
                            appointmentList.get(selector).setStatus(Appointment.Status.PENDING.toString());
                            appointmentList.get(selector).setPatient(patient);

                            // the following lines of code require more validation but I'm too lazy to write the validation for them
                            // supposed to check that all these variables and methods exist / not null and nothing is missing
                            // I am assuming they all exist here, so basically zero validation done
                            ArrayList<Appointment> temp = patient.getAppointments();
                            System.out.println("DEBUGGING - PATIENT's CURRENT APPOINTMENTS BEFORE UPDATE");
                            temp.forEach(s -> System.out.println(s.getId() + " " + s.getStatus()));

                            // when I "add" this object into the temp list, am I really creating a new object or simply adding the reference to it?
                            // this distinction is very important for later code logic, because if I am adding the reference, I will have only 1 object but 2 references to it, and I want 2 of the same object instead
                            // temp.add(appointmentList.get(selector));

                            // I believe this is the correct way to do what I want...? I need to create a new object instead of passing a reference
                            temp.add(new Appointment(appointmentList.get(selector).getId(), patient,
                                    appointmentList.get(selector).getDoctor(), appointmentList.get(selector).getDate(),
                                    appointmentList.get(selector).getTime(), Appointment.Status.PENDING.toString()));

                            patient.setAppointments(temp);
                            System.out.println("DEBUGGING - PATIENT's CURRENT APPOINTMENTS AFTER UPDATE");
                            patient.getAppointments().forEach(s -> System.out.println(s.getId() + " " + s.getStatus()));

                            // increment the number of bookings the patient has by 1
                            patient.incrementCurrentAppointmentBookings();

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
    public void rescheduleAppt(Patient patient, ArrayList<Doctor> doctorList) {
        String input = "";
        int selector = 0;
        boolean isValidSelectionType = true;

        ArrayList<Appointment> patientAppointments = patient.getAppointments();
        ArrayList<Appointment> reschedulableAppointments = new ArrayList<Appointment>();

        for (int i = 0; i < patientAppointments.size(); i++) {
            if (patientAppointments.get(i).getStatus().equals(Appointment.Status.PENDING.toString()) ||
                    patientAppointments.get(i).getStatus().equals(Appointment.Status.CONFIRMED.toString())
            ) {
                // I am adding an object reference here, not a new object, so any operations done on reschedulableAppointments
                // will automatically update the patientAppointments's "corresponding" appointment later too (rather, they are the same object)
                reschedulableAppointments.add(patientAppointments.get(i));
            }
        }

        if (!reschedulableAppointments.isEmpty()){

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

                    // I need to overload this method to return a boolean to tell me if the booking is successful or not
                    // There is only one case where it will end in an error and that is if the user tries to input an "invisible" index, like inputting 1. when 1. is not a "visible" option
                    // Ideally, I should handle that error case within the scheduleAppt() method itself, but I foresee it being a pain so I am not doing that for now
                    // For the time being, I will just assume that the booking done via this method is always successful (i.e. no validation done for now, which is dangerous)
                    scheduleAppt(patient, doctorList);

                    // boolean isSuccessful = scheduleAppt(patient, doctorList);
                    // TRUE if no error
                    // FALSE if error, or user changed their mind about rescheduling
                    // Error cases:
                    // 1. User inputs an "invisible" index (which is an error case I did not bother to fix in the original method)
                    // 2. User inputs the option to go "Back", which means the rest of the code should not execute

                    // scheduleAppt automatically increments the user's booking try at the end of the method
                    // Because this is a reschedule operation, refund the user's booking try
                    patient.decrementCurrentAppointmentBookings();

                    // Set the status of the appointment being rescheduled to AVAILABLE in both the patient and doctor's appointment lists
                    ArrayList<Appointment> doctorAppointments = reschedulableAppointments.get(selector).getDoctor().getAvailability();
                    for (int i = 0; i < doctorAppointments.size(); i++) {
                        if (doctorAppointments.get(i).getId().equals(reschedulableAppointments.get(selector).getId())) {
                            reschedulableAppointments.get(selector).setStatus(Appointment.Status.AVAILABLE.toString());
                            doctorAppointments.get(i).setStatus(Appointment.Status.AVAILABLE.toString());
                        }
                    }

                    // DEBUGGING
                    System.out.println("\nDEBUGGING - Doctor's appointments and statuses");
                    doctorAppointments.forEach(s -> System.out.println(s.getId() + " " + s.getStatus()));
                    System.out.println("\nDEBUGGING - Patient's appointments and statuses");
                    patientAppointments.forEach(s -> System.out.println(s.getId() + " " + s.getStatus()));

                    // Now here comes the tricky part
                    // I need to somehow update rescheduleAppts from within this do/while loop
                    // Or I can also 'return' and totally exit this method, but what if the user has more than one appointment to reschedule...?
                }
            } while (selector != (maxAppointmentsRange - 1));
        } else {
            System.out.println("There are no appointments available for you to reschedule.");
        }
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
