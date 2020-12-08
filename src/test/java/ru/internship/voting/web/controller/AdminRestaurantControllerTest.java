package ru.internship.voting.web.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.internship.voting.CommonTestData.NOT_FOUND;
import static ru.internship.voting.RestaurantTestData.RESTAURANT1;
import static ru.internship.voting.RestaurantTestData.RESTAURANT1_ID;
import static ru.internship.voting.RestaurantTestData.RESTAURANT2_ID;
import static ru.internship.voting.RestaurantTestData.RESTAURANT3_ID;
import static ru.internship.voting.RestaurantTestData.RESTAURANT_MATCHER;
import static ru.internship.voting.TestUtil.readFromJson;
import static ru.internship.voting.TestUtil.userHttpBasic;
import static ru.internship.voting.UserTestData.ADMIN;
import static ru.internship.voting.util.ValidationUtil.checkNotFoundWithId;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.internship.voting.RestaurantTestData;
import ru.internship.voting.model.Restaurant;
import ru.internship.voting.repository.RestaurantRepository;
import ru.internship.voting.util.exception.NotFoundException;
import ru.internship.voting.web.AbstractControllerTest;
import ru.internship.voting.web.json.JsonUtil;

class AdminRestaurantControllerTest extends AbstractControllerTest {

  private static final String RESTAURANT_URL = "/admin/restaurants/";

  @Autowired
  private RestaurantRepository restaurantRepository;

  @Test
  void addNewRestaurant() throws Exception {
    Restaurant newRestaurant = RestaurantTestData.getNew();
    ResultActions action = perform(MockMvcRequestBuilders.post(RESTAURANT_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtil.writeValue(newRestaurant))
        .with(userHttpBasic(ADMIN)))
        .andExpect(status().isCreated());

    Restaurant created = readFromJson(action, Restaurant.class);
    newRestaurant.setId(created.getId());
    RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
    RESTAURANT_MATCHER.assertMatch(restaurantRepository.get(created.getId()), newRestaurant);
  }

  @Test
  void getRestaurantById() throws Exception {
    perform(MockMvcRequestBuilders.get(RESTAURANT_URL + RESTAURANT1_ID)
        .with(userHttpBasic(ADMIN)))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(RESTAURANT_MATCHER.contentJson(RESTAURANT1));
  }

  @Test
  void getRestaurantNotFound() throws Exception {
    perform(MockMvcRequestBuilders.get(RESTAURANT_URL + NOT_FOUND)
        .with(userHttpBasic(ADMIN)))
        .andDo(print())
        .andExpect(status().isUnprocessableEntity());
    assertThrows(NotFoundException.class, () -> checkNotFoundWithId(restaurantRepository.get(NOT_FOUND), NOT_FOUND));
  }

  @Test
  void deleteRestaurant() throws Exception {
    perform(MockMvcRequestBuilders.delete(RESTAURANT_URL + RESTAURANT2_ID)
        .with(userHttpBasic(ADMIN)))
        .andExpect(status().isNoContent());
    assertThrows(NotFoundException.class, () -> checkNotFoundWithId(restaurantRepository.get(RESTAURANT2_ID), RESTAURANT2_ID));
  }

  @Test
  void deleteRestaurantNotFound() throws Exception {
    perform(MockMvcRequestBuilders.delete(RESTAURANT_URL + NOT_FOUND)
        .with(userHttpBasic(ADMIN)))
        .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void updateRestaurant() throws Exception {
    Restaurant updated = RestaurantTestData.getUpdated();
    perform(MockMvcRequestBuilders.put(RESTAURANT_URL + RESTAURANT3_ID).contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtil.writeValue(updated))
        .with(userHttpBasic(ADMIN)))
        .andExpect(status().isNoContent());

    RESTAURANT_MATCHER.assertMatch(restaurantRepository.get(RESTAURANT3_ID), updated);
  }
}