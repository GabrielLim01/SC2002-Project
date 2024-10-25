package Models;

import java.util.ArrayList;

public class Doctor extends User {

    // attributes
    private String id;
    private String name;
    private char gender; //can be M (male), F (female), or O (other)
    private int age;
    private String role;
    private ArrayList<String> availability;

    // default constructor
    public Doctor() {
    }

    // standard constructor
    public Doctor(String id, String name, char gender, int age, ArrayList<String> availability) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.role = Roles.DOCTOR.toString();
        this.availability = availability;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public ArrayList<String> getAvailability() {
        return availability;
    }

    public void setAvailability(ArrayList<String> availability) {
        this.availability = availability;
    }
};
