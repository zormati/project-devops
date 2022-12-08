package com.example.projectdevops.services;



import com.example.projectdevops.exceptions.ResourceNotFoundException;
import com.example.projectdevops.models.IdentificationCard;
import com.example.projectdevops.models.Student;
import com.example.projectdevops.repositories.IdentificationCardRepository;
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
public class IdentificationCardServiceTest {

    /**
     * Autowire in the service we want to test
     */
    @Autowired
    private IdentificationCardService service;

    /**
     * Create a mock implementation of the OwnerRepository
     */
    @MockBean
    private IdentificationCardRepository repository;


    @Test
    @DisplayName("Test findCardById Success")
    void testFindById() {
        // Set up our mock repository
        IdentificationCard card = new IdentificationCard("2022");
        doReturn(Optional.of(card)).when(repository).findById(1l);

        // Execute the service call
        IdentificationCard returnedCard = service.getIdCardById(1l);

        // Assert the response
        Assertions.assertSame(returnedCard, card, "The card returned was not the same as the mock");
    }

    @Test
    @DisplayName("Test findCardById Not Found")
    void testFindByIdNotFound() {

        doReturn(Optional.empty()).when(repository).findById(1l);

        Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            IdentificationCard returnedCard = service.getIdCardById(1l);
        });
        String expectedMessage = "Not id card matches.";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    @DisplayName("Test getAllCards")
    void testGetAllOCards() {

        // Set up our mock repository
        IdentificationCard card1 = new IdentificationCard("2023");
        IdentificationCard card2 = new IdentificationCard("2019");
        doReturn(Arrays.asList(card1, card2)).when(repository).findAll();

        // Execute the service call
        List<IdentificationCard> cards = service.getAllIds();

        // Assert the response
        Assertions.assertEquals(2, cards.size(), "findAll should return 2 cards");
    }


    @Test
    @DisplayName("Test createCard")
    void testCreateStudent() {

        IdentificationCard cardToSave = new IdentificationCard("2023");
        IdentificationCard card = new IdentificationCard(1l,"2023");
        doReturn(card).when(repository).save(cardToSave);

        // Execute the service call
        IdentificationCard returnedCard = service.createIdentificationCard(cardToSave);

        // Assert the response
        Assertions.assertNotNull(returnedCard, "The saved student card not be null");
        Assertions.assertSame(card, returnedCard, "The returned card is not the same as expected");
    }
}
