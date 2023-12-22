package BoundaryValueTesting;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import application.tryingpr.helperClasses.Role;
import application.tryingpr.Models.Librarian;

public class LibrarianTest {

    @Test
    void testConstructor1(){
        Librarian librarian = new Librarian("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Librarian);

        assertEquals("Antea", librarian.getName());
        assertEquals("antea", librarian.getUserName());
        assertEquals("12345678", librarian.getPassword());
        assertEquals("20/7/2003", librarian.getBirthday());
        assertEquals(50000, librarian.getSalary());
        assertEquals("0123456", librarian.getPhone());
        assertEquals(Role.Librarian, librarian.getRole());
    }

    @Test
    void testConstructor2(){
        Librarian librarian = new Librarian("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Librarian, 50.0);

        assertEquals("Antea", librarian.getName());
        assertEquals("antea", librarian.getUserName());
        assertEquals("12345678", librarian.getPassword());
        assertEquals("20/7/2003", librarian.getBirthday());
        assertEquals(50000, librarian.getSalary());
        assertEquals("0123456", librarian.getPhone());
        assertEquals(Role.Librarian, librarian.getRole());
        assertEquals(50.0, librarian.getTotalBilled());
    }

    @Test
    public void testGetTotalBilled() {
        Librarian librarian = new Librarian("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Librarian, 50.0);
        librarian.setTotalBilled(50.0);
        assertEquals(librarian.getTotalBilled(), 50.0);
    }

    @Test
    public void testSetTotalBilled() {
        Librarian librarian = new Librarian("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Librarian, 50.0);
        librarian.setTotalBilled(50.0);
        assertEquals(librarian.getTotalBilled(), 50.0);
    }


    @Test
    public void testToString() {
        Librarian librarian = new Librarian("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Librarian, 50.0);
        librarian.setName("Antea");
        librarian.setUserName("antea");
        librarian.setPassword("12345678");
        librarian.setBirthday("20/7/2003");
        librarian.setSalary(50000);
        librarian.setPhone("0123456");
        librarian.setRole(Role.Librarian);
        librarian.setTotalBilled(50.0);

        String expected = "Antea,20/7/2003,0123456,50000,antea,12345678,Librarian,50.0";
        assertEquals(expected, librarian.toString());
    }

     @Test
     void testChangePassword() {
         Librarian librarian = new Librarian("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Librarian, 50.0);
         librarian.setPassword("newPassword");
         assertEquals("newPassword", librarian.getPassword());
     }


     @Test
     void testChangeUsername() {
         Librarian librarian = new Librarian("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Librarian, 50.0);
         librarian.setUserName("antea123");
         assertEquals("antea123", librarian.getUserName());
     }


     @Test
     void testChangePhone() {
         Librarian librarian = new Librarian("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Librarian, 50.0);
         librarian.setPhone("1234567");
         assertEquals("1234567", librarian.getPhone());
     }


     @Test
     void testChangeSalary() {
         Librarian librarian = new Librarian("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Librarian, 50.0);
         librarian.setSalary(60000);
         assertEquals(60000, librarian.getSalary());
     }


     @Test
     void testChangeRole() {
         Librarian librarian = new Librarian("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Librarian, 50.0);
         librarian.setRole(Role.Manager);
         assertEquals(Role.Manager, librarian.getRole());
     }

     @Test
     void testChangeTotalBilled() {
         Librarian librarian = new Librarian("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Librarian, 50.0);
         librarian.setTotalBilled(50.0);
         assertEquals(50.0, librarian.getTotalBilled());
     }


}
