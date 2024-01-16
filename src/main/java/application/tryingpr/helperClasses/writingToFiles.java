package application.tryingpr.helperClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import application.tryingpr.Controllers.Controller;
import application.tryingpr.Models.*;

import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.logging.Level;

public class writingToFiles {

    //Method that is used to read the information about the person logging in.
    //Based on their username and password(which will be saved in a file)
    //The program decides which credentials in the text the user's input matches with
    //In the roles.txt file,the role of the person is stored as the third element
    //The method will return data[2] for this reason.

    private static final Logger logger = Logger.getLogger(writingToFiles.class.getName());

    public static String readCredentials(String username, String password,  String filePath) {
        // Maximum length of username and password is set to 50 characters
        if (username != null && username.length() > 50) {
            throw new IllegalArgumentException("Username exceeds maximum length of 50 characters");
        }

        if (password != null && password.length() > 50) {
            throw new IllegalArgumentException("Password exceeds maximum length of 50 characters");
        }

        // Rest of the method remains unchanged
        // Create a file object for the roles file
        File file = new File(filePath);

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
            return null;
        } catch (FileNotFoundException fileNotFoundEx) {
            throw new RuntimeException("File not found", fileNotFoundEx);
        }
    }

    public static boolean checkFileContent(String filePath, String expectedContent){
        try {
            String actual = Files.readString(Path.of(filePath));
            return actual.equals(expectedContent);

        } catch (IOException e) {
            String exceptionMessage = "An error occurred: " + e.getMessage();
            logger.log(Level.SEVERE, () -> exceptionMessage);
            return false;
        }
    }

    public static <T> List<String> serializeObjects(ObservableList<T> objects, Function<T,String> serializer){
        return objects.stream().map(serializer).toList();
    }

    public static void writeRoles(String filePath) throws IOException{
        File file = new File(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("admin,admin,Administrator");

            for (Person person : Controller.people) {
                writer.write("\n" + person.getUserName() + "," + person.getPassword() + "," + person.getRole().toString());
            }

            writer.flush();
        }
    }

    public static ObservableList<Book> getBooks(String filePath){
        ObservableList<Book> books = FXCollections.observableArrayList();

        File file = new File(filePath);

        if (!file.exists()) {
            throw new IllegalArgumentException("File not found: " + filePath);
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                books.add(new Book(data[0], data[1], Double.parseDouble(data[2]),
                        Double.parseDouble(data[3]), Double.parseDouble(data[4]),
                        data[5], data[6], data[7], Integer.parseInt(data[8]),
                        LocalDate.parse(data[9])));
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found - " + filePath);
        }
        return books;
    }

    public static ObservableList<Person> getPersons(String filePath) {
        ObservableList<Person> people = FXCollections.observableArrayList();
        File file = new File(filePath);

        try {
            if (!file.isFile() || !file.canRead()) {
                throw new FileNotFoundException("Invalid or unreadable file: " + file.getAbsolutePath());
            }

            int invalidDataLines = 0;

            Map<String, Role> roleMap = Map.of(
                    "librarian", Role.Librarian,
                    "manager", Role.Manager,
                    "administrator", Role.Administrator
            );

            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] data = line.split(",");

                    if (data.length >= 7 && roleMap.containsKey(data[6].trim().toLowerCase())) {
                        Role role = roleMap.get(data[6].trim().toLowerCase());

                        switch (role) {
                            case Librarian:
                                people.add(new Librarian(data[0], data[4], data[5], data[1],
                                        Integer.parseInt(data[3]), data[2], Role.Librarian, (data.length >= 8) ? Double.parseDouble(data[7]) : 0.0));
                                break;
                            case Manager:
                                people.add(new Manager(data[0], data[4], data[5], data[1],
                                        Integer.parseInt(data[3]), data[2], Role.Manager));
                                break;
                            case Administrator:
                                people.add(new Administrator(data[0], data[4], data[5], data[1],
                                        Integer.parseInt(data[3]), data[2], Role.Administrator));
                                break;
                        }
                    } else {
                        System.out.println("Warning: " + (data.length < 7 ? "Invalid data format" : "Unrecognized role") + " in line: " + line);
                        invalidDataLines++;
                    }
                }
            }

            System.out.println("Skipped " + invalidDataLines + " lines with invalid data.");

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

    public static int getNumberOfBills(String directoryPath){
        File file = new File(directoryPath);
        if (file.exists() ) {
             return Objects.requireNonNull(file.listFiles()).length;
        }
        return 0;
    }

    public static void writeBill(String billId, double totalBill, ObservableList<Book> books, FileWriterFactory fileWriterFactoryMock) {
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

    public static double getTotalBill(String filePath) {
        File file = new File(filePath);

        if (file.exists()) {
            try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
                return dis.readDouble();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error reading file: " + filePath, e);
            }
        }
        return 0;
    }

    public static double getTotalCost(String filePath) {
        File file = new File(filePath);

        if (file.exists()) {
            try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
                return dis.readDouble();
            } catch (IOException e) {
               return 0;
            }
        }
        return 0;
    }


    public static int getBooksSold(String filePath){
        File file = new File(filePath);
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


    public static void writeBooks(String filePath) throws IOException {
        File file = new File(filePath);

        try (FileWriter writer = new FileWriter(file)) {
            int booksWritten = 0;
            for (Book book : Controller.books) {
                if (booksWritten >= Book.MAX_NUM_OF_BOOKS) {
                    throw new RuntimeException("Maximum number of books exceeded.");
                }
                writer.write(book.toString() + "\n");
                booksWritten++;
            }

        }
    }

    public static void writePersons(String filePath) throws IOException {
        File file = new File(filePath);

        try (FileWriter writer = new FileWriter(file)) {
            int peopleWritten = 0;
            for (Person person : Controller.people) {
                if (peopleWritten >= Person.MAX_NUM_OF_PERSONS) {
                    throw new IOException("Maximum number of people exceeded.");
                }
                writer.write(person.toString() + "\n");
                peopleWritten++;
            }

        } catch (IOException e) {
            throw new IOException("Error while writing persons to file", e);
        }
    }


    public static void writeTotalBill(String filePath, double total) throws IOException{
        File file = new File(filePath);
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
            dos.writeDouble(total);
        }
    }

    public static void writeTotalCost(String filePath, double total) throws IOException {
        File file = new File(filePath);
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
            dos.writeDouble(total);
        }
    }

    public static void writeBooksSold(String filePath,int quantity) {
        try {
            File file = new File(filePath);

            OutputStream outputStream = createFileOutputStream(file);

            DataOutputStream dos = new DataOutputStream(outputStream);
            dos.writeInt(quantity);
            dos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static OutputStream createFileOutputStream(File file) throws IOException {
        return new FileOutputStream(file);
    }
}

