package BoundaryValueTesting;
import  application.tryingpr.helperClasses.Role;
import org.junit.jupiter.api.Test;
import application.tryingpr.Models.Person;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {
    @Test
    void test_Constructor(){
        Person person = new Person("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Administrator);

        assertEquals("Antea", person.getName());
        assertEquals("antea", person.getUserName());
        assertEquals("12345678", person.getPassword());
        assertEquals("20/7/2003", person.getBirthday());
        assertEquals(50000, person.getSalary());
        assertEquals("0123456", person.getPhone());
    }

    //name
    @Test
    public void test_GetName() {
        Person person = new Person("Antea","antea", "12345678", "20/7/2003", 50000, "0123456", Role.Librarian);
        person.setName("Antea");
        assertEquals(person.getName(), "Antea");
    }

    @Test
    public void test_SetName() {
        Person person = new Person("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Manager);
        person.setName("Antea");
        assertEquals(person.getName(),"Antea" );
    }


    //username
    @Test
    public void test_GetUserName() {
        Person person = new Person("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Administrator);
        person.setUserName("antea");
        assertEquals(person.getUserName(),"antea" );
    }

    @Test
    public void test_SetUserName() {
        Person person = new Person("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Librarian);
        person.setUserName("antea");
        assertEquals(person.getUserName(),"antea" );
    }

    //password
    @Test
    public void test_GetPassword() {
        Person person = new Person("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Manager);
        person.setPassword("12345678");
        assertEquals(person.getPassword(),"12345678" );
    }

    @Test
    public void test_SetPassword() {
        Person person = new Person("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Administrator);
        person.setPassword("12345678");
        assertEquals(person.getPassword(), "12345678");
    }

    //birthday
    @Test
    public void test_GetBirthday() {
        Person person = new Person("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Librarian);
        person.setBirthday("20/7/2003");
        assertEquals(person.getBirthday(),"20/7/2003" );
    }

    @Test
    public void test_SetBirthday() {
        Person person = new Person("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Manager);
        person.setBirthday("20/7/2003");
        assertEquals(person.getBirthday(),"20/7/2003" );
    }

    //salary
    @Test
    public void test_GetSalary() {
        Person person = new Person("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Administrator);
        person.setSalary(50000);
        assertEquals(person.getSalary(), 50000);
    }

    @Test
    public void test_SetSalary() {
        Person person = new Person("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Administrator);
        person.setSalary(50000);
        assertEquals(person.getSalary(), 50000);
    }


    //phone
    @Test
    public void test_GetPhone() {
        Person person = new Person("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Librarian);
        person.setPhone("0123456");
        assertEquals(person.getPhone(), "0123456");
    }

    @Test
    public void testSetPhone() {
        Person person = new Person("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Librarian);
        person.setPhone("0123456");
        assertEquals(person.getPhone(),"0123456" );
    }


    @Test
    public void test_ToString() {
        Person person = new Person("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Administrator);
        person.setName("Antea");
        person.setUserName("antea");
        person.setPassword("12345678");
        person.setBirthday("20/7/2003");
        person.setSalary(50000);
        person.setPhone("0123456");

        String expected = "Antea,20/7/2003,0123456,50000,antea,12345678,Administrator";
        assertEquals(expected, person.toString());
    }

      @Test
      void test_ChangePassword() {
          Person person = new Person("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Administrator);
          person.setPassword("newPassword");
          assertEquals("newPassword", person.getPassword());
      }


      @Test
      void test_ChangeUsername() {
          Person person = new Person("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Administrator);
          person.setUserName("antea123");
          assertEquals("antea123",person.getUserName());
      }


      @Test
      void test_ChangePhone() {
          Person person = new Person("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Administrator);
          person.setPhone("1234567");
          assertEquals("1234567", person.getPhone());
      }


      @Test
      void test_ChangeSalary() {
          Person person = new Person("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Administrator);
          person.setSalary(60000);
          assertEquals(60000, person.getSalary());
      }


      @Test
      void test_ChangeRole() {
          Person person = new Person("Antea", "antea", "12345678", "20/7/2003", 50000, "0123456", Role.Administrator);
          person.setRole(Role.Manager);
          assertEquals(Role.Manager, person.getRole());
      }

}