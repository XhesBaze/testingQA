package Integration;
import application.tryingpr.Models.Librarian;
import application.tryingpr.helperClasses.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LibrarianIntegrationTest {

    @Test
    public void testLibrarianTotalBilled() {

        Librarian librarian = new Librarian("Xhesi Baze", "xhesi", "password", "2002-01-01", 60000, "987654321", Role.Librarian, 100.0);

        assertNotNull(librarian);

        assertEquals(100.0, librarian.getTotalBilled());
        
        librarian.setTotalBilled(150.0);
        assertEquals(150.0, librarian.getTotalBilled());
    }

    @Test
    public void testLibrarianToString() {

        Librarian librarian = new Librarian("Antea Toska", "antea", "password", "2003-01-01", 70000, "555555555", Role.Librarian, 200.0);

        assertNotNull(librarian);

        assertEquals("Antea Toska,1980-01-01,555555555,70000,antea,password,Librarian,200.0", librarian.toString());
    }
}
