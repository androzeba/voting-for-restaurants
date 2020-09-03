package ru.internship.voting.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.internship.voting.repository.RestaurantRepository;
import ru.internship.voting.web.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.internship.voting.TestUtil.readFromJson;
import static ru.internship.voting.CommonTestData.NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;

class ProfileRestControllerTest extends AbstractControllerTest {

    private static final String VOTE_URL = "/votes/";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void getAllRestaurants() {
    }

    @Test
    void getAllRestaurantsWithDishes() {
    }

    @Test
    void getRestaurantWithDishes() {
    }

    @Test
    void getAllDishesByRestId() {
    }

    @Test
    void getFilteredDishesByRestId() {
    }

    @Test
    void getAllVotes() throws Exception {
        perform(MockMvcRequestBuilders.get(VOTE_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
//                .andExpect(VOTE_MATCHER)
    }

    @Test
    void getFilteredVotes() {
    }

    @Test
    void createOrUpdateVote() {
    }
}