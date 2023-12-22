package application.tryingpr.Models;

import application.tryingpr.helperClasses.Role;

public class Librarian extends Person {

    // private variable to store the total amount billed by the librarian
    private double totalBilled;

    public Librarian(String userName, String password, Role role){
        super(userName,password,role);
    }

    // constructor with 7 parameters
    public Librarian(String name, String userName, String password, String Birthday, int salary, String Phone, Role role) {

        // calling the constructor of the super class (Person) with 7 parameters
        super(name, userName, password, Birthday, salary, Phone, role);

        if (name.isEmpty() || userName.isEmpty() || password.isEmpty() || Birthday.isEmpty() || salary <= 0 || Phone.isEmpty()) {
            throw new IllegalArgumentException("A field cannot be null or empty");
        }
        // initializing the totalBilled with 0
        this.totalBilled = 0;

    }

    // constructor with 8 parameters
    public Librarian(String name, String userName, String password, String Birthday, int salary, String Phone, Role role, double totalBilled) {
        // calling the constructor of the super class (Person) with 7 parameters
        super(name, userName, password, Birthday, salary, Phone, role);

        if (name.isEmpty() || userName.isEmpty() || password.isEmpty() || Birthday.isEmpty() || salary <= 0 || Phone.isEmpty()) {
            throw new IllegalArgumentException("A field cannot be null or empty");
        }
        // setting the totalBilled with the provided value
        this.totalBilled = totalBilled;

    }

    // getter method for totalBilled
    public double getTotalBilled() {
        // returning the value of totalBilled
        return totalBilled;
    }

    // setter method for totalBilled
    public void setTotalBilled(double totalBilled) {
        if (totalBilled < 0) {
            throw new IllegalArgumentException("Total billed amount cannot be negative.");
        }
        this.totalBilled = totalBilled;
    }

    // overriding the toString method from the super class (Person)
    @Override
    public String toString() {
        // returning the string representation of the object
        // calling the toString method of the super class (Person) and concatenating it with the value of totalBilled
        return super.toString() + "," + totalBilled;
    }
}
