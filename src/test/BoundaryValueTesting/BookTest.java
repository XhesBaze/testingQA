package BoundaryValueTesting;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import application.tryingpr.Models.Book;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    String dateString = "2023-11-14";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate = LocalDate.parse(dateString, formatter);

    @Test
    void test_Constructor3(){
        Book book = new Book("1234", "title", 20.0, 19.0, 23.0, "author", "drama", "supplier", 100, localDate );

        assertEquals("1234", book.getIsbn());
        assertEquals("title", book.getTitle());
        assertEquals(20.0, book.getPurchasePrice());
        assertEquals(23.0, book.getSellPrice());
        assertEquals("drama", book.getCategory());
        assertEquals(100, book.getStock());
        assertEquals(19.0, book.getOriginalPrice());
        assertEquals("author", book.getAuthor());
        assertEquals("supplier", book.getSupplier());
        assertEquals(localDate, book.getPurchaseDate());
    }

    //isbn
    @Test
    public void testGetBIsbn() {
        Book book = new Book();
        book.setIsbn("1234");
        assertEquals(book.getIsbn(), "1234");
    }

    @Test
    public void testSetIsbn() {
        Book book = new Book();
        book.setIsbn("1234");
        assertEquals(book.getIsbn(), "1234");
    }

    //title
    @Test
    public void testGetTitle() {
        Book book = new Book();
        book.setTitle("title");
        assertEquals(book.getTitle(), "title");
    }

    @Test
    public void testSetTitle() {
        Book book = new Book();
        book.setTitle("title");
        assertEquals(book.getTitle(), "title");
    }


    //purchase price
    @Test
    public void testGetPurchasePrice() {
        Book book = new Book();
        book.setPurchasePrice(20.0);
        assertEquals(book.getPurchasePrice(),20.0 );
    }

    @Test
    public void testSetPurchasePrice() {
        Book book = new Book();
        book.setPurchasePrice(20.0);
        assertEquals(book.getPurchasePrice(), 20.0);
    }

    //original price
    @Test
    public void testGetOriginalPrice() {
        Book book = new Book();
        book.setOriginalPrice(19.0);
        assertEquals(book.getOriginalPrice(), 19.0);
    }

    @Test
    public void testSetOriginalPrice() {
        Book book = new Book();
        book.setOriginalPrice(19.0);
        assertEquals(book.getOriginalPrice(), 19.0);
    }

    //sell price
    @Test
    public void testGetSellPrice() {
        Book book = new Book();
        book.setSellPrice(23.0);
        assertEquals(book.getSellPrice(),23.0 );
    }

    @Test
    public void testSetSellPrice() {
        Book book = new Book();
        book.setSellPrice(23.0);
        assertEquals(book.getSellPrice(),23.0 );
    }

    //author
    @Test
    public void testGetAuthor() {
        Book book = new Book();
        book.setAuthor("author");
        assertEquals(book.getAuthor(), "author");
    }

    @Test
    public void testSetAuthor() {
        Book book = new Book();
        book.setAuthor("author");
        assertEquals(book.getAuthor(), "author");
    }

    //category
    @Test
    public void testGetCategory() {
        Book book = new Book();
        book.setCategory("drama");
        assertEquals(book.getCategory(), "drama");
    }

    @Test
    public void testSetCategory() {
        Book book = new Book();
        book.setCategory("drama");
        assertEquals(book.getCategory(),"drama" );
    }

    //supplier
    @Test
    public void testGetSupplier() {
        Book book = new Book();
        book.setSupplier("supplier");
        assertEquals(book.getSupplier(), "supplier");
    }

    @Test
    public void testSetSupplier() {
        Book book = new Book();
        book.setSupplier("supplier");
        assertEquals(book.getSupplier(),"supplier" );
    }

    //stock
    @Test
    public void testGetStock() {
        Book book = new Book();
        book.setStock(100);
        assertEquals(book.getStock(), 100);
    }

    @Test
    public void testSetStock() {
        Book book = new Book();
        book.setStock(100);
        assertEquals(book.getStock(),100);
    }

    @Test
    public void testGetPurchaseDate() {
        Book book = new Book();
        book.setPurchaseDate(localDate);
        assertEquals(book.getPurchaseDate(), localDate);
    }

    @Test
    public void testSetPurchaseDate() {
        Book book = new Book();
        book.setPurchaseDate(localDate);
        assertEquals(book.getPurchaseDate(),localDate);
    }

    @Test
    public void testToString() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setTitle("title");
        book.setPurchasePrice(20.0);
        book.setOriginalPrice(19.0);
        book.setSellPrice(23.0);
        book.setAuthor("author");
        book.setCategory("drama");
        book.setSupplier("supplier");
        book.setStock(100);
        book.setPurchaseDate(localDate);

        String expected = "1234,title,20.0,19.0,23.0,author,drama,supplier,100," + book.getPurchaseDate().toString();
        assertEquals(expected, book.toString());
    }

    @Test
    public void testToStringBill() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setTitle("title");
        book.setAuthor("author");

        String expected = "ISBN: 1234 ,Title: title ,Author: author";
        assertEquals(expected, book.toStringBill());
    }

}