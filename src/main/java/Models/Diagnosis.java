package Models;

public class Diagnosis {

    // attributes
    private String id;
    private String date;
    private String illness;
    private String comments;
    // private Prescription prescription;
    private boolean medicalCertificateIssued;
    private int medicalCertificateLength;

    public enum illnessTypes {
        MALINGERING,
        INFLUENZA,
        PNEUMONIA,
        COVID,
        GASTROENTERITIS,
        TERMINAL_CANCER
    }
}
