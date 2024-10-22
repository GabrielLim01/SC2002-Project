package Models;

public class Doctor extends User {

    // attributes
    private String id;
    private String name;
    private char gender; //can be M (male), F (female), or O (other)
    private int age;

    // default constructor
    public Doctor() {
    }

    // standard constructor
    public Doctor(String id, String name, char gender, int age) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

//    public ArrayList<Models.Doctor> generateDoctorData(){
//        ArrayList<Models.Doctor> listOfDoctors = new ArrayList<Models.Doctor>();
//
//    }
};
