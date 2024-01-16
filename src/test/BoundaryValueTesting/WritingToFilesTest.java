package BoundaryValueTesting;

import application.tryingpr.Controllers.Controller;
import application.tryingpr.Models.Librarian;
import application.tryingpr.Models.Manager;
import application.tryingpr.Models.Person;
import application.tryingpr.Models.Book;
import application.tryingpr.helperClasses.Role;
import application.tryingpr.helperClasses.writingToFiles;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Function;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;



import static org.junit.jupiter.api.Assertions.*;

public class WritingToFilesTest {

    @Test
    public void test_readCredentials_EmptyCredentials(){
        assertNull(writingToFiles.readCredentials("", " ","res/ROLES.txt"));
    }

    @Test
    public void test_readCredentials_MinimumLengthCredentials(){
        assertNull(writingToFiles.readCredentials("x","h","res/ROLES.txt"));
    }

    @Test
    public void test_readCredentials_MaximumLengthCredentials(){
        assertNull(writingToFiles.readCredentials("x".repeat(50), "h".repeat(50),"res/ROLES.txt"));

    }

    @Test
    public void test_readCredentials_ExceedingLengthCredentials(){
        assertThrows(IllegalArgumentException.class, () -> writingToFiles.readCredentials("a".repeat(51), "b".repeat(51),"res/ROLES.txt"));
    }

    @Test
    public void test_readCredentials_ValidCredentials(){
        assertEquals("Administrator",writingToFiles.readCredentials("admin", "admin","res/ROLES.txt"));
    }

    @Test
    public void test_readCredentials_InvalidCredentials(){
        assertNull(writingToFiles.readCredentials("admin", "adm","res/ROLES.txt"));
    }

    @Test
    public void test_readCredentials_NullCredentials(){
        assertNull(writingToFiles.readCredentials(null,null,"res/ROLES.txt"));
    }


    @Test
    public void test_writeRoles_singlePerson(){
        Controller.people = FXCollections.observableArrayList(
                new Person("username", "password", Role.Librarian)
        );

        Assertions.assertDoesNotThrow(() -> writingToFiles.writeRoles("res/ROLES.txt"));
        assertTrue(writingToFiles.checkFileContent("res/ROLES.txt", "admin,admin,Administrator\nusername,password,Librarian"));
    }

    @Test
    public void test_writeRoles_multiplePeople(){
        Controller.people = FXCollections.observableArrayList(
                new Person("user1","password1",Role.Manager),
                new Person("user2","password2",Role.Librarian),
                new Person("user3","password3",Role.Administrator)
        );

        Assertions.assertDoesNotThrow(() -> writingToFiles.writeRoles("res/ROLES.txt"));
        assertTrue(writingToFiles.checkFileContent("res/ROLES.txt", """
                admin,admin,Administrator
                user1,password1,Manager
                user2,password2,Librarian
                user3,password3,Administrator"""));
    }


    @Test
    public void test_getBooks_multipleBooks(){

        Controller.books = FXCollections.observableArrayList(
                new Book("ISBN1", "Book1", 15.0, 10.0, 25.0,
                        "Author1", "Category1", "Supplier1", 5, LocalDate.parse("2023-04-05")),
                new Book("ISBN2", "Book2", 16.0, 11.0, 26.0,
                        "Author2", "Category2", "Supplier2", 6, LocalDate.parse("2023-05-05")),
                new Book("ISBN3", "Book3", 17.0, 12.0, 27.0,
                        "Author3", "Category3", "Supplier3", 7, LocalDate.parse("2023-04-06")),
                new Book("ISBN4", "Book4", 18.0, 13.0, 28.0,
                        "Author4", "Category4", "Supplier4", 8, LocalDate.parse("2023-04-11")),
                new Book("ISBN5", "Book5", 19.0, 14.0, 29.0,
                        "Author5", "Category5", "Supplier5", 9, LocalDate.parse("2023-04-05"))
        );

        Function<Book, String> bookSerializer = book -> {

            String purchaseDateStr = (book.getPurchaseDate() != null) ? book.getPurchaseDate().toString() : "null";
            return String.format("%s,%s,%.2f,%.2f,%.2f,%s,%s,%s,%d,%s",
                    book.getIsbn(), book.getTitle(), book.getPurchasePrice(), book.getOriginalPrice(),
                    book.getSellPrice(), book.getAuthor(), book.getCategory(), book.getSupplier(),
                    book.getStock(), purchaseDateStr);
        };

        String path = "res/BOOKS.txt";
        try {
            Files.write(Path.of(path), writingToFiles.serializeObjects(Controller.books, bookSerializer));
        } catch (IOException e) {
            throw new RuntimeException("Cannot write file", e);
        }
    }

    @Test
    public void test_getPersons_singlePerson(){
        Person person = new Person("Name1", "UserName1", "Password1", "1990-01-01", 50000, "123456789", Role.Librarian);

        String path = "res/PERSONS.txt";
        Path path1 = Path.of(path);
        try{
            Files.write(path1, Collections.singletonList(person.toString()));
        }catch(IOException e){
            throw new RuntimeException("Cannot write file", e);
        }

        ObservableList<Person> actualList = writingToFiles.getPersons("res/PERSONS.txt");

        assertEquals(1, actualList.size());
        assertEquals("Name1",actualList.get(0).getName());
        assertEquals("UserName1",actualList.get(0).getUserName());
        assertEquals("Password1",actualList.get(0).getPassword());
        assertEquals("1990-01-01",actualList.get(0).getBirthday());
        assertEquals(50000,actualList.get(0).getSalary());
        assertEquals("123456789",actualList.get(0).getPhone());
        assertEquals(Role.Librarian,actualList.get(0).getRole());

    }

    @Test
    public void test_getPersons_multiplePeople(){

        Controller.people = FXCollections.observableArrayList(
               new Person("Username1","Password1",Role.Administrator),
                new Person("Username2","Password2",Role.Librarian),
                new Person("Username3","Password3",Role.Manager),
                new Person("Username4","Password4",Role.Librarian),
                new Person("Username5","Password5",Role.Administrator)
        );

        Function<Person, String> peopleSerializer = person -> String.format("%s,%s,%s",
                person.getUserName(), person.getPassword(), person.getRole().name());


        String path = "res/PERSONS.txt";
        try {
            Files.write(Path.of(path), writingToFiles.serializeObjects(Controller.people, peopleSerializer));
        } catch (IOException e) {
            throw new RuntimeException("Cannot write file", e);
        }
    }


    @Test
    public void test_getNumberOfLibrarians_noLibrarian(){
        Controller.people = FXCollections.observableArrayList();
        String result = writingToFiles.getNumberOfLibrarians();

        assertEquals("0",result);
    }

    @Test
    public void test_getNumberOfLibrarians_oneLibrarian(){
      Controller.people = FXCollections.observableArrayList(new Librarian("username1","password1",Role.Librarian));
      String result = writingToFiles.getNumberOfLibrarians();

      assertEquals("1", result);
    }

    @Test
    public void test_getNumberOfLibrarians_someLibrarians() {
        Controller.people = FXCollections.observableArrayList(
                new Librarian("username1", "password1", Role.Librarian),
                new Librarian("username2", "password2", Role.Librarian),
                new Librarian("username3", "password3", Role.Librarian),
                new Librarian("username3", "password3", Role.Librarian),
                new Librarian("username3", "password3", Role.Librarian)
        );
        String result = writingToFiles.getNumberOfLibrarians();
        assertEquals("5", result);
    }

    @Test
    public void test_getNumberOfLibrarians_noLibrarian_otherRoles(){
        Controller.people = FXCollections.observableArrayList(
                new Person("username1","password1",Role.Administrator),
                new Person("username2", "password2", Role.Manager),
                new Person("username3","password3",Role.Administrator)
        );

        String result = writingToFiles.getNumberOfLibrarians();
        assertEquals("0",result);
    }

    @Test
    public void test_getNumberOfLibrarians_oneLibrarian_otherRoles() {
        Controller.people = FXCollections.observableArrayList(
                new Librarian("username1", "password1", Role.Librarian),
                new Person("username2", "password2", Role.Manager),
                new Person("username3", "password3", Role.Administrator)
        );

        String result = writingToFiles.getNumberOfLibrarians();
        assertEquals("1", result);
    }

    @Test
    public void test_getNumberOfLibrarians_someLibrarians_otherRoles() {
        Controller.people = FXCollections.observableArrayList(
                new Librarian("username1", "password1", Role.Librarian),
                new Librarian("username2", "password2", Role.Librarian),
                new Librarian("username3", "password3", Role.Librarian),
                new Person("username4", "password4", Role.Manager),
                new Person("username5", "password5", Role.Administrator)
        );

        String result = writingToFiles.getNumberOfLibrarians();
        assertEquals("3", result);
    }


    @Test
    public void test_getNumberOfManagers_noManager(){
        Controller.people = FXCollections.observableArrayList();
        String result = writingToFiles.getNumberOfManagers();

        assertEquals("0",result);
    }

    @Test
    public void test_getNumberOfManagers_oneManager(){
        Controller.people = FXCollections.observableArrayList(new Manager("username1","password1",Role.Manager));
        String result = writingToFiles.getNumberOfManagers();

        assertEquals("1", result);
    }

    @Test
    public void test_getNumberOfManagers_someManagers() {
        Controller.people = FXCollections.observableArrayList(
                new Manager("username1", "password1", Role.Manager),
                new Manager("username2", "password2", Role.Manager),
                new Manager("username3", "password3", Role.Manager),
                new Manager("username3", "password3", Role.Manager),
                new Manager("username3", "password3", Role.Manager)
        );
        String result = writingToFiles.getNumberOfManagers();
        assertEquals("5", result);
    }

    @Test
    public void test_getNumberOfManagers_noManager_otherRoles(){
        Controller.people = FXCollections.observableArrayList(
                new Person("username1","password1",Role.Administrator),
                new Person("username2", "password2", Role.Librarian),
                new Person("username3","password3",Role.Administrator)
        );

        String result = writingToFiles.getNumberOfManagers();
        assertEquals("0",result);
    }

    @Test
    public void test_getNumberOfManagers_oneManager_otherRoles() {
        Controller.people = FXCollections.observableArrayList(
                new Manager("username1", "password1", Role.Manager),
                new Person("username2", "password2", Role.Librarian),
                new Person("username3", "password3", Role.Administrator)
        );

        String result = writingToFiles.getNumberOfManagers();
        assertEquals("1", result);
    }

    @Test
    public void test_getNumberOfManagers_someManagers_otherRoles() {
        Controller.people = FXCollections.observableArrayList(
                new Manager("username1", "password1", Role.Manager),
                new Manager("username2", "password2", Role.Manager),
                new Manager("username3", "password3", Role.Manager),
                new Person("username4", "password4", Role.Librarian),
                new Person("username5", "password5", Role.Administrator)
        );

        String result = writingToFiles.getNumberOfManagers();
        assertEquals("3", result);
    }

    @Test
    public void test_getNumberOfBills_folderWithFiles() throws IOException {
        File billsFolder = new File("res/Bills");
        assertTrue(billsFolder.exists());

        File file1 = new File(billsFolder, "testing_bill.txt");
        File file2 = new File(billsFolder, "testing_bill12.txt");

        assertTrue(file1.createNewFile());
        assertTrue(file2.createNewFile());

        int result = writingToFiles.getNumberOfBills("res/Bills");


        int expected = Objects.requireNonNull(billsFolder.listFiles()).length;

        assertEquals(expected, result);
    }

    @AfterEach
    public void cleanup_testingBills() {
        File billsFolder = new File("res/Bills");
        File file1 = new File(billsFolder, "testing_bill.txt");
        File file2 = new File(billsFolder, "testing_bill12.txt");

        if (file1.exists()) {
            if (!file1.delete()) {
                System.err.println("Failed to delete file: " + file1.getAbsolutePath());
            }
        }

        if (file2.exists()) {
            if (!file2.delete()) {
                System.err.println("Failed to delete file: " + file2.getAbsolutePath());
            }
        }
    }

    @Test
    public void test_writeBill_emptyList(){
        assertDoesNotThrow(()-> writingToFiles.writeBill("111",25.0,FXCollections.observableArrayList(),null));
    }

    @Test
    public void test_writeBill_emptyBillId() {
        assertThrows(RuntimeException.class, () -> writingToFiles.writeBill("", 50.0, FXCollections.observableArrayList(), null));
    }

    @Test
    public void test_writeBill_zeroTotalBill(){
        assertDoesNotThrow(() -> writingToFiles.writeBill("456", 0.0, FXCollections.observableArrayList(), null));
    }

    @Test
    public void test_writeBill_negativeTotalBill() {
        assertThrows(RuntimeException.class, () ->
                        writingToFiles.writeBill("789", -10.0, FXCollections.observableArrayList(), null),
                "Expected to throw RuntimeException for negative totalBill");
    }

    @Test
    public void test_writeBill_emptyBookList() {
        assertDoesNotThrow(() -> writingToFiles.writeBill("123", 50.0, FXCollections.observableArrayList(), null));
    }

    @Test
    public void test_writeBill_maxBillIdLength() {
        String maxBillId = String.join("", Collections.nCopies(25, "X"));
        assertDoesNotThrow(() -> writingToFiles.writeBill(maxBillId, 50.0, FXCollections.observableArrayList(), null));
    }

    @Test
    public void test_writeBill_maxNumBooks() {
        ObservableList<Book> maxBooks = FXCollections.observableArrayList();
        for (int i = 0; i < 250; i++) {
            maxBooks.add(new Book("Book" + i));
        }
        assertDoesNotThrow(() -> writingToFiles.writeBill("101", 100.0, maxBooks, null));
    }

    @Test
    public void test_writeBill_nullBookList() {
        assertThrows(IllegalArgumentException.class, () ->
                        writingToFiles.writeBill("303", 80.0, null, null),
                "Expected to throw IllegalArgumentException for null book list");
    }

    @BeforeEach
    public void setUp_writeBooks(){
        Controller.books.clear();
}
    @Test
    public void test_writeBooks_noBooks(){
        try {
            writingToFiles.writeBooks("res/bookInfo.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test_writeBooks_oneBook(){
        Controller.books.add(new Book("ISBN1"," TITLE1", 25.0, 20.0,30.0,"AUTHOR1","CATEGORY1","SUPPLIER1",10,LocalDate.parse("2022-03-03")));

        try{
            writingToFiles.writeBooks("res/bookInfo.txt");
        }catch(Exception e){
            fail("Exception: "+e.getMessage());
        }

        File file = new File("res/BOOKS.txt");
        assertTrue(file.exists());
    }

    @Test
    public void test_writeBooks_maxNumOfBooks(){
        for(int i = 0; i<Book.MAX_NUM_OF_BOOKS; i++){
            Controller.books.add(new Book("ISBN"," TITLE", 25.0, 20.0,30.0,"AUTHOR","CATEGORY","SUPPLIER",10,LocalDate.parse("2022-03-03")));
        }

        try{
            writingToFiles.writeBooks("res/bookInfo.txt");
        }catch(Exception e){
            fail("Exception:"+ e.getMessage());
        }
    }

    @Test
    public void test_writeBooks_exceedingNumOfBooks() {

        for (int i = 0; i < Book.MAX_NUM_OF_BOOKS + 1; i++) {
            Controller.books.add(new Book("ISBN", "TITLE", 25.0, 20.0, 30.0, "AUTHOR", "CATEGORY", "SUPPLIER", 10, LocalDate.parse("2022-03-03")));
        }

        assertThrows(RuntimeException.class, () -> writingToFiles.writeBooks("res/bookInfo.txt"));
    }

    @BeforeEach
    public void setUp_writePersons(){
        Controller.people.clear();
    }

    @Test
    public void test_writePersons_noPerson(){
        try {
            writingToFiles.writePersons("res/PEOPLE1.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File file = new File("res/PEOPLE1.txt");
        assertTrue(file.exists());
        assertEquals(0, file.length());
    }

    @Test
    public void test_writePersons_onePerson(){
        Controller.people.add(new Person("Xhesi","Baze",Role.Administrator));

        try{
            writingToFiles.writePersons("res/PEOPLE1.txt");
        }catch(Exception e){
            fail("Exception: "+e.getMessage());
        }
    }

    @Test
    public void test_writePersons_biggestNumberOfPeople(){
        for(int i =0; i<Person.MAX_NUM_OF_PERSONS; i++){
            Controller.people.add(new Person("Username","Password",Role.Administrator));
        }

        try{
            writingToFiles.writePersons("res/PEOPLE1.txt");
        }catch(Exception e){
            fail("Exception:"+e.getMessage());
        }

    }

    @Test
    public void test_writeBooks_exceedingNumOfPeople() {

        for (int i = 0; i < Person.MAX_NUM_OF_PERSONS + 1; i++) {
            Controller.people.add(new Person("Username","Password",Role.Librarian));
        }

        String testFilePath = "res/PEOPLE1.txt";

        assertThrows(IOException.class, () -> writingToFiles.writePersons(testFilePath));
    }

    @Test
    public void test_writeTotalBill_zeroBill(){
        double total = 0.0;

        try{
            writingToFiles.writeTotalBill("res/totalBill1.bin",total);
        }catch(Exception e){
            fail("Exception: " + e.getMessage());
        }

    }

    @Test
    public void test_writeTotalBill_largeAmountBill(){
        double total = 50000;

        try{
            writingToFiles.writeTotalBill("res/totalBill1.bin",total);
        }catch(Exception e){
            fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void test_writeTotalCost_zeroCost(){
        double total = 0.0;

        try{
            writingToFiles.writeTotalCost("res/totalCost1.bin",total);
        }catch(Exception e){
            fail("Exception: " + e.getMessage());
        }

    }

    @Test
    public void test_writeTotalCost_largeAmountCost(){
        double total = 50000;

        try{
            writingToFiles.writeTotalCost("res/totalCost1.bin",total);
        }catch(Exception e){
            fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testWriteBooksSold_MinimumQuantity() {

        int quantity = 1;

        try {
            writingToFiles.writeBooksSold("res/booksSold1.bin",quantity);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }

    }


    @Test
    public void testWriteBooksSold_ZeroQuantity() {
        int quantity = 0;

        try {
            writingToFiles.writeBooksSold("res/booksSold1.bin",quantity);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }

    }

    @Test
    public void testWriteBooksSold_PositiveQuantity() {

        int quantity = 100;

        try {
            writingToFiles.writeBooksSold("res/booksSold1.bin",quantity);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }

    }

}






