package application.tryingpr.Models;

import application.tryingpr.helperClasses.Role;
// Model class for representing an Administrator
public class Administrator extends Person {

    // Constructor to initialize the administrator object with the given details
    public Administrator(String name, String userName, String password, String Birthday, int salary, String Phone, Role role) {
        // Calling the super class constructor
        super(name, userName, password, Birthday, salary, Phone, role);
    }

}

