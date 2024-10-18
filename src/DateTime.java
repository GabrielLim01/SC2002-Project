import java.time.format.DateTimeFormatter;
import java.time.*;
import java.util.stream.Stream;
import java.util.ArrayList;

public class DateTime {
    public DateTime(){}

    public ArrayList<String> generateDateTime(){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM uuuu"); // e.g. 01 Jan 2001
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a"); // e.g. 8:00 am

        // TO-DO: Possibly combine date and time generation together

        // TESTING APPOINTMENT DATES AND TIMESLOTS DYNAMIC AUTO-GENERATION
        // 1. DATE GENERATION (1 week starting from tomorrow)
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(8);

        Stream<LocalDate> listOfDates = startDate.datesUntil(endDate);
        //listOfDates.forEach(System.out::println);

        // Convert each LocalDate into a String and save into an arrayList
        ArrayList<String> formattedListOfDates = new ArrayList<String>();
        listOfDates.forEach(s -> formattedListOfDates.add(dateFormatter.format(s)));
        // formattedListOfDates.forEach(System.out::println);

        // 2. TIME GENERATION (8am to 4pm)
        final int startHour = 8;
        final int endHour = 16;
        ArrayList<String> listOfTimes = new ArrayList<String>();
        for (int i = startHour; i < (endHour + 1); i++) {
            listOfTimes.add(timeFormatter.format(LocalTime.MIDNIGHT.plusHours(i)));
        }
        // listOfTimes.forEach(System.out::println);

        // 3. DATE AND TIME IN ONE STRING
        ArrayList<String> listOfTimeSlots = new ArrayList<String>();
        for (int i=0; i < formattedListOfDates.size(); i++){
            for (int j=0; j < listOfTimes.size(); j++){
                listOfTimeSlots.add(formattedListOfDates.get(i) + " " + listOfTimes.get(j));
            }
        }
        // listOfTimeSlots.forEach(System.out::println);

        return listOfTimeSlots;
    }
}
