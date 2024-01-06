package MethodsForFullCoverage;

import application.tryingpr.Models.Person;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonNameTest {

    @Test
    public void testConstructorWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> new Person(null));
    }

    @Test
    public void testConstructorWithEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> new Person(""));
    }

}

