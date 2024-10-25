package Models;

public class Doctor extends User {

    // attributes
    private String id;
    private String name;
    private char gender; //can be M (male), F (female), or O (other)
    private int age;
    private String role;

    // default constructor
    public Doctor() {
    }

    // standard constructor
    public Doctor(String id, String name, char gender, int age) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.role = Roles.DOCTOR.toString();
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
};
