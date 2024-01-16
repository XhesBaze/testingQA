
package MethodsForFullCoverage;
import application.tryingpr.helperClasses.writingToFiles;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class GetCredentialsTest {
    @Test
    void testValidPassword() {
        String result = writingToFiles.readCredentials("admin", "admin","res/ROLES.txt");
        Assertions.assertNotNull(result);
    }

    @Test
    void testLongPassword() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> writingToFiles.readCredentials("xhesi", "ThisIsAReallyLongPasswordThatExceedsFiftyCharacters","res/ROLES.txt"));

        Assertions.assertEquals("Password exceeds maximum length of 50 characters", exception.getMessage());
    }

    @Test
    void testShortPassword() {
        String result = writingToFiles.readCredentials("xhesi", "Short","res/ROLES.txt");
        Assertions.assertNull(result);
    }

    @Test
    void testNullPassword() {
        String result = writingToFiles.readCredentials("xhesi", null,"res/ROLES.txt");
        Assertions.assertNull(result);
    }

    @Test
    void testEmptyPassword() {
        String result = writingToFiles.readCredentials("xhesi", "","res/ROLES.txt");
        Assertions.assertNull(result);
    }

}
