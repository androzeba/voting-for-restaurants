package ru.internship.voting.web.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.internship.voting.RestaurantTestData.RESTAURANT1;
import static ru.internship.voting.RestaurantTestData.RESTAURANT1_ID;
import static ru.internship.voting.RestaurantTestData.RESTAURANT2;
import static ru.internship.voting.RestaurantTestData.RESTAURANT3;
import static ru.internship.voting.RestaurantTestData.RESTAURANT4;
import static ru.internship.voting.RestaurantTestData.RESTAURANT5;
import static ru.internship.voting.RestaurantTestData.RESTAURANT_MATCHER;
import static ru.internship.voting.RestaurantTestData.RESTAURANT_WITH_DISHES_MATCHER;
import static ru.internship.voting.TestUtil.userHttpBasic;
import static ru.internship.voting.UserTestData.USER1;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.internship.voting.web.AbstractControllerTest;

class ProfileRestaurantControllerTest extends AbstractControllerTest {

  private static final String RESTAURANT_URL = "/restaurants/";

  @Test
  void getAllRestaurants() throws Exception {
    perform(MockMvcRequestBuilders.get(RESTAURANT_URL)
        .with(userHttpBasic(USER1)))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(RESTAURANT_MATCHER.contentJson(RESTAURANT1, RESTAURANT2, RESTAURANT3, RESTAURANT4, RESTAURANT5));
  }

  @Test
  void getRestaurantWithDishes() throws Exception {
    perform(MockMvcRequestBuilders.get(RESTAURANT_URL + RESTAURANT1_ID + "/with-dishes")
        .with(userHttpBasic(USER1)))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(RESTAURANT_WITH_DISHES_MATCHER.contentJson(RESTAURANT1));
  }
}