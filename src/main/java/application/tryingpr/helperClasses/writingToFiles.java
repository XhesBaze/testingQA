package application.tryingpr.helperClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import application.tryingpr.Controllers.Controller;
import application.tryingpr.Models.*;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;

public class writingToFiles {

    private static final Logger logger = LoggerFactory.getLogger(writingToFiles.class);

    //Method that is used to read the information about the person logging in.
    //Based on their username and password(which will be saved in a file)
    //The program decides which credentials in the text the user's input matches with
    //In the roles.txt file,the role of the person is stored as the third element
    //The method will return data[2] for this reason.

    public static String readCredentials(String username, String password) {
        // Maximum length of username and password is set to 50 characters
        if (username != null && username.length() > 50) {
            throw new IllegalArgumentException("Username exceeds maximum length of 50 characters");
        }

        if (password != null && password.length() > 50) {
            throw new IllegalArgumentException("Password exceeds maximum length of 50 characters");
        }

        // Rest of the method remains unchanged
        // Create a file object for the roles file
        File file = new File("res/ROLES.txt");

        try {
            // Create a Scanner object to read the file
            Scanner scanner = new Scanner(file);
            // Read each line of the file
            while (scanner.hasNext()) {
                // Split the line into an array using the comma as a separator
                String[] data = scanner.nextLine().split(",");
                // Check if the username and password match the values in the array
                if (data[0].equalsIgnoreCase(username) && data[1].equalsIgnoreCase(password)) {
                    // Return the role if the match is found
                    return data[2];
                }
            }
            // Return null if no match is found
            return null;
        } catch (FileNotFoundException fileNotFoundEx) {
            // Return null if an exception is thrown while reading the file
            throw new RuntimeException("File not found", fileNotFoundEx);
        }
    }

    public static boolean checkFileContent(String filePath, String expectedContent){
        try {
            String actual = Files.readString(Path.of(filePath));
            return actual.equals(expectedContent);

        } catch (IOException e) {
            String exceptionMessage = "An error occurred: " + e.getMessage();
            logger.error(() -> exceptionMessage);
           return false;
        }
    }

    public static <T> List<String> serializeObjects(ObservableList<T> objects, Function<T,String> serializer){
        return objects.stream().map(serializer).toList();
    }


    public static void writeRoles() {
        File file = new File("res/ROLES.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("admin,admin,Administrator");

            for (Person person : Controller.people) {
                writer.write("\n" + person.getUserName() + "," + person.getPassword() + "," + person.getRole().toString());
            }

            writer.flush();
        } catch (IOException exception) {
            throw new RuntimeException("An error occurred while writing roles", exception);
        }
    }


    //Method used to write books in the books.txt file
    public static ObservableList<Book> getBooks(){
        // Create an ObservableList to store the books
        ObservableList<Book> books = FXCollections.observableArrayList();
        // Define the file location for the books data
        File file = new File("res/BOOKS.txt");
        try {
            // Check if the file has been created
            if (file.exists()){
                // Create a Scanner object to read the file
                Scanner scanner = new Scanner(file);
                // Read the file line by line
                while (scanner.hasNextLine()){
                    // Split the line into data fields
                    String[] Data = scanner.nextLine().split(",");
                    // Create a new book object using the data fields
                    books.add(new Book(Data[0],Data[1],Double.parseDouble(Data[2]),Double.parseDouble(Data[3]),Double.parseDouble(Data[4]),Data[5],Data[6],Data[7],Integer.parseInt(Data[8]), LocalDate.parse(Data[9])));
                }
            }
        } catch (IOException e) {
            // Throw a runtime exception if there is an error reading the file
            throw new RuntimeException(e);
        }
        // Return the list of books
        return books;
    }


    public static ObservableList<Person> getPersons() {
        ObservableList<Person> people = FXCollections.observableArrayList();
        File file = new File("res/PERSONS.txt");

        try {
            if (!file.exists()) {
                throw new FileNotFoundException("File not found: " + file.getAbsolutePath());
            }

            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    String[] data = line.split(",");
                    if (data.length >= 7) {
                        if (data[6].equalsIgnoreCase("Librarian")) {
                            people.add(new Librarian(data[0], data[4], data[5], data[1],
                                    Integer.parseInt(data[3]), data[2], Role.Librarian, (data.length >= 8) ? Double.parseDouble(data[7]) : 0.0));
                        } else if (data[6].equalsIgnoreCase("Manager")) {
                            people.add(new Manager(data[0], data[4], data[5], data[1],
                                    Integer.parseInt(data[3]), data[2], Role.Manager));
                        } else if (data[6].equalsIgnoreCase("Administrator")) {
                            people.add(new Administrator(data[0], data[4], data[5], data[1],
                                    Integer.parseInt(data[3]), data[2], Role.Administrator));
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + file.getAbsolutePath(), e);
        }

        return people;
    }



    public static String getNumberOfLibrarians(){
        // retrieve list of people from the BooksPersonsController
        ObservableList<Person> people = Controller.people;
        int numberOfLibrarians = 0;
        // iterate over each person in the list
        for (Person person: people) {
            // check if the person is an instance of Librarian
            if (person instanceof Librarian){
                numberOfLibrarians++;
            }
        }
        // return the number of Librarians as a string
        return String.valueOf(numberOfLibrarians);
    }

    public static String getNumberOfManagers(){
        // retrieve list of people from the BooksPersonsController
        ObservableList<Person> people = Controller.people;
        int numberOfManagers = 0;
        // iterate over each person in the list
        for (Person person: people) {
            // check if the person is an instance of Manager
            if (person instanceof Manager){
                numberOfManagers++;
            }
        }
        // return the number of Managers as a string
        return String.valueOf(numberOfManagers);
    }

    public static int getNumberOfBills(){
        File file = new File("res/Bills");
        File testing_file = new File("res/TestingBills");
        if (file.exists() ) {
             return Objects.requireNonNull(file.listFiles()).length;
        }
        if (testing_file.exists() ) {
            return Objects.requireNonNull(testing_file.listFiles()).length;
        }
        return 0;
    }

    public static void writeBill(String billId, double totalBill, ObservableList<Book> books, FileWriterFactory fileWriterFactoryMock){
        // Create a File object representing the bill file
        File file = new File("res/Bills/" + billId + ".txt");
        // Get the current date and time
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        if ("".equals(billId)) {
            throw new IllegalArgumentException("Bill ID cannot be null");
        }

        if(totalBill < 0){
            throw new IllegalArgumentException("Total cannot be less than zero");
        }

        if (books == null) {
            throw new IllegalArgumentException("Books cannot be null");
        }

        try {
            // Create a new file
            boolean fileCreated = file.createNewFile();
            if(fileCreated){
                FileWriter writer = FileWriterFactory.create(file);
                // Write the bill header information
                writer.write("Bill Id: " + billId);
                writer.write("\nDate: " + dateFormat.format(calendar.getTime()));
                // Write the books included in the bill
                int i = 0;
                for (Book book: books) {
                    writer.write("\n" + ++i + ": " + book.toStringBill());
                }
                // Write the total bill amount
                writer.write("\nTotal: " + totalBill);
                // Close the FileWriter
                writer.close();
            }

        } catch (IOException e) {
            // Throw a runtime exception if an IOException occurs
            throw new RuntimeException(e);
        }
    }

    public static double getTotalBill(){
        File file = new File("res/totalBill1.bin"); // Create a file object with the file path "res/totalBill.bin"
        if (file.exists()) { // Check if the file exists
            try (FileInputStream fis = new FileInputStream(file); // Create a FileInputStream object
                 DataInputStream dis = new DataInputStream(fis)) { // Wrap the FileInputStream object with a DataInputStream object
                return dis.readDouble(); // Read the double value from the DataInputStream object
            } catch (IOException e) { // Catch the IOException in case of any error
                return 0; // Return 0 if an error occurs
            }
        }
        return 0; // Return 0 if the file does not exist
    }

    public static double getTotalCost(){

        File file = new File("res/totalCost1.bin"); // Create a file object with the file path "res/totalCost.bin"
        if (file.exists()) { // Check if the file exists
            try (FileInputStream fis = new FileInputStream(file); // Create a FileInputStream object
                 DataInputStream dis = new DataInputStream(fis)) { // Wrap the FileInputStream object with a DataInputStream object
                return dis.readDouble(); // Read the double value from the DataInputStream object
            } catch (IOException e) { // Catch the IOException in case of any error
                return 0; // Return 0 if an error occurs
            }
        }
        return 0;
    }


    public static int getBooksSold(){
        File file = new File("res/booksSold1.bin");
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file);
                 DataInputStream dis = new DataInputStream(fis)) {
                return dis.readInt();
            } catch (IOException e) {
                return 0;
            }
        }
        return 0;
    }


    public static void writeBooks() {


        // Create the file "res/books.txt"
        File file = new File("res/BOOKS.txt");
        try {
            if (!file.exists()) {
                boolean fileCreated = file.createNewFile();
                if (!fileCreated) {
                    throw new IOException("Failed to create the file.");
                }
            }

            // Open the FileWriter outside the if block to avoid scope issues
            try (FileWriter writer = new FileWriter(file)) {
                int booksWritten = 0;
                // Write each book's information to the file
                for (Book book : Controller.books) {
                    if (booksWritten >= Book.MAX_NUM_OF_BOOKS) {
                        throw new RuntimeException("Maximum number of books exceeded.");
                    }
                    writer.write(book.toString() + "\n");
                    booksWritten++;
                }
            }

        } catch (IOException e) {
            // In case of IOException, throw a new RuntimeException with the caught exception
            throw new RuntimeException(e);
        }
    }


    public static void writePersons() {


        // Create the file "res/books.txt"
        File file = new File("res/PEOPLE.txt");
        try {
            if (!file.exists()) {
                boolean fileCreated = file.createNewFile();
                if (!fileCreated) {
                    throw new IOException("Failed to create the file.");
                }
            }

            // Open the FileWriter outside the if block to avoid scope issues
            try (FileWriter writer = new FileWriter(file)) {
                int peopleWritten = 0;
                // Write each book's information to the file
                for (Person person : Controller.people) {
                    if (peopleWritten >= Person.MAX_NUM_OF_PERSONS) {
                        throw new RuntimeException("Maximum number of people exceeded.");
                    }
                    writer.write(person.toString() + "\n");
                    peopleWritten++;
                }
            }

        } catch (IOException e) {
            // In case of IOException, throw a new RuntimeException with the caught exception
            throw new RuntimeException(e);
        }
    }

    public static void writeTotalBill(double total) {
        File file = new File("res/totalBill1.bin");
        try {
            if (!file.exists()) {
                boolean fileCreated = file.createNewFile();
                if (!fileCreated) {
                    throw new IOException("Failed to create the file.");
                }
            }

            FileOutputStream fos = new FileOutputStream(file);
            DataOutputStream dos = new DataOutputStream(fos);
            dos.writeDouble(total);
            dos.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeTotalCost(double total) {
        File file = new File("res/totalCost1.bin");
        try {
            if (!file.exists()) {
                boolean fileCreated = file.createNewFile();
                if (!fileCreated) {
                    throw new IOException("Failed to create the file.");
                }
            }

            FileOutputStream fos = new FileOutputStream(file);
            DataOutputStream dos = new DataOutputStream(fos);
            dos.writeDouble(total);
            dos.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeBooksSold(int quantity) {
        File file = new File("res/booksSold1.bin");
        try {
            boolean fileCreated = file.createNewFile();
            if (fileCreated) {
                FileOutputStream fos = new FileOutputStream(file);
                DataOutputStream dos = new DataOutputStream(fos);
                dos.writeInt(quantity);
                dos.close();
                fos.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

