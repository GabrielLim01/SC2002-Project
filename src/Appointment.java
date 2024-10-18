import java.time.*;

public class Appointment {

    // attributes
    private Patient patient;
    private Doctor doctor;
    private LocalDate date;
    private LocalDateTime time;

    // default constructor
    public Appointment(){}

    // standard constructor
    public Appointment(Patient patient, Doctor doctor, LocalDate date, LocalDateTime time){
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    // View which doctors' appointment slots are available
    public void viewAvailAppts(){

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
