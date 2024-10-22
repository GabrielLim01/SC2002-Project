package Models;

abstract class User {

    protected enum Roles {
        PATIENT,
        DOCTOR,
        PHARMACIST,
        ADMIN
    }

    protected String username;
    protected String password;

    protected String id;
    protected char gender;
    protected int age;
}
