package Controllers;

import Models.Appointment;

abstract class AppointmentController {
    public AppointmentController() {

    }
    public void printAppointment(Appointment appointment) {
        System.out.println(
            "\nDoctor: " + appointment.getDoctor().getName() + 
            "\nPatient: " + appointment.getPatient().getName() + 
            "\nTimeslot: " + appointment.getDate() + " " + appointment.getTime() +
            "\nStatus: " + appointment.getStatus() + 
            "\nMedications: " 
        );
        for (int i=0; i<appointment.getMedications().size(); i++) {
            appointment.getMedications().get(i).printMedication();
        }
    }
}
