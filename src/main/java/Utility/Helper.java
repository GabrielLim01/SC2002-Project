package Utility;

import Models.*;

import java.util.ArrayList;

public class Helper {

    public static Patient findPatient(ArrayList<Patient> patientList, String patientId){
        for (Patient patient : patientList) {
            if (patient.getId().equals(patientId)) {
                return patient;
            }
        }
        return null;
    }

    public static Doctor findDoctor(ArrayList<Doctor> doctorList, String doctorId){
        for (Doctor doctor : doctorList) {
            if (doctor.getId().equals(doctorId)) {
                return doctor;
            }
        }
        return null;
    }

    public static Pharmacist findPharmacist(ArrayList<Pharmacist> pharmacistList, String pharmacistId){
        for (Pharmacist pharmacist : pharmacistList) {
            if (pharmacist.getId().equals(pharmacistId)) {
                return pharmacist;
            }
        }
        return null;
    }

//    public static Administrator findAdministrator(ArrayList<Administrator> administratorList, String administratorId){
//        for (Administrator administrator : administratorList) {
//            if (administrator.getId().equals(administratorId)) {
//                return administrator;
//            }
//        }
//        return null;
//    }
}
