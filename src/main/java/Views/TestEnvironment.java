package Views;

import Controllers.*;
import Models.*;
import Utility.*;

import java.util.ArrayList;

public class TestEnvironment {

    public static void main(String[] args) {
        DataProcessing dp = new DataProcessing();
        DateTime dt = new DateTime();
        PatientController patientController = new PatientController();
        DoctorController doctorController = new DoctorController();

        ArrayList<Patient> patientList = dp.generatePatientList(dp.readFromCSV("Patient_List.csv"));
        // patientList.forEach(patient -> patientController.displayMenu(patient));

        ArrayList<Doctor> doctorList = dp.generateDoctorList(dp.readFromCSV("Doctor_List.csv"));

        ArrayList<Appointment> appointmentList = dt.generateAppointmentList(doctorList);
        dp.updateDoctorsListWithAppointments(doctorList, appointmentList);

        //patientController.displayMenu(patientList.get(0), appointmentList);
        doctorList.forEach(doctor -> doctorController.displayMenu(doctor));
    }
}
