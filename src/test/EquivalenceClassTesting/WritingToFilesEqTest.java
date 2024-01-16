package EquivalenceClassTesting;

import application.tryingpr.Controllers.Controller;
import application.tryingpr.Models.Person;
import application.tryingpr.Models.Book;
import application.tryingpr.Models.Administrator;
import application.tryingpr.Models.Manager;
import application.tryingpr.Models.Librarian;
import application.tryingpr.helperClasses.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import application.tryingpr.helperClasses.writingToFiles;

import java.io.FileNotFoundException;


public class WritingToFilesEqTest {
    @Test
    public void test_readCredentials_ValidCredentials() {
        String result = writingToFiles.readCredentials("admin", "admin","res/ROLES.txt");
        Assertions.assertEquals("Administrator", result);
    }

    @Test
    public void test_readCredentials_NullCredentials() {
        String result = writingToFiles.readCredentials(null, null,"res/ROLES.txt");
        Assertions.assertNull(result);
    }

    @Test
    public void test_readCredentials_ValidUsernameWithNullPassword() {
        String result = writingToFiles.readCredentials("admin", null,"res/ROLES.txt");
        Assertions.assertNull(result);
    }

    @Test
    public void test_readCredentials_NullUsernameWithValidPassword() {
        String result = writingToFiles.readCredentials(null, "password123","res/ROLES.txt");
        Assertions.assertNull(result);
    }

    @Test
    public void test_checkFileContent_FileContentMatches() {

        String filePath = "res/ROLES.txt";
        String expectedContent = "admin,admin,Administrator";
        Assertions.assertTrue(writingToFiles.checkFileContent(filePath, expectedContent));
    }

    @Test
    public void test_checkFileContent_FileContentMismatch() {

        String filePath = "res/ROLES.txt";
        String expectedContent = """
                admin1,admin1,Administrator1
                user1,password1,Manager
                user2,password2,Librarian
                user3,password3,Administrator""";
        Assertions.assertFalse(writingToFiles.checkFileContent(filePath, expectedContent));
    }

    @Test
    public void test_checkFileContent_FileNotFound() {

        String filePath = "res/ROLES11.txt";
        String expectedContent = """
                admin1,admin1,Administrator1
                user1,password1,Manager
                user2,password2,Librarian
                user3,password3,Administrator""";
        Assertions.assertFalse(writingToFiles.checkFileContent(filePath, expectedContent));
    }


    @Test
    public void test_checkFileContent_NullExpectedContent() {

        String filePath = "res/ROLES.txt";
        Assertions.assertFalse(writingToFiles.checkFileContent(filePath, null));
    }


    @Test
    public void test_writeRoles_WriteRolesWithEmptyPersonList() {

        Controller.people.clear();
        Assertions.assertDoesNotThrow(() -> writingToFiles.writeRoles("res/ROLES.txt"));
    }

    @Test
    public void test_writeRoles_WriteRolesWithNullRole() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Person("username", "password", null),
                "Expected to throw IllegalArgumentException for null role");
    }

    @Test
    public void test_writeRoles_WriteRolesWithNullUserName() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Person(null, "password", Role.Manager),
                "Expected to throw IllegalArgumentException for null userName");
    }

    @Test
    public void test_writeRoles_WriteRolesWithNullPassword() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Person("username", null, Role.Manager),
                "Expected to throw IllegalArgumentException for null password");
    }

    @Test
    public void test_getBooks_ValidFileWithBooks() throws FileNotFoundException {

        ObservableList<Book> books;
        books = writingToFiles.getBooks("res/bookInfo.txt");
        Assertions.assertNotNull(books);
        Assertions.assertTrue(books.isEmpty());

    }

    @Test
    public void test_getPersons_ValidFileWithPeople() {

        ObservableList<Person> people = writingToFiles.getPersons("res/PERSONS.txt");
        Assertions.assertNotNull(people);
        Assertions.assertTrue(people.isEmpty());

    }

    @Test
    public void test_getPersons_EmptyFile() {

        ObservableList<Person> people = writingToFiles.getPersons("res/PERSONS.txt");
        Assertions.assertNotNull(people);
        Assertions.assertTrue(people.isEmpty());
    }

    @Test
    public void test_getNumberOfLibrarians_LibrariansExist() {
        Controller.people = FXCollections.observableArrayList(
                new Librarian("Librarian1", "username1", "password1", "2000-01-01", 50000, "1234567890", Role.Librarian)
        );

        String result = writingToFiles.getNumberOfLibrarians();

        Assertions.assertEquals("1", result);
    }

    @Test
    public void test_getNumberOfLibrarians_NoLibrarians() {

        Controller.people = FXCollections.observableArrayList(
                new Administrator("Admin1", "admin1", "password1", "2000-01-01", 60000, "9876543210", Role.Administrator),
                new Manager("Manager1", "manager1", "password2", "2000-01-01", 70000, "5678901234", Role.Manager)

        );

        String result = writingToFiles.getNumberOfLibrarians();

        Assertions.assertEquals("0", result);
    }


    @Test
    public void test_getNumberOfLibrarians_EmptyList() {

        Controller.people = FXCollections.observableArrayList();

        String result = writingToFiles.getNumberOfLibrarians();

        Assertions.assertEquals("0", result);
    }

    @Test
    public void testGetNumberOfLibrarians_OneLibrarian() {

        Controller.people = FXCollections.observableArrayList(
                new Librarian("Librarian1", "username1", "password1", "2000-01-01", 50000, "1234567890", Role.Librarian)
        );

        String result = writingToFiles.getNumberOfLibrarians();

        Assertions.assertEquals("1", result);
    }

    @Test
    public void testGetNumberOfLibrarians_NullList() {
        Controller.people = null;
        Assertions.assertThrows(NullPointerException.class, writingToFiles::getNumberOfLibrarians);
    }

    @Test
    public void testGetNumberOfLibrarians_MixedRoles() {

        Controller.people = FXCollections.observableArrayList(
                new Librarian("Librarian1", "username1", "password1", "2000-01-01", 50000, "1234567890", Role.Librarian),
                new Administrator("Admin1", "admin1", "password1", "2000-01-01", 60000, "9876543210", Role.Administrator),
                new Manager("Manager1", "manager1", "password2", "2000-01-01", 70000, "5678901234", Role.Manager)

        );

        String result = writingToFiles.getNumberOfLibrarians();

        Assertions.assertEquals("1", result);
    }

    @Test
    public void testGetNumberOfLibrarians_LargeList() {

        ObservableList<Person> people = FXCollections.observableArrayList();

        for (int i = 0; i < 100000; i++) {
            people.add(new Librarian("Librarian" + i, "username" + i, "password" + i, "2000-01-01", 50000, "1234567890", Role.Librarian));
        }

        Controller.people = people;

        String result = writingToFiles.getNumberOfLibrarians();

        Assertions.assertEquals("100000", result);
    }

    @Test
    public void test_getNumberOfManagers_ManagersExist() {

        Controller.people = FXCollections.observableArrayList(
                new Manager("Manager1", "username1", "password1", "2000-01-01", 70000, "5678901234", Role.Manager)
        );
        String result = writingToFiles.getNumberOfManagers();
        Assertions.assertEquals("1", result);
    }

    @Test
    public void test_getNumberOfManagers_NoManagers() {

        Controller.people = FXCollections.observableArrayList(
                new Administrator("Admin1", "admin1", "pass1", "2000-01-01", 60000, "9876543210", Role.Administrator)

        );
        String result = writingToFiles.getNumberOfManagers();
        Assertions.assertEquals("0", result);
    }

    @Test
    public void test_getNumberOfManagers_EmptyList() {

        Controller.people = FXCollections.observableArrayList();

        String result = writingToFiles.getNumberOfManagers();


        Assertions.assertEquals("0", result);
    }

    @Test
    public void test_getNumberOfManagers_OneManager() {

        Controller.people = FXCollections.observableArrayList(
                new Manager("Manager1", "username1", "password1", "2000-01-01", 70000, "5678901234", Role.Manager)
        );

        String result = writingToFiles.getNumberOfManagers();

        Assertions.assertEquals("1", result);
    }

    @Test
    public void test_getNumberOfManagers_MixedRoles() {

        Controller.people = FXCollections.observableArrayList(
                new Manager("Manager1", "username1", "password1", "2000-01-01", 70000, "5678901234", Role.Manager),
                new Administrator("Admin1", "admin1", "pass1", "2000-01-01", 60000, "9876543210", Role.Administrator)

        );

        String result = writingToFiles.getNumberOfManagers();

        Assertions.assertEquals("1", result);
    }


    @Test
    public void testGetNumberOfManagers_LargeList() {

        ObservableList<Person> people = FXCollections.observableArrayList();

        for (int i = 0; i < 100000; i++) {
            people.add(new Manager("Manager" + i, "username" + i, "password" + i, "2000-01-01", 50000, "1234567890", Role.Manager));
        }

        Controller.people = people;

        String result = writingToFiles.getNumberOfManagers();

        Assertions.assertEquals("100000", result);
    }


    @Test
    public void test_getNumberOfManagers_NullList() {

        Controller.people = null;
        Assertions.assertThrows(NullPointerException.class, writingToFiles::getNumberOfManagers);
    }

}
