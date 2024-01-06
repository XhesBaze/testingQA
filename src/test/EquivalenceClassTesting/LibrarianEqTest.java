package EquivalenceClassTesting;

import application.tryingpr.Models.Librarian;
import application.tryingpr.helperClasses.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LibrarianEqTest {

    @Test
    public void test_ConstructorWith7Parameters() {

        Librarian validLibrarian = new Librarian("Antea Toska", "anteatoska", "password", "2003-01-01", 3000, "123456789", Role.Librarian);
        assertNotNull(validLibrarian);
        assertEquals("Antea Toska", validLibrarian.getName());
        assertEquals(0.0, validLibrarian.getTotalBilled(), 0.001); // Assuming a small epsilon for double comparison


        assertThrows(IllegalArgumentException.class, () ->
                new Librarian("", "", "password", "2003-01-01", -500, "123456789", Role.Librarian)
        );
    }

    @Test
    public void test_ConstructorWith8Parameters() {

        Librarian librarian = new Librarian("Xhesi Baze", "xhesibaze", "password", "2003-01-01", 3500, "987654321", Role.Librarian, 1000.0);
        assertNotNull(librarian);
        assertEquals(1000.0, librarian.getTotalBilled());

        assertThrows(IllegalArgumentException.class, () -> new Librarian("", "janedoe", "password", "2003-01-01", 3500, "987654321", Role.Librarian, -500.0));

    }

    @Test
    public void test_GettersAndSetters() {
        Librarian librarian = new Librarian("Test User", "testuser", "password", "2003-01-01", 3000, "123456789", Role.Librarian);

        librarian.setTotalBilled(1500.0);
        assertEquals(1500.0, librarian.getTotalBilled(), 0.001);  // Assuming a small epsilon for double comparison

        librarian.setTotalBilled(0.0);
        assertEquals(0.0, librarian.getTotalBilled(), 0.001);

        assertThrows(IllegalArgumentException.class, () -> librarian.setTotalBilled(-100.0));
    }

    @Test
    public void test_ToString() {

        Librarian librarian = new Librarian("Antea Toska", "anteatoska", "password", "2003-01-01", 3000, "123456789", Role.Librarian);
        String expectedString = "Antea Toska,2003-01-01,123456789,3000,anteatoska,password,Librarian,0.0";
        assertEquals(expectedString, librarian.toString());

        Librarian anotherLibrarian = new Librarian("Xhesi Baze", "xhesibaze", "password", "2003-01-01", 3500, "987654321", Role.Librarian, 1000.0);
        String expectedStringWithTotalBilled = "Xhesi Baze,2003-01-01,987654321,3500,xhesibaze,password,Librarian,1000.0";
        assertEquals(expectedStringWithTotalBilled, anotherLibrarian.toString());


    }
}

