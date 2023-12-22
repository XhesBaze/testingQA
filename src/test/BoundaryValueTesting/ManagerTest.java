package BoundaryValueTesting;

import application.tryingpr.Models.Manager;
import application.tryingpr.helperClasses.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ManagerTest {

    @Test

    void test_Constructor(){
        Manager manager = new Manager("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Manager);

        assertEquals("Antea", manager.getName());
        assertEquals("antea", manager.getUserName());
        assertEquals("12345678", manager.getPassword());
        assertEquals("20/7/2003", manager.getBirthday());
        assertEquals(50000, manager.getSalary());
        assertEquals("0123456", manager.getPhone());
        assertEquals(Role.Manager, manager.getRole());
    }

    @Test
    void test_ChangePassword() {
        Manager manager = new Manager("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Manager);
        manager.setPassword("newPassword");
        assertEquals("newPassword", manager.getPassword());
    }


    @Test
    void test_ChangeUsername() {
        Manager manager = new Manager("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Manager);
        manager.setUserName("antea123");
        assertEquals("antea123", manager.getUserName());
    }


    @Test
    void test_ChangePhone() {
        Manager manager = new Manager("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Manager);
        manager.setPhone("1234567");
        assertEquals("1234567", manager.getPhone());
    }


    @Test
    void test_ChangeSalary() {
        Manager manager = new Manager("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Manager);
        manager.setSalary(60000);
        assertEquals(60000, manager.getSalary());
    }


    @Test
    void test_ChangeRole() {
        Manager manager = new Manager("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Manager);
        manager.setRole(Role.Librarian);
        assertEquals(Role.Librarian, manager.getRole());
    }

}