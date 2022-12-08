package com.example.projectdevops.contollers;

import com.example.projectdevops.models.Book;
import com.example.projectdevops.models.Student;
import com.example.projectdevops.services.BookService;
import com.example.projectdevops.services.StudentService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.projectdevops.utils.objectMapper.asJsonString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc

public class BookControllerTest {

    @MockBean
    private BookService service;

    @Autowired
    private MockMvc mockMvc;

    @Test

    void testGetBooksSucces() throws Exception{
        Book book1 = new Book("bla","comedy","hahahah");
        Book book2 = new Book("hola","romance","sameplot");
        doReturn(Lists.newArrayList(book1,book2)).when(service).getAllBooks();

        mockMvc.perform(get("/book/all"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].title",is("bla")))
                .andExpect(jsonPath("$[0].genre",is("comedy")))
                .andExpect(jsonPath("$[0].plot",is("hahahah")))
                .andExpect(jsonPath("$[1].title",is("hola")))
                .andExpect(jsonPath("$[1].genre",is("romance")))
                .andExpect(jsonPath("$[1].plot",is("sameplot")));

    }

    @Test
    void testGetBookById() throws Exception {
        // Set up our mocked service
        Book book = new Book(1l,"bla","comedy","hahahah");
        doReturn(book).when(service).getBookById(1l);

        // Execute the GET request
        mockMvc.perform(get("/book/{authorId}", 1L))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Validate the returned fields
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.title", is("bla")))
                .andExpect(jsonPath("$.genre",is("comedy")))
                .andExpect(jsonPath("$.plot",is("hahahah")));
    }

    @Test

    void testCreateNewBook() throws Exception {
        // Set up our mocked service
        Book bookToCreate = new Book("bla", "comedy","hahahah");
        Book bookToReturn = new Book(1l,"bla", "comedy","hahahah");
        doReturn(bookToReturn).when(service).createBook(bookToCreate);

        // Execute the POST request
        mockMvc.perform(post("/book/create/{authorId}",20)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookToCreate)))

                // Validate the response code and content type
                .andExpect(status().isOk());
    }

}
