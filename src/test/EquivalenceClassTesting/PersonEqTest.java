package EquivalenceClassTesting;

import application.tryingpr.Models.Person;
import application.tryingpr.helperClasses.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonEqTest {

    @Test
    public void testValidConstructorParameters() {

        assertDoesNotThrow(() -> new Person("Ersa", "ersa123", "password123", "2002-01-01", 50000, "1234567890", Role.Manager));
        assertDoesNotThrow(() -> new Person("username1", "password123", Role.Librarian));
        assertDoesNotThrow(() -> new Person("name1"));
    }

    @Test
    public void testInvalidConstructorParameters() {

        assertThrows(IllegalArgumentException.class, () -> new Person("", "user", "pass", "2000-01-01", 0, "123", null));

        assertThrows(IllegalArgumentException.class, () -> new Person("Xhesi", "", "pass", "2000-01-01", 0, "123", null));

        assertThrows(IllegalArgumentException.class, () -> new Person("Xhesi", "user", "", "2000-01-01", 0, "123", null));

        assertThrows(IllegalArgumentException.class, () -> new Person("Xhesi", "user", "pass", "2000-01-01", -1000, "123", null));

        assertThrows(IllegalArgumentException.class, () -> new Person("Xhesi", "user", "pass", "2000-01-01", 0, "123", null));

        assertThrows(IllegalArgumentException.class, () -> new Person("Xhesi", "user", "pass", "2000-01-01", 0, "12345678901234567", null));

        assertThrows(IllegalArgumentException.class, () -> new Person("Xhesi", "user", "pass", "2000-01-01", 0, "not_a_number", null));

        assertThrows(IllegalArgumentException.class, () -> new Person("Xhesi", "user", "pass", "2000-01-01", 0, "123", null));
    }
}
