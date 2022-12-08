package com.example.projectdevops.contollers;

import com.example.projectdevops.models.IdentificationCard;
import com.example.projectdevops.models.Student;
import com.example.projectdevops.services.IdentificationCardService;
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

public class IdentificationCardTest {

    @MockBean
    private IdentificationCardService service;

    @Autowired
    private MockMvc mockMvc;

    @Test

    void testGetCardsSucces() throws Exception{
        IdentificationCard card1 = new IdentificationCard("2022");
        IdentificationCard card2 = new IdentificationCard("2021");
        doReturn(Lists.newArrayList(card1,card2)).when(service).getAllIds();

        mockMvc.perform(get("/identification/all"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].schoolYear",is("2022")))
                .andExpect(jsonPath("$[1].schoolYear",is("2021")));

    }

    @Test
    void testGetCardById() throws Exception {
        // Set up our mocked service
        IdentificationCard card = new IdentificationCard(1l,"2022");
        doReturn(card).when(service).getIdCardById(1l);

        // Execute the GET request
        mockMvc.perform(get("/identification/{cardId}", 1L))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Validate the returned fields
                .andExpect(jsonPath("$.schoolYear", is("2022")));
    }

    @Test

    void testCreateNewCard() throws Exception {
        // Set up our mocked service
        IdentificationCard cardToCreate = new IdentificationCard("2022");
        IdentificationCard cardToReturn = new IdentificationCard(1l,"2022");
        doReturn(cardToReturn).when(service).createIdentificationCard(cardToCreate);

        // Execute the POST request
        mockMvc.perform(post("/identification/create/{studentId}",1l)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(cardToCreate)))

                // Validate the response code and content type
                .andExpect(status().isOk());
    }

}
