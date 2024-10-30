package Models;

public class Appointment {

    // attributes
    private String id;
    private Patient patient;
    private Doctor doctor;
//    private LocalDate date;
//    private LocalTime time;

    // (Oct 27, 2024 update)
    // I am making these attributes Strings because I don't want to add in the conversion/formatting from LocalDate/Time <-> String
    // every time I work with them
    private String date;
    private String time;
    private String status;

    public enum Status {
        AVAILABLE,  // default state of appointment, AVAILABLE for booking
        PENDING,    // state when an appointment is booked by a Patient but has yet to be approved/rejected by a Doctor
        CONFIRMED,
        CANCELLED, // should this be REJECTED instead??
        COMPLETED   // state when the appointment is finished, used for medical records history
    }

    // default constructor
    public Appointment() {
    }

    // standard constructor
    public Appointment(String id, Patient patient, Doctor doctor, String date, String time) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
        this.status = Status.AVAILABLE.toString();
    }

    // overloaded constructor - additional parameter Status
    public Appointment(String id, Patient patient, Doctor doctor, String date, String time, String status) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

//    public LocalDate getDate() {
//        return date;
//    }
//
//    public LocalTime getTime() {
//        return time;
//    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

//    public void setDate(LocalDate date) {
//        this.date = date;
//    }
//
//    public void setTime(LocalTime time) {
//        this.time = time;
//    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}