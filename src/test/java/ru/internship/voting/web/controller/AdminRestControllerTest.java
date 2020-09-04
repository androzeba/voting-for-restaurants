package ru.internship.voting.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.internship.voting.DishTestData;
import ru.internship.voting.RestaurantTestData;
import ru.internship.voting.model.Dish;
import ru.internship.voting.model.Restaurant;
import ru.internship.voting.repository.DishRepository;
import ru.internship.voting.repository.RestaurantRepository;
import ru.internship.voting.util.exception.NotFoundException;
import ru.internship.voting.web.AbstractControllerTest;
import ru.internship.voting.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.internship.voting.TestUtil.readFromJson;
import static ru.internship.voting.CommonTestData.NOT_FOUND;
import static ru.internship.voting.RestaurantTestData.*;
import static ru.internship.voting.DishTestData.*;

class AdminRestControllerTest extends AbstractControllerTest {

    private static final String RESTAURANT_URL = "/admin/restaurants/";
    private static final String DISH_URL = "/dishes/";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DishRepository dishRepository;

    @Test
    void addNewRestaurant() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(RESTAURANT_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtil.writeValue(newRestaurant)));

        Restaurant created = readFromJson(action, Restaurant.class);
        newRestaurant.setId(created.getId());
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(restaurantRepository.get(created.getId()), newRestaurant);
    }

    @Test
    void getRestaurantById() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANT_URL + RESTAURANT1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(RESTAURANT1));
    }

    @Test
    void getRestaurantNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANT_URL + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
        assertThrows(NotFoundException.class, () -> restaurantRepository.get(NOT_FOUND));
    }

    @Test
    void deleteRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.delete(RESTAURANT_URL + RESTAURANT2_ID))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> restaurantRepository.get(RESTAURANT2_ID));
    }

    @Test
    void deleteRestaurantNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(RESTAURANT_URL + NOT_FOUND))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateRestaurant() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(RESTAURANT_URL + RESTAURANT3_ID).contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        RESTAURANT_MATCHER.assertMatch(restaurantRepository.get(RESTAURANT3_ID), updated);
    }

    @Test
    void addNewDish() throws Exception {
        Dish newDish = DishTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(RESTAURANT_URL + RESTAURANT3_ID + DISH_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtil.writeValue(newDish)));

        Dish created = readFromJson(action, Dish.class);
        newDish.setId(created.getId());
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(dishRepository.get(created.getId(), RESTAURANT3_ID), newDish);
    }

    @Test
    void getDishById() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANT_URL + RESTAURANT1_ID + DISH_URL + DISH4_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(DISH4));
    }

    @Test
    void deleteDish() throws Exception {
        perform(MockMvcRequestBuilders.delete(RESTAURANT_URL + RESTAURANT1_ID + DISH_URL + DISH3_ID))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> dishRepository.get(DISH3_ID, RESTAURANT1_ID));
    }

    @Test
    void deleteDishNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(RESTAURANT_URL + RESTAURANT2_ID + DISH_URL + NOT_FOUND))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateDish() throws Exception {
        Dish updated = DishTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(RESTAURANT_URL + RESTAURANT1_ID + DISH_URL + DISH2_ID).contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        DISH_MATCHER.assertMatch(dishRepository.get(DISH2_ID, RESTAURANT1_ID), updated);

    }
}