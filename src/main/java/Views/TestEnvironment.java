package Views;

import Controllers.*;
import Models.*;
import Utility.*;

import java.util.ArrayList;

public class TestEnvironment {

    public static void main(String[] args) {
        DataProcessing dp = new DataProcessing();
        PatientController patientController = new PatientController();
        DoctorController doctorController = new DoctorController();

        ArrayList<Patient> patientList = dp.generatePatientList(dp.readFromCSV("Patient_List.csv"));
        // patientList.forEach(patient -> patientController.displayMenu(patient));

        ArrayList<Doctor> doctorList = dp.generateDoctorList(dp.readFromCSV("Doctor_List.csv"));
        // doctorList.forEach(doctor -> doctorController.displayMenu(doctor));

        patientController.displayMenu(patientList.get(0), doctorList);
    }
}
