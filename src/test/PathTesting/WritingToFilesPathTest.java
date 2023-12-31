package PathTesting;

import application.tryingpr.helperClasses.writingToFiles;
import org.junit.jupiter.api.Assertions;


import org.junit.jupiter.api.Test;


public class WritingToFilesPathTest {
    @Test
    void test_ReadCredentials1() {
        // Path 1: Start → Check Length → Open File → Read File → Return role
        String username = "admin";
        String password = "admin";
        Assertions.assertNotNull(writingToFiles.readCredentials(username, password,"res/ROLES.txt"));
    }

    @Test
    void test_ReadCredentials2() {
        // Path 2: Start → Check Length → Open File → Read File → Match → Return role
        String username = "admin";
        String password = "admin";
        String expectedRole = "Administrator";
        Assertions.assertEquals(expectedRole, writingToFiles.readCredentials(username, password,"res/ROLES.txt"));
    }

    @Test
    void test_ReadCredentials3() {
        // Path 3: Start → Check Length → Open File → No match → Return null
        String username = "admin1";
        String password = "password1";
        Assertions.assertNull(writingToFiles.readCredentials(username, password,"res/ROLES.txt"));
    }


    @Test
    void test_CheckFileContent1() {
        // Path 1: Start → Try to read File → Return true
        String filePath = "res/ROLES.txt";
        String expectedContent = "admin,admin,Administrator";
        Assertions.assertTrue(writingToFiles.checkFileContent(filePath, expectedContent));
    }

    @Test
    void test_CheckFileContent2() {
        // Path 2: Start → Try to read the file → Content not correct -> Return false
        String filePath = "res/PERSONS.txt";
        String expectedContent = "someContent";
        Assertions.assertFalse(writingToFiles.checkFileContent(filePath, expectedContent));
    }

    @Test
    void test_CheckFileContent3() {
        // Path 3: Start → Catch IOException → Log Error → Return false
        String filePath = "res/PERSONS1.txt";
        String expectedContent = "someContent";
        Assertions.assertFalse(writingToFiles.checkFileContent(filePath, expectedContent));
    }

    @Test
    void test_CheckFileContent4() {
        // Path 4: Start → Catch IOException → Log Error → Return false
        String filePath = "res/PERSONS1.txt";

        Assertions.assertFalse(writingToFiles.checkFileContent(filePath, null));
    }

    @Test
    void test_CheckFileContent5() {
        // Path 5: Start → Catch IOException → Log Error → Return false
        String filePath = "";
        String expectedContent = "someContent";
        Assertions.assertFalse(writingToFiles.checkFileContent(filePath, expectedContent));
    }

    @Test
    void test_WriteRoles1() {
        // Path 1: Start → Try (Write File) → Flush and Close → No Exception
        try {
            writingToFiles.writeRoles("res/ROLES.txt");
            // If the method completes without throwing an exception, the test passes
            Assertions.assertTrue(true);
        } catch (Exception e) {
            Assertions.fail("Unexpected exception: " + e.getMessage());
        }
    }

}
