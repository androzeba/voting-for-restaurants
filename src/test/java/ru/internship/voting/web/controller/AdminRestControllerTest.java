package ru.internship.voting.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.internship.voting.repository.RestaurantRepository;
import ru.internship.voting.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.internship.voting.CommonTestData.NOT_FOUND;

class AdminRestControllerTest extends AbstractControllerTest {

    private static final String RESTAURANT_URL = "/admin/restaurants/";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void addNewRestaurant() {
    }

    @Test
    void getRestaurantById() {
    }

    @Test
    void getRestaurantNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANT_URL + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void deleteRestaurant() {
    }

    @Test
    void updateRestaurant() {
    }

    @Test
    void addNewDish() {
    }

    @Test
    void getDishById() {
    }

    @Test
    void deleteDish() {
    }

    @Test
    void updateDish() {
    }
}