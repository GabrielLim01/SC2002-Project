package Models;

public class MedicalRecord {

    // attributes
    private String id;
    private Patient patient;

    // additional attributes, representing information the doctor inputs into the system on behalf of the Patient

    // the below 4 attributes are deprecated for now due to additional coding complexity
//    private double height;
//    private double weight;
//    private String weightStatus;
    //private ArrayList<Diagnosis> diagnoses;

    // Diagnosis and Treatment may be of their own custom class type rather than String, will review again after combining other branches' code
    private String diagnosis;
    private String treatment;

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

    // standard constructor, complete (some attributes are deprecated for now, so this constructor is not in use)
//    public MedicalRecord(String id, Patient patient, double height, double weight, ArrayList<String> diagnoses, ArrayList<String> treatments) {
//        this.id = id;
//        this.height = height;
//        this.weight = weight;
//        this.weightStatus = calculateBMI(height, weight);
//        this.diagnoses = diagnoses;
//        this.treatments = treatments;
//    }

    // standard constructor, basic
    public MedicalRecord(String id, Patient patient, String diagnosis, String treatment) {
        this.id = id;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
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

    // getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

//    public double getHeight() {
//        return height;
//    }
//
//    public void setHeight(double height) {
//        this.height = height;
//    }
//
//    public double getWeight() {
//        return weight;
//    }
//
//    public void setWeight(double weight) {
//        this.weight = weight;
//    }

//    public String getWeightStatus() {
//        return weightStatus;
//    }
//
//    public void setWeightStatus(String weightStatus) {
//        this.weightStatus = weightStatus;
//    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
}
