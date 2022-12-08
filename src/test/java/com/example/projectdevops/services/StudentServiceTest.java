package com.example.projectdevops.services;



import com.example.projectdevops.exceptions.ResourceNotFoundException;
import com.example.projectdevops.exceptions.ResourceNotFoundException;
import com.example.projectdevops.models.Student;
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
public class StudentServiceTest {

    /**
     * Autowire in the service we want to test
     */
    @Autowired
    private StudentService service;

    /**
     * Create a mock implementation of the OwnerRepository
     */
    @MockBean
    private StudentRepository repository;


    @Test
    @DisplayName("Test findStudentById Success")
    void testFindById() {
        // Set up our mock repository
        Student student = new Student("Ranim","Said","1999-05-23","00041558");
        doReturn(Optional.of(student)).when(repository).findById(1l);

        // Execute the service call
        Student returnedStudent = service.getStudentById(1l);

        // Assert the response
        Assertions.assertSame(returnedStudent, student, "The student returned was not the same as the mock");
    }

    @Test
    @DisplayName("Test findStudentById Not Found")
    void testFindByIdNotFound() {

        doReturn(Optional.empty()).when(repository).findById(1l);

        Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            Student returnedStudent = service.getStudentById(1l);
        });
        String expectedMessage = "Not student matches.";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    @DisplayName("Test getAllStudents")
    void testGetAllOwners() {

        // Set up our mock repository
        Student student1 = new Student("Hana","Ben Asker","1999-04-22","11419727");
        Student student2 = new Student("Ranim","Said","1999-05-23","00041558");
        doReturn(Arrays.asList(student1, student2)).when(repository).findAll();

        // Execute the service call
        List<Student> students = service.getAllStudents();

        // Assert the response
        Assertions.assertEquals(2, students.size(), "findAll should return 2 students");
    }


    @Test
    @DisplayName("Test createStudent")
    void testCreateStudent() {

        Student studentToSave = new Student("Hana","Ben Asker","1999-04-22","11419727");
        Student student = new Student(1l,"Hana","Ben Asker","1999-04-22","11419727");
        doReturn(student).when(repository).save(studentToSave);

        // Execute the service call
        Student returnedStudent = service.createStudent(studentToSave);

        // Assert the response
        Assertions.assertNotNull(returnedStudent, "The saved student should not be null");
        Assertions.assertSame(student, returnedStudent, "The returned student is not the same as expected");
    }
}
