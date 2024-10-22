package Views;

import Controllers.*;
import Models.*;
import Utility.*;

import java.util.ArrayList;

public class TestEnvironment {

    // TEST FEATURE - Testing generation of object list from read csv data
    public static void main(String[] args) {
        DataProcessing dp = new DataProcessing();
        PatientController patientController = new PatientController();

        ArrayList<String> patientListStr = dp.readFromCSV("Patient_List.csv");
        // patientListStr.forEach(System.out::println);

        // should this generation method be moved into the readFromCSV method??
        // so that only one ArrayList needs to be initialized, and only one method needs to be invoked
        // instead of the two ArrayLists and two methods invoked here
        ArrayList<Patient> patientList = dp.generatePatientList(patientListStr);
        patientList.forEach(patient -> patientController.displayMenu(patient));
    }
}
