package com.example.projectdevops.services;



import com.example.projectdevops.exceptions.ResourceNotFoundException;
import com.example.projectdevops.exceptions.ResourceNotFoundException;
import com.example.projectdevops.models.Author;
import com.example.projectdevops.models.Student;
import com.example.projectdevops.repositories.AuthorRepository;
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
public class AuthorServiceTest {

    /**
     * Autowire in the service we want to test
     */
    @Autowired
    private AuthorService service;

    /**
     * Create a mock implementation of the OwnerRepository
     */
    @MockBean
    private AuthorRepository repository;


    @Test
    @DisplayName("Test findAuthorById Success")
    void testFindById() {
        // Set up our mock repository
        Author author = new Author("Hana","Ben Asker","hola hola hola");
        doReturn(Optional.of(author)).when(repository).findById(1l);

        // Execute the service call
        Author returnedAuthor = service.getAuthorById(1l);

        // Assert the response
        Assertions.assertSame(returnedAuthor, author, "The author returned was not the same as the mock");
    }

    @Test
    @DisplayName("Test findAuthorById Not Found")
    void testFindByIdNotFound() {

        doReturn(Optional.empty()).when(repository).findById(1l);

        Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            Author returnedAuthor = service.getAuthorById(1l);
        });
        String expectedMessage = "Not author matches.";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    @DisplayName("Test getAllAuthors")
    void testGetAllOwners() {

        // Set up our mock repository
        Author author1 = new Author("Hana","Ben Asker","hola hola hola");
        Author author2 = new Author("Ranim","Said","engineering student");
        doReturn(Arrays.asList(author1, author2)).when(repository).findAll();

        // Execute the service call
        List<Author> authors = service.getAllAuthors();

        // Assert the response
        Assertions.assertEquals(2, authors.size(), "findAll should return 2 authors");
    }


    @Test
    @DisplayName("Test createAuthor")
    void testCreateStudent() {

        Author authorToSave = new Author("Hana", "Ben Asker","hola hola hola");
        Author author = new Author(1l,"Hana", "Ben Asker","hola hola hola");
        doReturn(author).when(repository).save(authorToSave);

        // Execute the service call
        Author returnedAuthor = service.createAuthor(authorToSave);

        // Assert the response
        Assertions.assertNotNull(returnedAuthor, "The saved student should not be null");
        Assertions.assertSame(author, returnedAuthor, "The returned student is not the same as expected");
    }
}
