package Utility;

import Models.Doctor;
import Models.Patient;

import java.util.ArrayList;

public class Helper {

    public static Doctor findDoctor(ArrayList<Doctor> doctorList, String doctorId){
        for (Doctor doctor : doctorList) {
            if (doctor.getId().equals(doctorId)) {
                return doctor;
            }
        }
        return null;
    }

    public static Patient findPatient(ArrayList<Patient> patientList, String patientId){
        for (Patient patient : patientList) {
            if (patient.getId().equals(patientId)) {
                return patient;
            }
        }
        return null;
    }
}
