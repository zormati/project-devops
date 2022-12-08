package com.example.projectdevops.contollers;

import com.example.projectdevops.models.Author;
import com.example.projectdevops.models.Student;
import com.example.projectdevops.services.AuthorService;
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

public class AuthorControllerTest {

    @MockBean
    private AuthorService service;

    @Autowired
    private MockMvc mockMvc;

    @Test

    void testGetAuthorsSucces() throws Exception{
        Author author1 = new Author("Hana","Ben Asker","hola hola hola");
        Author author2 = new Author("Ranim","Said","engineering student");
        doReturn(Lists.newArrayList(author1,author2)).when(service).getAllAuthors();

        mockMvc.perform(get("/author/all"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].firstName",is("Hana")))
                .andExpect(jsonPath("$[0].lastName",is("Ben Asker")))
                .andExpect(jsonPath("$[0].bio",is("hola hola hola")))
                .andExpect(jsonPath("$[1].firstName",is("Ranim")))
                .andExpect(jsonPath("$[1].lastName",is("Said")))
                .andExpect(jsonPath("$[1].bio",is("engineering student")));

    }

    @Test
    void testGetAuthorById() throws Exception {
        // Set up our mocked service
        Author author = new Author(1l,"Hana", "Ben Asker","hola hola hola");
        doReturn(author).when(service).getAuthorById(1l);

        // Execute the GET request
        mockMvc.perform(get("/author/{authorId}", 1L))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Validate the returned fields
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.firstName", is("Hana")))
                .andExpect(jsonPath("$.lastName",is("Ben Asker")))
                .andExpect(jsonPath("$.bio",is("hola hola hola")));
    }

    @Test

    void testCreateNewStudent() throws Exception {
        // Set up our mocked service
        Author authorToCreate = new Author("Hana", "Ben Asker","hola hola hola");
        Author authorToReturn = new Author(1l,"Hana", "Ben Asker","hola hola hola");
        doReturn(authorToReturn).when(service).createAuthor(authorToCreate);

        // Execute the POST request
        mockMvc.perform(post("/author/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(authorToCreate)))

                // Validate the response code and content type
                .andExpect(status().isOk());
    }

}
