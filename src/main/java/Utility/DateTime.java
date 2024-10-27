// (Oct 27, 2024 update) Should the sole method in this class be moved to DataProcessing instead?

// THIS CLASS GENERATES THE DATES AND TIMES FOR THE APPOINTMENT CLASS
// DATES AND TIMES ARE COMBINED INTO A "TIMESLOT"

package Utility;

import Models.Appointment;
import Models.Doctor;
import Models.Patient;

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

        // TESTS SUCCESSFUL
        // TESTING APPOINTMENT DATES AND TIMESLOTS DYNAMIC AUTO-GENERATION
        // 1. DATE GENERATION (1 week starting from tomorrow)
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(2);
        // (25/10/24 update) I changed endDate to a max duration of 1 day instead
        // Should be 8 normally

        Stream<LocalDate> datesList = startDate.datesUntil(endDate);
        //listOfDates.forEach(System.out::println);

        // Convert each LocalDate into a String and save into an arrayList
        ArrayList<String> formattedDatesList = new ArrayList<String>();
        datesList.forEach(s -> formattedDatesList.add(dateFormatter.format(s)));
        // formattedListOfDates.forEach(System.out::println);

        // 2. TIME GENERATION (8am to 4pm)
        // (25/10/24 update) I changed this to a 4-hour duration instead to reduce console output printing length
        final int startHour = 8;
        final int endHour = 12; // should be 16 normally
        ArrayList<String> timesList = new ArrayList<String>();
        for (int i = startHour; i < (endHour + 1); i++) {
            timesList.add(timeFormatter.format(LocalTime.MIDNIGHT.plusHours(i)));
        }
        // listOfTimes.forEach(System.out::println);

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

        // listOfTimeSlots.forEach(System.out::println);

        return appointmentsList;
    }
}
