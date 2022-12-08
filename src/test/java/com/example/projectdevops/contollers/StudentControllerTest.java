package com.example.projectdevops.contollers;

import com.example.projectdevops.models.Student;
import com.example.projectdevops.services.StudentService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import static com.example.projectdevops.utils.objectMapper.asJsonString;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc

public class StudentControllerTest {

    @MockBean
    private StudentService service;

    @Autowired
    private MockMvc mockMvc;

    @Test

    void testGetStudentsSucces() throws Exception{
        Student student1 = new Student("Hana","Ben Asker","1999-04-22","11419727");
        Student student2 = new Student("Ranim","Said","1999-05-23","00041558");
        doReturn(Lists.newArrayList(student1,student2)).when(service).getAllStudents();

        mockMvc.perform(get("/student/all"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].firstName",is("Hana")))
                .andExpect(jsonPath("$[0].lastName",is("Ben Asker")))
                .andExpect(jsonPath("$[0].bornAt",is("1999-04-22")))
                .andExpect(jsonPath("$[0].cin",is("11419727")))
                .andExpect(jsonPath("$[1].firstName",is("Ranim")))
                .andExpect(jsonPath("$[1].lastName",is("Said")))
                .andExpect(jsonPath("$[1].bornAt",is("1999-05-23")))
                .andExpect(jsonPath("$[1].cin",is("00041558")));

    }

    @Test
    void testGetStudentById() throws Exception {
        // Set up our mocked service
        Student student = new Student(1l,"Hana", "Ben Asker","1999-04-22","11419727");
        doReturn(student).when(service).getStudentById(1l);

        // Execute the GET request
        mockMvc.perform(get("/student/{studentId}", 1L))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Validate the returned fields
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.firstName", is("Hana")))
                .andExpect(jsonPath("$.lastName",is("Ben Asker")))
                .andExpect(jsonPath("$.bornAt",is("1999-04-22")))
                .andExpect(jsonPath("$.cin",is("11419727")));
    }

    @Test

    void testCreateNewStudent() throws Exception {
        // Set up our mocked service
        Student studentToCreate = new Student("Hana", "Ben Asker","1999-04-22","11419727");
        Student studentToReturn = new Student(1l,"Hana", "Ben Asker","1999-04-22","11419727");
        doReturn(studentToReturn).when(service).createStudent(studentToCreate);

        // Execute the POST request
        mockMvc.perform(post("/student/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(studentToCreate)))

                // Validate the response code and content type
                .andExpect(status().isOk());
    }

}
