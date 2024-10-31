package Models;

import Models.*;

import java.util.ArrayList;

public class MedicalRecord {

    // attributes
    private String id;
    private Patient patient;

    // existing attributes from Patient, representing personal information as contributed by the patient
    private String name;
    private String dateOfBirth;
    private char gender;
    private int phoneNo;
    private String email;
    private String bloodType;

    // additional attributes, representing information the doctor inputs into the system on behalf of the Patient
    private double height;
    private double weight;
    private String weightStatus;
    private ArrayList<Diagnosis> diagnoses;

    public enum weightStatusTypes {
        SEVERELY_UNDERWEIGHT,
        UNDERWEIGHT,
        ACCEPTABLE,
        OVERWEIGHT,
        SEVERELY_OVERWEIGHT
    }

    // default constructor
    public MedicalRecord() {
    }

    // standard constructor
    public MedicalRecord(String id, Patient patient, double height, double weight) {
        this.id = id;
        this.name = patient.getName();
        this.dateOfBirth = patient.getDateOfBirth().toString();
        this.gender = patient.getGender();
        this.phoneNo = patient.getPhoneNo();
        this.email = patient.getEmail();
        this.bloodType = patient.getBloodType();
        this.height = height;
        this.weight = weight;
        this.weightStatus = calculateBMI(height, weight);
    }

    public String calculateBMI(double height, double weight) {
        // BMI formula = kg/m^2
        // assume the user inputs values in kg and metres for weight and height respectively
        double BMI = (weight / height * height);

        // calculation and BMI weight status ranges referenced from here: https://www.calculatorsoup.com/calculators/health/bmi-calculator-women.php
        if (BMI < 16.0) {
            return weightStatusTypes.SEVERELY_UNDERWEIGHT.toString();
        } else if (BMI >= 16.0 && BMI <= 18.4) {
            return weightStatusTypes.UNDERWEIGHT.toString();
        } else if (BMI >= 18.5 && BMI <= 24.9) {
            return weightStatusTypes.ACCEPTABLE.toString();
        } else if (BMI >= 25.0 && BMI <= 29.9) {
            return weightStatusTypes.OVERWEIGHT.toString();
        } else {
            return weightStatusTypes.SEVERELY_OVERWEIGHT.toString();
        }
    }
}
