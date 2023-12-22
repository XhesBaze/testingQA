package application.tryingpr.Models;

import application.tryingpr.helperClasses.Role;

public class Manager extends Person {

    public Manager(String name, String userName, String password, String Birthday, int salary, String Phone, Role role) {
        super(name, userName, password, Birthday, salary, Phone, role);
    }

}
