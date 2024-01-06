package application.tryingpr.Models;

import application.tryingpr.helperClasses.Role;

public class Person {

    public static int MAX_NUM_OF_PERSONS = 100000;
    private String name;

    private String birthday = " ";
    private String phone;

    private int salary;
    private String userName;
    private String password = " ";
    private Role role;

    public Person(String name, String userName, String password, String birthday, int salary, String Phone,  Role role) {

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        if (userName == null || userName.isEmpty()) {
            throw new IllegalArgumentException("UserName cannot be null or empty");
        }

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        if (birthday == null || birthday.isEmpty()) {
            throw new IllegalArgumentException("Invalid birthday format");
        }

        if (salary <= 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }

        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.birthday = birthday;
        this.phone = Phone;
        this.salary = salary;
    }

    public Person(String userName, String password, Role role){

        if (userName == null || userName.isEmpty()) {
            throw new IllegalArgumentException("UserName cannot be null or empty");
        }

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }

        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public Person(String name){
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public String getBirthday(){ return birthday;}


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password){ this.password = password;}

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(String birthday){this.birthday = birthday;}

    @Override
    public String toString() {
        return name + "," + birthday + "," +
                phone + "," + salary +
                "," + userName + "," + password + "," + role.toString();
    }
}

