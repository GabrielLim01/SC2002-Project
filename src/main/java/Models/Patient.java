package Models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Patient extends User {

    // Objects and variables for data processing
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM uuuu");

    // attributes
    private String id;
    private String name;
    private LocalDate dateOfBirth;
    private char gender; // can be M (male), F (female), or O (other)
    private int phoneNo;
    private String email;
    private String bloodType; // it cannot simply be char, because of Rh protein (e.g. O+ / O- blood type)
    private String role;
    private ArrayList<Appointment> appointments;
    private int currentAppointmentBookings;
    private int maxAppointmentBookings; // this is the field
    private final int MAX_APPOINTMENT_BOOKINGS = 1; // this is the actual value

    // default constructor
    public Patient() {
    }

    // standard constructor
    public Patient(String id, String name, String dateOfBirth, char gender, int phoneNo, String email, String bloodType) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = LocalDate.parse(dateOfBirth, dateTimeFormatter);
        this.gender = gender;
        this.phoneNo = phoneNo;
        this.email = email;
        this.bloodType = bloodType;
        this.role = Roles.PATIENT.toString();
        this.appointments = new ArrayList<Appointment>();
        this.currentAppointmentBookings = 0;
        this.maxAppointmentBookings = MAX_APPOINTMENT_BOOKINGS;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public char getGender() {
        return gender;
    }

    public int getPhoneNo() {
        return phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getRole() {
        return role;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public int getCurrentAppointmentBookings(){
        return currentAppointmentBookings;
    }

    public int getMaxAppointmentBookings(){
        return maxAppointmentBookings;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setPhoneNo(int phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }

    public void setCurrentAppointmentBookings(int currentAppointmentBookings){
        this.currentAppointmentBookings = currentAppointmentBookings;
    }

    public void decrementCurrentAppointmentBookings(){
        this.currentAppointmentBookings = getCurrentAppointmentBookings() - 1;
    }

    public void incrementCurrentAppointmentBookings(){
        this.currentAppointmentBookings = getCurrentAppointmentBookings() + 1;
    }
};

