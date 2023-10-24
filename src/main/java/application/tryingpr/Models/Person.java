package application.tryingpr.Models;

import application.tryingpr.helperClasses.Role;

public abstract class Person {

    private String name, birthday, phone;

    private int salary;
    private String userName;
    private String password;
    private Role role;

    public Person(String name, String userName, String password, String Birthday, int salary, String Phone,  Role role) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.birthday = Birthday;
        this.phone = Phone;
        this.salary = salary;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

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

    public void setPassword(String password) {
        this.password = password;
    }

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

    @Override
    public String toString() {
        return name + "," + birthday + "," +
                phone + "," + salary +
                "," + userName + "," + password + "," + role.toString();
    }
}
