package application.tryingpr.Models;

import application.tryingpr.helperClasses.Role;

public class Manager extends Person {

    public Manager(String userName, String password, Role role) {
        super(userName, password, role);

    }

    public Manager(String name, String userName, String password, String birthday, int salary, String phone, Role role) {
        super(name, userName, password, birthday, salary, phone, role);

    }
}
