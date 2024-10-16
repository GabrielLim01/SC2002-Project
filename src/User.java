// TO-DO: Possible move login and logout methods to a separate controller class,
// (e.g. UserController), to handle authentication - login and logout should NOT be in the User superclass
// following the MVC (Model-View-Controller) paradigm

abstract class User {

    protected enum Roles {
        PATIENT,
        DOCTOR,
        PHARMACIST,
        ADMIN
    }

    protected String username;
    protected String password;

    public abstract void login();
    public abstract void logout();
    // public abstract void displayMenu(Object object);
}
