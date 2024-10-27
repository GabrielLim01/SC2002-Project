package Models;

import java.time.*;

public class Appointment {

    // attributes
    private int id;
    private Patient patient;
    private Doctor doctor;
//    private LocalDate date;
//    private LocalTime time;

    // (Oct 27, 2024 update)
    // I am making these Strings because I don't want to add in the conversion/formatting to String codes every time
    private String date;
    private String time;

    private enum Status {
        INACTIVE,
        PENDING,
        CONFIRMED,
        CANCELLED,                  // should this be REJECTED instead??
        COMPLETED
    }

    // default constructor
    public Appointment() {
    }

    // standard constructor
    public Appointment(int id, Patient patient, Doctor doctor, String date, String time) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
    }

    public int getId() {
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

    public void setId(int id) {
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

}
