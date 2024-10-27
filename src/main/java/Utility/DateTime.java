// (Oct 27, 2024 update) Consider moving the sole method in this class to DataProcessing instead,
// and then deleting this class

// This class generates the dates and times for the Appointment class and related Controller classes
// Dates and times are combined into a "Timeslot"

package Utility;

import Models.Appointment;
import Models.Doctor;

import java.time.format.DateTimeFormatter;
import java.time.*;
import java.util.stream.Stream;
import java.util.ArrayList;

public class DateTime {

    // default constructor
    public DateTime(){}

    public ArrayList<Appointment> generateAppointmentList(ArrayList<Doctor> doctorList){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM uuuu"); // e.g. 01 Jan 2001
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a"); // e.g. 8:00 am

        // TO-DO: Possibly combine date and time generation together

        // 1. DATE GENERATION (1 week starting from tomorrow)
        // (Oct 25, 2024 update) I changed endDate to a max duration of 1 day instead
        // Should be 8 normally
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(2); // should be 8 normally

        Stream<LocalDate> datesList = startDate.datesUntil(endDate);
        // DEBUGGING
        // datesList.forEach(System.out::println);

        // Convert each LocalDate into a String and save into an arrayList
        ArrayList<String> formattedDatesList = new ArrayList<String>();
        datesList.forEach(s -> formattedDatesList.add(dateFormatter.format(s)));
        // DEBUGGING
        // formattedDatesList.forEach(System.out::println);

        // 2. TIME GENERATION (8am to 4pm)
        // (Oct 25, 2024 update) I changed the overall duration to 4 hours instead to reduce console output printing length
        final int startHour = 8;
        final int endHour = 12; // should be 16 normally
        ArrayList<String> timesList = new ArrayList<String>();
        for (int i = startHour; i < (endHour + 1); i++) {
            timesList.add(timeFormatter.format(LocalTime.MIDNIGHT.plusHours(i)));
        }
        // DEBUGGING
        // timesList.forEach(System.out::println);

        // 3. GENERATE APPOINTMENT TIMESLOTS DYNAMICALLY IN A TRIPLE NESTED FOR LOOP
        // DOCTORS { DATES { TIMES { ... } } }
        ArrayList<Appointment> appointmentsList = new ArrayList<Appointment>();
        for (int i=0; i <doctorList.size(); i++){
            for (int j=0; j < formattedDatesList.size(); j++){
                for (int k=1; k < timesList.size() + 1; k++){
                    appointmentsList.add(new Appointment(k, null, doctorList.get(i), formattedDatesList.get(j),
                            timesList.get(k-1)));
                }
            }
        }

        return appointmentsList;
    }
}
