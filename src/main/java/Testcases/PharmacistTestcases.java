package Testcases;

import Models.*;
import Utility.DataProcessing;
import Utility.DateTime;
import Controllers.*;
import java.util.ArrayList;

public class PharmacistTestcases {
    public static void main(String[] args) {
        DataProcessing dp = new DataProcessing();
        DateTime dt = new DateTime();
        ArrayList<Patient> patientList = dp.generatePatientList(dp.readFromCSV("Patient_List.csv"));
        ArrayList<Doctor> doctorList = dp.generateDoctorList(dp.readFromCSV("Doctor_List.csv"));
        ArrayList<Appointment> appointmentList = dt.generateAppointmentsList(doctorList);
        dp.updateDoctorsWithAppointments(doctorList, appointmentList);

        MedicationInventory mi = dp.generateMedicationList(dp.readFromCSV("Medicine_List.csv"));
        Pharmacist p = new Pharmacist("1", "Cherilyn", 'M', 69);
        PharmacistController pc = new PharmacistController(mi, doctorList);

        pc.displayMenu(p);
    }
    
}
