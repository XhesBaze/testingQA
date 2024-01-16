package Integration;
import application.tryingpr.Models.Librarian;
import application.tryingpr.helperClasses.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LibrarianIntegrationTest {
    @Test
    public void testLibrarianInitialization() {

        Librarian librarian = new Librarian("John Doe", "john", "password", "2000-01-01", 50000, "123456789", Role.Librarian);

        assertNotNull(librarian);

        assertEquals("John Doe", librarian.getName());
        assertEquals("john", librarian.getUserName());
        assertEquals("password", librarian.getPassword());
        assertEquals("2000-01-01", librarian.getBirthday());
        assertEquals(50000, librarian.getSalary());
        assertEquals("123456789", librarian.getPhone());
        assertEquals(Role.Librarian, librarian.getRole());
        assertEquals(0.0, librarian.getTotalBilled());
    }

    @Test
    public void testLibrarianTotalBilled() {

        Librarian librarian = new Librarian("Jane Doe", "jane", "password", "1990-01-01", 60000, "987654321", Role.Librarian, 100.0);

        assertNotNull(librarian);

        assertEquals(100.0, librarian.getTotalBilled());


        librarian.setTotalBilled(150.0);
        assertEquals(150.0, librarian.getTotalBilled());
    }

    @Test
    public void testLibrarianToString() {

        Librarian librarian = new Librarian("James Doe", "james", "password", "1980-01-01", 70000, "555555555", Role.Librarian, 200.0);

        assertNotNull(librarian);

        assertEquals("James Doe,1980-01-01,555555555,70000,james,password,Librarian,200.0", librarian.toString());
    }
}
