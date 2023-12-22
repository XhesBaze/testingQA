package BoundaryValueTesting;

import application.tryingpr.Models.Administrator;
import application.tryingpr.helperClasses.Role;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class AdministratorTest {


    @Test
    void testConstructor() {

        Administrator administrator = new Administrator("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Administrator);

        assertEquals("Antea", administrator.getName());
        assertEquals("antea", administrator.getUserName());
        assertEquals("12345678", administrator.getPassword());
        assertEquals("20/7/2003", administrator.getBirthday());
        assertEquals(50000, administrator.getSalary());
        assertEquals("0123456", administrator.getPhone());
        assertEquals(Role.Administrator, administrator.getRole());
    }

    @Test
    void testChangePassword() {
        Administrator administrator = new Administrator("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Administrator);
        administrator.setPassword("newPassword");
        assertEquals("newPassword", administrator.getPassword());
    }


    @Test
    void testChangeUsername() {
        Administrator administrator = new Administrator("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Administrator);
        administrator.setUserName("antea123");
        assertEquals("antea123", administrator.getUserName());
    }


    @Test
    void testChangePhone() {
        Administrator administrator = new Administrator("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Administrator);
        administrator.setPhone("1234567");
        assertEquals("1234567", administrator.getPhone());
    }


    @Test
    void testChangeSalary() {
        Administrator administrator = new Administrator("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Administrator);
        administrator.setSalary(60000);
        assertEquals(60000, administrator.getSalary());
    }


    @Test
    void testChangeRole() {
        Administrator administrator = new Administrator("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Administrator);
        administrator.setRole(Role.Manager);
        assertEquals(Role.Manager, administrator.getRole());
    }

}
