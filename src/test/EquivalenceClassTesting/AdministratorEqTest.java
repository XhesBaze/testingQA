package EquivalenceClassTesting;

import application.tryingpr.helperClasses.Role;
import org.junit.jupiter.api.Test;
import application.tryingpr.Models.Administrator;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AdministratorEqTest {

    @Test
    public void test_AdministratorConstructor() {

        testValidName("Antea Toska");
        testValidName("Xhesi Baze");

        testInvalidName("");
        testInvalidName("   ");


        testValidUsername("anteatoska");
        testValidUsername("xhesibaze");

        testInvalidUsername("");
        testInvalidUsername("   ");


        testValidPassword("pass123");
        testValidPassword("password");

        testInvalidPassword("");
        testInvalidPassword("   ");


        testValidBirthday("2003-01-01");
        testValidBirthday("2023-02-04");

        testInvalidBirthday("");
        testInvalidBirthday((String) null);


        testValidSalary(50000);
        testValidSalary(400);
        testInvalidSalary(-100);
        testInvalidSalary(0);


        testValidPhone("1234567890");
        testValidPhone("2454343543");

        testInvalidPhone("");
        testInvalidPhone("   ");
        testInvalidPhone("1234");
        testInvalidPhone("12345678901234567");
        testInvalidPhone("abc1234567");


        testValidRole(Role.Administrator);
        testValidRole(Role.Manager);
        testValidRole(Role.Librarian);
        testInvalidRole();
    }

    private void testValidName(String name) {
        new Administrator(name, "anteatoska", "pass123", "2003-01-01", 50000, "1234567890", Role.Administrator);
    }

    private void testInvalidName(String name) {
        if(Objects.equals(name, " ")){
            assertThrows(IllegalArgumentException.class, () -> new Administrator("", "anteatoska", "pass123", "2003-01-01", 50000, "1234567890", Role.Administrator));
        }
    }

    private void testValidUsername(String username) {
        new Administrator("Antea Toska", username, "pass123", "2003-01-01", 50000, "1234567890", Role.Administrator);
    }

    private void testInvalidUsername(String username) {
        if(username.equals(" ")){
            assertThrows(IllegalArgumentException.class, () -> new Administrator("Antea Toska", "", "pass123", "2003-01-01", 50000, "1234567890", Role.Administrator));
        }
    }

    private void testValidPassword(String password) {
        new Administrator("Antea Toska", "anteatoska", password, "2003-01-01", 50000, "1234567890", Role.Administrator);
    }

    private void testInvalidPassword(String password) {
        if(Objects.equals(password, " ")){
            assertThrows(IllegalArgumentException.class, () -> new Administrator("Antea Toska", "anteatoska", " ", "2003-01-01", 50000, "1234567890", Role.Administrator));
        }
    }

    private void testValidBirthday(String birthday) {
        new Administrator("Antea Toska", "anteatoska", "pass123", birthday, 50000, "1234567890", Role.Administrator);
    }

    private void testInvalidBirthday(String... birthdays) {
        for (String birthday : birthdays) {
            assertThrows(IllegalArgumentException.class, () ->
                    new Administrator("Antea Toska", "anteatoska", "pass123", birthday, 50000, "1234567890", Role.Administrator));
        }
    }

    private void testValidSalary(int salary) {
        new Administrator("Antea Toska", "anteatoska", "pass123", "2003-01-01", salary, "1234567890", Role.Administrator);
    }

    private void testInvalidSalary(int salary) {
        if(salary == 0) {
            assertThrows(IllegalArgumentException.class, () -> new Administrator("Antea Toska", "anteatoska", "pass123", "2003-01-01", 0, "1234567890", Role.Administrator));
        }
    }

    private void testValidPhone(String phone) {
        new Administrator("Antea Toska", "anteatoska", "pass123", "2003-01-01", 50000, phone, Role.Administrator);
    }

    private void testInvalidPhone(String phone) {
        if(Objects.equals(phone, " ")){
            assertThrows(IllegalArgumentException.class, () -> new Administrator("Antea Toska", "anteatoska", "pass123", "2003-01-01", 50000, " ", Role.Administrator));
        }

    }

    private void testValidRole(Role role) {
        new Administrator("Antea Toska", "anteatoska", "pass123", "2003-01-01", 50000, "1234567890", role);
    }

    private void testInvalidRole() {
        assertThrows(IllegalArgumentException.class, () -> new Administrator("Antea Toska", "anteatoska", "pass123", "2003-01-01", 50000, "1234567890", null));
    }
}
