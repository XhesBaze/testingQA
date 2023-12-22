package EquivalenceClassTesting;

import application.tryingpr.helperClasses.Role;
import application.tryingpr.Models.Manager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ManagerEqTest {

    @Test
    public void test_ValidManagerConstructor() {
        Role validRole = Role.Manager;
        new Manager("Antea Toska", "anteatoska", "password123", "1990-01-01", 50000, "1234567890", validRole);
    }

    @Test
    public void test_InvalidManagerConstructor() {
        assertThrows(IllegalArgumentException.class,
                () -> new Manager("", "user", "pass", "2000-01-01", 0, "123", null));

        assertThrows(IllegalArgumentException.class,
                () -> new Manager(null, null, null, null, -1000, null, null));
    }
}
