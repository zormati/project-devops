package com.example.projectdevops.services;



import com.example.projectdevops.exceptions.ResourceNotFoundException;
import com.example.projectdevops.exceptions.ResourceNotFoundException;
import com.example.projectdevops.models.Book;
import com.example.projectdevops.models.Student;
import com.example.projectdevops.repositories.BookRepository;
import com.example.projectdevops.repositories.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class BookServiceTest {

    /**
     * Autowire in the service we want to test
     */
    @Autowired
    private BookService service;

    /**
     * Create a mock implementation of the OwnerRepository
     */
    @MockBean
    private BookRepository repository;


    @Test
    @DisplayName("Test findBookById Success")
    void testFindById() {
        // Set up our mock repository
        Book book = new Book("hola","romance","sameplot");
        doReturn(Optional.of(book)).when(repository).findById(1l);

        // Execute the service call
        Book returnedBook = service.getBookById(1l);

        // Assert the response
        Assertions.assertSame(returnedBook, book, "The book returned was not the same as the mock");
    }

    @Test
    @DisplayName("Test findBookById Not Found")
    void testFindByIdNotFound() {

        doReturn(Optional.empty()).when(repository).findById(1l);

        Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            Book returnedBook= service.getBookById(1l);
        });
        String expectedMessage = "Not book matches.";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    @DisplayName("Test getAllBooks")
    void testGetAllOwners() {

        // Set up our mock repository
        Book book1 = new Book("bla","comedy","hahahah");
        Book book2 = new Book("hola","romance","sameplot");
        doReturn(Arrays.asList(book1, book2)).when(repository).findAll();

        // Execute the service call
        List<Book> books = service.getAllBooks();

        // Assert the response
        Assertions.assertEquals(2, books.size(), "findAll should return 2 books");
    }


    @Test
    @DisplayName("Test createBook")
    void testCreateBook() {

        Book bookToSave = new Book("hola","romance","sameplot");
        Book book = new Book(1l,"hola","romance","sameplot");
        doReturn(book).when(repository).save(bookToSave);

        // Execute the service call
        Book returnedBook = service.createBook(book);

        // Assert the response
        //Assertions.assertNotNull(returnedBook, "The saved book should not be null");
        //Assertions.assertSame(book, returnedBook, "The returned book is not the same as expected");
    }
}

